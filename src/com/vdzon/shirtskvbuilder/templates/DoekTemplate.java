package com.vdzon.shirtskvbuilder.templates;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.vdzon.shirtskvbuilder.Const;
import com.vdzon.shirtskvbuilder.data.Data;
import com.vdzon.shirtskvbuilder.data.Doek;
import com.vdzon.shirtskvbuilder.data.Repro;
import com.vdzon.shirtskvbuilder.data.Doeksoort;
import com.vdzon.shirtskvbuilder.util.Util;

public class DoekTemplate {
	
	public static void processDoeksoorten(String basedir, String title, String headerImage) {
		for (Doeksoort doeksoort : Data.data.doeksoorten) {
			processDoeksoort(basedir, doeksoort.doeksoort, doeksoort.doeksoort,
					"../images/subbuttons/kop-" + doeksoort.doeksoort + ".png",
					doeksoort.doeken);

		}

	}

	public static void processDoeksoort(String basedir, String subdir, String title,
			String headerImage, Vector<Doek> doeken) {
		String catalogusTemplate = Util.getFileLine(basedir
				+ "\\catalogus\\templates\\schilderij_template.txt");
		
		String globalHeaderTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_header.html"));
		String globalFooterTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_footer.html"));
		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%HEADER_IMAGE%", headerImage);
		String schilderijTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\schilderijen.html"));
		String schilderijRowTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\schilderijrow.html"));
		String schilderijDetailTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\schilderijdetail.html"));
		if (subdir.equals("stof")){
			schilderijDetailTemplate = Util.getFileLine(new File(
					basedir
					+ "\\templates\\stofschilderijdetail.html"));
		}		
		if (subdir.equals("hout")){
			schilderijDetailTemplate = Util.getFileLine(new File(
					basedir
					+ "\\templates\\houtschilderijdetail.html"));
		}		
		String schilderijImageTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\schilderijimage.html"));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%EXTRA_METADATA%", "");
		globalHeaderTemplate = globalHeaderTemplate.replaceAll("%EXTRA_BODY%",
				"");

		schilderijTemplate = schilderijTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		schilderijDetailTemplate = schilderijDetailTemplate.replaceAll(
				"%GLOBAL_HEADER%", globalHeaderTemplate);

		String allDoekenHeaderTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\alldoeken_header.html"));
		schilderijDetailTemplate = schilderijDetailTemplate.replaceAll("%ALLDOEKEN_HEADER%",
				allDoekenHeaderTemplate);
		schilderijTemplate = schilderijTemplate.replaceAll("%ALLDOEKEN_HEADER%",
				allDoekenHeaderTemplate);
		
		
		schilderijTemplate = schilderijTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);
		schilderijDetailTemplate = schilderijDetailTemplate.replaceAll(
				"%GLOBAL_FOOTER%", globalFooterTemplate);

		// Previous/Next definition

		String first = null;
		String last = null;
		String previous = null;
		Doek firstDoek = null;
		Doek lastDoek = null;
		Doek previousDoek = null;

		Hashtable nextTable = new Hashtable();
		Hashtable previousTable = new Hashtable();
		for (Enumeration e = doeken.elements(); e.hasMoreElements();) {
			Doek doek = (Doek) e.nextElement();
			String detailPageName = doek.name + "_orig_detail.html";
			if (previous != null) {
				nextTable.put(previousDoek, detailPageName);
				previousTable.put(doek, previous);
			} else {
				first = detailPageName;
				firstDoek = doek;
			}
			if (!e.hasMoreElements()) {
				last = detailPageName;
				lastDoek = doek;
			}
			previous = detailPageName;
			previousDoek = doek;
		}

		if (first != null)
			nextTable.put(lastDoek, first);
		if (last != null)
			previousTable.put(firstDoek, last);

		String rows = "";
		int pageNr = 0;
		for (Enumeration e = doeken.elements(); e.hasMoreElements();) {
			Doek doek = (Doek) e.nextElement();

			String links = "";
			String layers = "";
			String maatOptions = "";
			for (Repro doekmaat : doek.maten) {
				float price = ((float) doekmaat.priceInCents) / 100;
				maatOptions += "<option value='" + doekmaat.maat + "'>"
						+ doekmaat.maat + "&nbsp;&nbsp;&nbsp;(&euro; "
						+ Util.convertPrice(price) + ") </option>\n";
			}

			// create the image page
			String schilderijImagepageName = doek.name + "_orig_image.html";
            String reproductieImagepageName = doek.name + "_repro_image.html";

			String schilderijImage = schilderijImageTemplate;
            String reproductieImage = schilderijImageTemplate;

			schilderijImage = schilderijImage.replaceAll("%IMAGE1%",
					doek.imageName.replace(".png", "_1.gif"));
			schilderijImage = schilderijImage.replaceAll("%IMAGE2%",
					doek.imageName.replace(".png", "_2.gif"));
			schilderijImage = schilderijImage.replaceAll("%IMAGE3%",
					doek.imageName.replace(".png", "_3.gif"));
			schilderijImage = schilderijImage.replaceAll("%IMAGE4%",
					doek.imageName.replace(".png", "_4.gif"));
			schilderijImage = schilderijImage.replaceAll("%IMAGE5%",
					doek.imageName.replace(".png", "_5.gif"));

            reproductieImage = reproductieImage.replaceAll("%IMAGE1%",
            		doek.reproImageName.replace(".png", "_1.gif"));
            reproductieImage = reproductieImage.replaceAll("%IMAGE2%",
            		doek.reproImageName.replace(".png", "_2.gif"));
            reproductieImage = reproductieImage.replaceAll("%IMAGE3%",
            		doek.reproImageName.replace(".png", "_3.gif"));
            reproductieImage = reproductieImage.replaceAll("%IMAGE4%",
            		doek.reproImageName.replace(".png", "_4.gif"));
            reproductieImage = reproductieImage.replaceAll("%PAGE_TITLE%",
                    "ShirtsKV.nl - Schilderij reproductie '" + doek.name +
                    "'");
            reproductieImage = reproductieImage.replaceAll("%NAME%",
            		doek.name);

            
			schilderijImage = schilderijImage.replaceAll("%PAGE_TITLE%",
					"ShirtsKV.nl - Schilderij '" + doek.name + "'");
			schilderijImage = schilderijImage.replaceAll("%NAME", doek.name);
			Util.setFileLine(basedir + "\\html\\" + subdir + "\\"
					+ schilderijImagepageName, schilderijImage);
            Util.setFileLine(basedir + "\\html\\" + subdir + "\\" +
                    reproductieImagepageName, reproductieImage);

			// create the page
			String origDetailPageName = doek.name + "_orig_detail.html";

			String origDetailPage = schilderijDetailTemplate;
			float lowestReproPrice = ((float) doek.lowestReproductiePriceInCent) / 100;

			origDetailPage = origDetailPage.replaceAll("%ORIGIMAGE1%",
					doek.imageName.replace(".png", "_1.gif"));
			origDetailPage = origDetailPage.replaceAll("%ORIGIMAGE2%",
					doek.imageName.replace(".png", "_2.gif"));
			origDetailPage = origDetailPage.replaceAll("%ORIGIMAGE3%",
					doek.imageName.replace(".png", "_3.gif"));
			origDetailPage = origDetailPage.replaceAll("%ORIGIMAGE4%",
					doek.imageName.replace(".png", "_4.gif"));

			origDetailPage = origDetailPage.replaceAll("%THISIMAGE1%",
					doek.imageName.replace(".png", "_1.gif"));
			origDetailPage = origDetailPage.replaceAll("%THISIMAGE2%",
					doek.imageName.replace(".png", "_2.gif"));
			origDetailPage = origDetailPage.replaceAll("%THISIMAGE3%",
					doek.imageName.replace(".png", "_3.gif"));
			origDetailPage = origDetailPage.replaceAll("%THISIMAGE4%",
					doek.imageName.replace(".png", "_4.gif"));

			origDetailPage = origDetailPage.replaceAll("%REPROIMAGE1%",
					doek.reproImageName.replace(".png", "_1.gif"));
			origDetailPage = origDetailPage.replaceAll("%REPROIMAGE2%",
					doek.reproImageName.replace(".png", "_2.gif"));
			origDetailPage = origDetailPage.replaceAll("%REPROIMAGE3%",
					doek.reproImageName.replace(".png", "_3.gif"));
			origDetailPage = origDetailPage.replaceAll("%REPROIMAGE4%",
					doek.reproImageName.replace(".png", "_4.gif"));

			origDetailPage = origDetailPage.replaceAll("%NAME%", doek.name);
			origDetailPage = origDetailPage.replaceAll("%IMAGE_PAGE%",
					schilderijImagepageName);
			origDetailPage = origDetailPage.replaceAll("%REPRO_IMAGE_PAGE%",
					reproductieImagepageName);
			origDetailPage = origDetailPage.replaceAll("%MAAT_OPTIONS%",
					maatOptions);
			origDetailPage = origDetailPage
					.replaceAll("%ORIG_SIZE%", doek.maat);
			origDetailPage = origDetailPage.replaceAll("%PAGE_TITLE%",
					"ShirtsKV.nl - Schilderij '" + doek.name + "'");
			origDetailPage = origDetailPage.replaceAll("%MIN_PRICE%", "&euro; "
					+ Util.convertPrice(lowestReproPrice));
			origDetailPage = origDetailPage.replaceAll("%REPRO_CHECKED%", "");
			origDetailPage = origDetailPage.replaceAll("%ORIG_CHECKED%",
					"checked");

			origDetailPage = origDetailPage.replaceAll("%ORIG_PRODUCT_CODE%",
					doek.productCode);
			origDetailPage = origDetailPage.replaceAll("%REPRO_PRODUCT_CODE%",
					doek.reproBaseProductCode);

			try {
				origDetailPage = origDetailPage.replaceAll(
						"%NEXT_DETAIL_PAGE%", (String) nextTable.get(doek));
				origDetailPage = origDetailPage.replaceAll(
						"%PREVIOUS_DETAIL_PAGE%", (String) previousTable
								.get(doek));
			} catch (Exception ex) {
				System.out.println("NULL bij " + origDetailPage);
			}


            String nietTeKoop = "false";
			String verkocht = "false";
			if (doek.nietTeKoop) {
				nietTeKoop = "true";
				origDetailPage = origDetailPage.replaceAll("%PRICE%",
						"Niet te koop");
			} else if (doek.verkocht) {
				verkocht = "true";
				origDetailPage = origDetailPage.replaceAll("%PRICE%",
						"Reeds verkocht");
			} else {
				float price = ((float) doek.priceInCents) / 100;
				origDetailPage = origDetailPage.replaceAll("%PRICE%", "&euro; "
						+ Util.convertPrice(price));
			}

			origDetailPage = origDetailPage.replaceAll("%NIET_TE_KOOP%",
					nietTeKoop);
			origDetailPage = origDetailPage.replaceAll("%VERKOCHT%", verkocht);

			Util.setFileLine(basedir + "\\html\\" + subdir + "\\"
					+ origDetailPageName, origDetailPage);

			// add a row for this image
			String row = schilderijRowTemplate;
			row = row.replaceAll("%DETAILPAGE%", origDetailPageName);
			row = row.replaceAll("%IMAGE1%", doek.imageName.replace(".png",
					"_1.gif"));
			row = row.replaceAll("%IMAGE2%", doek.imageName.replace(".png",
					"_2.gif"));
			row = row.replaceAll("%IMAGE3%", doek.imageName.replace(".png",
					"_3.gif"));
			row = row.replaceAll("%IMAGE4%", doek.imageName.replace(".png",
					"_4.gif"));
			row = row.replaceAll("%IMAGE5%", doek.imageName.replace(".png",
					"_5.gif"));
			row = row.replaceAll("%MIN_PRICE%", "&euro; "
					+ Util.convertPrice(lowestReproPrice));

			row = row.replaceAll("%NAME%", doek.name);
			rows += row;

			String maatTable = "<table>";
			maatTable += "<tr><td id='schilderijMaten'><b>Maat</b></td><td width=10></td><td id='schilderijMaten'><b>Code</b></td><td width=10></td><td id='schilderijMaten'><b>Prijs</b></td></tr>";
			for (Repro doekmaat : doek.maten) {
				float priceSize = ((float) doekmaat.priceInCents) / 100;
				String pc = doekmaat.productieCode;
				maatTable += "<tr><td id='schilderijMaten'>"
						+ doekmaat.maat.toLowerCase()
						+ "</td><td></td><td id='schilderijMaten'>" + pc
						+ "</td><td></td><td id='schilderijMaten'>&euro; "
						+ Util.convertPrice(priceSize) + "</td></tr>";
			}
			maatTable += "</table>";

			if (doek.nietTeKoop) {
				origDetailPage = origDetailPage.replaceAll("%PRICE%",
						"Niet te koop");
			}

			pageNr++;

		}

		String schilderijenPage = schilderijTemplate;
		schilderijenPage = schilderijenPage.replaceAll("%TITLE%", title);
		
		if (subdir.equals("stof")){
			rows+="</table>";
			rows+="<p>";
			rows+="Het is ook mogelijk om je eigen stof aan te leveren die door ons gespannen en geschilderd wordt.<br>";
			rows+="Klik <a href=/eigenstof.html><b>[hier]</b></a> voor meer informatie.";
			rows+="</p>";
			rows+="<table>";
		}
		
		
		schilderijenPage = schilderijenPage.replaceAll("%ROWS%", rows);
		schilderijenPage = schilderijenPage.replaceAll("%PAGE_TITLE%",
				"ShirtsKV.nl - " + title);
		Util.setFileLine(basedir + "\\html\\" + subdir + "\\index.html", schilderijenPage);
	}
	

	public static void processDoekIndexTemplate(String basedir, String title, String headerImage) {
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
		String allDoekenHeaderTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\alldoeken_header.html"));

		String doekTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\indexpages\\doeken.html"));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%HEADER_IMAGE%", headerImage);

		doekTemplate = doekTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		doekTemplate = doekTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);
		doekTemplate = doekTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		doekTemplate = doekTemplate.replaceAll("%EXTRA_METADATA%",
				indexExtraHeaderTemplate);
		doekTemplate = doekTemplate.replaceAll("%ALLDOEKEN_HEADER%",
				allDoekenHeaderTemplate);

		doekTemplate = doekTemplate.replaceAll("%EXTRA_METADATA%", "");
		doekTemplate = doekTemplate.replaceAll("%EXTRA_BODY%", "");
		doekTemplate = doekTemplate.replaceAll("%TITLE%", title);
		doekTemplate = doekTemplate.replaceAll("%PAGE_TITLE%", "ShirtsKV.nl - "
				+ title);

		Util.setFileLine(basedir + "\\html\\" + "\\doeken.html",
				doekTemplate);

	}
	
}
