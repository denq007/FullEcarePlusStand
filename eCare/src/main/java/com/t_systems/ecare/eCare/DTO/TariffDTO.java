package com.t_systems.ecare.eCare.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class TariffDTO {
    private int id;
    @NotBlank
    @Size(min = 3, max = 255)
    private String name;
    @DecimalMin(value = "0.0")
    @DecimalMax("1000.0")
    private double price;
    boolean isOld;
    private Set<Integer> tariffOption;
    private Map<String, Integer> allOptions = new HashMap<>();
    private List<String> optionName=new ArrayList<>();
    private List<String> requiredOption;
    

}
