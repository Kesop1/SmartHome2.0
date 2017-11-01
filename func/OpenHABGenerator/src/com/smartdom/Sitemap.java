package com.smartdom;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sitemap implements SheetReader, FileCreator{

    private static final String SHEET_NAME = "Sitemap";

    public String getSheetName() {
        return SHEET_NAME;
    }

    private ArrayList<Frame> framesList = new ArrayList<>();

    private static final String FRAME_LEVEL = "Frame";

    private static final String FRAME_LABEL = "Frame label";

    private static final String ITEM_TYPE = "Item type";

    private static final String ITEM_NAME = "Item name";

    private static final String ITEM_LABEL = "Item label";

    private static final String MAPPINGS = "Mappings";

    private static final String ICON = "Icon";

    private static final String MIN_VALUE = "MinValue";

    private static final String MAX_VALUE = "MaxValue";

    private static final String STEP = "Step";

    public ArrayList<Frame> getFramesList() {
        return framesList;
    }

    @Override
    public boolean createFile(String path) {
        return false;
    }

    @Override
    public void readRow(HSSFRow row) {
        if(row==null) return;
        DataFormatter formatter = new DataFormatter();
        Frame frame = new Frame();
        frame.setFrameLevel(formatter.formatCellValue(row.getCell(columns.get(FRAME_LEVEL))));
        frame.setFrameLabel(row.getCell(columns.get(FRAME_LABEL)) == null ? "" : row.getCell(columns.get(FRAME_LABEL)).getStringCellValue());
        frame.setItemType(row.getCell(columns.get(ITEM_TYPE)) == null ? "" : row.getCell(columns.get(ITEM_TYPE)).getStringCellValue());
        frame.setItem(row.getCell(columns.get(ITEM_NAME)) == null ? "" : row.getCell(columns.get(ITEM_NAME)).getStringCellValue());
        frame.setItemLabel(row.getCell(columns.get(ITEM_LABEL)) == null ? "" : row.getCell(columns.get(ITEM_LABEL)).getStringCellValue());
        frame.setMappings(row.getCell(columns.get(MAPPINGS)) == null ? "" : row.getCell(columns.get(MAPPINGS)).getStringCellValue());
        frame.setIcon(row.getCell(columns.get(ICON)) == null ? "" : row.getCell(columns.get(ICON)).getStringCellValue());
        frame.setMinValue(row.getCell(columns.get(MIN_VALUE))==null ? 0 : (int) row.getCell(columns.get(MIN_VALUE)).getNumericCellValue());
        frame.setMaxValue(row.getCell(columns.get(MAX_VALUE))==null ? 0 : (int) row.getCell(columns.get(MAX_VALUE)).getNumericCellValue());
        frame.setStep(row.getCell(columns.get(STEP))==null ? 0 : (int) row.getCell(columns.get(STEP)).getNumericCellValue());
        framesList.add(frame);
    }

    public boolean setSubframes(){
        String pattern = "[0-9]+";
        Pattern p = Pattern.compile(pattern);
        for (int i= 0; i<this.getFramesList().size(); i++) {
            Frame frame = this.getFramesList().get(i);
            Matcher m = p.matcher(frame.getFrameLevel());
            if(!m.matches()){
                int j = i;
                boolean found = false;
//                loop backwards and find the element with the right level
                do{
                    j--;
                    if(this.getFramesList().get(j).getFrameLevel().equals(frame.getFrameLevel().substring(0, frame.getFrameLevel().length()-1))){
                        found = true;
                        break;
                    }
                }while(j>=0);
//                parent element found, add the child to its subFrames list
                if(found){
                    this.getFramesList().get(j).getSubFrames().add(frame);
                }
                else{
                    return false;
                }
            }
        }
        return true;
    }
}
