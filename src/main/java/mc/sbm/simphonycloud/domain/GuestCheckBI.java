package mc.sbm.simphonycloud.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GuestCheckBI.
 */
@Entity
@Table(name = "guest_check_bi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GuestCheckBI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "guest_check_id")
    private Integer guestCheckId;

    @Column(name = "chk_num")
    private Integer chkNum;

    @Column(name = "opn_lcl")
    private Instant opnLcl;

    @Column(name = "clsd_lcl")
    private Instant clsdLcl;

    @Column(name = "cancel_flag")
    private Boolean cancelFlag;

    @Column(name = "gst_cnt")
    private Integer gstCnt;

    @Column(name = "tbl_num")
    private Integer tblNum;

    @Column(name = "tax_coll_ttl", precision = 21, scale = 2)
    private BigDecimal taxCollTtl;

    @Column(name = "sub_ttl", precision = 21, scale = 2)
    private BigDecimal subTtl;

    @Column(name = "chk_ttl", precision = 21, scale = 2)
    private BigDecimal chkTtl;

    @Column(name = "svc_chg_ttl", precision = 21, scale = 2)
    private BigDecimal svcChgTtl;

    @Column(name = "tip_total", precision = 21, scale = 2)
    private BigDecimal tipTotal;

    @Column(name = "dsc_ttl", precision = 21, scale = 2)
    private BigDecimal dscTtl;

    @Column(name = "error_correct_ttl", precision = 21, scale = 2)
    private BigDecimal errorCorrectTtl;

    @Column(name = "return_ttl", precision = 21, scale = 2)
    private BigDecimal returnTtl;

    @Column(name = "xfer_to_chk_num")
    private Integer xferToChkNum;

    @Column(name = "xfer_status")
    private String xferStatus;

    @Column(name = "ot_num")
    private Integer otNum;

    @Column(name = "loc_ref")
    private String locRef;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "guestCheckBI")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "guestCheckBI" }, allowSetters = true)
    private Set<DetailLineBI> detailLineBILists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public GuestCheckBI id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGuestCheckId() {
        return this.guestCheckId;
    }

    public GuestCheckBI guestCheckId(Integer guestCheckId) {
        this.setGuestCheckId(guestCheckId);
        return this;
    }

    public void setGuestCheckId(Integer guestCheckId) {
        this.guestCheckId = guestCheckId;
    }

    public Integer getChkNum() {
        return this.chkNum;
    }

    public GuestCheckBI chkNum(Integer chkNum) {
        this.setChkNum(chkNum);
        return this;
    }

    public void setChkNum(Integer chkNum) {
        this.chkNum = chkNum;
    }

    public Instant getOpnLcl() {
        return this.opnLcl;
    }

    public GuestCheckBI opnLcl(Instant opnLcl) {
        this.setOpnLcl(opnLcl);
        return this;
    }

    public void setOpnLcl(Instant opnLcl) {
        this.opnLcl = opnLcl;
    }

    public Instant getClsdLcl() {
        return this.clsdLcl;
    }

    public GuestCheckBI clsdLcl(Instant clsdLcl) {
        this.setClsdLcl(clsdLcl);
        return this;
    }

    public void setClsdLcl(Instant clsdLcl) {
        this.clsdLcl = clsdLcl;
    }

    public Boolean getCancelFlag() {
        return this.cancelFlag;
    }

    public GuestCheckBI cancelFlag(Boolean cancelFlag) {
        this.setCancelFlag(cancelFlag);
        return this;
    }

    public void setCancelFlag(Boolean cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public Integer getGstCnt() {
        return this.gstCnt;
    }

    public GuestCheckBI gstCnt(Integer gstCnt) {
        this.setGstCnt(gstCnt);
        return this;
    }

    public void setGstCnt(Integer gstCnt) {
        this.gstCnt = gstCnt;
    }

    public Integer getTblNum() {
        return this.tblNum;
    }

    public GuestCheckBI tblNum(Integer tblNum) {
        this.setTblNum(tblNum);
        return this;
    }

    public void setTblNum(Integer tblNum) {
        this.tblNum = tblNum;
    }

    public BigDecimal getTaxCollTtl() {
        return this.taxCollTtl;
    }

    public GuestCheckBI taxCollTtl(BigDecimal taxCollTtl) {
        this.setTaxCollTtl(taxCollTtl);
        return this;
    }

    public void setTaxCollTtl(BigDecimal taxCollTtl) {
        this.taxCollTtl = taxCollTtl;
    }

    public BigDecimal getSubTtl() {
        return this.subTtl;
    }

    public GuestCheckBI subTtl(BigDecimal subTtl) {
        this.setSubTtl(subTtl);
        return this;
    }

    public void setSubTtl(BigDecimal subTtl) {
        this.subTtl = subTtl;
    }

    public BigDecimal getChkTtl() {
        return this.chkTtl;
    }

    public GuestCheckBI chkTtl(BigDecimal chkTtl) {
        this.setChkTtl(chkTtl);
        return this;
    }

    public void setChkTtl(BigDecimal chkTtl) {
        this.chkTtl = chkTtl;
    }

    public BigDecimal getSvcChgTtl() {
        return this.svcChgTtl;
    }

    public GuestCheckBI svcChgTtl(BigDecimal svcChgTtl) {
        this.setSvcChgTtl(svcChgTtl);
        return this;
    }

    public void setSvcChgTtl(BigDecimal svcChgTtl) {
        this.svcChgTtl = svcChgTtl;
    }

    public BigDecimal getTipTotal() {
        return this.tipTotal;
    }

    public GuestCheckBI tipTotal(BigDecimal tipTotal) {
        this.setTipTotal(tipTotal);
        return this;
    }

    public void setTipTotal(BigDecimal tipTotal) {
        this.tipTotal = tipTotal;
    }

    public BigDecimal getDscTtl() {
        return this.dscTtl;
    }

    public GuestCheckBI dscTtl(BigDecimal dscTtl) {
        this.setDscTtl(dscTtl);
        return this;
    }

    public void setDscTtl(BigDecimal dscTtl) {
        this.dscTtl = dscTtl;
    }

    public BigDecimal getErrorCorrectTtl() {
        return this.errorCorrectTtl;
    }

    public GuestCheckBI errorCorrectTtl(BigDecimal errorCorrectTtl) {
        this.setErrorCorrectTtl(errorCorrectTtl);
        return this;
    }

    public void setErrorCorrectTtl(BigDecimal errorCorrectTtl) {
        this.errorCorrectTtl = errorCorrectTtl;
    }

    public BigDecimal getReturnTtl() {
        return this.returnTtl;
    }

    public GuestCheckBI returnTtl(BigDecimal returnTtl) {
        this.setReturnTtl(returnTtl);
        return this;
    }

    public void setReturnTtl(BigDecimal returnTtl) {
        this.returnTtl = returnTtl;
    }

    public Integer getXferToChkNum() {
        return this.xferToChkNum;
    }

    public GuestCheckBI xferToChkNum(Integer xferToChkNum) {
        this.setXferToChkNum(xferToChkNum);
        return this;
    }

    public void setXferToChkNum(Integer xferToChkNum) {
        this.xferToChkNum = xferToChkNum;
    }

    public String getXferStatus() {
        return this.xferStatus;
    }

    public GuestCheckBI xferStatus(String xferStatus) {
        this.setXferStatus(xferStatus);
        return this;
    }

    public void setXferStatus(String xferStatus) {
        this.xferStatus = xferStatus;
    }

    public Integer getOtNum() {
        return this.otNum;
    }

    public GuestCheckBI otNum(Integer otNum) {
        this.setOtNum(otNum);
        return this;
    }

    public void setOtNum(Integer otNum) {
        this.otNum = otNum;
    }

    public String getLocRef() {
        return this.locRef;
    }

    public GuestCheckBI locRef(String locRef) {
        this.setLocRef(locRef);
        return this;
    }

    public void setLocRef(String locRef) {
        this.locRef = locRef;
    }

    public Set<DetailLineBI> getDetailLineBILists() {
        return this.detailLineBILists;
    }

    public void setDetailLineBILists(Set<DetailLineBI> detailLineBIS) {
        if (this.detailLineBILists != null) {
            this.detailLineBILists.forEach(i -> i.setGuestCheckBI(null));
        }
        if (detailLineBIS != null) {
            detailLineBIS.forEach(i -> i.setGuestCheckBI(this));
        }
        this.detailLineBILists = detailLineBIS;
    }

    public GuestCheckBI detailLineBILists(Set<DetailLineBI> detailLineBIS) {
        this.setDetailLineBILists(detailLineBIS);
        return this;
    }

    public GuestCheckBI addDetailLineBIList(DetailLineBI detailLineBI) {
        this.detailLineBILists.add(detailLineBI);
        detailLineBI.setGuestCheckBI(this);
        return this;
    }

    public GuestCheckBI removeDetailLineBIList(DetailLineBI detailLineBI) {
        this.detailLineBILists.remove(detailLineBI);
        detailLineBI.setGuestCheckBI(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GuestCheckBI)) {
            return false;
        }
        return getId() != null && getId().equals(((GuestCheckBI) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GuestCheckBI{" +
            "id=" + getId() +
            ", guestCheckId=" + getGuestCheckId() +
            ", chkNum=" + getChkNum() +
            ", opnLcl='" + getOpnLcl() + "'" +
            ", clsdLcl='" + getClsdLcl() + "'" +
            ", cancelFlag='" + getCancelFlag() + "'" +
            ", gstCnt=" + getGstCnt() +
            ", tblNum=" + getTblNum() +
            ", taxCollTtl=" + getTaxCollTtl() +
            ", subTtl=" + getSubTtl() +
            ", chkTtl=" + getChkTtl() +
            ", svcChgTtl=" + getSvcChgTtl() +
            ", tipTotal=" + getTipTotal() +
            ", dscTtl=" + getDscTtl() +
            ", errorCorrectTtl=" + getErrorCorrectTtl() +
            ", returnTtl=" + getReturnTtl() +
            ", xferToChkNum=" + getXferToChkNum() +
            ", xferStatus='" + getXferStatus() + "'" +
            ", otNum=" + getOtNum() +
            ", locRef='" + getLocRef() + "'" +
            "}";
    }
}
