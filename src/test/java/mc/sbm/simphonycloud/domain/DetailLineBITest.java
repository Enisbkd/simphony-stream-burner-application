package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.DetailLineBITestSamples.*;
import static mc.sbm.simphonycloud.domain.GuestCheckBITestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailLineBITest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailLineBI.class);
        DetailLineBI detailLineBI1 = getDetailLineBISample1();
        DetailLineBI detailLineBI2 = new DetailLineBI();
        assertThat(detailLineBI1).isNotEqualTo(detailLineBI2);

        detailLineBI2.setId(detailLineBI1.getId());
        assertThat(detailLineBI1).isEqualTo(detailLineBI2);

        detailLineBI2 = getDetailLineBISample2();
        assertThat(detailLineBI1).isNotEqualTo(detailLineBI2);
    }

    @Test
    void guestCheckBITest() {
        DetailLineBI detailLineBI = getDetailLineBIRandomSampleGenerator();
        GuestCheckBI guestCheckBIBack = getGuestCheckBIRandomSampleGenerator();

        detailLineBI.setGuestCheckBI(guestCheckBIBack);
        assertThat(detailLineBI.getGuestCheckBI()).isEqualTo(guestCheckBIBack);

        detailLineBI.guestCheckBI(null);
        assertThat(detailLineBI.getGuestCheckBI()).isNull();
    }
}
