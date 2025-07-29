package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ModePaiementTrans.
 */
@Entity
@Table(name = "mode_paiement_trans")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModePaiementTrans implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "tender_id")
    private Integer tenderId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "extensions")
    private String extensions;

    @Column(name = "org_short_name")
    private String orgShortName;

    @Column(name = "loc_ref")
    private String locRef;

    @Column(name = "rvc_ref")
    private Integer rvcRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public ModePaiementTrans id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTenderId() {
        return this.tenderId;
    }

    public ModePaiementTrans tenderId(Integer tenderId) {
        this.setTenderId(tenderId);
        return this;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public String getName() {
        return this.name;
    }

    public ModePaiementTrans name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public ModePaiementTrans type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtensions() {
        return this.extensions;
    }

    public ModePaiementTrans extensions(String extensions) {
        this.setExtensions(extensions);
        return this;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public String getOrgShortName() {
        return this.orgShortName;
    }

    public ModePaiementTrans orgShortName(String orgShortName) {
        this.setOrgShortName(orgShortName);
        return this;
    }

    public void setOrgShortName(String orgShortName) {
        this.orgShortName = orgShortName;
    }

    public String getLocRef() {
        return this.locRef;
    }

    public ModePaiementTrans locRef(String locRef) {
        this.setLocRef(locRef);
        return this;
    }

    public void setLocRef(String locRef) {
        this.locRef = locRef;
    }

    public Integer getRvcRef() {
        return this.rvcRef;
    }

    public ModePaiementTrans rvcRef(Integer rvcRef) {
        this.setRvcRef(rvcRef);
        return this;
    }

    public void setRvcRef(Integer rvcRef) {
        this.rvcRef = rvcRef;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModePaiementTrans)) {
            return false;
        }
        return getId() != null && getId().equals(((ModePaiementTrans) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModePaiementTrans{" +
            "id=" + getId() +
            ", tenderId=" + getTenderId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", extensions='" + getExtensions() + "'" +
            ", orgShortName='" + getOrgShortName() + "'" +
            ", locRef='" + getLocRef() + "'" +
            ", rvcRef=" + getRvcRef() +
            "}";
    }
}
