package uk.gov.justice.digital.hmpps.communityApiWiremock.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ProbationSearchRequest {
    List<String> teamCodes;
    String query;
    ProbationSearchSortByRequest sortBy;
}