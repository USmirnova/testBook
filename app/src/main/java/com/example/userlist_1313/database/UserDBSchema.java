package com.example.userlist_1313.database;
// описываем схему базы данных
public class UserDBSchema {
    // публичный, всем доступный, неизменяемый класс
    // static - чтобы обращаться к свойствам класса без создания объекта
    public static final class UserTable { // создаем класс-константу к которой в дальнейшем будем обращаться
        public static final String NAME = "users_1313"; // название табилцы базы данных
    }
    // столбцы
    // public - доступный отовсюду
    // static - можно обращаься к свойсвам/методам без создания объекта
    // final - чтобы нельзя было переопредить свойства - константа - большими буквами
    public static final class Cols { // задаем названия колонкам
        public static final String UUID = "uuid"; // Название колонки (столбца в таблице)
        public static final String USERNAME = "username";
        public static final String USERLASTNAME = "userlastname";
        public static final String PHONE = "phone";
        // столбец id будет создаваться в запросе, который пойдет к б.д.
    }
}
