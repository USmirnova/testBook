package com.example.userlist_1313.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// непосредственно создание базы данных, описанной в классе UserDBSchema
// название UserBaseHalper странное, потому, что данный класс расширяет (наследуется) класс SQLiteOpenHelper
// SQLiteOpenHelper - нужен собстенно для создания б.д. // мы должны реализовать 2 метода
public class UserBaseHalper extends SQLiteOpenHelper {
    private static final int VERSION = 1; // при изменении версии будет вызван метод onUpgrade
    private static final String DATABASE_NAME = "userBase_1313.db"; // название б.д. - название файла, в котором будет храниться вся информация о б.д.
    public UserBaseHalper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    } //

    @Override // создает бд.
    public void onCreate(SQLiteDatabase sqLiteDatabase) { // подключаемся
        sqLiteDatabase.execSQL("create table "+UserDBSchema.UserTable.NAME+" ("+ // создаем таблицу с именем users_1313 - оно задано в схеме
                "_id integer primary key autoincrement, "+  // задаем id _ нужно т.к. это служебное поле // это число
                UserDBSchema.Cols.UUID+", " + // название колонки uuid
                UserDBSchema.Cols.USERNAME+", " + // username
                UserDBSchema.Cols.USERLASTNAME+", " + // userlastname
                UserDBSchema.Cols.PHONE+")"); // phone
    }// вызывает при первом запуске приложения и обращении к UserBaseHalper
    // onCreate будет запущен лишь тогда, когда данной таблицы еще не существует

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { // меняет
        // если потребуется изменить структуру, например добавить столбец
        // нужно будет указать версию таблицы
    }
}
