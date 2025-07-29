package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CheckTrans.
 */
@Entity
@Table(name = "check_trans")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckTrans implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "rvc_ref")
    private Integer rvcRef;

    @Column(name = "check_ref")
    private String checkRef;

    @Column(name = "check_number")
    private Integer checkNumber;

    @Column(name = "check_name")
    private String checkName;

    @Column(name = "check_employee_ref")
    private Integer checkEmployeeRef;

    @Column(name = "order_type_ref")
    private Integer orderTypeRef;

    @Column(name = "order_channel_ref")
    private Integer orderChannelRef;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "table_group_number")
    private Integer tableGroupNumber;

    @Column(name = "open_time")
    private Instant openTime;

    @Column(name = "guest_count")
    private Integer guestCount;

    @Column(name = "language")
    private String language;

    @Column(name = "is_training_check")
    private Boolean isTrainingCheck;

    @Column(name = "status")
    private String status;

    @Column(name = "preparation_status")
    private String preparationStatus;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "subtotal_discount_total")
    private Double subtotalDiscountTotal;

    @Column(name = "auto_service_charge_total")
    private Double autoServiceChargeTotal;

    @Column(name = "service_charge_total")
    private Double serviceChargeTotal;

    @Column(name = "tax_total")
    private Double taxTotal;

    @Column(name = "payment_total")
    private Double paymentTotal;

    @Column(name = "total_due")
    private Double totalDue;

    @Column(name = "tax_rate_id")
    private Integer taxRateId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public CheckTrans id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRvcRef() {
        return this.rvcRef;
    }

    public CheckTrans rvcRef(Integer rvcRef) {
        this.setRvcRef(rvcRef);
        return this;
    }

    public void setRvcRef(Integer rvcRef) {
        this.rvcRef = rvcRef;
    }

    public String getCheckRef() {
        return this.checkRef;
    }

    public CheckTrans checkRef(String checkRef) {
        this.setCheckRef(checkRef);
        return this;
    }

    public void setCheckRef(String checkRef) {
        this.checkRef = checkRef;
    }

    public Integer getCheckNumber() {
        return this.checkNumber;
    }

    public CheckTrans checkNumber(Integer checkNumber) {
        this.setCheckNumber(checkNumber);
        return this;
    }

    public void setCheckNumber(Integer checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getCheckName() {
        return this.checkName;
    }

    public CheckTrans checkName(String checkName) {
        this.setCheckName(checkName);
        return this;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public Integer getCheckEmployeeRef() {
        return this.checkEmployeeRef;
    }

    public CheckTrans checkEmployeeRef(Integer checkEmployeeRef) {
        this.setCheckEmployeeRef(checkEmployeeRef);
        return this;
    }

    public void setCheckEmployeeRef(Integer checkEmployeeRef) {
        this.checkEmployeeRef = checkEmployeeRef;
    }

    public Integer getOrderTypeRef() {
        return this.orderTypeRef;
    }

    public CheckTrans orderTypeRef(Integer orderTypeRef) {
        this.setOrderTypeRef(orderTypeRef);
        return this;
    }

    public void setOrderTypeRef(Integer orderTypeRef) {
        this.orderTypeRef = orderTypeRef;
    }

    public Integer getOrderChannelRef() {
        return this.orderChannelRef;
    }

    public CheckTrans orderChannelRef(Integer orderChannelRef) {
        this.setOrderChannelRef(orderChannelRef);
        return this;
    }

    public void setOrderChannelRef(Integer orderChannelRef) {
        this.orderChannelRef = orderChannelRef;
    }

    public String getTableName() {
        return this.tableName;
    }

    public CheckTrans tableName(String tableName) {
        this.setTableName(tableName);
        return this;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getTableGroupNumber() {
        return this.tableGroupNumber;
    }

    public CheckTrans tableGroupNumber(Integer tableGroupNumber) {
        this.setTableGroupNumber(tableGroupNumber);
        return this;
    }

    public void setTableGroupNumber(Integer tableGroupNumber) {
        this.tableGroupNumber = tableGroupNumber;
    }

    public Instant getOpenTime() {
        return this.openTime;
    }

    public CheckTrans openTime(Instant openTime) {
        this.setOpenTime(openTime);
        return this;
    }

    public void setOpenTime(Instant openTime) {
        this.openTime = openTime;
    }

    public Integer getGuestCount() {
        return this.guestCount;
    }

    public CheckTrans guestCount(Integer guestCount) {
        this.setGuestCount(guestCount);
        return this;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public String getLanguage() {
        return this.language;
    }

    public CheckTrans language(String language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getIsTrainingCheck() {
        return this.isTrainingCheck;
    }

    public CheckTrans isTrainingCheck(Boolean isTrainingCheck) {
        this.setIsTrainingCheck(isTrainingCheck);
        return this;
    }

    public void setIsTrainingCheck(Boolean isTrainingCheck) {
        this.isTrainingCheck = isTrainingCheck;
    }

    public String getStatus() {
        return this.status;
    }

    public CheckTrans status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPreparationStatus() {
        return this.preparationStatus;
    }

    public CheckTrans preparationStatus(String preparationStatus) {
        this.setPreparationStatus(preparationStatus);
        return this;
    }

    public void setPreparationStatus(String preparationStatus) {
        this.preparationStatus = preparationStatus;
    }

    public Double getSubtotal() {
        return this.subtotal;
    }

    public CheckTrans subtotal(Double subtotal) {
        this.setSubtotal(subtotal);
        return this;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getSubtotalDiscountTotal() {
        return this.subtotalDiscountTotal;
    }

    public CheckTrans subtotalDiscountTotal(Double subtotalDiscountTotal) {
        this.setSubtotalDiscountTotal(subtotalDiscountTotal);
        return this;
    }

    public void setSubtotalDiscountTotal(Double subtotalDiscountTotal) {
        this.subtotalDiscountTotal = subtotalDiscountTotal;
    }

    public Double getAutoServiceChargeTotal() {
        return this.autoServiceChargeTotal;
    }

    public CheckTrans autoServiceChargeTotal(Double autoServiceChargeTotal) {
        this.setAutoServiceChargeTotal(autoServiceChargeTotal);
        return this;
    }

    public void setAutoServiceChargeTotal(Double autoServiceChargeTotal) {
        this.autoServiceChargeTotal = autoServiceChargeTotal;
    }

    public Double getServiceChargeTotal() {
        return this.serviceChargeTotal;
    }

    public CheckTrans serviceChargeTotal(Double serviceChargeTotal) {
        this.setServiceChargeTotal(serviceChargeTotal);
        return this;
    }

    public void setServiceChargeTotal(Double serviceChargeTotal) {
        this.serviceChargeTotal = serviceChargeTotal;
    }

    public Double getTaxTotal() {
        return this.taxTotal;
    }

    public CheckTrans taxTotal(Double taxTotal) {
        this.setTaxTotal(taxTotal);
        return this;
    }

    public void setTaxTotal(Double taxTotal) {
        this.taxTotal = taxTotal;
    }

    public Double getPaymentTotal() {
        return this.paymentTotal;
    }

    public CheckTrans paymentTotal(Double paymentTotal) {
        this.setPaymentTotal(paymentTotal);
        return this;
    }

    public void setPaymentTotal(Double paymentTotal) {
        this.paymentTotal = paymentTotal;
    }

    public Double getTotalDue() {
        return this.totalDue;
    }

    public CheckTrans totalDue(Double totalDue) {
        this.setTotalDue(totalDue);
        return this;
    }

    public void setTotalDue(Double totalDue) {
        this.totalDue = totalDue;
    }

    public Integer getTaxRateId() {
        return this.taxRateId;
    }

    public CheckTrans taxRateId(Integer taxRateId) {
        this.setTaxRateId(taxRateId);
        return this;
    }

    public void setTaxRateId(Integer taxRateId) {
        this.taxRateId = taxRateId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckTrans)) {
            return false;
        }
        return getId() != null && getId().equals(((CheckTrans) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckTrans{" +
            "id=" + getId() +
            ", rvcRef=" + getRvcRef() +
            ", checkRef='" + getCheckRef() + "'" +
            ", checkNumber=" + getCheckNumber() +
            ", checkName='" + getCheckName() + "'" +
            ", checkEmployeeRef=" + getCheckEmployeeRef() +
            ", orderTypeRef=" + getOrderTypeRef() +
            ", orderChannelRef=" + getOrderChannelRef() +
            ", tableName='" + getTableName() + "'" +
            ", tableGroupNumber=" + getTableGroupNumber() +
            ", openTime='" + getOpenTime() + "'" +
            ", guestCount=" + getGuestCount() +
            ", language='" + getLanguage() + "'" +
            ", isTrainingCheck='" + getIsTrainingCheck() + "'" +
            ", status='" + getStatus() + "'" +
            ", preparationStatus='" + getPreparationStatus() + "'" +
            ", subtotal=" + getSubtotal() +
            ", subtotalDiscountTotal=" + getSubtotalDiscountTotal() +
            ", autoServiceChargeTotal=" + getAutoServiceChargeTotal() +
            ", serviceChargeTotal=" + getServiceChargeTotal() +
            ", taxTotal=" + getTaxTotal() +
            ", paymentTotal=" + getPaymentTotal() +
            ", totalDue=" + getTotalDue() +
            ", taxRateId=" + getTaxRateId() +
            "}";
    }
}
