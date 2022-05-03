package uk.gov.justice.digital.hmpps.communityApiWiremock.dao.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.StaffEntity;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, Long> {
  Optional<StaffEntity> findByStaffIdentifier(Long staffIdentifier);
  Optional<StaffEntity> findByUsername(String staffUsername);
  Optional<StaffEntity> findByStaffCode(String staffCode);
  List<StaffEntity> findByUsernameIn(List<String> staffUsernames);
  List<StaffEntity> findByStaffCodeIn(List<String> staffCodes);
}
