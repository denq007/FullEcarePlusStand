package services;

import com.t_systems.ecare.eCare.entity.Tariff;
import com.t_systems.ecare.eCare.services.HotTariffService;
import com.t_systems.ecare.eCare.services.HotTariffServiceImpl;
import com.t_systems.ecare.eCare.services.MessageSender;
import com.t_systems.ecare.eCare.services.TariffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotTariffServiceImplTest {

    @Mock
    MessageSender messageSender;
    @Mock
    TariffService tariffService;
    @InjectMocks
    HotTariffServiceImpl hotTariffService;
    @BeforeEach
    void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void sendMessage() {
        Tariff t = new Tariff();
        t.setName("test");
        t.setPrice(5);
        when(tariffService.getLast(3)).thenReturn(Collections.singletonList(t));
        assertDoesNotThrow(() -> hotTariffService.sendMessage());
        verify(messageSender, times(1)).sendMessage("[{\"name\":\"test\",\"price\":5.0}]");
    }

}