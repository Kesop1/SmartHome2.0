package com.smartdom;

public interface Constants {

    //file locations
    String HOME = "..\\..\\";
    String CONFIG_FILE = HOME + "doc\\OpenHAB_configuration.xls";
    String ITEMS_FOLDER = HOME + "func\\OpenHAB\\configurations\\items\\";
    String SITEMAP_FOLDER = HOME + "func\\OpenHAB\\configurations\\sitemap\\";
    String TITLE = "Chata";
    String ITEMS_FILE_NAME = TITLE + ".items";
    String SITEMAP_FILE_NAME = TITLE + ".sitemap";

    //sitemap
    String SHEET_NAME_SITEMAP = "Sitemap";
    String SITEMAP_TITLE = "sitemap demo label=\"" + TITLE + "\"";
    String FRAME_LEVEL = "Frame";
    String FRAME_LABEL = "Frame label";
    String ITEM_LABEL = "Item label";
    String MAPPINGS = "Mappings";
    String ICON = "Icon";
    String MIN_VALUE = "MinValue";
    String MAX_VALUE = "MaxValue";
    String STEP = "Step";

    //items
    String SHEET_NAME_ITEMS = "Items";
    String ITEM_DESC = "Item desc";
    String ITEM_TYPE = "Item type";
    String ITEM_NAME = "Item name";
    String LABEL_TEXT = "Labeltext";
    String ICON_NAME = "Iconname";
    String GROUP = "Group1, Group2, ...";
    String BINDING = "Bindingconfig";

    //system
    String LINE_SEPARATOR = System.getProperty("line.separator");
    String OPEN_BRACE = "{";
    String CLOSE_BRACE = "}";

}
