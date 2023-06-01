package uk.gov.justice.digital.hmpps.communityApiWiremock;


import org.junit.jupiter.api.Test;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.OffenderEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class TestIt {

    @Test
    public void test_logic() {
        String searchString = "555";
        List<String> teamCodes = new ArrayList<>();

        teamCodes.add("ABC123");

        if (searchString.isEmpty())
            System.out.println(List.of());


        List<OffenderEntity> result =  teamCodes.stream()
                .flatMap(teamCode -> getAllOffendersByTeamCode(teamCode).stream())
                .filter(offender -> matchesOffender(offender, searchString.toLowerCase().trim()))
                .sorted(Comparator.comparing(OffenderEntity::getForename))
                .toList();

        System.out.println(result);
    }

    private Boolean matchesOffender(OffenderEntity offender, String searchString) {
        return (offender.getCrnNumber().toLowerCase().contains(searchString)) ||
                (offender.getNomsNumber().toLowerCase().contains(searchString)) ||
                (offender.getForename().toLowerCase().contains(searchString)) ||
                (offender.getSurname().toLowerCase().contains(searchString))
//                ||
//                (offender.getStaff().getStaffForenames().toLowerCase().contains(searchString)) ||
//                (offender.getStaff().getStaffSurname().toLowerCase().contains(searchString))
                ;
    }

    private List<OffenderEntity> getAllOffendersByTeamCode(String teamcode) {
        List<OffenderEntity> offenders = new ArrayList<>();
        OffenderEntity offenderEntity = new OffenderEntity();
        offenderEntity.setNomsNumber("111");
        offenderEntity.setCrnNumber("222");
        offenderEntity.setCroNumber("333");
        offenderEntity.setPncNumber("444");
        offenderEntity.setForename("Test2");
        offenderEntity.setSurname("Surname2");
        OffenderEntity offenderEntity2 = new OffenderEntity();
        offenderEntity2.setNomsNumber("555");
        offenderEntity2.setCrnNumber("666");
        offenderEntity2.setCroNumber("777");
        offenderEntity2.setPncNumber("888");
        offenderEntity2.setForename("Test1");
        offenderEntity2.setSurname("Surname1");
        offenders.add(offenderEntity);
        offenders.add(offenderEntity2);
        return offenders;
    }
}
