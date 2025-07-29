package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.MajorGroupCnCTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MajorGroupCnCTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MajorGroupCnC.class);
        MajorGroupCnC majorGroupCnC1 = getMajorGroupCnCSample1();
        MajorGroupCnC majorGroupCnC2 = new MajorGroupCnC();
        assertThat(majorGroupCnC1).isNotEqualTo(majorGroupCnC2);

        majorGroupCnC2.setId(majorGroupCnC1.getId());
        assertThat(majorGroupCnC1).isEqualTo(majorGroupCnC2);

        majorGroupCnC2 = getMajorGroupCnCSample2();
        assertThat(majorGroupCnC1).isNotEqualTo(majorGroupCnC2);
    }
}
