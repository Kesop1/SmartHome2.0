package com.smartdom;

public class Constants {

    private Constants(){
        //do not instantiate
    }

    //file locations
    public static final String HOME = "..\\..\\";
    public static final String CONFIG_FILE = HOME + "doc\\OpenHAB_configuration.xls";
    public static final String ITEMS_FOLDER = HOME + "func\\OpenHAB\\configurations\\items\\";
    public static final String SITEMAP_FOLDER = HOME + "func\\OpenHAB\\configurations\\sitemap\\";
    public static final String TITLE = "Chata";
    public static final String ITEMS_FILE_NAME = TITLE + ".items";
    public static final String SITEMAP_FILE_NAME = TITLE + ".sitemap";

    //sitemap
    public static final String SHEET_NAME_SITEMAP = "Sitemap";
    public static final String SITEMAP_TITLE = "sitemap demo label=\"" + TITLE + "\"";
    public static final String FRAME_LEVEL = "Frame";
    public static final String FRAME_LABEL = "Frame label";
    public static final String ITEM_LABEL = "Item label";
    public static final String MAPPINGS = "Mappings";
    public static final String ICON = "Icon";
    public static final String MIN_VALUE = "MinValue";
    public static final String MAX_VALUE = "MaxValue";
    public static final String STEP = "Step";

    //items
    public static final String SHEET_NAME_ITEMS = "Items";
    public static final String ITEM_DESC = "Item desc";
    public static final String ITEM_TYPE = "Item type";
    public static final String ITEM_NAME = "Item name";
    public static final String LABEL_TEXT = "Labeltext";
    public static final String ICON_NAME = "Iconname";
    public static final String GROUP = "Group1, Group2, ...";
    public static final String BINDING = "Bindingconfig";

    //system
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String OPEN_BRACE = "{";
    public static final String CLOSE_BRACE = "}";

}
