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
public class PartnerConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private Long person1Id;
    private Long person2Id;
    private Boolean currentPartner;
    private Long familyTree;

    public PartnerConnection(@NotBlank Long person1Id, Long person2Id, Boolean currentPartner, Long familyTree) {
        this.person1Id = person1Id;
        this.person2Id = person2Id;
        this.currentPartner = currentPartner;
        this.familyTree = familyTree;
    }
}
