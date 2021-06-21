package com.t_systems.ecare.eCare.services;

import com.t_systems.ecare.eCare.DTO.RequiredOptionDTO;
import com.t_systems.ecare.eCare.entity.RequiredOption;

import java.util.List;
import java.util.Map;

public interface RequiredOptionService{
    public RequiredOptionDTO convertToDto(RequiredOption requiredOption);
    public Map<Integer, List<RequiredOptionDTO>> findAllRequiredOption();
}
