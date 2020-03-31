package SugupuuBackend.controller;

import SugupuuBackend.exceptions.ApiRequestExcepiton;
import SugupuuBackend.model.Person;
import SugupuuBackend.model.PersonToChildConnection;
import SugupuuBackend.pojo.PersonDto;
import SugupuuBackend.pojo.PersonToChildConnectionDto;
import SugupuuBackend.pojo.validators.ParentValidator;
import SugupuuBackend.service.PartnerConnectionService;
import SugupuuBackend.service.PersonService;
import SugupuuBackend.service.PersonToChildConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/connection")
public class ConnectionController {

    @Autowired
    PersonService personService;

    @Autowired
    PersonToChildConnectionService personToChildConnectionService;

    @Autowired
    PartnerConnectionService partnerConnectionService;

    @Autowired
    ParentValidator parentValidator;

    @PostMapping("/addPersonToChild")
    public ResponseEntity<?> addPersonToChildConnection(@RequestBody PersonToChildConnectionDto personToChildConnectionDto) {
        Optional<Person> parent = personService.getPersonById(personToChildConnectionDto.getPersonId());
        if (parent.isEmpty()) throw new ApiRequestExcepiton("Person not found!");

        parentValidator.validate(new PersonDto(parent.get()), personToChildConnectionDto.getChildId());
        personToChildConnectionService.saveConnection(new PersonToChildConnection(personToChildConnectionDto));
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
