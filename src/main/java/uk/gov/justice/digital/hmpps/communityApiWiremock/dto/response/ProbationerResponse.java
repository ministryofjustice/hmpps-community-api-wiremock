package uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProbationerResponse {

  ProbationerIdsResponse otherIds;
  List<OffenderManagerResponse> offenderManagers;
}
