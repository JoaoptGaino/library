package com.joaoptgaino.library.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    private TestUtils() {
        throw new AssertionError("Util class should not be instantiated.");
    }

    public static String readJsonFile(final String filePath) throws IOException {
        try {
            final File input = new ClassPathResource(filePath).getFile();
            return new String(Files.readAllBytes(Paths.get(input.getPath())));
        } catch (IOException e) {
            throw e;
        }
    }

    @SafeVarargs
    public static final <T> List<T> newList(T ...objects) {
        return new ArrayList<>(Arrays.asList(objects));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
