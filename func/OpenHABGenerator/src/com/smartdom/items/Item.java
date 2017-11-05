package com.smartdom.items;

import com.smartdom.Element;

import java.util.ArrayList;
import static com.smartdom.Constants.*;

public class Item implements Element {
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

    public String printElement(){
        StringBuilder sb = new StringBuilder(250);
        sb.append("//").append(this.description).append(LINE_SEPARATOR);
        sb.append(formatElement(20, this.type)).append(formatElement(20, this.name)).append(formatElement(50, "\"" + this.label + "\""))
                .append(formatElement(20, "<" + this.icon + ">"));
        StringBuilder group = new StringBuilder();
        group.append("(");
        for(String g: getGroup()){
            group.append(g).append(", ");
        }
        group.append(")");
        sb.append(formatElement(20, group.toString())).append(this.binding).append(LINE_SEPARATOR).append(LINE_SEPARATOR);
        return sb.toString();
    }
}
