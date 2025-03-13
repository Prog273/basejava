package ru.javawebinar.basejava.model;

import com.github.javafaker.Faker;

import java.util.Locale;

public class FullNameGenerator {
    public static String generateFullName() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().fullName();
    }
}
