package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderTypeBITestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static OrderTypeBI getOrderTypeBISample1() {
        return new OrderTypeBI()
            .id(1)
            .num(1)
            .locRef("locRef1")
            .name("name1")
            .mstrNum(1)
            .mstrName("mstrName1")
            .catGrpHierName1("catGrpHierName11")
            .catGrpName1("catGrpName11")
            .catGrpHierName2("catGrpHierName21")
            .catGrpName2("catGrpName21")
            .catGrpHierName3("catGrpHierName31")
            .catGrpName3("catGrpName31")
            .catGrpHierName4("catGrpHierName41")
            .catGrpName4("catGrpName41");
    }

    public static OrderTypeBI getOrderTypeBISample2() {
        return new OrderTypeBI()
            .id(2)
            .num(2)
            .locRef("locRef2")
            .name("name2")
            .mstrNum(2)
            .mstrName("mstrName2")
            .catGrpHierName1("catGrpHierName12")
            .catGrpName1("catGrpName12")
            .catGrpHierName2("catGrpHierName22")
            .catGrpName2("catGrpName22")
            .catGrpHierName3("catGrpHierName32")
            .catGrpName3("catGrpName32")
            .catGrpHierName4("catGrpHierName42")
            .catGrpName4("catGrpName42");
    }

    public static OrderTypeBI getOrderTypeBIRandomSampleGenerator() {
        return new OrderTypeBI()
            .id(intCount.incrementAndGet())
            .num(intCount.incrementAndGet())
            .locRef(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .mstrNum(intCount.incrementAndGet())
            .mstrName(UUID.randomUUID().toString())
            .catGrpHierName1(UUID.randomUUID().toString())
            .catGrpName1(UUID.randomUUID().toString())
            .catGrpHierName2(UUID.randomUUID().toString())
            .catGrpName2(UUID.randomUUID().toString())
            .catGrpHierName3(UUID.randomUUID().toString())
            .catGrpName3(UUID.randomUUID().toString())
            .catGrpHierName4(UUID.randomUUID().toString())
            .catGrpName4(UUID.randomUUID().toString());
    }
}
