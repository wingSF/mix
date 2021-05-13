package com.wing.lynne;

import java.io.File;

public class FileUtil {

    public static boolean isFile(String fileName){
        return new File(fileName).isFile();
    }
}
