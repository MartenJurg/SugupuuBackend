package SugupuuBackend.service;

import SugupuuBackend.model.Connection;
import SugupuuBackend.repository.ConnectionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class ConnectionService {

    @Resource
    ConnectionRepository connectionRepository;

    public Optional<Connection> getConnectionById(Long id) {
        return connectionRepository.findById(id);
    }

    public List<Connection> getConnectionsByFirstId(Long id) {
        return connectionRepository.findByFirstPersonId(id);
    }

    public List<Connection> getConnectionsBySecondId(Long id) {
        return connectionRepository.findByFirstPersonId(id);
    }

    public void deleteConnectionById(Long id) {
        Optional<Connection> connection = getConnectionById(id);
        connection.ifPresent(value -> connectionRepository.delete(value));
    }

    public void saveConnection(Connection connection) {
        connectionRepository.save(connection);
    }
}
