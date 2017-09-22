package com.vdzon.shirtskvbuilder.container;

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
public class PrijslijstObject {
    public String ID = "";
    public String omschrijving = "";
    public String imageSmall = "";
    public String imageLarge = "";
    public int price = 0;
    public int verzendkosten = 0;
    public String detailURL = "";
    public String imageURL = "";

    public static PrijslijstObject load(String line){
        StringTokenizer st = new StringTokenizer(line, ":", false);
        if (st.countTokens()==9){
            try{
                PrijslijstObject codeObject = new PrijslijstObject();
                codeObject.ID = st.nextToken().trim();
                codeObject.price = Integer.parseInt(st.nextToken());
                codeObject.imageSmall = st.nextToken().trim();
                codeObject.imageLarge = st.nextToken().trim();
                codeObject.omschrijving = st.nextToken().trim();
                codeObject.verzendkosten = Integer.parseInt(st.nextToken());
                codeObject.imageURL = st.nextToken().trim();
                codeObject.detailURL = st.nextToken().trim();

                return codeObject;
            }
            catch(Exception ex){
                ex.printStackTrace();
                return null;
            }
        }
        else{
            return null;
        }
    }

    public String toString(){
        return ID+":"+price+":"+imageSmall+":"+imageLarge+":"+omschrijving+":"+verzendkosten+":"+imageURL+":"+detailURL;
    }
}
