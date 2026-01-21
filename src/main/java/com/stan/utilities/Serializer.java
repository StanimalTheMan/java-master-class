package com.stan.utilities;

import java.io.*;
import java.util.Base64;

public class Serializer {
    /** Read object from Base64 string */
    private static Object fromString(String s) throws IOException, ClassNotFoundException {
        // Stack Overflow: https://stackoverflow.com/questions/134492/how-to-serialize-an-object-into-a-string
        byte [] data = Base64.getDecoder().decode(s);
        ObjectInputStream outputInputStream = new ObjectInputStream(
                                        new ByteArrayInputStream(data));
        Object o = outputInputStream.readObject();
        outputInputStream.close();
        return o;
    }

    /** Write the object to a Base64 string. */
    private static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(o);
        objectOutputStream.close();
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }
}
