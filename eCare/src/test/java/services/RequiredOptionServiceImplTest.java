package services;

import com.t_systems.ecare.eCare.DAO.RequiredOptionDAO;
import com.t_systems.ecare.eCare.DTO.RequiredOptionDTO;
import com.t_systems.ecare.eCare.entity.RequiredOption;
import com.t_systems.ecare.eCare.services.RequiredOptionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class RequiredOptionServiceImplTest {
    @InjectMocks
    RequiredOptionServiceImpl optionService;
    @Mock
    ModelMapper modelMapper;
    @Mock
    RequiredOptionDAO requiredOptionDAO;
    @Mock
    RequiredOption requiredOption;
    @Mock
    RequiredOptionDTO requiredOptionDTO;
    @Test
    void convertToDto() {
        when(modelMapper.map(requiredOption,RequiredOptionDTO.class)).thenReturn(requiredOptionDTO);
        assertEquals(optionService.convertToDto(requiredOption),requiredOptionDTO);
    }

}