/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package construction_project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author Mohammad.y
 */
public class Tracker {

    private String FileName = "manifest stream.txt";//Must change to dynamic fileName
    private File fileObject;
    private int numberOfSegment = 1;

    public Tracker() {
        super();
        this.fileObject = new File(this.FileName);
    }

    public File getFile() {
        return fileObject;
    }

    public String checkPortionType(String PortionURL) {
        PortionURL = PortionURL.replaceAll("\r", "");
        if (PortionURL.contains("-segment")) {
            return "segment";
        } else if (PortionURL.contains("-text")) {
            return "manifest";
        } else {
            return "false";
        }
    }

    public LinkedList<FilePortion> getPortionsLocation(File file) {
        String fileData = "";//Variable to store the file's data
        try {
            //Opening a connection into the file to read the data as a raw bytes
            FileInputStream inStream = new FileInputStream(file);
            byte[] bf = new byte[(int) file.length()];
            inStream.read(bf);
            fileData = new String(bf, "UTF-8");//Convert the stream byte data into a string data.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LinkedList<FilePortion> portionsList = new LinkedList<FilePortion>();
        String[] FilesPortions = fileData.split("\\*\\*");//Split the string, the seperator will bee ** as the Dr request
        /*Each row in the FilesPortions array reperesent a location  for one portation 
        or it maybe ccontain more than one location as an alternative locations for the same portion*/
        //loop through the array to get the the portions locations.
        for (int i = 0; i < FilesPortions.length; i++) {
            FilePortion portion = new FilePortion(i + 1);//This poriotn will contain the main and alternaive  location for the same portion
            //Each line represent one location for the same portion
            String[] line = FilesPortions[i].split("\n");
            for (int j = 0; j < line.length; j++) {
                //There are different in the end of line according to the platform
                //We will use the windows as platform and it end with CRLF(CR=\r,LF=\n)
                //So we split on \n and have to replace the CR with none
                line[j] = line[j].replaceAll("\r", "");
                if (!line[j].equals("") && !line[j].equals("\r")) {
                    //Check if the portion is segment or manifest(nested portion)
                    if (checkPortionType(line[j]) == "segment") {
                        //Store in the portion (URL,Order) as a segment object 
                        Segment segment = new Segment(i + 1, line[j]);
                        portion.AddInList(segment);//Store the segment in the portions list

                    } else if (checkPortionType(line[j]) == "manifest") {
                        Manifest manifest = new Manifest(i + 1, line[j]);
                        //Start recursive calling until reach a segment file
                        manifest.list = getPortionsLocation(new File(manifest.getURLName()));
                        portion.AddInList(manifest);
                    }
                }
            }
            portionsList.add(portion);
        }
        return portionsList;
    }
}