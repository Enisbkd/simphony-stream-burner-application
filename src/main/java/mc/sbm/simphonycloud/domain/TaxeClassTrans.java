package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaxeClassTrans.
 */
@Entity
@Table(name = "taxe_class_trans")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaxeClassTrans implements Serializable {

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

    @Column(name = "tax_class_id")
    private Integer taxClassId;

    @Column(name = "active_tax_rate_refs")
    private String activeTaxRateRefs;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public TaxeClassTrans id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgShortName() {
        return this.orgShortName;
    }

    public TaxeClassTrans orgShortName(String orgShortName) {
        this.setOrgShortName(orgShortName);
        return this;
    }

    public void setOrgShortName(String orgShortName) {
        this.orgShortName = orgShortName;
    }

    public String getLocRef() {
        return this.locRef;
    }

    public TaxeClassTrans locRef(String locRef) {
        this.setLocRef(locRef);
        return this;
    }

    public void setLocRef(String locRef) {
        this.locRef = locRef;
    }

    public Integer getRvcRef() {
        return this.rvcRef;
    }

    public TaxeClassTrans rvcRef(Integer rvcRef) {
        this.setRvcRef(rvcRef);
        return this;
    }

    public void setRvcRef(Integer rvcRef) {
        this.rvcRef = rvcRef;
    }

    public Integer getTaxClassId() {
        return this.taxClassId;
    }

    public TaxeClassTrans taxClassId(Integer taxClassId) {
        this.setTaxClassId(taxClassId);
        return this;
    }

    public void setTaxClassId(Integer taxClassId) {
        this.taxClassId = taxClassId;
    }

    public String getActiveTaxRateRefs() {
        return this.activeTaxRateRefs;
    }

    public TaxeClassTrans activeTaxRateRefs(String activeTaxRateRefs) {
        this.setActiveTaxRateRefs(activeTaxRateRefs);
        return this;
    }

    public void setActiveTaxRateRefs(String activeTaxRateRefs) {
        this.activeTaxRateRefs = activeTaxRateRefs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxeClassTrans)) {
            return false;
        }
        return getId() != null && getId().equals(((TaxeClassTrans) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxeClassTrans{" +
            "id=" + getId() +
            ", orgShortName='" + getOrgShortName() + "'" +
            ", locRef='" + getLocRef() + "'" +
            ", rvcRef=" + getRvcRef() +
            ", taxClassId=" + getTaxClassId() +
            ", activeTaxRateRefs='" + getActiveTaxRateRefs() + "'" +
            "}";
    }
}
