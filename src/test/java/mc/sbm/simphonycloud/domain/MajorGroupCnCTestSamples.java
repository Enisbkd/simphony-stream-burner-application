package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MajorGroupCnCTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MajorGroupCnC getMajorGroupCnCSample1() {
        return new MajorGroupCnC().id(1).nom("nom1").nomCourt("nomCourt1").pointDeVenteRef(1);
    }

    public static MajorGroupCnC getMajorGroupCnCSample2() {
        return new MajorGroupCnC().id(2).nom("nom2").nomCourt("nomCourt2").pointDeVenteRef(2);
    }

    public static MajorGroupCnC getMajorGroupCnCRandomSampleGenerator() {
        return new MajorGroupCnC()
            .id(intCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .nomCourt(UUID.randomUUID().toString())
            .pointDeVenteRef(intCount.incrementAndGet());
    }
}
