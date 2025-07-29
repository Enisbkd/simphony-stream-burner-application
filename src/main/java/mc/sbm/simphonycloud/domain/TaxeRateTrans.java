package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaxeRateTrans.
 */
@Entity
@Table(name = "taxe_rate_trans")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaxeRateTrans implements Serializable {

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

    @Column(name = "tax_rate_id")
    private Integer taxRateId;

    @Column(name = "percentage")
    private Double percentage;

    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "name_fr")
    private String nameFR;

    @Column(name = "name_en")
    private String nameEN;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public TaxeRateTrans id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgShortName() {
        return this.orgShortName;
    }

    public TaxeRateTrans orgShortName(String orgShortName) {
        this.setOrgShortName(orgShortName);
        return this;
    }

    public void setOrgShortName(String orgShortName) {
        this.orgShortName = orgShortName;
    }

    public String getLocRef() {
        return this.locRef;
    }

    public TaxeRateTrans locRef(String locRef) {
        this.setLocRef(locRef);
        return this;
    }

    public void setLocRef(String locRef) {
        this.locRef = locRef;
    }

    public Integer getRvcRef() {
        return this.rvcRef;
    }

    public TaxeRateTrans rvcRef(Integer rvcRef) {
        this.setRvcRef(rvcRef);
        return this;
    }

    public void setRvcRef(Integer rvcRef) {
        this.rvcRef = rvcRef;
    }

    public Integer getTaxRateId() {
        return this.taxRateId;
    }

    public TaxeRateTrans taxRateId(Integer taxRateId) {
        this.setTaxRateId(taxRateId);
        return this;
    }

    public void setTaxRateId(Integer taxRateId) {
        this.taxRateId = taxRateId;
    }

    public Double getPercentage() {
        return this.percentage;
    }

    public TaxeRateTrans percentage(Double percentage) {
        this.setPercentage(percentage);
        return this;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getTaxType() {
        return this.taxType;
    }

    public TaxeRateTrans taxType(String taxType) {
        this.setTaxType(taxType);
        return this;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getNameFR() {
        return this.nameFR;
    }

    public TaxeRateTrans nameFR(String nameFR) {
        this.setNameFR(nameFR);
        return this;
    }

    public void setNameFR(String nameFR) {
        this.nameFR = nameFR;
    }

    public String getNameEN() {
        return this.nameEN;
    }

    public TaxeRateTrans nameEN(String nameEN) {
        this.setNameEN(nameEN);
        return this;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxeRateTrans)) {
            return false;
        }
        return getId() != null && getId().equals(((TaxeRateTrans) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxeRateTrans{" +
            "id=" + getId() +
            ", orgShortName='" + getOrgShortName() + "'" +
            ", locRef='" + getLocRef() + "'" +
            ", rvcRef=" + getRvcRef() +
            ", taxRateId=" + getTaxRateId() +
            ", percentage=" + getPercentage() +
            ", taxType='" + getTaxType() + "'" +
            ", nameFR='" + getNameFR() + "'" +
            ", nameEN='" + getNameEN() + "'" +
            "}";
    }
}
