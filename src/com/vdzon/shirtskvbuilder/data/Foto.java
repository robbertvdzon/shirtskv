package com.vdzon.shirtskvbuilder.data;

import java.io.File;
import com.vdzon.shirtskvbuilder.util.Util;

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
public class Foto {
    public String name = "";
    public String imageName = "";

    public void load(File schilderijFile) throws Exception {
        this.name = schilderijFile.getName();
        this.imageName = "../images/fotos/" +name;
    }
}
