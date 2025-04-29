package uk.gov.justice.digital.hmpps.communityApiWiremock.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.StaffEntity;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.TeamEntity;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.repository.OffenderRepository;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.repository.StaffRepository;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.repository.TeamRepository;
import uk.gov.justice.digital.hmpps.communityApiWiremock.exception.NotFoundException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class DeliusService {

  private final StaffRepository staffRepository;
  private final TeamRepository teamRepository;
  private final OffenderRepository offenderRepository;

  public DeliusService(
      StaffRepository staffRepository,
      TeamRepository teamRepository,
      OffenderRepository offenderRepository
  ) {
    this.staffRepository = staffRepository;
    this.teamRepository = teamRepository;
    this.offenderRepository = offenderRepository;
  }

  public Optional<StaffEntity> getStaffByUsername(String staffUsername) {
    return this.staffRepository.findByUsername(staffUsername);
  }

  public Optional<StaffEntity> getStaffByCode(String staffCode) {
    return this.staffRepository.findByStaffCode(staffCode);
  }

  public Optional<StaffEntity> getStaff(Long staffId) {
    return this.staffRepository.findByStaffIdentifier(staffId);
  }

  public List<StaffEntity> getStaff(List<String> staffUsernames) {
    return this.staffRepository.findByUsernameIn(staffUsernames);
  }

  public List<StaffEntity> getPduHeads(String pduCode) {
    return this.teamRepository.findByBoroughCode(pduCode).map(TeamEntity::getStaff).orElse(Collections.emptyList());
  }

  public List<OffenderEntity> getAllOffendersByStaffId(Long staffId) {
    StaffEntity staff = this.staffRepository.findByStaffIdentifier(staffId).orElseThrow(() ->
            new NotFoundException(String.format("Staff with id %s not found", staffId)));
    return staff.getManagedOffenders();
  }

  public List<OffenderEntity> getAllOffendersByTeamCode(String teamCode) {
    TeamEntity team = this.teamRepository.getByTeamCode(teamCode);
    return team.getManagedOffenders();
  }

  public List<OffenderEntity> findOffendersByCrnOrNomsNumberIn(List<String> crnsOrNomsNumbers) {
    return this.offenderRepository.findByCrnNumberInOrNomsNumberIn(crnsOrNomsNumbers, crnsOrNomsNumbers);
  }

  public List<OffenderEntity> getTeamManagedOffendersByStaffId(Long staffId, String query, Pageable page) {
    String searchString = query.toLowerCase().trim();

    if (searchString.isEmpty())
      return List.of();

    Comparator<OffenderEntity> sortPreference = page.getSort().stream().findFirst()
            .filter(sort -> sort.getProperty().equals("firstName"))
            .map(sort -> {
              if (sort.getDirection().isAscending()) {
                return Comparator.comparing(OffenderEntity::getForename);
              } else {
                return Comparator.comparing(OffenderEntity::getForename, Comparator.reverseOrder());
              }
            }).orElse(Comparator.comparing(OffenderEntity::getSurname));

    return getStaff(staffId)
            .map(s -> s.getTeams().stream()
                    .map(TeamEntity::getTeamCode)
                    .flatMap(teamCode -> getAllOffendersByTeamCode(teamCode).stream())
                    .filter(offender -> matchesOffender(offender, searchString))
                    .sorted(sortPreference)
                    .toList()
            ).orElse(Collections.emptyList());
  }

  private Boolean matchesOffender(OffenderEntity offender, String searchString) {
    if (offender.getStaff() != null) {
      return (offender.getCrnNumber().toLowerCase().contains(searchString)) ||
              (offender.getNomsNumber().toLowerCase().contains(searchString)) ||
              (offender.getForename().toLowerCase().contains(searchString)) ||
              (offender.getSurname().toLowerCase().contains(searchString)) ||
              (offender.getStaff().getStaffForenames().toLowerCase().contains(searchString)) ||
              (offender.getStaff().getStaffSurname().toLowerCase().contains(searchString));
    } else {
      return (offender.getCrnNumber().toLowerCase().contains(searchString)) ||
              (offender.getNomsNumber().toLowerCase().contains(searchString)) ||
              (offender.getForename().toLowerCase().contains(searchString)) ||
              (offender.getSurname().toLowerCase().contains(searchString));
    }
  }
}
