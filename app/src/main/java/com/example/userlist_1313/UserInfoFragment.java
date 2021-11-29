package com.example.userlist_1313;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class UserInfoFragment extends Fragment {
    TextView infoUserFio;
    TextView infoUserPhone;
    Button editBtn;
    Button deleteBtn;
    Button backMainBtn;
    User user;
    Users users;
    int userPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        user = (User) bundle.getSerializable("user");
        userPosition = (int) bundle.get("userPosition");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_info_user, viewGroup, false);
        infoUserFio = view.findViewById(R.id.infoUserFio); //
        infoUserPhone = view.findViewById(R.id.infoUserPhone);
        editBtn = view.findViewById(R.id.editBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);
        backMainBtn= view.findViewById(R.id.backMainBtn);
        String fio = user.getUserLastName()+" "+user.getUserName();
        infoUserFio.setText(fio);
        infoUserPhone.setText(user.getPhone());

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FormUserActivity.class);
                intent.putExtra("userPosition", userPosition);
                startActivity(intent);
                getActivity().finish();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                users = new Users(getContext());
                users.deleteUser(user.getUuid());
                getActivity().onBackPressed();
                Toast.makeText(getContext(), "Удаление...", Toast.LENGTH_SHORT).show();
            }
        });

        backMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}
