package uk.gov.justice.digital.hmpps.communityApiWiremock.httpClient;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import uk.gov.justice.digital.hmpps.communityApiWiremock.httpClient.dto.PrisonerDetailsResponse;
import uk.gov.justice.digital.hmpps.communityApiWiremock.httpClient.dto.PrisonerSearchResponse;
import uk.gov.justice.digital.hmpps.communityApiWiremock.httpClient.dto.SearchByListOfNomisIdRequest;

@Component
public class PrisonerSearchApiClient {

  private final RestClient restClient;
  @Value("${prisonerSearchApi.host}")
  private String prisonerSearchApiHost;

  public PrisonerSearchApiClient(RestClient restClient) {
    this.restClient = restClient;
  }

  public List<PrisonerDetailsResponse> getPrisoners(List<String> nomisIds) {
    if (nomisIds.isEmpty()) {
      return Collections.emptyList();
    }
    return this.restClient.post(
        prisonerSearchApiHost + "/prisoner-search/prisoner-numbers",
        SearchByListOfNomisIdRequest.builder().prisonerNumbers(nomisIds).build(),
        new ParameterizedTypeReference<>() {
        });
  }

  public PrisonerSearchResponse getPrisonersByPrison(String prisonCode) {
    if (prisonCode == null || prisonCode.isEmpty()) {
      return null;
    }
    return this.restClient.get(
        prisonerSearchApiHost + "/prisoner-search/prison/" + prisonCode + "?size=10000",
        PrisonerSearchResponse.class);
  }
}
