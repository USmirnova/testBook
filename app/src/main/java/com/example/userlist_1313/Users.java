package com.example.userlist_1313;
// Для управления пользователями.
// Добавление/удаление/редактирование, формирование списка пользователей из записей б.д.
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.userlist_1313.database.UserBaseHalper;
import com.example.userlist_1313.database.UserDBSchema;
import java.util.ArrayList;
import java.util.UUID;

public class Users {
    ArrayList<User> userList; // коллекция пользователей
    private SQLiteDatabase database; // Объект для работы с б.д.

    public Users(Context context) { // инициализируем конструктор, где принимаем контекст // значит при создании объекта Users надо передавать контекст, например MainActivity.this
        this.database = new UserBaseHalper(context).getWritableDatabase(); // чтобы можно было писать что-то в б.д. .getWritableDatabase()
    }

    // Вспомогательный метод для добавления пользователя
    // Передаем сюда объект пользователя и раскладываем его по колонкам
    // Преобразуем в специализированный объект
    private ContentValues getContentValues(User user) { // приватный. Будет доступен только в этом классе
        ContentValues values = new ContentValues(); // создаеt объект ContentValues
        // положить (в столбец ... кладем соответствующие данные пользователя)
        values.put(UserDBSchema.Cols.UUID, user.getUuid().toString()); // в столбец uuid кладем собственно uuid (тип данных UUID), преобразованное в строку)
        values.put(UserDBSchema.Cols.USERNAME, user.getUserName()); // username -> имя пользователя
        values.put(UserDBSchema.Cols.USERLASTNAME, user.getUserLastName()); // userlastname -> фамилия пользователя
        values.put(UserDBSchema.Cols.PHONE, user.getPhone()); // phone -> его телефон

        return values; // возвращаем его // тип данных ContentValues
    }

    public void addUser(User user) { // insert для того чтобы вставить что-то в б.д.
        ContentValues values = getContentValues(user); // Из юзера делаем values - объект с соответствием колонок и значений для них. Из него будет сформирована запись в б.д.
        database.insert(UserDBSchema.UserTable.NAME, null, values); // Вставляет в таблицу данные конкретного юзера (в такие-то поля такие-то значения)
        // указываем название нужной таблицы (ведь их может быть несколько в б.д.)
        // некоторые поля должны быть обязательны для заполнения и им нужны данные по умолчанию nullColumnHuck. Если таких полей нет, пишем null.
        // третий параметр contentValues - преобразованные вспомогательным методом данные юзера для вставки в поля таблицы.
    }

    // метод получения пользователей // считывания их из б.д.
    // для считывания данных из б.д. нам нужен курсор - бродилка по строкам таблицы
    // в андроиде есть класс CursorWrapper, но он возвращает данные в неподходящем виде
    // Чтобы привести данные в адекватный вид создаем класс-прослойку UserCursorWrapper

    // вспомогательный метод для формирования курсора, для формирования списка пользователей // берем данные из таблицы и формируем коллекцию
    private UserCursorWrapper queryUsers() { // формирует курсор посредством отдельного класса UserCursorWrapper и возвращает объект UserCursorWrapper
        // создаем низкоуровневый курсор, который возвращает сырые данные
        // делаем запрос к базе данных database.query(). Указываем название таблицы и название колонок, которые нам нужны
        //  и другие параметры, группы, выделенные области...
        // т.к. нужны нам все, то передаем null, null, null... // получаем все данные из б.д.
        Cursor cursor = database.query(UserDBSchema.UserTable.NAME, null, null, null, null, null, null);
        return new UserCursorWrapper(cursor); // сырой курсор пропускаем через спец класс - делаем из него объект UserCursorWrapper
    } // возвращаем объект нашего курсора

    public ArrayList<User> getUserList() { // возвращает список пользователей с типом данных ArrayList<Users>


        this.userList = new ArrayList();
        UserCursorWrapper cursorWrapper = queryUsers(); // получаем объект курсора
        try {
            cursorWrapper.moveToFirst(); // перемещаем курсор к первой записи в объекта курсора (таблицы)
            while (!cursorWrapper.isAfterLast()) { // считываем до тех пор пока не настала после последняя запись
                User user = cursorWrapper.getUser(); // вычитываем запись и присваиваем ее объекту user
                userList.add(user); // добавили в коллекцию
                cursorWrapper.moveToNext(); // перемещаем курсор к следующей строке
            }
        }
        finally { // чтобы не произошло в процессе (исключения, или все хорошо) закрываем курсор
            cursorWrapper.close(); // после работы с б.д. нужно ее закрыть (закрыть подключение?)
        }
        // автозаполнение на новом устройстве, чтобы наглядно видна была работа resyclerView
//        for (int i = 0; i < 5; i++) { // формируем  список контактов // автозаполнение для наглядности
//            User user = new User(); // создаем объект пользователя, uuid автоматически формируется
//            user.setUserName("Имя_"+i); // задаем имя
//            user.setUserLastName("Фамилия_"+i); // и фамилию
//             user.setPhone("+7 322 "+i); //
//            userList.add(user);// Добавляем пользователя в коллекцию пользователей
//        }
        return userList; // возвращает список пользователей
    }

    public void updateUser(User user) {
        ContentValues values = getContentValues(user); // разложим пользователя по колонкам, сопоставим.
        String uuidString = user.getUuid().toString();
        database.update(UserDBSchema.UserTable.NAME,
                values,
                UserDBSchema.Cols.UUID+"=?", new String[]{uuidString}); // подготовленный запрос, против sql инъекции
    }

    public void deleteUser (UUID uuid) { // удаление пользователя
        String uuidString = uuid.toString(); // uuid - это объект, а при обращении к б.д. мы не можем слать объект, приобразуем его в строку.
        //System.out.println("----------------------удаляем---------------------------"+uuidString); // в констоль для наглядности
        // нужно сопоставить данные колонки uuid и переданный uuid
        // database.delete(UserDBSchema.UserTable.NAME, UserDBSchema.Cols.UUID+"="+uuidString); // опасность sql инъекции
        // безопасно делать это через подготовленные запросы
        database.delete(UserDBSchema.UserTable.NAME, UserDBSchema.Cols.UUID+"=?", new String[]{uuidString}); // отдельно имя колонки и отдельно ее содержимое, но переданное массивом строк, что исключает атаку
         //удаление тестовых пользователей
//        for (int i = 0; i <20 ; i++) {
//            database.delete(UserDBSchema.UserTable.NAME, UserDBSchema.Cols.USERNAME+"=?", new String[]{"Имя_"+i+""});
//        }
    }
}
