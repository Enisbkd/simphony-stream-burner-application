package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MenuItemPricesCnCTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MenuItemPricesCnC getMenuItemPricesCnCSample1() {
        return new MenuItemPricesCnC()
            .id(1L)
            .hierUnitId(1L)
            .menuItemPriceId(1L)
            .menuItemMasterId(1L)
            .menuItemMasterObjNum(1L)
            .menuItemDefinitionId(1L)
            .defSequenceNum(1)
            .externalReference1("externalReference11")
            .externalReference2("externalReference21")
            .priceSequenceNum(1)
            .activeOnMenuLevel(1)
            .effectivityGroupObjNum("effectivityGroupObjNum1")
            .options("options1");
    }

    public static MenuItemPricesCnC getMenuItemPricesCnCSample2() {
        return new MenuItemPricesCnC()
            .id(2L)
            .hierUnitId(2L)
            .menuItemPriceId(2L)
            .menuItemMasterId(2L)
            .menuItemMasterObjNum(2L)
            .menuItemDefinitionId(2L)
            .defSequenceNum(2)
            .externalReference1("externalReference12")
            .externalReference2("externalReference22")
            .priceSequenceNum(2)
            .activeOnMenuLevel(2)
            .effectivityGroupObjNum("effectivityGroupObjNum2")
            .options("options2");
    }

    public static MenuItemPricesCnC getMenuItemPricesCnCRandomSampleGenerator() {
        return new MenuItemPricesCnC()
            .id(longCount.incrementAndGet())
            .hierUnitId(longCount.incrementAndGet())
            .menuItemPriceId(longCount.incrementAndGet())
            .menuItemMasterId(longCount.incrementAndGet())
            .menuItemMasterObjNum(longCount.incrementAndGet())
            .menuItemDefinitionId(longCount.incrementAndGet())
            .defSequenceNum(intCount.incrementAndGet())
            .externalReference1(UUID.randomUUID().toString())
            .externalReference2(UUID.randomUUID().toString())
            .priceSequenceNum(intCount.incrementAndGet())
            .activeOnMenuLevel(intCount.incrementAndGet())
            .effectivityGroupObjNum(UUID.randomUUID().toString())
            .options(UUID.randomUUID().toString());
    }
}
