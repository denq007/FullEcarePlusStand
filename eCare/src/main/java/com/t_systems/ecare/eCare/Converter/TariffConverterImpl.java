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
public class TariffConverterImpl implements TariffConverter {
    @Autowired
    OptionConverter optionConverter;
    @Override
    public Tariff converterTariffDTOToTareff(TariffDTO tariffDTO) {
        Tariff tariff=new Tariff();
        tariff.setId(tariffDTO.getTariffID());
        tariff.setName(tariffDTO.getTariffName());
        tariff.setOld(tariffDTO.isTariffCheckOld());
        tariff.setPrice(tariffDTO.getTariffPrice());
        List<OptionDTO> optionDTOList=tariffDTO.getTariffOption();
        tariff.setOptionIdList(optionDTOList.stream().
                map(s->optionConverter.converterOptionDTOToOPtion(s)).collect(Collectors.toList()));
        return tariff;
    }

    @Override
    public TariffDTO converterTarifToTariffDTO(Tariff tariff) {
        TariffDTO tariffDTO=new TariffDTO();
        tariffDTO.setTariffID(tariff.getId());
        tariffDTO.setTariffName(tariff.getName());
        tariffDTO.setTariffPrice(tariff.getPrice());
        List<Option>optionList=tariff.getOptionIdList();
        tariffDTO.setTariffOption(optionList.stream().
                map(s->optionConverter.converterOptionToOptionDTO(s)).collect(Collectors.toList()));
        tariffDTO.setTariffCheckOld(tariff.isOld());
       return tariffDTO;
    }
}
*/
