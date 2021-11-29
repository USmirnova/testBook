package com.example.userlist_1313;
//общая активность для создания нового пользователя и для редактирования существующего
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormUserActivity extends AppCompatActivity {
    EditText editTextName; // создаем свойства для каждого элемента данной активности
    EditText editTextLastName;
    EditText editTextPhone;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_user);

        editTextName = findViewById(R.id.editTextName); // находим данные элементы на активности
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhone = findViewById(R.id.editTextPhone);
        addBtn = findViewById(R.id.addBtn);

        // если позиция передана - редактируем, если она не передана, по умолчанию будет -1, тогда добавляем пользователя.
        int userPosition = getIntent().getIntExtra("userPosition", -1);
        if (userPosition != -1) { //редактируем
            Users users = new Users(FormUserActivity.this);
            User user = users.getUserList().get(userPosition);
            editTextName.setText(user.getUserName());
            editTextLastName.setText(user.getUserLastName());
            editTextPhone.setText(user.getPhone());
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.setUserName(editTextName.getText().toString()); // вычитываем из поля имя пользователя и назначаем в объект пользователя
                    user.setUserLastName(editTextLastName.getText().toString());
                    user.setPhone(editTextPhone.getText().toString());
                    users.updateUser(user); // на объекте users вызываем метод updateUser()
                    //onBackPressed();// возвращаемся обратно на главную активность
                    finish();
                } // не забываем, что после возвращения в предыдущую активность
                // мы попадаем не в метод onCreate, а в onResume
            });
        }
        else { // добавляем
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User user = new User(); // вызов конструктора пользователя без идетификатора // пользователь будет создан новый с новым uuid
                    user.setUserName(editTextName.getText().toString()); // вычитываем из поля имя пользователя и назначаем в объект пользователя
                    user.setUserLastName(editTextLastName.getText().toString());
                    user.setPhone(editTextPhone.getText().toString());

                    // создаем запись в базе данных
                    Users users = new Users(FormUserActivity.this); // Создаем объект юзерс - управление пользователями, передаем туда текущую активность
                    users.addUser(user); // в метод добавления юзера передаем юзера
                    onBackPressed();// возвращаемся обратно на главную активность
                }
            });
        }


    }
}