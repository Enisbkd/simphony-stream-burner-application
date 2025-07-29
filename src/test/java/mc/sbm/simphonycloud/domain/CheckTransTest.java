package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.CheckTransTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckTransTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckTrans.class);
        CheckTrans checkTrans1 = getCheckTransSample1();
        CheckTrans checkTrans2 = new CheckTrans();
        assertThat(checkTrans1).isNotEqualTo(checkTrans2);

        checkTrans2.setId(checkTrans1.getId());
        assertThat(checkTrans1).isEqualTo(checkTrans2);

        checkTrans2 = getCheckTransSample2();
        assertThat(checkTrans1).isNotEqualTo(checkTrans2);
    }
}
