package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.HierarchieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HierarchieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hierarchie.class);
        Hierarchie hierarchie1 = getHierarchieSample1();
        Hierarchie hierarchie2 = new Hierarchie();
        assertThat(hierarchie1).isNotEqualTo(hierarchie2);

        hierarchie2.setId(hierarchie1.getId());
        assertThat(hierarchie1).isEqualTo(hierarchie2);

        hierarchie2 = getHierarchieSample2();
        assertThat(hierarchie1).isNotEqualTo(hierarchie2);
    }
}
