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
    String saveFileDir = "";//Must change to our path
    String fileName = "";//Must change to our Code
    static FileOutputStream outputStream;// opens an output stream to save into file
            
    Tracker readFile;
    static LinkedList<FilePortion> AllSegmentDownload;

    public GUIDownload(Tracker trackFile) {
        super();
        try {
            String saveFilePath = saveFileDir + File.separator + fileName;
            this.outputStream = new FileOutputStream(saveFilePath);
            this.AllSegmentDownload = new LinkedList<FilePortion>();
            this.readFile = readFile;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// TODO Auto-generated constructor stub
    }

    public void downloadFile(File f) throws IOException {
        //This function must be tagged into the download button in GUI

        //Read all the portions locations and store in linkedlist to achive this we will call getPartsLocation() method
        LinkedList<FilePortion> portionsList = this.readFile.getPortionsLocation(f);
//Loop through the partions linkedlist to download each portion
        for (int i = 0; i < portionsList.size(); i++) {
            FilePortion p = portionsList.get(i);
            LinkedList<Object> list = p.getList();
            for (int r = 0; r < list.size(); r++) {

                Object obj = list.get(r);
                //Here
            }
        }

    }

    public static void main(String h[]) {
        try {
            Tracker tracker = new Tracker();
            GUIDownload main = new GUIDownload(tracker);
            
            main.downloadFile(tracker.getFile());
           
        } catch (IOException ex) {
            Logger.getLogger(GUIDownload.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
