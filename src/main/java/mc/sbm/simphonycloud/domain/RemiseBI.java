package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RemiseBI.
 */
@Entity
@Table(name = "remise_bi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RemiseBI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "num")
    private Integer num;

    @Column(name = "name")
    private String name;

    @Column(name = "pos_percent")
    private Double posPercent;

    @Column(name = "rpt_grp_num")
    private Integer rptGrpNum;

    @Column(name = "rpt_grp_name")
    private String rptGrpName;

    @Column(name = "loc_ref")
    private String locRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public RemiseBI id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return this.num;
    }

    public RemiseBI num(Integer num) {
        this.setNum(num);
        return this;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return this.name;
    }

    public RemiseBI name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPosPercent() {
        return this.posPercent;
    }

    public RemiseBI posPercent(Double posPercent) {
        this.setPosPercent(posPercent);
        return this;
    }

    public void setPosPercent(Double posPercent) {
        this.posPercent = posPercent;
    }

    public Integer getRptGrpNum() {
        return this.rptGrpNum;
    }

    public RemiseBI rptGrpNum(Integer rptGrpNum) {
        this.setRptGrpNum(rptGrpNum);
        return this;
    }

    public void setRptGrpNum(Integer rptGrpNum) {
        this.rptGrpNum = rptGrpNum;
    }

    public String getRptGrpName() {
        return this.rptGrpName;
    }

    public RemiseBI rptGrpName(String rptGrpName) {
        this.setRptGrpName(rptGrpName);
        return this;
    }

    public void setRptGrpName(String rptGrpName) {
        this.rptGrpName = rptGrpName;
    }

    public String getLocRef() {
        return this.locRef;
    }

    public RemiseBI locRef(String locRef) {
        this.setLocRef(locRef);
        return this;
    }

    public void setLocRef(String locRef) {
        this.locRef = locRef;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RemiseBI)) {
            return false;
        }
        return getId() != null && getId().equals(((RemiseBI) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RemiseBI{" +
            "id=" + getId() +
            ", num=" + getNum() +
            ", name='" + getName() + "'" +
            ", posPercent=" + getPosPercent() +
            ", rptGrpNum=" + getRptGrpNum() +
            ", rptGrpName='" + getRptGrpName() + "'" +
            ", locRef='" + getLocRef() + "'" +
            "}";
    }
}
