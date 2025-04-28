package uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProbationCaseResponse {
  String crn;
  String nomisId;
  String croNumber;
  String pncNumber;
}
