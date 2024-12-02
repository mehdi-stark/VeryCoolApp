package org.techwork.verycool;

import java.io.File;

import static org.techwork.verycool.utils.MapperUtils.transformToObject;

public class TestUtils {

    public static  <T> T getFromRessources(String nameFile, Class<T> classType) {
        File resource = new File("src/test/resources/data/" + nameFile);
        if (!resource.exists())
            throw new RuntimeException("Data file does not exits");
        return transformToObject(resource, classType);
    }
}
