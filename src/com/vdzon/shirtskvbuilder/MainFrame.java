package com.vdzon.shirtskvbuilder;

import java.text.*;
import com.vdzon.shirtskvbuilder.data.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.*;
import java.util.*;
import java.awt.*;

import com.vdzon.shirtskvbuilder.data.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import com.borland.jbcl.layout.*;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.vdzon.shirtskvbuilder.templates.*;
import com.vdzon.shirtskvbuilder.util.Util;
import com.vdzon.shirtskvbuilder.container.*;
import javax.swing.JCheckBox;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class MainFrame extends JFrame {


	JPanel contentPane;
	XYLayout xYLayout1 = new XYLayout();
	JLabel jLabel1 = new JLabel();
	JTextField dbTextField = new JTextField();
	JButton jButton1 = new JButton();
	JButton jButton2 = new JButton();

	JCheckBox testdataCheckBox = new JCheckBox();
	JCheckBox jCheckBoxHeren = new JCheckBox();
	JCheckBox jCheckBoxDames = new JCheckBox();
	JCheckBox jCheckBoxJunior = new JCheckBox();
	JCheckBox jCheckBoxKleuter = new JCheckBox();
	JCheckBox jCheckBoxBaby = new JCheckBox();
	JCheckBox jCheckBoxTassen = new JCheckBox();
	JCheckBox jCheckBoxSchilderijen = new JCheckBox();
	JCheckBox jCheckBoxFotos = new JCheckBox();
	JButton productlijstButton = new JButton();

	public MainFrame() {
		try {
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			jbInit();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Component initialization.
	 * 
	 * @throws java.lang.Exception
	 */
	private void jbInit() throws Exception {
		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(xYLayout1);
		setSize(new Dimension(400, 336));
		setTitle("ShirtsKV Builder");
		jLabel1.setText("Database directory:");
		dbTextField.setText("D:\\documenten\\source\\shirtskv-site\\db");
		jButton1.setText("Maak html pagina\'s");
		jButton1.addActionListener(new MainFrame_jButton1_actionAdapter(this));
		jButton2.setText("maak images");
		jButton2.addActionListener(new MainFrame_jButton2_actionAdapter(this));
		testdataCheckBox.setSelected(false);
		testdataCheckBox.setText("Gebruik test data");
		jCheckBoxHeren.setText("Heren shirts");
		jCheckBoxDames.setText("Dames shirts");
		jCheckBoxJunior.setText("Junior/kinder");
		jCheckBoxKleuter.setText("Kleuter");
		jCheckBoxBaby.setText("Baby");
		jCheckBoxTassen.setText("Tassen");
		jCheckBoxSchilderijen.setText("Schilderijen");
		jCheckBoxFotos.setText("Foto\'s");
		productlijstButton.setText("Genereer product lijst");
		productlijstButton
				.addActionListener(new MainFrame_productlijstButton_actionAdapter(
						this));
		contentPane.add(jLabel1, new XYConstraints(5, 9, -1, -1));
		contentPane.add(dbTextField, new XYConstraints(5, 26, 377, -1));
		contentPane.add(jButton1, new XYConstraints(9, 68, -1, -1));
		contentPane.add(jButton2, new XYConstraints(10, 105, 122, -1));
		contentPane.add(testdataCheckBox, new XYConstraints(14, 136, -1, -1));
		contentPane.add(jCheckBoxHeren, new XYConstraints(238, 66, -1, -1));
		contentPane.add(jCheckBoxFotos, new XYConstraints(238, 234, -1, -1));
		contentPane.add(jCheckBoxDames, new XYConstraints(238, 90, -1, -1));
		contentPane.add(jCheckBoxJunior, new XYConstraints(238, 114, -1, -1));
		contentPane.add(jCheckBoxKleuter, new XYConstraints(238, 138, -1, -1));
		contentPane.add(jCheckBoxBaby, new XYConstraints(238, 162, -1, -1));
		contentPane.add(jCheckBoxTassen, new XYConstraints(238, 186, -1, -1));
		contentPane.add(jCheckBoxSchilderijen, new XYConstraints(238, 210, -1,
				-1));
		contentPane
				.add(productlijstButton, new XYConstraints(10, 231, 122, -1));
		dbTextField.setText("c:\\git\\shirtskv\\db");
	}




//	public static String dateToStr(Date date, String format) throws Exception {
//		if (date == null) {
//			return null;
//		}
//		SimpleDateFormat formatter = new SimpleDateFormat(format);
//		return formatter.format(date);
//	}

	public void startProcessing() {
		try {
			beep();
			Data.data.loadData(dbTextField.getText(),testdataCheckBox.isSelected());
			beep();
			Thread.sleep(50);
			beep();

			GlobalTemplate.processTemplate(dbTextField.getText(), "Wie zijn wij","../images/subbuttons/kop-wij.png","wiezijnwij.html","wiezijnwij.html");
			GlobalTemplate.processTemplate(dbTextField.getText(), "Making of...","../images/subbuttons/kop-makingof.png","makingof.html","makingof.html");
			GlobalTemplate.processTemplate(dbTextField.getText(), "Eigen shirts","../images/subbuttons/kop-eigenshirts.png","eigenshirt.html","eigenshirt.html");
			GlobalTemplate.processTemplate(dbTextField.getText(), "Eigen stof","../images/subbuttons/kop-eigenstof.png","eigenstof.html","eigenstof.html");
			
			KledingTemplate.processKledingTemplates(dbTextField.getText());

			/*
			 * processSchilderijen("Schilderijen",
			 * "../images/subbuttons/kop-schilderijen.png",
			 * Data.data.schilderijen);
			 */
			DoekTemplate.processDoeksoorten(dbTextField.getText(), "Schilderijen",
					"../images/subbuttons/kop-schilderijen.png");

			TasTemplate.processTassoorten(dbTextField.getText(), "Tassen", "../images/subbuttons/kop-tassen.png");

			FotoTemplate.processFotos(dbTextField.getText(), "Fotos", "../images/subbuttons/kop-fotos.png",
					Data.data.fotos);
			ForumTemplate.processForum(dbTextField.getText(), "Forum", "../images/subbuttons/kop-blablabla.png");
			InfoTemplate.processInfo(dbTextField.getText(), "Info", "../images/subbuttons/kop-informatie.png");
			IndexTemplate.processHome(dbTextField.getText(), "Info", "../images/subbuttons/kop-welkom.png");

			NieuwsbriefTemplate.processNieuwsbrief(dbTextField.getText(), testdataCheckBox.isSelected(),"Nieuwsbrief",
					"../images/subbuttons/kop-nieuwsbrief.png");
			KleurplaatTemplate.processKleurplaat(dbTextField.getText(), "Kleurplaat",
					"../images/subbuttons/kop-zelfdoen.png");
			SpreukenTemplate.processKleurplaat(dbTextField.getText(), "Spreukenboekje",
			"../images/subbuttons/kop-spreuken.png");

			VideosTemplate.processVideos(dbTextField.getText(), "Videos",
			"../images/subbuttons/kop-filmpjes.png");

		
			KledingTemplate.processKledingIndexTemplate(dbTextField.getText(), "dames", "kop-dames",
					"../images/subbuttons/kop-damesshirts.png");
			KledingTemplate.processKledingIndexTemplate(dbTextField.getText(), "heren", "kop-heren",
					"../images/subbuttons/kop-herenshirtsslimfit.png");
			KledingTemplate.processKledingIndexTemplate(dbTextField.getText(), "kinder", "kop-junior",
					"../images/subbuttons/kop-kindershirts.png");
			KledingTemplate.processKledingIndexTemplate(dbTextField.getText(), "kleuter", "kop-kleuter",
					"../images/subbuttons/kop-kleutershirts.png");
			KledingTemplate.processKledingIndexTemplate(dbTextField.getText(), "baby", "kop-baby",
					"../images/subbuttons/kop-babyshirts.png");
			KledingTemplate.processKledingIndexTemplate(dbTextField.getText(), "kleding", "kop-shirts",
					"../images/subbuttons/kop-shirts.png");
			DoekTemplate.processDoekIndexTemplate(dbTextField.getText(), "kop-shirts",
					"../images/subbuttons/kop-schilderijen.png");
			TasTemplate.processTasIndexTemplate(dbTextField.getText(), "kop-shirts","../images/subbuttons/kop-tassen.png");
			Data.data.createPricelist(dbTextField.getText());

			beep();
			Thread.sleep(250);
			beep();
			Thread.sleep(250);
			beep();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}





	public void processImages() {
		String targetBase = dbTextField.getText() + "\\html\\images";
		ConvertImages.processTemplateImages(dbTextField.getText(),testdataCheckBox.isSelected(),targetBase);
		ConvertImages.processFotoImages(dbTextField.getText(),testdataCheckBox.isSelected(),targetBase);
		ConvertImages.processKledingImages(dbTextField.getText(),testdataCheckBox.isSelected(),targetBase);
		ConvertImages.processTasImages(dbTextField.getText(),testdataCheckBox.isSelected(),targetBase);
		ConvertImages.processSchilderijImages(dbTextField.getText(),testdataCheckBox.isSelected(),targetBase);
		// }
	}

	public void jButton1_actionPerformed(ActionEvent e) {
		startProcessing();
System.out.println("klaar");
	}

	public void jButton2_actionPerformed(ActionEvent e) {
		beep();
		Data.data.loadData(dbTextField.getText(),testdataCheckBox.isSelected());
		beep();
		processImages();
		beep();
		beep();
		System.out.println("klaar");
	}

	public static void beep() {
		Toolkit.getDefaultToolkit().beep();
	}

	public void productlijstButton_actionPerformed(ActionEvent e) {
		Data.data.buildAllproductlijsten(dbTextField.getText(),testdataCheckBox.isSelected());
		//convertOldVoorraad();
	}
	
	
	public void convertOldVoorraad(){
		File dir = new File(dbTextField.getText()+"\\html\\admin\\data\\oude_voorraad");
		File files[] = dir.listFiles();
		for (File file:files){
			String name = file.getName();
			String[] parts = name.split("\\.");
			if (parts.length==4){
				if (parts[2].equals("s")){
					parts[0]="5";
				}
				String newName = parts[0]+"."+parts[1]+"."+parts[3];
				System.out.println(name+" -> "+newName);
				file.renameTo(new File(dbTextField.getText()+"\\html\\admin\\data\\oude_voorraad\\"+newName));
			}
		}
		
	}
	

}

class MainFrame_productlijstButton_actionAdapter implements ActionListener {
	private MainFrame adaptee;

	MainFrame_productlijstButton_actionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.productlijstButton_actionPerformed(e);
	}
}

class MainFrame_jButton2_actionAdapter implements ActionListener {
	private MainFrame adaptee;

	MainFrame_jButton2_actionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton2_actionPerformed(e);
	}
}

class MainFrame_jButton1_actionAdapter implements ActionListener {
	private MainFrame adaptee;

	MainFrame_jButton1_actionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton1_actionPerformed(e);
	}
}
