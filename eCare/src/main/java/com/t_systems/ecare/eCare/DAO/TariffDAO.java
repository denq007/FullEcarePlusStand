package com.t_systems.ecare.eCare.DAO;

import com.t_systems.ecare.eCare.entity.Tariff;

import java.util.List;

public interface TariffDAO extends IGenericDAO<Tariff>{
    public List<Object[]> getAllTariffNamesAndIds();

    public Tariff findByName(String tariffName);

    boolean isUsed(int id);

}
