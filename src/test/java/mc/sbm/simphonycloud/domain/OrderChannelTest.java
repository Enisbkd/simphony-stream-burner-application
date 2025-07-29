package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.OrderChannelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderChannelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderChannel.class);
        OrderChannel orderChannel1 = getOrderChannelSample1();
        OrderChannel orderChannel2 = new OrderChannel();
        assertThat(orderChannel1).isNotEqualTo(orderChannel2);

        orderChannel2.setId(orderChannel1.getId());
        assertThat(orderChannel1).isEqualTo(orderChannel2);

        orderChannel2 = getOrderChannelSample2();
        assertThat(orderChannel1).isNotEqualTo(orderChannel2);
    }
}
