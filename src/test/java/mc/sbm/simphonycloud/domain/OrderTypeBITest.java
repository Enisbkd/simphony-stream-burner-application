package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.OrderTypeBITestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderTypeBITest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderTypeBI.class);
        OrderTypeBI orderTypeBI1 = getOrderTypeBISample1();
        OrderTypeBI orderTypeBI2 = new OrderTypeBI();
        assertThat(orderTypeBI1).isNotEqualTo(orderTypeBI2);

        orderTypeBI2.setId(orderTypeBI1.getId());
        assertThat(orderTypeBI1).isEqualTo(orderTypeBI2);

        orderTypeBI2 = getOrderTypeBISample2();
        assertThat(orderTypeBI1).isNotEqualTo(orderTypeBI2);
    }
}
