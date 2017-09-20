package com.vdzon.shirtskvbuilder.templates;

import java.io.File;

import com.vdzon.shirtskvbuilder.util.Util;

public class GlobalTemplate {
	public static void processTemplate(String basedir,String title, String headerImage, String htmlTemplate, String htmlFilename) {
		String globalHeaderTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_header.html"));
		String globalFooterTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\global_footer.html"));

		String allDoekenHeaderTemplate = Util.getFileLine(new File(basedir + "\\templates\\alldoeken_header.html"));
		String allShirtsHeaderTemplate = Util.getFileLine(new File(basedir + "\\templates\\allshirts_header.html"));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%EXTRA_METADATA%", "");
		globalHeaderTemplate = globalHeaderTemplate.replaceAll("%EXTRA_BODY%",
				"");

		String template = Util.getFileLine(new File(
				basedir
				+ "\\templates\\"+htmlTemplate));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%HEADER_IMAGE%", headerImage);

		template = template.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		template = template.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);

		template = template.replaceAll("%TITLE%", title);
		template = template.replaceAll("%PAGE_TITLE%",
				"ShirtsKV.nl - " + title);

		template = template.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		template = template.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);

		template = template.replaceAll("%ALLDOEKEN_HEADER%",allDoekenHeaderTemplate);

		template = template.replaceAll("%ALLSHIRTS_HEADER%", allShirtsHeaderTemplate);
		
		
		
		Util.setFileLine(basedir + "\\html\\" + htmlFilename, template);

	}

}
