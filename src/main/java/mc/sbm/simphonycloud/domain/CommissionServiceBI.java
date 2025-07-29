package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CommissionServiceBI.
 */
@Entity
@Table(name = "commission_service_bi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommissionServiceBI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "nom_court")
    private String nomCourt;

    @Column(name = "type_value")
    private String typeValue;

    @Column(name = "value")
    private Float value;

    @NotNull
    @Column(name = "etablissement_ref", nullable = false)
    private String etablissementRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public CommissionServiceBI id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public CommissionServiceBI nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomCourt() {
        return this.nomCourt;
    }

    public CommissionServiceBI nomCourt(String nomCourt) {
        this.setNomCourt(nomCourt);
        return this;
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public String getTypeValue() {
        return this.typeValue;
    }

    public CommissionServiceBI typeValue(String typeValue) {
        this.setTypeValue(typeValue);
        return this;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public Float getValue() {
        return this.value;
    }

    public CommissionServiceBI value(Float value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getEtablissementRef() {
        return this.etablissementRef;
    }

    public CommissionServiceBI etablissementRef(String etablissementRef) {
        this.setEtablissementRef(etablissementRef);
        return this;
    }

    public void setEtablissementRef(String etablissementRef) {
        this.etablissementRef = etablissementRef;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommissionServiceBI)) {
            return false;
        }
        return getId() != null && getId().equals(((CommissionServiceBI) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommissionServiceBI{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", typeValue='" + getTypeValue() + "'" +
            ", value=" + getValue() +
            ", etablissementRef='" + getEtablissementRef() + "'" +
            "}";
    }
}
