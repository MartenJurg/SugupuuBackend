package SugupuuBackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PartnerConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long person1Id;
    @NotNull private Long person2Id;
    @NotNull private Boolean currentPartner;
    @NotNull private Long familyTree;

    public PartnerConnection(@NotNull Long person1Id, @NotNull Long person2Id, @NotNull Boolean currentPartner, @NotNull Long familyTree) {
        this.person1Id = person1Id;
        this.person2Id = person2Id;
        this.currentPartner = currentPartner;
        this.familyTree = familyTree;
    }
}
