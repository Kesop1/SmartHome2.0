package com.smartdom;

public interface LinePrinter {

    String printLine();

    default String formatElement(int length, String element){
        return String.format("%-" + length + "s", element);
    }
}
