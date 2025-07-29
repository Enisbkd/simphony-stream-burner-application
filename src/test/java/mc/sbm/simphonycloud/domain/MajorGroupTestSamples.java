package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MajorGroupTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MajorGroup getMajorGroupSample1() {
        return new MajorGroup().id(1L).nom("nom1").nomCourt("nomCourt1").pointDeVenteRef(1);
    }

    public static MajorGroup getMajorGroupSample2() {
        return new MajorGroup().id(2L).nom("nom2").nomCourt("nomCourt2").pointDeVenteRef(2);
    }

    public static MajorGroup getMajorGroupRandomSampleGenerator() {
        return new MajorGroup()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .nomCourt(UUID.randomUUID().toString())
            .pointDeVenteRef(intCount.incrementAndGet());
    }
}
