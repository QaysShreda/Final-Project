/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package construction_project;

/**
 *
 * @author Mohammad.y
 */
public class Segment {

    private String segmentURL;//Segment URL
    private static int count;//How many URLs for the same potion
    private int orderNum;//Order of segment

    public Segment() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Segment(int orderNum, String segmentURL) {
        super();
        this.orderNum = orderNum;
        this.segmentURL = segmentURL;
        this.count++;
    }
    
    public String showSegmentData() {
		return "Segment1: [nameURL=" + segmentURL + ", number=" + orderNum + "]\n";
	}
public String getNameURL() {
		return segmentURL;
	}
}
