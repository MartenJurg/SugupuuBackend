package SugupuuBackend.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PersonDto implements Serializable {

    @NotBlank
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private Long familyTreeId;

    public PersonDto(@NotBlank String firstName, String lastName, int age, String gender, Long familyTreeId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.familyTreeId = familyTreeId;
    }
}
