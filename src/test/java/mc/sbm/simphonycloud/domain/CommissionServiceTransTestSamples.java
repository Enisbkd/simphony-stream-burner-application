package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CommissionServiceTransTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static CommissionServiceTrans getCommissionServiceTransSample1() {
        return new CommissionServiceTrans()
            .id(1L)
            .orgShortName("orgShortName1")
            .locRef("locRef1")
            .rvcRef(1)
            .serviceChargeId(1)
            .name("name1")
            .type("type1");
    }

    public static CommissionServiceTrans getCommissionServiceTransSample2() {
        return new CommissionServiceTrans()
            .id(2L)
            .orgShortName("orgShortName2")
            .locRef("locRef2")
            .rvcRef(2)
            .serviceChargeId(2)
            .name("name2")
            .type("type2");
    }

    public static CommissionServiceTrans getCommissionServiceTransRandomSampleGenerator() {
        return new CommissionServiceTrans()
            .id(longCount.incrementAndGet())
            .orgShortName(UUID.randomUUID().toString())
            .locRef(UUID.randomUUID().toString())
            .rvcRef(intCount.incrementAndGet())
            .serviceChargeId(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
