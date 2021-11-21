package com.example.userlist_1313;
// активность для размещения viewPager
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class UserPagerActivity extends AppCompatActivity {
    private ViewPager viewPager; // чтобы добраться до layout viewPager
    private Users users; // для списка пользователей // приватная. Только для обращения к ней в этом классе
    private int userPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pager);
        userPosition = getIntent().getIntExtra("userPosition", 0);
        viewPager = findViewById(R.id.userViewPager); // находим его на активности
        FragmentManager fragmentManager = getSupportFragmentManager(); // чтобы можно было менять фрагменты на активности нашего приложения
        users = new Users(UserPagerActivity.this); // была ошибка - 'UserPagerActivity' is not an enclosing class: №строки
        // это из-за <> в объявлении класса UserPagerActivity<UserPagerActivity> extends AppCompatActivity // удалить <> и их содержимое
        //users = new Users(getApplicationContext()); // это был выход из положения на тот момент. найден случайно.
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) { // устанавливаем адаптер с устаревшим методом (про актуальный не рассказали)
            @Override
            public Fragment getItem(int position) {
                User user = users.getUserList().get(position);// получаем текущего пользователя
                Fragment fragment = new UserInfoFragment(); // устанавливаем фрагмент
                Bundle bundle = new Bundle(); // bundle - по сути та же коллекция // для передачи данных
                bundle.putSerializable("user", user); //передаем пользователя // Класс User.java надо сделать сериализуемым // дописать implements Serializable // иначе подчеркнет красным
                bundle.putSerializable("userPosition", userPosition); // передаем позицию, т.к. без нее на редактирование открывается дефолтное значение 0
                fragment.setArguments(bundle); // получаем пользователя
                return fragment; // возвращаем фрагмент
            }

            @Override
            public int getCount() {
                return users.getUserList().size();
            } // к-во пользователей
        });
        viewPager.setCurrentItem(userPosition); // устанавливаем текущего пользователя // писать обязательно после адаптера
    }
}