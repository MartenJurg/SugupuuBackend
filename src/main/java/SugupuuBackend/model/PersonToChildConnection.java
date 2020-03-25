package SugupuuBackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
public class PersonToChildConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private Long personId;
    private Long childId;
    private Long familyTreeId;

    public PersonToChildConnection(@NotBlank Long personId, Long childId, Long familyTreeId) {
        this.personId = personId;
        this.childId = childId;
        this.familyTreeId = familyTreeId;
    }
}
