package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SocieteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Societe getSocieteSample1() {
        return new Societe().id(1L).nom("nom1").nomCourt("nomCourt1");
    }

    public static Societe getSocieteSample2() {
        return new Societe().id(2L).nom("nom2").nomCourt("nomCourt2");
    }

    public static Societe getSocieteRandomSampleGenerator() {
        return new Societe().id(longCount.incrementAndGet()).nom(UUID.randomUUID().toString()).nomCourt(UUID.randomUUID().toString());
    }
}
