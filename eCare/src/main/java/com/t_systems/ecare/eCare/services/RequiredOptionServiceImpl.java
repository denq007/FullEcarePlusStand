package com.t_systems.ecare.eCare.services;

import com.t_systems.ecare.eCare.DAO.RequiredOptionDAO;
import com.t_systems.ecare.eCare.DTO.RequiredOptionDTO;
import com.t_systems.ecare.eCare.entity.RequiredOption;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequiredOptionServiceImpl implements RequiredOptionService{
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    RequiredOptionDAO requiredOptionDAO;

    @Override
    public RequiredOptionDTO convertToDto(RequiredOption requiredOption) {
        RequiredOptionDTO requiredOptionDTO=modelMapper.map(requiredOption,RequiredOptionDTO.class);
        return requiredOptionDTO;
    }

    @Override
    public RequiredOption convertToEntity(RequiredOptionDTO requiredOptionDTO) {
        RequiredOption requiredOption=modelMapper.map(requiredOptionDTO,RequiredOption.class);
        return null;
    }

    public Map<Integer, List<RequiredOptionDTO>> findAllRequiredOption()
    {
        Map<Integer, List<RequiredOptionDTO>> mapOptions = new HashMap<>();
        List<RequiredOptionDTO> list=requiredOptionDAO.findAll().stream().map(s->convertToDto(s)).collect(Collectors.toList());
       for(int i=0;i<list.size();i++)
       {
           if(!mapOptions.containsKey(list.get(i).getGroupId())) {
               RequiredOptionDTO optionDTO = list.get(i);
               mapOptions.put(list.get(i).getGroupId(), new ArrayList<>());
               mapOptions.get(list.get(i).getGroupId()).add(optionDTO);
           }
           else
           {
               mapOptions.get(list.get(i).getGroupId()).add(list.get(i));
           }
       }
       return mapOptions;
    }
}
