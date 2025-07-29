package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RemiseTransTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RemiseTrans getRemiseTransSample1() {
        return new RemiseTrans()
            .id(1L)
            .orgShortName("orgShortName1")
            .locRef("locRef1")
            .rvcRef(1)
            .discountId(1)
            .frName("frName1")
            .engName("engName1")
            .discountType("discountType1");
    }

    public static RemiseTrans getRemiseTransSample2() {
        return new RemiseTrans()
            .id(2L)
            .orgShortName("orgShortName2")
            .locRef("locRef2")
            .rvcRef(2)
            .discountId(2)
            .frName("frName2")
            .engName("engName2")
            .discountType("discountType2");
    }

    public static RemiseTrans getRemiseTransRandomSampleGenerator() {
        return new RemiseTrans()
            .id(longCount.incrementAndGet())
            .orgShortName(UUID.randomUUID().toString())
            .locRef(UUID.randomUUID().toString())
            .rvcRef(intCount.incrementAndGet())
            .discountId(intCount.incrementAndGet())
            .frName(UUID.randomUUID().toString())
            .engName(UUID.randomUUID().toString())
            .discountType(UUID.randomUUID().toString());
    }
}
