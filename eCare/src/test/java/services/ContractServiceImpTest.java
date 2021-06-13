package services;

import com.t_systems.ecare.eCare.DAO.*;
import com.t_systems.ecare.eCare.DTO.ContractDTO;
import com.t_systems.ecare.eCare.basket.BasketImpl;
import com.t_systems.ecare.eCare.entity.Contract;
import com.t_systems.ecare.eCare.entity.Customer;
import com.t_systems.ecare.eCare.entity.Option;
import com.t_systems.ecare.eCare.entity.Tariff;
import com.t_systems.ecare.eCare.services.ContractServiceImp;
import com.t_systems.ecare.eCare.services.CustomerService;
import com.t_systems.ecare.eCare.services.OptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.CollectionUtils;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
@RunWith(MockitoJUnitRunner.class)
class ContractServiceImpTest {
    @Mock
    ContractDAOImpl contractDAO;
    @Mock
    ModelMapper modelMapper;
    @Mock
    OptionDAO optionDAO;
    @Mock
    TariffDAO tariffDAO;
    @Mock
    CustomerService customerService;
    @Mock
    CustomerDAOImpl customerDAO;
    @Mock
    PhoneNumberDAO phoneNumberDAO;
    @Mock
    OptionService optionService;
    @InjectMocks
    ContractServiceImp contractServiceImp;
    @Mock
    Tariff tariff;
    @Mock
    Contract contract;
    @Mock
    Customer customer;
    @Mock
    ContractDTO contractDTO;

    @BeforeEach
    void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void update() {
        ContractDTO dto = new ContractDTO();
        dto.setId(1);
        Set<Integer> idOptionsForAdd = new HashSet<>();
        idOptionsForAdd.add(1);
        dto.setOptionsIds(idOptionsForAdd);
        dto.setTariffName("First");
        List<Option> optionList = new ArrayList<>();
        Option newOption = new Option();
        newOption.setName("First");
        newOption.setId(1);
        optionList.add(newOption);
        Contract newContract = mock(Contract.class);
        when(tariffDAO.findOne(anyInt())).thenReturn(tariff);
        when(contractDAO.findOne(anyInt())).thenReturn(contract);
        when(optionService.checkCompatibilityOptions(anySet(), anyList(), anySet())).thenReturn(Optional.empty());
        Set<String> optionsNames = new HashSet<>();
        optionsNames.add("Internet");
        when(optionService.deleteOptionsAvailableTariffAnDADDNameOption(idOptionsForAdd, optionList)).thenReturn(optionsNames);
        Customer customer = mock(Customer.class);
        when(contract.getCustomerId()).thenReturn(customer);
        when(contractServiceImp.convertToEntity(dto)).thenReturn(newContract);
        Optional<String> e = contractServiceImp.update(dto);
        assertFalse(e.isPresent());
    }

    @Test
    void updateOptionСompatibilityError() {

        assertEquals(optionService.checkCompatibilityOptions(anySet(), anyList(), anySet()).isPresent(), false);

    }

    @Test
    void create() {
        when(tariffDAO.findOne(anyInt())).thenReturn(tariff);
        when(optionService.checkCompatibilityOptions(anySet(), anyList(), anySet())).thenReturn(Optional.empty());
        Optional<String> e = contractServiceImp.create(contractDTO);
        assertFalse(e.isPresent());
    }

    @Test
    void createOptionСompatibilityError() {
        when(tariffDAO.findOne(anyInt())).thenReturn(tariff);
        when(optionService.checkCompatibilityOptions(anySet(), anyList(), anySet())).thenReturn(Optional.of("Error"));
        Optional<String> e = contractServiceImp.create(contractDTO);
        assertTrue(e.isPresent());
    }

    @Test
    void updateByCart() {
        BasketImpl basket = mock(BasketImpl.class);
        when(tariffDAO.findOne(basket.getTariffId())).thenReturn(tariff);
        when(contractDAO.findOne(basket.getId())).thenReturn(contract);
        when(contractServiceImp.convertToDto(contract)).thenReturn(contractDTO);
        assertEquals(basket.getNumber(),contractDTO.getNumber());
    }

}