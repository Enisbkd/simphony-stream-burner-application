package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.CommissionServiceTransTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommissionServiceTransTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommissionServiceTrans.class);
        CommissionServiceTrans commissionServiceTrans1 = getCommissionServiceTransSample1();
        CommissionServiceTrans commissionServiceTrans2 = new CommissionServiceTrans();
        assertThat(commissionServiceTrans1).isNotEqualTo(commissionServiceTrans2);

        commissionServiceTrans2.setId(commissionServiceTrans1.getId());
        assertThat(commissionServiceTrans1).isEqualTo(commissionServiceTrans2);

        commissionServiceTrans2 = getCommissionServiceTransSample2();
        assertThat(commissionServiceTrans1).isNotEqualTo(commissionServiceTrans2);
    }
}
