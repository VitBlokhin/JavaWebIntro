package org.communis.javawebintro.enums;

public enum ArticleStatus {
    NEW,
    SHOWN,
    HIDDEN,
    BLOCKED;

    public String getStringName() {
        switch (this) {
            case NEW:
                return "Новое";
            case SHOWN:
                return "Показано";
            case HIDDEN:
                return "Скрыто";
            case BLOCKED:
                return "Заблокировано";
            default:
                return null;
        }
    }
}
