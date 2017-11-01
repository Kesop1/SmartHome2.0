package com.smartdom;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Items implements SheetReader, FileCreator {

    private static final String SHEET_NAME = "Items";

    private static final String ITEM_DESC = "item desc";

    private static final String ITEM_TYPE = "item type";

    private static final String ITEM_NAME = "item name";

    private static final String LABEL_TEXT = "labeltext";

    private static final String ICON_NAME = "iconname";

    private static final String GROUP = "group1, group2, ...";

    private static final String BINDING = "bindingconfig";

//TODO dodac config file dla kolumn

    private ArrayList<Item> itemsList = new ArrayList<>();

    public ArrayList<Item> getItemsList() {
        return itemsList;
    }

    public String getSheetName() {
        return SHEET_NAME;
    }

    @Override
    public void readRow(HSSFRow row) {
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
    public boolean createFile(String path) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
            for(Item i: getItemsList()){
                writer.write(i.printItem());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }
}
