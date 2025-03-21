package ru.javawebinar.basejava.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Resume with UUID " + uuid + " is not in database.", uuid);
    }
}
