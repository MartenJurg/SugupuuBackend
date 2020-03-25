package SugupuuBackend.model;

import SugupuuBackend.pojo.FamilyTreeDto;
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
public class FamilyTree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    public FamilyTree(FamilyTreeDto familyTreeDto) {
        this.name = familyTreeDto.getName();
    }

    public FamilyTree(@NotBlank String name) {
        this.name = name;
    }
}
