package com.smartdom.sitemaps;

import com.smartdom.Element;

public class SitemapItem implements Element{
    private String itemType;
    private String item;
    private String itemLabel;
    private String mappings;
    private String icon;
    private int minValue;
    private int maxValue;
    private int step;
    private String frameLevel;

    public String getFrameLevel() {
        return frameLevel;
    }

    public void setFrameLevel(String frameLevel) {
        this.frameLevel = frameLevel;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItem() {
        return "item=" + item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemLabel() {
        return "label=\"" + itemLabel + "\"";
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public String getMappings() {
        return "mappings=[" + mappings + "]";
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

    public String getMinValue() {
        return "minValue=" + minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return "maxValue=" + maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public String getStep() {
        return "step=" + step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "SitemapItem{" +
                "itemType='" + itemType + '\'' +
                ", item='" + item + '\'' +
                ", itemLabel='" + itemLabel + '\'' +
                ", mappings='" + mappings + '\'' +
                ", icon='" + icon + '\'' +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", step=" + step +
                '}';
    }

    @Override
    public String printElement() {
        StringBuilder sb = new StringBuilder(500);
        sb
                .append(formatElement(15, this.getItemType()))
                .append(formatElement(20, this.getItem()))
                .append(formatElement(30, this.getItemLabel()));
        if(!"".equals(this.icon))
            sb.append(formatElement(20, this.getIcon()));
        if(!"".equals(this.mappings))
            sb.append(formatElement(200, this.getMappings()));
        if(this.minValue!=0 || this.maxValue!=0)
            sb
                    .append(formatElement(12, this.getMinValue()))
                    .append(formatElement(12, this.getMaxValue()))
                    .append(formatElement(8, this.getStep()));

        return sb.toString();
    }
}
