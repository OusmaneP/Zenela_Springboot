package com.podosoft.zenela.ApiMobile.Dto;

import com.podosoft.zenela.Models.Role;

import java.util.Collection;
import java.util.Date;

public class UserMobileDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Boolean accountNonLocked;

    private Boolean accountEnabled;

    private Collection<Role> roles;

    private String profile;

    private String cover;

    private Date createdAt;

    public int missedMessages;

    public UserMobileDto(String firstName, String lastName, String email, String password, Boolean accountNonLocked, Boolean accountEnabled, Collection<Role> roles, String profile, String cover, Date createdAt) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.accountNonLocked = accountNonLocked;
        this.accountEnabled = accountEnabled;
        this.roles = roles;
        this.profile = profile;
        this.cover = cover;
        this.createdAt = createdAt;
    }

    public UserMobileDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getAccountEnabled() {
        return accountEnabled;
    }

    public void setAccountEnabled(Boolean accountEnabled) {
        this.accountEnabled = accountEnabled;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getMissedMessages() {
        return missedMessages;
    }

    public void setMissedMessages(int missedMessages) {
        this.missedMessages = missedMessages;
    }
}
