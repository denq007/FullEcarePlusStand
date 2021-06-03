/*
package com.t_systems.ecare.eCare.Converter;

import com.t_systems.ecare.eCare.DTO.ContractDTO;
import com.t_systems.ecare.eCare.entity.Contract;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@NoArgsConstructor
public class ContractConverterImpl implements ContractConverter{
    @Autowired
    CustomerConverter customerConverter;
    @Autowired
    TariffConverter tariffConverter;
    @Override
    public Contract converterContractDTOToContract(ContractDTO contractDTO) {
        Contract contract=new Contract();
        contract.setCustomerId(customerConverter.converterCustomerDTOToCustomer(contractDTO.getCustomerDTO()));
        contract.setId(contractDTO.getContractId());
        contract.setBlockedByAdmin(contractDTO.isBlockedByAdmin());
        contract.setBlockedByUser(contractDTO.isBlockedByCustomer());
        contract.setTariffId(tariffConverter.converterTariffDTOToTareff(contractDTO.getTariffDTO()));
        contract.setNumber(contractDTO.getContractNumber());
        return contract;
    }
    public ContractDTO convertContractToContractDTO(Contract contract)
    {
        ContractDTO contractDTO=new ContractDTO();
        contractDTO.setContractId(contract.getId());
        contractDTO.setContractNumber(contract.getNumber());
        contractDTO.setCustomerDTO(customerConverter.converterCustomerToCustomerDTO(contract.getCustomerId()));
        contractDTO.setTariffDTO(tariffConverter.converterTarifToTariffDTO(contract.getTariffId()));
        contractDTO.setBlockedByAdmin(contract.isBlockedByAdmin());
        contractDTO.setBlockedByCustomer(contract.isBlockedByUser());

        return contractDTO;
    }

}
*/
