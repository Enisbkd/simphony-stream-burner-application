package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HierarchieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Hierarchie getHierarchieSample1() {
        return new Hierarchie().id(1L).nom("nom1").parentHierId("parentHierId1").unitId("unitId1");
    }

    public static Hierarchie getHierarchieSample2() {
        return new Hierarchie().id(2L).nom("nom2").parentHierId("parentHierId2").unitId("unitId2");
    }

    public static Hierarchie getHierarchieRandomSampleGenerator() {
        return new Hierarchie()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .parentHierId(UUID.randomUUID().toString())
            .unitId(UUID.randomUUID().toString());
    }
}
