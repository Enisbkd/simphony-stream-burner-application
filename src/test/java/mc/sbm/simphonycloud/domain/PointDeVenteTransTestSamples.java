package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class PointDeVenteTransTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PointDeVenteTrans getPointDeVenteTransSample1() {
        return new PointDeVenteTrans().id(1).rvcRef(1).name("name1").locRef("locRef1").orgShortName("orgShortName1").address("address1");
    }

    public static PointDeVenteTrans getPointDeVenteTransSample2() {
        return new PointDeVenteTrans().id(2).rvcRef(2).name("name2").locRef("locRef2").orgShortName("orgShortName2").address("address2");
    }

    public static PointDeVenteTrans getPointDeVenteTransRandomSampleGenerator() {
        return new PointDeVenteTrans()
            .id(intCount.incrementAndGet())
            .rvcRef(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .locRef(UUID.randomUUID().toString())
            .orgShortName(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString());
    }
}
