package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TaxeClassTransTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TaxeClassTrans getTaxeClassTransSample1() {
        return new TaxeClassTrans()
            .id(1)
            .orgShortName("orgShortName1")
            .locRef("locRef1")
            .rvcRef(1)
            .taxClassId(1)
            .activeTaxRateRefs("activeTaxRateRefs1");
    }

    public static TaxeClassTrans getTaxeClassTransSample2() {
        return new TaxeClassTrans()
            .id(2)
            .orgShortName("orgShortName2")
            .locRef("locRef2")
            .rvcRef(2)
            .taxClassId(2)
            .activeTaxRateRefs("activeTaxRateRefs2");
    }

    public static TaxeClassTrans getTaxeClassTransRandomSampleGenerator() {
        return new TaxeClassTrans()
            .id(intCount.incrementAndGet())
            .orgShortName(UUID.randomUUID().toString())
            .locRef(UUID.randomUUID().toString())
            .rvcRef(intCount.incrementAndGet())
            .taxClassId(intCount.incrementAndGet())
            .activeTaxRateRefs(UUID.randomUUID().toString());
    }
}
