package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PointDeVenteTrans.
 */
@Entity
@Table(name = "point_de_vente_trans")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PointDeVenteTrans implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "rvc_ref")
    private Integer rvcRef;

    @Column(name = "name")
    private String name;

    @Column(name = "loc_ref")
    private String locRef;

    @Column(name = "org_short_name")
    private String orgShortName;

    @Column(name = "address")
    private String address;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PointDeVenteTrans id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRvcRef() {
        return this.rvcRef;
    }

    public PointDeVenteTrans rvcRef(Integer rvcRef) {
        this.setRvcRef(rvcRef);
        return this;
    }

    public void setRvcRef(Integer rvcRef) {
        this.rvcRef = rvcRef;
    }

    public String getName() {
        return this.name;
    }

    public PointDeVenteTrans name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocRef() {
        return this.locRef;
    }

    public PointDeVenteTrans locRef(String locRef) {
        this.setLocRef(locRef);
        return this;
    }

    public void setLocRef(String locRef) {
        this.locRef = locRef;
    }

    public String getOrgShortName() {
        return this.orgShortName;
    }

    public PointDeVenteTrans orgShortName(String orgShortName) {
        this.setOrgShortName(orgShortName);
        return this;
    }

    public void setOrgShortName(String orgShortName) {
        this.orgShortName = orgShortName;
    }

    public String getAddress() {
        return this.address;
    }

    public PointDeVenteTrans address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointDeVenteTrans)) {
            return false;
        }
        return getId() != null && getId().equals(((PointDeVenteTrans) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointDeVenteTrans{" +
            "id=" + getId() +
            ", rvcRef=" + getRvcRef() +
            ", name='" + getName() + "'" +
            ", locRef='" + getLocRef() + "'" +
            ", orgShortName='" + getOrgShortName() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
