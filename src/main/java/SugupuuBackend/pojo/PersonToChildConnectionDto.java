package SugupuuBackend.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class PersonToChildConnectionDto {

    private Long personId;
    private Long childId;
    private Long familyTreeId;

    public PersonToChildConnectionDto(Long personId, Long childId, Long familyTreeId) {
        this.personId = personId;
        this.childId = childId;
        this.familyTreeId = familyTreeId;
    }
}
