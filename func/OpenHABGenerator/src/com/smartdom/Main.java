package com.smartdom;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.smartdom.Constants.*;

public class Main {

    public static void main(String[] args) {
        FileInputStream configFileStream;
        try{
            configFileStream = new FileInputStream(new File(CONFIG_FILE));
            HSSFWorkbook workbook = new HSSFWorkbook(configFileStream);

            Items items = new Items();
            readSheet(workbook, items);
            items.createFile(ITEMS_FOLDER + items.getItemsFile());
            Sitemap sitemap = new Sitemap();
            readSheet(workbook, sitemap);
            sitemap.setSubframes();

            System.out.println();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            System.out.println();
        }

    }

    private static boolean readSheet(HSSFWorkbook workbook, SheetReader reader){
        if(workbook==null) return false;
        HSSFSheet worksheet = workbook.getSheet(reader.getSheetName());
        reader.readSheet(worksheet);
        return true;
    }

}
