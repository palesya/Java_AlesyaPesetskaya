package com.joint_walks.java_alesyapesetskaya.model;

public enum SEX {
    MAN("man","male"),
    WOMAN("woman","female");

    private final String user;
    private final String dog;

    SEX(String user, String dog) {
        this.user = user;
        this.dog = dog;
    }

    public String getUser() {
        return user;
    }

    public String getDog() {
        return dog;
    }
}
