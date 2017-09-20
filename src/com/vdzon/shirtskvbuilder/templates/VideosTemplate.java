package com.vdzon.shirtskvbuilder.templates;

import java.io.File;

import com.vdzon.shirtskvbuilder.util.Util;

public class VideosTemplate {
	public static void processVideos(String basedir,String title, String headerImage) {
		String subdir = "";
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

		String spreukenboekjeTemplate = Util.getFileLine(new File(
				basedir
				+ "\\templates\\videos.html"));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%HEADER_IMAGE%", headerImage);

		spreukenboekjeTemplate = spreukenboekjeTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		spreukenboekjeTemplate = spreukenboekjeTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);

		spreukenboekjeTemplate = spreukenboekjeTemplate.replaceAll("%TITLE%", title);
		spreukenboekjeTemplate = spreukenboekjeTemplate.replaceAll("%PAGE_TITLE%",
				"ShirtsKV.nl - " + title);

		spreukenboekjeTemplate = spreukenboekjeTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		spreukenboekjeTemplate = spreukenboekjeTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);

		Util.setFileLine(basedir + "\\html\\" + subdir
				+ "\\videos.html", spreukenboekjeTemplate);

	}

}
