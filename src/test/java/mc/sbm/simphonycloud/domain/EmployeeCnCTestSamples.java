package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EmployeeCnCTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static EmployeeCnC getEmployeeCnCSample1() {
        return new EmployeeCnC()
            .id(1L)
            .objectNum(1)
            .firstName("firstName1")
            .lastName("lastName1")
            .checkName("checkName1")
            .email("email1")
            .languageObjNum(1)
            .langId(1)
            .alternateId(1)
            .level(1)
            .group(1)
            .userName("userName1")
            .firstRoleHierUnitId(1)
            .firstRoleObjNum(1)
            .firstVisibilityHierUnitId(1)
            .firstPropertyHierUnitId(1)
            .firstPropertyObjNum(1)
            .firstPropertyEmpClassObjNum(1)
            .firstPropertyOptions("firstPropertyOptions1")
            .firstOperatorRvcHierUnitId(1)
            .firstOperatorRvcObjNum(1)
            .firstOperatorOptions("firstOperatorOptions1")
            .firstOperatorCashDrawerObjNum(1)
            .firstOperatorTmsColorObjNum(1)
            .firstOperatorServerEfficiency(1)
            .pin("pin1")
            .accessId(1);
    }

    public static EmployeeCnC getEmployeeCnCSample2() {
        return new EmployeeCnC()
            .id(2L)
            .objectNum(2)
            .firstName("firstName2")
            .lastName("lastName2")
            .checkName("checkName2")
            .email("email2")
            .languageObjNum(2)
            .langId(2)
            .alternateId(2)
            .level(2)
            .group(2)
            .userName("userName2")
            .firstRoleHierUnitId(2)
            .firstRoleObjNum(2)
            .firstVisibilityHierUnitId(2)
            .firstPropertyHierUnitId(2)
            .firstPropertyObjNum(2)
            .firstPropertyEmpClassObjNum(2)
            .firstPropertyOptions("firstPropertyOptions2")
            .firstOperatorRvcHierUnitId(2)
            .firstOperatorRvcObjNum(2)
            .firstOperatorOptions("firstOperatorOptions2")
            .firstOperatorCashDrawerObjNum(2)
            .firstOperatorTmsColorObjNum(2)
            .firstOperatorServerEfficiency(2)
            .pin("pin2")
            .accessId(2);
    }

    public static EmployeeCnC getEmployeeCnCRandomSampleGenerator() {
        return new EmployeeCnC()
            .id(longCount.incrementAndGet())
            .objectNum(intCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .checkName(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .languageObjNum(intCount.incrementAndGet())
            .langId(intCount.incrementAndGet())
            .alternateId(intCount.incrementAndGet())
            .level(intCount.incrementAndGet())
            .group(intCount.incrementAndGet())
            .userName(UUID.randomUUID().toString())
            .firstRoleHierUnitId(intCount.incrementAndGet())
            .firstRoleObjNum(intCount.incrementAndGet())
            .firstVisibilityHierUnitId(intCount.incrementAndGet())
            .firstPropertyHierUnitId(intCount.incrementAndGet())
            .firstPropertyObjNum(intCount.incrementAndGet())
            .firstPropertyEmpClassObjNum(intCount.incrementAndGet())
            .firstPropertyOptions(UUID.randomUUID().toString())
            .firstOperatorRvcHierUnitId(intCount.incrementAndGet())
            .firstOperatorRvcObjNum(intCount.incrementAndGet())
            .firstOperatorOptions(UUID.randomUUID().toString())
            .firstOperatorCashDrawerObjNum(intCount.incrementAndGet())
            .firstOperatorTmsColorObjNum(intCount.incrementAndGet())
            .firstOperatorServerEfficiency(intCount.incrementAndGet())
            .pin(UUID.randomUUID().toString())
            .accessId(intCount.incrementAndGet());
    }
}
