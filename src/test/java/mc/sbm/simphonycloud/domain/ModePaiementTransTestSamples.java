package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ModePaiementTransTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ModePaiementTrans getModePaiementTransSample1() {
        return new ModePaiementTrans()
            .id(1)
            .tenderId(1)
            .name("name1")
            .type("type1")
            .extensions("extensions1")
            .orgShortName("orgShortName1")
            .locRef("locRef1")
            .rvcRef(1);
    }

    public static ModePaiementTrans getModePaiementTransSample2() {
        return new ModePaiementTrans()
            .id(2)
            .tenderId(2)
            .name("name2")
            .type("type2")
            .extensions("extensions2")
            .orgShortName("orgShortName2")
            .locRef("locRef2")
            .rvcRef(2);
    }

    public static ModePaiementTrans getModePaiementTransRandomSampleGenerator() {
        return new ModePaiementTrans()
            .id(intCount.incrementAndGet())
            .tenderId(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .extensions(UUID.randomUUID().toString())
            .orgShortName(UUID.randomUUID().toString())
            .locRef(UUID.randomUUID().toString())
            .rvcRef(intCount.incrementAndGet());
    }
}
