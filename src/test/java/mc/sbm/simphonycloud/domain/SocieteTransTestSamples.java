package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SocieteTransTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SocieteTrans getSocieteTransSample1() {
        return new SocieteTrans().id(1L).nom("nom1").nomCourt("nomCourt1");
    }

    public static SocieteTrans getSocieteTransSample2() {
        return new SocieteTrans().id(2L).nom("nom2").nomCourt("nomCourt2");
    }

    public static SocieteTrans getSocieteTransRandomSampleGenerator() {
        return new SocieteTrans().id(longCount.incrementAndGet()).nom(UUID.randomUUID().toString()).nomCourt(UUID.randomUUID().toString());
    }
}
