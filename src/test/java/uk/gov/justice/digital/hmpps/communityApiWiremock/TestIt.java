package uk.gov.justice.digital.hmpps.communityApiWiremock;


import org.junit.jupiter.api.Test;
import uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity.OffenderEntity;

import java.util.ArrayList;
import java.util.List;

public class TestIt {

    @Test
    public void test_logic() {
        List<String> teamCodes = new ArrayList<>();
        List<OffenderEntity> offenders = new ArrayList<>();

        teamCodes.add("ABC123");
        teamCodes.add("ABC456");

        OffenderEntity offenderEntity = new OffenderEntity();
        offenderEntity.setSurname("Surname");
        offenders.add(offenderEntity);


    }
}
