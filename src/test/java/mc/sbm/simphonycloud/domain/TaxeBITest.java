package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.TaxeBITestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaxeBITest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxeBI.class);
        TaxeBI taxeBI1 = getTaxeBISample1();
        TaxeBI taxeBI2 = new TaxeBI();
        assertThat(taxeBI1).isNotEqualTo(taxeBI2);

        taxeBI2.setId(taxeBI1.getId());
        assertThat(taxeBI1).isEqualTo(taxeBI2);

        taxeBI2 = getTaxeBISample2();
        assertThat(taxeBI1).isNotEqualTo(taxeBI2);
    }
}
