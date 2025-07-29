package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuItemMastersCnCTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MenuItemMastersCnC getMenuItemMastersCnCSample1() {
        return new MenuItemMastersCnC()
            .id(1)
            .hierUnitId(1)
            .menuItemMasterId(1)
            .familyGroupObjectNum(1)
            .majorGroupObjectNum(1)
            .reportGroupObjectNum(1)
            .externalReference1("externalReference11")
            .externalReference2("externalReference21")
            .objectNum(1)
            .name("name1");
    }

    public static MenuItemMastersCnC getMenuItemMastersCnCSample2() {
        return new MenuItemMastersCnC()
            .id(2)
            .hierUnitId(2)
            .menuItemMasterId(2)
            .familyGroupObjectNum(2)
            .majorGroupObjectNum(2)
            .reportGroupObjectNum(2)
            .externalReference1("externalReference12")
            .externalReference2("externalReference22")
            .objectNum(2)
            .name("name2");
    }

    public static MenuItemMastersCnC getMenuItemMastersCnCRandomSampleGenerator() {
        return new MenuItemMastersCnC()
            .id(intCount.incrementAndGet())
            .hierUnitId(intCount.incrementAndGet())
            .menuItemMasterId(intCount.incrementAndGet())
            .familyGroupObjectNum(intCount.incrementAndGet())
            .majorGroupObjectNum(intCount.incrementAndGet())
            .reportGroupObjectNum(intCount.incrementAndGet())
            .externalReference1(UUID.randomUUID().toString())
            .externalReference2(UUID.randomUUID().toString())
            .objectNum(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString());
    }
}
