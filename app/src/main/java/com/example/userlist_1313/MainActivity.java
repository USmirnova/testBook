package com.example.userlist_1313; // название пакета с названем от прошлого проекта
// импортируем нужные классы
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity { // наследуется от AppCompatActivity
    RecyclerView recyclerView; // объявляем переменную компонента RecyclerView
    UserAdapter userAdapter; // объявляем адаптер
    Button addUserBtn;
    Users users; // создаем объект, отвечающий за взаимоедействие с пользователями

    @Override // переопределяем метод
    protected void onCreate(Bundle savedInstanceState) { // создание активности c объектом для сохранения состояния
        super.onCreate(savedInstanceState); //? вызов родительского метода с объектом bundle - информация о состоянии активности ?
        setContentView(R.layout.activity_main); // построение макета
        users = new Users(MainActivity.this); // объекту, отвечающий за взаимоедействие с пользователями передаем текущую активность
        recyclerView = findViewById(R.id.recyclerView); // находим по id
        addUserBtn = findViewById(R.id.addUserBtn);
        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddUserActivity.class); // Создаем намерение переключиться из текущей активности в ту где будем добавлять пользователя
                startActivity(intent); // запускаем активность
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this)); // планируем макет. Элементы располагаем друг под другом в контексте данной активности
    }

    // Создавать и запускать адаптер будем не в методе onCreate, а тут
    // т.к. при возвращении в главную активность после добавления пользователя
    // данная активность уже создана, поставлена на паузу и после возвращения на нее запускается метод Resume
    @Override
    public void onResume() { // запускается при запуске приложения и после возвращения на активность
        super.onResume();
        userAdapter = new UserAdapter(users.getUserList()); // Создаем адаптер - объект класса со спец. методами расположения элементов на экране recyclerView
        recyclerView.setAdapter(userAdapter); // устанавливаем userAdapter для recyclerView, чтобы можно было применять его методы
        //адаптеров в приложении может быть несколько разных
    }

    // Для создания каждого элемента списка
    public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener{ // Класс для настройки самого элемента, наследуемый от RecyclerView.ViewHolder. Свойство itemView из него.
        TextView itemTextView; // Объявляем переменную текстовой вьюшки из макете элемента
        String userName; // имя контакта
        public UserHolder(LayoutInflater inflater, ViewGroup viewGroup) { //? Конструктор класса элемента списка // принимает инфлятор и объект viewGroup (андроид его сам создает, наша задача передать его дальше) на котором будут расположены все виджеты данного приложения?
            super(inflater.inflate(R.layout.single_item, viewGroup, false)); //?? вызываем родительский метод?, передаем в него раздуватель, тот раздувает макет элемента с группой виджетов и еще false зачем-то
            itemTextView = itemView.findViewById(R.id.itemTextView); //на раздутом макете - свойство itemView - свойство из класса RecyclerView.ViewHolder от которого наследуется данный класс находим вьюшку по id
            itemView.setOnClickListener(this); // связали текущий элемент с кликом
        } // самая непонятная фиговина, наполнитель экрана виджетами(вьюхами)?
        // инфлятор раздувает макет, чтобы на нем можно было разместить визуальные компоненты (кнопки, текст, и т.д.)
        // до этого макет сколлапсирован, как сингулярность?
        public void bind(String userName) { // укладывает данные,
            this.userName = userName; // переданные в параметре
            itemTextView.setText(userName); // в текстовую вьюшку каждого холдера (макета элемента)
        } // вызывается в методе onBindViewHolder, в который мы передаем userHolder

        // чтобы элемент стал кликабельным нужно наследоваться или подписываться на класс View.OnClickListener и реализовывать onClick метод
        @Override // теперь каждый элемент списка будет кликабельным
        public void onClick(View view) {
            // сделать редактирование надо
            // при клике открывать новую активность с формой для редактирования
            // вывести информацию о пользователе, возможнось редактирования на новой активности
            // и кнопку "удалить"
            // все что будет написано в форму должно отправляться в б.д. и менять ее
            // удаление обязательно. Редактирование дополнительно (можно не делать)
            Toast.makeText(MainActivity.this, "ItemClick", Toast.LENGTH_SHORT).show(); // короткое уведомление о клике
        }
    }

    public class UserAdapter extends RecyclerView.Adapter<UserHolder> { // Класс для размещения элементов наследуется (расширяет) RecyclerView.Adapter<UserHolder>
        ArrayList<User> userList; // = new ArrayList<>(); можно не писать тут
        public UserAdapter(ArrayList<User> userList) { // инициируем конструктор, чтобы можно было принимать коллекцию контактов при создание объекта адаптера
            this.userList = userList; // коллекцию через параметр
        }
        //в классе UserAdapter мы должны реализовать 3 нижеследующих метода:
        @Override
        public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) { // вызывается ресайклером,создает пустой холдер(макет для элемента) и возвращает его
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this); // Создаем раздуватель(наполнятель?), который будет наполнять макет элементами
            return new UserHolder(inflater, parent); // возвращает объект UserHolder (т.к. тип данных метода это UserHolder)
        }// создает холдеры когда они требуются // они пока пустые, данные еще не привязана // тарелку создали, еду не положили

        @Override // получим в телефоне список формата: Имя_nn Фамилия_nn
        public void onBindViewHolder(UserHolder userHolder, int position) { //Привязывает данные пользователя к userHolder
            User user = userList.get(position); // берем пользователя по порядку (по позиции)
            String userName = user.getUserName()+"  "+user.getUserLastName()+"  "+user.getPhone(); // каждая позиция списка //position - иттератор, индекс позиции
            userHolder.bind(userName); // привязывается к userHolder
        }// связывает макет элемента с данными элемента // на терелку положи еду // данная конкретная еда лежит именно в этой тарелке

        @Override
        public int getItemCount() { // Количество элементов всего
            return userList.size(); // В данном случае вернет к-во элементов коллекции контактов
        }
    }
}