package com.example.userlist_1313;
//
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class InfoUserActivity extends AppCompatActivity {
    TextView infoUserName; // создаем свойства для каждого элемента данной активности
    TextView infoUserLastName;
    TextView infoUserPhone;
    Button editBtn;
    Button deleteBtn;

    private String testHere; // сюда получим тестовую строку

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);

        infoUserName = findViewById(R.id.infoUserName); // находим нужные свойства на активности
        infoUserLastName = findViewById(R.id.infoUserLastName);
        infoUserPhone = findViewById(R.id.infoUserPhone);
        editBtn = findViewById(R.id.editBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        testHere = getIntent().getStringExtra("varuousName"); //берем значение из переданного через намерение
        //User user = new User(UUID uuid); // Вызов конструктора пользователя с uuid текущего пользователя
        infoUserName.append(testHere); // дописываем значение в поле

        deleteBtn.setOnClickListener(new View.OnClickListener() { //удаление контакта
            @Override
            public void onClick(View view) {
                //запрос к б.д. удалить запись с uuid  данного пользователя
                // закрыть б.д.
                Toast.makeText(InfoUserActivity.this, "Контакт будет удален!", Toast.LENGTH_SHORT).show(); // короткое уведомление об удалении контакта
                onBackPressed();// возвращаемся обратно на главную активность
            }
        });
    }
}