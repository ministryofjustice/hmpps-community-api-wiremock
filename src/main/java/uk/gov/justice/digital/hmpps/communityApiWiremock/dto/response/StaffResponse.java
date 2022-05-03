package uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StaffResponse {

  String code;
  String forenames;
  String surname;
  Boolean unallocated = false;
}
