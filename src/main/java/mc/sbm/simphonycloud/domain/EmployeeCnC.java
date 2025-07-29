package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EmployeeCnC.
 */
@Entity
@Table(name = "employee_cn_c")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeCnC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "object_num")
    private Integer objectNum;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "check_name")
    private String checkName;

    @Column(name = "email")
    private String email;

    @Column(name = "language_obj_num")
    private Integer languageObjNum;

    @Column(name = "lang_id")
    private Integer langId;

    @Column(name = "alternate_id")
    private Integer alternateId;

    @Column(name = "level")
    private Integer level;

    @Column(name = "jhi_group")
    private Integer group;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_role_hier_unit_id")
    private Integer firstRoleHierUnitId;

    @Column(name = "first_role_obj_num")
    private Integer firstRoleObjNum;

    @Column(name = "first_visibility_hier_unit_id")
    private Integer firstVisibilityHierUnitId;

    @Column(name = "first_visibility_propagate_to_children")
    private Boolean firstVisibilityPropagateToChildren;

    @Column(name = "first_property_hier_unit_id")
    private Integer firstPropertyHierUnitId;

    @Column(name = "first_property_obj_num")
    private Integer firstPropertyObjNum;

    @Column(name = "first_property_emp_class_obj_num")
    private Integer firstPropertyEmpClassObjNum;

    @Column(name = "first_property_options")
    private String firstPropertyOptions;

    @Column(name = "first_operator_rvc_hier_unit_id")
    private Integer firstOperatorRvcHierUnitId;

    @Column(name = "first_operator_rvc_obj_num")
    private Integer firstOperatorRvcObjNum;

    @Column(name = "first_operator_options")
    private String firstOperatorOptions;

    @Column(name = "first_operator_cash_drawer_obj_num")
    private Integer firstOperatorCashDrawerObjNum;

    @Column(name = "first_operator_tms_color_obj_num")
    private Integer firstOperatorTmsColorObjNum;

    @Column(name = "first_operator_server_efficiency")
    private Integer firstOperatorServerEfficiency;

    @Column(name = "pin")
    private String pin;

    @Column(name = "access_id")
    private Integer accessId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmployeeCnC id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getObjectNum() {
        return this.objectNum;
    }

    public EmployeeCnC objectNum(Integer objectNum) {
        this.setObjectNum(objectNum);
        return this;
    }

    public void setObjectNum(Integer objectNum) {
        this.objectNum = objectNum;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public EmployeeCnC firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public EmployeeCnC lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCheckName() {
        return this.checkName;
    }

    public EmployeeCnC checkName(String checkName) {
        this.setCheckName(checkName);
        return this;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getEmail() {
        return this.email;
    }

    public EmployeeCnC email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLanguageObjNum() {
        return this.languageObjNum;
    }

    public EmployeeCnC languageObjNum(Integer languageObjNum) {
        this.setLanguageObjNum(languageObjNum);
        return this;
    }

    public void setLanguageObjNum(Integer languageObjNum) {
        this.languageObjNum = languageObjNum;
    }

    public Integer getLangId() {
        return this.langId;
    }

    public EmployeeCnC langId(Integer langId) {
        this.setLangId(langId);
        return this;
    }

    public void setLangId(Integer langId) {
        this.langId = langId;
    }

    public Integer getAlternateId() {
        return this.alternateId;
    }

    public EmployeeCnC alternateId(Integer alternateId) {
        this.setAlternateId(alternateId);
        return this;
    }

    public void setAlternateId(Integer alternateId) {
        this.alternateId = alternateId;
    }

    public Integer getLevel() {
        return this.level;
    }

    public EmployeeCnC level(Integer level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getGroup() {
        return this.group;
    }

    public EmployeeCnC group(Integer group) {
        this.setGroup(group);
        return this;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getUserName() {
        return this.userName;
    }

    public EmployeeCnC userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getFirstRoleHierUnitId() {
        return this.firstRoleHierUnitId;
    }

    public EmployeeCnC firstRoleHierUnitId(Integer firstRoleHierUnitId) {
        this.setFirstRoleHierUnitId(firstRoleHierUnitId);
        return this;
    }

    public void setFirstRoleHierUnitId(Integer firstRoleHierUnitId) {
        this.firstRoleHierUnitId = firstRoleHierUnitId;
    }

    public Integer getFirstRoleObjNum() {
        return this.firstRoleObjNum;
    }

    public EmployeeCnC firstRoleObjNum(Integer firstRoleObjNum) {
        this.setFirstRoleObjNum(firstRoleObjNum);
        return this;
    }

    public void setFirstRoleObjNum(Integer firstRoleObjNum) {
        this.firstRoleObjNum = firstRoleObjNum;
    }

    public Integer getFirstVisibilityHierUnitId() {
        return this.firstVisibilityHierUnitId;
    }

    public EmployeeCnC firstVisibilityHierUnitId(Integer firstVisibilityHierUnitId) {
        this.setFirstVisibilityHierUnitId(firstVisibilityHierUnitId);
        return this;
    }

    public void setFirstVisibilityHierUnitId(Integer firstVisibilityHierUnitId) {
        this.firstVisibilityHierUnitId = firstVisibilityHierUnitId;
    }

    public Boolean getFirstVisibilityPropagateToChildren() {
        return this.firstVisibilityPropagateToChildren;
    }

    public EmployeeCnC firstVisibilityPropagateToChildren(Boolean firstVisibilityPropagateToChildren) {
        this.setFirstVisibilityPropagateToChildren(firstVisibilityPropagateToChildren);
        return this;
    }

    public void setFirstVisibilityPropagateToChildren(Boolean firstVisibilityPropagateToChildren) {
        this.firstVisibilityPropagateToChildren = firstVisibilityPropagateToChildren;
    }

    public Integer getFirstPropertyHierUnitId() {
        return this.firstPropertyHierUnitId;
    }

    public EmployeeCnC firstPropertyHierUnitId(Integer firstPropertyHierUnitId) {
        this.setFirstPropertyHierUnitId(firstPropertyHierUnitId);
        return this;
    }

    public void setFirstPropertyHierUnitId(Integer firstPropertyHierUnitId) {
        this.firstPropertyHierUnitId = firstPropertyHierUnitId;
    }

    public Integer getFirstPropertyObjNum() {
        return this.firstPropertyObjNum;
    }

    public EmployeeCnC firstPropertyObjNum(Integer firstPropertyObjNum) {
        this.setFirstPropertyObjNum(firstPropertyObjNum);
        return this;
    }

    public void setFirstPropertyObjNum(Integer firstPropertyObjNum) {
        this.firstPropertyObjNum = firstPropertyObjNum;
    }

    public Integer getFirstPropertyEmpClassObjNum() {
        return this.firstPropertyEmpClassObjNum;
    }

    public EmployeeCnC firstPropertyEmpClassObjNum(Integer firstPropertyEmpClassObjNum) {
        this.setFirstPropertyEmpClassObjNum(firstPropertyEmpClassObjNum);
        return this;
    }

    public void setFirstPropertyEmpClassObjNum(Integer firstPropertyEmpClassObjNum) {
        this.firstPropertyEmpClassObjNum = firstPropertyEmpClassObjNum;
    }

    public String getFirstPropertyOptions() {
        return this.firstPropertyOptions;
    }

    public EmployeeCnC firstPropertyOptions(String firstPropertyOptions) {
        this.setFirstPropertyOptions(firstPropertyOptions);
        return this;
    }

    public void setFirstPropertyOptions(String firstPropertyOptions) {
        this.firstPropertyOptions = firstPropertyOptions;
    }

    public Integer getFirstOperatorRvcHierUnitId() {
        return this.firstOperatorRvcHierUnitId;
    }

    public EmployeeCnC firstOperatorRvcHierUnitId(Integer firstOperatorRvcHierUnitId) {
        this.setFirstOperatorRvcHierUnitId(firstOperatorRvcHierUnitId);
        return this;
    }

    public void setFirstOperatorRvcHierUnitId(Integer firstOperatorRvcHierUnitId) {
        this.firstOperatorRvcHierUnitId = firstOperatorRvcHierUnitId;
    }

    public Integer getFirstOperatorRvcObjNum() {
        return this.firstOperatorRvcObjNum;
    }

    public EmployeeCnC firstOperatorRvcObjNum(Integer firstOperatorRvcObjNum) {
        this.setFirstOperatorRvcObjNum(firstOperatorRvcObjNum);
        return this;
    }

    public void setFirstOperatorRvcObjNum(Integer firstOperatorRvcObjNum) {
        this.firstOperatorRvcObjNum = firstOperatorRvcObjNum;
    }

    public String getFirstOperatorOptions() {
        return this.firstOperatorOptions;
    }

    public EmployeeCnC firstOperatorOptions(String firstOperatorOptions) {
        this.setFirstOperatorOptions(firstOperatorOptions);
        return this;
    }

    public void setFirstOperatorOptions(String firstOperatorOptions) {
        this.firstOperatorOptions = firstOperatorOptions;
    }

    public Integer getFirstOperatorCashDrawerObjNum() {
        return this.firstOperatorCashDrawerObjNum;
    }

    public EmployeeCnC firstOperatorCashDrawerObjNum(Integer firstOperatorCashDrawerObjNum) {
        this.setFirstOperatorCashDrawerObjNum(firstOperatorCashDrawerObjNum);
        return this;
    }

    public void setFirstOperatorCashDrawerObjNum(Integer firstOperatorCashDrawerObjNum) {
        this.firstOperatorCashDrawerObjNum = firstOperatorCashDrawerObjNum;
    }

    public Integer getFirstOperatorTmsColorObjNum() {
        return this.firstOperatorTmsColorObjNum;
    }

    public EmployeeCnC firstOperatorTmsColorObjNum(Integer firstOperatorTmsColorObjNum) {
        this.setFirstOperatorTmsColorObjNum(firstOperatorTmsColorObjNum);
        return this;
    }

    public void setFirstOperatorTmsColorObjNum(Integer firstOperatorTmsColorObjNum) {
        this.firstOperatorTmsColorObjNum = firstOperatorTmsColorObjNum;
    }

    public Integer getFirstOperatorServerEfficiency() {
        return this.firstOperatorServerEfficiency;
    }

    public EmployeeCnC firstOperatorServerEfficiency(Integer firstOperatorServerEfficiency) {
        this.setFirstOperatorServerEfficiency(firstOperatorServerEfficiency);
        return this;
    }

    public void setFirstOperatorServerEfficiency(Integer firstOperatorServerEfficiency) {
        this.firstOperatorServerEfficiency = firstOperatorServerEfficiency;
    }

    public String getPin() {
        return this.pin;
    }

    public EmployeeCnC pin(String pin) {
        this.setPin(pin);
        return this;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Integer getAccessId() {
        return this.accessId;
    }

    public EmployeeCnC accessId(Integer accessId) {
        this.setAccessId(accessId);
        return this;
    }

    public void setAccessId(Integer accessId) {
        this.accessId = accessId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeCnC)) {
            return false;
        }
        return getId() != null && getId().equals(((EmployeeCnC) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCnC{" +
            "id=" + getId() +
            ", objectNum=" + getObjectNum() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", checkName='" + getCheckName() + "'" +
            ", email='" + getEmail() + "'" +
            ", languageObjNum=" + getLanguageObjNum() +
            ", langId=" + getLangId() +
            ", alternateId=" + getAlternateId() +
            ", level=" + getLevel() +
            ", group=" + getGroup() +
            ", userName='" + getUserName() + "'" +
            ", firstRoleHierUnitId=" + getFirstRoleHierUnitId() +
            ", firstRoleObjNum=" + getFirstRoleObjNum() +
            ", firstVisibilityHierUnitId=" + getFirstVisibilityHierUnitId() +
            ", firstVisibilityPropagateToChildren='" + getFirstVisibilityPropagateToChildren() + "'" +
            ", firstPropertyHierUnitId=" + getFirstPropertyHierUnitId() +
            ", firstPropertyObjNum=" + getFirstPropertyObjNum() +
            ", firstPropertyEmpClassObjNum=" + getFirstPropertyEmpClassObjNum() +
            ", firstPropertyOptions='" + getFirstPropertyOptions() + "'" +
            ", firstOperatorRvcHierUnitId=" + getFirstOperatorRvcHierUnitId() +
            ", firstOperatorRvcObjNum=" + getFirstOperatorRvcObjNum() +
            ", firstOperatorOptions='" + getFirstOperatorOptions() + "'" +
            ", firstOperatorCashDrawerObjNum=" + getFirstOperatorCashDrawerObjNum() +
            ", firstOperatorTmsColorObjNum=" + getFirstOperatorTmsColorObjNum() +
            ", firstOperatorServerEfficiency=" + getFirstOperatorServerEfficiency() +
            ", pin='" + getPin() + "'" +
            ", accessId=" + getAccessId() +
            "}";
    }
}
