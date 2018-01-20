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

public class Multipart {
      private String seqURL;//Segment URL
    private static int count;//How many URLs for the same potion
    private int orderNum;//Order of segment

        public Multipart() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Multipart(int orderNum, String seqURL) {
        super();
        this.orderNum = orderNum;
        this.seqURL = seqURL;
        this.count++;
    }
}
