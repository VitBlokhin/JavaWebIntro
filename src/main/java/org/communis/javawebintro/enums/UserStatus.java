package org.communis.javawebintro.enums;

public enum UserStatus {
    ACTIVE,
    BLOCKED,
    DELETED;

    public String getStringName() {
        switch (this) {
            case BLOCKED:
                return "Заблокирован";
            case ACTIVE:
                return "Активен";
            case DELETED:
                return "Удалён";
            default:
                return null;
        }
    }
}