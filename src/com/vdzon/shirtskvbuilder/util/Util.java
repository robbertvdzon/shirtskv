package com.vdzon.shirtskvbuilder.util;
import java.io.*;
import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Util {
    public static String getFileLine(String filename) {
        return getFileLine(new File(filename));
    }

    public static String getFileLine(File file) {
        String result = "";

        try {
            int chars_read = 0;
            char[] data = new char[0];

            int size = (int) file.length();
            int temp = 0;
            boolean endReached = false;
            FileReader in = new FileReader(file);
            data = new char[size];

            while ((!endReached) && (size >= 0) && (in.ready())) {
                temp = in.read(data, chars_read, size - chars_read);
                if (temp > 0) {
                    chars_read += temp;
                } else {
                    endReached = true;
                }
            }

            in.close();

            if (chars_read > 0) {
                result = new String(data, 0, chars_read);
            } else {
                result = "";
            }
        } catch (IOException e) {
        }

        return result.trim();
    }

    public static boolean setFileLine(String fileName, String data) {
        try {
            File file = new File(fileName);
            file.getParentFile().mkdirs();
            FileWriter out = new FileWriter(file);
            out.write(data);
            out.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static String convertPrice(float price) {
        String p = ""+price;
        p = p.replace(",0",",-");
        p = p.replace(".0",",-");
        p = p.replace(",5",",50");
        p = p.replace(".5",",50");
        return p;
    }

    public static String firstCapitalLetter(String s){
        String result = s.substring(0,1).toUpperCase();
        result += s.substring(1).toLowerCase();
        return result;
    }
    
    public static boolean fileExists(String filename){
    	return new File(filename).exists();
    }
    

    public static File[] sortFiles(File[] files, boolean reverse) {
        Vector filesVector = new Vector();
        if (files != null) {
            for (File file : files) {
                filesVector.addElement(file);
            }
        }
        Vector sorted = sortFiles(filesVector);
        if (reverse) {
            sorted = reverseVector(sorted);
        }
        File[] result = new File[sorted.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = (File) sorted.elementAt(i);
        }
        return result;
    }


    public static Vector sortFiles(Vector vector) {
        Vector sortedVector = new Vector();
        try {
            Hashtable save = new Hashtable();
            Vector toSort = new Vector();
            for (Enumeration e = vector.elements(); e.hasMoreElements(); ) {
                File file = (File) e.nextElement();
                save.put(file.getAbsolutePath(), file);
                toSort.addElement(file.getAbsolutePath());
            }

            TreeSet set = new TreeSet();
            set.addAll(toSort);

            for (Iterator it = set.iterator(); it.hasNext(); ) {
                String filename = (String) it.next();
                sortedVector.addElement(save.get(filename));
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
            sortedVector = vector;
        }
        return sortedVector;
    }

    public static Vector reverseVector(Vector vector) {
        Vector result = new Vector();
        if (vector == null) {
            return null;
        }
        for (int i = vector.size() - 1; i >= 0; i--) {
            result.addElement(vector.elementAt(i));
        }
        return result;
    }
    
}
