package com.vdzon.shirtskvbuilder.container;

import com.vdzon.shirtskvbuilder.util.Util;
import java.util.StringTokenizer;
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
public class PrijslijstTable {

    public Hashtable items = new Hashtable();
    private String filename = "";

    public PrijslijstTable(String filename) {
        this.filename = filename;
    }

    public void load() {
        String file = Util.getFileLine(filename);
        if (file == null) {
            return;
        }
        StringTokenizer st = new StringTokenizer(file, "\n", false);
        while (st.hasMoreTokens()) {
            String line = st.nextToken().trim();
            PrijslijstObject prijslijstObject = PrijslijstObject.load(line);
            if (prijslijstObject != null) {
                addObject(prijslijstObject);
            }
        }
    }

    public void save() {
        StringBuffer file = new StringBuffer();
        Object[] myArray = items.entrySet().toArray();
        MyComparator myComparator = new MyComparator();
        Arrays.sort(myArray, (Comparator) myComparator);
        for (int i = 0; i < myArray.length; ++i) {
//        	System.out.print(i+"/"+myArray.length+"  ");
            PrijslijstObject prijslijstObject = (PrijslijstObject) ((Map.Entry) myArray[i]).getValue();
            file.append(prijslijstObject.toString() + "\n");
        }

        Util.setFileLine(filename, file.toString());
    }

    public PrijslijstObject getObject(String key) {
        if (key == null) {
            return null;
        }
        PrijslijstObject result = (PrijslijstObject) items.get(key.toLowerCase());
        return result;
    }

    public void addObject(PrijslijstObject object) {
        if (object == null) {
            return;
        }
        if (object.ID == null) {
            return;
        }
        items.put(object.ID.toLowerCase(), object);
    }

    public void update(String ID, String imageSmall, String imageLarge,
                       int price, String omschrijving, int verzendkosten, String detailURL, String imageURL) {
        PrijslijstObject object = getObject(ID);
        if (object == null) {
            object = new PrijslijstObject();
        }
        object.ID = ID;
        object.imageSmall = imageSmall;
        object.imageLarge = imageLarge;
        object.price = price;
        object.omschrijving = omschrijving;
        object.verzendkosten = verzendkosten;
        object.detailURL = detailURL;
        object.imageURL = imageURL;
        addObject(object);
    }


    class MyComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            String code1 = ((PrijslijstObject) ((Map.Entry) o1).getValue()).ID;
            String code2 = ((PrijslijstObject) ((Map.Entry) o2).getValue()).ID;
            return code1.compareTo(code2);
        }
    }
}
