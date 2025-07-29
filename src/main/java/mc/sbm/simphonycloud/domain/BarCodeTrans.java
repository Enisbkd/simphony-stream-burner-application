package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BarCodeTrans.
 */
@Entity
@Table(name = "bar_code_trans")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BarCodeTrans implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "loc_ref")
    private String locRef;

    @Column(name = "rvc_ref")
    private Integer rvcRef;

    @Column(name = "barcode_id")
    private Integer barcodeId;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "menu_item_id")
    private Integer menuItemId;

    @Column(name = "defenition_sequence")
    private Integer defenitionSequence;

    @Column(name = "price")
    private Double price;

    @Column(name = "price_sequence")
    private Integer priceSequence;

    @Column(name = "preparation_cost")
    private Double preparationCost;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public BarCodeTrans id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocRef() {
        return this.locRef;
    }

    public BarCodeTrans locRef(String locRef) {
        this.setLocRef(locRef);
        return this;
    }

    public void setLocRef(String locRef) {
        this.locRef = locRef;
    }

    public Integer getRvcRef() {
        return this.rvcRef;
    }

    public BarCodeTrans rvcRef(Integer rvcRef) {
        this.setRvcRef(rvcRef);
        return this;
    }

    public void setRvcRef(Integer rvcRef) {
        this.rvcRef = rvcRef;
    }

    public Integer getBarcodeId() {
        return this.barcodeId;
    }

    public BarCodeTrans barcodeId(Integer barcodeId) {
        this.setBarcodeId(barcodeId);
        return this;
    }

    public void setBarcodeId(Integer barcodeId) {
        this.barcodeId = barcodeId;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public BarCodeTrans barcode(String barcode) {
        this.setBarcode(barcode);
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getMenuItemId() {
        return this.menuItemId;
    }

    public BarCodeTrans menuItemId(Integer menuItemId) {
        this.setMenuItemId(menuItemId);
        return this;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Integer getDefenitionSequence() {
        return this.defenitionSequence;
    }

    public BarCodeTrans defenitionSequence(Integer defenitionSequence) {
        this.setDefenitionSequence(defenitionSequence);
        return this;
    }

    public void setDefenitionSequence(Integer defenitionSequence) {
        this.defenitionSequence = defenitionSequence;
    }

    public Double getPrice() {
        return this.price;
    }

    public BarCodeTrans price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getPriceSequence() {
        return this.priceSequence;
    }

    public BarCodeTrans priceSequence(Integer priceSequence) {
        this.setPriceSequence(priceSequence);
        return this;
    }

    public void setPriceSequence(Integer priceSequence) {
        this.priceSequence = priceSequence;
    }

    public Double getPreparationCost() {
        return this.preparationCost;
    }

    public BarCodeTrans preparationCost(Double preparationCost) {
        this.setPreparationCost(preparationCost);
        return this;
    }

    public void setPreparationCost(Double preparationCost) {
        this.preparationCost = preparationCost;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BarCodeTrans)) {
            return false;
        }
        return getId() != null && getId().equals(((BarCodeTrans) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BarCodeTrans{" +
            "id=" + getId() +
            ", locRef='" + getLocRef() + "'" +
            ", rvcRef=" + getRvcRef() +
            ", barcodeId=" + getBarcodeId() +
            ", barcode='" + getBarcode() + "'" +
            ", menuItemId=" + getMenuItemId() +
            ", defenitionSequence=" + getDefenitionSequence() +
            ", price=" + getPrice() +
            ", priceSequence=" + getPriceSequence() +
            ", preparationCost=" + getPreparationCost() +
            "}";
    }
}
