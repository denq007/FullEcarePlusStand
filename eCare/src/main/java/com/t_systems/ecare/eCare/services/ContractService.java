package com.t_systems.ecare.eCare.services;

import com.t_systems.ecare.eCare.DTO.ContractDTO;
import com.t_systems.ecare.eCare.entity.Contract;

import java.util.List;
import java.util.Optional;

public interface ContractService {
    public Optional<String> update(ContractDTO contractDTO);
    public ContractDTO getDto(int id);
    public ContractDTO convertToDto(Contract contract);
    public Contract convertToEntity(ContractDTO contractDTO);
    public void showTariffandOptions(ContractDTO dto);
    Optional<String> create(ContractDTO contractDTO);
    List<ContractDTO> showAllContracts();
    public boolean isContractBlocked(ContractDTO dto);
}
