package org.communis.javawebintro.exception.error;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodeConstants {

    public static final Map<ErrorCodeIdentifier, String> messages = new HashMap<>();

    public static final ErrorCodeIdentifier BASE = new ErrorCodeIdentifier("0");
    public static final ErrorCodeIdentifier DATA_NOT_FOUND = BASE.branch("1");
    public static final ErrorCodeIdentifier ACCESS_ERROR = BASE.branch("2");
    public static final ErrorCodeIdentifier DATA_VALIDATE_ERROR = BASE.branch("3");

    public static final ErrorCodeIdentifier USER = new ErrorCodeIdentifier("1");
    public static final ErrorCodeIdentifier USER_LIST_ERROR = USER.branch("1");
    public static final ErrorCodeIdentifier USER_INFO_ERROR = USER.branch("2");

    public static final ErrorCodeIdentifier USER_ADD_ERROR = USER.branch("3");
    public static final ErrorCodeIdentifier USER_LOGIN_ALREADY_EXIST = USER_ADD_ERROR.branch("1");

    public static final ErrorCodeIdentifier USER_PASSWORD_ERROR = USER.branch("4");
    public static final ErrorCodeIdentifier USER_PASSWORD_LENGTH_ERROR = USER_PASSWORD_ERROR.branch("1");
    public static final ErrorCodeIdentifier USER_PASSWORD_COMPARE_ERROR = USER_PASSWORD_ERROR.branch("2");

    public static final ErrorCodeIdentifier USER_UPDATE_ERROR = USER.branch("5");

    public static final ErrorCodeIdentifier USER_BLOCK_ERROR = USER.branch("6");
    public static final ErrorCodeIdentifier USER_BLOCK_SELF_ERROR = USER_BLOCK_ERROR.branch("1");

    public static final ErrorCodeIdentifier USER_UNBLOCK_ERROR = USER.branch("7");

    public static final ErrorCodeIdentifier USER_DELETE_ERROR = USER.branch("8");
    public static final ErrorCodeIdentifier USER_DELETE_SELF_ERROR = USER_DELETE_ERROR.branch("1");


    public static final ErrorCodeIdentifier CATEGORY = new ErrorCodeIdentifier("2");
    public static final ErrorCodeIdentifier CATEGORY_LIST_ERROR = CATEGORY.branch("1");
    public static final ErrorCodeIdentifier CATEGORY_INFO_ERROR = CATEGORY.branch("2");

    public static final ErrorCodeIdentifier CATEGORY_ADD_ERROR = CATEGORY.branch("3");
    public static final ErrorCodeIdentifier CATEGORY_ALREADY_EXIST = CATEGORY_ADD_ERROR.branch("1");

    public static final ErrorCodeIdentifier CATEGORY_UPDATE_ERROR = CATEGORY.branch("4");
    public static final ErrorCodeIdentifier CATEGORY_DELETE_ERROR = CATEGORY.branch("5");


    public static final ErrorCodeIdentifier ARTICLE = new ErrorCodeIdentifier("3");
    public static final ErrorCodeIdentifier ARTICLE_LIST_ERROR = ARTICLE.branch("1");
    public static final ErrorCodeIdentifier ARTICLE_INFO_ERROR = ARTICLE.branch("2");

    public static final ErrorCodeIdentifier ARTICLE_ADD_ERROR = ARTICLE.branch("3");
    public static final ErrorCodeIdentifier ARTICLE_ALREADY_EXIST = ARTICLE_ADD_ERROR.branch("1");
    public static final ErrorCodeIdentifier ARTICLE_AUTHOR_NOT_FOUND = ARTICLE_ADD_ERROR.branch("2");
    public static final ErrorCodeIdentifier ARTICLE_CATEGORY_NOT_FOUND = ARTICLE_ADD_ERROR.branch("3");

    public static final ErrorCodeIdentifier ARTICLE_UPDATE_ERROR = ARTICLE.branch("4");
    public static final ErrorCodeIdentifier ARTICLE_DELETE_ERROR = ARTICLE.branch("5");

    public static final ErrorCodeIdentifier ARTICLE_STATUS_ERROR = ARTICLE.branch("6");
    public static final ErrorCodeIdentifier ARTICLE_SHOW_ERROR = ARTICLE_STATUS_ERROR.branch("1");
    public static final ErrorCodeIdentifier ARTICLE_HIDE_ERROR = ARTICLE_STATUS_ERROR.branch("2");
    public static final ErrorCodeIdentifier ARTICLE_BLOCK_ERROR = ARTICLE_STATUS_ERROR.branch("3");

    static {
        messages.put(DATA_NOT_FOUND, "Ошибка при получении данных");
        messages.put(ACCESS_ERROR, "Доступ запрещен");
        messages.put(DATA_VALIDATE_ERROR, "Отправленные данные некорректны");

        messages.put(USER_LIST_ERROR, "Ошибка при получении реестра пользователей");
        messages.put(USER_INFO_ERROR, "Ошибка при получении пользователя");

        messages.put(USER_ADD_ERROR, "Ошибка при добавлении пользователя");
        messages.put(USER_LOGIN_ALREADY_EXIST, "Логин занят другим пользователем");

        messages.put(USER_PASSWORD_ERROR, "Ошибка при изменении пароля пользователя");
        messages.put(USER_PASSWORD_LENGTH_ERROR, "Некорректная длина пароля");
        messages.put(USER_PASSWORD_COMPARE_ERROR, "Пароли не совпадают");

        messages.put(USER_UPDATE_ERROR, "Ошибка при изменении пользователя");

        messages.put(USER_BLOCK_ERROR, "Ошибка при блокировке пользователя");
        messages.put(USER_BLOCK_SELF_ERROR, "Нельзя заблокировать себя");

        messages.put(USER_UNBLOCK_ERROR, "Ошибка при разблокировке пользователя");

        messages.put(USER_DELETE_ERROR, "Ошибка при удалении пользователя");
        messages.put(USER_DELETE_SELF_ERROR, "Нельзя удалить себя");

        messages.put(CATEGORY_LIST_ERROR, "Ошибка при получении реестра категорий");
        messages.put(CATEGORY_INFO_ERROR, "Ошибка при получении категории");

        messages.put(CATEGORY_ADD_ERROR, "Ошибка при добавлении категории");
        messages.put(CATEGORY_ALREADY_EXIST, "Категория уже существует");

        messages.put(CATEGORY_UPDATE_ERROR, "Ошибка при изменении категории");
        messages.put(CATEGORY_DELETE_ERROR, "Ошибка при удалении категории");

        messages.put(ARTICLE_LIST_ERROR, "Ошибка при получении реестра заметок");
        messages.put(ARTICLE_INFO_ERROR, "Ошибка при получении заметки");
        messages.put(ARTICLE_ADD_ERROR, "Ошибка при добавлении заметки");
        messages.put(ARTICLE_ALREADY_EXIST, "Заметка уже существует");

        messages.put(ARTICLE_UPDATE_ERROR, "Ошибка при изменении заметки");
        messages.put(ARTICLE_DELETE_ERROR, "Ошибка при удалении заметки");

        messages.put(ARTICLE_STATUS_ERROR, "Ошибка при изменении статуса заметки");
        messages.put(ARTICLE_SHOW_ERROR, "Ошибка при подтверждении заметки");
        messages.put(ARTICLE_HIDE_ERROR, "Ошибка при скрытии заметки");
        messages.put(ARTICLE_BLOCK_ERROR, "Ошибка при блокировке заметки");
    }
}
