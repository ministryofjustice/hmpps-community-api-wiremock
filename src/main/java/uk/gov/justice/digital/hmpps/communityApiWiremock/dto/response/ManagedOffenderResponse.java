package uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ManagedOffenderResponse {
  String nomsNumber;
  String crnNumber;
  Boolean currentOm = true;
}
