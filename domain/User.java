package demo.domain;

import java.util.Objects;

public class User {
    private Long id;
    private String  userid,
                    email,
                    phoneNum,
                    userType,
                    street,
                    city,
                    state,
                    zipCode,
                    countryCode,
                    tenant;

    public User() {
    }

    public User(Long id, String userid, String email, String phoneNum, String userType, String street, String city, String state, String zipCode, String countryCode, String tenant) {
        this.id = id;
        this.userid = userid;
        this.email = email;
        this.phoneNum = phoneNum;
        this.userType = userType;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.countryCode = countryCode;
        this.tenant = tenant;
    }

    public User(String userid, String email, String phoneNum, String userType, String street, String city, String state, String zipCode, String countryCode, String tenant) {
        this.userid = userid;
        this.email = email;
        this.phoneNum = phoneNum;
        this.userType = userType;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.countryCode = countryCode;
        this.tenant = tenant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(userid, user.userid) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phoneNum, user.phoneNum) &&
                Objects.equals(userType, user.userType) &&
                Objects.equals(street, user.street) &&
                Objects.equals(city, user.city) &&
                Objects.equals(state, user.state) &&
                Objects.equals(zipCode, user.zipCode) &&
                Objects.equals(countryCode, user.countryCode) &&
                Objects.equals(tenant, user.tenant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userid, email, phoneNum, userType, street, city, state, zipCode, countryCode, tenant);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", userType='" + userType + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", tenant='" + tenant + '\'' +
                '}';
    }
}
