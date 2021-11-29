package com.example.userlist_1313;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class UserPagerActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Users users;
    private int userPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pager);
        userPosition = getIntent().getIntExtra("userPosition", 0);
        viewPager = findViewById(R.id.userViewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        users = new Users(UserPagerActivity.this);

        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                User user = users.getUserList().get(position);
                Fragment fragment = new UserInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                bundle.putSerializable("userPosition", position);
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public int getCount() {
                return users.getUserList().size();
            }
        });
        viewPager.setCurrentItem(userPosition);
    }
}