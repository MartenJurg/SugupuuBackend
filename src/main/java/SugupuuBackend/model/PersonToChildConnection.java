package SugupuuBackend.model;

import SugupuuBackend.pojo.PersonToChildConnectionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PersonToChildConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull private Long personId;
    @NotNull private Long childId;
    @NotNull private Long familyTreeId;

    public PersonToChildConnection(PersonToChildConnectionDto personToChildConnectionDto) {
        this.personId = personToChildConnectionDto.getPersonId();
        this.childId = personToChildConnectionDto.getChildId();
        this.familyTreeId = personToChildConnectionDto.getFamilyTreeId();
    }

    public PersonToChildConnection(@NotNull Long personId, @NotNull Long childId, @NotNull Long familyTreeId) {
        this.personId = personId;
        this.childId = childId;
        this.familyTreeId = familyTreeId;
    }
}
