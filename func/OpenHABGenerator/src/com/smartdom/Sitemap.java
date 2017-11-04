package com.smartdom;

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

    private ArrayList<Frame> framesList = new ArrayList<>();

    private HashMap<String, Integer> columns = new HashMap<>();

    public ArrayList<Frame> getFramesList() {
        return framesList;
    }

    @Override
    public boolean createFile(String path) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
            writer.write(SITEMAP_TITLE);
            writer.write(LINE_SEPARATOR);
            writer.write(OPEN_BRACE);
            writer.write(LINE_SEPARATOR);
            for(Frame f: getFramesList()){
                writer.write(f.printLine());
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
        frame.setFrameLevel(formatter.formatCellValue(row.getCell(columns.get(FRAME_LEVEL))));
        frame.setFrameLabel(row.getCell(columns.get(FRAME_LABEL)) == null ? "" : row.getCell(columns.get(FRAME_LABEL)).getStringCellValue());
        frame.setItemType(row.getCell(columns.get(ITEM_TYPE)) == null ? "" : row.getCell(columns.get(ITEM_TYPE)).getStringCellValue());
        frame.setItem(row.getCell(columns.get(ITEM_NAME)) == null ? "" : row.getCell(columns.get(ITEM_NAME)).getStringCellValue());
        frame.setItemLabel(row.getCell(columns.get(ITEM_LABEL)) == null ? "" : row.getCell(columns.get(ITEM_LABEL)).getStringCellValue());
        frame.setMappings(row.getCell(columns.get(MAPPINGS)) == null ? "" : row.getCell(columns.get(MAPPINGS)).getStringCellValue());
        frame.setIcon(row.getCell(columns.get(ICON)) == null ? "" : row.getCell(columns.get(ICON)).getStringCellValue());
        frame.setMinValue(row.getCell(columns.get(MIN_VALUE)) == null ? 0 : (int) row.getCell(columns.get(MIN_VALUE)).getNumericCellValue());
        frame.setMaxValue(row.getCell(columns.get(MAX_VALUE)) == null ? 0 : (int) row.getCell(columns.get(MAX_VALUE)).getNumericCellValue());
        frame.setStep(row.getCell(columns.get(STEP)) == null ? 0 : (int) row.getCell(columns.get(STEP)).getNumericCellValue());
        framesList.add(frame);
    }

    @Override
    public HashMap<String, Integer> getColumns() {
        return columns;
    }

    public boolean setSubframes() {
        String pattern = "[0-9]+";
        Pattern p = Pattern.compile(pattern);
        for (int i = 0; i < this.getFramesList().size(); i++) {
            Frame frame = this.getFramesList().get(i);
            Matcher m = p.matcher(frame.getFrameLevel());
            if (!m.matches()) {
                int j = i;
                boolean found = false;
//                loop backwards and find the element with the right level
                do {
                    j--;
                    if (this.getFramesList().get(j).getFrameLevel().equals(frame.getFrameLevel().substring(0, frame.getFrameLevel().length() - 1))) {
                        found = true;
                        break;
                    }
                } while (j >= 0);
//                parent element found, add the child to its subFrames list
                if (found) {
                    this.getFramesList().get(j).getSubFrames().add(frame);
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
