package com.t_systems.ecare.eCare.DTO;

import com.t_systems.ecare.eCare.entity.Contract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {
    private int id;
    private UserDTO user;
    @NotBlank
    @Size(min =2, message ="length must be greater than 2")
    private String name;
    @NotBlank
    @Size(min =2,message ="length must be greater than 2")
    private String surname;
    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    @NotBlank
    @Size(min =10)
    private String passportDetails;
    private String address;
    private List<Contract> contractIdList;
    private String email;
}
