package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.DetailLineBITestSamples.*;
import static mc.sbm.simphonycloud.domain.GuestCheckBITestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GuestCheckBITest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuestCheckBI.class);
        GuestCheckBI guestCheckBI1 = getGuestCheckBISample1();
        GuestCheckBI guestCheckBI2 = new GuestCheckBI();
        assertThat(guestCheckBI1).isNotEqualTo(guestCheckBI2);

        guestCheckBI2.setId(guestCheckBI1.getId());
        assertThat(guestCheckBI1).isEqualTo(guestCheckBI2);

        guestCheckBI2 = getGuestCheckBISample2();
        assertThat(guestCheckBI1).isNotEqualTo(guestCheckBI2);
    }

    @Test
    void detailLineBIListTest() {
        GuestCheckBI guestCheckBI = getGuestCheckBIRandomSampleGenerator();
        DetailLineBI detailLineBIBack = getDetailLineBIRandomSampleGenerator();

        guestCheckBI.addDetailLineBIList(detailLineBIBack);
        assertThat(guestCheckBI.getDetailLineBILists()).containsOnly(detailLineBIBack);
        assertThat(detailLineBIBack.getGuestCheckBI()).isEqualTo(guestCheckBI);

        guestCheckBI.removeDetailLineBIList(detailLineBIBack);
        assertThat(guestCheckBI.getDetailLineBILists()).doesNotContain(detailLineBIBack);
        assertThat(detailLineBIBack.getGuestCheckBI()).isNull();

        guestCheckBI.detailLineBILists(new HashSet<>(Set.of(detailLineBIBack)));
        assertThat(guestCheckBI.getDetailLineBILists()).containsOnly(detailLineBIBack);
        assertThat(detailLineBIBack.getGuestCheckBI()).isEqualTo(guestCheckBI);

        guestCheckBI.setDetailLineBILists(new HashSet<>());
        assertThat(guestCheckBI.getDetailLineBILists()).doesNotContain(detailLineBIBack);
        assertThat(detailLineBIBack.getGuestCheckBI()).isNull();
    }
}
