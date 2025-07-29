package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.BarCodeTransTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BarCodeTransTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BarCodeTrans.class);
        BarCodeTrans barCodeTrans1 = getBarCodeTransSample1();
        BarCodeTrans barCodeTrans2 = new BarCodeTrans();
        assertThat(barCodeTrans1).isNotEqualTo(barCodeTrans2);

        barCodeTrans2.setId(barCodeTrans1.getId());
        assertThat(barCodeTrans1).isEqualTo(barCodeTrans2);

        barCodeTrans2 = getBarCodeTransSample2();
        assertThat(barCodeTrans1).isNotEqualTo(barCodeTrans2);
    }
}
