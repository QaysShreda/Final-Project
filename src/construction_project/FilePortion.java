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
public class FilePortion {
private int portionNumber;
private String string;
private LinkedList<Object> list;

public enum States {
    QUEUED, DOWNLOADING, RETRYING, ERROR, STOP, DONE;
}
int status;
public FilePortion() {
	super();
	this.list=new LinkedList<Object>();
	
}

public FilePortion(int portionNumber) {
	super();
	this.portionNumber = portionNumber;
	this.list=new LinkedList<Object>();
}


public FilePortion(int portionNumber, String string) {
	super();
	this.portionNumber = portionNumber;
	this.string = string;
	this.list=new LinkedList<Object>();
}


public String getString() {
	return string;
}

public void setString(String string) {
	this.string = string;
}

public LinkedList<Object> getList() {
	return list;
}

public void setList(LinkedList<Object> list) {
	this.list = list;
}
public void AddInList(Object o){
	list.add(o);
}
public int getPortionNumber() {
	return portionNumber;
}

public void setPortionNumber(int portionNumber) {
	this.portionNumber = portionNumber;
}

}
