package SugupuuBackend.controller;

import SugupuuBackend.exceptions.*;
import SugupuuBackend.model.PartnerConnection;
import SugupuuBackend.model.Person;
import SugupuuBackend.model.PersonToChildConnection;
import SugupuuBackend.pojo.PersonDto;
import SugupuuBackend.pojo.validators.*;
import SugupuuBackend.service.PartnerConnectionService;
import SugupuuBackend.service.PersonService;
import SugupuuBackend.service.PersonToChildConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    PersonToChildConnectionService personToChildConnectionService;

    @Autowired
    PartnerConnectionService partnerConnectionService;

    @Autowired
    private ChildValidator childValidator;

    @Autowired
    private ParentValidator parentValidator;

    @Autowired
    private PartnerValidator partnerValidator;

    @Autowired
    private UpdatePersonValidator updatePersonValidator;

    @PostMapping("/addChild/{id}")
    public ResponseEntity<?> addChildToPerson(@RequestBody PersonDto childDto, @PathVariable Long id) {
        try {
            // Try to validate
            childValidator.validate(childDto, id);

            // Create new person and add it to the repository
            Person child = new Person(childDto);
            personService.savePerson(child);

            // Create connection between two people
            personToChildConnectionService.saveConnection(new PersonToChildConnection(id, child.getId(), child.getFamilyTreeId()));

            return new ResponseEntity<>(child, HttpStatus.OK);
        } catch (PersonNotFoundException | ChildTooOldException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addParent/{id}")
    public ResponseEntity<?> addParentToPerson(@RequestBody PersonDto parentDto, @PathVariable Long id) {
        try {
            // Try to validate
            parentValidator.validate(parentDto, id);

            // Create new person and add it to the repository
            Person parent = new Person(parentDto);
            personService.savePerson(parent);

            // Create connection between two people
            personToChildConnectionService.saveConnection(new PersonToChildConnection(parent.getId(), id, parent.getFamilyTreeId()));

            return new ResponseEntity<>(parent, HttpStatus.OK);
        } catch (ParentTooYoungException | PersonNotFoundException | TooManyParentsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addPartner/{id}")
    public ResponseEntity<?> addPartnerToPerson(@RequestBody PersonDto partnerDto, @PathVariable Long id) {
        try {
            // Try to validate
            System.out.println(partnerDto);
            System.out.println(id);
            partnerValidator.validate(partnerDto, id);

            // Create new person and add it to the repository
            Person partner = new Person(partnerDto);
            personService.savePerson(partner);

            PartnerConnection partnerConnection = new PartnerConnection(partner.getId(), id, true, partner.getFamilyTreeId());

            // Create partner connection
            partnerConnectionService.save(partnerConnection);

            return new ResponseEntity<>(partner, HttpStatus.OK);
        } catch (PersonNotFoundException | PartnerAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addExPartner/{id}")
    public ResponseEntity<?> addExPartnerToPerson(@RequestBody PersonDto partnerDto, @PathVariable Long id) {
        Person partner = new Person(partnerDto);
        personService.savePerson(partner);
        PartnerConnection partnerConnection = new PartnerConnection(partner.getId(), id, false, partner.getFamilyTreeId());

        // Create partner connection
        partnerConnectionService.save(partnerConnection);
        return new ResponseEntity<>(partner, HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updatePerson(@RequestBody PersonDto personDto, @PathVariable Long id) {
        try {
            updatePersonValidator.validate(personDto, id);
            personService.updatePerson(personDto, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidAgeInsertedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public List<Person> getAll() {
        return personService.getAll();
    }

    @GetMapping("/get/{id}")
    public Optional<Person> getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @GetMapping("/getAllByTree/{id}")
    public List<Person> getAllByTreeId(@PathVariable Long id) {
        return personService.getAllByFamilyTreeId(id);
    }

    @GetMapping("/getParents/{id}")
    public List<Person> getParents(@PathVariable Long id) {
        List<PersonToChildConnection> connections = personToChildConnectionService.getConnectionsByChildId(id);
        if (connections.isEmpty()) return new ArrayList<>();
        return personService.getParents(id, connections);
    }

    @GetMapping("/getChildren/{id}")
    public List<Person> getChildren(@PathVariable Long id) {
        List<PersonToChildConnection> connections = personToChildConnectionService.getConnectionsByPersonId(id);
        if (connections.isEmpty()) return new ArrayList<>();
        return personService.getChildren(id, connections);
    }

    @GetMapping("/getPartner/{id}")
    public List<Person> getPartner(@PathVariable Long id) {

        Optional<PartnerConnection> connection = partnerConnectionService.getCurrentPartnerConnection(id);
        if (connection.isEmpty()) return new ArrayList<>();
        Optional<Person> person;
        if (connection.get().getPerson1Id().equals(id)) {
            person =  personService.getPersonById(connection.get().getPerson2Id());
        } else {
            person = personService.getPersonById(connection.get().getPerson1Id());

        }
        if (person.isPresent()) {
            ArrayList<Person> people = new ArrayList<>();
            people.add(person.get());
            return people;
        }
        return new ArrayList<>();
    }

    @GetMapping("/getExPartners/{id}")
    public List<Person> getExPartner(@PathVariable Long id) {

        List<PartnerConnection> connections = partnerConnectionService.getExPartnersConnections(id);
        if (connections.isEmpty()) return new ArrayList<>();

        ArrayList<Person> partners = new ArrayList<>();
        for (PartnerConnection connection : connections) {

            if (connection.getPerson1Id().equals(id)) {
                if (personService.getPersonById(connection.getPerson2Id()).isPresent()) {
                    partners.add(personService.getPersonById(connection.getPerson2Id()).get());
                }
            } else {
                if (personService.getPersonById(connection.getPerson1Id()).isPresent()) {
                    partners.add(personService.getPersonById(connection.getPerson1Id()).get());
                }
            }
        }
        return partners;
    }

    @GetMapping("/getAllPartners/{id}")
    public List<Person> getAllPartner(@PathVariable Long id) {

        List<PartnerConnection> connections = partnerConnectionService.getAllConnectionsById(id);
        if (connections.isEmpty()) return new ArrayList<>();

        ArrayList<Person> partners = new ArrayList<>();
        for (PartnerConnection connection : connections) {

            if (connection.getPerson1Id().equals(id)) {
                if (personService.getPersonById(connection.getPerson2Id()).isPresent()) {
                    partners.add(personService.getPersonById(connection.getPerson2Id()).get());
                }
            } else {
                if (personService.getPersonById(connection.getPerson1Id()).isPresent()) {
                    partners.add(personService.getPersonById(connection.getPerson1Id()).get());
                }
            }
        }
        return partners;
    }
}
