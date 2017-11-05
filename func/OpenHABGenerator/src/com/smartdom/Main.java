package com.smartdom;

import com.smartdom.items.Items;
import com.smartdom.sitemaps.Sitemap;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.smartdom.Constants.*;

public class Main {

    final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        FileInputStream configFileStream;
        try{
            configFileStream = new FileInputStream(new File(CONFIG_FILE));
            HSSFWorkbook workbook = new HSSFWorkbook(configFileStream);

            logger.log(Level.INFO, "Reading Items sheet");
            Items items = new Items();
            readSheet(workbook, items);
            logger.log(Level.INFO, "Items sheet correctly read");

            logger.log(Level.INFO, "Reading Sitemap sheet");
            Sitemap sitemap = new Sitemap();
            readSheet(workbook, sitemap);
//            sitemap.setSubframes();
            logger.log(Level.INFO, "Sitemap sheet correctly read");

            items.createFile(ITEMS_FOLDER + items.getItemsFile());

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
