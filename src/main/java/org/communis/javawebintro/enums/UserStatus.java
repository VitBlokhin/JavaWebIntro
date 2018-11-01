package org.communis.javawebintro.enums;

public enum UserStatus {
    ACTIVE,
    BLOCKED;

    public String getStringName() {
        switch (this) {
            case BLOCKED:
                return "Заблокирован";
            case ACTIVE:
                return "Активен";
            default:
                return null;
        }
    }
}