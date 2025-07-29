package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.MenuItemPricesCnCTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MenuItemPricesCnCTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuItemPricesCnC.class);
        MenuItemPricesCnC menuItemPricesCnC1 = getMenuItemPricesCnCSample1();
        MenuItemPricesCnC menuItemPricesCnC2 = new MenuItemPricesCnC();
        assertThat(menuItemPricesCnC1).isNotEqualTo(menuItemPricesCnC2);

        menuItemPricesCnC2.setId(menuItemPricesCnC1.getId());
        assertThat(menuItemPricesCnC1).isEqualTo(menuItemPricesCnC2);

        menuItemPricesCnC2 = getMenuItemPricesCnCSample2();
        assertThat(menuItemPricesCnC1).isNotEqualTo(menuItemPricesCnC2);
    }
}
