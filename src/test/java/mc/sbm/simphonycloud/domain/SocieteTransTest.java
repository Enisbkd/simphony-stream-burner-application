package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.SocieteTransTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocieteTransTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocieteTrans.class);
        SocieteTrans societeTrans1 = getSocieteTransSample1();
        SocieteTrans societeTrans2 = new SocieteTrans();
        assertThat(societeTrans1).isNotEqualTo(societeTrans2);

        societeTrans2.setId(societeTrans1.getId());
        assertThat(societeTrans1).isEqualTo(societeTrans2);

        societeTrans2 = getSocieteTransSample2();
        assertThat(societeTrans1).isNotEqualTo(societeTrans2);
    }
}
