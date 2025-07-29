package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MenuItemMastersCnC.
 */
@Entity
@Table(name = "menu_item_masters_cn_c")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MenuItemMastersCnC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "hier_unit_id")
    private Integer hierUnitId;

    @Column(name = "menu_item_master_id")
    private Integer menuItemMasterId;

    @Column(name = "family_group_object_num")
    private Integer familyGroupObjectNum;

    @Column(name = "major_group_object_num")
    private Integer majorGroupObjectNum;

    @Column(name = "report_group_object_num")
    private Integer reportGroupObjectNum;

    @Column(name = "external_reference_1")
    private String externalReference1;

    @Column(name = "external_reference_2")
    private String externalReference2;

    @Column(name = "object_num")
    private Integer objectNum;

    @Column(name = "name")
    private String name;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public MenuItemMastersCnC id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHierUnitId() {
        return this.hierUnitId;
    }

    public MenuItemMastersCnC hierUnitId(Integer hierUnitId) {
        this.setHierUnitId(hierUnitId);
        return this;
    }

    public void setHierUnitId(Integer hierUnitId) {
        this.hierUnitId = hierUnitId;
    }

    public Integer getMenuItemMasterId() {
        return this.menuItemMasterId;
    }

    public MenuItemMastersCnC menuItemMasterId(Integer menuItemMasterId) {
        this.setMenuItemMasterId(menuItemMasterId);
        return this;
    }

    public void setMenuItemMasterId(Integer menuItemMasterId) {
        this.menuItemMasterId = menuItemMasterId;
    }

    public Integer getFamilyGroupObjectNum() {
        return this.familyGroupObjectNum;
    }

    public MenuItemMastersCnC familyGroupObjectNum(Integer familyGroupObjectNum) {
        this.setFamilyGroupObjectNum(familyGroupObjectNum);
        return this;
    }

    public void setFamilyGroupObjectNum(Integer familyGroupObjectNum) {
        this.familyGroupObjectNum = familyGroupObjectNum;
    }

    public Integer getMajorGroupObjectNum() {
        return this.majorGroupObjectNum;
    }

    public MenuItemMastersCnC majorGroupObjectNum(Integer majorGroupObjectNum) {
        this.setMajorGroupObjectNum(majorGroupObjectNum);
        return this;
    }

    public void setMajorGroupObjectNum(Integer majorGroupObjectNum) {
        this.majorGroupObjectNum = majorGroupObjectNum;
    }

    public Integer getReportGroupObjectNum() {
        return this.reportGroupObjectNum;
    }

    public MenuItemMastersCnC reportGroupObjectNum(Integer reportGroupObjectNum) {
        this.setReportGroupObjectNum(reportGroupObjectNum);
        return this;
    }

    public void setReportGroupObjectNum(Integer reportGroupObjectNum) {
        this.reportGroupObjectNum = reportGroupObjectNum;
    }

    public String getExternalReference1() {
        return this.externalReference1;
    }

    public MenuItemMastersCnC externalReference1(String externalReference1) {
        this.setExternalReference1(externalReference1);
        return this;
    }

    public void setExternalReference1(String externalReference1) {
        this.externalReference1 = externalReference1;
    }

    public String getExternalReference2() {
        return this.externalReference2;
    }

    public MenuItemMastersCnC externalReference2(String externalReference2) {
        this.setExternalReference2(externalReference2);
        return this;
    }

    public void setExternalReference2(String externalReference2) {
        this.externalReference2 = externalReference2;
    }

    public Integer getObjectNum() {
        return this.objectNum;
    }

    public MenuItemMastersCnC objectNum(Integer objectNum) {
        this.setObjectNum(objectNum);
        return this;
    }

    public void setObjectNum(Integer objectNum) {
        this.objectNum = objectNum;
    }

    public String getName() {
        return this.name;
    }

    public MenuItemMastersCnC name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuItemMastersCnC)) {
            return false;
        }
        return getId() != null && getId().equals(((MenuItemMastersCnC) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MenuItemMastersCnC{" +
            "id=" + getId() +
            ", hierUnitId=" + getHierUnitId() +
            ", menuItemMasterId=" + getMenuItemMasterId() +
            ", familyGroupObjectNum=" + getFamilyGroupObjectNum() +
            ", majorGroupObjectNum=" + getMajorGroupObjectNum() +
            ", reportGroupObjectNum=" + getReportGroupObjectNum() +
            ", externalReference1='" + getExternalReference1() + "'" +
            ", externalReference2='" + getExternalReference2() + "'" +
            ", objectNum=" + getObjectNum() +
            ", name='" + getName() + "'" +
            "}";
    }
}
