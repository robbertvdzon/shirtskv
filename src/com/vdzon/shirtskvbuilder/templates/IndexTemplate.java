package com.vdzon.shirtskvbuilder.templates;

import java.io.File;

import com.vdzon.shirtskvbuilder.util.Util;

public class IndexTemplate {

	public static void processHome(String basedir, String title, String headerImage) {
		String subdir = "info";
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

		String indexTemplate = Util.getFileLine(new File(basedir
				+ "\\templates\\indexpages\\index.html"));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%HEADER_IMAGE%", headerImage);

		indexTemplate = indexTemplate.replaceAll("%GLOBAL_HEADER%",
				globalHeaderTemplate);
		indexTemplate = indexTemplate.replaceAll("%EXTRA_METADATA%",
				indexExtraHeaderTemplate);
		indexTemplate = indexTemplate.replaceAll("%EXTRA_BODY%",
				indexExtraBodyTemplate);

		indexTemplate = indexTemplate.replaceAll("%GLOBAL_FOOTER%",
				globalFooterTemplate);
		indexTemplate = indexTemplate.replaceAll("%TITLE%", title);
		indexTemplate = indexTemplate.replaceAll("%PAGE_TITLE%",
				"ShirtsKV.nl - " + title);

		indexTemplate = indexTemplate.replaceAll("\\.\\.", "."); // not in
		// subfolder

		Util.setFileLine(basedir + "\\html\\index.html",
				indexTemplate);
	}


}
