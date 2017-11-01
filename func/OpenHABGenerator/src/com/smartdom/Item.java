package com.smartdom;

import java.util.ArrayList;

public class Item {
    private String type;
    private String name;
    private String label;
    private String icon;
    private ArrayList<String> group;
    private String binding;
    private String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ArrayList<String> getGroup() {
        return group;
    }

    public void setGroup(ArrayList<String> group) {
        this.group = group;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", icon='" + icon + '\'' +
                ", group=" + group +
                ", binding='" + binding + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String printItem(){
        StringBuilder sb = new StringBuilder(250);
        sb.append("//").append(this.description).append(System.getProperty("line.separator"));
        sb.append(String.format("%-20s", this.type)).append(String.format("%-20s", this.name)).append(String.format("%-50s", "\"" + this.label + "\""))
                .append(String.format("%-20s", "<" + this.icon + ">"));
        StringBuilder group = new StringBuilder();
        group.append("(");
        for(String g: getGroup()){
            group.append(g).append(", ");
        }
        group.append(")");
        sb.append(String.format("%-20s", group)).append(this.binding).append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));
        return sb.toString();
    }
}
