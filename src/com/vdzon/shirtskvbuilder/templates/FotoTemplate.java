package com.vdzon.shirtskvbuilder.templates;

import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import com.vdzon.shirtskvbuilder.data.Foto;
import com.vdzon.shirtskvbuilder.util.Util;

public class FotoTemplate {

	public static void processFotos(String basedir, String title, String headerImage, Vector fotos) {
		String subdir = "fotos";
		String globalHeaderTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_header.html"));
		String globalFooterTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_footer.html"));
		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%HEADER_IMAGE%", headerImage);
		String fotoTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\fotos.html"));
		String fotoRowTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\fotorow.html"));
		String fotoImageTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\fotoimage.html"));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%EXTRA_METADATA%", "");
		globalHeaderTemplate = globalHeaderTemplate.replaceAll("%EXTRA_BODY%",
				"");

		fotoTemplate = fotoTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		fotoTemplate = fotoTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);

		String rows = "";
		for (Enumeration e = fotos.elements(); e.hasMoreElements();) {
			Foto foto1 = (Foto) e.nextElement();

			/*
			 * PHOTO 1
			 */
			String fotoImagepageName1 = foto1.name + ".html";

			String extension1 = ".gif";
			if (foto1.imageName.endsWith(".gif")) {
				extension1 = ".gif";
			}
			if (foto1.imageName.endsWith(".jpg")) {
				extension1 = ".jpg";
			}
			if (foto1.imageName.endsWith(".png")) {
				extension1 = ".png";
			}
			String fotoImage1 = fotoImageTemplate;
			fotoImage1 = fotoImage1.replaceAll("%IMAGE1%", foto1.imageName
					.replace(extension1, "_1.gif"));
			fotoImage1 = fotoImage1.replaceAll("%IMAGE2%", foto1.imageName
					.replace(extension1, "_2.gif"));
			fotoImage1 = fotoImage1.replaceAll("%IMAGE3%", foto1.imageName
					.replace(extension1, "_3.gif"));
			fotoImage1 = fotoImage1.replaceAll("%IMAGE4%", foto1.imageName
					.replace(extension1, "_4.gif"));
			fotoImage1 = fotoImage1.replaceAll("%NAME%", foto1.name);
			fotoImage1 = fotoImage1.replaceAll("%PAGE_TITLE%", "ShirtsKV.nl - "
					+ foto1.name);
			Util.setFileLine(basedir + "\\html\\" + subdir + "\\"
					+ fotoImagepageName1, fotoImage1);
			// add a row for these 2 images
			String row = fotoRowTemplate;
			row = row.replaceAll("%PHOTO1_DETAILPAGE%", fotoImagepageName1);
			row = row.replaceAll("%PHOTO1_IMAGE1%", foto1.imageName.replace(
					extension1, "_1.gif"));
			row = row.replaceAll("%PHOTO1_IMAGE2%", foto1.imageName.replace(
					extension1, "_2.gif"));
			row = row.replaceAll("%PHOTO1_IMAGE3%", foto1.imageName.replace(
					extension1, "_3.gif"));
			row = row.replaceAll("%PHOTO1_IMAGE4%", foto1.imageName.replace(
					extension1, "_4.gif"));
			row = row.replaceAll("%PHOTO1_NAME%", foto1.name);

			rows += row;

		}

		String fotosPage = fotoTemplate;
		fotosPage = fotosPage.replaceAll("%TITLE%", title);
		fotosPage = fotosPage.replaceAll("%ROWS%", rows);
		fotosPage = fotosPage.replaceAll("%PAGE_TITLE%", "ShirtsKV.nl - "
				+ title);
		Util.setFileLine(basedir + "\\html\\" + subdir + "\\"
				+ subdir + ".html", fotosPage);

	}

	
}
