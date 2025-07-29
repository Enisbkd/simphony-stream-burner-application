package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ModePaiementBITestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ModePaiementBI getModePaiementBISample1() {
        return new ModePaiementBI().id(1).locRef("locRef1").num(1).name("name1").type(1);
    }

    public static ModePaiementBI getModePaiementBISample2() {
        return new ModePaiementBI().id(2).locRef("locRef2").num(2).name("name2").type(2);
    }

    public static ModePaiementBI getModePaiementBIRandomSampleGenerator() {
        return new ModePaiementBI()
            .id(intCount.incrementAndGet())
            .locRef(UUID.randomUUID().toString())
            .num(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .type(intCount.incrementAndGet());
    }
}
