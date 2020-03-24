package SugupuuBackend.controller;

import SugupuuBackend.model.Person;
import SugupuuBackend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping("/add")
    public ResponseEntity<?> addNewPerson(@RequestBody String username) {
        return ResponseEntity.ok("Works");
    }

    @GetMapping("/all")
    public List<Person> getAll() {
        return personService.getAll();
    }

}
