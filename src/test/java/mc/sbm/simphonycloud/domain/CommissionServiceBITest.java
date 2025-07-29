package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.CommissionServiceBITestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommissionServiceBITest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommissionServiceBI.class);
        CommissionServiceBI commissionServiceBI1 = getCommissionServiceBISample1();
        CommissionServiceBI commissionServiceBI2 = new CommissionServiceBI();
        assertThat(commissionServiceBI1).isNotEqualTo(commissionServiceBI2);

        commissionServiceBI2.setId(commissionServiceBI1.getId());
        assertThat(commissionServiceBI1).isEqualTo(commissionServiceBI2);

        commissionServiceBI2 = getCommissionServiceBISample2();
        assertThat(commissionServiceBI1).isNotEqualTo(commissionServiceBI2);
    }
}
