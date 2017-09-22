package com.vdzon.shirtskvbuilder.container;
import java.util.*;
import com.vdzon.shirtskvbuilder.util.*;
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
public class CodeTable {

    private Hashtable items = new Hashtable();
    private String filename = "";

    public CodeTable(String filename) {
        this.filename = filename;
        load();
    }

    public Enumeration getEnumeration(){
        return items.elements();
    }

    public Enumeration getKeys(){
        return items.keys();
    }
    
    public void load(){
        String file = Util.getFileLine(filename);
        if (file==null) return;
        StringTokenizer st = new StringTokenizer(file, "\n", false);
        while (st.hasMoreTokens()) {
            String line = st.nextToken().trim();
            CodeObject codeObject = CodeObject.load(line);
            if (codeObject!=null){
                addObject(codeObject);
            }
        }
    }

    public void save(){
        String file = "";
        Object[] myArray = items.entrySet().toArray();
        MyComparator myComparator = new MyComparator();
        Arrays.sort(myArray, (Comparator) myComparator);
        for (int i = 0; i < myArray.length; ++i) {
            CodeObject codeObject = (CodeObject) ((Map.Entry) myArray[i]).getValue();
            file += codeObject.toString() + "\n";
        }

        Util.setFileLine(filename,file);
    }

    public CodeObject getObject(String key){
        if (key==null) return null;
        CodeObject result = (CodeObject)items.get(key.toLowerCase());
        return result;
    }

    private void addObject(CodeObject object){
        if (object==null) return;
        if (object.key==null) return;
        items.put(object.key.toLowerCase(),object);
    }

    private void addNewItem(String key){
        CodeObject object = new CodeObject();
        object.code = getNextFreeCode();
        object.key = key.toLowerCase();
        addObject(object);
    }

    public CodeObject getOrCreate(String key){
        CodeObject object = getObject(key.toLowerCase());
        if (object==null){
            object = new CodeObject();
            object.code = getNextFreeCode();
            object.key = key.toLowerCase();
            addObject(object);
        }
        return object;
    }


    public int getNextFreeCode(){
        int freeCode = 0;
        for (Enumeration e = items.elements(); e.hasMoreElements();){
            CodeObject codeObject = (CodeObject)e.nextElement();
            if (codeObject.code>=freeCode){
                freeCode = codeObject.code+1;
            }
        }
        return freeCode;
    }

    class MyComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            int code1 = ((CodeObject) ((Map.Entry) o1).getValue()).code;
            int code2 = ((CodeObject) ((Map.Entry) o2).getValue()).code;
            if (code1==code2) return 0;
            if (code1>code2) return 1;
            return -1;
        }
    }

}
