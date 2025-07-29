package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class HierarchieCnCTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static HierarchieCnC getHierarchieCnCSample1() {
        return new HierarchieCnC().id(1).nom("nom1").parentHierId("parentHierId1").unitId("unitId1");
    }

    public static HierarchieCnC getHierarchieCnCSample2() {
        return new HierarchieCnC().id(2).nom("nom2").parentHierId("parentHierId2").unitId("unitId2");
    }

    public static HierarchieCnC getHierarchieCnCRandomSampleGenerator() {
        return new HierarchieCnC()
            .id(intCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .parentHierId(UUID.randomUUID().toString())
            .unitId(UUID.randomUUID().toString());
    }
}
