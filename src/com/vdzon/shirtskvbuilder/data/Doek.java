package com.vdzon.shirtskvbuilder.data;

import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;

import com.vdzon.shirtskvbuilder.container.CodeTable;
import com.vdzon.shirtskvbuilder.util.Util;

public class Doek {
	public Vector<Repro> maten = new Vector<Repro>();
	public Doeksoort doeksoort = new Doeksoort();
    public String fullName = "";
    public String name = "";
    public String imageName = "";
    public String maat = "";
    public String reproImageName = "";
    public String prijsCode = "";
    public String productCode = "";
    public String reproBaseProductCode = "";
    public boolean verkocht = false;
    public boolean nietTeKoop = false;
    public int priceInCents = 0;
    public int verzendkosten = 0;
    public int lowestReproductiePriceInCent = 0;
    
    public void load(File schilderijFolder,CodeTable reproMaat) throws Exception {
        fullName = schilderijFolder.getName().substring(2);
        StringTokenizer stNaam = new StringTokenizer(fullName, ".", false);
        this.name = stNaam.nextToken().trim();
        this.maat = stNaam.nextToken().trim();
        this.imageName = "../images/schilderijen/" +doeksoort.doeksoort+"/"+schilderijFolder.getName()+"/image.png";
        this.reproImageName = "../images/schilderijen/" +doeksoort.doeksoort+"/"+schilderijFolder.getName()+"/repro.png";

        if (new File(schilderijFolder.getCanonicalPath() + "\\verkocht.txt").exists()){
            verkocht = true;
        }
        prijsCode = Util.getFileLine(new File(schilderijFolder.getCanonicalPath() + "\\prijs.txt")).trim();
        if (prijsCode.startsWith("~")) prijsCode = prijsCode.substring(1);
        if (new File(schilderijFolder.getCanonicalPath() + "\\prijs.txt").exists()){
            priceInCents = ((Integer)Data.data.prijsLijst.get(prijsCode)).intValue();
            Integer temp = (Integer)Data.data.verzendkostenPrijsLijst.get(prijsCode);
            if (temp==null){
            	throw new Exception("Geen prijscode '"+prijsCode+"' gevonden in de Data.data.verzendkostenPrijsLijst");
            }
            verzendkosten = temp.intValue();
            
        }
        else {
            nietTeKoop = true;
            verzendkosten = 0;
        }


        // load maten
        String maten = Util.getFileLine(new File(schilderijFolder.getCanonicalPath() +
                                                 "\\maten.txt"));
        StringTokenizer st = new StringTokenizer(maten, "\n", false);
        while (st.hasMoreTokens()) {
            String line = st.nextToken().trim();
            StringTokenizer st2 = new StringTokenizer(line, ":", false);
            if (!st2.hasMoreElements()){
                throw new RuntimeException("Ongeldig formaat");
            }
            String maat = st2.nextToken().trim();
            if (maat.startsWith("!")) maat = maat.substring(1);
            String prijsCode = st2.nextToken().trim().trim();

            Repro doekmaat = new Repro();
            doekmaat.maat = maat;
            doekmaat.prijsCode = prijsCode;
            if (Data.data.prijsLijst.get(prijsCode)==null){
                System.out.println("Prijs voor "+prijsCode+" kon niet gevonden worden");
            }
            doekmaat.priceInCents = ((Integer)Data.data.prijsLijst.get(prijsCode)).intValue();
            doekmaat.verzendkosten = ((Integer)Data.data.verzendkostenPrijsLijst.get(prijsCode)).intValue();
            doekmaat.productieCode=this.reproBaseProductCode+"."+doekmaat.maat.toLowerCase();

            this.maten.addElement(doekmaat);
            if (this.lowestReproductiePriceInCent==0){
                this.lowestReproductiePriceInCent=doekmaat.priceInCents;
            }
            if (this.lowestReproductiePriceInCent>doekmaat.priceInCents){
                this.lowestReproductiePriceInCent=doekmaat.priceInCents;
            }
        }
        if (this.lowestReproductiePriceInCent==0){
            this.lowestReproductiePriceInCent=priceInCents;
        }

    }
	
	
}
