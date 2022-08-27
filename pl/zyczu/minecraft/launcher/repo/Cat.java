package pl.zyczu.minecraft.launcher.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cat {
   private String nazwa;
   private String opis;
   private ArrayList<Mod> modyWtejKategorii = null;

   public Cat() {
      super();
      this.modyWtejKategorii = new ArrayList();
   }

   public void setName(String tagValue1) {
      this.nazwa = tagValue1;
   }

   public void setText(String tagValue1) {
      this.opis = tagValue1;
   }

   public String getName() {
      return this.nazwa;
   }

   public String getText() {
      return this.opis;
   }

   public void addMod(Mod modDoDodania) {
      this.modyWtejKategorii.add(modDoDodania);
   }

   public int getModCount() {
      return this.modyWtejKategorii.size();
   }

   public List<Mod> getMods() {
      return Collections.unmodifiableList(this.modyWtejKategorii);
   }
}
