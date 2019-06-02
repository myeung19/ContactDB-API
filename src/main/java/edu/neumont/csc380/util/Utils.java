package edu.neumont.csc380.util;

import javax.activation.MimetypesFileTypeMap;

public class Utils
{
    public static String getTypeFromFileName(String fileName)
    {
        return new MimetypesFileTypeMap().getContentType(fileName);
    }
}
