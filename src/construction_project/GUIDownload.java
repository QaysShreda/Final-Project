/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package construction_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohammad.y
 */
public class GUIDownload {

    InputStream inputStream;
    String saveFilePath;
    String saveFileDir = "C:\\Users\\Mohammad odeh\\Desktop";//Must change to our path
    String fileName = "Manifest.xml";//Must change to our Code
    static FileOutputStream outputStream;// opens an output stream to save into file

    Tracker trackFile;
    static LinkedList<FilePortion> SegmentDownload;//Use to store the the downloaded segment

    public GUIDownload(Tracker trackFile) {
        super();
        try {
            System.out.println("Get the file path");
            String saveFilePath = saveFileDir + File.separator + fileName;
            this.outputStream = new FileOutputStream(saveFilePath);
            System.out.println(this.outputStream);
            this.SegmentDownload = new LinkedList<FilePortion>();
            this.trackFile = trackFile;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// TODO Auto-generated constructor stub
    }

    public boolean isDownload(FilePortion p) {
        boolean flag = false;
        for (int i = 0; i < this.SegmentDownload.size(); i++) {
            FilePortion portion = this.SegmentDownload.get(i);
            if (portion.equals(p)) {
                flag = true;
            }
        }
        return flag;
    }

    public void downloadFile(File f) throws IOException {
        //This function must be tagged into download button in GUI
        //Read all the portions locations and store in linkedlist to achive this we will call getPartsLocation() method
        System.out.println("Start to get the portions Location");
        LinkedList<FilePortion> portionsList = this.trackFile.getPortionsLocation(f);
//Loop through the partions linkedlist to download each portion
        for (int i = 0; i < portionsList.size(); i++) {
            System.out.println(portionsList.get(i).showPortionData());

            //Get the portios array one by one  
            FilePortion portion = portionsList.get(i);
            //portion:this maybe contian on location or moer so must loop if the first location is valid 
            //and the part is downloaded break the loop
            LinkedList<Object> list = portion.getList();
            for (int r = 0; r < list.size(); r++) {
                Object obj = list.get(r);
                //Stop Here
            }
        }

    }

    public static void main(String h[]) {
        try {
            Tracker tracker = new Tracker();
            GUIDownload main = new GUIDownload(tracker);
            main.downloadFile(tracker.getFile());
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

    }
}
