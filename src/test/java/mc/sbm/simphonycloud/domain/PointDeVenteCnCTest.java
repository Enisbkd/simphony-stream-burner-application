package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.PointDeVenteCnCTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PointDeVenteCnCTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointDeVenteCnC.class);
        PointDeVenteCnC pointDeVenteCnC1 = getPointDeVenteCnCSample1();
        PointDeVenteCnC pointDeVenteCnC2 = new PointDeVenteCnC();
        assertThat(pointDeVenteCnC1).isNotEqualTo(pointDeVenteCnC2);

        pointDeVenteCnC2.setId(pointDeVenteCnC1.getId());
        assertThat(pointDeVenteCnC1).isEqualTo(pointDeVenteCnC2);

        pointDeVenteCnC2 = getPointDeVenteCnCSample2();
        assertThat(pointDeVenteCnC1).isNotEqualTo(pointDeVenteCnC2);
    }
}
