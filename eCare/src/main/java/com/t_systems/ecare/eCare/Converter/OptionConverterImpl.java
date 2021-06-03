/*
package com.t_systems.ecare.eCare.Converter;

import com.t_systems.ecare.eCare.DTO.OptionDTO;
import com.t_systems.ecare.eCare.DTO.TariffDTO;
import com.t_systems.ecare.eCare.entity.Option;
import com.t_systems.ecare.eCare.entity.Tariff;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
@Repository
@NoArgsConstructor
public class OptionConverterImpl implements OptionConverter{
    @Autowired
    TariffConverter tariffConverter;
    @Override
    public Option converterOptionDTOToOPtion(OptionDTO optionDTO) {
        Option option=new Option();
        option.setConnectionCost(optionDTO.getOptionConnectionCost());
        option.setId(optionDTO.getOptionId());
        option.setName(optionDTO.getOptionName());
        option.setPrice(optionDTO.getOptionPrice());
        option.setNumberGroup(optionDTO.getOptionGroupNumber());
        List<TariffDTO>tariffDTOList=optionDTO.getListTariff();
        option.setTariffsList(tariffDTOList.stream().
                map(s->tariffConverter.converterTariffDTOToTareff(s)).collect(Collectors.toList()));
        return option;
    }

    @Override
    public OptionDTO converterOptionToOptionDTO(Option option) {
        OptionDTO optionDTO=new OptionDTO();
        optionDTO.setOptionId(option.getId());
        optionDTO.setOptionName(option.getName());
        optionDTO.setOptionPrice(option.getPrice());
        optionDTO.setOptionConnectionCost(optionDTO.getOptionConnectionCost());
        optionDTO.setOptionGroupNumber(optionDTO.getOptionGroupNumber());
        List<Tariff> tariffList=option.getTariffsList();
        optionDTO.setListTariff(tariffList.stream().
                map(s->tariffConverter.converterTarifToTariffDTO(s)).collect(Collectors.toList()));
        return optionDTO;
    }
}
*/
