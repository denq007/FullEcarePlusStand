package services;

import com.t_systems.ecare.eCare.DAO.OptionDAO;
import com.t_systems.ecare.eCare.DAO.TariffDAO;
import com.t_systems.ecare.eCare.DTO.TariffDTO;
import com.t_systems.ecare.eCare.entity.Option;
import com.t_systems.ecare.eCare.entity.Tariff;
import com.t_systems.ecare.eCare.services.HotTariffService;
import com.t_systems.ecare.eCare.services.RequiredOptionServiceImpl;
import com.t_systems.ecare.eCare.services.TariffServiceImpl;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class TariffServiceImplTest {
    @InjectMocks
    TariffServiceImpl tariffService;
    @Mock
    TariffDAO tariffDAO;
    @Mock
    ModelMapper modelMapper;
    @Mock
    OptionDAO optionDAO;
    @Mock
    RequiredOptionServiceImpl requiredOptionService;
    @Mock
    HotTariffService hotTariffService;
    @Mock
    Tariff tariff;
    @Mock
    TariffDTO tariffDTO;
    private List<Option> optionList=new ArrayList<>();

    @BeforeEach
    void before() {
        MockitoAnnotations.initMocks(this);
        tariff.setName("Vasya");

    }

    @Test
    void showAllOptions() {
    }

    @Test
    void showAllRequiredOptions() {
    }

    @Test
    void findTariffByName() {
    }

    @Test
    void deleteTariff() {
        when(tariffDAO.findByName("Vasya")).thenReturn(tariff);
        when(tariffDAO.isUsed(tariff.getId())).thenReturn(true);
        assertEquals(tariffService.deleteTariff("Vasya"),Optional.of("tariff is used in some contracts"));
    }

    @Test
    void updatePositive() {
        when(tariffDAO.findOne(tariffDTO.getId())).thenReturn(tariff);
        assertEquals(tariffService.update(tariffDTO),Optional.empty());

    }
   /* @Test
    void updateNegative()
    {
        when(tariffDAO.findOne(tariffDTO.getId())).thenReturn(tariff);
        when(tariffService.checkOptionsCompatibility(anyList())).thenReturn(false);
        assertEquals(tariffService.update(tariffDTO),Optional.of("Incompatible options are selected"));
    }*/

    @Test
    void convertToEntity() {
        when(modelMapper.map(tariffDTO, Tariff.class)).thenReturn(tariff);
        assertEquals(tariffService.convertToEntity(tariffDTO),tariff);
    }
    @Test
    void saveTariff() {
        when(tariffDAO.findByName(tariffDTO.getName())).thenReturn(null);
        when(tariffService.convertToEntity(tariffDTO)).thenReturn(tariff);
        assertEquals(tariffService.saveTariff(tariffDTO),Optional.empty());
    }

 /*   @Test
    void saveTariffNegativeIncompatibleOptions() {
        when(tariffDAO.findByName(tariffDTO.getName())).thenReturn(null);
        when(tariffService.convertToEntity(tariffDTO)).thenReturn(tariff);
        when(tariffDTO.getTariffOption()).thenReturn(anySet());
        when(tariffService.checkOptionsCompatibility(anyList())).thenReturn(false);
        tariffService.saveTariff(tariffDTO);
        assertEquals(tariffService.saveTariff(tariffDTO),Optional.of("Incompatible options are selected"));
    }*/
    @Test
    void saveTariffNegative() {
        when(tariffDAO.findByName(tariffDTO.getName())).thenReturn(tariff);
        assertEquals(tariffService.saveTariff(tariffDTO),Optional.of("This tariff's name is already exist"));
    }

}