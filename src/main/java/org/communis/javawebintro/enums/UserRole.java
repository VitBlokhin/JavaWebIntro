package org.communis.javawebintro.enums;

public enum UserRole {
    ROLE_ADMIN { },
    ROLE_USER { };

    public String getStringName() {
        switch (this) {
            case ROLE_ADMIN:
                return "Администратор";
            case ROLE_USER:
                return "Пользователь";
            default:
                return null;
        }
    }

}