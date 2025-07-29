package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PointDeVenteCnCTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PointDeVenteCnC getPointDeVenteCnCSample1() {
        return new PointDeVenteCnC()
            .id(1L)
            .locHierUnitId(1)
            .locObjNum(1)
            .rvcId(1)
            .kdsControllerId(1)
            .hierUnitId(1)
            .objectNum(1)
            .name("name1")
            .dataExtensions("dataExtensions1");
    }

    public static PointDeVenteCnC getPointDeVenteCnCSample2() {
        return new PointDeVenteCnC()
            .id(2L)
            .locHierUnitId(2)
            .locObjNum(2)
            .rvcId(2)
            .kdsControllerId(2)
            .hierUnitId(2)
            .objectNum(2)
            .name("name2")
            .dataExtensions("dataExtensions2");
    }

    public static PointDeVenteCnC getPointDeVenteCnCRandomSampleGenerator() {
        return new PointDeVenteCnC()
            .id(longCount.incrementAndGet())
            .locHierUnitId(intCount.incrementAndGet())
            .locObjNum(intCount.incrementAndGet())
            .rvcId(intCount.incrementAndGet())
            .kdsControllerId(intCount.incrementAndGet())
            .hierUnitId(intCount.incrementAndGet())
            .objectNum(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .dataExtensions(UUID.randomUUID().toString());
    }
}
