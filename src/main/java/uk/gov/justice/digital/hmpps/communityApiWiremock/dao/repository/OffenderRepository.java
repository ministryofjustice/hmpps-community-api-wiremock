package uk.gov.justice.digital.hmpps.communityApiWiremock.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.OffenderEntity;

import java.util.List;

@Repository
public interface OffenderRepository extends JpaRepository<OffenderEntity, Long> {
  List<OffenderEntity> findByCrnNumberInOrNomsNumberIn(List<String> crns, List<String> nomsNumbers);
}
