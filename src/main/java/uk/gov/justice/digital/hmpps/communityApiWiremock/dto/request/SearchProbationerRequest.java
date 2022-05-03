package uk.gov.justice.digital.hmpps.communityApiWiremock.dto.request;

import lombok.Data;

@Data
public class SearchProbationerRequest {

  String nomsNumber;
  String crn;
}
