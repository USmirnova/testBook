package com.example.userlist_1313;
// Класс пользователя. Для задания параметров по каждому контакту: Фамилия, Имя, телефон, uuid
import java.util.UUID;

public class User {
    private String userName;
    private String userLastName;
    private String phone; // + это не число, поэтому номер в формате +7... будет строкой
    private UUID uuid;
// данный класс будет с двумя конструкторами - перегрузка метода
// в одном мы будем передавать параметр uuid, а в другом нет
// Значит, если передаем uuid - он взят из б.д. мы создаем его с созданным ранее uuid
// это нужно для курсора - адекватной считывалки строк UserCursorWrapper // там подробнее описано
// Если не передаем параметр uuid в конструкторе, значит пользователь создается впервые и ему нуже новый uuid
    // вторичное создание пользователя
    public User(UUID uuid) { // через конструктор будем uuid, считанного из бд пользователя
        this.uuid = uuid; // передаем его через параметр // т.к. он уже был создан ранее и есть в б.д.
    }
    // первичное создание пользователя
    public User() { // через конструктор будем задавать только uuid новому пользователю, остальное сеттерами
        this.uuid = UUID.randomUUID(); // передаем его не через параметр, а формируем спец. функцией
    }
    // геттеры для всех, сеттеры всем кроме uuid - формируется функцией при создании объекта
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserLastName() { return userLastName; }
    public void setUserLastName(String userLastName) { this.userLastName = userLastName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public UUID getUuid() { return uuid; }

}
