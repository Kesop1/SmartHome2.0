package com.smartdom;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.HashMap;
import java.util.Iterator;

public interface SheetReader {

    String getSheetName();

    default boolean readSheet(HSSFSheet sheet) {
        if(sheet==null) return false;
        Iterator rows = sheet.rowIterator();
        while (rows.hasNext()) {
            HSSFRow row = (HSSFRow) rows.next();
            if(row.getRowNum()==0) columnsPosition(row);
            else readRow(row);
        }
        return true;
    }

    void readRow(HSSFRow row);

    HashMap<String, Integer> getColumns();

    default void columnsPosition(HSSFRow row){
        Iterator cells = row.cellIterator();
        while (cells.hasNext()) {
            HSSFCell cell = (HSSFCell) cells.next();
            String value = cell.getStringCellValue();
            int index = cell.getColumnIndex();
            getColumns().put(value, index);
        }
    }
}
