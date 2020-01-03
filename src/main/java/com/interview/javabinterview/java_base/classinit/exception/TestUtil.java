package com.interview.javabinterview.java_base.classinit.exception;

import java.io.File;
import java.util.Objects;

/**
 * @author jianyun.zhao
 * Created at 2019/11/13
 */
public class TestUtil {
    private static int id = countFiles();

    public static int countFiles() {

        File file = new File("/xx");
        return Objects.requireNonNull(file.list()).length;
    }

    public static void main(String[] args) {

        System.out.println(id);
    }
}
