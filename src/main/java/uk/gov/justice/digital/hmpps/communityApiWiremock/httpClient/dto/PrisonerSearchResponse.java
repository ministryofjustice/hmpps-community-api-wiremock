package uk.gov.justice.digital.hmpps.communityApiWiremock.httpClient.dto;

import java.util.List;
import lombok.Data;

@Data
public class PrisonerSearchResponse {

  private List<PrisonerDetailsResponse> content;
}
