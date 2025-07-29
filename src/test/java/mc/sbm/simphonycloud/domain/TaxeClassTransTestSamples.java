package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TaxeClassTransTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TaxeClassTrans getTaxeClassTransSample1() {
        return new TaxeClassTrans()
            .id(1L)
            .orgShortName("orgShortName1")
            .locRef("locRef1")
            .rvcRef(1)
            .taxClassId(1)
            .activeTaxRateRefs("activeTaxRateRefs1");
    }

    public static TaxeClassTrans getTaxeClassTransSample2() {
        return new TaxeClassTrans()
            .id(2L)
            .orgShortName("orgShortName2")
            .locRef("locRef2")
            .rvcRef(2)
            .taxClassId(2)
            .activeTaxRateRefs("activeTaxRateRefs2");
    }

    public static TaxeClassTrans getTaxeClassTransRandomSampleGenerator() {
        return new TaxeClassTrans()
            .id(longCount.incrementAndGet())
            .orgShortName(UUID.randomUUID().toString())
            .locRef(UUID.randomUUID().toString())
            .rvcRef(intCount.incrementAndGet())
            .taxClassId(intCount.incrementAndGet())
            .activeTaxRateRefs(UUID.randomUUID().toString());
    }
}
