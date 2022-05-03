package uk.gov.justice.digital.hmpps.communityApiWiremock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CommunityApiWiremockApplication {

  public static void main(String[] args) {
    SpringApplication.run(CommunityApiWiremockApplication.class, args);
  }
}
