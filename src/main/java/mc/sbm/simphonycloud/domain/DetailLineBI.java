package mc.sbm.simphonycloud.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DetailLineBI.
 */
@Entity
@Table(name = "detail_line_bi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetailLineBI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "guest_check_line_item_id")
    private Long guestCheckLineItemId;

    @Column(name = "detail_utc")
    private Instant detailUTC;

    @Column(name = "detail_lcl")
    private Instant detailLcl;

    @Column(name = "seat_num")
    private Integer seatNum;

    @Column(name = "prc_lvl")
    private Integer prcLvl;

    @Column(name = "dsp_ttl", precision = 21, scale = 2)
    private BigDecimal dspTtl;

    @Column(name = "dsp_qty")
    private Integer dspQty;

    @Column(name = "err_cor_flag")
    private Boolean errCorFlag;

    @Column(name = "vd_flag")
    private Boolean vdFlag;

    @Column(name = "return_flag")
    private Boolean returnFlag;

    @Column(name = "do_not_show_flag")
    private Boolean doNotShowFlag;

    @Column(name = "agg_ttl", precision = 21, scale = 2)
    private BigDecimal aggTtl;

    @Column(name = "rsn_code_num")
    private Integer rsnCodeNum;

    @Column(name = "ref_info_1")
    private String refInfo1;

    @Column(name = "ref_info_2")
    private String refInfo2;

    @Column(name = "svc_rnd_num")
    private Integer svcRndNum;

    @Column(name = "par_dtl_id")
    private Integer parDtlId;

    @Column(name = "chk_emp_id")
    private Integer chkEmpId;

    @Column(name = "trans_emp_num")
    private Integer transEmpNum;

    @Column(name = "mgr_emp_num")
    private Integer mgrEmpNum;

    @Column(name = "meal_emp_num")
    private Integer mealEmpNum;

    @Column(name = "dsc_num")
    private Integer dscNum;

    @Column(name = "dsc_mi_num")
    private Integer dscMiNum;

    @Column(name = "svc_chg_num")
    private Integer svcChgNum;

    @Column(name = "tmed_num")
    private Integer tmedNum;

    @Column(name = "mi_num")
    private Integer miNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "detailLineBILists" }, allowSetters = true)
    private GuestCheckBI guestCheckBI;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DetailLineBI id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGuestCheckLineItemId() {
        return this.guestCheckLineItemId;
    }

    public DetailLineBI guestCheckLineItemId(Long guestCheckLineItemId) {
        this.setGuestCheckLineItemId(guestCheckLineItemId);
        return this;
    }

    public void setGuestCheckLineItemId(Long guestCheckLineItemId) {
        this.guestCheckLineItemId = guestCheckLineItemId;
    }

    public Instant getDetailUTC() {
        return this.detailUTC;
    }

    public DetailLineBI detailUTC(Instant detailUTC) {
        this.setDetailUTC(detailUTC);
        return this;
    }

    public void setDetailUTC(Instant detailUTC) {
        this.detailUTC = detailUTC;
    }

    public Instant getDetailLcl() {
        return this.detailLcl;
    }

    public DetailLineBI detailLcl(Instant detailLcl) {
        this.setDetailLcl(detailLcl);
        return this;
    }

    public void setDetailLcl(Instant detailLcl) {
        this.detailLcl = detailLcl;
    }

    public Integer getSeatNum() {
        return this.seatNum;
    }

    public DetailLineBI seatNum(Integer seatNum) {
        this.setSeatNum(seatNum);
        return this;
    }

    public void setSeatNum(Integer seatNum) {
        this.seatNum = seatNum;
    }

    public Integer getPrcLvl() {
        return this.prcLvl;
    }

    public DetailLineBI prcLvl(Integer prcLvl) {
        this.setPrcLvl(prcLvl);
        return this;
    }

    public void setPrcLvl(Integer prcLvl) {
        this.prcLvl = prcLvl;
    }

    public BigDecimal getDspTtl() {
        return this.dspTtl;
    }

    public DetailLineBI dspTtl(BigDecimal dspTtl) {
        this.setDspTtl(dspTtl);
        return this;
    }

    public void setDspTtl(BigDecimal dspTtl) {
        this.dspTtl = dspTtl;
    }

    public Integer getDspQty() {
        return this.dspQty;
    }

    public DetailLineBI dspQty(Integer dspQty) {
        this.setDspQty(dspQty);
        return this;
    }

    public void setDspQty(Integer dspQty) {
        this.dspQty = dspQty;
    }

    public Boolean getErrCorFlag() {
        return this.errCorFlag;
    }

    public DetailLineBI errCorFlag(Boolean errCorFlag) {
        this.setErrCorFlag(errCorFlag);
        return this;
    }

    public void setErrCorFlag(Boolean errCorFlag) {
        this.errCorFlag = errCorFlag;
    }

    public Boolean getVdFlag() {
        return this.vdFlag;
    }

    public DetailLineBI vdFlag(Boolean vdFlag) {
        this.setVdFlag(vdFlag);
        return this;
    }

    public void setVdFlag(Boolean vdFlag) {
        this.vdFlag = vdFlag;
    }

    public Boolean getReturnFlag() {
        return this.returnFlag;
    }

    public DetailLineBI returnFlag(Boolean returnFlag) {
        this.setReturnFlag(returnFlag);
        return this;
    }

    public void setReturnFlag(Boolean returnFlag) {
        this.returnFlag = returnFlag;
    }

    public Boolean getDoNotShowFlag() {
        return this.doNotShowFlag;
    }

    public DetailLineBI doNotShowFlag(Boolean doNotShowFlag) {
        this.setDoNotShowFlag(doNotShowFlag);
        return this;
    }

    public void setDoNotShowFlag(Boolean doNotShowFlag) {
        this.doNotShowFlag = doNotShowFlag;
    }

    public BigDecimal getAggTtl() {
        return this.aggTtl;
    }

    public DetailLineBI aggTtl(BigDecimal aggTtl) {
        this.setAggTtl(aggTtl);
        return this;
    }

    public void setAggTtl(BigDecimal aggTtl) {
        this.aggTtl = aggTtl;
    }

    public Integer getRsnCodeNum() {
        return this.rsnCodeNum;
    }

    public DetailLineBI rsnCodeNum(Integer rsnCodeNum) {
        this.setRsnCodeNum(rsnCodeNum);
        return this;
    }

    public void setRsnCodeNum(Integer rsnCodeNum) {
        this.rsnCodeNum = rsnCodeNum;
    }

    public String getRefInfo1() {
        return this.refInfo1;
    }

    public DetailLineBI refInfo1(String refInfo1) {
        this.setRefInfo1(refInfo1);
        return this;
    }

    public void setRefInfo1(String refInfo1) {
        this.refInfo1 = refInfo1;
    }

    public String getRefInfo2() {
        return this.refInfo2;
    }

    public DetailLineBI refInfo2(String refInfo2) {
        this.setRefInfo2(refInfo2);
        return this;
    }

    public void setRefInfo2(String refInfo2) {
        this.refInfo2 = refInfo2;
    }

    public Integer getSvcRndNum() {
        return this.svcRndNum;
    }

    public DetailLineBI svcRndNum(Integer svcRndNum) {
        this.setSvcRndNum(svcRndNum);
        return this;
    }

    public void setSvcRndNum(Integer svcRndNum) {
        this.svcRndNum = svcRndNum;
    }

    public Integer getParDtlId() {
        return this.parDtlId;
    }

    public DetailLineBI parDtlId(Integer parDtlId) {
        this.setParDtlId(parDtlId);
        return this;
    }

    public void setParDtlId(Integer parDtlId) {
        this.parDtlId = parDtlId;
    }

    public Integer getChkEmpId() {
        return this.chkEmpId;
    }

    public DetailLineBI chkEmpId(Integer chkEmpId) {
        this.setChkEmpId(chkEmpId);
        return this;
    }

    public void setChkEmpId(Integer chkEmpId) {
        this.chkEmpId = chkEmpId;
    }

    public Integer getTransEmpNum() {
        return this.transEmpNum;
    }

    public DetailLineBI transEmpNum(Integer transEmpNum) {
        this.setTransEmpNum(transEmpNum);
        return this;
    }

    public void setTransEmpNum(Integer transEmpNum) {
        this.transEmpNum = transEmpNum;
    }

    public Integer getMgrEmpNum() {
        return this.mgrEmpNum;
    }

    public DetailLineBI mgrEmpNum(Integer mgrEmpNum) {
        this.setMgrEmpNum(mgrEmpNum);
        return this;
    }

    public void setMgrEmpNum(Integer mgrEmpNum) {
        this.mgrEmpNum = mgrEmpNum;
    }

    public Integer getMealEmpNum() {
        return this.mealEmpNum;
    }

    public DetailLineBI mealEmpNum(Integer mealEmpNum) {
        this.setMealEmpNum(mealEmpNum);
        return this;
    }

    public void setMealEmpNum(Integer mealEmpNum) {
        this.mealEmpNum = mealEmpNum;
    }

    public Integer getDscNum() {
        return this.dscNum;
    }

    public DetailLineBI dscNum(Integer dscNum) {
        this.setDscNum(dscNum);
        return this;
    }

    public void setDscNum(Integer dscNum) {
        this.dscNum = dscNum;
    }

    public Integer getDscMiNum() {
        return this.dscMiNum;
    }

    public DetailLineBI dscMiNum(Integer dscMiNum) {
        this.setDscMiNum(dscMiNum);
        return this;
    }

    public void setDscMiNum(Integer dscMiNum) {
        this.dscMiNum = dscMiNum;
    }

    public Integer getSvcChgNum() {
        return this.svcChgNum;
    }

    public DetailLineBI svcChgNum(Integer svcChgNum) {
        this.setSvcChgNum(svcChgNum);
        return this;
    }

    public void setSvcChgNum(Integer svcChgNum) {
        this.svcChgNum = svcChgNum;
    }

    public Integer getTmedNum() {
        return this.tmedNum;
    }

    public DetailLineBI tmedNum(Integer tmedNum) {
        this.setTmedNum(tmedNum);
        return this;
    }

    public void setTmedNum(Integer tmedNum) {
        this.tmedNum = tmedNum;
    }

    public Integer getMiNum() {
        return this.miNum;
    }

    public DetailLineBI miNum(Integer miNum) {
        this.setMiNum(miNum);
        return this;
    }

    public void setMiNum(Integer miNum) {
        this.miNum = miNum;
    }

    public GuestCheckBI getGuestCheckBI() {
        return this.guestCheckBI;
    }

    public void setGuestCheckBI(GuestCheckBI guestCheckBI) {
        this.guestCheckBI = guestCheckBI;
    }

    public DetailLineBI guestCheckBI(GuestCheckBI guestCheckBI) {
        this.setGuestCheckBI(guestCheckBI);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailLineBI)) {
            return false;
        }
        return getId() != null && getId().equals(((DetailLineBI) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetailLineBI{" +
            "id=" + getId() +
            ", guestCheckLineItemId=" + getGuestCheckLineItemId() +
            ", detailUTC='" + getDetailUTC() + "'" +
            ", detailLcl='" + getDetailLcl() + "'" +
            ", seatNum=" + getSeatNum() +
            ", prcLvl=" + getPrcLvl() +
            ", dspTtl=" + getDspTtl() +
            ", dspQty=" + getDspQty() +
            ", errCorFlag='" + getErrCorFlag() + "'" +
            ", vdFlag='" + getVdFlag() + "'" +
            ", returnFlag='" + getReturnFlag() + "'" +
            ", doNotShowFlag='" + getDoNotShowFlag() + "'" +
            ", aggTtl=" + getAggTtl() +
            ", rsnCodeNum=" + getRsnCodeNum() +
            ", refInfo1='" + getRefInfo1() + "'" +
            ", refInfo2='" + getRefInfo2() + "'" +
            ", svcRndNum=" + getSvcRndNum() +
            ", parDtlId=" + getParDtlId() +
            ", chkEmpId=" + getChkEmpId() +
            ", transEmpNum=" + getTransEmpNum() +
            ", mgrEmpNum=" + getMgrEmpNum() +
            ", mealEmpNum=" + getMealEmpNum() +
            ", dscNum=" + getDscNum() +
            ", dscMiNum=" + getDscMiNum() +
            ", svcChgNum=" + getSvcChgNum() +
            ", tmedNum=" + getTmedNum() +
            ", miNum=" + getMiNum() +
            "}";
    }
}
