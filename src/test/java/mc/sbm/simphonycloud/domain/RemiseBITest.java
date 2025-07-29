package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.RemiseBITestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemiseBITest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemiseBI.class);
        RemiseBI remiseBI1 = getRemiseBISample1();
        RemiseBI remiseBI2 = new RemiseBI();
        assertThat(remiseBI1).isNotEqualTo(remiseBI2);

        remiseBI2.setId(remiseBI1.getId());
        assertThat(remiseBI1).isEqualTo(remiseBI2);

        remiseBI2 = getRemiseBISample2();
        assertThat(remiseBI1).isNotEqualTo(remiseBI2);
    }
}
