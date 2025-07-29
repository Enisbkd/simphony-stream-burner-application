package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TaxeRateTransTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TaxeRateTrans getTaxeRateTransSample1() {
        return new TaxeRateTrans()
            .id(1L)
            .orgShortName("orgShortName1")
            .locRef("locRef1")
            .rvcRef(1)
            .taxRateId(1)
            .taxType("taxType1")
            .nameFR("nameFR1")
            .nameEN("nameEN1");
    }

    public static TaxeRateTrans getTaxeRateTransSample2() {
        return new TaxeRateTrans()
            .id(2L)
            .orgShortName("orgShortName2")
            .locRef("locRef2")
            .rvcRef(2)
            .taxRateId(2)
            .taxType("taxType2")
            .nameFR("nameFR2")
            .nameEN("nameEN2");
    }

    public static TaxeRateTrans getTaxeRateTransRandomSampleGenerator() {
        return new TaxeRateTrans()
            .id(longCount.incrementAndGet())
            .orgShortName(UUID.randomUUID().toString())
            .locRef(UUID.randomUUID().toString())
            .rvcRef(intCount.incrementAndGet())
            .taxRateId(intCount.incrementAndGet())
            .taxType(UUID.randomUUID().toString())
            .nameFR(UUID.randomUUID().toString())
            .nameEN(UUID.randomUUID().toString());
    }
}
