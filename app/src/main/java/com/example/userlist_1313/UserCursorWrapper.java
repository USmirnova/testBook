package com.example.userlist_1313;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.userlist_1313.database.UserDBSchema;
import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) { // обязаны вызвать этот конструктор
        super(cursor);
    }
    public User getUser() {
        String uuidString = getString(getColumnIndex(UserDBSchema.Cols.UUID));
        String userName = getString(getColumnIndex(UserDBSchema.Cols.USERNAME));
        String userLastName = getString(getColumnIndex(UserDBSchema.Cols.USERLASTNAME));
        String phone = getString(getColumnIndex(UserDBSchema.Cols.PHONE));

        User user = new User(UUID.fromString(uuidString));
        user.setUserName(userName);
        user.setUserLastName(userLastName);
        user.setPhone(phone);

        return user;
    }
}