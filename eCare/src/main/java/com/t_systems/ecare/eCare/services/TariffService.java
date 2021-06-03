package com.t_systems.ecare.eCare.services;

import com.t_systems.ecare.eCare.DTO.TariffDTO;
import com.t_systems.ecare.eCare.entity.Option;
import com.t_systems.ecare.eCare.entity.Tariff;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TariffService {
    public Optional<String> saveTariff(TariffDTO tariffDTO);
    public Tariff convertToEntity(TariffDTO tariffDTO);
    public TariffDTO convertToDto(Tariff tariff);

    void showAllOptions(TariffDTO tariffDTO);

    public TariffDTO findTariffByName(String name);

    public Optional<String> deleteTariff(String name);

    public List<TariffDTO> showAllTariffs();

    public Optional<String> update(TariffDTO tariffDTO);

    public List<String> showAllRequiredOptions(Set<Integer> tariffOption);

    public boolean checkOptionsCompatibility(List<Option> optionList);

    public List<TariffDTO> showAllTariffsForEmployee();

    List<Tariff> getLast(int i);

    TariffDTO findById(int id);
}
