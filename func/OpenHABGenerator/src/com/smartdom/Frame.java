package com.smartdom;

import java.util.ArrayList;

public class Frame {
    private String frameLevel;
    private String frameLabel;
    private String itemType;
    private String item;
    private String itemLabel;
    private String mappings;
    private String icon;
    private int minValue;
    private int maxValue;
    private int step;
    private ArrayList<Frame> subFrames = new ArrayList<>();

    public ArrayList<Frame> getSubFrames() {
        return subFrames;
    }

    public void setSubFrames(ArrayList<Frame> subFrames) {
        this.subFrames = subFrames;
    }

    public String getFrameLevel() {
        return frameLevel;

    }

    public void setFrameLevel(String frameLevel) {
        this.frameLevel = frameLevel;
    }

    public String getFrameLabel() {
        return frameLabel;
    }

    public void setFrameLabel(String frameLabel) {
        this.frameLabel = frameLabel;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemLabel() {
        return itemLabel;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public String getMappings() {
        return mappings;
    }

    public void setMappings(String mappings) {
        this.mappings = mappings;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "Frame{" +
                "frameLevel='" + frameLevel + '\'' +
                ", frameLabel='" + frameLabel + '\'' +
                ", itemType='" + itemType + '\'' +
                ", item=" + item +
                ", itemLabel='" + itemLabel + '\'' +
                ", mappings='" + mappings + '\'' +
                ", icon='" + icon + '\'' +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", step=" + step +
                ", subFrames=" + subFrames +
                '}';
    }
}
