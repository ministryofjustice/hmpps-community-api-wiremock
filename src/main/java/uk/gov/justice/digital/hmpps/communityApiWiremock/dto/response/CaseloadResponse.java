package uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CaseloadResponse {

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate allocationDate = LocalDate.now().minusDays(1);

  String offenderCrn;
  StaffResponse staff;
  Long staffIdentifier;
  TeamResponse team;
}
