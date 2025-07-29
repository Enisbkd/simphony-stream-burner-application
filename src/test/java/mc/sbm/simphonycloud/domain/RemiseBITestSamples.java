package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RemiseBITestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RemiseBI getRemiseBISample1() {
        return new RemiseBI().id(1L).num(1).name("name1").rptGrpNum(1).rptGrpName("rptGrpName1").locRef("locRef1");
    }

    public static RemiseBI getRemiseBISample2() {
        return new RemiseBI().id(2L).num(2).name("name2").rptGrpNum(2).rptGrpName("rptGrpName2").locRef("locRef2");
    }

    public static RemiseBI getRemiseBIRandomSampleGenerator() {
        return new RemiseBI()
            .id(longCount.incrementAndGet())
            .num(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .rptGrpNum(intCount.incrementAndGet())
            .rptGrpName(UUID.randomUUID().toString())
            .locRef(UUID.randomUUID().toString());
    }
}
