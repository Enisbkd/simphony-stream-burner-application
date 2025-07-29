package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.ModePaiementBITestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModePaiementBITest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModePaiementBI.class);
        ModePaiementBI modePaiementBI1 = getModePaiementBISample1();
        ModePaiementBI modePaiementBI2 = new ModePaiementBI();
        assertThat(modePaiementBI1).isNotEqualTo(modePaiementBI2);

        modePaiementBI2.setId(modePaiementBI1.getId());
        assertThat(modePaiementBI1).isEqualTo(modePaiementBI2);

        modePaiementBI2 = getModePaiementBISample2();
        assertThat(modePaiementBI1).isNotEqualTo(modePaiementBI2);
    }
}
