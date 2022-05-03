package uk.gov.justice.digital.hmpps.communityApiWiremock.httpClient.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchByListOfNomisIdRequest {

  List<String> prisonerNumbers;
}
