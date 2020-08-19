package com.example.onsitetask1;

import java.nio.file.Path;

public class DirectoryCard {

    private int imgRsc;
    private String directoryName;
    private String path;
    private String openStatus;

    public DirectoryCard(String name, String p, String opnSts, int img) {
        directoryName = name;
        path = p;
        openStatus = opnSts;
        imgRsc = img;
    }

    public DirectoryCard(String name, String p, String opnSts) {
        directoryName = name;
        openStatus = opnSts;
        path = p;
    }

    public void InvertArrow() {
        if(imgRsc == R.drawable.rightarrow)
            imgRsc = R.drawable.downarrow;
        else
            imgRsc = R.drawable.rightarrow;
    }

    public void InvertStatus() {
        if(openStatus == "true")
            openStatus = "false";
        else
            openStatus = "true";
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public int getImgRsc() {
        return imgRsc;
    }

    public String getPath() {
        return path;
    }

    public String getOpenStatus() {
        return openStatus;
    }
}
