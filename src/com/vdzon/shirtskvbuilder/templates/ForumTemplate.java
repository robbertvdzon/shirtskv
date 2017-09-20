package com.vdzon.shirtskvbuilder.templates;

import java.io.File;

import com.vdzon.shirtskvbuilder.util.Util;

public class ForumTemplate {
	public static void processForum(String basedir, String title, String headerImage) {
		String subdir = "forum";
		String globalHeaderTemplate = Util.getFileLine(new File(basedir+ "\\templates\\global_header.html"));
		String globalFooterTemplate = Util.getFileLine(new File(basedir+ "\\templates\\global_footer.html"));
		String forum = Util.getFileLine(new File(basedir+ "\\templates\\forum.html"));
		String fullForum = Util.getFileLine(new File(basedir+ "\\templates\\forumFull.html"));

		globalHeaderTemplate = globalHeaderTemplate.replaceAll(
				"%EXTRA_METADATA%", "");
		globalHeaderTemplate = globalHeaderTemplate.replaceAll("%EXTRA_BODY%",
				"");

		forum = forum.replaceAll("%GLOBAL_HEADER%", globalHeaderTemplate);
		forum = forum.replaceAll("%GLOBAL_FOOTER%", globalFooterTemplate);
		forum = forum.replaceAll("%HEADER_IMAGE%", headerImage);
		forum = forum.replaceAll("%TITLE%", title);
		forum = forum.replaceAll("%PAGE_TITLE%", "ShirtsKV.nl - " + title);

		fullForum = fullForum.replaceAll("%GLOBAL_HEADER%", globalHeaderTemplate);
		fullForum = fullForum.replaceAll("%GLOBAL_FOOTER%", globalFooterTemplate);
		fullForum = fullForum.replaceAll("%HEADER_IMAGE%", headerImage);
		fullForum = fullForum.replaceAll("%TITLE%", title);
		fullForum = fullForum.replaceAll("%PAGE_TITLE%", "ShirtsKV.nl - " + title);

		Util.setFileLine(basedir + "\\html\\" + subdir
				+ "\\forum.html", forum);
		Util.setFileLine(basedir + "\\html\\" + subdir
				+ "\\forumfull.html", fullForum);
	}


}
