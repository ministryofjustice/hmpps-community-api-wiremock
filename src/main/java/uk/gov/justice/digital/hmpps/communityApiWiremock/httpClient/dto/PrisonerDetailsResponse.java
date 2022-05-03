package uk.gov.justice.digital.hmpps.communityApiWiremock.httpClient.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class PrisonerDetailsResponse {

  private String prisonerNumber;
  private String firstName;
  private String lastName;
  private LocalDate conditionalReleaseDate;
  private LocalDate topupSupervisionExpiryDate;
  private LocalDate licenceExpiryDate;
  private LocalDate sentenceExpiryDate;
  private LocalDate paroleEligibilityDate;
  private LocalDate homeDetentionCurfewEndDate;
  private LocalDate releaseDate;
  private String status;
  private Boolean indeterminateSentence;
  private String legalStatus;
  private String prisonId;
}
