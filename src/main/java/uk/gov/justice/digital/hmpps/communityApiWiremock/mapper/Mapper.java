package uk.gov.justice.digital.hmpps.communityApiWiremock.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.StaffEntity;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.CaseloadResponse;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.ProbationCaseResponse;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.ResponsibleCommunityManager;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.StaffDetailResponse;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.TeamResponse;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

  private final ModelMapper modelMapper;

  public Mapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public StaffDetailResponse fromEntityToStaffDetailResponse(StaffEntity staffEntity) {
    StaffDetailResponse result = modelMapper.map(staffEntity, StaffDetailResponse.class);

    List<TeamResponse> teams = staffEntity.getTeams().stream()
        .map(team -> modelMapper.map(team, TeamResponse.class))
        .collect(Collectors.toList());

    result.setTeams(teams);

    return result;
  }

  public CaseloadResponse fromEntityToCaseloadResponse(OffenderEntity offenderEntity) {
    return modelMapper.map(offenderEntity, CaseloadResponse.class);
  }

  public ResponsibleCommunityManager fromEntityToCommunityOrPrisonOffenderManager(OffenderEntity offenderEntity) {
    return modelMapper.map(offenderEntity, ResponsibleCommunityManager.class);
  }

  public ProbationCaseResponse fromEntityToProbationCaseResponse(OffenderEntity offenderEntity) {
    return modelMapper.map(offenderEntity, ProbationCaseResponse.class);
  }
}
