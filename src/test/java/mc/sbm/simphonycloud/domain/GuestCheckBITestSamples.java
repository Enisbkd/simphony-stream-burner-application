package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class GuestCheckBITestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static GuestCheckBI getGuestCheckBISample1() {
        return new GuestCheckBI()
            .id(1)
            .guestCheckId(1)
            .chkNum(1)
            .gstCnt(1)
            .tblNum(1)
            .xferToChkNum(1)
            .xferStatus("xferStatus1")
            .otNum(1)
            .locRef("locRef1");
    }

    public static GuestCheckBI getGuestCheckBISample2() {
        return new GuestCheckBI()
            .id(2)
            .guestCheckId(2)
            .chkNum(2)
            .gstCnt(2)
            .tblNum(2)
            .xferToChkNum(2)
            .xferStatus("xferStatus2")
            .otNum(2)
            .locRef("locRef2");
    }

    public static GuestCheckBI getGuestCheckBIRandomSampleGenerator() {
        return new GuestCheckBI()
            .id(intCount.incrementAndGet())
            .guestCheckId(intCount.incrementAndGet())
            .chkNum(intCount.incrementAndGet())
            .gstCnt(intCount.incrementAndGet())
            .tblNum(intCount.incrementAndGet())
            .xferToChkNum(intCount.incrementAndGet())
            .xferStatus(UUID.randomUUID().toString())
            .otNum(intCount.incrementAndGet())
            .locRef(UUID.randomUUID().toString());
    }
}
