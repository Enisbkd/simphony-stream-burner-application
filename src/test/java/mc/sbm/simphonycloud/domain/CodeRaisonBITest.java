package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.CodeRaisonBITestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CodeRaisonBITest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodeRaisonBI.class);
        CodeRaisonBI codeRaisonBI1 = getCodeRaisonBISample1();
        CodeRaisonBI codeRaisonBI2 = new CodeRaisonBI();
        assertThat(codeRaisonBI1).isNotEqualTo(codeRaisonBI2);

        codeRaisonBI2.setId(codeRaisonBI1.getId());
        assertThat(codeRaisonBI1).isEqualTo(codeRaisonBI2);

        codeRaisonBI2 = getCodeRaisonBISample2();
        assertThat(codeRaisonBI1).isNotEqualTo(codeRaisonBI2);
    }
}
