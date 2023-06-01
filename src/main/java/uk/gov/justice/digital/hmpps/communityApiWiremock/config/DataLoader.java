package uk.gov.justice.digital.hmpps.communityApiWiremock.config;

import com.github.javafaker.Faker;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.StaffEntity;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.TeamEntity;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.repository.OffenderRepository;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.repository.StaffRepository;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.repository.TeamRepository;
import uk.gov.justice.digital.hmpps.communityApiWiremock.exception.NotFoundException;
import uk.gov.justice.digital.hmpps.communityApiWiremock.httpClient.PrisonerSearchApiClient;
import uk.gov.justice.digital.hmpps.communityApiWiremock.httpClient.dto.PrisonerDetailsResponse;

@Component
@Slf4j
public class DataLoader implements ApplicationRunner {

  private final OffenderRepository offenderRepository;
  private final StaffRepository staffRepository;
  private final TeamRepository teamRepository;
  private final PrisonerSearchApiClient prisonerSearchApiClient;

  @Value("classpath:caseloads.csv")
  Resource caseloads;

  @Autowired
  public DataLoader(
      OffenderRepository offenderRepository,
      StaffRepository staffRepository,
      TeamRepository teamRepository,
      PrisonerSearchApiClient prisonerSearchApiClient
  ) {
    this.offenderRepository = offenderRepository;
    this.staffRepository = staffRepository;
    this.teamRepository = teamRepository;
    this.prisonerSearchApiClient = prisonerSearchApiClient;
  }

  @Transactional
  public void run(ApplicationArguments args) throws IOException {
    List<CsvOffenderCase> cases = new CsvToBeanBuilder<CsvOffenderCase>(
        new InputStreamReader(caseloads.getInputStream()))
        .withType(CsvOffenderCase.class)
        .build()
        .parse();

    addCases(cases);
  }

  private void addCases(List<CsvOffenderCase> cases) {
    List<OffenderEntity> offenders = new ArrayList<>();

    Map<String, Map<String, List<CsvOffenderCase>>> casesGroupedByUsernameAndTeam =
        cases.stream().collect(Collectors.groupingBy(CsvOffenderCase::getUsername,
            Collectors.groupingBy(CsvOffenderCase::getTeamCode)));

    casesGroupedByUsernameAndTeam.forEach((username, groupedByTeam) -> {
      groupedByTeam.forEach((teamCode, caseload) -> {
        List<String> nomisIds = caseload.stream().map(c -> c.nomisId).collect(Collectors.toList());
        List<PrisonerDetailsResponse> prisonerList = prisonerSearchApiClient.getPrisoners(nomisIds);

        StaffEntity staff = this.staffRepository.findByUsername(username).orElse(null);
        TeamEntity team = this.teamRepository.findByTeamCode(teamCode)
            .orElseThrow(NotFoundException::new);

        log.info(String.format("Loading cases for %s in team %s",
            staff != null ? staff.getUsername() : "Unallocated", teamCode));

        for (PrisonerDetailsResponse prisoner : prisonerList) {
          OffenderEntity offenderEntity = new OffenderEntity();
          Faker faker = new Faker(new Random(prisoner.getPrisonerNumber().hashCode()));

          offenderEntity.setNomsNumber(prisoner.getPrisonerNumber());
          offenderEntity.setCrnNumber(faker.regexify("[A-Z][0-9]{6}"));
          offenderEntity.setCroNumber(faker.regexify("[0-9]{1}/[0-9]{5}"));
          offenderEntity.setPncNumber(faker.regexify("[0-9]{4}/[0-9]{5}"));
          offenderEntity.setForename(faker.name().firstName());
          offenderEntity.setForename(faker.name().lastName());
          offenderEntity.setStaff(staff);
          offenderEntity.setTeam(team);

          offenders.add(offenderEntity);
        }
      });
    });

    offenderRepository.saveAllAndFlush(offenders);
  }
}
