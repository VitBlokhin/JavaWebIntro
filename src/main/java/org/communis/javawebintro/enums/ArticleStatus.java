package org.communis.javawebintro.enums;

public enum ArticleStatus {
    ACTIVE,
    BLOCKED,
    DELETED;

    public String getStringName() {
        switch (this) {
            case ACTIVE:
                return "Активно";
            case BLOCKED:
                return "Заблокировано";
            case DELETED:
                return "Удалено";
            default:
                return null;
        }
    }
}
