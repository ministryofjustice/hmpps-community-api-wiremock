package uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ResponsibleCommunityManager {
  @JsonProperty("case")
  ProbationCaseResponse probationCase;
  Long id;
  String code;
  String email;
  String telephoneNumber;
  AreaResponse provider;
  TeamResponse team;
  String allocationDate = LocalDate.now().minusDays(1).toString();
}
