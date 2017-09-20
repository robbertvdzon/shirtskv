package com.vdzon.shirtskvbuilder;

import com.vdzon.shirtskvbuilder.container.CodeObject;
import java.io.File;
import java.util.Vector;
import com.vdzon.shirtskvbuilder.data.Data;
import com.vdzon.shirtskvbuilder.util.Util;
import java.util.StringTokenizer;
import com.vdzon.shirtskvbuilder.container.CodeTable;

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
public class RenameImages {

    public static void rename(File shirtFolder) throws Exception {
        File[] files = shirtFolder.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().toUpperCase().endsWith(".PNG")) {
                String colorName = files[i].getName().substring(0,files[i].getName().length()-4);
                System.out.println("mkdir "+files[i].getParentFile().getCanonicalPath()+"\\"+colorName);
                new File(files[i].getParentFile().getCanonicalPath()+"\\"+colorName).mkdirs();
                System.out.println("rename "+files[i].getCanonicalPath()+" to "+files[i].getParentFile().getCanonicalPath()+"\\"+colorName+"\\s.png");
                files[i].renameTo(new File(files[i].getParentFile().getCanonicalPath()+"\\"+colorName+"\\r.png"));
            }
        }
    }

    public static void loadShirts(String baseFolder) throws Exception{
        File root = new File(baseFolder);
        File[] kledingstukken = root.listFiles();
        for (int i = 0; i < kledingstukken.length; i++) {
            if (kledingstukken[i].isDirectory()) {
                File[] kledingtype = kledingstukken[i].listFiles();
                for (int j = 0; j < kledingtype.length; j++) {
                    if (kledingtype[j].isDirectory()) {
                        File[] shirts = kledingtype[j].listFiles();
                        for (int k = 0; k < shirts.length; k++) {
                            if (shirts[k].isDirectory()) {
                                rename(shirts[k]);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        try{
            loadShirts("d:\\shirtskv\\website\\db\\data\\kleding\\baby");
            loadShirts("d:\\shirtskv\\website\\db\\data\\kleding\\dames");
            loadShirts("d:\\shirtskv\\website\\db\\data\\kleding\\heren");
            loadShirts("d:\\shirtskv\\website\\db\\data\\kleding\\kinder");
            loadShirts("d:\\shirtskv\\website\\db\\data\\kleding\\kleuter");


//            loadShirts("d:\\shirtskv\\website\\tmp\\heren");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
//        loadShirts("d:\\shirtskv\\website\\db\\data\\kleding\\dames");

    }
}
