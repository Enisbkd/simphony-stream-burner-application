package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderChannelBITestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static OrderChannelBI getOrderChannelBISample1() {
        return new OrderChannelBI().id(1).num(1).locRef("locRef1").name("name1").mstrNum(1).mstrName("mstrName1");
    }

    public static OrderChannelBI getOrderChannelBISample2() {
        return new OrderChannelBI().id(2).num(2).locRef("locRef2").name("name2").mstrNum(2).mstrName("mstrName2");
    }

    public static OrderChannelBI getOrderChannelBIRandomSampleGenerator() {
        return new OrderChannelBI()
            .id(intCount.incrementAndGet())
            .num(intCount.incrementAndGet())
            .locRef(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .mstrNum(intCount.incrementAndGet())
            .mstrName(UUID.randomUUID().toString());
    }
}
