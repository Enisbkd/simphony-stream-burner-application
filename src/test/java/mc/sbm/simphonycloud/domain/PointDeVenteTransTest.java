package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.PointDeVenteTransTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PointDeVenteTransTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointDeVenteTrans.class);
        PointDeVenteTrans pointDeVenteTrans1 = getPointDeVenteTransSample1();
        PointDeVenteTrans pointDeVenteTrans2 = new PointDeVenteTrans();
        assertThat(pointDeVenteTrans1).isNotEqualTo(pointDeVenteTrans2);

        pointDeVenteTrans2.setId(pointDeVenteTrans1.getId());
        assertThat(pointDeVenteTrans1).isEqualTo(pointDeVenteTrans2);

        pointDeVenteTrans2 = getPointDeVenteTransSample2();
        assertThat(pointDeVenteTrans1).isNotEqualTo(pointDeVenteTrans2);
    }
}
