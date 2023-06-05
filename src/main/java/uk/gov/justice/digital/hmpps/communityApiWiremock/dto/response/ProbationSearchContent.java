package uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ProbationSearchContent {
    NameResponse name;
    IdentifiersResponse identifiers;
    ManagerResponse manager;
    String allocationDate = LocalDate.now().minusDays(1).toString();
}
