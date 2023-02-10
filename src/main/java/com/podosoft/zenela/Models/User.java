package com.podosoft.zenela.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String password;

    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;

    @Column(name = "account_enabled")
    private Boolean accountEnabled;

    @Transient
    private Collection<Role> roles = new ArrayList<>();

    private String profile;

    private String profileThumb;

    private String cover;

    private String bio;

    @Column(name = "created_at")
    private Date createdAt;

    @Transient
    public int missedMessages;

    public User(String firstName, String lastName, String email, String password, Boolean accountNonLocked, Boolean accountEnabled, Collection<Role> roles, String profile, String cover, Date createdAt) {
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

    public User() {

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileThumb() {
        return profileThumb;
    }

    public void setProfileThumb(String profileThumb) {
        this.profileThumb = profileThumb;
    }
}
