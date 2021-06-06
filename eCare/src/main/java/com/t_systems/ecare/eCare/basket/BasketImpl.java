package com.t_systems.ecare.eCare.basket;

import com.t_systems.ecare.eCare.DAO.OptionDAO;
import com.t_systems.ecare.eCare.DAO.OptionDAOImp;
import com.t_systems.ecare.eCare.DTO.ContractDTO;
import com.t_systems.ecare.eCare.entity.Option;
import com.t_systems.ecare.eCare.services.TariffService;
import com.t_systems.ecare.eCare.services.TariffServiceImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;
import java.util.stream.Collectors;

@Component
@SessionScope
@NoArgsConstructor
@Getter
@Setter
public class BasketImpl implements Basket {
    private int id;
    private String number;
    private int tariffId;
    private int NewTariffId;
    private String tariffName;
    private String newTariffName;
    private Map<String, Integer> allTariffs = new HashMap<>();
    private Map<String, Integer> allOptions = new HashMap<>();
    private Set<Integer> optionsIds = new HashSet<>();
    private Set<String> addNameOptions = new HashSet<>();
    private String tariffDescription;
    @Autowired
    TariffService tariffService;
  /*  @Autowired
    OptionDAO optionDAO;*/
    @Override
    public BasketImpl createBasket(ContractDTO dto) {
        BasketImpl basket = new BasketImpl();
        basket.setId(dto.getId());
        basket.setTariffId(dto.getTariffId());
        basket.setNumber(dto.getNumber());
        basket.setTariffName(dto.getTariffName());
        basket.setAllTariffs(dto.getAllTariffs());
        basket.setAllOptions(dto.getAllOptions());
        basket.setAddNameOptions(dto.getAddNameOptions());
        basket.setOptionsIds(dto.getOptionsIds());
        tariffService.findTariffByName(basket.tariffName).getTariffOption();
        return basket;
    }

    @Override
    public Optional<String> update(BasketImpl currentBasket, BasketImpl basket) {
        if (basket.NewTariffId != 0)
            currentBasket.setTariffId(basket.NewTariffId);
       currentBasket.getOptionsIds().add(basket.getOptionsIds().stream().iterator().next());
       List<Integer> listId=new ArrayList<>();
       listId.addAll(currentBasket.getOptionsIds());
        TariffServiceImpl tariffService=new TariffServiceImpl();
        List<Option> list=new ArrayList<>()/*tariffService.getOptionsById(listId)*/;
        OptionDAOImp optionDAOImp=new OptionDAOImp();
        list.addAll(listId.stream().map(s->optionDAOImp.findOne(s)).collect(Collectors.toList()));
        boolean b=tariffService.checkOptionsCompatibility(list);
      if(b)
      return Optional.of("Incompatible options are selected");
      return Optional.empty();
    }

    @Override
    public Optional<String> checkOptions() {
        List<Option> list=new ArrayList<>();
        list= this.tariffService.getOptionsById(this.getOptionsIds().stream().collect(Collectors.toList()));
       boolean b= this.tariffService.checkOptionsCompatibility(list);
        if(!b)
            return Optional.of("Incompatible options are selected");
        return Optional.empty();
    }

    @Override
    public void clearAll() {
        this.setTariffId(0);
        this.setTariffName(null);
        this.setAddNameOptions(null);
    }
}
