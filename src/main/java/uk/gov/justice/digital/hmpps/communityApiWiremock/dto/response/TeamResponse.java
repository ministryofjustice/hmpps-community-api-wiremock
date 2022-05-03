package uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class TeamResponse {

  String code;
  String description;
  AreaResponse borough;
  AreaResponse district;
  String telephone;
}
