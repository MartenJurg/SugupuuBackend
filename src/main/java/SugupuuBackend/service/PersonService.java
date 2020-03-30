package SugupuuBackend.service;

import SugupuuBackend.model.Person;
import SugupuuBackend.model.PersonToChildConnection;
import SugupuuBackend.pojo.PersonDto;
import SugupuuBackend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonToChildConnectionService personToChildConnectionService;

    public void updatePerson(PersonDto personDto, Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            Person personObject = person.get();
            personObject.setFirstName(personDto.getFirstName());
            personObject.setLastName(personDto.getLastName());
            personObject.setAge(personDto.getAge());
            personObject.setGender(personDto.getGender());
            personRepository.save(personObject);
        }
    }

    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public void savePerson(Person person) {
        personRepository.save(person);
    }

    public List<Person> getAllByFamilyTreeId(Long id) {
        return personRepository.getAllByFamilyTreeId(id);
    }

    public List<Person> getParents(Long id, List<PersonToChildConnection> connections) {
        ArrayList<Person> parents = new ArrayList<>();
        for (PersonToChildConnection connection : connections) {
            if (getPersonById(connection.getPersonId()).isPresent()) {
                parents.add(getPersonById(connection.getPersonId()).get());
            }
        }
        return parents;
    }

    public List<Person> getChildren(Long id, List<PersonToChildConnection> connections) {
        ArrayList<Person> children = new ArrayList<>();
        for (PersonToChildConnection connection : connections) {
            if (getPersonById(connection.getChildId()).isPresent()) {
                children.add(getPersonById(connection.getChildId()).get());
            }
        }
        return children;
    }

}
