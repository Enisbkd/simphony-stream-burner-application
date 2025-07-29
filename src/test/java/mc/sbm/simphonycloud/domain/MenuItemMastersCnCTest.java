package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.MenuItemMastersCnCTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MenuItemMastersCnCTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuItemMastersCnC.class);
        MenuItemMastersCnC menuItemMastersCnC1 = getMenuItemMastersCnCSample1();
        MenuItemMastersCnC menuItemMastersCnC2 = new MenuItemMastersCnC();
        assertThat(menuItemMastersCnC1).isNotEqualTo(menuItemMastersCnC2);

        menuItemMastersCnC2.setId(menuItemMastersCnC1.getId());
        assertThat(menuItemMastersCnC1).isEqualTo(menuItemMastersCnC2);

        menuItemMastersCnC2 = getMenuItemMastersCnCSample2();
        assertThat(menuItemMastersCnC1).isNotEqualTo(menuItemMastersCnC2);
    }
}
