package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.MenuItemDefinitionsCnCTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MenuItemDefinitionsCnCTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuItemDefinitionsCnC.class);
        MenuItemDefinitionsCnC menuItemDefinitionsCnC1 = getMenuItemDefinitionsCnCSample1();
        MenuItemDefinitionsCnC menuItemDefinitionsCnC2 = new MenuItemDefinitionsCnC();
        assertThat(menuItemDefinitionsCnC1).isNotEqualTo(menuItemDefinitionsCnC2);

        menuItemDefinitionsCnC2.setId(menuItemDefinitionsCnC1.getId());
        assertThat(menuItemDefinitionsCnC1).isEqualTo(menuItemDefinitionsCnC2);

        menuItemDefinitionsCnC2 = getMenuItemDefinitionsCnCSample2();
        assertThat(menuItemDefinitionsCnC1).isNotEqualTo(menuItemDefinitionsCnC2);
    }
}
