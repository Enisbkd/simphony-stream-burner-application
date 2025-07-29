package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class LocationCnCTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static LocationCnC getLocationCnCSample1() {
        return new LocationCnC()
            .id(1)
            .hierUnitId(1)
            .tzIndex(1)
            .tzName("tzName1")
            .localeInfoId(1)
            .name("name1")
            .reportingLocName("reportingLocName1")
            .locRef("locRef1")
            .reportingParentEnterpriseLevelName("reportingParentEnterpriseLevelName1")
            .objectNum(1)
            .sbmPmsIfcIp("sbmPmsIfcIp1")
            .sbmPmsIfcPort("sbmPmsIfcPort1")
            .sbmPriveRoomStart("sbmPriveRoomStart1")
            .sbmPriveRoomEnd("sbmPriveRoomEnd1")
            .sbmPmsSendAllDetails("sbmPmsSendAllDetails1")
            .sbmPmsSendFullDscv("sbmPmsSendFullDscv1")
            .sbmPmsSend64Tax("sbmPmsSend64Tax1")
            .sbmCardPaymentUrl("sbmCardPaymentUrl1")
            .sbmCheckHotelDataUrl("sbmCheckHotelDataUrl1")
            .sbmVoucherSvcUrl("sbmVoucherSvcUrl1")
            .sbmVoucherInvPm("sbmVoucherInvPm1")
            .sbmVoucherCorpPm("sbmVoucherCorpPm1")
            .sbmVoucherRewardPm("sbmVoucherRewardPm1")
            .sbmVoucherMcPm("sbmVoucherMcPm1")
            .sbmPmsIfcPort2("sbmPmsIfcPort21")
            .sbmPmsIfcPort3("sbmPmsIfcPort31")
            .sbmPmsIfcPort4("sbmPmsIfcPort41")
            .sbmTimeout("sbmTimeout1");
    }

    public static LocationCnC getLocationCnCSample2() {
        return new LocationCnC()
            .id(2)
            .hierUnitId(2)
            .tzIndex(2)
            .tzName("tzName2")
            .localeInfoId(2)
            .name("name2")
            .reportingLocName("reportingLocName2")
            .locRef("locRef2")
            .reportingParentEnterpriseLevelName("reportingParentEnterpriseLevelName2")
            .objectNum(2)
            .sbmPmsIfcIp("sbmPmsIfcIp2")
            .sbmPmsIfcPort("sbmPmsIfcPort2")
            .sbmPriveRoomStart("sbmPriveRoomStart2")
            .sbmPriveRoomEnd("sbmPriveRoomEnd2")
            .sbmPmsSendAllDetails("sbmPmsSendAllDetails2")
            .sbmPmsSendFullDscv("sbmPmsSendFullDscv2")
            .sbmPmsSend64Tax("sbmPmsSend64Tax2")
            .sbmCardPaymentUrl("sbmCardPaymentUrl2")
            .sbmCheckHotelDataUrl("sbmCheckHotelDataUrl2")
            .sbmVoucherSvcUrl("sbmVoucherSvcUrl2")
            .sbmVoucherInvPm("sbmVoucherInvPm2")
            .sbmVoucherCorpPm("sbmVoucherCorpPm2")
            .sbmVoucherRewardPm("sbmVoucherRewardPm2")
            .sbmVoucherMcPm("sbmVoucherMcPm2")
            .sbmPmsIfcPort2("sbmPmsIfcPort22")
            .sbmPmsIfcPort3("sbmPmsIfcPort32")
            .sbmPmsIfcPort4("sbmPmsIfcPort42")
            .sbmTimeout("sbmTimeout2");
    }

    public static LocationCnC getLocationCnCRandomSampleGenerator() {
        return new LocationCnC()
            .id(intCount.incrementAndGet())
            .hierUnitId(intCount.incrementAndGet())
            .tzIndex(intCount.incrementAndGet())
            .tzName(UUID.randomUUID().toString())
            .localeInfoId(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .reportingLocName(UUID.randomUUID().toString())
            .locRef(UUID.randomUUID().toString())
            .reportingParentEnterpriseLevelName(UUID.randomUUID().toString())
            .objectNum(intCount.incrementAndGet())
            .sbmPmsIfcIp(UUID.randomUUID().toString())
            .sbmPmsIfcPort(UUID.randomUUID().toString())
            .sbmPriveRoomStart(UUID.randomUUID().toString())
            .sbmPriveRoomEnd(UUID.randomUUID().toString())
            .sbmPmsSendAllDetails(UUID.randomUUID().toString())
            .sbmPmsSendFullDscv(UUID.randomUUID().toString())
            .sbmPmsSend64Tax(UUID.randomUUID().toString())
            .sbmCardPaymentUrl(UUID.randomUUID().toString())
            .sbmCheckHotelDataUrl(UUID.randomUUID().toString())
            .sbmVoucherSvcUrl(UUID.randomUUID().toString())
            .sbmVoucherInvPm(UUID.randomUUID().toString())
            .sbmVoucherCorpPm(UUID.randomUUID().toString())
            .sbmVoucherRewardPm(UUID.randomUUID().toString())
            .sbmVoucherMcPm(UUID.randomUUID().toString())
            .sbmPmsIfcPort2(UUID.randomUUID().toString())
            .sbmPmsIfcPort3(UUID.randomUUID().toString())
            .sbmPmsIfcPort4(UUID.randomUUID().toString())
            .sbmTimeout(UUID.randomUUID().toString());
    }
}
