package com.example.userlist_1313;
// главная активность. В ней раньше размещался непосредственно список пользователей, а теперь
// здесь отображается фрагмент UserListFragment, т.е. фрагмент со списком пользователей
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager(); // установили фрагмент-менеджер
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Создаём фрагмент
        Fragment fragment = new UserListFragment(); // создали сам фрагмент
        //Для размещения этого фрагмента на экране обращаемся к менеджеру фрагментов
        // id контейнера, куда будем помещать фрагмент; сам фрагмент; 3й параметр - тег-название, но пока не обязательно
        fragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit(); // с помощью фрагмент-менеджера устанавливаем на экране этот фрагмент
    }
}