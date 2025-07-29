package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MenuItemPricesCnC.
 */
@Entity
@Table(name = "menu_item_prices_cn_c")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MenuItemPricesCnC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "hier_unit_id")
    private Long hierUnitId;

    @Column(name = "menu_item_price_id")
    private Long menuItemPriceId;

    @Column(name = "menu_item_master_id")
    private Long menuItemMasterId;

    @Column(name = "menu_item_master_obj_num")
    private Long menuItemMasterObjNum;

    @Column(name = "menu_item_definition_id")
    private Long menuItemDefinitionId;

    @Column(name = "def_sequence_num")
    private Integer defSequenceNum;

    @Column(name = "external_reference_1")
    private String externalReference1;

    @Column(name = "external_reference_2")
    private String externalReference2;

    @Column(name = "price_sequence_num")
    private Integer priceSequenceNum;

    @Column(name = "active_on_menu_level")
    private Integer activeOnMenuLevel;

    @Column(name = "effectivity_group_obj_num")
    private String effectivityGroupObjNum;

    @Column(name = "prep_cost")
    private Double prepCost;

    @Column(name = "price")
    private Double price;

    @Column(name = "options")
    private String options;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MenuItemPricesCnC id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHierUnitId() {
        return this.hierUnitId;
    }

    public MenuItemPricesCnC hierUnitId(Long hierUnitId) {
        this.setHierUnitId(hierUnitId);
        return this;
    }

    public void setHierUnitId(Long hierUnitId) {
        this.hierUnitId = hierUnitId;
    }

    public Long getMenuItemPriceId() {
        return this.menuItemPriceId;
    }

    public MenuItemPricesCnC menuItemPriceId(Long menuItemPriceId) {
        this.setMenuItemPriceId(menuItemPriceId);
        return this;
    }

    public void setMenuItemPriceId(Long menuItemPriceId) {
        this.menuItemPriceId = menuItemPriceId;
    }

    public Long getMenuItemMasterId() {
        return this.menuItemMasterId;
    }

    public MenuItemPricesCnC menuItemMasterId(Long menuItemMasterId) {
        this.setMenuItemMasterId(menuItemMasterId);
        return this;
    }

    public void setMenuItemMasterId(Long menuItemMasterId) {
        this.menuItemMasterId = menuItemMasterId;
    }

    public Long getMenuItemMasterObjNum() {
        return this.menuItemMasterObjNum;
    }

    public MenuItemPricesCnC menuItemMasterObjNum(Long menuItemMasterObjNum) {
        this.setMenuItemMasterObjNum(menuItemMasterObjNum);
        return this;
    }

    public void setMenuItemMasterObjNum(Long menuItemMasterObjNum) {
        this.menuItemMasterObjNum = menuItemMasterObjNum;
    }

    public Long getMenuItemDefinitionId() {
        return this.menuItemDefinitionId;
    }

    public MenuItemPricesCnC menuItemDefinitionId(Long menuItemDefinitionId) {
        this.setMenuItemDefinitionId(menuItemDefinitionId);
        return this;
    }

    public void setMenuItemDefinitionId(Long menuItemDefinitionId) {
        this.menuItemDefinitionId = menuItemDefinitionId;
    }

    public Integer getDefSequenceNum() {
        return this.defSequenceNum;
    }

    public MenuItemPricesCnC defSequenceNum(Integer defSequenceNum) {
        this.setDefSequenceNum(defSequenceNum);
        return this;
    }

    public void setDefSequenceNum(Integer defSequenceNum) {
        this.defSequenceNum = defSequenceNum;
    }

    public String getExternalReference1() {
        return this.externalReference1;
    }

    public MenuItemPricesCnC externalReference1(String externalReference1) {
        this.setExternalReference1(externalReference1);
        return this;
    }

    public void setExternalReference1(String externalReference1) {
        this.externalReference1 = externalReference1;
    }

    public String getExternalReference2() {
        return this.externalReference2;
    }

    public MenuItemPricesCnC externalReference2(String externalReference2) {
        this.setExternalReference2(externalReference2);
        return this;
    }

    public void setExternalReference2(String externalReference2) {
        this.externalReference2 = externalReference2;
    }

    public Integer getPriceSequenceNum() {
        return this.priceSequenceNum;
    }

    public MenuItemPricesCnC priceSequenceNum(Integer priceSequenceNum) {
        this.setPriceSequenceNum(priceSequenceNum);
        return this;
    }

    public void setPriceSequenceNum(Integer priceSequenceNum) {
        this.priceSequenceNum = priceSequenceNum;
    }

    public Integer getActiveOnMenuLevel() {
        return this.activeOnMenuLevel;
    }

    public MenuItemPricesCnC activeOnMenuLevel(Integer activeOnMenuLevel) {
        this.setActiveOnMenuLevel(activeOnMenuLevel);
        return this;
    }

    public void setActiveOnMenuLevel(Integer activeOnMenuLevel) {
        this.activeOnMenuLevel = activeOnMenuLevel;
    }

    public String getEffectivityGroupObjNum() {
        return this.effectivityGroupObjNum;
    }

    public MenuItemPricesCnC effectivityGroupObjNum(String effectivityGroupObjNum) {
        this.setEffectivityGroupObjNum(effectivityGroupObjNum);
        return this;
    }

    public void setEffectivityGroupObjNum(String effectivityGroupObjNum) {
        this.effectivityGroupObjNum = effectivityGroupObjNum;
    }

    public Double getPrepCost() {
        return this.prepCost;
    }

    public MenuItemPricesCnC prepCost(Double prepCost) {
        this.setPrepCost(prepCost);
        return this;
    }

    public void setPrepCost(Double prepCost) {
        this.prepCost = prepCost;
    }

    public Double getPrice() {
        return this.price;
    }

    public MenuItemPricesCnC price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getOptions() {
        return this.options;
    }

    public MenuItemPricesCnC options(String options) {
        this.setOptions(options);
        return this;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuItemPricesCnC)) {
            return false;
        }
        return getId() != null && getId().equals(((MenuItemPricesCnC) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MenuItemPricesCnC{" +
            "id=" + getId() +
            ", hierUnitId=" + getHierUnitId() +
            ", menuItemPriceId=" + getMenuItemPriceId() +
            ", menuItemMasterId=" + getMenuItemMasterId() +
            ", menuItemMasterObjNum=" + getMenuItemMasterObjNum() +
            ", menuItemDefinitionId=" + getMenuItemDefinitionId() +
            ", defSequenceNum=" + getDefSequenceNum() +
            ", externalReference1='" + getExternalReference1() + "'" +
            ", externalReference2='" + getExternalReference2() + "'" +
            ", priceSequenceNum=" + getPriceSequenceNum() +
            ", activeOnMenuLevel=" + getActiveOnMenuLevel() +
            ", effectivityGroupObjNum='" + getEffectivityGroupObjNum() + "'" +
            ", prepCost=" + getPrepCost() +
            ", price=" + getPrice() +
            ", options='" + getOptions() + "'" +
            "}";
    }
}
