package uk.gov.justice.digital.hmpps.communityApiWiremock.config;

import javax.sql.DataSource;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.StaffEntity;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.TeamEntity;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.CaseloadResponse;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.CommunityOrPrisonOffenderManager;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.OffenderManagerResponse;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.ProbationerResponse;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.ProbationSearchResponse;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.StaffDetailResponse;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.TeamResponse;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dto.response.UserDetailResponse;

@Configuration
public class CommunityApiWiremockConfiguration {

  @Value("${db.driver.class.name}")
  private String dbDriverClassName;

  @Value("${db.url}")
  private String dbUrl;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    dataSource.setDriverClassName(dbDriverClassName);
    dataSource.setUrl(dbUrl);

    return dataSource;
  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    modelMapper.createTypeMap(StaffEntity.class, StaffDetailResponse.class)
        .addMappings(mapper -> mapper.skip(StaffDetailResponse::setTeams))
        .addMappings(mapper -> mapper.<String>map(StaffEntity::getStaffForenames, (dest, v) -> dest.getStaff().setForenames(v)))
        .addMappings(mapper -> mapper.<String>map(StaffEntity::getStaffSurname, (dest, v) -> dest.getStaff().setSurname(v)))
        .addMappings(mapper -> mapper.<String>map(StaffEntity::getProbationAreaCode, (dest, v) -> dest.getProbationArea().setCode(v)))
        .addMappings(mapper -> mapper.<String>map(StaffEntity::getProbationAreaDescription, (dest, v) -> dest.getProbationArea().setDescription(v)));

    modelMapper.createTypeMap(OffenderEntity.class, CaseloadResponse.class)
        .addMappings(mapper -> mapper.map(OffenderEntity::getCrnNumber, CaseloadResponse::setOffenderCrn))
        .addMappings(mapper -> mapper.<String>map(src -> src.getStaff().getStaffForenames(), (dest, v) -> dest.getStaff().setForenames(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getStaff().getStaffSurname(), (dest, v) -> dest.getStaff().setSurname(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getStaff().getStaffCode(), (dest, v) -> dest.getStaff().setCode(v)))
        .addMappings(mapper -> mapper.<Long>map(src -> src.getStaff().getStaffIdentifier(), CaseloadResponse::setStaffIdentifier))
        .addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getTeamCode(), (dest, v) -> dest.getTeam().setCode(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getTeamDescription(), (dest, v) -> dest.getTeam().setDescription(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getBoroughCode(), (dest, v) -> dest.getTeam().getBorough().setCode(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getBoroughDescription(), (dest, v) -> dest.getTeam().getBorough().setDescription(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getDistrictCode(), (dest, v) -> dest.getTeam().getDistrict().setCode(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getDistrictDescription(), (dest, v) -> dest.getTeam().getDistrict().setDescription(v)));

    modelMapper.createTypeMap(OffenderEntity.class, CommunityOrPrisonOffenderManager.class)
        .addMappings(mapper -> mapper.map(src -> src.getStaff().getStaffIdentifier(), CommunityOrPrisonOffenderManager::setStaffId))
        .addMappings(mapper -> mapper.map(src -> src.getStaff().getStaffCode(), CommunityOrPrisonOffenderManager::setStaffCode))
        .addMappings(mapper -> mapper.<String>map(src -> src.getStaff().getProbationAreaCode(), (dest, v) -> dest.getProbationArea().setCode(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getStaff().getProbationAreaDescription(), (dest, v) -> dest.getProbationArea().setDescription(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getTeamCode(), (dest, v) -> dest.getTeam().setCode(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getTeamDescription(), (dest, v) -> dest.getTeam().setDescription(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getBoroughCode(), (dest, v) -> dest.getTeam().getBorough().setCode(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getBoroughDescription(), (dest, v) -> dest.getTeam().getBorough().setDescription(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getDistrictCode(), (dest, v) -> dest.getTeam().getDistrict().setCode(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getDistrictDescription(), (dest, v) -> dest.getTeam().getDistrict().setDescription(v)));

    modelMapper.createTypeMap(OffenderEntity.class, ProbationerResponse.class)
        .addMappings(mapper -> mapper.skip(ProbationerResponse::setOffenderManagers))
        .addMappings(mapper -> mapper.<String>map(OffenderEntity::getNomsNumber, (dest, v) -> dest.getOtherIds().setNomsNumber(v)))
        .addMappings(mapper -> mapper.<String>map(OffenderEntity::getCrnNumber, (dest, v) -> dest.getOtherIds().setCrn(v)))
        .addMappings(mapper -> mapper.<String>map(OffenderEntity::getCroNumber, (dest, v) -> dest.getOtherIds().setCroNumber(v)))
        .addMappings(mapper -> mapper.<String>map(OffenderEntity::getPncNumber, (dest, v) -> dest.getOtherIds().setPncNumber(v)));

    modelMapper.createTypeMap(StaffEntity.class, OffenderManagerResponse.class)
        .addMappings(mapper -> mapper.skip(OffenderManagerResponse::setTeam))
        .addMappings(mapper -> mapper.<String>map(StaffEntity::getStaffCode, (dest, v) -> dest.getStaff().setCode(v)))
        .addMappings(mapper -> mapper.<String>map(StaffEntity::getStaffForenames, (dest, v) -> dest.getStaff().setForenames(v)))
        .addMappings(mapper -> mapper.<String>map(StaffEntity::getStaffSurname, (dest, v) -> dest.getStaff().setSurname(v)))
        .addMappings(mapper -> mapper.<String>map(StaffEntity::getProbationAreaCode, (dest, v) -> dest.getProbationArea().setCode(v)))
        .addMappings(mapper -> mapper.<String>map(StaffEntity::getProbationAreaDescription, (dest, v) -> dest.getProbationArea().setDescription(v)));

    modelMapper.createTypeMap(TeamEntity.class, TeamResponse.class)
        .addMappings(mapper -> mapper.<String>map(TeamEntity::getBoroughCode, (dest, v) -> dest.getBorough().setCode(v)))
        .addMappings(mapper -> mapper.<String>map(TeamEntity::getBoroughDescription, (dest, v) -> dest.getBorough().setDescription(v)))
        .addMappings(mapper -> mapper.<String>map(TeamEntity::getDistrictCode, (dest, v) -> dest.getDistrict().setCode(v)))
        .addMappings(mapper -> mapper.<String>map(TeamEntity::getDistrictDescription, (dest, v) -> dest.getDistrict().setDescription(v)))
        .addMappings(mapper -> mapper.map(TeamEntity::getTeamCode, TeamResponse::setCode))
        .addMappings(mapper -> mapper.map(TeamEntity::getTeamDescription, TeamResponse::setDescription));

    modelMapper.createTypeMap(StaffEntity.class, UserDetailResponse.class)
        .addMappings(mapper -> mapper.map(StaffEntity::getEmail, UserDetailResponse::setEmail))
        .addMappings(mapper -> mapper.map(StaffEntity::getStaffForenames, UserDetailResponse::setFirstName))
        .addMappings(mapper -> mapper.map(StaffEntity::getStaffSurname, UserDetailResponse::setSurname))
        .addMappings(mapper -> mapper.map(StaffEntity::getUsername, UserDetailResponse::setUsername));

    modelMapper.createTypeMap(OffenderEntity.class, ProbationSearchResponse.class)
        .addMappings(mapper -> mapper.<String>map(OffenderEntity::getForename, (dest, v) -> dest.getName().setForename(v)))
        .addMappings(mapper -> mapper.<String>map(OffenderEntity::getSurname, (dest, v) -> dest.getName().setSurname(v)))
        .addMappings(mapper -> mapper.<String>map(OffenderEntity::getCrnNumber, (dest, v) -> dest.getIdentifiers().setCrn(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getStaff().getStaffCode(), (dest, v) -> dest.getManager().setCode(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getStaff().getStaffForenames(), (dest, v) -> dest.getManager().getName().setForename(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getStaff().getStaffSurname(), (dest, v) -> dest.getManager().getName().setSurname(v)))
        .addMappings(mapper -> mapper.<String>map(src -> src.getTeam().getTeamCode(), (dest, v) -> dest.getManager().getTeam().setCode(v)));

    return modelMapper;
  }
}
