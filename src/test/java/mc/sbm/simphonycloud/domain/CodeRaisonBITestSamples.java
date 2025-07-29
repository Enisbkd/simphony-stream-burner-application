package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CodeRaisonBITestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static CodeRaisonBI getCodeRaisonBISample1() {
        return new CodeRaisonBI().id(1).nomCourt(1).nomMstr("nomMstr1").numMstr(1).name("name1").etablissementRef("etablissementRef1");
    }

    public static CodeRaisonBI getCodeRaisonBISample2() {
        return new CodeRaisonBI().id(2).nomCourt(2).nomMstr("nomMstr2").numMstr(2).name("name2").etablissementRef("etablissementRef2");
    }

    public static CodeRaisonBI getCodeRaisonBIRandomSampleGenerator() {
        return new CodeRaisonBI()
            .id(intCount.incrementAndGet())
            .nomCourt(intCount.incrementAndGet())
            .nomMstr(UUID.randomUUID().toString())
            .numMstr(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .etablissementRef(UUID.randomUUID().toString());
    }
}
