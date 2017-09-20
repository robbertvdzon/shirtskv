package com.vdzon.shirtskvbuilder.data;

import java.io.File;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import com.vdzon.shirtskvbuilder.container.CodeTable;
import com.vdzon.shirtskvbuilder.util.Util;

public class KledingStuk {
	public Vector<Kledingmaat> maten = new Vector<Kledingmaat>();
	public Vector<Kledingkleur> kleuren = new Vector<Kledingkleur>();
    public String omschrijving = "";
    public int kledingStukCode = 0;
    public int ontwerpCode = 0;
    public String name = "";
    public String ontwerpname = "";
    public String verzendMethode = "";	
    public String sortKey = "";
    public String productCode = "";

    public String logoImageName = "";
    public Kledingkleur firstKledingKleur = null;
    public int lowestPriceInCent = 0;
    public String alleMaten = "";
    public String kledingOmschrijving = "";

    
    public void load(File shirtFolder, String sortKey, String kledingOmschrijving, int kledingStukCode, int ontwerpCode , CodeTable codeKleur, CodeTable codeType, CodeTable codeMaat) throws Exception {
        this.omschrijving = omschrijving;
        this.kledingStukCode = kledingStukCode;
        this.ontwerpCode = ontwerpCode;
        this.name = shirtFolder.getName().substring(2);
        this.ontwerpname = shirtFolder.getName().substring(2);
        this.sortKey = sortKey;

        if (new File(shirtFolder.getCanonicalPath() + "\\envelopzending.txt").exists()){
           verzendMethode = "brief";
        }
        else{
            verzendMethode = "doos";
        }

        // load maten
        String maten = Util.getFileLine(new File(shirtFolder.getCanonicalPath() +
                                                 "\\maten.txt"));
        StringTokenizer st = new StringTokenizer(maten, "\n", false);
        this.alleMaten = "";
        while (st.hasMoreTokens()) {
            String line = st.nextToken();
            StringTokenizer st2 = new StringTokenizer(line, ":", false);
            String maat = st2.nextToken();
            if (maat.startsWith("%")) maat = maat.substring(1);
            String prijsCode = st2.nextToken();
            Kledingmaat kledingmaat = new Kledingmaat();
            kledingmaat.kledingStuk = this;
            kledingmaat.maat = maat;
            kledingmaat.prijsCode = prijsCode;
            try{
            	kledingmaat.priceInCents = ((Integer) Data.data.prijsLijst.get(
                        prijsCode)).intValue();
            }
            catch(Exception ex){
                System.out.println("Err bij prijsCode="+prijsCode);
                System.exit(0);
            }
            try{
            	kledingmaat.verzendkosten = ((Integer) Data.data.verzendkostenPrijsLijst.get(
                        prijsCode)).intValue();
            }
            catch(Exception ex){
                System.out.println("Err bij prijsCode="+prijsCode);
                System.exit(0);
            }
            kledingmaat.code = codeMaat.getOrCreate(maat).code;


            this.maten.addElement(kledingmaat);
            if (alleMaten.length()>0) alleMaten+=",";
            alleMaten+=maat;
            if (this.lowestPriceInCent==0){
                this.lowestPriceInCent=kledingmaat.priceInCents;
            }
            if (this.lowestPriceInCent>kledingmaat.priceInCents){
                this.lowestPriceInCent=kledingmaat.priceInCents;
            }
        }

        // load kleuren
        File[] files = Util.sortFiles(shirtFolder.listFiles(),false);
        this.firstKledingKleur=null;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
            	Kledingkleur kledingkleur = new Kledingkleur();
                kledingkleur.kleur = files[i].getName().substring(1);
                int code = codeKleur.getOrCreate(kledingkleur.kleur).code;
                kledingkleur.productCode = this.productCode+"."+code;
                kledingkleur.omschrijving = codeKleur.getOrCreate(kledingkleur.kleur).omschrijving;
                if (kledingkleur.omschrijving.length()==0) kledingkleur.omschrijving = kledingkleur.kleur;
                kledingkleur.imageName = "../images/kleding/" +
                files[i].getParentFile().getParentFile().getName() + "/" +
                files[i].getParentFile().getName() + "/"+
                files[i].getName() + "/r.png";
                kleuren.addElement(kledingkleur);
                if (files[i].getName().startsWith("1")) this.firstKledingKleur = kledingkleur;
            }

        }
    }
    
}
