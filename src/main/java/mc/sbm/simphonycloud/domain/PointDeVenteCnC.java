package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PointDeVenteCnC.
 */
@Entity
@Table(name = "point_de_vente_cn_c")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PointDeVenteCnC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "loc_hier_unit_id")
    private Integer locHierUnitId;

    @Column(name = "loc_obj_num")
    private Integer locObjNum;

    @Column(name = "rvc_id")
    private Integer rvcId;

    @Column(name = "kds_controller_id")
    private Integer kdsControllerId;

    @Column(name = "hier_unit_id")
    private Integer hierUnitId;

    @Column(name = "object_num")
    private Integer objectNum;

    @Column(name = "name")
    private String name;

    @Column(name = "data_extensions")
    private String dataExtensions;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public PointDeVenteCnC id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLocHierUnitId() {
        return this.locHierUnitId;
    }

    public PointDeVenteCnC locHierUnitId(Integer locHierUnitId) {
        this.setLocHierUnitId(locHierUnitId);
        return this;
    }

    public void setLocHierUnitId(Integer locHierUnitId) {
        this.locHierUnitId = locHierUnitId;
    }

    public Integer getLocObjNum() {
        return this.locObjNum;
    }

    public PointDeVenteCnC locObjNum(Integer locObjNum) {
        this.setLocObjNum(locObjNum);
        return this;
    }

    public void setLocObjNum(Integer locObjNum) {
        this.locObjNum = locObjNum;
    }

    public Integer getRvcId() {
        return this.rvcId;
    }

    public PointDeVenteCnC rvcId(Integer rvcId) {
        this.setRvcId(rvcId);
        return this;
    }

    public void setRvcId(Integer rvcId) {
        this.rvcId = rvcId;
    }

    public Integer getKdsControllerId() {
        return this.kdsControllerId;
    }

    public PointDeVenteCnC kdsControllerId(Integer kdsControllerId) {
        this.setKdsControllerId(kdsControllerId);
        return this;
    }

    public void setKdsControllerId(Integer kdsControllerId) {
        this.kdsControllerId = kdsControllerId;
    }

    public Integer getHierUnitId() {
        return this.hierUnitId;
    }

    public PointDeVenteCnC hierUnitId(Integer hierUnitId) {
        this.setHierUnitId(hierUnitId);
        return this;
    }

    public void setHierUnitId(Integer hierUnitId) {
        this.hierUnitId = hierUnitId;
    }

    public Integer getObjectNum() {
        return this.objectNum;
    }

    public PointDeVenteCnC objectNum(Integer objectNum) {
        this.setObjectNum(objectNum);
        return this;
    }

    public void setObjectNum(Integer objectNum) {
        this.objectNum = objectNum;
    }

    public String getName() {
        return this.name;
    }

    public PointDeVenteCnC name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataExtensions() {
        return this.dataExtensions;
    }

    public PointDeVenteCnC dataExtensions(String dataExtensions) {
        this.setDataExtensions(dataExtensions);
        return this;
    }

    public void setDataExtensions(String dataExtensions) {
        this.dataExtensions = dataExtensions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointDeVenteCnC)) {
            return false;
        }
        return getId() != null && getId().equals(((PointDeVenteCnC) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointDeVenteCnC{" +
            "id=" + getId() +
            ", locHierUnitId=" + getLocHierUnitId() +
            ", locObjNum=" + getLocObjNum() +
            ", rvcId=" + getRvcId() +
            ", kdsControllerId=" + getKdsControllerId() +
            ", hierUnitId=" + getHierUnitId() +
            ", objectNum=" + getObjectNum() +
            ", name='" + getName() + "'" +
            ", dataExtensions='" + getDataExtensions() + "'" +
            "}";
    }
}
