package SugupuuBackend.pojo;

import SugupuuBackend.model.Person;
import SugupuuBackend.model.PersonToChildConnection;
import SugupuuBackend.service.PartnerConnectionService;
import SugupuuBackend.service.PersonService;
import SugupuuBackend.service.PersonToChildConnectionService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@NoArgsConstructor
public class PredecessorsHelper {
    @Autowired
    PersonService personService;

    @Autowired
    PersonToChildConnectionService personToChildConnectionService;

    @Autowired
    PartnerConnectionService partnerConnectionService;

    public List<Person> getPredecessorsOf(Long id) {
        System.out.println("ADDING" );
        Optional<Person> person = personService.getPersonById(id);
        return person.map(this::predecessorsSearch).orElseGet(ArrayList::new);
    }

    private List<Person> predecessorsSearch(Person person) {
        List<Person> predecessors = new ArrayList<>();
        Stack<Person> queue = new Stack<>();
        personService.getParents(person.getId()).forEach(queue::push);
        Person processingPredecessor;

        while (!queue.isEmpty()) {
            processingPredecessor = queue.pop();
            predecessors.add(processingPredecessor);
            personService.getParents(processingPredecessor.getId()).forEach( parent -> {
                if (!predecessors.contains(parent)) {
                    queue.add(parent);
                }
            });
        }
        return predecessors;
    }

    public Person getPersonWithMostPredecessors() {
        List<Person> peopleWithNoChildren = getPeopleWithoutChildren();
        int highestCount = 0;
        Person personWithMostPredecessors = peopleWithNoChildren.get(0);
        for (Person person: peopleWithNoChildren) {
            int count = getPredecessorsCount(person);
            if (count > highestCount) {
                highestCount = count;
                personWithMostPredecessors = person;
            }
        }
        return personWithMostPredecessors;
    }

    private Integer getPredecessorsCount(Person person) {
        Stack<Person> queue = new Stack<>();
        ArrayList<Person> processedList = new ArrayList<>();
        Integer count = 0;
        Person processingPerson;
        queue.add(person);

        while (!queue.isEmpty()) {
            processingPerson = queue.pop();
            if (!processedList.contains(processingPerson)) {
                count++;
                personService.getParents(processingPerson.getId()).forEach( person1 -> {
                    if (!processedList.contains(person1)) queue.add(person1);
                });
                processedList.add(processingPerson);
            }
        }
        return count;
    }

    private List<Person> getPeopleWithoutChildren() {
        List<Person> people = new ArrayList<>();
        personService.getAll().forEach(person -> {
            if (personToChildConnectionService.getConnectionsByPersonId(person.getId()).isEmpty()) {
                people.add(person);
            }
        });
        return people;
    }
}
