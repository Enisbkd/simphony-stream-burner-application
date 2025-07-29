package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CommissionServiceTrans.
 */
@Entity
@Table(name = "commission_service_trans")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommissionServiceTrans implements Serializable {

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

    @Column(name = "service_charge_id")
    private Integer serviceChargeId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private Double value;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public CommissionServiceTrans id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgShortName() {
        return this.orgShortName;
    }

    public CommissionServiceTrans orgShortName(String orgShortName) {
        this.setOrgShortName(orgShortName);
        return this;
    }

    public void setOrgShortName(String orgShortName) {
        this.orgShortName = orgShortName;
    }

    public String getLocRef() {
        return this.locRef;
    }

    public CommissionServiceTrans locRef(String locRef) {
        this.setLocRef(locRef);
        return this;
    }

    public void setLocRef(String locRef) {
        this.locRef = locRef;
    }

    public Integer getRvcRef() {
        return this.rvcRef;
    }

    public CommissionServiceTrans rvcRef(Integer rvcRef) {
        this.setRvcRef(rvcRef);
        return this;
    }

    public void setRvcRef(Integer rvcRef) {
        this.rvcRef = rvcRef;
    }

    public Integer getServiceChargeId() {
        return this.serviceChargeId;
    }

    public CommissionServiceTrans serviceChargeId(Integer serviceChargeId) {
        this.setServiceChargeId(serviceChargeId);
        return this;
    }

    public void setServiceChargeId(Integer serviceChargeId) {
        this.serviceChargeId = serviceChargeId;
    }

    public String getName() {
        return this.name;
    }

    public CommissionServiceTrans name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public CommissionServiceTrans type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return this.value;
    }

    public CommissionServiceTrans value(Double value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommissionServiceTrans)) {
            return false;
        }
        return getId() != null && getId().equals(((CommissionServiceTrans) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommissionServiceTrans{" +
            "id=" + getId() +
            ", orgShortName='" + getOrgShortName() + "'" +
            ", locRef='" + getLocRef() + "'" +
            ", rvcRef=" + getRvcRef() +
            ", serviceChargeId=" + getServiceChargeId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", value=" + getValue() +
            "}";
    }
}
