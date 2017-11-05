package com.smartdom.items;

import com.smartdom.Constants;
import com.smartdom.FileCreator;
import com.smartdom.SheetReader;
import com.smartdom.items.Item;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import static com.smartdom.Constants.*;

public class Items implements SheetReader, FileCreator {

    public String getItemsFile() {
        return ITEMS_FILE_NAME;
    }

//TODO dodac config file dla kolumn

    private ArrayList<Item> itemsList = new ArrayList<>();

    private HashMap<String, Integer> columns = new HashMap<>();

    public ArrayList<Item> getItemsList() {
        return itemsList;
    }

    @Override
    public String getSheetName() {
        return Constants.SHEET_NAME_ITEMS;
    }

    @Override
    public void readRow(HSSFRow row) {
        if(row==null) return;
        Item item = new Item();
        item.setDescription(row.getCell(columns.get(ITEM_DESC)) == null ? "" : row.getCell(columns.get(ITEM_DESC)).getStringCellValue());
        item.setType(row.getCell(columns.get(ITEM_TYPE)) == null ? "" : row.getCell(columns.get(ITEM_TYPE)).getStringCellValue());
        item.setName(row.getCell(columns.get(ITEM_NAME)) == null ? "" : row.getCell(columns.get(ITEM_NAME)).getStringCellValue());
        item.setLabel(row.getCell(columns.get(LABEL_TEXT)) == null ? "" : row.getCell(columns.get(LABEL_TEXT)).getStringCellValue());
        item.setIcon(row.getCell(columns.get(ICON_NAME)) == null ? "" : row.getCell(columns.get(ICON_NAME)).getStringCellValue());
        item.setBinding(row.getCell(columns.get(BINDING)) == null ? "" : row.getCell(columns.get(BINDING)).getStringCellValue());
        String groups = row.getCell(columns.get(GROUP)) == null ? "" : row.getCell(columns.get(GROUP)).getStringCellValue();
        ArrayList<String> groupsList = new ArrayList<>();
        if (!"".equals(groups)) {
            while (groups.contains(",")) {
                groupsList.add(groups.substring(0, groups.indexOf(",")));
                groups = groups.substring(groups.indexOf("'")).trim();
            }
            groupsList.add(groups);
        }
        item.setGroup(groupsList);
        itemsList.add(item);
    }

    @Override
    public HashMap<String, Integer> getColumns() {
        return columns;
    }

    @Override
    public boolean createFile(String path) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
            for(Item i: getItemsList()){
                writer.write(i.printElement());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
}
