package SugupuuBackend.service;

import SugupuuBackend.model.PartnerConnection;
import SugupuuBackend.repository.PartnerConnectionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartnerConnectionService {

    @Resource
    PartnerConnectionRepository partnerConnectionRepository;

    public List<PartnerConnection> getAllConnectionsById(Long id) {
        List<PartnerConnection> firstConnections = partnerConnectionRepository.findByPerson1Id(id);
        List<PartnerConnection> secondConnections = partnerConnectionRepository.findByPerson2Id(id);
        firstConnections.addAll(secondConnections);
        return firstConnections;
    }

    public List<PartnerConnection> getExPartnersConnections(Long id) {
        List<PartnerConnection> connections = getAllConnectionsById(id);
        if (connections.isEmpty()) return new ArrayList<>();
        return connections.stream().filter(connection -> connection.getCurrentPartner().equals(false)).collect(Collectors.toList());
    }

    public Optional<PartnerConnection> getCurrentPartnerConnection(Long id) {
        List<PartnerConnection> firstConnections = partnerConnectionRepository.findByPerson1IdAndCurrentPartner(id, true);
        List<PartnerConnection> secondConnections = partnerConnectionRepository.findByPerson2IdAndCurrentPartner(id, true);
        firstConnections.addAll(secondConnections);
        if (firstConnections.isEmpty()) return Optional.empty();
        return Optional.of(firstConnections.get(0));
    }

    public void save(PartnerConnection partnerConnection) {
        partnerConnectionRepository.save(partnerConnection);
    }

}
