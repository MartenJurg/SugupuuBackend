package SugupuuBackend.service;

import SugupuuBackend.model.PersonToChildConnection;
import SugupuuBackend.repository.PersonToChildConnectionRepository;
import com.sun.xml.bind.v2.TODO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class PersonToChildConnectionService {

    @Resource
    PersonToChildConnectionRepository personToChildConnectionRepository;

    public Optional<PersonToChildConnection> getConnectionById(Long id) {
        return personToChildConnectionRepository.findById(id);
    }

    public List<PersonToChildConnection> getConnectionsByPersonId(Long id) {
        return personToChildConnectionRepository.findByPersonId(id);
    }

    public List<PersonToChildConnection> getConnectionsByChildId(Long id) {
        return personToChildConnectionRepository.findByChildId(id);
    }

    public void deleteConnectionById(Long id) {
        Optional<PersonToChildConnection> connection = getConnectionById(id);
        connection.ifPresent(value -> personToChildConnectionRepository.delete(value));
    }

    public void saveConnection(PersonToChildConnection personToChildConnection) {
        personToChildConnectionRepository.save(personToChildConnection);
    }
}
