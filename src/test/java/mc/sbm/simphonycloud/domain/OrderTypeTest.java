package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.OrderTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderType.class);
        OrderType orderType1 = getOrderTypeSample1();
        OrderType orderType2 = new OrderType();
        assertThat(orderType1).isNotEqualTo(orderType2);

        orderType2.setId(orderType1.getId());
        assertThat(orderType1).isEqualTo(orderType2);

        orderType2 = getOrderTypeSample2();
        assertThat(orderType1).isNotEqualTo(orderType2);
    }
}
