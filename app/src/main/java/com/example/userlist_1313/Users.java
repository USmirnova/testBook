package com.example.userlist_1313;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.userlist_1313.database.UserBaseHalper;
import com.example.userlist_1313.database.UserDBSchema;
import java.util.ArrayList;
import java.util.UUID;

public class Users {
    ArrayList<User> userList;
    private SQLiteDatabase database;

    public Users(Context context) {
        this.database = new UserBaseHalper(context).getWritableDatabase();
    }

    private ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();

        values.put(UserDBSchema.Cols.UUID, user.getUuid().toString());
        values.put(UserDBSchema.Cols.USERNAME, user.getUserName());
        values.put(UserDBSchema.Cols.USERLASTNAME, user.getUserLastName());
        values.put(UserDBSchema.Cols.PHONE, user.getPhone());

        return values;
    }

    public void addUser(User user) {
        ContentValues values = getContentValues(user);
        database.insert(UserDBSchema.UserTable.NAME, null, values);
    }

    private UserCursorWrapper queryUsers() {
        Cursor cursor = database.query(UserDBSchema.UserTable.NAME, null, null, null, null, null, null);
        return new UserCursorWrapper(cursor);
    }

    public ArrayList<User> getUserList() {


        this.userList = new ArrayList();
        UserCursorWrapper cursorWrapper = queryUsers();
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                User user = cursorWrapper.getUser();
                userList.add(user);
                cursorWrapper.moveToNext();
            }
        }
        finally {
            cursorWrapper.close();
        }

        return userList;
    }

    public void updateUser(User user) {
        ContentValues values = getContentValues(user);
        String uuidString = user.getUuid().toString();
        database.update(UserDBSchema.UserTable.NAME,
                values,
                UserDBSchema.Cols.UUID+"=?", new String[]{uuidString});
    }

    public void deleteUser (UUID uuid) {
        String uuidString = uuid.toString();

        database.delete(UserDBSchema.UserTable.NAME, UserDBSchema.Cols.UUID+"=?", new String[]{uuidString});
         // удаление тестовых пользователей // удалит всех с тестовыми именами при удалении одного любого из них
//        for (int i = 0; i <20 ; i++) {
//            database.delete(UserDBSchema.UserTable.NAME, UserDBSchema.Cols.USERNAME+"=?", new String[]{"Имя_"+i+""});
//        }
    }
}
