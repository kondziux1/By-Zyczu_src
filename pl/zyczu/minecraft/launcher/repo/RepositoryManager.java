package pl.zyczu.minecraft.launcher.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.util.System;

public class RepositoryManager {
   private Map<String, Repository> repozytoria;
   private Map<String, Cat> kategorie;
   private Map<String, Mod> mody;
   private Map<String, ArrayList<String>> replacements;
   private Map<String, ModPack> modpacks;
   private List<Cat> listaKategorii;
   private List<ModPack> modpaki;
   private List<Repository> posortowaneRepozytoria;
   private static RepositoryManager instance = null;
   private java.io.File basepath = null;

   private RepositoryManager() {
      super();
      this.repozytoria = new HashMap();
      this.mody = new HashMap();
      this.replacements = new HashMap();
      this.kategorie = new HashMap();
      this.modpacks = new HashMap();
      this.listaKategorii = new LinkedList();
      this.modpaki = new ArrayList();
      this.posortowaneRepozytoria = new ArrayList();
   }

   public List<ModPack> getModPacks() {
      return this.modpaki;
   }

   public List<Cat> getOrderedCatList() {
      return this.listaKategorii;
   }

   public Collection<Cat> getCatList() {
      return this.kategorie.values();
   }

   public Collection<Repository> getRepositories() {
      return this.repozytoria.values();
   }

   public Collection<Repository> getRepositoriesInOrder() {
      return Collections.unmodifiableList(this.posortowaneRepozytoria);
   }

   public Cat getCatById(String id) {
      return (Cat)this.kategorie.get(id);
   }

   public Mod getModById(String id) {
      return (Mod)this.mody.get(id);
   }

   public Collection<Mod> getModList() {
      return Collections.unmodifiableCollection(this.mody.values());
   }

   public Repository getRepositoryById(String id) {
      return (Repository)this.repozytoria.get(id);
   }

   public Repository getRepositoryByOrdialNumber(int selectedIndex) {
      return (Repository)this.posortowaneRepozytoria.get(selectedIndex);
   }

   public List<String> getModReplacedBy(Mod m) {
      return this.replacements.containsKey(m.getId()) ? Collections.unmodifiableList((List)this.replacements.get(m.getId())) : null;
   }

   public boolean setup(java.io.File bp) throws Exception {
      this.basepath = bp;
      this.setupRepo();
      this.setupCat();
      this.setupMods();
      this.setupFiles();
      this.setupPacks();
      return true;
   }

   private void setupRepo() throws Exception {
      Minecraft.log.log("RepositoryManager", "Analizowanie repo.xml");
      Document repoXml = this.getXmlFile("repo");
      NodeList repoList = repoXml.getElementsByTagName("repo");

      for(int i = 0; i < repoList.getLength(); ++i) {
         Element ten = (Element)repoList.item(i);
         Repository repo = new Repository(ten.getAttribute("name"), ten.getChildNodes().item(0).getNodeValue(), ten.getAttribute("id"));
         this.repozytoria.put(ten.getAttribute("id"), repo);
         this.posortowaneRepozytoria.add(repo);
      }

      Minecraft.log.log("RepositoryManager", "Załadowano " + repoList.getLength() + " repozytoria");
   }

   private void setupCat() throws Exception {
      Minecraft.log.log("RepositoryManager", "Analizowanie cat.xml");
      Document catXml = this.getXmlFile("cat");
      NodeList catList = catXml.getElementsByTagName("cat");

      for(int i = 0; i < catList.getLength(); ++i) {
         Element ten = (Element)catList.item(i);
         Cat kategoria = new Cat();
         kategoria.setName(ten.getAttribute("name"));
         kategoria.setText(ten.getChildNodes().item(0).getNodeValue());
         this.kategorie.put(ten.getAttribute("id"), kategoria);
         this.listaKategorii.add(kategoria);
      }

      Minecraft.log.log("RepositoryManager", "Załadowano " + catList.getLength() + " kategorii");
   }

   private void setupMods() throws Exception {
      Minecraft.log.log("RepositoryManager", "Analizowanie packages.xml");
      Document packagesXml = this.getXmlFile("packages");
      NodeList nodeList = packagesXml.getElementsByTagName("package");

      for(int i = 0; i < nodeList.getLength(); ++i) {
         Element ten = (Element)nodeList.item(i);
         String id = ten.getAttribute("id");
         Mod mod = new Mod();
         this.getCatById(ten.getAttribute("cat")).addMod(mod);
         mod.setId(id);
         mod.setName(this.getTagValue("nazwa", ten));
         mod.setAuthor(this.getTagValue("author", ten));
         mod.setWebsite(this.getTagValue("www", ten));
         mod.setText(this.getTagValue("opis", ten));
         mod.setImage(this.getTagValue("img", ten));
         mod.setPriority(this.getTagValue("priority", ten));
         mod.setStandalone(this.getTagValue("standalone", ten).equals("1"));
         NodeList dependencyList = ten.getElementsByTagName("depends");

         for(int j = 0; j < dependencyList.getLength(); ++j) {
            mod.addDependency(dependencyList.item(j).getChildNodes().item(0).getNodeValue());
         }

         NodeList conflictList = ten.getElementsByTagName("conflicts");

         for(int j = 0; j < conflictList.getLength(); ++j) {
            mod.addConflict(conflictList.item(j).getChildNodes().item(0).getNodeValue());
         }

         NodeList replacementList = ten.getElementsByTagName("replaces");

         for(int j = 0; j < replacementList.getLength(); ++j) {
            String zast = replacementList.item(j).getChildNodes().item(0).getNodeValue();
            mod.addReplacement(zast);
            this.registerReplacement(zast, id);
         }

         NodeList installList = ten.getElementsByTagName("after");

         for(int j = 0; j < installList.getLength(); ++j) {
            String zast = installList.item(j).getChildNodes().item(0).getNodeValue();
            mod.addInstallAfter(zast);
         }

         this.mody.put(id, mod);
      }

      Minecraft.log.log("RepositoryManager", "Załadowano " + nodeList.getLength() + " paczek");
   }

   private void setupFiles() throws Exception {
      Minecraft.log.log("RepositoryManager", "Analizowanie files.xml");
      Document filesXml = this.getXmlFile("files");
      NodeList packageList = filesXml.getElementsByTagName("package");
      short filecount = 0;
      String osname = System.getOperatingSystemName();

      for(int i = 0; i < packageList.getLength(); ++i) {
         Element pack = (Element)packageList.item(i);
         Mod mod = this.getModById(pack.getAttribute("id"));
         NodeList repoList = pack.getElementsByTagName("repo");

         for(int j = 0; j < repoList.getLength(); ++j) {
            Element ten = (Element)repoList.item(j);
            Repository repo = this.getRepositoryById(ten.getAttribute("id"));
            NodeList fileList = ten.getElementsByTagName("file");
            ArrayList<File> pliczki = new ArrayList(fileList.getLength());

            for(int k = 0; k < fileList.getLength(); ++k) {
               ++filecount;
               Element plik = (Element)fileList.item(k);
               File f = new File();
               f.setName(plik.getAttribute("name").replace("OSNAME", osname));
               f.setInstallMethod(plik.getAttribute("install"));
               f.setHash(plik.getAttribute("sha1"));
               pliczki.add(f);
            }

            repo.addMod(mod, ten.getAttribute("nr"), pliczki);
         }
      }

      Minecraft.log.log("RepositoryManager", "Przetworzono " + filecount + " plików.");
   }

   private void setupPacks() throws Exception {
      Minecraft.log.log("RepositoryManager", "Analizowanie modpacks.xml");
      Document modpackXml = this.getXmlFile("modpacks");
      NodeList packList = modpackXml.getElementsByTagName("pack");

      for(int i = 0; i < packList.getLength(); ++i) {
         Element ten = (Element)packList.item(i);
         ModPack paczkaModow = new ModPack();
         paczkaModow.setName(this.getTagValue("nazwa", ten));
         paczkaModow.attachRepository(this.getRepositoryById(this.getTagValue("wersja", ten)));
         NodeList modList = ten.getElementsByTagName("mod");

         for(int j = 0; j < modList.getLength(); ++j) {
            Element modElement = (Element)modList.item(j);
            paczkaModow.addMod(this.getModById(modElement.getChildNodes().item(0).getNodeValue()));
         }

         String ajdi = ten.getAttribute("id");
         paczkaModow.setId(ajdi);
         this.modpacks.put(ajdi, paczkaModow);
         this.modpaki.add(paczkaModow);
      }

      Minecraft.log.log("RepositoryManager", "Załadowano " + packList.getLength() + " paczki modów.");
   }

   private void registerReplacement(String klucz, String wartosc) {
      Minecraft.log.debug("Register replacement " + klucz + " -> " + wartosc);
      if (this.replacements.containsKey(klucz)) {
         ((ArrayList)this.replacements.get(klucz)).add(wartosc);
      } else {
         ArrayList<String> tablica = new ArrayList();
         tablica.add(wartosc);
         this.replacements.put(klucz, tablica);
      }

   }

   private Document getXmlFile(String name) throws Exception {
      java.io.File xmlFile = new java.io.File(this.basepath, "repo/" + name + ".xml");
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(xmlFile);
      doc.getDocumentElement().normalize();
      return doc;
   }

   private String getTagValue(String tag, Element element) {
      try {
         NodeList lista = element.getElementsByTagName(tag).item(0).getChildNodes();
         Node node = lista.item(0);
         return node.getNodeValue();
      } catch (Exception var5) {
         return "";
      }
   }

   public static RepositoryManager getInstance() {
      if (instance == null) {
         instance = new RepositoryManager();
      }

      return instance;
   }

   public void shutdownNow() {
      this.repozytoria = null;
      this.mody = null;
      this.replacements = null;
      this.kategorie = null;
      this.modpacks = null;
      this.listaKategorii = null;
      this.modpaki = null;
      this.posortowaneRepozytoria = null;
   }

   public static void shutdownInstance() {
      if (instance != null) {
         instance.shutdownNow();
      }

      instance = null;
   }
}
