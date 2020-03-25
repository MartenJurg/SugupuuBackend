package SugupuuBackend.controller;

import SugupuuBackend.exceptions.*;
import SugupuuBackend.model.PartnerConnection;
import SugupuuBackend.model.Person;
import SugupuuBackend.model.PersonToChildConnection;
import SugupuuBackend.pojo.PersonDto;
import SugupuuBackend.pojo.validators.ChildValidator;
import SugupuuBackend.pojo.validators.IValidator;
import SugupuuBackend.pojo.validators.ParentValidator;
import SugupuuBackend.pojo.validators.PartnerValidator;
import SugupuuBackend.service.PartnerConnectionService;
import SugupuuBackend.service.PersonService;
import SugupuuBackend.service.PersonToChildConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    private IValidator childValidator = new ChildValidator();
    private IValidator parentValidator = new ParentValidator();
    private IValidator partnerValidator = new PartnerValidator();

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
            partnerValidator.validate(partnerDto, id);

            // Create new person and add it to the repository
            Person partner = new Person(partnerDto);
            personService.savePerson(partner);

            // Create partner connection
            partnerConnectionService.save(new PartnerConnection(partner.getId(), id, true, partner.getFamilyTreeId()));

            return new ResponseEntity<>(partner, HttpStatus.OK);
        } catch (PersonNotFoundException | PartnerAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public List<Person> getAll() {
        return personService.getAll();
    }

}
