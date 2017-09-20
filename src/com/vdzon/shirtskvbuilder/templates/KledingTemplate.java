package com.vdzon.shirtskvbuilder.templates;

import java.io.File;
import java.util.Hashtable;

import com.vdzon.shirtskvbuilder.Const;
import com.vdzon.shirtskvbuilder.data.Data;
import com.vdzon.shirtskvbuilder.data.Kleding;
import com.vdzon.shirtskvbuilder.data.KledingStuk;
import com.vdzon.shirtskvbuilder.data.Kledingkleur;
import com.vdzon.shirtskvbuilder.data.Kledingmaat;
import com.vdzon.shirtskvbuilder.util.Util;

public class KledingTemplate {

	public static void processKledingTemplates(String basedir) {
		for (Kleding kleding : Data.data.kleding) {
			processKledingTemplates(basedir, kleding.kleding, kleding.kleding,
					"../images/subbuttons/kop-" + kleding.kleding + ".png",
					kleding);

		}
	}

	public static void processKledingTemplates(String basedir, String subdir, String title,
			String headerImage, Kleding kleding) {

		String indexTemplate = Util.getFileLine(basedir
				+ "\\catalogus\\templates\\shirt_index_template.txt");
		String page1Template = Util.getFileLine(basedir
				+ "\\catalogus\\templates\\shirt1_template.txt");
		String page2Template = Util.getFileLine(basedir
				+ "\\catalogus\\templates\\shirt2_template.txt");
		String page3Template = Util.getFileLine(basedir
				+ "\\catalogus\\templates\\shirt3_template.txt");

		String globalHeaderTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_header.html"));
		String globalFooterTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_footer.html"));
		String allShirtsHeaderTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\allshirts_header.html"));
		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%HEADER_IMAGE%", headerImage);
		String shirtsTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\shirts.html"));
		String shirtRowTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\shirtrow.html"));
		String shirtDetailTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\shirtdetail.html"));
		String shirtKleurLinkTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\shirtkleurlink.html"));
		String shirtKleurLayerTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\shirtkleurlayer.html"));
		String shirtImageTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\shirtimage.html"));
		// String shirtTemplate = Util.getFileLine(new
		// File(basedir + "\\templates\\shirtvoorwaarden.html"));
		String tasTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\tasvoorwaarden.html"));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%EXTRA_METADATA%", "");
		globalHeaderTemplate = globalHeaderTemplate.replaceAll("%EXTRA_BODY%",
				"");

		shirtsTemplate = shirtsTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		shirtDetailTemplate = shirtDetailTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);

		shirtsTemplate = shirtsTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);
		shirtDetailTemplate = shirtDetailTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);

		shirtsTemplate = shirtsTemplate.replaceAll("%ALLSHIRTS_HEADER%",
				allShirtsHeaderTemplate);
		shirtDetailTemplate = shirtDetailTemplate.replaceAll(
				"%ALLSHIRTS_HEADER%", allShirtsHeaderTemplate);

		// Previous/Next definition
		String first = null;
		String last = null;
		String previous = null;
		KledingStuk firstShirt = null;
		KledingStuk lastShirt = null;
		KledingStuk previousShirt = null;

		Hashtable nextTable = new Hashtable();
		Hashtable previousTable = new Hashtable();
		for (KledingStuk kledingStuk : kleding.kledingstukken) {
			String detailPageName = "";
			for (Kledingkleur kledingkleur : kledingStuk.kleuren) {
				if (kledingkleur.kleur
						.equals(kledingStuk.firstKledingKleur.kleur)) {
					detailPageName = kleding.kleding + "_" + kledingStuk.name
							+ "_" + kledingkleur.kleur + "_detail.html";
				}
			}

			if (previous != null) {
				nextTable.put(previousShirt, detailPageName);
				previousTable.put(kledingStuk, previous);
			} else {
				first = detailPageName;
				firstShirt = kledingStuk;
			}
			if (kledingStuk == kleding.kledingstukken.lastElement()) {
				last = detailPageName;
				lastShirt = kledingStuk;
			}
			previous = detailPageName;
			previousShirt = kledingStuk;
		}
		if (lastShirt != null && first != null)
			nextTable.put(lastShirt, first);
		if (firstShirt != null && last != null)
			previousTable.put(firstShirt, last);

		String rows = "";
		int pageNr = 0;

		for (KledingStuk kledingStuk : kleding.kledingstukken) {

			String layers = "";
			String maatOptions = "";
			for (Kledingmaat shirtMaat : kledingStuk.maten) {
				float price = ((float) shirtMaat.priceInCents) / 100;
				maatOptions += "<option value='" + shirtMaat.maat + "'>"
						+ shirtMaat.maat + "</option>\n";
				// maatOptions+="<option value='"+shirtMaat.maat+"'>"+shirtMaat.maat+"&nbsp;&nbsp;&nbsp;(&euro; "+Util.convertPrice(price)+") </option>\n";
			}

			// now create all the detail pages and the image page

			String omschrijving1 = "";
			String image1 = "";
			String pc1 = "";
			String shirtkleur1 = "";
			String maatprijzen1 = "";

			String omschrijving2 = "";
			String image2 = "";
			String pc2 = "";
			String shirtkleur2 = "";
			String maatprijzen2 = "";

			String omschrijving3 = "";
			String image3 = "";
			String pc3 = "";
			String shirtkleur3 = "";
			String maatprijzen3 = "";

			String omschrijving4 = "";
			String image4 = "";
			String pc4 = "";
			String shirtkleur4 = "";
			String maatprijzen4 = "";

			String omschrijving5 = "";
			String image5 = "";
			String pc5 = "";
			String shirtkleur5 = "";
			String maatprijzen5 = "";

			String omschrijving6 = "";
			String image6 = "";
			String pc6 = "";
			String shirtkleur6 = "";
			String maatprijzen6 = "";

			int colorIndex = 0;
			String maatTable = "";
			int shirtCount = 0;

			String firstPageName = kleding.kleding + "_" + kledingStuk.name
					+ "_" + kledingStuk.firstKledingKleur.kleur
					+ "_detail.html";

			float price = 0;
			for (Kledingkleur kledingkleur : kledingStuk.kleuren) {

				shirtCount++;

				// get links for this type
				// loop door alle kleuren en zoek de type die hetzelfde is, voeg
				// deze toe aan de link lijst
				String links = "";
				for (Kledingkleur kledingkleur2 : kledingStuk.kleuren) {
					String detailPageName = kleding.kleding + "_"
							+ kledingStuk.name + "_" + kledingkleur2.kleur
							+ "_detail.html";

					String link = shirtKleurLinkTemplate;
					link = link.replaceAll("%KLEUR%", kledingkleur2.kleur);
					link = link.replaceAll("%IMAGE1%", kledingkleur2.imageName
							.replace(".png", "_1.gif"));
					link = link.replaceAll("%IMAGE2%", kledingkleur2.imageName
							.replace(".png", "_2.gif"));
					link = link.replaceAll("%IMAGE3%", kledingkleur2.imageName
							.replace(".png", "_3.gif"));
					link = link.replaceAll("%IMAGE4%", kledingkleur2.imageName
							.replace(".png", "_4.gif"));
					link = link.replaceAll("%DETAIL_PAGE%", detailPageName);
					link = link.replaceAll("%NAME%", kledingStuk.name);
					links += link;
				}

				String omschrijving = Util.firstCapitalLetter(kledingStuk.name)
						+ " op " + " " + kledingkleur.kleur.toLowerCase()+" "+kledingStuk.kledingOmschrijving;

				String shirtTemplate = Util.getFileLine(new File(
						basedir
						+ "\\templates\\shirtvoorwaarden.html"));
				switch (kledingStuk.kledingStukCode) { // heren shirts
				case 0:
					shirtTemplate = Util.getFileLine(new File(
							basedir
							+ "\\templates\\shirtmaten_dames.html"));
					break;
				case 1:
					shirtTemplate = Util.getFileLine(new File(
							basedir
							+ "\\templates\\shirtmaten_trui_effen.html"));
					break;
				case 2:
					shirtTemplate = Util.getFileLine(new File(
							basedir
							+ "\\templates\\shirtmaten_trui_2kleur.html"));
					break;
				case 3:
					shirtTemplate = Util.getFileLine(new File(
							basedir
							+ "\\templates\\shirtmaten_heren_regular.html"));
					break;
				case 4:
					shirtTemplate = Util.getFileLine(new File(
							basedir
							+ "\\templates\\shirtmaten_kleuter.html"));
					break;
				case 5:
					shirtTemplate = Util.getFileLine(new File(
							basedir
							+ "\\templates\\shirtmaten_junior.html"));
					break;
				case 6:
					shirtTemplate = Util.getFileLine(new File(
							basedir
							+ "\\templates\\shirtmaten_babyromper.html"));
					break;
				case 7:
					shirtTemplate = Util.getFileLine(new File(
							basedir
							+ "\\templates\\shirtmaten_babyshirt.html"));
					break;
				case 8:
					shirtTemplate = Util.getFileLine(new File(
							basedir
							+ "\\templates\\shirtmaten_trui_effen.html"));
					break;
				case 9:
					shirtTemplate = Util.getFileLine(new File(
							basedir
							+ "\\templates\\shirtmaten_trui_2kleur.html"));
					break;
				case 10:
					shirtTemplate = Util.getFileLine(new File(
							basedir
							+ "\\templates\\shirtmaten_junior.html"));
					break;
				case 11:
					shirtTemplate = Util.getFileLine(new File(
							basedir
							+ "\\templates\\shirtmaten_heren_slimfit.html"));
					break;
				}

				// create the image page
				String imagepageName = kleding.kleding + "_" + kledingStuk.name
						+ "_" + kledingkleur.kleur + "_image.html";
				String imagePage = shirtImageTemplate;
				imagePage = imagePage.replaceAll("%IMAGE1%",
						kledingkleur.imageName.replace(".png", "_1.gif"));
				imagePage = imagePage.replaceAll("%IMAGE2%",
						kledingkleur.imageName.replace(".png", "_2.gif"));
				imagePage = imagePage.replaceAll("%IMAGE3%",
						kledingkleur.imageName.replace(".png", "_3.gif"));
				imagePage = imagePage.replaceAll("%IMAGE4%",
						kledingkleur.imageName.replace(".png", "_4.gif"));

				imagePage = imagePage.replaceAll("%NAME%", kledingStuk.name);
				imagePage = imagePage.replaceAll("%PAGE_TITLE%",
						"ShirtsKV.nl - " + omschrijving);
				Util.setFileLine(basedir + "\\html\\" + subdir
						+ "\\" + imagepageName, imagePage);

				// create the page
				String detailPageName = kleding.kleding + "_"
						+ kledingStuk.name + "_" + kledingkleur.kleur
						+ "_detail.html";
				String detailPage = shirtDetailTemplate;
				price = ((float) kledingStuk.lowestPriceInCent) / 100;
				detailPage = detailPage.replaceAll("%KLEUR%",
						kledingkleur.kleur);
				detailPage = detailPage.replaceAll("%IMAGE1%",
						kledingkleur.imageName.replace(".png", "_1.gif"));
				detailPage = detailPage.replaceAll("%IMAGE2%",
						kledingkleur.imageName.replace(".png", "_2.gif"));
				detailPage = detailPage.replaceAll("%IMAGE3%",
						kledingkleur.imageName.replace(".png", "_3.gif"));
				detailPage = detailPage.replaceAll("%IMAGE4%",
						kledingkleur.imageName.replace(".png", "_4.gif"));
				detailPage = detailPage.replaceAll("%PRODUCT_CODE%", kledingkleur.productCode);

				String extraText = "<br><br><b><font color=red>Let op, bekijk eerst de maattabel voor de lengte en breedte maten voor het bestellen!</font></b>";
				if (kledingStuk.kledingStukCode == 5) { // heren shirts
					extraText = "<br><br><b><font color=red>Let op! De slim-fit shirts vallen een maatje kleiner dan normale shirts, dus bekijk de maattabel voor het bestellen!</font></b>";
				}
				if (kledingStuk.kledingStukCode == 0) { // dames shirts
					extraText = "<br><br><b><font color=red>Let op! De shirts vallen klein, dus bekijk de maattabel voor het bestellen!</font></b>";
				}
				if (kledingStuk.kledingStukCode == 1
						|| kledingStuk.kledingStukCode == 2) { // dames truien
					extraText = "<br><br><b><font color=red>Let op! De maten zijn heren maten en vallen ongeveer 2 maten groter dan normale dames truien. Bekijk dus de maattabel! </font></b>";
				}

				detailPage = detailPage.replaceAll("%NAME%", kledingStuk.name);

				detailPage = detailPage.replaceAll("%EXTRA_TEXT%", extraText);
				detailPage = detailPage.replaceAll("%OMSCHRIJVING%",
						omschrijving);
				detailPage = detailPage.replaceAll("%ALLE_MATEN%",
						kledingStuk.alleMaten);
				detailPage = detailPage.replaceAll("%IMAGE_PAGE%",
						imagepageName);
				detailPage = detailPage.replaceAll("%MAAT_OPTIONS%",
						maatOptions);
				detailPage = detailPage.replaceAll("%PAGE_LINK%", "../"
						+ subdir + "/" + detailPageName);
				detailPage = detailPage.replaceAll("%PAGE_TITLE%",
						"ShirtsKV.nl - " + omschrijving);
				detailPage = detailPage.replaceAll("%KLEUR_LINKS%", links);
				detailPage = detailPage.replaceAll("%MIN_PRICE%", ""
						+ Util.convertPrice(price));

				detailPage = detailPage.replaceAll("%INFO_SHIRT%",
						shirtTemplate);

				detailPage = detailPage.replaceAll("%INFO_TAS%", tasTemplate);
				detailPage = detailPage.replaceAll("%DIRNAME%", subdir);

				try {
					detailPage = detailPage.replaceAll("%NEXT_DETAIL_PAGE%",
							(String) nextTable.get(kledingStuk));
					detailPage = detailPage.replaceAll(
							"%PREVIOUS_DETAIL_PAGE%", (String) previousTable
									.get(kledingStuk));
				} catch (Exception ex) {
					System.out.println("NULL bij " + detailPageName);
				}

				Util.setFileLine(basedir + "\\html\\" + subdir
						+ "\\" + detailPageName, detailPage);

				maatTable = "<table>";
				maatTable += "<tr><td><b>Maat</b></td><td width=10></td><td><b>Code</b></td><td width=10></td><td><b>Prijs</b></td></tr>";
				for (Kledingmaat kledingmaat : kledingStuk.maten) {
					float priceSize = ((float) kledingmaat.priceInCents) / 100;
					String pc = kledingkleur+ "." + kledingmaat.maat.toLowerCase();
					maatTable += "<tr><td>" + kledingmaat.maat.toLowerCase()
							+ "</td><td></td><td>" + pc
							+ "</td><td></td><td>&euro; "
							+ Util.convertPrice(price) + "</td></tr>";
				}
				maatTable += "</table>";

				colorIndex++;
				if (colorIndex == 1) {
					omschrijving1 = Util.firstCapitalLetter(kledingStuk.name)
							+ " op " + " " + kledingkleur.kleur.toLowerCase();
					image1 = kledingkleur.imageName.replaceAll("../images", "");
					pc1 = kledingkleur.productCode;
					maatprijzen1 = maatTable;
					shirtkleur1 = kledingkleur.kleur;
				}
				if (colorIndex == 2) {
					omschrijving2 = Util.firstCapitalLetter(kledingStuk.name)
							+ " op " + " " + kledingkleur.kleur.toLowerCase();
					image2 = kledingkleur.imageName.replaceAll("../images", "");
					pc2 = kledingkleur.productCode;
					maatprijzen2 = maatTable;
					shirtkleur2 = kledingkleur.kleur;
				}
				if (colorIndex == 3) {
					omschrijving3 = Util.firstCapitalLetter(kledingStuk.name)
							+ " op " + " " + kledingkleur.kleur.toLowerCase();
					image3 = kledingkleur.imageName.replaceAll("../images", "");
					pc3 = kledingkleur.productCode;
					maatprijzen3 = maatTable;
					shirtkleur3 = kledingkleur.kleur;
				}
				if (colorIndex == 4) {
					omschrijving4 = Util.firstCapitalLetter(kledingStuk.name)
							+ " op " + " " + kledingkleur.kleur.toLowerCase();
					image4 = kledingkleur.imageName.replaceAll("../images", "");
					pc4 = kledingkleur.productCode;
					maatprijzen4 = maatTable;
					shirtkleur4 = kledingkleur.kleur;
				}
				if (colorIndex == 5) {
					omschrijving5 = Util.firstCapitalLetter(kledingStuk.name)
							+ " op " + " " + kledingkleur.kleur.toLowerCase();
					image5 = kledingkleur.imageName.replaceAll("../images", "");
					pc5 = kledingkleur.productCode;
					maatprijzen5 = maatTable;
					shirtkleur5 = kledingkleur.kleur;
				}
				if (colorIndex == 6) {
					omschrijving6 = Util.firstCapitalLetter(kledingStuk.name)
							+ " op " + " " + kledingkleur.kleur.toLowerCase();
					image6 = kledingkleur.imageName.replaceAll("../images", "");
					pc6 = kledingkleur.productCode;
					maatprijzen6 = maatTable;
					shirtkleur6 = kledingkleur.kleur;
				}
			}

			// add a row for this image
			String row = shirtRowTemplate;
			row = row.replaceAll("%DETAILPAGE%", firstPageName);
			row = row.replaceAll("%IMAGE1%",
					kledingStuk.firstKledingKleur.imageName.replace(".png",
							"_1.gif"));
			row = row.replaceAll("%IMAGE2%",
					kledingStuk.firstKledingKleur.imageName.replace(".png",
							"_2.gif"));
			row = row.replaceAll("%IMAGE3%",
					kledingStuk.firstKledingKleur.imageName.replace(".png",
							"_3.gif"));
			row = row.replaceAll("%IMAGE4%",
					kledingStuk.firstKledingKleur.imageName.replace(".png",
							"_4.gif"));
			row = row.replaceAll("%IMAGE5%",
					kledingStuk.firstKledingKleur.imageName.replace(".png",
							"_5.gif"));
			row = row.replaceAll("%MIN_PRICE%", "" + Util.convertPrice(price));

			row = row.replaceAll("%NAME%", kledingStuk.name);
			rows += row;

		}

		String shirtsPage = shirtsTemplate;
		shirtsPage = shirtsPage.replaceAll("%TITLE%", title);
		shirtsPage = shirtsPage.replaceAll("%ROWS%", rows);
		shirtsPage = shirtsPage.replaceAll("%PAGE_TITLE%", "ShirtsKV.nl - "
				+ title);
		Util.setFileLine(basedir + "\\html\\" + subdir
				+ "\\index.html", shirtsPage);
	}

	
	
	public static void processKledingIndexTemplate(String basedir, String kleding, String title,
			String headerImage) {
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
		String allShirtsHeaderTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\allshirts_header.html"));

		String kledingTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\indexpages\\" + kleding + ".html"));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%HEADER_IMAGE%", headerImage);

		kledingTemplate = kledingTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		kledingTemplate = kledingTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);
		kledingTemplate = kledingTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		kledingTemplate = kledingTemplate.replaceAll("%EXTRA_METADATA%",
				indexExtraHeaderTemplate);
		kledingTemplate = kledingTemplate.replaceAll("%ALLSHIRTS_HEADER%",
				allShirtsHeaderTemplate);

		kledingTemplate = kledingTemplate.replaceAll("%EXTRA_METADATA%", "");
		kledingTemplate = kledingTemplate.replaceAll("%EXTRA_BODY%", "");
		kledingTemplate = kledingTemplate.replaceAll("%TITLE%", title);
		kledingTemplate = kledingTemplate.replaceAll("%PAGE_TITLE%",
				"ShirtsKV.nl - " + title);

		Util.setFileLine(basedir + "\\html\\" + "\\" + kleding
				+ ".html", kledingTemplate);

	}
	

}
