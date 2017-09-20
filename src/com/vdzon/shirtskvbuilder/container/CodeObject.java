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
public class CodeObject {
    public int code = 0;
    public String key = "";
    public String omschrijving = "";

    public static CodeObject load(String line){
        StringTokenizer st = new StringTokenizer(line, ":", false);
        if (st.countTokens()>=2){
            try{
                CodeObject codeObject = new CodeObject();
                codeObject.code = Integer.parseInt(st.nextToken());
                codeObject.key = st.nextToken();
                if (st.hasMoreTokens()) codeObject.omschrijving = st.nextToken();

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
        return code+":"+key+":"+omschrijving;
    }
}
