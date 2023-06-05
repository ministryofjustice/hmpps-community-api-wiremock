package uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProbationSearchResponse {
    List<ProbationSearchContent> content;
}
