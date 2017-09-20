package com.vdzon.shirtskvbuilder.templates;

import java.io.File;

import com.vdzon.shirtskvbuilder.util.Util;

public class KleurplaatTemplate {
	public static void processKleurplaat(String basedir,String title, String headerImage) {
		String subdir = "kleurplaat";
		String globalHeaderTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_header.html"));
		String globalFooterTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_footer.html"));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%EXTRA_METADATA%", "");
		globalHeaderTemplate = globalHeaderTemplate.replaceAll("%EXTRA_BODY%",
				"");

		String kleurplaatTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\kleurplaat.html"));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%HEADER_IMAGE%", headerImage);

		kleurplaatTemplate = kleurplaatTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		kleurplaatTemplate = kleurplaatTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);

		kleurplaatTemplate = kleurplaatTemplate.replaceAll("%TITLE%", title);
		kleurplaatTemplate = kleurplaatTemplate.replaceAll("%PAGE_TITLE%",
				"ShirtsKV.nl - " + title);

		kleurplaatTemplate = kleurplaatTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		kleurplaatTemplate = kleurplaatTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);

		Util.setFileLine(basedir + "\\html\\" + subdir
				+ "\\kleurplaten.html", kleurplaatTemplate);

	}

}
