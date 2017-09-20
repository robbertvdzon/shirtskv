package com.vdzon.shirtskvbuilder.templates;

import java.io.File;

import com.vdzon.shirtskvbuilder.util.Util;

public class InfoTemplate {

	public static void processInfo(String basedir, String title, String headerImage) {
		String subdir = "info";

		String globalHeaderTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_header.html"));
		String globalFooterTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_footer.html"));

		String voorwaardenLinks = Util.getFileLine(new File(
				basedir
				+ "\\templates\\voorwaardenlinks.html"));

		String algemeenTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\algemenevoorwaarden.html"));
		String prijsTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\prijsvoorwaarden.html"));
		String leveringTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\leveringsvoorwaarden.html"));
		String shirtTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\shirtvoorwaarden.html"));
		String tasTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\tasvoorwaarden.html"));
		String schilderijTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\schilderijvoorwaarden.html"));
		String contactTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\contactgegevens.html"));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%HEADER_IMAGE%", headerImage);

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%EXTRA_METADATA%", "");
		globalHeaderTemplate = globalHeaderTemplate.replaceAll("%EXTRA_BODY%",
				"");

		// info
		String infoTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\voorwaarden.html"));
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);
		infoTemplate = infoTemplate.replaceAll("%TITLE%", title);
		infoTemplate = infoTemplate.replaceAll("%PAGE_TITLE%",
				"ShirtsKV.nl - voorwaarden");
		infoTemplate = infoTemplate.replaceAll("%INFO_DETAILS%",
				voorwaardenLinks);

		Util.setFileLine(basedir + "\\html\\" + subdir
				+ "\\info.html", infoTemplate);
		// algemeen
		infoTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\voorwaarden.html"));
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);
		infoTemplate = infoTemplate.replaceAll("%TITLE%", title);
		infoTemplate = infoTemplate.replaceAll("%PAGE_TITLE%",
				"ShirtsKV.nl - algemene voorwaarden");
		infoTemplate = infoTemplate.replaceAll("%INFO_DETAILS%",
				algemeenTemplate);
		Util.setFileLine(basedir + "\\html\\" + subdir
				+ "\\algemeen.html", infoTemplate);
		// algemeen
		infoTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\voorwaarden.html"));
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);
		infoTemplate = infoTemplate.replaceAll("%TITLE%", "Prijs voorwaarden");
		infoTemplate = infoTemplate.replaceAll("%PAGE_TITLE%",
				"ShirtsKV.nl - prijs voorwaarden");
		infoTemplate = infoTemplate.replaceAll("%INFO_DETAILS%", prijsTemplate);
		Util.setFileLine(basedir + "\\html\\" + subdir
				+ "\\prijs.html", infoTemplate);
		// algemeen
		infoTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\voorwaarden.html"));
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);
		infoTemplate = infoTemplate.replaceAll("%TITLE%",
				"Leverings voorwaarden");
		infoTemplate = infoTemplate.replaceAll("%PAGE_TITLE%",
				"ShirtsKV.nl - leverings voorwaarden");
		infoTemplate = infoTemplate.replaceAll("%INFO_DETAILS%",
				leveringTemplate);
		Util.setFileLine(basedir + "\\html\\" + subdir
				+ "\\levering.html", infoTemplate);
		// algemeen
		infoTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\voorwaarden.html"));
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);
		infoTemplate = infoTemplate.replaceAll("%TITLE%", "Shirts gegevens");
		infoTemplate = infoTemplate.replaceAll("%PAGE_TITLE%",
				"ShirtsKV.nl - shirt gegevens");
		infoTemplate = infoTemplate.replaceAll("%INFO_DETAILS%", shirtTemplate);
		Util.setFileLine(basedir + "\\html\\" + subdir
				+ "\\shirt.html", infoTemplate);
		// algemeen
		infoTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\voorwaarden.html"));
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);
		infoTemplate = infoTemplate.replaceAll("%TITLE%", "Tas gegevens");
		infoTemplate = infoTemplate.replaceAll("%PAGE_TITLE%",
				"ShirtsKV.nl - tas gegevens");
		infoTemplate = infoTemplate.replaceAll("%INFO_DETAILS%", tasTemplate);
		Util.setFileLine(basedir + "\\html\\" + subdir
				+ "\\tas.html", infoTemplate);
		// algemeen
		infoTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\voorwaarden.html"));
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);
		infoTemplate = infoTemplate
				.replaceAll("%TITLE%", "Schilderij gegevens");
		infoTemplate = infoTemplate.replaceAll("%PAGE_TITLE%",
				"ShirtsKV.nl - schilderij gegevens");
		infoTemplate = infoTemplate.replaceAll("%INFO_DETAILS%",
				schilderijTemplate);
		Util.setFileLine(basedir + "\\html\\" + subdir
				+ "\\schilderij.html", infoTemplate);
		// algemeen
		infoTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\voorwaarden.html"));
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		infoTemplate = infoTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);
		infoTemplate = infoTemplate.replaceAll("%TITLE%", "Contact gegevens");
		infoTemplate = infoTemplate.replaceAll("%PAGE_TITLE%",
				"ShirtsKV.nl - contact gegevens");
		infoTemplate = infoTemplate.replaceAll("%INFO_DETAILS%",
				contactTemplate);
		Util.setFileLine(basedir + "\\html\\" + subdir
				+ "\\contact.html", infoTemplate);
	}
	
	
}
