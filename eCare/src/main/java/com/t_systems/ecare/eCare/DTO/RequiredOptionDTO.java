package com.t_systems.ecare.eCare.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class RequiredOptionDTO {
    private int id;
    @NotBlank
    @Size(min = 3, max = 75)
    String name;
    int groupId;
    Map<Integer,List<RequiredOptionDTO>> allRequiredOption;
}
