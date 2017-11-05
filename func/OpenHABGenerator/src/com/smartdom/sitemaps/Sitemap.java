package com.smartdom.sitemaps;

import com.smartdom.Element;
import com.smartdom.FileCreator;
import com.smartdom.SheetReader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.smartdom.Constants.*;

public class Sitemap implements SheetReader, FileCreator {

    public String getSheetName() {
        return SHEET_NAME_SITEMAP;
    }

    private ArrayList<Element> elementsList = new ArrayList<>();

    private HashMap<String, Integer> columns = new HashMap<>();

    public ArrayList<Element> getElementsList() {
        return elementsList;
    }

    @Override
    public boolean createFile(String path) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
            writer.write(SITEMAP_TITLE);
            writer.write(LINE_SEPARATOR);
            writer.write(OPEN_BRACE);
            writer.write(LINE_SEPARATOR);
            for(Element f: getElementsList()){
                writer.write(f.printElement());
            }
            writer.write(CLOSE_BRACE);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void readRow(HSSFRow row) {
        if (row == null) return;
        DataFormatter formatter = new DataFormatter();
        Frame frame = new Frame();
        SitemapItem item = new SitemapItem();
        frame.setFrameLevel(formatter.formatCellValue(row.getCell(columns.get(FRAME_LEVEL))));
        frame.setFrameLabel(row.getCell(columns.get(FRAME_LABEL)) == null ? "" : row.getCell(columns.get(FRAME_LABEL)).getStringCellValue());
        if(row.getCell(columns.get(ITEM_TYPE)) != null){
            item.setItemType(row.getCell(columns.get(ITEM_TYPE)) == null ? "" : row.getCell(columns.get(ITEM_TYPE)).getStringCellValue());
            item.setItem(row.getCell(columns.get(ITEM_NAME)) == null ? "" : row.getCell(columns.get(ITEM_NAME)).getStringCellValue());
            item.setItemLabel(row.getCell(columns.get(ITEM_LABEL)) == null ? "" : row.getCell(columns.get(ITEM_LABEL)).getStringCellValue());
            item.setMappings(row.getCell(columns.get(MAPPINGS)) == null ? "" : row.getCell(columns.get(MAPPINGS)).getStringCellValue());
            item.setIcon(row.getCell(columns.get(ICON)) == null ? "" : row.getCell(columns.get(ICON)).getStringCellValue());
            item.setMinValue(row.getCell(columns.get(MIN_VALUE)) == null ? 0 : (int) row.getCell(columns.get(MIN_VALUE)).getNumericCellValue());
            item.setMaxValue(row.getCell(columns.get(MAX_VALUE)) == null ? 0 : (int) row.getCell(columns.get(MAX_VALUE)).getNumericCellValue());
            item.setStep(row.getCell(columns.get(STEP)) == null ? 0 : (int) row.getCell(columns.get(STEP)).getNumericCellValue());
        }
        if(item.getItemType()!=null)
            elementsList.add(item);
        else
            elementsList.add(frame);
    }

    @Override
    public HashMap<String, Integer> getColumns() {
        return columns;
    }

//    public boolean setSubframes() {
//        String pattern = "[0-9]+";
//        Pattern p = Pattern.compile(pattern);
//        for (int i = 0; i < this.getElementsList().size(); i++) {
//            Frame frame = this.getElementsList().get(i);
//            Matcher m = p.matcher(frame.getFrameLevel());
//            if (!m.matches()) {
//                int j = i;
//                boolean found = false;
////                loop backwards and find the element with the right level
//                do {
//                    j--;
//                    if (this.getElementsList().get(j).getFrameLevel().equals(frame.getFrameLevel().substring(0, frame.getFrameLevel().length() - 1))) {
//                        found = true;
//                        break;
//                    }
//                } while (j >= 0);
////                parent element found, add the child to its subFrames list
//                if (found) {
//                    this.getElementsList().get(j).getSubFrames().add(frame);
//                } else {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

    public Sitemap sortFrames(){

        return null;
    }
}
