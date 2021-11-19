package com.example.userlist_1313;
// переносим сюда данные из класса InfoUserActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

// чтобы класс стал фрагментом унаследуемся от класса Fragment
public class UserInfoFragment extends Fragment {
    TextView infoUserFio;
    TextView infoUserPhone;
    Button editBtn;
    Button deleteBtn;
    Button backMainBtn;
    User user;
    Users users;
    int userPosition; //
    // при создании фрагмента нагрузим его параметрами
    // передадим ему пользователя как сериализованный объект
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments(); // по сути это тоже коллекция // принимаем все аргументы
        // извлекаем переданного из UserPagerActivity.java пользователя
        user = (User) bundle.getSerializable("user"); // тут нужно указывать какого типа объект мы получаем
        userPosition = (int) bundle.get("userPosition"); // извлекаем переданную через bundle позицию пользователя
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        super.onCreate(savedInstanceState); // вызов родителя
        View view = inflater.inflate(R.layout.activity_info_user, viewGroup, false); // раздуваем макет с группой вьюшек
        // находи элементы на объекте view по id // указываем объект view, далее как обычно
        infoUserFio = view.findViewById(R.id.infoUserFio); //
        infoUserPhone = view.findViewById(R.id.infoUserPhone);
        editBtn = view.findViewById(R.id.editBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);
        backMainBtn= view.findViewById(R.id.backMainBtn);
        String fio = user.getUserLastName()+" "+user.getUserName();
        infoUserFio.setText(fio);
        infoUserPhone.setText(user.getPhone());

        editBtn.setOnClickListener(new View.OnClickListener() { //редактирование контакта
            @Override
            public void onClick(View view) { // для добавления и редактирования одна активность, но в случае редактирования мы передаем позицию.
                Intent intent = new Intent(getContext(), FormUserActivity.class);
                intent.putExtra("userPosition", userPosition); // передаем позицию в форму
                startActivity(intent); // запускаем активность
                getActivity().finish(); // возвращение на главную активность (если из фрагмента добавляем getActivity().), минуя промежуточные
                Toast.makeText(getContext(), "Редактирование..."+user.getUuid(), Toast.LENGTH_LONG).show(); // короткое уведомление об удалении контакта
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() { //удаление контакта
            @Override
            public void onClick(View view) {
                //запрос к б.д. удалить запись с uuid  данного пользователя
                // закрыть б.д.
                Toast.makeText(getContext(), "Удаление..."+user.getUuid(), Toast.LENGTH_LONG).show(); // короткое уведомление об удалении контакта
                users = new Users(getContext()); // получаем сюда объект users
                users.deleteUser(user.getUuid()); // не получив объект users будем ссылаться на объект null и приложение будет падать
                getActivity().onBackPressed();// возвращаемся на предыдущую активность

            }
        });

        backMainBtn.setOnClickListener(new View.OnClickListener() { //удаление контакта
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();// возвращаемся на предыдущую активность
            }
        });

        return view;
    }
}
