package com.coodev.module_compiler.util;


import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

public class Logger {
    private final Messager messager;

    public Logger(Messager messager) {
        this.messager = messager;
    }

    public void info(String info) {
        messager.printMessage(Diagnostic.Kind.NOTE, info);
    }

    public void info(String info, Object... objects) {
        String format = String.format(info, objects);
        messager.printMessage(Diagnostic.Kind.NOTE, format);
    }

    public void waring(CharSequence error) {
        messager.printMessage(Diagnostic.Kind.WARNING, error);
    }


    public void error(CharSequence error) {
        messager.printMessage(Diagnostic.Kind.ERROR, error);
    }

    public void error(Throwable throwable) {
        if (null != throwable) {
            messager.printMessage(Diagnostic.Kind.ERROR,
                    "An exception is , [" + throwable.getMessage() + "]"
                            + formatStackTrace(throwable.getStackTrace()));
        }
    }

    private String formatStackTrace(StackTraceElement[] stackTraceElements) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTraceElements) {
            sb.append("   at")
                    .append(element.toString())
                    .append("\n");
        }

        return sb.toString();
    }

}
