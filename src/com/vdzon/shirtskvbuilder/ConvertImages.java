package com.vdzon.shirtskvbuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.stream.Stream;

import com.vdzon.shirtskvbuilder.data.Data;
import com.vdzon.shirtskvbuilder.data.Doek;
import com.vdzon.shirtskvbuilder.data.Doeksoort;
import com.vdzon.shirtskvbuilder.data.Foto;
import com.vdzon.shirtskvbuilder.data.Kleding;
import com.vdzon.shirtskvbuilder.data.KledingStuk;
import com.vdzon.shirtskvbuilder.data.Kledingkleur;
import com.vdzon.shirtskvbuilder.data.Tas;
import com.vdzon.shirtskvbuilder.data.Tassoort;
import com.vdzon.shirtskvbuilder.util.Util;

public class ConvertImages {

    public static void convertImage(String sourceFilename, String destinationFilename,
                                    int width, int height, boolean transparancy) {
        System.out.println("create:" + destinationFilename);
        File file = new File(destinationFilename);
        sourceFilename = sourceFilename.replace('/', '\\');
        destinationFilename = destinationFilename.replace('/', '\\');
        file.getParentFile().mkdirs();
        String extra = "";
        if (transparancy) {
            // extra = "/transpcolor=(0,0,0)";
        }
        // String imageCommand =
        // "\"C:\\Program Files\\IrfanView\\i_view32.exe\"  \""+sourceFilename+"\" /resize=("+width+","+height+") /resample /aspectratio "+extra+" /silent /jpgq=10 /convert=\""+destinationFilename+"\"";
        String imageCommand = "\"C:\\Program Files (x86)\\IrfanView\\i_view32.exe\"  \""
                + sourceFilename
                + "\" /resize=(0,"
                + height
                + ") /resample /aspectratio "
                + extra
                + " /silent /jpgq=10 /convert=\"" + destinationFilename + "\"";
        Runtime runtime = Runtime.getRuntime();
        System.out.println(imageCommand);
        try {
            Process proc = runtime.exec(imageCommand);
            proc.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void convertImageWidth(String sourceFilename, String destinationFilename,
                                         int width, int height, boolean transparancy) {
        System.out.println("create:" + destinationFilename);
        File file = new File(destinationFilename);
        sourceFilename = sourceFilename.replace('/', '\\');
        destinationFilename = destinationFilename.replace('/', '\\');
        file.getParentFile().mkdirs();
        String extra = "";
        if (transparancy) {
            // extra = "/transpcolor=(0,0,0)";
        }
        // String imageCommand =
        // "\"C:\\Program Files\\IrfanView\\i_view32.exe\"  \""+sourceFilename+"\" /resize=("+width+","+height+") /resample /aspectratio "+extra+" /silent /jpgq=10 /convert=\""+destinationFilename+"\"";
        String imageCommand = "\"C:\\Program Files (x86)\\IrfanView\\i_view32.exe\"  \""
                + sourceFilename
                + "\" /resize=("
                + width
                + ",0) /resample /aspectratio "
                + extra
                + " /silent /jpgq=10 /convert=\"" + destinationFilename + "\"";
        Runtime runtime = Runtime.getRuntime();
        System.out.println(imageCommand);
        try {
            Process proc = runtime.exec(imageCommand);
            proc.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void processKledingImage(String basedir, boolean testdata, String targetBase, Kledingkleur kleur) {
        String base = basedir + "\\data";
        if (testdata) {
            base = basedir + "\\data-test";
        }

        String imageName = base + "\\" + kleur.imageName.substring(10);

        System.out.println(imageName);
        if (!Util.fileExists(imageName)) {
            String imageName2 = imageName.replaceAll("r.png", "s.png");
            if (Util.fileExists(imageName2)) {
                new File(imageName2).renameTo(new File(imageName));
            }
        }

        String catalogusImageName = basedir + "\\catalogus\\"
                + kleur.imageName.substring(10);
        String remainFilename = imageName.substring(base.length());
        remainFilename = remainFilename.substring(0,
                remainFilename.length() - 4);
        String file1 = targetBase + remainFilename + "_1.gif";
        String file2 = targetBase + remainFilename + "_2.gif";
        String file3 = targetBase + remainFilename + "_3.gif";
        String file4 = targetBase + remainFilename + "_4.gif";
        String file5 = targetBase + remainFilename + "_5.gif";

        if (!Util.fileExists(file1))
            convertImage(imageName, file1, 60, 60, true);
        if (!Util.fileExists(file2))
            convertImage(imageName, file2, 100, 100, true);
        if (!Util.fileExists(file3))
            convertImage(imageName, file3, 300, 300, true);
        if (!Util.fileExists(file4))
            convertImage(imageName, file4, 5000, 500, true);
        if (!Util.fileExists(file5))
            convertImageWidth(imageName, file5, 170, 170, true);
        if (!Util.fileExists(catalogusImageName))
            convertImage(imageName, catalogusImageName, 800, 800, false);

    }

    public static void processSchilderijImage(String basedir, boolean testdata, String targetBase, Doek doek) {
        String base = basedir + "\\data";
        if (testdata) {
            base = basedir + "\\data-test";
        }
        String imageName = base + "\\" + doek.imageName.substring(10);
        String remainFilename = imageName.substring(base.length());
        remainFilename = remainFilename.substring(0,
                remainFilename.length() - 4);
        String file1 = targetBase + remainFilename + "_1.gif";
        String file2 = targetBase + remainFilename + "_2.gif";
        String file3 = targetBase + remainFilename + "_3.gif";
        String file4 = targetBase + remainFilename + "_4.gif";
        String file5 = targetBase + remainFilename + "_5.gif";
        if (!Util.fileExists(file1))
            convertImage(imageName, file1, 60, 60, false);
        if (!Util.fileExists(file2))
            convertImage(imageName, file2, 100, 100, false);
        if (!Util.fileExists(file3))
            convertImage(imageName, file3, 270, 270, false);
        if (!Util.fileExists(file4))
            convertImage(imageName, file4, 5000, 500, false);
        if (!Util.fileExists(file5))
            convertImageWidth(imageName, file5, 190, 190, false);
    }

    public static void processReproductieImage(String basedir, boolean testdata, String targetBase, Doek doek) {
        String base = basedir + "\\data";
        if (testdata) {
            base = basedir + "\\data-test";
        }
        String imageName = base + "\\"
                + doek.reproImageName.substring(10);
        String catalogusImageName = basedir + "\\catalogus\\"
                + doek.reproImageName.substring(10);
        String remainFilename = imageName.substring(base.length());
        remainFilename = remainFilename.substring(0,
                remainFilename.length() - 4);
        String file1 = targetBase + remainFilename + "_1.gif";
        String file2 = targetBase + remainFilename + "_2.gif";
        String file3 = targetBase + remainFilename + "_3.gif";
        String file4 = targetBase + remainFilename + "_4.gif";
        if (!Util.fileExists(file1))
            convertImage(imageName, file1, 60, 60, false);
        if (!Util.fileExists(file2))
            convertImage(imageName, file2, 100, 100, false);
        if (!Util.fileExists(file3))
            convertImage(imageName, file3, 270, 270, false);
        if (!Util.fileExists(file4))
            convertImage(imageName, file4, 5000, 500, false);
        if (!Util.fileExists(catalogusImageName))
            convertImage(imageName, catalogusImageName, 800, 800, false);
    }

    public static void processFotoImage(String basedir, boolean testdata, String targetBase, Foto foto) {
        String base = basedir + "\\data";
        if (testdata) {
            base = basedir + "\\data-test";
        }
        String imageName = base + "\\" + foto.imageName.substring(10);
        String remainFilename = imageName.substring(base.length());
        remainFilename = remainFilename.substring(0,
                remainFilename.length() - 4);
        String file1 = targetBase + remainFilename + "_1.gif";
        String file2 = targetBase + remainFilename + "_2.gif";
        String file3 = targetBase + remainFilename + "_3.gif";
        String file4 = targetBase + remainFilename + "_4.gif";
        if (!Util.fileExists(file1))
            convertImage(imageName, file1, 60, 60, false);
        if (!Util.fileExists(file2))
            convertImage(imageName, file2, 145, 145, false);
        if (!Util.fileExists(file3))
            convertImage(imageName, file3, 290, 290, false);
        if (!Util.fileExists(file4))
            convertImage(imageName, file4, 500, 500, false);
    }

    public static void processTasImage(String basedir, boolean testdata, String targetBase, Tas tas) {
        String base = basedir + "\\data";
        if (testdata) {
            base = basedir + "\\data-test";
        }
        String imageName = base + "\\" + tas.imageName.substring(10);
        String catalogusImageName = basedir + "\\catalogus\\"
                + tas.imageName.substring(10);
        String remainFilename = imageName.substring(base.length());
        remainFilename = remainFilename.substring(0,
                remainFilename.length() - 4);
        String file1 = targetBase + remainFilename + "_1.gif";
        String file2 = targetBase + remainFilename + "_2.gif";
        String file3 = targetBase + remainFilename + "_3.gif";
        String file4 = targetBase + remainFilename + "_4.gif";
        String file5 = targetBase + remainFilename + "_5.gif";
        if (!Util.fileExists(file1))
            convertImage(imageName, file1, 60, 60, true);
        if (!Util.fileExists(file2))
            convertImage(imageName, file2, 100, 100, true);
        if (!Util.fileExists(file3))
            convertImage(imageName, file3, 300, 300, true);
        if (!Util.fileExists(file4))
            convertImage(imageName, file4, 500, 500, true);
        if (!Util.fileExists(file5))
            convertImageWidth(imageName, file5, 170, 170, true);
        if (!Util.fileExists(catalogusImageName))
            convertImage(imageName, catalogusImageName, 800, 800, false);
    }

    public static void processFotoImages(String basedir, boolean testdata, String targetBase) {
        for (Enumeration e = Data.data.fotos.elements(); e.hasMoreElements(); ) {
            Foto foto = (Foto) e.nextElement();
            processFotoImage(basedir, testdata, targetBase, foto);
        }
    }

    public static void processSchilderijImages(String basedir, boolean testdata, String targetBase) {
        for (Doeksoort doeksoort : Data.data.doeksoorten) {
            for (Doek doek : doeksoort.doeken) {
                processSchilderijImage(basedir, testdata, targetBase, doek);
                processReproductieImage(basedir, testdata, targetBase, doek);
            }
        }
    }

    public static void processTasImages(String basedir, boolean testdata, String targetBase) {
        for (Tassoort tassoort : Data.data.tassoorten) {
            for (Tas tas : tassoort.tassen) {
                processTasImage(basedir, testdata, targetBase, tas);
            }
        }
    }

    public static void processKledingImages(String basedir, boolean testdata, String targetBase) {
        for (Kleding kleding : Data.data.kleding) {
            for (KledingStuk kledingstuk : kleding.kledingstukken) {
                for (Kledingkleur kledingkleur : kledingstuk.kleuren) {
                    // System.out.println("process:"+kledingkleur.imageName);
                    processKledingImage(basedir, testdata, targetBase, kledingkleur);
                }
            }
        }
    }


    public static void processTemplateImages(String baseDir, boolean selected, String targetBase) {
        File srcDir = new File(baseDir+"\\images");
        File targetDir = new File(targetBase);
        targetDir.mkdirs();
        copyFolder(srcDir, targetDir);
    }

    static private void copyFolder(File src, File dest) {
        // checks
        if(src==null || dest==null)
            return;
        if(!src.isDirectory())
            return;
        if(dest.exists()){
            if(!dest.isDirectory()){
                //System.out.println("destination not a folder " + dest);
                return;
            }
        } else {
            dest.mkdir();
        }

        if(src.listFiles()==null || src.listFiles().length==0)
            return;

        for(File file: src.listFiles()){
            File fileDest = new File(dest, file.getName());
            //System.out.println(fileDest.getAbsolutePath());
            if(file.isDirectory()){
                copyFolder(file, fileDest);
            }else{
                if(fileDest.exists())
                    continue;

                try {
                    Files.copy(file.toPath(), fileDest.toPath());
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
    }}
