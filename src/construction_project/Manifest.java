/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package construction_project;

import java.util.LinkedList;

/**
 *
 * @author Mohammad.y
 */
public class Manifest {

    private int number;
    private String manifestURL;
    LinkedList<FilePortion> list;

    public Manifest() {
        super();
        this.list = new LinkedList<FilePortion>();
        // TODO Auto-generated constructor stub
    }

    public Manifest(int number, String manifestURL) {
        super();
        this.number = number;
        manifestURL = manifestURL;
        this.list = new LinkedList<FilePortion>();
    }
    public String getURLName() {
	this.manifestURL=this.manifestURL.replaceAll("\r", "");
	return manifestURL;
}
    
}
