package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckTransTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static CheckTrans getCheckTransSample1() {
        return new CheckTrans()
            .id(1)
            .rvcRef(1)
            .checkRef("checkRef1")
            .checkNumber(1)
            .checkName("checkName1")
            .checkEmployeeRef(1)
            .orderTypeRef(1)
            .orderChannelRef(1)
            .tableName("tableName1")
            .tableGroupNumber(1)
            .guestCount(1)
            .language("language1")
            .status("status1")
            .preparationStatus("preparationStatus1")
            .taxRateId(1);
    }

    public static CheckTrans getCheckTransSample2() {
        return new CheckTrans()
            .id(2)
            .rvcRef(2)
            .checkRef("checkRef2")
            .checkNumber(2)
            .checkName("checkName2")
            .checkEmployeeRef(2)
            .orderTypeRef(2)
            .orderChannelRef(2)
            .tableName("tableName2")
            .tableGroupNumber(2)
            .guestCount(2)
            .language("language2")
            .status("status2")
            .preparationStatus("preparationStatus2")
            .taxRateId(2);
    }

    public static CheckTrans getCheckTransRandomSampleGenerator() {
        return new CheckTrans()
            .id(intCount.incrementAndGet())
            .rvcRef(intCount.incrementAndGet())
            .checkRef(UUID.randomUUID().toString())
            .checkNumber(intCount.incrementAndGet())
            .checkName(UUID.randomUUID().toString())
            .checkEmployeeRef(intCount.incrementAndGet())
            .orderTypeRef(intCount.incrementAndGet())
            .orderChannelRef(intCount.incrementAndGet())
            .tableName(UUID.randomUUID().toString())
            .tableGroupNumber(intCount.incrementAndGet())
            .guestCount(intCount.incrementAndGet())
            .language(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .preparationStatus(UUID.randomUUID().toString())
            .taxRateId(intCount.incrementAndGet());
    }
}
