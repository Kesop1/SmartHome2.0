package com.smartdom.sitemaps;

import com.smartdom.Element;

import java.util.ArrayList;
import static com.smartdom.Constants.*;

public class Frame implements Element {
    private String frameLevel;
    private String frameLabel;
    private ArrayList<SitemapItem> sitemapItems;
    private ArrayList<Frame> subFrames = new ArrayList<>();

    public ArrayList<Frame> getSubFrames() {
        return subFrames;
    }

    public String getFrameLevel() {
        return frameLevel;
    }

    public void setFrameLevel(String frameLevel) {
        this.frameLevel = frameLevel;
    }

    public String getFrameLabel() {
        return "label=" + frameLabel;
    }

    public void setFrameLabel(String frameLabel) {
        this.frameLabel = frameLabel;
    }

    @Override
    public String toString() {
        return "Frame{" +
                "frameLevel='" + frameLevel + '\'' +
                ", frameLabel='" + frameLabel + '\'' +
                ", sitemapItems=" + sitemapItems +
                ", subFrames=" + subFrames +
                '}';
    }

    public String printElement(){
        StringBuilder sb = new StringBuilder(1000);

        sb.append("Frame ").append(this.getFrameLabel()).append(OPEN_BRACE).append(LINE_SEPARATOR);
        if(!this.getSubFrames().isEmpty()){
            for (Frame f: this.getSubFrames()) {
                sb.append(f.printElement());
            }
        }

//        sb.append("//").append(this.description).append(System.getProperty("line.separator"));
//        sb.append(String.format("%-20s", this.type)).append(String.format("%-20s", this.name)).append(String.format("%-50s", "\"" + this.label + "\""))
//                .append(String.format("%-20s", "<" + this.icon + ">"));
//        StringBuilder group = new StringBuilder();
//        group.append("(");
//        for(String g: getGroup()){
//            group.append(g).append(", ");
//        }
//        group.append(")");
//        sb.append(String.format("%-20s", group)).append(this.binding).append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));
        return sb.toString();
    }
}
