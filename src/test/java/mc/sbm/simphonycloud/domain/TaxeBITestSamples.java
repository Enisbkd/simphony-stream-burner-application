package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TaxeBITestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TaxeBI getTaxeBISample1() {
        return new TaxeBI().id(1L).locRef("locRef1").num(1).name("name1").type(1);
    }

    public static TaxeBI getTaxeBISample2() {
        return new TaxeBI().id(2L).locRef("locRef2").num(2).name("name2").type(2);
    }

    public static TaxeBI getTaxeBIRandomSampleGenerator() {
        return new TaxeBI()
            .id(longCount.incrementAndGet())
            .locRef(UUID.randomUUID().toString())
            .num(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .type(intCount.incrementAndGet());
    }
}
