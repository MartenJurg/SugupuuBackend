package SugupuuBackend.service;

import SugupuuBackend.model.FamilyTree;
import SugupuuBackend.repository.FamilyTreeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class FamilyTreeService {

    @Resource
    FamilyTreeRepository familyTreeRepository;

    public List<FamilyTree> getAllFamilyTrees() {
        return familyTreeRepository.findAll();
    }

    public Optional<FamilyTree> getFamilyTreeById(Long id) {
        return familyTreeRepository.findById(id);
    }

    public void deleteFamilyTreeById(Long id) {
        Optional<FamilyTree> connection = getFamilyTreeById(id);
        connection.ifPresent(value -> familyTreeRepository.delete(value));
    }

    public void deleteFamilyTree(FamilyTree familyTree) {
        familyTreeRepository.delete(familyTree);
    }

    public void saveFamilyTree(FamilyTree familyTree) {
        familyTreeRepository.save(familyTree);
    }
}
