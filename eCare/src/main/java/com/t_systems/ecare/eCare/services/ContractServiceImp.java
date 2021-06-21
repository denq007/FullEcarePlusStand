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

    /**
     * Transforms the Contract to ContractDTO
     *
     * @param contract database {@code Contract} object
     * @return contractDTO (@code ContractDTO) data transfer object contains option properties
     */
    @Override
    public ContractDTO convertToDto(Contract contract) {
        return modelMapper.map(contract, ContractDTO.class);
    }

    /**
     * Transforms the ContractDTO to Contract
     *
     * @param contractDTO data transfer object
     * @return contract (@code Contract)
     */
    @Override
    public Contract convertToEntity(ContractDTO contractDTO) {
        return modelMapper.map(contractDTO, Contract.class);
    }

    /**
     * Update all fields in corresponding {@code Contract} with field values from data transfer object
     *
     * @param dto data transfer object contains contract id and properties
     * @return either empty Optional if contract is successfully updated or error message if not
     */
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
        set.addAll(dto.getOptionsIds().stream().map(s -> optionDAO.findOne(s)).collect(Collectors.toSet()));
        contract.setAddOptionIdList(set);
        contractDAO.update(contract);
        return Optional.empty();
    }

    /**
     * Build data transfer object for {@code Contract} with specific id, including fields of type collection (initialized)
     *
     * @param id database id of desired {@code Contract} object
     * @return {@code ContractDTO} object contains contract properties
     */
    @Override
    @Transactional
    public ContractDTO getDto(int id) {
        Contract contract = contractDAO.findOne(id);
        ContractDTO dto = convertToDto(contract);
        dto.setAddNameOptions(contract.getAddOptionIdList().stream().map(s -> s.getName()).collect(Collectors.toSet()));
        dto.setOptionsIds(contract.getAddOptionIdList().stream().map(s -> s.getId()).collect(Collectors.toSet()));
        return dto;
    }

    /**
     * Add additional data into contract data transfer object
     *
     * @param dto
     */
    public void showTariffandOptions(ContractDTO dto) {
        Map<String, Integer> mapOptions = dto.getAllOptions();
        optionDAO.getAllOptionNamesAndIds().forEach(array -> mapOptions.put((String) array[1], (Integer) array[0]));
        Map<String, Integer> mapTariffs = dto.getAllTariffs();
        tariffDAO.getAllTariffNamesAndIds().forEach(array -> mapTariffs.put((String) array[1], (Integer) array[0]));
    }

    /**
     * Create new {@code Contract} based on dto properties
     *
     * @param contractDTO data transfer object contains required properties
     * @return empty Optional if contract is successfully created or error message if not
     */
    @Override
    @Transactional
    public Optional<String> create(ContractDTO contractDTO) {
        Contract contract = new Contract();
        Tariff tariff = tariffDAO.findOne(contractDTO.getTariffId());
        contract.setTariffId(tariff);
        contract.setBlockedByUser(contractDTO.isBlockedByUser());
        contract.setBlockedByAdmin(contractDTO.isBlockedByAdmin());
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

    /**
     * Requests all contracts in (@link ContractDAO) database
     *
     * @return list of {@code ContractDTO}
     */
    @Override
    @Transactional
    public List<ContractDTO> showAllContracts() {
        List<Contract> list = contractDAO.findAll();
        return list.stream().map(s -> convertToDto(s)).collect(Collectors.toList());
    }

    /**
     * Checking the contract status (true or false)
     *
     * @param dto
     * @return boolean
     */
    @Override
    public boolean isContractBlocked(ContractDTO dto) {
        if (dto.isBlockedByAdmin() || dto.isBlockedByUser())
            return true;
        return false;
    }

    /**
     * Update all fields in corresponding {@code ContractDTO} with field values from data transfer object
     *
     * @param basket data transfer object
     * @return {@code ContractDTO} object contains contract properties
     */
    @Override
    @Transactional
    public ContractDTO updateByCart(BasketImpl basket) {
        Tariff tariff = tariffDAO.findOne(basket.getTariffId());
        Contract contract = contractDAO.findOne(basket.getId());
        contract.setTariffId(tariff);
        contract.setAddOptionIdList(basket.getOptionsIds().stream().map(s -> optionDAO.findOne(s)).collect(Collectors.toSet()));
        contractDAO.update(contract);
        basket.clearAll();
        return convertToDto(contract);
    }


}
