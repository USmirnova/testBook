package com.example.userlist_1313;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddUserActivity extends AppCompatActivity {
    EditText editTextName; // создаем свойства для каждого элемента данной активности
    EditText editTextLastName;
    EditText editTextPhone;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        editTextName = findViewById(R.id.editTextName); // находим данные элементы на активности
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhone = findViewById(R.id.editTextPhone);
        addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(); // вызов конструктора пользователя без идетификатора // пользователь будет создан новый с новым uuid
                user.setUserName(editTextName.getText().toString()); // вычитываем из поля имя пользователя и назначаем в объект пользователя
                user.setUserLastName(editTextLastName.getText().toString());
                user.setPhone(editTextPhone.getText().toString());

                // создаем запись в базе данных
                Users users = new Users(AddUserActivity.this); // Создаем объект юзерс - управление пользователями, передаем туда текущую активность
                users.addUser(user); // в метод добавления юзера передаем юзера
                onBackPressed();// возвращаемся обратно на главную активность
            }
        });
    }
}