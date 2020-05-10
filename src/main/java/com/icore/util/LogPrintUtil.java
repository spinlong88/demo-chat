package com.icore.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogPrintUtil {
    public static String logExceptionTack(Throwable e){
        StringWriter errorsWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(errorsWriter));
        return errorsWriter.toString();
    }

}
