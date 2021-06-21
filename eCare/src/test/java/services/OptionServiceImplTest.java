package services;

import com.t_systems.ecare.eCare.DAO.OptionDAO;
import com.t_systems.ecare.eCare.DTO.OptionDTO;
import com.t_systems.ecare.eCare.entity.Option;
import com.t_systems.ecare.eCare.entity.Tariff;
import com.t_systems.ecare.eCare.services.OptionServiceImpl;
import com.t_systems.ecare.eCare.services.TariffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class OptionServiceImplTest {
    @InjectMocks
    OptionServiceImpl optionService;
    @Mock
    OptionDAO optionDAO;
    @Mock
    ModelMapper modelMapper;
    @Mock
    TariffService tariffService;
    @Mock
    Option option;
    @Mock
    OptionDTO optionDTO;
    @Mock
    Tariff tariff;
    private Set<Integer> idOptionforAdd=new HashSet<>();
    private List<Option> tariffOptionList =new ArrayList<>();
    private Set<Option> contractOptions =new HashSet<>();

    @BeforeEach
    void before() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void saveOptionPositive() {
        when(optionDTO.getOptionName()).thenReturn("First");
        when(optionDAO.findByName("First")).thenReturn(null);
        when(optionService.convertToEntity(optionDTO)).thenReturn(option);
        assertEquals(optionService.saveOption(optionDTO),Optional.empty());
    }
    @Test
    void saveOptionNegative() {
        when(optionDTO.getOptionName()).thenReturn("First");
        when(optionDAO.findByName("First")).thenReturn(option);
        assertEquals(optionService.saveOption(optionDTO),Optional.of("This option's name is already registered"));
    }

/*    @Test
    void checkCompatibilityOptions() {
        //check if customer didn't choose any options
       *//* Optional<String> e=Optional.empty();
        when(idOptionforAdd.isEmpty()).thenReturn(new Optional<String> o);
        if (idOptionforAdd.isEmpty()) {
            return Optional.empty();
        }*//*
        List<Integer> onlyNewOption = new ArrayList<>();
        onlyNewOption.addAll(idOptionforAdd);
        Set<Option> list = new HashSet<>();
        for (int i = 0; i < onlyNewOption.size(); i++) {
            list.add(optionDAO.findOne(onlyNewOption.get(i)));
        }
        list.removeAll(contractOptions);
        if(list.size()==0)
        {
            return Optional.of("You already have all these options");
        }
        list.addAll(tariffOptionList);
        list.addAll(contractOptions);
        list.stream().filter(s -> s.getNumberGroup() != 0).collect(Collectors.toSet());
        Map<Integer, Set<Option>> map2 = list.stream()
                .collect(Collectors.groupingBy(Option::getNumberGroup, Collectors.toSet()));
        for (Integer a : map2.keySet()) {
            String str = "";
            Set<Option> set1 = map2.get(a);
            if (set1.size() > 1) {
                Iterator<Option> itr = map2.get(a).iterator();
                while (itr.hasNext()) {
                    str += itr.next().getName() + " and ";
                }
                return Optional.of("You are trying to add incompatible options - " + str.substring(0, str.length() - 4) + " ");
            }
        }

        return Optional.empty();
    }*/

  /*  @Test
    void deleteOptionsAvailableTariffAnDADDNameOption() {

    }*/

    @Test
    void findById() {
        when(optionDAO.findOne(1)).thenReturn(option);
        when(optionService.convertToDto(option)).thenReturn(optionDTO);
        assertEquals(optionService.findById(1),optionDTO);
    }

    @Test
    void update() {
        when(optionDTO.getOptionId()).thenReturn(1);
        when(optionDAO.findOne(1)).thenReturn(option);
        option.setTariffsList(new ArrayList<>());
     /*   when(option.getTariffsList().size()).thenReturn(3);*/
      /*  optionService.update(optionDTO);
       assertEquals(optionService.update(optionDTO),Optional.of("This option is used in the tariffs"));*/
        assertEquals(optionService.update(optionDTO),Optional.empty());
    }

}