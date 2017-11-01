package com.smartdom;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static final String HOME = "..\\..\\";
    public static final String CONFIG_FILE = HOME + "doc\\OpenHAB_configuration.xls";

    public static void main(String[] args) {
        FileInputStream configFileStream;
        try{
            configFileStream = new FileInputStream(new File(CONFIG_FILE));
            HSSFWorkbook workbook = new HSSFWorkbook(configFileStream);

            Items items = new Items();
            readItems(workbook, items);
            items.createFile(HOME + items.getItemsFile());
            System.out.println();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            System.out.println();
        }

    }

    private static boolean readItems(HSSFWorkbook workbook, Items items){
        if(workbook==null) return false;
        HSSFSheet itemsWorksheet = workbook.getSheet(items.getSheetName());
        items.readSheet(itemsWorksheet);
        return true;
    }
}
