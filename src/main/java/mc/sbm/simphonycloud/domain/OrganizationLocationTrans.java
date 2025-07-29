package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrganizationLocationTrans.
 */
@Entity
@Table(name = "organization_location_trans")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationLocationTrans implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "org_short_name")
    private String orgShortName;

    @Column(name = "loc_ref")
    private String locRef;

    @Column(name = "name")
    private String name;

    @Column(name = "currency")
    private String currency;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "languages")
    private String languages;

    @Column(name = "timezone_iana_name")
    private String timezoneIanaName;

    @Column(name = "timezone_windows_name")
    private String timezoneWindowsName;

    @Column(name = "timezone_tz_index")
    private Integer timezoneTzIndex;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "address_floor")
    private String addressFloor;

    @Column(name = "address_locality")
    private String addressLocality;

    @Column(name = "address_region")
    private String addressRegion;

    @Column(name = "address_postal_code")
    private String addressPostalCode;

    @Column(name = "address_country")
    private String addressCountry;

    @Column(name = "address_notes")
    private String addressNotes;

    @Column(name = "geo_latitude", precision = 21, scale = 2)
    private BigDecimal geoLatitude;

    @Column(name = "geo_longitude", precision = 21, scale = 2)
    private BigDecimal geoLongitude;

    @Column(name = "pos_platform_name")
    private String posPlatformName;

    @Column(name = "pos_platform_version")
    private String posPlatformVersion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrganizationLocationTrans id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgShortName() {
        return this.orgShortName;
    }

    public OrganizationLocationTrans orgShortName(String orgShortName) {
        this.setOrgShortName(orgShortName);
        return this;
    }

    public void setOrgShortName(String orgShortName) {
        this.orgShortName = orgShortName;
    }

    public String getLocRef() {
        return this.locRef;
    }

    public OrganizationLocationTrans locRef(String locRef) {
        this.setLocRef(locRef);
        return this;
    }

    public void setLocRef(String locRef) {
        this.locRef = locRef;
    }

    public String getName() {
        return this.name;
    }

    public OrganizationLocationTrans name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return this.currency;
    }

    public OrganizationLocationTrans currency(String currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public OrganizationLocationTrans phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLanguages() {
        return this.languages;
    }

    public OrganizationLocationTrans languages(String languages) {
        this.setLanguages(languages);
        return this;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getTimezoneIanaName() {
        return this.timezoneIanaName;
    }

    public OrganizationLocationTrans timezoneIanaName(String timezoneIanaName) {
        this.setTimezoneIanaName(timezoneIanaName);
        return this;
    }

    public void setTimezoneIanaName(String timezoneIanaName) {
        this.timezoneIanaName = timezoneIanaName;
    }

    public String getTimezoneWindowsName() {
        return this.timezoneWindowsName;
    }

    public OrganizationLocationTrans timezoneWindowsName(String timezoneWindowsName) {
        this.setTimezoneWindowsName(timezoneWindowsName);
        return this;
    }

    public void setTimezoneWindowsName(String timezoneWindowsName) {
        this.timezoneWindowsName = timezoneWindowsName;
    }

    public Integer getTimezoneTzIndex() {
        return this.timezoneTzIndex;
    }

    public OrganizationLocationTrans timezoneTzIndex(Integer timezoneTzIndex) {
        this.setTimezoneTzIndex(timezoneTzIndex);
        return this;
    }

    public void setTimezoneTzIndex(Integer timezoneTzIndex) {
        this.timezoneTzIndex = timezoneTzIndex;
    }

    public String getAddressLine1() {
        return this.addressLine1;
    }

    public OrganizationLocationTrans addressLine1(String addressLine1) {
        this.setAddressLine1(addressLine1);
        return this;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return this.addressLine2;
    }

    public OrganizationLocationTrans addressLine2(String addressLine2) {
        this.setAddressLine2(addressLine2);
        return this;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressFloor() {
        return this.addressFloor;
    }

    public OrganizationLocationTrans addressFloor(String addressFloor) {
        this.setAddressFloor(addressFloor);
        return this;
    }

    public void setAddressFloor(String addressFloor) {
        this.addressFloor = addressFloor;
    }

    public String getAddressLocality() {
        return this.addressLocality;
    }

    public OrganizationLocationTrans addressLocality(String addressLocality) {
        this.setAddressLocality(addressLocality);
        return this;
    }

    public void setAddressLocality(String addressLocality) {
        this.addressLocality = addressLocality;
    }

    public String getAddressRegion() {
        return this.addressRegion;
    }

    public OrganizationLocationTrans addressRegion(String addressRegion) {
        this.setAddressRegion(addressRegion);
        return this;
    }

    public void setAddressRegion(String addressRegion) {
        this.addressRegion = addressRegion;
    }

    public String getAddressPostalCode() {
        return this.addressPostalCode;
    }

    public OrganizationLocationTrans addressPostalCode(String addressPostalCode) {
        this.setAddressPostalCode(addressPostalCode);
        return this;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getAddressCountry() {
        return this.addressCountry;
    }

    public OrganizationLocationTrans addressCountry(String addressCountry) {
        this.setAddressCountry(addressCountry);
        return this;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getAddressNotes() {
        return this.addressNotes;
    }

    public OrganizationLocationTrans addressNotes(String addressNotes) {
        this.setAddressNotes(addressNotes);
        return this;
    }

    public void setAddressNotes(String addressNotes) {
        this.addressNotes = addressNotes;
    }

    public BigDecimal getGeoLatitude() {
        return this.geoLatitude;
    }

    public OrganizationLocationTrans geoLatitude(BigDecimal geoLatitude) {
        this.setGeoLatitude(geoLatitude);
        return this;
    }

    public void setGeoLatitude(BigDecimal geoLatitude) {
        this.geoLatitude = geoLatitude;
    }

    public BigDecimal getGeoLongitude() {
        return this.geoLongitude;
    }

    public OrganizationLocationTrans geoLongitude(BigDecimal geoLongitude) {
        this.setGeoLongitude(geoLongitude);
        return this;
    }

    public void setGeoLongitude(BigDecimal geoLongitude) {
        this.geoLongitude = geoLongitude;
    }

    public String getPosPlatformName() {
        return this.posPlatformName;
    }

    public OrganizationLocationTrans posPlatformName(String posPlatformName) {
        this.setPosPlatformName(posPlatformName);
        return this;
    }

    public void setPosPlatformName(String posPlatformName) {
        this.posPlatformName = posPlatformName;
    }

    public String getPosPlatformVersion() {
        return this.posPlatformVersion;
    }

    public OrganizationLocationTrans posPlatformVersion(String posPlatformVersion) {
        this.setPosPlatformVersion(posPlatformVersion);
        return this;
    }

    public void setPosPlatformVersion(String posPlatformVersion) {
        this.posPlatformVersion = posPlatformVersion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationLocationTrans)) {
            return false;
        }
        return getId() != null && getId().equals(((OrganizationLocationTrans) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationLocationTrans{" +
            "id=" + getId() +
            ", orgShortName='" + getOrgShortName() + "'" +
            ", locRef='" + getLocRef() + "'" +
            ", name='" + getName() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", languages='" + getLanguages() + "'" +
            ", timezoneIanaName='" + getTimezoneIanaName() + "'" +
            ", timezoneWindowsName='" + getTimezoneWindowsName() + "'" +
            ", timezoneTzIndex=" + getTimezoneTzIndex() +
            ", addressLine1='" + getAddressLine1() + "'" +
            ", addressLine2='" + getAddressLine2() + "'" +
            ", addressFloor='" + getAddressFloor() + "'" +
            ", addressLocality='" + getAddressLocality() + "'" +
            ", addressRegion='" + getAddressRegion() + "'" +
            ", addressPostalCode='" + getAddressPostalCode() + "'" +
            ", addressCountry='" + getAddressCountry() + "'" +
            ", addressNotes='" + getAddressNotes() + "'" +
            ", geoLatitude=" + getGeoLatitude() +
            ", geoLongitude=" + getGeoLongitude() +
            ", posPlatformName='" + getPosPlatformName() + "'" +
            ", posPlatformVersion='" + getPosPlatformVersion() + "'" +
            "}";
    }
}
