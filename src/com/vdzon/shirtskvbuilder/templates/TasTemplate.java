package com.vdzon.shirtskvbuilder.templates;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.vdzon.shirtskvbuilder.data.Data;
import com.vdzon.shirtskvbuilder.data.Tas;
import com.vdzon.shirtskvbuilder.data.Tassoort;
import com.vdzon.shirtskvbuilder.util.Util;

public class TasTemplate {

	public static void processTassoorten(String basedir, String title, String headerImage) {
		for (Tassoort tassoort : Data.data.tassoorten) {
			System.out.println("process doek:" + tassoort.tassoort);
			processTassen(basedir, tassoort.tassoort,tassoort.tassoort,
					"../images/subbuttons/kop-" + tassoort.tassoort + ".png",
					tassoort.tassen);

		}

	}

	public static void processTassen(String basedir, String subdir, String title, String headerImage, Vector<Tas> doeken) {
		String page1Template = Util.getFileLine(basedir
				+ "\\catalogus\\templates\\tas_template.txt");
		String globalHeaderTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_header.html"));
		String globalFooterTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_footer.html"));
		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%HEADER_IMAGE%", headerImage);
		String tasTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\tassen.html"));
		String tasRowTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\tasrow.html"));
		String tasDetailTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\tasdetail.html"));
		String tasImageTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\tasimage.html"));
		String tasVoorwaardenTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\tasvoorwaarden.html"));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%EXTRA_METADATA%", "");
		globalHeaderTemplate = globalHeaderTemplate.replaceAll("%EXTRA_BODY%",
				"");

		tasTemplate = tasTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		tasDetailTemplate = tasDetailTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		tasTemplate = tasTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);
		tasDetailTemplate = tasDetailTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);

		// Previous/Next definition
		String first = null;
		String last = null;
		String previous = null;
		Tas firstTas = null;
		Tas lastTas = null;
		Tas previousTas = null;

		Hashtable nextTable = new Hashtable();
		Hashtable previousTable = new Hashtable();
		for (Enumeration e = doeken.elements(); e.hasMoreElements();) {
			Tas tas = (Tas) e.nextElement();
			String detailPageName = tas.name + "_detail.html";
			if (previous != null) {
				nextTable.put(previousTas, detailPageName);
				previousTable.put(tas, previous);
			} else {
				first = detailPageName;
				firstTas = tas;
			}
			if (!e.hasMoreElements()) {
				last = detailPageName;
				lastTas = tas;
			}
			previous = detailPageName;
			previousTas = tas;
		}

		if (first != null)
			nextTable.put(lastTas, first);
		if (last != null)
			previousTable.put(firstTas, last);

		String rows = "";
		int pageNr = 0;
		for (Enumeration e = doeken.elements(); e.hasMoreElements();) {
			Tas tas = (Tas) e.nextElement();

			String links = "";

			// create the image page
			String tasImagepageName = tas.name + "_image.html";

			String tasImage = tasImageTemplate;

			tasImage = tasImage.replaceAll("%PAGE_TITLE%", "ShirtsKV.nl - "
					+ tas.name);
			tasImage = tasImage.replaceAll("%IMAGE1%", tas.imageName.replace(
					".png", "_1.gif"));
			tasImage = tasImage.replaceAll("%IMAGE2%", tas.imageName.replace(
					".png", "_2.gif"));
			tasImage = tasImage.replaceAll("%IMAGE3%", tas.imageName.replace(
					".png", "_3.gif"));
			tasImage = tasImage.replaceAll("%IMAGE4%", tas.imageName.replace(
					".png", "_4.gif"));
			tasImage = tasImage.replaceAll("%NAME%", tas.name);
			Util.setFileLine(basedir + "\\html\\" + subdir + "\\"
					+ tasImagepageName, tasImage);

			// create the page
			String detailPageName = tas.name + "_detail.html";

			String detailPage = tasDetailTemplate;

			detailPage = detailPage.replaceAll("%IMAGE1%", tas.imageName
					.replace(".png", "_1.gif"));
			detailPage = detailPage.replaceAll("%IMAGE2%", tas.imageName
					.replace(".png", "_2.gif"));
			detailPage = detailPage.replaceAll("%IMAGE3%", tas.imageName
					.replace(".png", "_3.gif"));
			detailPage = detailPage.replaceAll("%IMAGE4%", tas.imageName
					.replace(".png", "_4.gif"));

			detailPage = detailPage.replaceAll("%NAME%", tas.name);
			detailPage = detailPage
					.replaceAll("%IMAGE_PAGE%", tasImagepageName);
			detailPage = detailPage.replaceAll("%PAGE_LINK%", "../" + subdir
					+ "/" + detailPageName);
			detailPage = detailPage.replaceAll("%PAGE_TITLE%", "ShirtsKV.nl - "
					+ tas.name);
			detailPage = detailPage.replaceAll("%INFO_TAS%",
					tasVoorwaardenTemplate);

			try {
				detailPage = detailPage.replaceAll("%NEXT_DETAIL_PAGE%",
						(String) nextTable.get(tas));
				detailPage = detailPage.replaceAll("%PREVIOUS_DETAIL_PAGE%",
						(String) previousTable.get(tas));
			} catch (Exception ex) {
				System.out.println("NULL bij " + detailPageName);
			}

			detailPage = detailPage.replaceAll("%PRODUCT_CODE%", tas.productCode);

			float price = ((float) tas.priceInCents) / 100;
			detailPage = detailPage.replaceAll("%PRICE%", "&euro; "
					+ Util.convertPrice(price));

			Util.setFileLine(basedir + "\\html\\" + subdir + "\\"
					+ detailPageName, detailPage);

			// add a row for this image
			String row = tasRowTemplate;
			row = row.replaceAll("%DETAILPAGE%", detailPageName);
			row = row.replaceAll("%IMAGE1%", tas.imageName.replace(".png",
					"_1.gif"));
			row = row.replaceAll("%IMAGE2%", tas.imageName.replace(".png",
					"_2.gif"));
			row = row.replaceAll("%IMAGE3%", tas.imageName.replace(".png",
					"_3.gif"));
			row = row.replaceAll("%IMAGE4%", tas.imageName.replace(".png",
					"_4.gif"));
			row = row.replaceAll("%IMAGE5%", tas.imageName.replace(".png",
					"_5.gif"));
			row = row.replaceAll("%PRICE%", "&euro; "
					+ Util.convertPrice(price));

			row = row.replaceAll("%NAME%", tas.name);
			rows += row;

		}

		String tassenPage = tasTemplate;
		tassenPage = tassenPage.replaceAll("%TITLE%", title);
		tassenPage = tassenPage.replaceAll("%ROWS%", rows);
		tassenPage = tassenPage.replaceAll("%PAGE_TITLE%", "ShirtsKV.nl - "
				+ title);
		Util.setFileLine(basedir + "\\html\\" + subdir + "\\index.html", tassenPage);

	}

	
	public static void processTasIndexTemplate(String basedir,String title, String headerImage) {
		String indexExtraBodyTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\index_extra_body.html"));
		String indexExtraHeaderTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\index_extra_header.html"));
		String globalHeaderTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_header.html"));
		String globalFooterTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_footer.html"));
		String allTassenHeaderTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\alltassen_header.html"));

		String tasTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\indexpages\\tassen.html"));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%HEADER_IMAGE%", headerImage);

		tasTemplate = tasTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		tasTemplate = tasTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);
		tasTemplate = tasTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		tasTemplate = tasTemplate.replaceAll("%EXTRA_METADATA%",
				indexExtraHeaderTemplate);
		tasTemplate = tasTemplate.replaceAll("%ALLTASSEN_HEADER%",
				allTassenHeaderTemplate);

		tasTemplate = tasTemplate.replaceAll("%EXTRA_METADATA%", "");
		tasTemplate = tasTemplate.replaceAll("%EXTRA_BODY%", "");
		tasTemplate = tasTemplate.replaceAll("%TITLE%", title);
		tasTemplate = tasTemplate.replaceAll("%PAGE_TITLE%", "ShirtsKV.nl - "
				+ title);

		Util.setFileLine(basedir + "\\html\\" + "\\tassen.html",
				tasTemplate);

	}

	
}
