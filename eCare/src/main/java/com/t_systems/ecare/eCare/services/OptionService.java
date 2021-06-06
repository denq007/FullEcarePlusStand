package com.t_systems.ecare.eCare.services;

import com.t_systems.ecare.eCare.DTO.OptionDTO;
import com.t_systems.ecare.eCare.entity.Option;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OptionService {
    Optional<String> saveOption(OptionDTO optionDTO);
    public OptionDTO convertToDto(Option option);
    public Option convertToEntity(OptionDTO optionDTO);
    public Optional<String> checkCompatibilityOptions(Set<Integer> idOptionforAdd, List<Option> tariffOptionList, Set<Option> contractOptions);
    List<OptionDTO> getAllOptions();
    public Set<String> deleteOptionsAvailableTariffAnDADDNameOption(Set<Integer> idOptionforAdd,List<Option> tariffOptionList);

    public OptionDTO findById(int id);

    Optional<String> update(OptionDTO dto);
    Set<String> getOptionsNameById(Set<Integer> id);

}
