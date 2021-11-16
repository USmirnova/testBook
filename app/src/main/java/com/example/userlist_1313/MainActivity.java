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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity { // наследуется от AppCompatActivity //изначально есть в активности
    RecyclerView recyclerView; // объявляем переменную компонента RecyclerView
    UserAdapter userAdapter; // объявляем адаптер
    Button addUserBtn; // кнопка добавления пользователя
    Users users; // создаем объект, отвечающий за взаимоедействие с пользователями

    @Override // переопределяем метод
    protected void onCreate(Bundle savedInstanceState) { // создание активности c объектом для сохранения состояния
        super.onCreate(savedInstanceState); //? вызов родительского метода с объектом bundle - информация о состоянии активности ?
        setContentView(R.layout.activity_main); // построение макета

        users = new Users(MainActivity.this); // создаем обект Users, отвечающий за взаимоедействие с пользователями передаем текущую активность в качестве контекста
        recyclerView = findViewById(R.id.recyclerView); // находим по id
        addUserBtn = findViewById(R.id.addUserBtn); // Кнопка добавления пользователя
        addUserBtn.setOnClickListener(new View.OnClickListener() { // вешаем событие на нее
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormUserActivity.class); // Создаем намерение переключиться из текущей активности в ту где будем добавлять пользователя
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
    // Класс для настройки самого элемента, наследуемый от RecyclerView.ViewHolder. Свойство itemView из него.
    // интерфейс View.OnClickListener - чтобы элементы были кликабельны. Реализуем onClick()
    public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemTextView; // Объявляем переменную текстовой вьюшки из макете элемента
        String userParameters; // параметры контакта, которые будем выводить в списке RecyclerView, например имя и фамилию
        int userPosition; // для поиска юзера в списке // передавать будем в bind

        public UserHolder(LayoutInflater inflater, ViewGroup viewGroup) { //Конструктор класса элемента списка // принимает инфлятор и объект viewGroup (андроид его сам создает, наша задача передать его дальше) на котором будут расположены все виджеты данного приложения?
            super(inflater.inflate(R.layout.single_item, viewGroup, false)); //?? вызываем родительский метод?, передаем в него раздуватель, тот раздувает макет элемента с группой виджетов и еще false зачем-то
            itemTextView = itemView.findViewById(R.id.itemTextView); //на раздутом макете - свойство itemView - свойство из класса RecyclerView.ViewHolder от которого наследуется данный класс находим вьюшку по id
            itemView.setOnClickListener(this); // связали текущий элемент с кликом
        } // самая непонятная фиговина, наполнитель экрана виджетами(вьюхами)?
        // инфлятор раздувает макет, чтобы на нем можно было разместить визуальные компоненты (кнопки, текст, и т.д.)
        // до этого макет сколлапсирован, как сингулярность?

        public void bind(String userParameters, int position) { // укладывает данные, // сюда впихнем и позицию юзера
            this.userParameters = userParameters; // переданные в параметре
            this.userPosition = position; // присвоим позицию текущего пользователя // нужно для редактирования и удаления юзеров
            itemTextView.setText(userParameters); // в текстовую вьюшку каждого холдера (макета элемента)

        } // вызывается в методе onBindViewHolder, в который мы передаем userHolder

        // чтобы элемент стал кликабельным нужно наследоваться или подписываться на класс View.OnClickListener и реализовывать onClick метод
        @Override // теперь каждый элемент списка будет кликабельным
        public void onClick(View view) { // Здесь определяются действия, которые будут происходить по клику
            //Toast.makeText(MainActivity.this, "ItemClick", Toast.LENGTH_SHORT).show(); // короткое уведомление о клике
            Intent intent = new Intent(MainActivity.this, InfoUserActivity.class); // Создаем намерение переключиться из текущей активности в информацию о пользователе
            //------КАК передать текущего пользователя!
            //String test  = userList.get(0).getUuid().toString(); // -
            //int userPosition = this.userPosition;//" Тестовая строка"; // Удалить
            intent.putExtra("userPosition", this.userPosition); // передаем данные в инфоАктивность
            startActivity(intent); // запускаем активность
        }
    }//class UserHolder

    // Класс для размещения элементов. Наследуется (расширяет) RecyclerView.Adapter<UserHolder>
    public class UserAdapter extends RecyclerView.Adapter<UserHolder> {
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
        public void onBindViewHolder(UserHolder userHolder, int position) { // Привязывает данные пользователя к  плашке userHolder
            User user = userList.get(position); // берем пользователя по порядку (по позиции) // здесь позиция изначально включена в метод
            String userParameters = user.getUserName()+"  "+user.getUserLastName()+"  "+user.getPhone(); // каждую позицию списка //position - иттератор, индекс позиции
            userHolder.bind(userParameters, position); // привязывается к userHolder // здесь же передадим позицию юзера
            // у userHolder вызываем метод bind(), который осуществляет привязку
        }// связывает макет элемента с данными элемента // на терелку положи еду // данная конкретная еда лежит именно на этой тарелке

        @Override
        public int getItemCount() { // Количество элементов всего
            return userList.size(); // В данном случае вернет к-во элементов коллекции контактов
        }
    }//class UserAdapter
}