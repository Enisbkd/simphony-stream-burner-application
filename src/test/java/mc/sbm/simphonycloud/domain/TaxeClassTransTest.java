package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.TaxeClassTransTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaxeClassTransTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxeClassTrans.class);
        TaxeClassTrans taxeClassTrans1 = getTaxeClassTransSample1();
        TaxeClassTrans taxeClassTrans2 = new TaxeClassTrans();
        assertThat(taxeClassTrans1).isNotEqualTo(taxeClassTrans2);

        taxeClassTrans2.setId(taxeClassTrans1.getId());
        assertThat(taxeClassTrans1).isEqualTo(taxeClassTrans2);

        taxeClassTrans2 = getTaxeClassTransSample2();
        assertThat(taxeClassTrans1).isNotEqualTo(taxeClassTrans2);
    }
}
