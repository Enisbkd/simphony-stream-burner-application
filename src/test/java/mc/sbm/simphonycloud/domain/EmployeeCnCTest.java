package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.EmployeeCnCTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeCnCTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeCnC.class);
        EmployeeCnC employeeCnC1 = getEmployeeCnCSample1();
        EmployeeCnC employeeCnC2 = new EmployeeCnC();
        assertThat(employeeCnC1).isNotEqualTo(employeeCnC2);

        employeeCnC2.setId(employeeCnC1.getId());
        assertThat(employeeCnC1).isEqualTo(employeeCnC2);

        employeeCnC2 = getEmployeeCnCSample2();
        assertThat(employeeCnC1).isNotEqualTo(employeeCnC2);
    }
}
