package com.t_systems.ecare.eCare.services;

import com.t_systems.ecare.eCare.DAO.OptionDAO;
import com.t_systems.ecare.eCare.DTO.OptionDTO;
import com.t_systems.ecare.eCare.entity.Option;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Access;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OptionServiceImpl implements OptionService {
    @Autowired
    OptionDAO optionDAO;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TariffService tariffService;

    /**
     * Create new {@code Option} based on dto properties
     * @param optionDTO data transfer object contains required properties
     * @return empty Optional if contract is successfully created or error message if not
     */

    @Override
    @Transactional
    public Optional<String> saveOption(OptionDTO optionDTO) {
        Option newOption = optionDAO.findByName(optionDTO.getOptionName());
        if (newOption != null) {
            return Optional.of("This option's name is already registered");
        }
        newOption = convertToEntity(optionDTO);
        optionDAO.save(newOption);
        return Optional.empty();
    }

    /**
     * Transforms the Option to OptionDTO
     * @param option database {@code Option} object
     * @return optionDTO (@code OptionDTO)
     */
    @Override
    public OptionDTO convertToDto(Option option) {
        return modelMapper.map(option, OptionDTO.class);
    }

    /**
     * Transforms the OptionDTO to Option
     * @param optionDTO data transfer object
     * @return option (@code Option)
     */
    @Override
    public Option convertToEntity(OptionDTO optionDTO) {
        return modelMapper.map(optionDTO, Option.class);
    }

    /**
     * Requests all options in (@link OptionDAO) database
     * @return list of {@code ContractDTO}
     */
    @Override
    @Transactional
    public List<OptionDTO> getAllOptions() {
        return optionDAO.findAll().stream().map(s -> convertToDto(s)).collect(Collectors.toList());
    }

    /**
     * Checking the compatibility of options (@code Option).
     * Options must be from different groups (there can be any number of options with the group number - 0).
     * @param idOptionforAdd new options for add
     * @param tariffOptionList tariffs's options
     * @param contractOptions contract's options
     * @return empty Optional if contract is successfully created or error message if not
     */
    //check id and group
    public Optional<String> checkCompatibilityOptions(Set<Integer> idOptionforAdd, List<Option> tariffOptionList, Set<Option> contractOptions) {
        if (idOptionforAdd.isEmpty()) {
            return Optional.empty();
        }
        List<Integer> onlyNewOption = new ArrayList<>();
        onlyNewOption.addAll(idOptionforAdd);
        Set<Option> list = new HashSet<>();
        for (int i = 0; i < onlyNewOption.size(); i++) {
            list.add(optionDAO.findOne(onlyNewOption.get(i)));
        }
        list.removeAll(contractOptions);
        if(list.size()==0)
        {
            return Optional.of("You already have all these options");
        }
        list.addAll(tariffOptionList);
        list.addAll(contractOptions);
        list.stream().filter(s -> s.getNumberGroup() != 0).collect(Collectors.toSet());
        Map<Integer, Set<Option>> map2 = list.stream()
                .collect(Collectors.groupingBy(Option::getNumberGroup, Collectors.toSet()));
        for (Integer a : map2.keySet()) {
            String str = "";
            Set<Option> set1 = map2.get(a);
            if (set1.size() > 1) {
                Iterator<Option> itr = map2.get(a).iterator();
                while (itr.hasNext()) {
                    str += itr.next().getName() + " and ";
                }
                return Optional.of("You are trying to add incompatible options - " + str.substring(0, str.length() - 4) + " ");
            }
        }

        return Optional.empty();
    }

    /**
     * Add additional data into contract data transfer object
     * @param idOptionforAdd new options for add
     * @param tariffOptionList tariffs's options
     * @return
     */
    public Set<String> deleteOptionsAvailableTariffAnDADDNameOption(Set<Integer> idOptionforAdd, List<Option> tariffOptionList) {
        Set<String> setNameOption = new HashSet<>();
        for (int i = 0; i < tariffOptionList.size(); i++) {
            if (idOptionforAdd.contains(tariffOptionList.get(i).getId())) {
                idOptionforAdd.remove(tariffOptionList.get(i).getId());
            } else {
                setNameOption.add(tariffOptionList.get(i).getName());
            }
        }
        return setNameOption;
    }

    /**
     * Requests option by id (@link OptionDAO) in database
     * @param id database id of desired {@code Option} object
     * @return optionDTO (@code OptionDTO) object contains option properties
     */
    @Override
    @Transactional
    public OptionDTO findById(int id) {
      return  convertToDto(optionDAO.findOne(id));
    }

    /**
     * Update all fields in corresponding {@code Option} with field values from data transfer object
     * @param dto data transfer object contains tariff id and properties
     * @return either empty Optional if contract is successfully updated or error message if not
     */
    @Override
    @Transactional
    public Optional<String> update(OptionDTO dto) {
       Option option=optionDAO.findOne(dto.getOptionId());
       if(option.getTariffsList().size()!=0)
       {
           return Optional.of("This option is used in the tariffs");
       }
       if(option.getContractList().size()!=0)
       {
           return Optional.of("This option is used in the contracts");
       }
        option.setPrice(dto.getOptionPrice());
        option.setName(dto.getOptionName());
        option.setConnectionCost(dto.getOptionConnectionCost());
        option.setNumberGroup(dto.getOptionGroupNumber());
       // option.setTariffsList(dto.getListTariff().stream().map(s->tariffService.convertToEntity(s)).collect(Collectors.toList()));
        optionDAO.update(option);
        return Optional.empty();
    }

    /**
     * Add additional data into contract data transfer object
     * Requests options by id (@link OptionDAO) in database
     * @param id id of all options to be found
     * @return set of {@code String}
     */
    @Override
    @Transactional
    public Set<String> getOptionsNameById(Set<Integer> id) {
      return  (id.stream().map(s->optionDAO.findOne(s).getName())).collect(Collectors.toSet());
    }


}
