package org.communis.javawebintro.enums;

public enum ArticleStatus {
    NEW,
    SHOWN,
    HIDDEN,
    BLOCKED,
    DELETED;

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
            case DELETED:
                return "Удалено";
            default:
                return null;
        }
    }
}
