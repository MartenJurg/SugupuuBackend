package SugupuuBackend.model;

import SugupuuBackend.pojo.PersonDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotNull private int age;
    @NotBlank private String gender;
    @NotNull private Long familyTreeId;


    public Person(PersonDto personDto) {
        this.firstName = personDto.getFirstName();
        this.lastName = personDto.getLastName();
        this.age = personDto.getAge();
        this.gender = personDto.getGender();
        this.familyTreeId = personDto.getFamilyTreeId();
    }

    public Person(@NotBlank String firstName, @NotBlank String lastName, @NotNull int age, @NotBlank String gender, @NotNull Long familyTreeId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.familyTreeId = familyTreeId;
    }
}
