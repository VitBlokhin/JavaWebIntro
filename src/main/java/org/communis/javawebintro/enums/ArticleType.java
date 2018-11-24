package org.communis.javawebintro.enums;

public enum ArticleType {
    PRIVATE,
    PUBLIC;

    public String getStringName() {
        switch (this) {
            case PRIVATE:
                return "Приватная заметка";
            case PUBLIC:
                return "Публичная заметка";
            default:
                return null;
        }
    }
}
