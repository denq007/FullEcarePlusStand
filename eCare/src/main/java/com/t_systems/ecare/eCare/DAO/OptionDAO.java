package com.t_systems.ecare.eCare.DAO;

import com.t_systems.ecare.eCare.entity.Option;

import java.util.List;

public interface OptionDAO extends IGenericDAO<Option>{
    List<Object[]> getAllOptionNamesAndIds();

    Option findByName(String optionName);
}
