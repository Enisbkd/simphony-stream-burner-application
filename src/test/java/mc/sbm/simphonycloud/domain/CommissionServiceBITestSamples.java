package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CommissionServiceBITestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static CommissionServiceBI getCommissionServiceBISample1() {
        return new CommissionServiceBI()
            .id(1)
            .nom("nom1")
            .nomCourt("nomCourt1")
            .typeValue("typeValue1")
            .etablissementRef("etablissementRef1");
    }

    public static CommissionServiceBI getCommissionServiceBISample2() {
        return new CommissionServiceBI()
            .id(2)
            .nom("nom2")
            .nomCourt("nomCourt2")
            .typeValue("typeValue2")
            .etablissementRef("etablissementRef2");
    }

    public static CommissionServiceBI getCommissionServiceBIRandomSampleGenerator() {
        return new CommissionServiceBI()
            .id(intCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .nomCourt(UUID.randomUUID().toString())
            .typeValue(UUID.randomUUID().toString())
            .etablissementRef(UUID.randomUUID().toString());
    }
}
