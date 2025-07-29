package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.OrderChannelBITestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderChannelBITest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderChannelBI.class);
        OrderChannelBI orderChannelBI1 = getOrderChannelBISample1();
        OrderChannelBI orderChannelBI2 = new OrderChannelBI();
        assertThat(orderChannelBI1).isNotEqualTo(orderChannelBI2);

        orderChannelBI2.setId(orderChannelBI1.getId());
        assertThat(orderChannelBI1).isEqualTo(orderChannelBI2);

        orderChannelBI2 = getOrderChannelBISample2();
        assertThat(orderChannelBI1).isNotEqualTo(orderChannelBI2);
    }
}
