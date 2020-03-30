package SugupuuBackend.controller;

import SugupuuBackend.model.PersonToChildConnection;
import SugupuuBackend.pojo.PersonToChildConnectionDto;
import SugupuuBackend.service.PartnerConnectionService;
import SugupuuBackend.service.PersonService;
import SugupuuBackend.service.PersonToChildConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addPersonToChild")
    public void addPersonToChildConnection(@RequestBody PersonToChildConnectionDto personToChildConnectionDto) {
        System.out.println(personToChildConnectionDto.getPersonId());
        System.out.println(personToChildConnectionDto.getChildId());
        System.out.println(personToChildConnectionDto.getFamilyTreeId());
        personToChildConnectionService.saveConnection(new PersonToChildConnection(personToChildConnectionDto));
    }

}
