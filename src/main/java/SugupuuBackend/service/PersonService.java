package SugupuuBackend.service;

import SugupuuBackend.model.PartnerConnection;
import SugupuuBackend.model.Person;
import SugupuuBackend.model.PersonToChildConnection;
import SugupuuBackend.pojo.PersonDto;
import SugupuuBackend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonToChildConnectionService personToChildConnectionService;

    @Autowired
    PartnerConnectionService partnerConnectionService;

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

    public List<Person> getSearchResult(String search, Long id) {
        return personRepository.getAllByFirstNameContainingAndFamilyTreeId(search, id);
    }

    public void savePerson(Person person) {
        personRepository.save(person);
    }

    public List<Person> getAllByFamilyTreeId(Long id) {
        return personRepository.getAllByFamilyTreeId(id);
    }

    public List<Person> getParents(Long id) {
        List<PersonToChildConnection> connections = personToChildConnectionService.getConnectionsByChildId(id);
        if (connections.isEmpty()) return new ArrayList<>();

        ArrayList<Person> parents = new ArrayList<>();
        for (PersonToChildConnection connection : connections) {
            if (getPersonById(connection.getPersonId()).isPresent()) {
                parents.add(getPersonById(connection.getPersonId()).get());
            }
        }
        return parents;
    }

    public List<Person> getChildren(Long id) {
        List<PersonToChildConnection> connections = personToChildConnectionService.getConnectionsByPersonId(id);
        if (connections.isEmpty()) return new ArrayList<>();

        ArrayList<Person> children = new ArrayList<>();
        for (PersonToChildConnection connection : connections) {
            if (getPersonById(connection.getChildId()).isPresent()) {
                children.add(getPersonById(connection.getChildId()).get());
            }
        }
        return children;
    }

    public List<Person> getPartner(Long id) {
        Optional<PartnerConnection> connection = partnerConnectionService.getCurrentPartnerConnection(id);
        if (connection.isEmpty()) return new ArrayList<>();
        Optional<Person> person;
        if (connection.get().getPerson1Id().equals(id)) {
            person =  getPersonById(connection.get().getPerson2Id());
        } else {
            person = getPersonById(connection.get().getPerson1Id());

        }
        if (person.isPresent()) {
            ArrayList<Person> people = new ArrayList<>();
            people.add(person.get());
            return people;
        }
        return new ArrayList<>();
    }

    public List<Person> getAllPartners(Long id) {
        List<PartnerConnection> connections = partnerConnectionService.getAllConnectionsById(id);
        if (connections.isEmpty()) return new ArrayList<>();
        return getPartnersByConnection(id, connections);
    }

    public List<Person> getExPartners(Long id) {
        List<PartnerConnection> connections = partnerConnectionService.getExPartnersConnections(id);
        if (connections.isEmpty()) return new ArrayList<>();
        return getPartnersByConnection(id, connections);
    }

    private List<Person> getPartnersByConnection(Long id, List<PartnerConnection> connections) {
        ArrayList<Person> partners = new ArrayList<>();
        for (PartnerConnection connection : connections) {

            if (connection.getPerson1Id().equals(id)) {
                if (getPersonById(connection.getPerson2Id()).isPresent()) {
                    partners.add(getPersonById(connection.getPerson2Id()).get());
                }
            } else {
                if (getPersonById(connection.getPerson1Id()).isPresent()) {
                    partners.add(getPersonById(connection.getPerson1Id()).get());
                }
            }
        }
        return partners;
    }

    public List<Person> getAllSiblings(Long id) {
        List<Person> parents = getParents(id);
        ArrayList<Person> siblings = new ArrayList<>();
        parents.forEach(person -> siblings.addAll(getChildren(person.getId())));
        List<Person> allSiblings = new ArrayList<>();
        siblings.forEach( sibling -> {
            if (!sibling.getId().equals(id)) allSiblings.add(sibling);
        } );

        return allSiblings;
    }

    public List<Person> getHalfSiblings(Long id) {
        List<Person> parents = getParents(id);
        if (parents.size() == 2) {
            // Find all the children for each parent
            List<Person> allChildrenFromParent1 = getChildren(parents.get(0).getId());
            List<Person> allChildrenFromParent2 = getChildren(parents.get(1).getId());
            // Loop over and add half-siblings to list
            if (allChildrenFromParent1.isEmpty() || allChildrenFromParent2.isEmpty()) return new ArrayList<>();
            List<Person> halfSiblings = new ArrayList<>();
            allChildrenFromParent1.forEach(person -> {
                if (!allChildrenFromParent2.contains(person)) {
                    halfSiblings.add(person);
                }
            });
            allChildrenFromParent1.addAll(allChildrenFromParent2);
            return halfSiblings;

        }
        return new ArrayList<>();
    }

    public List<Person> getRealSiblings(Long id) {
        List<Person> parents = getParents(id);
        if (parents.size() == 2) {
            // Find all the children for each parent
            List<Person> allChildrenFromParent1 = getChildren(parents.get(0).getId());
            List<Person> allChildrenFromParent2 = getChildren(parents.get(1).getId());
            // Loop over and add real siblings to new list
            List<Person> realSiblings = new ArrayList<>();
            allChildrenFromParent1.forEach( sibling -> {
                if (allChildrenFromParent2.contains(sibling) && !sibling.getId().equals(id)) {
                    realSiblings.add(sibling);
                }
            });
            return realSiblings;

        } else if (parents.size() == 1) {
            List<Person> siblings = getChildren(parents.get(0).getId());
            if (getPersonById(id).isPresent()) siblings.remove(getPersonById(id).get());
            return siblings;
        }
        return new ArrayList<>();
    }
}
