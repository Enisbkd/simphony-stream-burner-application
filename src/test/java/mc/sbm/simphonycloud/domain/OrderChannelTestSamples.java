package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class OrderChannelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static OrderChannel getOrderChannelSample1() {
        return new OrderChannel().id(1L).num(1).locRef("locRef1").name("name1").mstrNum(1).mstrName("mstrName1");
    }

    public static OrderChannel getOrderChannelSample2() {
        return new OrderChannel().id(2L).num(2).locRef("locRef2").name("name2").mstrNum(2).mstrName("mstrName2");
    }

    public static OrderChannel getOrderChannelRandomSampleGenerator() {
        return new OrderChannel()
            .id(longCount.incrementAndGet())
            .num(intCount.incrementAndGet())
            .locRef(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .mstrNum(intCount.incrementAndGet())
            .mstrName(UUID.randomUUID().toString());
    }
}
