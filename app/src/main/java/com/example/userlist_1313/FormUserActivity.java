package com.example.userlist_1313;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormUserActivity extends AppCompatActivity {
    EditText editTextName;
    EditText editTextLastName;
    EditText editTextPhone;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_user);

        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhone = findViewById(R.id.editTextPhone);
        addBtn = findViewById(R.id.addBtn);

        int userPosition = getIntent().getIntExtra("userPosition", -1);
        if (userPosition != -1) {
            Users users = new Users(FormUserActivity.this);
            User user = users.getUserList().get(userPosition);
            editTextName.setText(user.getUserName());
            editTextLastName.setText(user.getUserLastName());
            editTextPhone.setText(user.getPhone());
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.setUserName(editTextName.getText().toString());
                    user.setUserLastName(editTextLastName.getText().toString());
                    user.setPhone(editTextPhone.getText().toString());
                    users.updateUser(user);
                    finish();
                }
            });
        }
        else {
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User user = new User();
                    user.setUserName(editTextName.getText().toString());
                    user.setUserLastName(editTextLastName.getText().toString());
                    user.setPhone(editTextPhone.getText().toString());

                    Users users = new Users(FormUserActivity.this);
                    users.addUser(user);
                    onBackPressed();
                }
            });
        }


    }
}