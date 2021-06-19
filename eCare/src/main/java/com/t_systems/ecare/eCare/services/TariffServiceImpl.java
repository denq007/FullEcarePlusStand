package com.t_systems.ecare.eCare.services;

import com.t_systems.ecare.eCare.DAO.OptionDAO;
import com.t_systems.ecare.eCare.DAO.TariffDAO;
import com.t_systems.ecare.eCare.DTO.RequiredOptionDTO;
import com.t_systems.ecare.eCare.DTO.TariffDTO;
import com.t_systems.ecare.eCare.entity.Option;
import com.t_systems.ecare.eCare.entity.Tariff;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TariffServiceImpl implements TariffService {
    @Autowired
    TariffDAO tariffDAO;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    OptionDAO optionDAO;
    @Autowired
    RequiredOptionServiceImpl requiredOptionService;
    @Autowired
    HotTariffService hotTariffService;

    /**
     * Transforms the Tariff to TariffDTO (all fields)
     *
     * @param tariff database {@code Tariff} object
     * @return TariffDTO (@code TariffDTO) object contains tariff properties
     */
    @Override
    public TariffDTO convertToDto(Tariff tariff) {
        TariffDTO tariffDTO = new TariffDTO();
        tariffDTO.setId(tariff.getId());
        tariffDTO.setName(tariff.getName());
        tariffDTO.setPrice(tariff.getPrice());
        tariffDTO.setOld(tariff.isOld());
        tariffDTO.setOptionName(tariff.getOptionIdList().stream().map(s -> s.getName()).collect(Collectors.toList()));
        tariffDTO.setTariffOption(tariff.getOptionIdList().stream().map(s -> s.getNumberGroup()).collect(Collectors.toSet()));
        tariffDTO.setRequiredOption(showAllRequiredOptions(tariffDTO.getTariffOption()));
        return tariffDTO;
    }

    /**
     * Add additional data into tariff data transfer object
     *
     * @param tariffDTO
     */

    @Override
    public void showAllOptions(TariffDTO tariffDTO) {
        Map<String, Integer> mapOptions = tariffDTO.getAllOptions();
        optionDAO.getAllOptionNamesAndIds().forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
    }

    /**
     * Add additional data into tariff data transfer object
     *
     * @param tariffOption
     * @return list of {@code String} name {@code Option}
     */
    @Override
    public List<String> showAllRequiredOptions(Set<Integer> tariffOption) {
        List<String> list = new ArrayList<>();
        Set<Integer> set = tariffOption;
        Map<Integer, List<RequiredOptionDTO>> map = requiredOptionService.findAllRequiredOption();
        for (Integer i : set) {
            if (map.containsKey(i)) {
                List<RequiredOptionDTO> listRequired = map.get(i);
                for (int j = 0; j < listRequired.size(); j++) {
                    list.add(listRequired.get(j).getName());
                }
            }
        }
        return list;
    }

    /**
     * Request a tariff by its name (@link TarifDAO)
     *
     * @param name tariffs's name
     * @return TariffDTO (@code TariffDTO) object contains tariff properties
     */

    @Override
    @Transactional
    public TariffDTO findTariffByName(String name) {
        try {
            Tariff tariff = tariffDAO.findByName(name);
            return convertToDto(tariff);
        } catch (NullPointerException e) {
            return null;
        }

    }

    /**
     * Delete {@code Option} with specific id from database if it is not used in any contract;
     *
     * @param name name of tariff to delete
     * @return empty Optional if option is successfully deleted or error message if not
     */
    @Override
    @Transactional
    public Optional<String> deleteTariff(String name) {
        Tariff tariff = tariffDAO.findByName(name);
        if (tariff == null) {
            return Optional.of("tariff not found");
        }
        if (tariffDAO.isUsed(tariff.getId())) {
            return Optional.of("tariff is used in some contracts");
        }
        tariffDAO.delete(tariff);

        return Optional.empty();
    }

    /**
     * Requests all tariffs in (@link TarifDAO) database (including-unused tariffs)
     *
     * @return list of {@code TariffDTO}
     */
    @Override
    @Transactional
    public List<TariffDTO> showAllTariffs() {

        return tariffDAO.findAll().stream().map(s -> convertToDto(s)).filter(s -> s.isOld() != true).collect(Collectors.toList());
    }

    /**
     * Requests all tariffs in (@link TarifDAO) database (no unused tariffs)
     *
     * @return list of {@code TariffDTO}
     */
    @Override
    @Transactional
    public List<TariffDTO> showAllTariffsForEmployee() {
        return tariffDAO.findAll().stream().map(s -> convertToDto(s)).collect(Collectors.toList());
    }

    /**
     * Requests last added tariffs in (@link TarifDAO) database
     *
     * @param i i count tariffs
     * @return list of {@code TariffDTO}
     */
    @Override
    @Transactional
    public List<Tariff> getLast(int i) {
        return tariffDAO.getLast(i);
    }

    /**
     * Requests tariff by id (@link TariffDAO) in database
     *
     * @param id database id of desired {@code Tariff} object
     * @return TariffDTO (@code TariffDTO) object contains tariff properties
     */
    @Override
    @Transactional
    public TariffDTO findById(int id) {
        return convertToDto(tariffDAO.findOne(id));
    }

    /**
     * Update all fields in corresponding {@code Tariff} with field values from data transfer object
     *
     * @param tariffDTO data transfer object contains tariff id and properties
     * @return either empty Optional if contract is successfully updated or error message if not
     */
    @Override
    @Transactional
    public Optional<String> update(TariffDTO tariffDTO) {
        Tariff tariff = tariffDAO.findOne(tariffDTO.getId());
        tariff.getOptionIdList().clear();
        if (checkOptionsCompatibility(getOptionsById((tariffDTO.getTariffOption()).stream().collect(Collectors.toList()))) == false) {
            return Optional.of("Incompatible options are selected");
        }

        List<Option> listForAdd = getOptionsById((tariffDTO.getTariffOption()).stream().collect(Collectors.toList()));
        tariff.setName(tariffDTO.getName());
        tariff.setOptionIdList(listForAdd);
        tariff.setPrice(tariffDTO.getPrice());
        tariffDAO.update(tariff);
        hotTariffService.sendMessage();
        return Optional.empty();
    }

 /*   public Optional<String> checkRepeatOption(List<Option> listOld, List<Option> listNew) {
        for (int i = 0; i < listNew.size(); i++) {
            if (listOld.contains(listNew.get(i)))
                return Optional.of("This option is already added -" + listNew.get(i));
        }
        return Optional.empty();
    }*/

    /**
     * Transforms the TariffDTO to Tariff
     *
     * @param tariffDTO data transfer object
     * @return tariff (@code Tariff)
     */
    @Override
    public Tariff convertToEntity(TariffDTO tariffDTO) {
        Tariff tariff = modelMapper.map(tariffDTO, Tariff.class);
        return tariff;
    }

    /**
     * Create new {@code Tariff} based on dto properties
     *
     * @param tariffDTO data transfer object contains required properties
     * @return empty Optional if contract is successfully created or error message if not
     */
    @Override
    @Transactional
    public Optional<String> saveTariff(TariffDTO tariffDTO) {
        Tariff newTariff = tariffDAO.findByName(tariffDTO.getName());
        if (newTariff != null) {
            return Optional.of("This tariff's name is already exist");
        }
        newTariff = convertToEntity(tariffDTO);
        List<Option> list = getOptionsById((tariffDTO.getTariffOption()).stream().collect(Collectors.toList()));
        if (checkOptionsCompatibility(list) == false) {
            return Optional.of("Incompatible options are selected");
        }
        newTariff.setOptionIdList(list);
        tariffDAO.save(newTariff);
        tariffDTO.setOptionName(newTariff.getOptionIdList().stream().map(s -> s.getName()).collect(Collectors.toList()));
        hotTariffService.sendMessage();
        return Optional.empty();
    }

    /**
     * Checking the compatibility of options (@code Option).
     * Options must be from different groups (there can be any number of options with the group number - 0).
     *
     * @param optionList
     * @return
     */
    @Override
    public boolean checkOptionsCompatibility(List<Option> optionList) {
        boolean b = true;
        Map<Integer, List<Option>> map = optionList.stream()
                .collect(Collectors.groupingBy(Option::getNumberGroup, Collectors.toList()));
        for (Integer i : map.keySet()) {
            if (map.get(i).size() > 1 && i != 0) {
                b = false;
                break;
            }
        }
        return b;
    }

    /**
     * Add additional data into tariff data transfer object
     * @param listId
     * @return list of {@code Option}
     */
    @Transactional
    public List<Option> getOptionsById(List<Integer> listId) {
        List<Option> listOption = new ArrayList<>();
        for (int a : listId
        ) {
            listOption.add(optionDAO.findOne(a));
        }
        return listOption;
    }

}
