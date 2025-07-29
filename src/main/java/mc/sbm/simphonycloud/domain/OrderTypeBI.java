package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrderTypeBI.
 */
@Entity
@Table(name = "order_type_bi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderTypeBI implements Serializable {

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

    @Column(name = "cat_grp_hier_name_1")
    private String catGrpHierName1;

    @Column(name = "cat_grp_name_1")
    private String catGrpName1;

    @Column(name = "cat_grp_hier_name_2")
    private String catGrpHierName2;

    @Column(name = "cat_grp_name_2")
    private String catGrpName2;

    @Column(name = "cat_grp_hier_name_3")
    private String catGrpHierName3;

    @Column(name = "cat_grp_name_3")
    private String catGrpName3;

    @Column(name = "cat_grp_hier_name_4")
    private String catGrpHierName4;

    @Column(name = "cat_grp_name_4")
    private String catGrpName4;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public OrderTypeBI id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return this.num;
    }

    public OrderTypeBI num(Integer num) {
        this.setNum(num);
        return this;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getLocRef() {
        return this.locRef;
    }

    public OrderTypeBI locRef(String locRef) {
        this.setLocRef(locRef);
        return this;
    }

    public void setLocRef(String locRef) {
        this.locRef = locRef;
    }

    public String getName() {
        return this.name;
    }

    public OrderTypeBI name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMstrNum() {
        return this.mstrNum;
    }

    public OrderTypeBI mstrNum(Integer mstrNum) {
        this.setMstrNum(mstrNum);
        return this;
    }

    public void setMstrNum(Integer mstrNum) {
        this.mstrNum = mstrNum;
    }

    public String getMstrName() {
        return this.mstrName;
    }

    public OrderTypeBI mstrName(String mstrName) {
        this.setMstrName(mstrName);
        return this;
    }

    public void setMstrName(String mstrName) {
        this.mstrName = mstrName;
    }

    public String getCatGrpHierName1() {
        return this.catGrpHierName1;
    }

    public OrderTypeBI catGrpHierName1(String catGrpHierName1) {
        this.setCatGrpHierName1(catGrpHierName1);
        return this;
    }

    public void setCatGrpHierName1(String catGrpHierName1) {
        this.catGrpHierName1 = catGrpHierName1;
    }

    public String getCatGrpName1() {
        return this.catGrpName1;
    }

    public OrderTypeBI catGrpName1(String catGrpName1) {
        this.setCatGrpName1(catGrpName1);
        return this;
    }

    public void setCatGrpName1(String catGrpName1) {
        this.catGrpName1 = catGrpName1;
    }

    public String getCatGrpHierName2() {
        return this.catGrpHierName2;
    }

    public OrderTypeBI catGrpHierName2(String catGrpHierName2) {
        this.setCatGrpHierName2(catGrpHierName2);
        return this;
    }

    public void setCatGrpHierName2(String catGrpHierName2) {
        this.catGrpHierName2 = catGrpHierName2;
    }

    public String getCatGrpName2() {
        return this.catGrpName2;
    }

    public OrderTypeBI catGrpName2(String catGrpName2) {
        this.setCatGrpName2(catGrpName2);
        return this;
    }

    public void setCatGrpName2(String catGrpName2) {
        this.catGrpName2 = catGrpName2;
    }

    public String getCatGrpHierName3() {
        return this.catGrpHierName3;
    }

    public OrderTypeBI catGrpHierName3(String catGrpHierName3) {
        this.setCatGrpHierName3(catGrpHierName3);
        return this;
    }

    public void setCatGrpHierName3(String catGrpHierName3) {
        this.catGrpHierName3 = catGrpHierName3;
    }

    public String getCatGrpName3() {
        return this.catGrpName3;
    }

    public OrderTypeBI catGrpName3(String catGrpName3) {
        this.setCatGrpName3(catGrpName3);
        return this;
    }

    public void setCatGrpName3(String catGrpName3) {
        this.catGrpName3 = catGrpName3;
    }

    public String getCatGrpHierName4() {
        return this.catGrpHierName4;
    }

    public OrderTypeBI catGrpHierName4(String catGrpHierName4) {
        this.setCatGrpHierName4(catGrpHierName4);
        return this;
    }

    public void setCatGrpHierName4(String catGrpHierName4) {
        this.catGrpHierName4 = catGrpHierName4;
    }

    public String getCatGrpName4() {
        return this.catGrpName4;
    }

    public OrderTypeBI catGrpName4(String catGrpName4) {
        this.setCatGrpName4(catGrpName4);
        return this;
    }

    public void setCatGrpName4(String catGrpName4) {
        this.catGrpName4 = catGrpName4;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderTypeBI)) {
            return false;
        }
        return getId() != null && getId().equals(((OrderTypeBI) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderTypeBI{" +
            "id=" + getId() +
            ", num=" + getNum() +
            ", locRef='" + getLocRef() + "'" +
            ", name='" + getName() + "'" +
            ", mstrNum=" + getMstrNum() +
            ", mstrName='" + getMstrName() + "'" +
            ", catGrpHierName1='" + getCatGrpHierName1() + "'" +
            ", catGrpName1='" + getCatGrpName1() + "'" +
            ", catGrpHierName2='" + getCatGrpHierName2() + "'" +
            ", catGrpName2='" + getCatGrpName2() + "'" +
            ", catGrpHierName3='" + getCatGrpHierName3() + "'" +
            ", catGrpName3='" + getCatGrpName3() + "'" +
            ", catGrpHierName4='" + getCatGrpHierName4() + "'" +
            ", catGrpName4='" + getCatGrpName4() + "'" +
            "}";
    }
}
