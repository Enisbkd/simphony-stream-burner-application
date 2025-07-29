package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuItemDefinitionsCnCTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MenuItemDefinitionsCnC getMenuItemDefinitionsCnCSample1() {
        return new MenuItemDefinitionsCnC()
            .id(1)
            .hierUnitId(1)
            .menuItemMasterObjNum(1)
            .menuItemMasterId(1)
            .menuItemDefinitionId(1)
            .defSequenceNum(1)
            .menuItemClassObjNum(1)
            .overridePrintClassObjNum(1)
            .mainLevel("mainLevel1")
            .subLevel("subLevel1")
            .quantity(1)
            .prefixLevelOverride(1)
            .guestCount(1)
            .slu1ObjNum("slu1ObjNum1")
            .slu2ObjNum("slu2ObjNum1")
            .slu3ObjNum("slu3ObjNum1")
            .slu4ObjNum("slu4ObjNum1")
            .slu5ObjNum("slu5ObjNum1")
            .slu6ObjNum("slu6ObjNum1")
            .slu7ObjNum("slu7ObjNum1")
            .slu8ObjNum("slu8ObjNum1")
            .firstName("firstName1");
    }

    public static MenuItemDefinitionsCnC getMenuItemDefinitionsCnCSample2() {
        return new MenuItemDefinitionsCnC()
            .id(2)
            .hierUnitId(2)
            .menuItemMasterObjNum(2)
            .menuItemMasterId(2)
            .menuItemDefinitionId(2)
            .defSequenceNum(2)
            .menuItemClassObjNum(2)
            .overridePrintClassObjNum(2)
            .mainLevel("mainLevel2")
            .subLevel("subLevel2")
            .quantity(2)
            .prefixLevelOverride(2)
            .guestCount(2)
            .slu1ObjNum("slu1ObjNum2")
            .slu2ObjNum("slu2ObjNum2")
            .slu3ObjNum("slu3ObjNum2")
            .slu4ObjNum("slu4ObjNum2")
            .slu5ObjNum("slu5ObjNum2")
            .slu6ObjNum("slu6ObjNum2")
            .slu7ObjNum("slu7ObjNum2")
            .slu8ObjNum("slu8ObjNum2")
            .firstName("firstName2");
    }

    public static MenuItemDefinitionsCnC getMenuItemDefinitionsCnCRandomSampleGenerator() {
        return new MenuItemDefinitionsCnC()
            .id(intCount.incrementAndGet())
            .hierUnitId(intCount.incrementAndGet())
            .menuItemMasterObjNum(intCount.incrementAndGet())
            .menuItemMasterId(intCount.incrementAndGet())
            .menuItemDefinitionId(intCount.incrementAndGet())
            .defSequenceNum(intCount.incrementAndGet())
            .menuItemClassObjNum(intCount.incrementAndGet())
            .overridePrintClassObjNum(intCount.incrementAndGet())
            .mainLevel(UUID.randomUUID().toString())
            .subLevel(UUID.randomUUID().toString())
            .quantity(intCount.incrementAndGet())
            .prefixLevelOverride(intCount.incrementAndGet())
            .guestCount(intCount.incrementAndGet())
            .slu1ObjNum(UUID.randomUUID().toString())
            .slu2ObjNum(UUID.randomUUID().toString())
            .slu3ObjNum(UUID.randomUUID().toString())
            .slu4ObjNum(UUID.randomUUID().toString())
            .slu5ObjNum(UUID.randomUUID().toString())
            .slu6ObjNum(UUID.randomUUID().toString())
            .slu7ObjNum(UUID.randomUUID().toString())
            .slu8ObjNum(UUID.randomUUID().toString())
            .firstName(UUID.randomUUID().toString());
    }
}
