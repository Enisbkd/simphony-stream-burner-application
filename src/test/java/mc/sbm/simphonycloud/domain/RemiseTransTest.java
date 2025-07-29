package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.RemiseTransTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemiseTransTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemiseTrans.class);
        RemiseTrans remiseTrans1 = getRemiseTransSample1();
        RemiseTrans remiseTrans2 = new RemiseTrans();
        assertThat(remiseTrans1).isNotEqualTo(remiseTrans2);

        remiseTrans2.setId(remiseTrans1.getId());
        assertThat(remiseTrans1).isEqualTo(remiseTrans2);

        remiseTrans2 = getRemiseTransSample2();
        assertThat(remiseTrans1).isNotEqualTo(remiseTrans2);
    }
}
