package pl.zyczu.minecraft.launcher.repo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import pl.zyczu.minecraft.launcher.Minecraft;

public class Repository {
   private String name;
   private URL url;
   private String id;
   private HashMap<Mod, Short> modyNaTaWersje;
   private HashMap<Mod, ArrayList<File>> plikiDoPobrania;
   private java.io.File directory = null;

   public Repository(String nazwa, String u, String ajdi) {
      super();
      this.modyNaTaWersje = new HashMap(20);
      this.plikiDoPobrania = new HashMap();
      this.name = nazwa;

      try {
         this.url = new URL(u);
      } catch (MalformedURLException var5) {
         Minecraft.log.severe("Nieprawidłowy URL w repozytorium!");
         var5.printStackTrace();
      }

      this.id = ajdi;
   }

   public URL getPath() {
      return this.url;
   }

   public String getName() {
      return this.name;
   }

   public String getId() {
      return this.id;
   }

   public void addMod(Mod theValue1, String theValue2, ArrayList<File> theValue3) {
      this.modyNaTaWersje.put(theValue1, new Short(theValue2));
      this.plikiDoPobrania.put(theValue1, theValue3);
   }

   public boolean containsMod(Mod other) {
      return this.modyNaTaWersje.containsKey(other);
   }

   public short getModLatestVersion(Mod mod) {
      return this.modyNaTaWersje.get(mod);
   }

   public short getModInstalledVersion(Mod mod) {
      java.io.File versionFile = new java.io.File(this.getModDirectory(mod), "version");
      if (!versionFile.exists()) {
         return 0;
      } else {
         try {
            BufferedReader br = new BufferedReader(new FileReader(versionFile));
            String liczbaStr = br.readLine();
            br.close();
            return new Short(liczbaStr);
         } catch (Exception var5) {
            return 0;
         }
      }
   }

   public void updateModToLatestVersion(Mod mod) {
      java.io.File versionFile = new java.io.File(this.getModDirectory(mod), "version");
      if (versionFile.exists()) {
         versionFile.delete();
      }

      try {
         BufferedWriter bw = new BufferedWriter(new FileWriter(versionFile));
         bw.write(String.valueOf(this.getModLatestVersion(mod)));
         bw.close();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public java.io.File getDirectory() {
      if (this.directory == null) {
         java.io.File p = new java.io.File(Minecraft.getInstance().getCacheDirectory(), this.id);
         if (!p.exists()) {
            p.mkdirs();
         }

         this.directory = p;
      }

      return this.directory;
   }

   public java.io.File getModDirectory(Mod m) {
      java.io.File f = new java.io.File(this.getDirectory(), m.getId());
      if (!f.exists()) {
         f.mkdirs();
      }

      return f;
   }

   public short getFilesCount(Mod mod) {
      short localVariable1 = 0;
      ArrayList<File> localVariable2 = (ArrayList)this.plikiDoPobrania.get(mod);
      return localVariable2 == null ? localVariable1 : (short)localVariable2.size();
   }

   private List<File> getFiles(Mod mod) {
      ArrayList<File> localVariable1 = (ArrayList)this.plikiDoPobrania.get(mod);
      return localVariable1 == null ? null : Collections.unmodifiableList(localVariable1);
   }

   public List<File> þ(Mod next) {
      return this.getFiles(next);
   }

   public String getShortName() {
      String[] tablica = this.getName().split(" ");
      return tablica[tablica.length - 1];
   }

   public List<File> getModFiles(Mod mod) {
      return Collections.unmodifiableList((List)this.plikiDoPobrania.get(mod));
   }
}
