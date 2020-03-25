package SugupuuBackend.controller;

import SugupuuBackend.model.FamilyTree;
import SugupuuBackend.pojo.FamilyTreeDto;
import SugupuuBackend.service.FamilyTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/familytree")
public class FamilyTreeController {

    @Autowired
    FamilyTreeService familyTreeService;

    @GetMapping("/getAll")
    public List<FamilyTree> getAll() {
        return familyTreeService.getAllFamilyTrees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FamilyTree> getFamilyTree(@PathVariable Long id) {
        Optional<FamilyTree> familyTree = familyTreeService.getFamilyTreeById(id);

        if (familyTree.isPresent()) return new ResponseEntity<>(familyTree.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public void addFamilyTree(@RequestBody FamilyTreeDto familyTreeDto) {
        familyTreeService.saveFamilyTree(new FamilyTree(familyTreeDto));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deleteFamilyTree(@PathVariable Long id) {
        Optional<FamilyTree> familyTree = familyTreeService.getFamilyTreeById(id);
        if (familyTree.isPresent()) {
            familyTreeService.deleteFamilyTree(familyTree.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
