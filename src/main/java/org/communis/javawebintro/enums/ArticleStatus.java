package org.communis.javawebintro.enums;

public enum ArticleStatus {
    ACTIVE,
    BLOCKED;

    public String getStringName() {
        switch (this) {
            case BLOCKED:
                return "Заблокировано";
            case ACTIVE:
                return "Активно";
            default:
                return null;
        }
    }
}
