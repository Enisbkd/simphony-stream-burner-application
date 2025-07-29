package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.ModePaiementTransTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModePaiementTransTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModePaiementTrans.class);
        ModePaiementTrans modePaiementTrans1 = getModePaiementTransSample1();
        ModePaiementTrans modePaiementTrans2 = new ModePaiementTrans();
        assertThat(modePaiementTrans1).isNotEqualTo(modePaiementTrans2);

        modePaiementTrans2.setId(modePaiementTrans1.getId());
        assertThat(modePaiementTrans1).isEqualTo(modePaiementTrans2);

        modePaiementTrans2 = getModePaiementTransSample2();
        assertThat(modePaiementTrans1).isNotEqualTo(modePaiementTrans2);
    }
}
