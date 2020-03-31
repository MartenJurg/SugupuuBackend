package SugupuuBackend.controller;

import SugupuuBackend.exceptions.*;
import SugupuuBackend.model.PartnerConnection;
import SugupuuBackend.model.Person;
import SugupuuBackend.model.PersonToChildConnection;
import SugupuuBackend.pojo.PersonDto;
import SugupuuBackend.pojo.PredecessorsHelper;
import SugupuuBackend.pojo.validators.*;
import SugupuuBackend.service.PartnerConnectionService;
import SugupuuBackend.service.PersonService;
import SugupuuBackend.service.PersonToChildConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    @Autowired PersonDtoValidator personDtoValidator;

    @Autowired
    private UpdatePersonValidator updatePersonValidator;

    @Autowired
    private PredecessorsHelper predecessorsHelper;

    @PostMapping("/addChild/{id}")
    public ResponseEntity<?> addChildToPerson(@RequestBody PersonDto childDto, @PathVariable Long id) {
        // Try to validate
        personDtoValidator.validate(childDto);
        childValidator.validate(childDto, id);

        // Create new person and add it to the repository
        Person child = new Person(childDto);
        personService.savePerson(child);

        // Create connection between two people
        personToChildConnectionService.saveConnection(new PersonToChildConnection(id, child.getId(), child.getFamilyTreeId()));
        return new ResponseEntity<>(child, HttpStatus.OK);
    }

    @PostMapping("/addParent/{id}")
    public ResponseEntity<?> addParentToPerson(@RequestBody PersonDto parentDto, @PathVariable Long id) {
        // Try to validate
        personDtoValidator.validate(parentDto);
        parentValidator.validate(parentDto, id);

        // Create new person and add it to the repository
        Person parent = new Person(parentDto);
        personService.savePerson(parent);

        // If person already has 1 parent then add partner connection aswell
        boolean makeCurrent;
        if (personService.getParents(id).size() == 1) {
            if (personService.getPartner(id).size() == 1) {
                // Create new connection
                makeCurrent = false;
            } else {
                makeCurrent = true;
            }
            partnerConnectionService.save(
                    new PartnerConnection(
                            personService.getParents(id).get(0).getId(),
                            parent.getId(),
                            makeCurrent,
                            parent.getFamilyTreeId()));
        }

        // Create connection between two people
        personToChildConnectionService.saveConnection(new PersonToChildConnection(parent.getId(), id, parent.getFamilyTreeId()));
        return new ResponseEntity<>(parent, HttpStatus.OK);

    }

    @PostMapping("/addPartner/{id}")
    public ResponseEntity<?> addPartnerToPerson(@RequestBody PersonDto partnerDto, @PathVariable Long id) {


        // Try to validate
        personDtoValidator.validate(partnerDto);
        partnerValidator.validate(partnerDto, id);

        // Create new person and add it to the repository
        Person partner = new Person(partnerDto);
        personService.savePerson(partner);
        PartnerConnection partnerConnection = new PartnerConnection(partner.getId(), id, true, partner.getFamilyTreeId());

        // Create partner connection
        partnerConnectionService.save(partnerConnection);
        return new ResponseEntity<>(partner, HttpStatus.OK);
    }

    @PostMapping("/addExPartner/{id}")
    public ResponseEntity<?> addExPartnerToPerson(@RequestBody PersonDto partnerDto, @PathVariable Long id) {
        personDtoValidator.validate(partnerDto);
        Person partner = new Person(partnerDto);
        personService.savePerson(partner);
        PartnerConnection partnerConnection = new PartnerConnection(partner.getId(), id, false, partner.getFamilyTreeId());

        // Create partner connection
        partnerConnectionService.save(partnerConnection);
        return new ResponseEntity<>(partner, HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updatePerson(@RequestBody PersonDto personDto, @PathVariable Long id) {
        personDtoValidator.validate(personDto);
        updatePersonValidator.validate(personDto, id);
        personService.updatePerson(personDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Person> getAll() {
        return personService.getAll();
    }

    @GetMapping("/search/{pattern}/{id}")
    public List<Person> getSearchResults(@PathVariable String pattern, @PathVariable Long id) {
        return personService.getSearchResult(pattern, id);
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
        return personService.getParents(id);
    }

    @GetMapping("/getChildren/{id}")
    public List<Person> getChildren(@PathVariable Long id) {
        return personService.getChildren(id);
    }

    @GetMapping("/getPartner/{id}")
    public List<Person> getPartner(@PathVariable Long id) {
        return personService.getPartner(id);
    }

    @GetMapping("/getExPartners/{id}")
    public List<Person> getExPartner(@PathVariable Long id) {
        return personService.getExPartners(id);
    }

    @GetMapping("/getAllPartners/{id}")
    public List<Person> getAllPartners(@PathVariable Long id) {
        return personService.getAllPartners(id);
    }

    @GetMapping("/getAllSiblingsOf/{id}")
    public List<Person> getAllSiblings(@PathVariable Long id) {
        // Sorted by age
        return personService.getAllSiblings(id).stream()
                .sorted(Comparator.comparing(Person::getAge))
                .collect(Collectors.toList());
    }

    @GetMapping("/getHalfSiblingsOf/{id}")
    public List<Person> getHalfSiblings(@PathVariable Long id) {
        // Sorted by age
        return personService.getHalfSiblings(id).stream()
                .sorted(Comparator.comparing(Person::getAge))
                .collect(Collectors.toList());
    }

    @GetMapping("/getRealSiblingsOf/{id}")
    public List<Person> getRealSiblings(@PathVariable Long id) {
        // Sorted by age
        return personService.getRealSiblings(id).stream()
                .sorted(Comparator.comparing(Person::getAge))
                .collect(Collectors.toList());
    }

    @GetMapping("/getPersonWithMostPredecessors")
    public Person getPersonWithMostPredecessors() {
        return predecessorsHelper.getPersonWithMostPredecessors();
    }

    @GetMapping("/getPersonsPredecessors/{id}")
    public List<Person> getPersonsPredecessors(@PathVariable Long id) {
        return predecessorsHelper.getPredecessorsOf(id).stream()
                .sorted(Comparator.comparing(Person::getAge))
                .collect(Collectors.toList());
    }

    @GetMapping("/getYoungestUncleOrAunt/{id}")
    public Optional<Person> getYoungestUncleOrAunt(@PathVariable Long id) {
        Optional<Person> person = personService.getPersonById(id);
        if (person.isPresent()) {
            List<Person> parents = personService.getParents(person.get().getId());
            List<Person> unclesAndAunts = new ArrayList<>();
            parents.forEach(parent -> unclesAndAunts.addAll(personService.getRealSiblings(parent.getId())));
            if (unclesAndAunts.isEmpty()) return Optional.empty();
            return Optional.of(unclesAndAunts.stream()
                    .sorted(Comparator.comparing(Person::getAge))
                    .collect(Collectors.toList()).get(0));
        }
        return Optional.empty();
    }
}



























