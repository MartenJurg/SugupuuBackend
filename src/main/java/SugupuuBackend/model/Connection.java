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
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private Long firstPersonId;
    private Long secondPersonId;

    public Connection(@NotBlank Long firstPersonId, Long secondPersonId) {
        this.firstPersonId = firstPersonId;
        this.secondPersonId = secondPersonId;
    }
}
