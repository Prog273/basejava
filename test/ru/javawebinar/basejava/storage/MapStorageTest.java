package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    void getAll() {
        List<Resume> allResumes = new ArrayList<>(asList(new Resume[]{RESUME_1, RESUME_2, RESUME_3}));
        List<Resume> sortedResumes = storage.getAllSorted();
        assertEquals(allResumes, sortedResumes);
    }
}
