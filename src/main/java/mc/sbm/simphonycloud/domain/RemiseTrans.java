package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RemiseTrans.
 */
@Entity
@Table(name = "remise_trans")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RemiseTrans implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "org_short_name")
    private String orgShortName;

    @Column(name = "loc_ref")
    private String locRef;

    @Column(name = "rvc_ref")
    private Integer rvcRef;

    @Column(name = "discount_id")
    private Integer discountId;

    @Column(name = "fr_name")
    private String frName;

    @Column(name = "eng_name")
    private String engName;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "discount_value")
    private Double discountValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public RemiseTrans id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgShortName() {
        return this.orgShortName;
    }

    public RemiseTrans orgShortName(String orgShortName) {
        this.setOrgShortName(orgShortName);
        return this;
    }

    public void setOrgShortName(String orgShortName) {
        this.orgShortName = orgShortName;
    }

    public String getLocRef() {
        return this.locRef;
    }

    public RemiseTrans locRef(String locRef) {
        this.setLocRef(locRef);
        return this;
    }

    public void setLocRef(String locRef) {
        this.locRef = locRef;
    }

    public Integer getRvcRef() {
        return this.rvcRef;
    }

    public RemiseTrans rvcRef(Integer rvcRef) {
        this.setRvcRef(rvcRef);
        return this;
    }

    public void setRvcRef(Integer rvcRef) {
        this.rvcRef = rvcRef;
    }

    public Integer getDiscountId() {
        return this.discountId;
    }

    public RemiseTrans discountId(Integer discountId) {
        this.setDiscountId(discountId);
        return this;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public String getFrName() {
        return this.frName;
    }

    public RemiseTrans frName(String frName) {
        this.setFrName(frName);
        return this;
    }

    public void setFrName(String frName) {
        this.frName = frName;
    }

    public String getEngName() {
        return this.engName;
    }

    public RemiseTrans engName(String engName) {
        this.setEngName(engName);
        return this;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getDiscountType() {
        return this.discountType;
    }

    public RemiseTrans discountType(String discountType) {
        this.setDiscountType(discountType);
        return this;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountValue() {
        return this.discountValue;
    }

    public RemiseTrans discountValue(Double discountValue) {
        this.setDiscountValue(discountValue);
        return this;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RemiseTrans)) {
            return false;
        }
        return getId() != null && getId().equals(((RemiseTrans) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RemiseTrans{" +
            "id=" + getId() +
            ", orgShortName='" + getOrgShortName() + "'" +
            ", locRef='" + getLocRef() + "'" +
            ", rvcRef=" + getRvcRef() +
            ", discountId=" + getDiscountId() +
            ", frName='" + getFrName() + "'" +
            ", engName='" + getEngName() + "'" +
            ", discountType='" + getDiscountType() + "'" +
            ", discountValue=" + getDiscountValue() +
            "}";
    }
}
