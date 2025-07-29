package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DetailLineBITestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static DetailLineBI getDetailLineBISample1() {
        return new DetailLineBI()
            .id(1)
            .guestCheckLineItemId(1L)
            .seatNum(1)
            .prcLvl(1)
            .dspQty(1)
            .rsnCodeNum(1)
            .refInfo1("refInfo11")
            .refInfo2("refInfo21")
            .svcRndNum(1)
            .parDtlId(1)
            .chkEmpId(1)
            .transEmpNum(1)
            .mgrEmpNum(1)
            .mealEmpNum(1)
            .dscNum(1)
            .dscMiNum(1)
            .svcChgNum(1)
            .tmedNum(1)
            .miNum(1);
    }

    public static DetailLineBI getDetailLineBISample2() {
        return new DetailLineBI()
            .id(2)
            .guestCheckLineItemId(2L)
            .seatNum(2)
            .prcLvl(2)
            .dspQty(2)
            .rsnCodeNum(2)
            .refInfo1("refInfo12")
            .refInfo2("refInfo22")
            .svcRndNum(2)
            .parDtlId(2)
            .chkEmpId(2)
            .transEmpNum(2)
            .mgrEmpNum(2)
            .mealEmpNum(2)
            .dscNum(2)
            .dscMiNum(2)
            .svcChgNum(2)
            .tmedNum(2)
            .miNum(2);
    }

    public static DetailLineBI getDetailLineBIRandomSampleGenerator() {
        return new DetailLineBI()
            .id(intCount.incrementAndGet())
            .guestCheckLineItemId(longCount.incrementAndGet())
            .seatNum(intCount.incrementAndGet())
            .prcLvl(intCount.incrementAndGet())
            .dspQty(intCount.incrementAndGet())
            .rsnCodeNum(intCount.incrementAndGet())
            .refInfo1(UUID.randomUUID().toString())
            .refInfo2(UUID.randomUUID().toString())
            .svcRndNum(intCount.incrementAndGet())
            .parDtlId(intCount.incrementAndGet())
            .chkEmpId(intCount.incrementAndGet())
            .transEmpNum(intCount.incrementAndGet())
            .mgrEmpNum(intCount.incrementAndGet())
            .mealEmpNum(intCount.incrementAndGet())
            .dscNum(intCount.incrementAndGet())
            .dscMiNum(intCount.incrementAndGet())
            .svcChgNum(intCount.incrementAndGet())
            .tmedNum(intCount.incrementAndGet())
            .miNum(intCount.incrementAndGet());
    }
}
