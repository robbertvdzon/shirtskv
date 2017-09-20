package com.vdzon.shirtskvbuilder.templates;

import java.io.File;

import com.vdzon.shirtskvbuilder.util.Util;

public class NieuwsbriefTemplate {
	public static void processNieuwsbrief(String basedir, boolean testdata,String title, String headerImage) {
		String subdir = "nieuwsbrief";
		String globalHeaderTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\global_header.html"));
		String globalFooterTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\global_footer.html"));

		String nieuwsbrievenTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\nieuwsbrieven.html"));

		String nieuwsbriefTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\nieuwsbrief.html"));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%HEADER_IMAGE%", headerImage);

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%EXTRA_METADATA%", "");
		globalHeaderTemplate = globalHeaderTemplate.replaceAll("%EXTRA_BODY%",
				"");
		
		nieuwsbrievenTemplate = nieuwsbrievenTemplate.replaceAll(
				"%GLOBAL_HEADER%", globalHeaderTemplate);
		nieuwsbrievenTemplate = nieuwsbrievenTemplate.replaceAll(
				"%GLOBAL_FOOTER%", globalFooterTemplate);

		nieuwsbrievenTemplate = nieuwsbrievenTemplate.replaceAll("%TITLE%",
				title);
		nieuwsbrievenTemplate = nieuwsbrievenTemplate.replaceAll(
				"%PAGE_TITLE%", "ShirtsKV.nl - " + title);

		nieuwsbriefTemplate = nieuwsbriefTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		nieuwsbriefTemplate = nieuwsbriefTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);

		
		String dataDir = basedir + "\\data";
		if (testdata) {
			dataDir = basedir + "\\data-test";
		}
		File briefDir = new File(dataDir + "\\nieuwsbrieven");
		File[] brieven = Util.sortFiles(briefDir.listFiles(),true);
		String titles = "";
		String first10Titles = "";
		for (int i =0 ; i<brieven.length; i++) {
			File brief = brieven[i];
			String korteText = Util.getFileLine(brief.getAbsolutePath()
					+ "\\kortetext.html");
			String titel = Util.getFileLine(brief.getAbsolutePath()
					+ "\\titel.txt");
			String text = Util.getFileLine(brief.getAbsolutePath()
					+ "\\text.html");
			String briefHtmlName = brief.getName() + ".html";
			String titleText = "<a href=" + briefHtmlName + "><b>" + titel + "</b><br>"
			+ korteText + "</a><br><br>";
			titles += titleText;
			if (i<10){
				first10Titles+=titleText;
			}

			String nieuwsbriefPage = nieuwsbriefTemplate;
			nieuwsbriefPage = nieuwsbriefPage.replaceAll("%TITLE%", title);
			nieuwsbriefPage = nieuwsbriefPage.replaceAll("%PAGE_TITLE%",
					"ShirtsKV.nl - " + title);
			nieuwsbriefPage = nieuwsbriefPage
					.replaceAll("%NIEUWS_BRIEF%", text);
			Util.setFileLine(basedir + "\\html\\" + subdir + "\\"
					+ briefHtmlName, nieuwsbriefPage);
		}
		
		first10Titles+="<br><a href=allenieuwsbrieven.html><b>[Klik hier voor meer nieuwsbrieven]</b></a><br>\n";

		String allNieuwsbrievenTemplate = nieuwsbrievenTemplate;
		
		nieuwsbrievenTemplate = nieuwsbrievenTemplate.replaceAll(
				"%NIEUWS_BRIEVEN%", first10Titles);
		allNieuwsbrievenTemplate = allNieuwsbrievenTemplate.replaceAll(
				"%NIEUWS_BRIEVEN%", titles);

		Util.setFileLine(basedir + "\\html\\" + subdir
				+ "\\nieuwsbrieven.html", nieuwsbrievenTemplate);
		Util.setFileLine(basedir + "\\html\\" + subdir
				+ "\\allenieuwsbrieven.html", allNieuwsbrievenTemplate);

	}


}
