package com.example.userlist_1313;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserListFragment extends Fragment {
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    Button addUserBtn;
    Button addUsersAlotBtn;
    Users users;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View view = layoutInflater.inflate(R.layout.fragment_user_list, viewGroup, false);

        users = new Users(getActivity());
        addUserBtn = view.findViewById(R.id.addUserBtn);
        addUsersAlotBtn = view.findViewById(R.id.addUsersAlotBtn);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FormUserActivity.class);
                startActivity(intent);
            }
        });

        addUsersAlotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAlotOfUsers(20); // для тестирования resyclerView добавляем сразу 20 пользователей
                Fragment fragment = new UserListFragment();
                FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
                fragmentManager.replace(R.id.fragmentContainer, fragment).commit();
            }
        });

        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        userAdapter = new UserAdapter(users.getUserList());
        recyclerView.setAdapter(userAdapter);
    }

    public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemTextView;
        String userParameters;
        int userPosition;

        public UserHolder(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.single_item, viewGroup, false));
            itemTextView = itemView.findViewById(R.id.itemTextView);
            itemView.setOnClickListener(this);
        }

        public void bind(String userParameters, int position) {
            this.userParameters = userParameters;
            this.userPosition = position;
            itemTextView.setText(userParameters);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), UserPagerActivity.class);

            intent.putExtra("userPosition", this.userPosition);
            startActivity(intent);
        }
    }


    public class UserAdapter extends RecyclerView.Adapter<UserHolder> {
        ArrayList<User> userList;
        public UserAdapter(ArrayList<User> userList) {
            this.userList = userList;
        }

        @Override
        public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new UserHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(UserHolder userHolder, int position) {
            User user = userList.get(position);
            String userParameters = user.getUserName()+"     "+user.getUserLastName();
            userHolder.bind(userParameters, position);
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }
    }

    public void addAlotOfUsers(int countUsers) { // метод для тестирования resyclerView
        for (int i = 0; i < countUsers; i++) {
            User user = new User();
            user.setUserName("Имя_"+i);
            user.setUserLastName("Фамилия_"+i);
            user.setPhone("+7 333 "+i);
            users.addUser(user);
        }
    }
}