package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrderChannelBI.
 */
@Entity
@Table(name = "order_channel_bi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderChannelBI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "num")
    private Integer num;

    @NotNull
    @Column(name = "loc_ref", nullable = false)
    private String locRef;

    @Column(name = "name")
    private String name;

    @Column(name = "mstr_num")
    private Integer mstrNum;

    @Column(name = "mstr_name")
    private String mstrName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public OrderChannelBI id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return this.num;
    }

    public OrderChannelBI num(Integer num) {
        this.setNum(num);
        return this;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getLocRef() {
        return this.locRef;
    }

    public OrderChannelBI locRef(String locRef) {
        this.setLocRef(locRef);
        return this;
    }

    public void setLocRef(String locRef) {
        this.locRef = locRef;
    }

    public String getName() {
        return this.name;
    }

    public OrderChannelBI name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMstrNum() {
        return this.mstrNum;
    }

    public OrderChannelBI mstrNum(Integer mstrNum) {
        this.setMstrNum(mstrNum);
        return this;
    }

    public void setMstrNum(Integer mstrNum) {
        this.mstrNum = mstrNum;
    }

    public String getMstrName() {
        return this.mstrName;
    }

    public OrderChannelBI mstrName(String mstrName) {
        this.setMstrName(mstrName);
        return this;
    }

    public void setMstrName(String mstrName) {
        this.mstrName = mstrName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderChannelBI)) {
            return false;
        }
        return getId() != null && getId().equals(((OrderChannelBI) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderChannelBI{" +
            "id=" + getId() +
            ", num=" + getNum() +
            ", locRef='" + getLocRef() + "'" +
            ", name='" + getName() + "'" +
            ", mstrNum=" + getMstrNum() +
            ", mstrName='" + getMstrName() + "'" +
            "}";
    }
}
