package SugupuuBackend.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class FamilyTreeDto {

    @NotBlank
    private String name;

    public FamilyTreeDto(@NotBlank String name) {
        this.name = name;
    }
}
