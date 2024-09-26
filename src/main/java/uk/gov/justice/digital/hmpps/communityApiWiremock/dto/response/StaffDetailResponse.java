package uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StaffDetailResponse {

  Long id;
  String code;
  Name name;
  List<TeamResponse> teams;
  String email;
  String telephoneNumber;
  String username;
  AreaResponse provider;
  Boolean unallocated;
}
