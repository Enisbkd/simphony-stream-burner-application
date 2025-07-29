package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.HierarchieCnCTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HierarchieCnCTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HierarchieCnC.class);
        HierarchieCnC hierarchieCnC1 = getHierarchieCnCSample1();
        HierarchieCnC hierarchieCnC2 = new HierarchieCnC();
        assertThat(hierarchieCnC1).isNotEqualTo(hierarchieCnC2);

        hierarchieCnC2.setId(hierarchieCnC1.getId());
        assertThat(hierarchieCnC1).isEqualTo(hierarchieCnC2);

        hierarchieCnC2 = getHierarchieCnCSample2();
        assertThat(hierarchieCnC1).isNotEqualTo(hierarchieCnC2);
    }
}
