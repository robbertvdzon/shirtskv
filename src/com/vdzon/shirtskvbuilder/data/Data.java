package com.vdzon.shirtskvbuilder.data;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.*;

import com.vdzon.shirtskvbuilder.Const;
import com.vdzon.shirtskvbuilder.container.CodeObject;
import com.vdzon.shirtskvbuilder.container.CodeTable;
import com.vdzon.shirtskvbuilder.container.PrijslijstObject;
import com.vdzon.shirtskvbuilder.container.PrijslijstTable;
import com.vdzon.shirtskvbuilder.util.Util;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Data {
    public static Data data = new Data();
    public boolean loaded = false;

    public Hashtable prijsLijst = new Hashtable();
    public Hashtable verzendkostenPrijsLijst = new Hashtable();
    public Vector<Kleding> kleding = new Vector<Kleding>();
    public Vector<Doeksoort> doeksoorten = new Vector<Doeksoort>();
    public Vector<Tassoort> tassoorten = new Vector<Tassoort>();
    public Vector schilderijen = new Vector();
    public Vector tassen = new Vector();
    public Vector fotos = new Vector();

    
	private CodeTable codeTabelKledingstuk = null;
	private CodeTable codeTabelKledingkleur = null;
	private CodeTable codeTabelKledingtype = null;
	private CodeTable codeTabelKledingontwerp = null;
	private CodeTable codeTabelDoeksoort = null;
	private CodeTable codeTabelKledingmaat = null;
	private CodeTable codeTabelDoek = null;
	private CodeTable codeTabelRepromaat = null;
	private CodeTable codeTabelTas = null;
	private CodeTable codeTabelTasSoort = null;
	private PrijslijstTable prijslijstTabel = null;
    
	public void loadPrijslijst(String prijslijstFilename) throws Exception {
		String prijslijst = Util.getFileLine(new File(prijslijstFilename));
		StringTokenizer st = new StringTokenizer(prijslijst, "\n", false);
		while (st.hasMoreTokens()) {
			String line = st.nextToken().trim().trim();
			StringTokenizer st2 = new StringTokenizer(line, ":", false);
			String code = st2.nextToken().trim().trim();
			String prijs = st2.nextToken().trim().trim();
			System.out.println(prijs);
			int price = Integer.parseInt(prijs);
			Data.data.prijsLijst.put(code, new Integer(price));
		}
	}

	public void loadVerzendkostenPrijslijst(String prijslijstFilename)
			throws Exception {
		String prijslijst = Util.getFileLine(new File(prijslijstFilename));
		StringTokenizer st = new StringTokenizer(prijslijst, "\n", false);
		while (st.hasMoreTokens()) {
			String line = st.nextToken().trim().trim();
			StringTokenizer st2 = new StringTokenizer(line, ":", false);
			String code = st2.nextToken().trim().trim();
			String prijs = st2.nextToken().trim().trim();
			int price = Integer.parseInt(prijs);
			
			Data.data.verzendkostenPrijsLijst.put(code, new Integer(price));
		}
	}

	public void loadKleding(String baseFolder, Data data) throws Exception {
		File root = new File(baseFolder);
		File[] kledingFiles = Util.sortFiles(root.listFiles(),false);
		for (File kledingFile : kledingFiles) {
			Kleding kleding = new Kleding();
			kleding.kleding = kledingFile.getName();
			data.kleding.addElement(kleding);
			CodeObject codeObject = codeTabelKledingstuk
					.getOrCreate(kleding.kleding);
			File[] kledingstukkenFiles = Util.sortFiles(kledingFile.listFiles(),true);
			for (File kledingstukkenFile : kledingstukkenFiles) {
				KledingStuk kledingStuk = new KledingStuk();
				kledingStuk.name = kledingstukkenFile.getName();
				kledingStuk.kledingOmschrijving = codeObject.omschrijving;
				String ontwerpname = kledingstukkenFile.getName().substring(2);
				String sortKey = kledingStuk.name;
				CodeObject codeOntwerpObject = codeTabelKledingontwerp
						.getOrCreate(ontwerpname);
				kledingStuk.productCode = Const.SHIRT_BASECODE+"."+ codeOntwerpObject.code + "." + codeObject.code;

//				kledingStuk.productCode = Const.SHIRT_BASECODE+"."+ kledingStuk.ontwerpCode + "."
//				+ kledingStuk.kledingStukCode + "." + kledingkleur.code);
				
					
				kledingStuk.load(kledingstukkenFile, sortKey,
						codeObject.omschrijving, codeObject.code,
						codeOntwerpObject.code, codeTabelKledingkleur,
						codeTabelKledingtype, codeTabelKledingmaat);

				kleding.kledingstukken.addElement(kledingStuk);
			}
		}

		// update pricelist
		for (Kleding kleding : data.kleding) {
			for (KledingStuk kledingStuk : kleding.kledingstukken) {
				for (Kledingkleur kledingkleur : kledingStuk.kleuren) {
					for (Kledingmaat kledingmaat : kledingStuk.maten) {
//						String ID = Const.SHIRT_BASECODE+"." + kledingStuk.ontwerpCode + "."
//								+ kledingStuk.kledingStukCode + "."
//								+ kledingkleur.code + ".r."
//								+ kledingmaat.maat.toLowerCase();

						String ID = kledingkleur.productCode+"."+ kledingmaat.maat.toLowerCase();
						
						String detailURL = "../" + kleding.kleding + "/" + kleding.kleding+"_"
						
								+ kledingStuk.name + "_" + kledingkleur.kleur
								+ "_detail.html";
						String imageURL = kledingkleur.imageName.replace(
								".png", "_1.gif");
						String omschrijving = Util
								.firstCapitalLetter(kledingStuk.name)
								+ " op "
								+ " "  
								+ kledingkleur.kleur.toLowerCase()
								+ " " + kledingStuk.kledingOmschrijving+" " 
								+ ", maat "
								+ kledingmaat.maat;
						prijslijstTabel.update(ID, kledingkleur.imageName,
								kledingkleur.imageName,
								kledingmaat.priceInCents, omschrijving,
								kledingmaat.verzendkosten, detailURL, imageURL);

					}

				}

			}
		}
	}
	
	public void loadTassen(String baseFolder, Data data) throws Exception {
		File root = new File(baseFolder);
		File[] tassoortFiles = Util.sortFiles(root.listFiles(),true);
		for (File tassoortFile : tassoortFiles) {
			Tassoort tassoort = new Tassoort();
			tassoort.tassoort = tassoortFile.getName();
			CodeObject codeObject = codeTabelTasSoort.getOrCreate(tassoort.tassoort);
			tassoort.tassoortCode = codeObject.code;
			data.tassoorten.addElement(tassoort);
			
			File[] tassen = Util.sortFiles(tassoortFile.listFiles(),true);
			for (int i = 0; i < tassen.length; i++) {
				if (tassen[i].isDirectory()) {
					Tas tas = new Tas();
					tas.tassoort = tassoort;
					tassoort.tassen.addElement(tas);
					tas.tassoort = tassoort;
					tas.load(tassen[i]);
					tas.productCode = Const.TAS_BASECODE+"."+tassoort.tassoortCode+"."+codeTabelTas.getOrCreate(tas.name).code;
				}
			}

			// update pricelist
			for (Tas tas:tassoort.tassen) {
				String ID = tas.productCode;
				String detailURL = "../"+tas.tassoort.tassoort+"/" + tas.name + "_detail.html";
				String imageURL = tas.imageName.replace(".png", "_1.gif");
				prijslijstTabel.update(ID, tas.imageName, tas.imageName,
						tas.priceInCents, "Tas '" + tas.name + "'",
						tas.verzendkosten, detailURL, imageURL);
			}
		}

	}

	public void loadDoeken(String baseFolder, Data data) throws Exception {
		File root = new File(baseFolder);
		File[] doeksoortFiles = Util.sortFiles(root.listFiles(),true);
		for (File doeksoortFile : doeksoortFiles) {
			Doeksoort doeksoort = new Doeksoort();
			doeksoort.doeksoort = doeksoortFile.getName();
			CodeObject codeObject = codeTabelDoeksoort
					.getOrCreate(doeksoort.doeksoort);
			doeksoort.doeksoortCode = codeObject.code;
			data.doeksoorten.addElement(doeksoort);
			File[] doekenFiles = Util.sortFiles(doeksoortFile.listFiles(),true);
			for (File doekFile : doekenFiles) {
				Doek doek = new Doek();
				doek.doeksoort = doeksoort;
				doek.name = doekFile.getName();
				int doekcode = codeTabelDoek.getOrCreate(doek.name).code;
				doek.productCode = Const.DOEK_BASECODE + "." +  doeksoort.doeksoortCode + "." + doekcode;
				doek.reproBaseProductCode = Const.REPRO_BASECODE + "." + doeksoort.doeksoortCode + "." + doekcode;
				doek.load(doekFile, codeTabelRepromaat);
				doeksoort.doeken.addElement(doek);
				
			}
		}

		// update pricelist
		for (Doeksoort doeksoort : data.doeksoorten) {
			for (Doek doek : doeksoort.doeken) {
				String ID = doek.productCode;
				String detailURL = "../" + doeksoort.doeksoort + "/"
						+ doek.name + "_orig_detail.html";
				String imageURL = doek.imageName.replace(".png", "_1.gif");
				String omschrijving = "Schilderij "+Util.firstCapitalLetter(doek.name);
				prijslijstTabel.update(ID, doek.imageName, doek.imageName,
						doek.priceInCents, omschrijving,
						doek.verzendkosten, detailURL, imageURL);

				
				for (Repro doekmaat : doek.maten) {
					String reproID = doek.reproBaseProductCode + "." + doekmaat.maat.toLowerCase();
					String reproDetailURL = "../" + doeksoort.doeksoort + "/"
							+ doek.name + "_orig_detail.html";
					String reproImageURL = doek.reproImageName.replace(".png", "_1.gif");
					String reproOmschrijving = "Reproductie "+Util.firstCapitalLetter(doek.name)
							+ ", maat " + doekmaat.maat;
					prijslijstTabel.update(reproID, doek.reproImageName, doek.reproImageName,
							doekmaat.priceInCents, reproOmschrijving,
							doekmaat.verzendkosten, reproDetailURL, reproImageURL);

				}
			}
		}
	}


	public void loadFotos(String baseFolder, Vector vector) throws Exception {
		File root = new File(baseFolder);
		File[] fotos = Util.sortFiles(root.listFiles(),true);
		for (int i = 0; i < fotos.length; i++) {
			if (fotos[i].isFile()) {
				Foto foto = new Foto();
				foto.load(fotos[i]);
				vector.addElement(foto);
			}
		}
	}
	
	public void createPricelist(String basedir) {
		String verzendkosten = "";
		String priceList = "";
		String descriptionList = "";
		String imageURL = "";
		String detailURL = "";

		String kledingstukken = "";
		String ontwerpen = "";
		String shirtkleuren = "";

		for (Enumeration e = codeTabelKledingstuk.getEnumeration(); e
				.hasMoreElements();) {
			CodeObject code = (CodeObject) e.nextElement();
			kledingstukken += "$this->kledingstukken [\"" + code.code
					+ "\"]=\"" + code.omschrijving + "\";\n";
		}
		for (Enumeration e = codeTabelKledingontwerp.getEnumeration(); e
				.hasMoreElements();) {
			CodeObject code = (CodeObject) e.nextElement();
			kledingstukken += "$this->ontwerpen  [\"" + code.code + "\"]=\""
					+ code.key + "\";\n";
		}
		for (Enumeration e = codeTabelKledingkleur.getEnumeration(); e
				.hasMoreElements();) {
			CodeObject code = (CodeObject) e.nextElement();
			kledingstukken += "$this->shirtkleuren  [\"" + code.code + "\"]=\""
					+ code.key + "\";\n";
		}

		for (Enumeration e = prijslijstTabel.items.elements(); e
				.hasMoreElements();) {
			PrijslijstObject prijs = (PrijslijstObject) e.nextElement();
			priceList += "$this->pricelist[\"" + prijs.ID + "\"]="
					+ prijs.price + ";\n";
			verzendkosten += "$this->verzendkosten[\"" + prijs.ID + "\"]="
					+ prijs.verzendkosten + ";\n";
			descriptionList += "$this->descriptionlist[\"" + prijs.ID
					+ "\"]=\"" + prijs.omschrijving + "\";\n";
			imageURL += "$this->imageURLlist[\"" + prijs.ID + "\"]=\""
					+ prijs.imageURL + "\";\n";
			detailURL += "$this->detailURLlist[\"" + prijs.ID + "\"]=\""
					+ prijs.detailURL + "\";\n";
		}

		String script = Util.getFileLine(new File(basedir+ "\\templates\\cart.inc"));
		script = script.replace("%PRICES%", priceList);
		script = script.replace("%DESCRIPTIONS%", descriptionList);
		script = script.replace("%VERZENDKOSTEN%", verzendkosten);
		script = script.replace("%IMAGE_URL%", imageURL);
		script = script.replace("%DETAIL_URL%", detailURL);
		script = script.replace("%KLEDINGSTUKKEN%", kledingstukken);
		script = script.replace("%ONTWERPEN%", ontwerpen);
		script = script.replace("%SHIRTKLEUREN%", shirtkleuren);

		Util.setFileLine(basedir + "\\html\\scripts\\cart.inc",
				script);
	}

	public void buildProductLijst(String basedir, String shirtNaam, 
			String omschrijving, String rdppCode, String[] kleuren,
			String[] maten, Vector allProducts) throws Exception {
		CodeObject shirt = codeTabelKledingstuk.getObject(shirtNaam);
		if (shirt == null) {
			throw new Exception("shirt " + shirtNaam + " bestaat niet!");
		}
		for (String kleur : kleuren) {
			for (String maat : maten) {
				CodeObject kleurObject = codeTabelKledingkleur.getObject(kleur);
				if (kleurObject == null) {
					throw new Exception("kleur " + kleur + " bestaat niet!");
					
				}
				String id = shirt.code + "." + kleurObject.code + "." +maat;
				String dir = basedir + "\\html\\admin\\data\\productcodes_kledingstukken\\" + id;
				new File(dir).mkdirs();
				Util.setFileLine(dir + "\\omschrijving.txt", omschrijving + ","
						+ kleur + "," + maat);
				Util.setFileLine(dir + "\\shirtomschrijving.txt", omschrijving);
				Util.setFileLine(dir + "\\kleur.txt", kleur);
				Util.setFileLine(dir + "\\maat.txt", maat);
				Util.setFileLine(dir + "\\rdppcode.txt", rdppCode);
				allProducts.addElement(id);
			}
		}
	}
	
	
	public void loadData(String basedir, boolean testdata) {
		try {
			if (!Data.data.loaded) {
				Data.data.loaded = true;
//				String dataDir = dbTextField.getText() + "\\data";
//				if (testdataCheckBox.isSelected()) {
//					dataDir = dbTextField.getText() + "\\data-test";
//				}
				String dataDir = basedir + "\\data";
				if (testdata) {
					dataDir = basedir + "\\data-test";
				}

				codeTabelKledingontwerp = new CodeTable(dataDir
						+ "\\codetabel-kledingontwerp.txt");
				codeTabelKledingstuk = new CodeTable(dataDir
						+ "\\codetabel-kledingstukken.txt");
				codeTabelKledingkleur = new CodeTable(dataDir
						+ "\\codetabel-kledingkleur.txt");
				codeTabelKledingtype = new CodeTable(dataDir
						+ "\\codetabel-kledingtype.txt");
				codeTabelDoeksoort = new CodeTable(dataDir
						+ "\\codetabel-doeksoort.txt");
				codeTabelKledingmaat = new CodeTable(dataDir
						+ "\\codetabel-kledingmaat.txt");
				codeTabelDoek = new CodeTable(dataDir
						+ "\\codetabel-schilderij.txt");
				codeTabelRepromaat = new CodeTable(dataDir
						+ "\\codetabel-reproductiemaat.txt");
				codeTabelTas = new CodeTable(dataDir + "\\codetabel-tas.txt");
				codeTabelTasSoort = new CodeTable(dataDir
						+ "\\codetabel-tassoort.txt");
				prijslijstTabel = new PrijslijstTable(dataDir
						+ "\\prijslijstTabel.txt");

				loadPrijslijst(dataDir + "\\prijslijst.txt");
				loadVerzendkostenPrijslijst(dataDir + "\\verzendkosten.txt");

				loadTassen(dataDir + "\\tassen", Data.data);
				loadDoeken(dataDir + "\\schilderijen", Data.data);
				loadFotos(dataDir + "\\fotos", Data.data.fotos);

				loadKleding(dataDir + "\\kleding", Data.data);

				/*
				 * loadShirts(dataDir + "\\kleding\\dames",
				 * Data.data.damesShirts, "dames"); loadShirts(dataDir +
				 * "\\kleding\\heren", Data.data.herenShirts, "heren");
				 * loadShirts(dataDir + "\\kleding\\kleuter",
				 * Data.data.kleuterShirts, "kleuter"); loadShirts(dataDir +
				 * "\\kleding\\kinder", Data.data.kinderShirts, "kinder");
				 * loadShirts(dataDir + "\\kleding\\baby", Data.data.babyShirts,
				 * "baby");
				 */
				codeTabelKledingontwerp.save();
				codeTabelKledingstuk.save();
				codeTabelKledingkleur.save();
				codeTabelKledingtype.save();
				codeTabelKledingmaat.save();
				codeTabelDoek.save();
				codeTabelRepromaat.save();
				codeTabelTas.save();
				prijslijstTabel.save();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	
	public void buildAllproductlijsten(String basedir, boolean testdata) {
		Data.data.loadData(basedir,testdata);
		try {
			Vector allProducts = new Vector();

			buildProductLijst(basedir,"damesshirts", "Dames t-shirt",
					"H281", new String[] { "zwart", "grijs", "donkerblauw",
							"blauw", "lichtblauw", "rood", "oranje", "geel",
							"bruin", "groen" }, new String[] { "xs", "s", "m",
							"l", "xl" }, allProducts);

			buildProductLijst(basedir,"damestruieneffen", 
					"Dames trui effen", "G18500", new String[] { "bruin",
							"rood", "lichtblauw", "groen" }, new String[] {
							"s", "m", "l", "xl", "xxl" }, allProducts);

			buildProductLijst(basedir,"damestruien2kleur", 
					"Dames trui 2-kleuren", "F424", new String[] { "blauw",
							"grijs" },
					new String[] { "xs", "s", "m", "l", "xl" }, allProducts);

			buildProductLijst(basedir,"herenshirtsslimfit", 
					"Heren slimfit shirt", "H210", new String[] { "zwart",
							"grijs", "donkerblauw", "blauw", "lichtblauw",
							"rood", "oranje", "geel", "bruin", "groen" },
					new String[] { "s", "m", "l", "xl", "xxl" }, allProducts);

			buildProductLijst(basedir,"herenshirtsregular", 
					"Heren regular shirt", "H142", new String[] { "zwart",
							"grijs", "donkerblauw", "blauw", "lichtblauw",
							"rood", "oranje", "geel", "bruin", "groen" },
					new String[] { "s", "m", "l", "xl", "xxl" }, allProducts);

			buildProductLijst(basedir,"kleutershirts", "Kleuter shirt",
					"H903", new String[] { "grijs", "blauw", "rood", "roze",
							"geel" }, new String[] { "m", "l", "xl" },
					allProducts);

			buildProductLijst(basedir,"babyrompers",
					"Baby rompertje", "H904", new String[] { "roze", "blauw",
							"geel" }, new String[] { "s", "m", "l", "xl" },
					allProducts);

			buildProductLijst(basedir,"babyshirts", "Baby shirt",
					"H901", new String[] { "roze", "blauw", "geel" },
					new String[] { "s", "m", "l", "xl" }, allProducts);

			buildProductLijst(basedir,"herentruieneffen", 
					"Heren trui effen", "G18500", new String[] { "lichtblauw",
							"bruin", "rood" }, new String[] { "xs", "s", "m",
							"l", "xl", "xxl" }, allProducts);

			buildProductLijst(basedir,"herentruien2kleur", 
					"Heren trui 2-kleuren", "F424", new String[] { "blauw",
							"grijs" }, new String[] { "xs", "s", "m", "l",
							"xl", "xxl" }, allProducts);

			buildProductLijst(basedir,"kindershirts", 
					"Kinder/Junior t-shirt", "H142K", new String[] { "grijs",
							"donkerblauw", "blauw", "lichtblauw", "rood",
							"oranje", "geel", "bruin", "groen" }, new String[] {
							"xs", "s", "m", "l", "xl" }, allProducts);

			String producten = "";
			for (Object id : allProducts) {
				producten += (String) id + "\n";
			}
			Util.setFileLine(basedir + "\\html\\admin\\data\\productcodes_kledingstukken\\products.txt", producten.trim());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
