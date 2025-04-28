package uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageMetadata {
	long page = 1;
	long size = 10;
	long totalElements = 10;
	long totalPages = 1;
}
