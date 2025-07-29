package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.TaxeRateTransTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaxeRateTransTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxeRateTrans.class);
        TaxeRateTrans taxeRateTrans1 = getTaxeRateTransSample1();
        TaxeRateTrans taxeRateTrans2 = new TaxeRateTrans();
        assertThat(taxeRateTrans1).isNotEqualTo(taxeRateTrans2);

        taxeRateTrans2.setId(taxeRateTrans1.getId());
        assertThat(taxeRateTrans1).isEqualTo(taxeRateTrans2);

        taxeRateTrans2 = getTaxeRateTransSample2();
        assertThat(taxeRateTrans1).isNotEqualTo(taxeRateTrans2);
    }
}
