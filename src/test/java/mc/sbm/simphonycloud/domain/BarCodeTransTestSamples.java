package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BarCodeTransTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static BarCodeTrans getBarCodeTransSample1() {
        return new BarCodeTrans()
            .id(1L)
            .locRef("locRef1")
            .rvcRef(1)
            .barcodeId(1)
            .barcode("barcode1")
            .menuItemId(1)
            .defenitionSequence(1)
            .priceSequence(1);
    }

    public static BarCodeTrans getBarCodeTransSample2() {
        return new BarCodeTrans()
            .id(2L)
            .locRef("locRef2")
            .rvcRef(2)
            .barcodeId(2)
            .barcode("barcode2")
            .menuItemId(2)
            .defenitionSequence(2)
            .priceSequence(2);
    }

    public static BarCodeTrans getBarCodeTransRandomSampleGenerator() {
        return new BarCodeTrans()
            .id(longCount.incrementAndGet())
            .locRef(UUID.randomUUID().toString())
            .rvcRef(intCount.incrementAndGet())
            .barcodeId(intCount.incrementAndGet())
            .barcode(UUID.randomUUID().toString())
            .menuItemId(intCount.incrementAndGet())
            .defenitionSequence(intCount.incrementAndGet())
            .priceSequence(intCount.incrementAndGet());
    }
}
