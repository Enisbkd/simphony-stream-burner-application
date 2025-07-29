package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LocationCnC.
 */
@Entity
@Table(name = "location_cn_c")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationCnC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "hier_unit_id")
    private Integer hierUnitId;

    @Column(name = "tz_index")
    private Integer tzIndex;

    @Column(name = "tz_name")
    private String tzName;

    @Column(name = "locale_info_id")
    private Integer localeInfoId;

    @Column(name = "name")
    private String name;

    @Column(name = "reporting_loc_name")
    private String reportingLocName;

    @Column(name = "loc_ref")
    private String locRef;

    @Column(name = "reporting_parent_enterprise_level_name")
    private String reportingParentEnterpriseLevelName;

    @Column(name = "object_num")
    private Integer objectNum;

    @Column(name = "sbm_pms_ifc_ip")
    private String sbmPmsIfcIp;

    @Column(name = "sbm_pms_ifc_port")
    private String sbmPmsIfcPort;

    @Column(name = "sbm_prive_room_start")
    private String sbmPriveRoomStart;

    @Column(name = "sbm_prive_room_end")
    private String sbmPriveRoomEnd;

    @Column(name = "sbm_pms_send_all_details")
    private String sbmPmsSendAllDetails;

    @Column(name = "sbm_pms_send_full_dscv")
    private String sbmPmsSendFullDscv;

    @Column(name = "sbm_pms_send_64_tax")
    private String sbmPmsSend64Tax;

    @Column(name = "sbm_card_payment_url")
    private String sbmCardPaymentUrl;

    @Column(name = "sbm_check_hotel_data_url")
    private String sbmCheckHotelDataUrl;

    @Column(name = "sbm_voucher_svc_url")
    private String sbmVoucherSvcUrl;

    @Column(name = "sbm_voucher_inv_pm")
    private String sbmVoucherInvPm;

    @Column(name = "sbm_voucher_corp_pm")
    private String sbmVoucherCorpPm;

    @Column(name = "sbm_voucher_reward_pm")
    private String sbmVoucherRewardPm;

    @Column(name = "sbm_voucher_mc_pm")
    private String sbmVoucherMcPm;

    @Column(name = "sbm_pms_ifc_port_2")
    private String sbmPmsIfcPort2;

    @Column(name = "sbm_pms_ifc_port_3")
    private String sbmPmsIfcPort3;

    @Column(name = "sbm_pms_ifc_port_4")
    private String sbmPmsIfcPort4;

    @Column(name = "sbm_timeout")
    private String sbmTimeout;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public LocationCnC id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHierUnitId() {
        return this.hierUnitId;
    }

    public LocationCnC hierUnitId(Integer hierUnitId) {
        this.setHierUnitId(hierUnitId);
        return this;
    }

    public void setHierUnitId(Integer hierUnitId) {
        this.hierUnitId = hierUnitId;
    }

    public Integer getTzIndex() {
        return this.tzIndex;
    }

    public LocationCnC tzIndex(Integer tzIndex) {
        this.setTzIndex(tzIndex);
        return this;
    }

    public void setTzIndex(Integer tzIndex) {
        this.tzIndex = tzIndex;
    }

    public String getTzName() {
        return this.tzName;
    }

    public LocationCnC tzName(String tzName) {
        this.setTzName(tzName);
        return this;
    }

    public void setTzName(String tzName) {
        this.tzName = tzName;
    }

    public Integer getLocaleInfoId() {
        return this.localeInfoId;
    }

    public LocationCnC localeInfoId(Integer localeInfoId) {
        this.setLocaleInfoId(localeInfoId);
        return this;
    }

    public void setLocaleInfoId(Integer localeInfoId) {
        this.localeInfoId = localeInfoId;
    }

    public String getName() {
        return this.name;
    }

    public LocationCnC name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReportingLocName() {
        return this.reportingLocName;
    }

    public LocationCnC reportingLocName(String reportingLocName) {
        this.setReportingLocName(reportingLocName);
        return this;
    }

    public void setReportingLocName(String reportingLocName) {
        this.reportingLocName = reportingLocName;
    }

    public String getLocRef() {
        return this.locRef;
    }

    public LocationCnC locRef(String locRef) {
        this.setLocRef(locRef);
        return this;
    }

    public void setLocRef(String locRef) {
        this.locRef = locRef;
    }

    public String getReportingParentEnterpriseLevelName() {
        return this.reportingParentEnterpriseLevelName;
    }

    public LocationCnC reportingParentEnterpriseLevelName(String reportingParentEnterpriseLevelName) {
        this.setReportingParentEnterpriseLevelName(reportingParentEnterpriseLevelName);
        return this;
    }

    public void setReportingParentEnterpriseLevelName(String reportingParentEnterpriseLevelName) {
        this.reportingParentEnterpriseLevelName = reportingParentEnterpriseLevelName;
    }

    public Integer getObjectNum() {
        return this.objectNum;
    }

    public LocationCnC objectNum(Integer objectNum) {
        this.setObjectNum(objectNum);
        return this;
    }

    public void setObjectNum(Integer objectNum) {
        this.objectNum = objectNum;
    }

    public String getSbmPmsIfcIp() {
        return this.sbmPmsIfcIp;
    }

    public LocationCnC sbmPmsIfcIp(String sbmPmsIfcIp) {
        this.setSbmPmsIfcIp(sbmPmsIfcIp);
        return this;
    }

    public void setSbmPmsIfcIp(String sbmPmsIfcIp) {
        this.sbmPmsIfcIp = sbmPmsIfcIp;
    }

    public String getSbmPmsIfcPort() {
        return this.sbmPmsIfcPort;
    }

    public LocationCnC sbmPmsIfcPort(String sbmPmsIfcPort) {
        this.setSbmPmsIfcPort(sbmPmsIfcPort);
        return this;
    }

    public void setSbmPmsIfcPort(String sbmPmsIfcPort) {
        this.sbmPmsIfcPort = sbmPmsIfcPort;
    }

    public String getSbmPriveRoomStart() {
        return this.sbmPriveRoomStart;
    }

    public LocationCnC sbmPriveRoomStart(String sbmPriveRoomStart) {
        this.setSbmPriveRoomStart(sbmPriveRoomStart);
        return this;
    }

    public void setSbmPriveRoomStart(String sbmPriveRoomStart) {
        this.sbmPriveRoomStart = sbmPriveRoomStart;
    }

    public String getSbmPriveRoomEnd() {
        return this.sbmPriveRoomEnd;
    }

    public LocationCnC sbmPriveRoomEnd(String sbmPriveRoomEnd) {
        this.setSbmPriveRoomEnd(sbmPriveRoomEnd);
        return this;
    }

    public void setSbmPriveRoomEnd(String sbmPriveRoomEnd) {
        this.sbmPriveRoomEnd = sbmPriveRoomEnd;
    }

    public String getSbmPmsSendAllDetails() {
        return this.sbmPmsSendAllDetails;
    }

    public LocationCnC sbmPmsSendAllDetails(String sbmPmsSendAllDetails) {
        this.setSbmPmsSendAllDetails(sbmPmsSendAllDetails);
        return this;
    }

    public void setSbmPmsSendAllDetails(String sbmPmsSendAllDetails) {
        this.sbmPmsSendAllDetails = sbmPmsSendAllDetails;
    }

    public String getSbmPmsSendFullDscv() {
        return this.sbmPmsSendFullDscv;
    }

    public LocationCnC sbmPmsSendFullDscv(String sbmPmsSendFullDscv) {
        this.setSbmPmsSendFullDscv(sbmPmsSendFullDscv);
        return this;
    }

    public void setSbmPmsSendFullDscv(String sbmPmsSendFullDscv) {
        this.sbmPmsSendFullDscv = sbmPmsSendFullDscv;
    }

    public String getSbmPmsSend64Tax() {
        return this.sbmPmsSend64Tax;
    }

    public LocationCnC sbmPmsSend64Tax(String sbmPmsSend64Tax) {
        this.setSbmPmsSend64Tax(sbmPmsSend64Tax);
        return this;
    }

    public void setSbmPmsSend64Tax(String sbmPmsSend64Tax) {
        this.sbmPmsSend64Tax = sbmPmsSend64Tax;
    }

    public String getSbmCardPaymentUrl() {
        return this.sbmCardPaymentUrl;
    }

    public LocationCnC sbmCardPaymentUrl(String sbmCardPaymentUrl) {
        this.setSbmCardPaymentUrl(sbmCardPaymentUrl);
        return this;
    }

    public void setSbmCardPaymentUrl(String sbmCardPaymentUrl) {
        this.sbmCardPaymentUrl = sbmCardPaymentUrl;
    }

    public String getSbmCheckHotelDataUrl() {
        return this.sbmCheckHotelDataUrl;
    }

    public LocationCnC sbmCheckHotelDataUrl(String sbmCheckHotelDataUrl) {
        this.setSbmCheckHotelDataUrl(sbmCheckHotelDataUrl);
        return this;
    }

    public void setSbmCheckHotelDataUrl(String sbmCheckHotelDataUrl) {
        this.sbmCheckHotelDataUrl = sbmCheckHotelDataUrl;
    }

    public String getSbmVoucherSvcUrl() {
        return this.sbmVoucherSvcUrl;
    }

    public LocationCnC sbmVoucherSvcUrl(String sbmVoucherSvcUrl) {
        this.setSbmVoucherSvcUrl(sbmVoucherSvcUrl);
        return this;
    }

    public void setSbmVoucherSvcUrl(String sbmVoucherSvcUrl) {
        this.sbmVoucherSvcUrl = sbmVoucherSvcUrl;
    }

    public String getSbmVoucherInvPm() {
        return this.sbmVoucherInvPm;
    }

    public LocationCnC sbmVoucherInvPm(String sbmVoucherInvPm) {
        this.setSbmVoucherInvPm(sbmVoucherInvPm);
        return this;
    }

    public void setSbmVoucherInvPm(String sbmVoucherInvPm) {
        this.sbmVoucherInvPm = sbmVoucherInvPm;
    }

    public String getSbmVoucherCorpPm() {
        return this.sbmVoucherCorpPm;
    }

    public LocationCnC sbmVoucherCorpPm(String sbmVoucherCorpPm) {
        this.setSbmVoucherCorpPm(sbmVoucherCorpPm);
        return this;
    }

    public void setSbmVoucherCorpPm(String sbmVoucherCorpPm) {
        this.sbmVoucherCorpPm = sbmVoucherCorpPm;
    }

    public String getSbmVoucherRewardPm() {
        return this.sbmVoucherRewardPm;
    }

    public LocationCnC sbmVoucherRewardPm(String sbmVoucherRewardPm) {
        this.setSbmVoucherRewardPm(sbmVoucherRewardPm);
        return this;
    }

    public void setSbmVoucherRewardPm(String sbmVoucherRewardPm) {
        this.sbmVoucherRewardPm = sbmVoucherRewardPm;
    }

    public String getSbmVoucherMcPm() {
        return this.sbmVoucherMcPm;
    }

    public LocationCnC sbmVoucherMcPm(String sbmVoucherMcPm) {
        this.setSbmVoucherMcPm(sbmVoucherMcPm);
        return this;
    }

    public void setSbmVoucherMcPm(String sbmVoucherMcPm) {
        this.sbmVoucherMcPm = sbmVoucherMcPm;
    }

    public String getSbmPmsIfcPort2() {
        return this.sbmPmsIfcPort2;
    }

    public LocationCnC sbmPmsIfcPort2(String sbmPmsIfcPort2) {
        this.setSbmPmsIfcPort2(sbmPmsIfcPort2);
        return this;
    }

    public void setSbmPmsIfcPort2(String sbmPmsIfcPort2) {
        this.sbmPmsIfcPort2 = sbmPmsIfcPort2;
    }

    public String getSbmPmsIfcPort3() {
        return this.sbmPmsIfcPort3;
    }

    public LocationCnC sbmPmsIfcPort3(String sbmPmsIfcPort3) {
        this.setSbmPmsIfcPort3(sbmPmsIfcPort3);
        return this;
    }

    public void setSbmPmsIfcPort3(String sbmPmsIfcPort3) {
        this.sbmPmsIfcPort3 = sbmPmsIfcPort3;
    }

    public String getSbmPmsIfcPort4() {
        return this.sbmPmsIfcPort4;
    }

    public LocationCnC sbmPmsIfcPort4(String sbmPmsIfcPort4) {
        this.setSbmPmsIfcPort4(sbmPmsIfcPort4);
        return this;
    }

    public void setSbmPmsIfcPort4(String sbmPmsIfcPort4) {
        this.sbmPmsIfcPort4 = sbmPmsIfcPort4;
    }

    public String getSbmTimeout() {
        return this.sbmTimeout;
    }

    public LocationCnC sbmTimeout(String sbmTimeout) {
        this.setSbmTimeout(sbmTimeout);
        return this;
    }

    public void setSbmTimeout(String sbmTimeout) {
        this.sbmTimeout = sbmTimeout;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationCnC)) {
            return false;
        }
        return getId() != null && getId().equals(((LocationCnC) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationCnC{" +
            "id=" + getId() +
            ", hierUnitId=" + getHierUnitId() +
            ", tzIndex=" + getTzIndex() +
            ", tzName='" + getTzName() + "'" +
            ", localeInfoId=" + getLocaleInfoId() +
            ", name='" + getName() + "'" +
            ", reportingLocName='" + getReportingLocName() + "'" +
            ", locRef='" + getLocRef() + "'" +
            ", reportingParentEnterpriseLevelName='" + getReportingParentEnterpriseLevelName() + "'" +
            ", objectNum=" + getObjectNum() +
            ", sbmPmsIfcIp='" + getSbmPmsIfcIp() + "'" +
            ", sbmPmsIfcPort='" + getSbmPmsIfcPort() + "'" +
            ", sbmPriveRoomStart='" + getSbmPriveRoomStart() + "'" +
            ", sbmPriveRoomEnd='" + getSbmPriveRoomEnd() + "'" +
            ", sbmPmsSendAllDetails='" + getSbmPmsSendAllDetails() + "'" +
            ", sbmPmsSendFullDscv='" + getSbmPmsSendFullDscv() + "'" +
            ", sbmPmsSend64Tax='" + getSbmPmsSend64Tax() + "'" +
            ", sbmCardPaymentUrl='" + getSbmCardPaymentUrl() + "'" +
            ", sbmCheckHotelDataUrl='" + getSbmCheckHotelDataUrl() + "'" +
            ", sbmVoucherSvcUrl='" + getSbmVoucherSvcUrl() + "'" +
            ", sbmVoucherInvPm='" + getSbmVoucherInvPm() + "'" +
            ", sbmVoucherCorpPm='" + getSbmVoucherCorpPm() + "'" +
            ", sbmVoucherRewardPm='" + getSbmVoucherRewardPm() + "'" +
            ", sbmVoucherMcPm='" + getSbmVoucherMcPm() + "'" +
            ", sbmPmsIfcPort2='" + getSbmPmsIfcPort2() + "'" +
            ", sbmPmsIfcPort3='" + getSbmPmsIfcPort3() + "'" +
            ", sbmPmsIfcPort4='" + getSbmPmsIfcPort4() + "'" +
            ", sbmTimeout='" + getSbmTimeout() + "'" +
            "}";
    }
}
