package ru.javawebinar.basejava.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Ошибка. Резюме с UUID " + uuid + " нет в базе.", uuid);
    }
}
