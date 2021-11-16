package com.example.userlist_1313;
//
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class InfoUserActivity extends AppCompatActivity { // создаем свойства для каждого элемента данной активности
    TextView infoUserFio; // для имя и фамилии
    TextView infoUserPhone;
    Button editBtn;
    Button deleteBtn;
    Button backMainBtn;
    Users users;
    User user;
    private int userPosition; // сюда получим переданную через intent позицию данного пользователя

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
        // находим нужные свойства на активности
        infoUserFio = findViewById(R.id.infoUserFio);
        infoUserPhone = findViewById(R.id.infoUserPhone);
        editBtn = findViewById(R.id.editBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        backMainBtn = findViewById(R.id.backMainBtn);

        editBtn.setOnClickListener(new View.OnClickListener() { //редактирование контакта
            @Override
            public void onClick(View view) { // для добавления и редактирования одна активность, но в случае редактирования мы передаем позицию.
                Intent intent = new Intent(InfoUserActivity.this, FormUserActivity.class);
                intent.putExtra("userPosition", userPosition); // передаем позицию в форму
                startActivity(intent); // запускаем активность
                Toast.makeText(InfoUserActivity.this, "Редактирование...", Toast.LENGTH_SHORT).show(); // короткое уведомление об удалении контакта
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() { //удаление контакта
            @Override
            public void onClick(View view) {
                //запрос к б.д. удалить запись с uuid  данного пользователя
                // закрыть б.д.
                Toast.makeText(InfoUserActivity.this, "Удаление...", Toast.LENGTH_SHORT).show(); // короткое уведомление об удалении контакта
                users.deleteUser(user.getUuid());
                onBackPressed();// возвращаемся обратно на главную активность
            }
        });

        backMainBtn.setOnClickListener(new View.OnClickListener() { //удаление контакта
            @Override
            public void onClick(View view) {
                onBackPressed();// возвращаемся обратно на главную активность
            }
        });
    }//onCreate

    //не забываем, что после возвращения в  активность
    // мы попадаем не в метод onCreate, а в onResume
    @Override
    public void onResume() {
        super.onResume();
        userPosition = getIntent().getIntExtra("userPosition", 0); //берем значение из переданного через намерение, для чисел и булев указываем значение по умолчанию
        // не лучшее решение задачи выдергивать сюда весь список // но оставляем его как вариант решения задачи
        users = new Users(InfoUserActivity.this); // Вызов конструктора users, чтобы получить объект с методом формирования всего списка пользователей
        user = users.getUserList().get(userPosition); // Получаем список пользователей и из него конкретного пользователя по номеру позиции
        // получили пользователя
        // теперь из конкретного пользователя можно вынуть все его параметры
        infoUserFio.setText(user.getUserName()+" "+user.getUserLastName()); // записываем имя фамилия
        infoUserPhone.setText(user.getPhone()); // телефон
    }
}