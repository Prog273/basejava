package ru.javawebinar.basejava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Ошибка. Резюме с UUID " + uuid + " уже есть в базе.", uuid);
    }
}
