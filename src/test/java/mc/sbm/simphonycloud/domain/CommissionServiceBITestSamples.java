package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CommissionServiceBITestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CommissionServiceBI getCommissionServiceBISample1() {
        return new CommissionServiceBI()
            .id(1L)
            .nom("nom1")
            .nomCourt("nomCourt1")
            .typeValue("typeValue1")
            .etablissementRef("etablissementRef1");
    }

    public static CommissionServiceBI getCommissionServiceBISample2() {
        return new CommissionServiceBI()
            .id(2L)
            .nom("nom2")
            .nomCourt("nomCourt2")
            .typeValue("typeValue2")
            .etablissementRef("etablissementRef2");
    }

    public static CommissionServiceBI getCommissionServiceBIRandomSampleGenerator() {
        return new CommissionServiceBI()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .nomCourt(UUID.randomUUID().toString())
            .typeValue(UUID.randomUUID().toString())
            .etablissementRef(UUID.randomUUID().toString());
    }
}
