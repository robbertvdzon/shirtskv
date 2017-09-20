package com.vdzon.shirtskvbuilder.data;

import java.io.File;
import com.vdzon.shirtskvbuilder.util.Util;
import java.util.Vector;
import java.util.StringTokenizer;

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
public class Tas {
	public Tassoort tassoort = new Tassoort();
    public String name = "";
    public String imageName = "";
    public String prijsCode = "";
    public int priceInCents = 0;
    public int verzendkosten = 0;
    public String productCode = "";
    public String verzendMethode = "";
    public Tassoort tasSoort = new Tassoort();

    public void load(File tasFolder) throws Exception {
        this.name = tasFolder.getName().substring(2);
        this.imageName = "../images/tassen/" +tassoort.tassoort+"/"+ tasFolder.getName()+"/image.png";

        if (new File(tasFolder.getCanonicalPath() + "\\envelopzending.txt").exists()){
           verzendMethode = "brief";
        }
        else{
            verzendMethode = "doos";
        }

        prijsCode = Util.getFileLine(new File(tasFolder.getCanonicalPath() + "\\prijs.txt")).trim();
        if (prijsCode.startsWith("]")) prijsCode = prijsCode.substring(1);
        try{
            priceInCents = ((Integer) Data.data.prijsLijst.get(prijsCode)).
                           intValue();
            verzendkosten = ((Integer) Data.data.verzendkostenPrijsLijst.get(
                    prijsCode)).intValue();
        }
        catch(Exception ex){
            System.out.println("Error bij code "+prijsCode+":"+tasFolder.getCanonicalPath());
            throw ex;
        }
    }
}
