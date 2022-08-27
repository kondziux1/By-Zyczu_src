package pl.zyczu.minecraft.launcher.repo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mod {
   private String nazwa = null;
   private String autor = null;
   private String www = null;
   private String opis = null;
   private URL obrazek = null;
   private String id = null;
   private short priorytet = 0;
   private boolean standalone = false;
   private List<String> zaleznosci = new ArrayList();
   private List<String> konflikty = new ArrayList();
   private List<String> zastapienia = new ArrayList();
   private List<String> instalki = new ArrayList();

   public Mod() {
      super();
   }

   public void setName(String tagValue) {
      this.nazwa = tagValue;
   }

   public String getName() {
      return this.nazwa;
   }

   public void setAuthor(String tagValue) {
      this.autor = tagValue;
   }

   public String getAuthor() {
      return this.autor;
   }

   public void setWebsite(String tagValue) {
      this.www = tagValue;
   }

   public String getWebsite() {
      return this.www;
   }

   public void setText(String tagValue) {
      this.opis = tagValue;
   }

   public String getText() {
      return this.opis;
   }

   public void setImage(String tagValue) {
      if (tagValue == null) {
         this.obrazek = null;
      } else {
         try {
            this.obrazek = new URL(tagValue);
         } catch (MalformedURLException var3) {
            var3.printStackTrace();
         }
      }

   }

   public URL getImage() {
      return this.obrazek;
   }

   public void setPriority(String tagValue) {
      if (tagValue == null) {
         this.priorytet = 0;
      } else {
         try {
            this.priorytet = new Short(tagValue);
         } catch (NumberFormatException var3) {
            this.priorytet = 0;
         }
      }

   }

   public void setStandalone(boolean stndaln) {
      this.standalone = stndaln;
   }

   public boolean isStandalone() {
      return this.standalone;
   }

   public void addDependency(String tagValue) {
      this.zaleznosci.add(tagValue);
   }

   public void addConflict(String tagValue) {
      this.konflikty.add(tagValue);
   }

   public void addReplacement(String tagValue) {
      this.zastapienia.add(tagValue);
   }

   public void addInstallAfter(String tagValue) {
      this.instalki.add(tagValue);
   }

   public boolean conflicts(Mod inny) {
      return this.konflikty.contains(inny.getId());
   }

   public boolean installAfter(Mod inny) {
      return this.instalki.contains(inny.getId());
   }

   public List<String> getDependencies() {
      return Collections.unmodifiableList(this.zaleznosci);
   }

   public List<String> getReplacements() {
      return Collections.unmodifiableList(this.zastapienia);
   }

   public List<String> getConflicts() {
      return Collections.unmodifiableList(this.konflikty);
   }

   public boolean depends(Mod inny) {
      return this.zaleznosci.contains(inny.getId());
   }

   public boolean replaces(Mod m) {
      return this.zastapienia.contains(m.getId());
   }

   public boolean isReplacedBy(Mod m) {
      try {
         for(String x : RepositoryManager.getInstance().getModReplacedBy(m)) {
            if (x.equalsIgnoreCase(m.getId())) {
               return true;
            }
         }

         return false;
      } catch (NullPointerException var4) {
         return false;
      }
   }

   public void setId(String i) {
      this.id = i;
   }

   public String getId() {
      return this.id;
   }

   public short getPriority() {
      return this.priorytet;
   }
}
