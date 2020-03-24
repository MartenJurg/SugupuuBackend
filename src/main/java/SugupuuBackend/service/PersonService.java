package SugupuuBackend.service;

import SugupuuBackend.model.Person;
import SugupuuBackend.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Resource
    PersonRepository personRepository;

    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public List<Person> getAll() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("Marten", "Jurg", 20, "M"));
        return personRepository.findAll();
    }

    public void savePerson(Person person) {
        personRepository.save(person);
    }

}
