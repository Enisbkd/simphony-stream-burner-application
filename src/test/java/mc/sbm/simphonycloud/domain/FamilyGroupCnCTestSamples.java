package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FamilyGroupCnCTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static FamilyGroupCnC getFamilyGroupCnCSample1() {
        return new FamilyGroupCnC().id(1L).nom("nom1").nomCourt("nomCourt1").majorGroupRef(1);
    }

    public static FamilyGroupCnC getFamilyGroupCnCSample2() {
        return new FamilyGroupCnC().id(2L).nom("nom2").nomCourt("nomCourt2").majorGroupRef(2);
    }

    public static FamilyGroupCnC getFamilyGroupCnCRandomSampleGenerator() {
        return new FamilyGroupCnC()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .nomCourt(UUID.randomUUID().toString())
            .majorGroupRef(intCount.incrementAndGet());
    }
}
