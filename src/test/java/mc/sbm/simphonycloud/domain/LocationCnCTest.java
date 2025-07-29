package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.LocationCnCTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocationCnCTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationCnC.class);
        LocationCnC locationCnC1 = getLocationCnCSample1();
        LocationCnC locationCnC2 = new LocationCnC();
        assertThat(locationCnC1).isNotEqualTo(locationCnC2);

        locationCnC2.setId(locationCnC1.getId());
        assertThat(locationCnC1).isEqualTo(locationCnC2);

        locationCnC2 = getLocationCnCSample2();
        assertThat(locationCnC1).isNotEqualTo(locationCnC2);
    }
}
