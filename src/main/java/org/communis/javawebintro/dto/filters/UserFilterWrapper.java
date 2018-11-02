package org.communis.javawebintro.dto.filters;


import org.communis.javawebintro.enums.UserRole;
import org.communis.javawebintro.enums.UserStatus;

public class UserFilterWrapper extends ObjectFilter {
    private String email;
    private UserRole role;
    private UserStatus status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
