package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class OrganizationLocationTransTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static OrganizationLocationTrans getOrganizationLocationTransSample1() {
        return new OrganizationLocationTrans()
            .id(1L)
            .orgShortName("orgShortName1")
            .locRef("locRef1")
            .name("name1")
            .currency("currency1")
            .phoneNumber("phoneNumber1")
            .languages("languages1")
            .timezoneIanaName("timezoneIanaName1")
            .timezoneWindowsName("timezoneWindowsName1")
            .timezoneTzIndex(1)
            .addressLine1("addressLine11")
            .addressLine2("addressLine21")
            .addressFloor("addressFloor1")
            .addressLocality("addressLocality1")
            .addressRegion("addressRegion1")
            .addressPostalCode("addressPostalCode1")
            .addressCountry("addressCountry1")
            .addressNotes("addressNotes1")
            .posPlatformName("posPlatformName1")
            .posPlatformVersion("posPlatformVersion1");
    }

    public static OrganizationLocationTrans getOrganizationLocationTransSample2() {
        return new OrganizationLocationTrans()
            .id(2L)
            .orgShortName("orgShortName2")
            .locRef("locRef2")
            .name("name2")
            .currency("currency2")
            .phoneNumber("phoneNumber2")
            .languages("languages2")
            .timezoneIanaName("timezoneIanaName2")
            .timezoneWindowsName("timezoneWindowsName2")
            .timezoneTzIndex(2)
            .addressLine1("addressLine12")
            .addressLine2("addressLine22")
            .addressFloor("addressFloor2")
            .addressLocality("addressLocality2")
            .addressRegion("addressRegion2")
            .addressPostalCode("addressPostalCode2")
            .addressCountry("addressCountry2")
            .addressNotes("addressNotes2")
            .posPlatformName("posPlatformName2")
            .posPlatformVersion("posPlatformVersion2");
    }

    public static OrganizationLocationTrans getOrganizationLocationTransRandomSampleGenerator() {
        return new OrganizationLocationTrans()
            .id(longCount.incrementAndGet())
            .orgShortName(UUID.randomUUID().toString())
            .locRef(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .currency(UUID.randomUUID().toString())
            .phoneNumber(UUID.randomUUID().toString())
            .languages(UUID.randomUUID().toString())
            .timezoneIanaName(UUID.randomUUID().toString())
            .timezoneWindowsName(UUID.randomUUID().toString())
            .timezoneTzIndex(intCount.incrementAndGet())
            .addressLine1(UUID.randomUUID().toString())
            .addressLine2(UUID.randomUUID().toString())
            .addressFloor(UUID.randomUUID().toString())
            .addressLocality(UUID.randomUUID().toString())
            .addressRegion(UUID.randomUUID().toString())
            .addressPostalCode(UUID.randomUUID().toString())
            .addressCountry(UUID.randomUUID().toString())
            .addressNotes(UUID.randomUUID().toString())
            .posPlatformName(UUID.randomUUID().toString())
            .posPlatformVersion(UUID.randomUUID().toString());
    }
}
