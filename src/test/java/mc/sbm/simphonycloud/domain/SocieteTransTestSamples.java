package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class SocieteTransTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static SocieteTrans getSocieteTransSample1() {
        return new SocieteTrans().id(1).nom("nom1").nomCourt("nomCourt1");
    }

    public static SocieteTrans getSocieteTransSample2() {
        return new SocieteTrans().id(2).nom("nom2").nomCourt("nomCourt2");
    }

    public static SocieteTrans getSocieteTransRandomSampleGenerator() {
        return new SocieteTrans().id(intCount.incrementAndGet()).nom(UUID.randomUUID().toString()).nomCourt(UUID.randomUUID().toString());
    }
}
