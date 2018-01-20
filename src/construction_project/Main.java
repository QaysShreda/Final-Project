/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package construction_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Mohammad.y
 */
public class Main extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Main main = new Main();
                main.setVisible(true);
            }
        });
    }
    /**
     * *By Team Member*
     */
    private static final int BUFFER_SIZE = 4096;
    InputStream inputStream;
    String saveFilePath;
    String saveFileDir = "C:\\Users\\Mohammad odeh\\Desktop";//Must change to our path
    String fileName = "Manifest.xml";//Must change to our Code
    static FileOutputStream outputStream;// opens an output stream to save into file

    Tracker trackFile;
    static LinkedList<FilePortion> ListDownloadedSegments;
    /**
     * *End By Team Member*
     */

    public static JLabel imageView, progressLabel;
    private JTextArea textView;
    private JScrollPane scrollPane;
    private JTextField urlField;
    private JSlider animateSlider;
    private JLabel sliderLabel;
    private JButton stepButton;

    private Timer timer;
    private InputStream multipart;
    private String fileType = "txt";
    private static final String SEQ_SUFFIX = "-seq";
    private static final String MANIFEST_SUFFIX = ".segments";

    ByteArrayOutputStream dest;

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Multi-part Downloader");
        dest = new ByteArrayOutputStream();
        urlField = new JTextField(40);
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startDownload();
            }
        });
        stepButton = new JButton("Step");
        stepButton.setEnabled(false);
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //downloadNextFileFromSequence();
            }
        });
        sliderLabel = new JLabel("Animation Speed:");
        sliderLabel.setEnabled(false);
        animateSlider = new JSlider(0, 10, 0);
        //animateSlider.setMajorTickSpacing(10);
        //animateSlider.setMinorTickSpacing(1);
        //animateSlider.setPaintTicks(true);
        //animateSlider.setPaintLabels(true);
        animateSlider.setEnabled(false);
        animateSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                //animate();
            }
        });

        imageView = new JLabel();
        textView = new JTextArea();
        textView.setEditable(false);

        progressLabel = new JLabel("Nothing downloaded yet.");

        scrollPane = new JScrollPane(textView);
        JPanel urlPanel = new JPanel(new BorderLayout());
        urlPanel.add(urlField, BorderLayout.CENTER);
        urlPanel.add(progressLabel, BorderLayout.SOUTH);
        urlPanel.add(new JLabel("Download URL:"), BorderLayout.NORTH);
        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.add(sliderLabel, BorderLayout.NORTH);
        sliderPanel.add(animateSlider, BorderLayout.SOUTH);
        JPanel controls = new JPanel();
        controls.add(startButton);
        controls.add(stepButton);
        controls.add(sliderPanel);
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(urlPanel, BorderLayout.CENTER);
        controlPanel.add(controls, BorderLayout.EAST);
        add(controlPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);

        setSize(600, 400);
    }

    /**
     * Begins the download. Downloads and previews the entire stream if it's a
     * normal file. If it is a sequence file (with suffix .seq), downloads and
     * previews one file at a time.
     */
    private void startDownload() {
        // finishDownload();

        String url = urlField.getText();

        boolean isSequence = false;
        //try {
        // String path = new URL(url).getPath();
        String path = url;//We allow him to get the file from current machine not as link
        if (path.endsWith(".cgi")) // ignore .cgi suffix
        {
            path = path.substring(0, path.length() - ".cgi".length());
        }
        if (path.endsWith(MANIFEST_SUFFIX)) // ignore metafile suffix
        {
            path = path.substring(0, path.length() - MANIFEST_SUFFIX.length());
        }

        if (path.endsWith(SEQ_SUFFIX)) { // note, then remove sequence type
            path = path.substring(0, path.length() - SEQ_SUFFIX.length());
            isSequence = true;
        }
        System.out.println(path);
        // file type is everything after last '.':
        fileType = path.substring(path.lastIndexOf('.') + 1);
        fileType = "png";
        System.out.println(fileType);

        try {

            //if (!isSequence) {
                Tracker tracker = new Tracker(path);
                this.ListDownloadedSegments = new LinkedList<FilePortion>();
                
                this.trackFile = tracker;
                downloadFile(tracker.getFile());
          /*  } else {
                progressLabel.setText("Downloading sequence of files...");
                stepButton.setEnabled(true);
                animateSlider.setValue(0);
                animateSlider.setEnabled(true);
                sliderLabel.setEnabled(true);
                timer = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //    downloadNextFileFromSequence();
                    }
                });
            }*/
        } catch (IOException ex) {
            progressLabel.setText("Download failed.");
            System.out.println(ex.toString());
        }

        // timer will be started and delay set by animate()
        //}
    }

    public boolean isDownload(FilePortion p) {
        boolean flag = false;
        for (int i = 0; i < this.ListDownloadedSegments.size(); i++) {
            FilePortion portion = this.ListDownloadedSegments.get(i);
            if (portion.equals(p)) {
                flag = true;
            }
        }
        return flag;
    }

    public static void setProgressLabel(String st) {
        System.out.println(st);
        progressLabel.setText(st);
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
                if (!isDownload(portion)) {
                    if (obj instanceof Segment) {
                        downloadSegmen((Segment) obj);
                    } else if (obj instanceof Manifest) {
                        File n = new File(((Manifest) obj).getURLName());
                        downloadFile(n);
                    }
                    portion.status = 1;
                    System.out.println(portion);

                    this.ListDownloadedSegments.add(portion);
                } else {
                    r = list.size();//Break the loop when download the portion is'n neccessary continue to others alternative              }
                }
            }

        }
        downloadSingleFile();
    }

    private void downloadSegmen(Segment seg)
            throws IOException {
        String fileURL = seg.getNameURL();
        //    String fileURL = "http://localhost/machine1.birzeit.eduDB/DB.txt-segment1.001";

        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
        System.out.println(responseCode);
        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }

            System.out.print("Content-Type = " + contentType + "\t");
            System.out.println("Content-Disposition = " + disposition + "\t");
            System.out.println("Content-Length = " + contentLength + "\t");
            System.out.println("fileName = " + fileName + "\n");

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();

            // opens an output stream to save into file
            int bytesRead = -1;

            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                //  outputStream.write(buffer, 0, bytesRead);
                dest.write(buffer, 0, bytesRead); // accumulate file into dest
            }

            inputStream.close();
            //  AllSegmentDownload.add(s);

        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }

    private void downloadSingleFile() {

        try {
            /*ByteArrayOutputStream dest = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int read = 0;
            while (read != -1) {
                dest.write(buf, 0, read); // accumulate file into dest
                read = multipart.read(buf);
            }*/
            progressLabel.setText("Download succeeded.");
            progressLabel.setForeground(new Color(0, 0, 0, 255));
            preview(dest.toByteArray());

            System.out.println("size: " + dest.toByteArray().length);

        } catch (Exception e) {
            e.printStackTrace();
            progressLabel.setText("Download failed.");
            progressLabel.setForeground(new Color(240, 0, 0, 255));
        } finally {
            //finishDownload();
        }
    }

    private void preview(byte[] data) throws Exception {
        System.out.println("size: " + fileType);
        if (fileType.equalsIgnoreCase("jpg")
                || fileType.equalsIgnoreCase("gif")
                || fileType.equalsIgnoreCase("png")) {
            imageView.setIcon(new ImageIcon(data));
            scrollPane.setViewportView(imageView);
        } else if (fileType.equalsIgnoreCase("txt") || fileType.equalsIgnoreCase("xml")) {
            textView.setText(new String(data));
            scrollPane.setViewportView(textView);
        } else {
            textView.setText("[unknown file type]");
            scrollPane.setViewportView(textView);
        }

    }


    private void downloadNextFileFromSequence() {
        try {
           //   Tracker tracker = new Tracker(path);
             //   this.ListDownloadedSegments = new LinkedList<FilePortion>();
            byte[] data = FileSequenceReader.readOneFile(inputStream);
            if (data != null) {
                preview(data);
            } else { // no more sub-files
                progressLabel.setText("Sequence finished.");
                //finishDownload();
            }
        } catch (IOException e) {
            e.printStackTrace();
            progressLabel.setText("Download failed.");
           // finishDownload();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
