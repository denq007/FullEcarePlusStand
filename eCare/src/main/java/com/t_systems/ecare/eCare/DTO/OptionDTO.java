package com.t_systems.ecare.eCare.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class OptionDTO {
    private int optionId;
    @NotBlank
    @Size(min = 3, max = 75)
    private String optionName;
    @DecimalMin(value = "0.00")
    private double optionPrice;
    @DecimalMin(value = "0.00")
    private double optionConnectionCost;
    @NotNull
    private int optionGroupNumber;
    private List<TariffDTO> listTariff;
}
