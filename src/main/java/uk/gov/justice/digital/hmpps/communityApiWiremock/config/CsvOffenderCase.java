package uk.gov.justice.digital.hmpps.communityApiWiremock.config;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class CsvOffenderCase {
  @CsvBindByName
  String nomisId ;

  @CsvBindByName
  String teamCode;

  @CsvBindByName
  String username;
}
