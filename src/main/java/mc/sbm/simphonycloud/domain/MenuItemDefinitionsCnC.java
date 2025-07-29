package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MenuItemDefinitionsCnC.
 */
@Entity
@Table(name = "menu_item_definitions_cn_c")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MenuItemDefinitionsCnC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "hier_unit_id")
    private Integer hierUnitId;

    @Column(name = "menu_item_master_obj_num")
    private Integer menuItemMasterObjNum;

    @Column(name = "menu_item_master_id")
    private Integer menuItemMasterId;

    @Column(name = "menu_item_definition_id")
    private Integer menuItemDefinitionId;

    @Column(name = "def_sequence_num")
    private Integer defSequenceNum;

    @Column(name = "menu_item_class_obj_num")
    private Integer menuItemClassObjNum;

    @Column(name = "override_print_class_obj_num")
    private Integer overridePrintClassObjNum;

    @Column(name = "main_level")
    private String mainLevel;

    @Column(name = "sub_level")
    private String subLevel;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "kds_prep_time")
    private Double kdsPrepTime;

    @Column(name = "prefix_level_override")
    private Integer prefixLevelOverride;

    @Column(name = "guest_count")
    private Integer guestCount;

    @Column(name = "slu_1_obj_num")
    private String slu1ObjNum;

    @Column(name = "slu_2_obj_num")
    private String slu2ObjNum;

    @Column(name = "slu_3_obj_num")
    private String slu3ObjNum;

    @Column(name = "slu_4_obj_num")
    private String slu4ObjNum;

    @Column(name = "slu_5_obj_num")
    private String slu5ObjNum;

    @Column(name = "slu_6_obj_num")
    private String slu6ObjNum;

    @Column(name = "slu_7_obj_num")
    private String slu7ObjNum;

    @Column(name = "slu_8_obj_num")
    private String slu8ObjNum;

    @Column(name = "first_name")
    private String firstName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MenuItemDefinitionsCnC id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHierUnitId() {
        return this.hierUnitId;
    }

    public MenuItemDefinitionsCnC hierUnitId(Integer hierUnitId) {
        this.setHierUnitId(hierUnitId);
        return this;
    }

    public void setHierUnitId(Integer hierUnitId) {
        this.hierUnitId = hierUnitId;
    }

    public Integer getMenuItemMasterObjNum() {
        return this.menuItemMasterObjNum;
    }

    public MenuItemDefinitionsCnC menuItemMasterObjNum(Integer menuItemMasterObjNum) {
        this.setMenuItemMasterObjNum(menuItemMasterObjNum);
        return this;
    }

    public void setMenuItemMasterObjNum(Integer menuItemMasterObjNum) {
        this.menuItemMasterObjNum = menuItemMasterObjNum;
    }

    public Integer getMenuItemMasterId() {
        return this.menuItemMasterId;
    }

    public MenuItemDefinitionsCnC menuItemMasterId(Integer menuItemMasterId) {
        this.setMenuItemMasterId(menuItemMasterId);
        return this;
    }

    public void setMenuItemMasterId(Integer menuItemMasterId) {
        this.menuItemMasterId = menuItemMasterId;
    }

    public Integer getMenuItemDefinitionId() {
        return this.menuItemDefinitionId;
    }

    public MenuItemDefinitionsCnC menuItemDefinitionId(Integer menuItemDefinitionId) {
        this.setMenuItemDefinitionId(menuItemDefinitionId);
        return this;
    }

    public void setMenuItemDefinitionId(Integer menuItemDefinitionId) {
        this.menuItemDefinitionId = menuItemDefinitionId;
    }

    public Integer getDefSequenceNum() {
        return this.defSequenceNum;
    }

    public MenuItemDefinitionsCnC defSequenceNum(Integer defSequenceNum) {
        this.setDefSequenceNum(defSequenceNum);
        return this;
    }

    public void setDefSequenceNum(Integer defSequenceNum) {
        this.defSequenceNum = defSequenceNum;
    }

    public Integer getMenuItemClassObjNum() {
        return this.menuItemClassObjNum;
    }

    public MenuItemDefinitionsCnC menuItemClassObjNum(Integer menuItemClassObjNum) {
        this.setMenuItemClassObjNum(menuItemClassObjNum);
        return this;
    }

    public void setMenuItemClassObjNum(Integer menuItemClassObjNum) {
        this.menuItemClassObjNum = menuItemClassObjNum;
    }

    public Integer getOverridePrintClassObjNum() {
        return this.overridePrintClassObjNum;
    }

    public MenuItemDefinitionsCnC overridePrintClassObjNum(Integer overridePrintClassObjNum) {
        this.setOverridePrintClassObjNum(overridePrintClassObjNum);
        return this;
    }

    public void setOverridePrintClassObjNum(Integer overridePrintClassObjNum) {
        this.overridePrintClassObjNum = overridePrintClassObjNum;
    }

    public String getMainLevel() {
        return this.mainLevel;
    }

    public MenuItemDefinitionsCnC mainLevel(String mainLevel) {
        this.setMainLevel(mainLevel);
        return this;
    }

    public void setMainLevel(String mainLevel) {
        this.mainLevel = mainLevel;
    }

    public String getSubLevel() {
        return this.subLevel;
    }

    public MenuItemDefinitionsCnC subLevel(String subLevel) {
        this.setSubLevel(subLevel);
        return this;
    }

    public void setSubLevel(String subLevel) {
        this.subLevel = subLevel;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public MenuItemDefinitionsCnC quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getKdsPrepTime() {
        return this.kdsPrepTime;
    }

    public MenuItemDefinitionsCnC kdsPrepTime(Double kdsPrepTime) {
        this.setKdsPrepTime(kdsPrepTime);
        return this;
    }

    public void setKdsPrepTime(Double kdsPrepTime) {
        this.kdsPrepTime = kdsPrepTime;
    }

    public Integer getPrefixLevelOverride() {
        return this.prefixLevelOverride;
    }

    public MenuItemDefinitionsCnC prefixLevelOverride(Integer prefixLevelOverride) {
        this.setPrefixLevelOverride(prefixLevelOverride);
        return this;
    }

    public void setPrefixLevelOverride(Integer prefixLevelOverride) {
        this.prefixLevelOverride = prefixLevelOverride;
    }

    public Integer getGuestCount() {
        return this.guestCount;
    }

    public MenuItemDefinitionsCnC guestCount(Integer guestCount) {
        this.setGuestCount(guestCount);
        return this;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public String getSlu1ObjNum() {
        return this.slu1ObjNum;
    }

    public MenuItemDefinitionsCnC slu1ObjNum(String slu1ObjNum) {
        this.setSlu1ObjNum(slu1ObjNum);
        return this;
    }

    public void setSlu1ObjNum(String slu1ObjNum) {
        this.slu1ObjNum = slu1ObjNum;
    }

    public String getSlu2ObjNum() {
        return this.slu2ObjNum;
    }

    public MenuItemDefinitionsCnC slu2ObjNum(String slu2ObjNum) {
        this.setSlu2ObjNum(slu2ObjNum);
        return this;
    }

    public void setSlu2ObjNum(String slu2ObjNum) {
        this.slu2ObjNum = slu2ObjNum;
    }

    public String getSlu3ObjNum() {
        return this.slu3ObjNum;
    }

    public MenuItemDefinitionsCnC slu3ObjNum(String slu3ObjNum) {
        this.setSlu3ObjNum(slu3ObjNum);
        return this;
    }

    public void setSlu3ObjNum(String slu3ObjNum) {
        this.slu3ObjNum = slu3ObjNum;
    }

    public String getSlu4ObjNum() {
        return this.slu4ObjNum;
    }

    public MenuItemDefinitionsCnC slu4ObjNum(String slu4ObjNum) {
        this.setSlu4ObjNum(slu4ObjNum);
        return this;
    }

    public void setSlu4ObjNum(String slu4ObjNum) {
        this.slu4ObjNum = slu4ObjNum;
    }

    public String getSlu5ObjNum() {
        return this.slu5ObjNum;
    }

    public MenuItemDefinitionsCnC slu5ObjNum(String slu5ObjNum) {
        this.setSlu5ObjNum(slu5ObjNum);
        return this;
    }

    public void setSlu5ObjNum(String slu5ObjNum) {
        this.slu5ObjNum = slu5ObjNum;
    }

    public String getSlu6ObjNum() {
        return this.slu6ObjNum;
    }

    public MenuItemDefinitionsCnC slu6ObjNum(String slu6ObjNum) {
        this.setSlu6ObjNum(slu6ObjNum);
        return this;
    }

    public void setSlu6ObjNum(String slu6ObjNum) {
        this.slu6ObjNum = slu6ObjNum;
    }

    public String getSlu7ObjNum() {
        return this.slu7ObjNum;
    }

    public MenuItemDefinitionsCnC slu7ObjNum(String slu7ObjNum) {
        this.setSlu7ObjNum(slu7ObjNum);
        return this;
    }

    public void setSlu7ObjNum(String slu7ObjNum) {
        this.slu7ObjNum = slu7ObjNum;
    }

    public String getSlu8ObjNum() {
        return this.slu8ObjNum;
    }

    public MenuItemDefinitionsCnC slu8ObjNum(String slu8ObjNum) {
        this.setSlu8ObjNum(slu8ObjNum);
        return this;
    }

    public void setSlu8ObjNum(String slu8ObjNum) {
        this.slu8ObjNum = slu8ObjNum;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public MenuItemDefinitionsCnC firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuItemDefinitionsCnC)) {
            return false;
        }
        return getId() != null && getId().equals(((MenuItemDefinitionsCnC) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MenuItemDefinitionsCnC{" +
            "id=" + getId() +
            ", hierUnitId=" + getHierUnitId() +
            ", menuItemMasterObjNum=" + getMenuItemMasterObjNum() +
            ", menuItemMasterId=" + getMenuItemMasterId() +
            ", menuItemDefinitionId=" + getMenuItemDefinitionId() +
            ", defSequenceNum=" + getDefSequenceNum() +
            ", menuItemClassObjNum=" + getMenuItemClassObjNum() +
            ", overridePrintClassObjNum=" + getOverridePrintClassObjNum() +
            ", mainLevel='" + getMainLevel() + "'" +
            ", subLevel='" + getSubLevel() + "'" +
            ", quantity=" + getQuantity() +
            ", kdsPrepTime=" + getKdsPrepTime() +
            ", prefixLevelOverride=" + getPrefixLevelOverride() +
            ", guestCount=" + getGuestCount() +
            ", slu1ObjNum='" + getSlu1ObjNum() + "'" +
            ", slu2ObjNum='" + getSlu2ObjNum() + "'" +
            ", slu3ObjNum='" + getSlu3ObjNum() + "'" +
            ", slu4ObjNum='" + getSlu4ObjNum() + "'" +
            ", slu5ObjNum='" + getSlu5ObjNum() + "'" +
            ", slu6ObjNum='" + getSlu6ObjNum() + "'" +
            ", slu7ObjNum='" + getSlu7ObjNum() + "'" +
            ", slu8ObjNum='" + getSlu8ObjNum() + "'" +
            ", firstName='" + getFirstName() + "'" +
            "}";
    }
}
