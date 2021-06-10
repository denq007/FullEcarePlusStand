package services;

import com.t_systems.ecare.eCare.DAO.*;
import com.t_systems.ecare.eCare.DTO.ContractDTO;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
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

    @BeforeEach
    void before() {
        //enable mocks
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void update() {
        //when(mock).thenReturn(value)
        ContractDTO dto = new ContractDTO();
        dto.setId(1);
        Contract contract = mock(Contract.class);
        when(tariffDAO.findOne(anyInt()))
                .thenReturn(tariff);
        when(contractDAO.findOne(anyInt()))
                .thenReturn(contract);
        when(optionService.checkCompatibilityOptions(anySet(), anyList(), anySet())).thenReturn(Optional.empty());
        Set<String> optionsNames = new HashSet<>();
        optionsNames.add("Internet");
        when(optionService.deleteOptionsAvailableTariffAnDADDNameOption(anySet(), anyList())).thenReturn(optionsNames);
        Customer customer = mock(Customer.class);
        when(contract.getCustomerId()).thenReturn(customer);
     //   ArgumentCaptor<Contract> contractArgumentCaptor = ArgumentCaptor.forClass(Contract.class);

     //   verify(contractDAO).update(contractArgumentCaptor.capture());
    //    Contract ubdateContract = contractArgumentCaptor.getValue();

        assertEquals(optionsNames, dto.getAddNameOptions());
  //      assertEquals(ubdateContract.getCustomerId(),customer);
    }

    @Test
    void getDto() {
    }

    @Test
    void showTariffandOptions() {
    }

    @Test
    void create() {
    }

    @Test
    void showAllContracts() {
    }

    @Test
    void isContractBlocked() {
    }

    @Test
    void updateByCart() {
    }

}