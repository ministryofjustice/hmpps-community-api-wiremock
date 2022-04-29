package uk.gov.justice.digital.hmpps.communityApiWiremock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Resource not found")
public class NotFoundException extends RuntimeException {

  public NotFoundException() {
    super();
  }

  public NotFoundException(String message) {
    super(message);
  }
}
