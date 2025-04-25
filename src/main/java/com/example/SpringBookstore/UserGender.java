package com.example.SpringBookstore;

public enum UserGender {
    MALE('M'),
    FEMALE('F');

    final Character genderInitial;

    UserGender(Character genderInitial) {
        this.genderInitial = genderInitial;
    }
}
