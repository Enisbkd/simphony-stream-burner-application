package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class FamilyGroupCnCTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static FamilyGroupCnC getFamilyGroupCnCSample1() {
        return new FamilyGroupCnC().id(1).nom("nom1").nomCourt("nomCourt1").majorGroupRef(1);
    }

    public static FamilyGroupCnC getFamilyGroupCnCSample2() {
        return new FamilyGroupCnC().id(2).nom("nom2").nomCourt("nomCourt2").majorGroupRef(2);
    }

    public static FamilyGroupCnC getFamilyGroupCnCRandomSampleGenerator() {
        return new FamilyGroupCnC()
            .id(intCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .nomCourt(UUID.randomUUID().toString())
            .majorGroupRef(intCount.incrementAndGet());
    }
}
