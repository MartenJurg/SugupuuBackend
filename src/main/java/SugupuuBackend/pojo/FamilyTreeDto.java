package SugupuuBackend.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class FamilyTreeDto {

    private String name;

    public FamilyTreeDto(String name) {
        this.name = name;
    }
}
