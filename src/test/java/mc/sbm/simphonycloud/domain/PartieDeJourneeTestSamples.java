package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PartieDeJourneeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PartieDeJournee getPartieDeJourneeSample1() {
        return new PartieDeJournee().id(1L).timeRangeStart("timeRangeStart1").timeRangeEnd("timeRangeEnd1").nom("nom1");
    }

    public static PartieDeJournee getPartieDeJourneeSample2() {
        return new PartieDeJournee().id(2L).timeRangeStart("timeRangeStart2").timeRangeEnd("timeRangeEnd2").nom("nom2");
    }

    public static PartieDeJournee getPartieDeJourneeRandomSampleGenerator() {
        return new PartieDeJournee()
            .id(longCount.incrementAndGet())
            .timeRangeStart(UUID.randomUUID().toString())
            .timeRangeEnd(UUID.randomUUID().toString())
            .nom(UUID.randomUUID().toString());
    }
}
