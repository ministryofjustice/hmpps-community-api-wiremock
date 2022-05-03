package uk.gov.justice.digital.hmpps.communityApiWiremock.dao.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.OffenderEntity;

@Repository
public interface OffenderRepository extends JpaRepository<OffenderEntity, Long> {
  Optional<OffenderEntity> findByNomsNumber(String nomisId);
  Optional<OffenderEntity> findByCrnNumber(String crn);
  List<OffenderEntity> findByCrnNumberIn(List<String> crns);
  List<OffenderEntity> findByNomsNumberIn(List<String> nomsNumbers);
}
