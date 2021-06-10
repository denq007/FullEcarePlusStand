package com.t_systems.ecare.eCare.services;

import com.t_systems.ecare.eCare.DAO.*;
import com.t_systems.ecare.eCare.DTO.ContractDTO;
import com.t_systems.ecare.eCare.basket.BasketImpl;
import com.t_systems.ecare.eCare.entity.Contract;
import com.t_systems.ecare.eCare.entity.Customer;
import com.t_systems.ecare.eCare.entity.Option;
import com.t_systems.ecare.eCare.entity.Tariff;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContractServiceImp implements ContractService {
    @Autowired
    ContractDAOImpl contractDAO;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    OptionDAO optionDAO;
    @Autowired
    TariffDAO tariffDAO;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerDAOImpl customerDAO;
    @Autowired
    PhoneNumberDAO phoneNumberDAO;
    @Autowired
    OptionService optionService;

    @Override
    public ContractDTO convertToDto(Contract contract) {
        return modelMapper.map(contract, ContractDTO.class);
    }

    @Override
    public Contract convertToEntity(ContractDTO contractDTO) {
        Contract contract = modelMapper.map(contractDTO, Contract.class);
        return contract;
    }

    @Override
    @Transactional
    public Optional<String> update(ContractDTO dto) {
        Tariff tariff = tariffDAO.findOne(dto.getTariffId());
        Contract contract = contractDAO.findOne(dto.getId());
        Optional<String> error = optionService.checkCompatibilityOptions(dto.getOptionsIds(), tariff.getOptionIdList(), contract.getAddOptionIdList());
        if (error.isPresent()) {
            return error;
        }
        dto.setAddNameOptions(optionService.deleteOptionsAvailableTariffAnDADDNameOption(dto.getOptionsIds(), tariff.getOptionIdList()));
        Customer customer = contract.getCustomerId();
        contract = convertToEntity(dto);
        contract.setCustomerId(customer);
        contract.setTariffId(tariff);
        contract.setBlockedByUser(dto.isBlockedByUser());
        contract.setBlockedByAdmin(dto.isBlockedByAdmin());
        Set<Option> set = contract.getAddOptionIdList();
        Set<Integer> wer = dto.getOptionsIds();
        set.addAll(dto.getOptionsIds().stream().map(s -> optionDAO.findOne(s)).collect(Collectors.toSet()));
        contract.setAddOptionIdList(set);
        contractDAO.update(contract);
        return Optional.empty();
    }

    @Override
    @Transactional
    public ContractDTO getDto(int id) {
        Contract contract = contractDAO.findOne(id);
        ContractDTO dto = convertToDto(contract);
        dto.setAddNameOptions(contract.getAddOptionIdList().stream().map(s -> s.getName()).collect(Collectors.toSet()));
        dto.setOptionsIds(contract.getAddOptionIdList().stream().map(s -> s.getId()).collect(Collectors.toSet()));
        return dto;
    }

    public void showTariffandOptions(ContractDTO dto) {
        Map<String, Integer> mapOptions = dto.getAllOptions();
        optionDAO.getAllOptionNamesAndIds().forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
        Map<String, Integer> mapTariffs = dto.getAllTariffs();
        tariffDAO.getAllTariffNamesAndIds().forEach(array -> mapTariffs.put((String) array[1], (Integer) array[0]));
    }

    @Override
    @Transactional
    public Optional<String> create(ContractDTO contractDTO) {
        Contract contract = new Contract();
        Tariff tariff = tariffDAO.findOne(contractDTO.getTariffId());
        contract.setTariffId(tariff);
        contract.setBlockedByUser(contractDTO.isBlockedByUser());
        contract.setBlockedByAdmin(contractDTO.isBlockedByAdmin());
        // contract.setCustomerId(customerService.convertToEntity(customerService.findById(contractDTO.getCustomerId())));
        contract.setCustomerId(customerDAO.findOne(contractDTO.getCustomerId()));
        contract.setNumber(contractDTO.getNumber());
        Optional<String> error = optionService.checkCompatibilityOptions(contractDTO.getOptionsIds(), tariff.getOptionIdList(), contract.getAddOptionIdList());
        if (error.isPresent()) {
            return error;
        }
        contract.setAddOptionIdList(contractDTO.getOptionsIds().stream().map(s -> optionDAO.findOne(s)).collect(Collectors.toSet()));
        contractDTO.setId(contractDAO.save(contract));
        contractDTO.setTariffName(tariff.getName());
        return Optional.empty();
    }

    @Override
    @Transactional
    public List<ContractDTO> showAllContracts() {
        List<Contract> list = contractDAO.findAll();
        return list.stream().map(s -> convertToDto(s)).collect(Collectors.toList());
    }

    @Override
    public boolean isContractBlocked(ContractDTO dto) {
        if (dto.isBlockedByAdmin() || dto.isBlockedByUser())
            return true;
        return false;
    }

    @Override
    @Transactional
    public ContractDTO updateByCart(BasketImpl basket) {
        Tariff tariff = tariffDAO.findOne(basket.getTariffId());
        Contract contract = contractDAO.findOne(basket.getId());
        /*  Customer customer = contract.getCustomerId();*/
        contract.setTariffId(tariff);
        contract.setAddOptionIdList(basket.getOptionsIds().stream().map(s -> optionDAO.findOne(s)).collect(Collectors.toSet()));
        contractDAO.update(contract);
        basket.clearAll();
        return convertToDto(contract);
    }


}
