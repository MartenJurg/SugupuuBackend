package SugupuuBackend.repository;

import SugupuuBackend.model.PartnerConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Repository
public interface PartnerConnectionRepository extends JpaRepository<PartnerConnection, Long> {

    List<PartnerConnection> findByPerson1Id(@NotBlank Long person1Id);
    List<PartnerConnection> findByPerson2Id(@NotBlank Long person1Id);

    List<PartnerConnection> findByPerson1IdAndCurrentPartner(@NotBlank Long person1Id, Boolean currentPartner);
    List<PartnerConnection> findByPerson2IdAndCurrentPartner(@NotBlank Long person1Id, Boolean currentPartner);

}
