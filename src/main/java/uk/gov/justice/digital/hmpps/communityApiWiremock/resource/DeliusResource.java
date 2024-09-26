package uk.gov.justice.digital.hmpps.communityApiWiremock.resource;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.StaffEntity;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.request.ProbationSearchRequest;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.request.SearchProbationerRequest;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.CaseloadResponse;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.ResponsibleCommunityManager;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.ProbationerResponse;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.ProbationSearchContent;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.ProbationSearchResponse;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.StaffDetailResponse;
import uk.gov.justice.digital.hmpps.communityApiWiremock.exception.NotFoundException;
import uk.gov.justice.digital.hmpps.communityApiWiremock.mapper.Mapper;
import uk.gov.justice.digital.hmpps.communityApiWiremock.service.DeliusService;


@Transactional
@RestController
public class DeliusResource {

  private final DeliusService service;
  private final Mapper mapper;

  public DeliusResource(DeliusService service, Mapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @PutMapping(value = "/users/{username}/roles")
  public String assignRole(@PathVariable String username) {
    return String.format("CVL role assigned to %s", username);
  }

  @GetMapping(value = "/staff/{username}")
  public StaffDetailResponse getStaffDetail(@PathVariable String username)
      throws NotFoundException {
    username = username.toLowerCase();

    StaffEntity staff = this.service.getStaffByUsername(username).orElse(this.service.getStaff(2000L).get());

    if (staff.getStaffIdentifier() == 2000L) {
      staff.setUsername(username);
    }

    return mapper.fromEntityToStaffDetailResponse(staff);
  }

  @GetMapping(value = "/staff/byid/{staffId}")
  public StaffDetailResponse getStaffDetailByStaffIdentifier(@PathVariable Long staffId)
      throws NotFoundException {

    StaffEntity staff = this.service.getStaff(staffId)
        .orElseThrow(() -> new NotFoundException("Staff member not found"));

    return mapper.fromEntityToStaffDetailResponse(staff);
  }

  @GetMapping(value = "/staff/bycode/{staffCode}")
  public StaffDetailResponse getStaffDetailByStaffCode(@PathVariable String staffCode)
      throws NotFoundException {

    StaffEntity staff = this.service.getStaffByCode(staffCode)
        .orElseThrow(() -> new NotFoundException("Staff member not found"));

    return mapper.fromEntityToStaffDetailResponse(staff);
  }

  @GetMapping(value = "/staff/{pduCode}/pdu-head")
  public List<StaffDetailResponse> getPduHeads(@PathVariable String pduCode) {
    List<StaffEntity> staff = this.service.getPduHeads(pduCode);

    return staff.stream()
        .map(mapper::fromEntityToStaffDetailResponse)
        .collect(Collectors.toList());
  }

  @PostMapping(value = "/staff")
  public List<StaffDetailResponse> getStaffDetailByList(@RequestBody List<String> staffUsernames) {
    staffUsernames = staffUsernames.stream().filter(Objects::nonNull).map(String::toLowerCase)
        .collect(Collectors.toList());

    List<StaffEntity> staff = this.service.getStaff(staffUsernames);

    List<StaffDetailResponse> response = staff.stream().map(mapper::fromEntityToStaffDetailResponse)
        .collect(Collectors.toList());

    if (response.size() != staffUsernames.size()) {
      staffUsernames.stream()
          .filter(s -> !response.stream().map(StaffDetailResponse::getUsername)
              .collect(Collectors.toList()).contains(s))
          .forEach(s -> {
            StaffDetailResponse extraStaff = mapper.fromEntityToStaffDetailResponse(
                this.service.getStaff(2000L).get());
            extraStaff.setUsername(s);
            response.add(extraStaff);
          });
    }

    return response;
  }

  @GetMapping(value = "/staff/byid/{staffId}/caseload/managed-offenders")
  public List<CaseloadResponse> getStaffCaseload(@PathVariable long staffId) {
    return service.getAllOffendersByStaffId(staffId).stream()
        .map(mapper::fromEntityToCaseloadResponse)
        .collect(Collectors.toList());
  }

  @GetMapping(value = "/team/{teamCode}/caseload/managed-offenders")
  public List<CaseloadResponse> getTeamCaseload(@PathVariable String teamCode) {
    return service.getAllOffendersByTeamCode(teamCode).stream()
        .map(mapper::fromEntityToCaseloadResponse)
        .collect(Collectors.toList());
  }

  @GetMapping(value = "/probation-case/{crn}/responsible-community-manager")
  public ResponsibleCommunityManager getManagersForAnOffender(@PathVariable String crn) {
    return mapper.fromEntityToCommunityOrPrisonOffenderManager(service.getOffenderByCrn(crn));
  }

  @PostMapping(value = "/probation-case/responsible-community-manager")
  public List<ResponsibleCommunityManager> getManagersForAnOffender(@RequestBody List<String> crns) {
    return service.findOffendersByCrnIn(crns).stream()
            .map(mapper::fromEntityToCommunityOrPrisonOffenderManager)
            .collect(Collectors.toList());
  }

  @PostMapping(value = "/search")
  public List<ProbationerResponse> getProbationer(@RequestBody SearchProbationerRequest body) {
    OffenderEntity offender = body.getNomsNumber() != null ? service.getOffenderByNomsId(body.getNomsNumber()) : service.getOffenderByCrn(body.getCrn());
    if (offender == null) {
      return Collections.emptyList();
    }

    ProbationerResponse response = mapper.fromEntityToProbationerResponse(offender);

    return List.of(response);
  }

  @PostMapping(value = "/crns")
  public List<ProbationerResponse> getProbationersByCrns(@RequestBody List<String> crns) {
    return service.findOffendersByCrnIn(crns).stream()
        .map(mapper::fromEntityToProbationerResponse)
        .collect(Collectors.toList());
  }

  @PostMapping(value = "/nomsNumbers")
  public List<ProbationerResponse> getProbationersByNomsNumbers(@RequestBody List<String> nomsNumbers) {
    return service.findOffendersByNomsNumberIn(nomsNumbers).stream()
        .map(mapper::fromEntityToProbationerResponse)
        .collect(Collectors.toList());
  }

  @PostMapping(value = "/licence-caseload/by-team")
  public ProbationSearchResponse getProbationSearchResult(@RequestBody ProbationSearchRequest body) {
    List<ProbationSearchContent> content = service
            .getProbationSearchResult(body.getTeamCodes(), body.getQuery(), body.getSortBy().get(0))
            .stream()
            .map(mapper::fromEntityToProbationSearchContent)
            .toList();
    ProbationSearchResponse response = new ProbationSearchResponse();
    response.setContent(content);
    return response;
  }

  @GetMapping(value = "/health/ping")
  public void ping() {
  }
}
