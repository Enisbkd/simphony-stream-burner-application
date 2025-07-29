package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.FamilyGroupCnCTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FamilyGroupCnCTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilyGroupCnC.class);
        FamilyGroupCnC familyGroupCnC1 = getFamilyGroupCnCSample1();
        FamilyGroupCnC familyGroupCnC2 = new FamilyGroupCnC();
        assertThat(familyGroupCnC1).isNotEqualTo(familyGroupCnC2);

        familyGroupCnC2.setId(familyGroupCnC1.getId());
        assertThat(familyGroupCnC1).isEqualTo(familyGroupCnC2);

        familyGroupCnC2 = getFamilyGroupCnCSample2();
        assertThat(familyGroupCnC1).isNotEqualTo(familyGroupCnC2);
    }
}
