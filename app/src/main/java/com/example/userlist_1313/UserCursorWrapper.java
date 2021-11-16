package com.example.userlist_1313;
// для считывания данных из б.д. нам нужен курсор - бродилка по строкам таблицы
// в андроиде есть класс CursorWrapper, но он возвращает данные в безобразном виде
// Чтобы привести данные в адекватный вид создаем класс-прослойку UserCursorWrapper
// Чтобы не засорять класс Users - не громоздить в одном методе другой
import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.userlist_1313.database.UserDBSchema;
import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) { // обязаны вызвать этот конструктор
        super(cursor);
    }
    public User getUser() { // формирует объект пользователя
        // в бд мы клали uuid, преобразованный в строку, значит и считывать из бд будем строку
        // все данные берем из UserDBSchema // это очень удобно // поменяли в одном месте - измелись данные везде.
        // для этого создавали класс UserDBSchema // не нужно писать название столбца везде строкой, лучше ссылаться на константу
        // избавляет от проблем
        String uuidString = getString(getColumnIndex(UserDBSchema.Cols.UUID)); // берем данные из колонки uuid
        String userName = getString(getColumnIndex(UserDBSchema.Cols.USERNAME)); // из username
        String userLastName = getString(getColumnIndex(UserDBSchema.Cols.USERLASTNAME)); // из userlastname
        String phone = getString(getColumnIndex(UserDBSchema.Cols.PHONE)); // из phone
        // Тут нужно создать нового пользователя
        // но при создании пользователя ему присваивается uuid
        // Но мы не создаем пользователя с нуля, мы считываем его из б.д., а тут уже есть uuid
        // Нужно создать пользователя не с новым uuid, а с пришедшим из б.д.
        // на помощь приходит перегрузка метода! - методы с одинакомым именем, но с разными параметрами, или с разной реализацией
        // Перейдем в класс User и создадим еще один конструктор

        // теперь наполним объект нужными данными
        User user = new User(UUID.fromString(uuidString)); // передаем объект uuid, преобразованный в строку
        user.setUserName(userName);
        user.setUserLastName(userLastName);
        user.setPhone(phone);

        // теперь его можно возвращать
        return user;
    }
}
// c помощью этого курсора мы будем пробегаться по строкам б.д. и вместо сырого ответа
// будем получать готовый объект user
// прочитали строчку - получили объект