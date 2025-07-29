package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaxeBI.
 */
@Entity
@Table(name = "taxe_bi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaxeBI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "loc_ref")
    private String locRef;

    @Column(name = "num")
    private Integer num;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Integer type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public TaxeBI id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocRef() {
        return this.locRef;
    }

    public TaxeBI locRef(String locRef) {
        this.setLocRef(locRef);
        return this;
    }

    public void setLocRef(String locRef) {
        this.locRef = locRef;
    }

    public Integer getNum() {
        return this.num;
    }

    public TaxeBI num(Integer num) {
        this.setNum(num);
        return this;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return this.name;
    }

    public TaxeBI name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return this.type;
    }

    public TaxeBI type(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxeBI)) {
            return false;
        }
        return getId() != null && getId().equals(((TaxeBI) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxeBI{" +
            "id=" + getId() +
            ", locRef='" + getLocRef() + "'" +
            ", num=" + getNum() +
            ", name='" + getName() + "'" +
            ", type=" + getType() +
            "}";
    }
}
