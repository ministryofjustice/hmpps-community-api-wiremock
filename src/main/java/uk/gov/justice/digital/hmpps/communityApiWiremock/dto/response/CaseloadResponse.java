package uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CaseloadResponse {
  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate allocationDate = LocalDate.now().minusDays(1);
  Name name;
  String crn;
  String nomisId;
  StaffDetailResponse staff;
  TeamResponse team;
}
