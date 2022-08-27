package pl.zyczu.minecraft.launcher.repo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import pl.zyczu.minecraft.launcher.Minecraft;

public class ModPack {
   private String nazwa;
   private Repository wersja;
   private ArrayList<Mod> mody = new ArrayList();
   private String ajdi;

   public ModPack() {
      super();
   }

   public void setName(String theValue1) {
      this.nazwa = theValue1;
   }

   public void attachRepository(Repository repo) {
      this.wersja = repo;
   }

   public void addMod(Mod mod) {
      this.mody.add(mod);
   }

   public String getName() {
      return this.wersja.getName() + " - " + this.nazwa;
   }

   public String getUserName() {
      return this.nazwa;
   }

   public List<Mod> getMods() {
      return Collections.unmodifiableList(this.mody);
   }

   private List<Mod> getInternalMods() {
      return this.mody;
   }

   public boolean containsMod(Mod mod) {
      return this.mody.contains(mod);
   }

   public boolean containsMod(String mod) {
      return this.containsMod(RepositoryManager.getInstance().getModById(mod));
   }

   public Repository getRepository() {
      return this.wersja;
   }

   public void loadMods(ModPack other) {
      this.mody = other.mody;
   }

   public void dependencyResolver(Mod mod) throws ModConflictException {
      Iterator<Mod> it = this.mody.iterator();
      boolean dodaj = true;

      while(it.hasNext()) {
         Mod inny = (Mod)it.next();
         if (inny.conflicts(mod) || mod.conflicts(inny)) {
            throw new ModConflictException("Konflikt pomiędzy modami", mod, inny);
         }

         if (inny.replaces(mod)) {
            dodaj = false;
            Minecraft.log.info("Mod " + mod.getName() + " został pominięty, ponieważ zastępuje go " + inny.getName());
         }
      }

      if (dodaj) {
         this.addMod(mod);
         Iterator var6 = mod.getReplacements().iterator();

         while(var6.hasNext()) {
            Mod replacement = RepositoryManager.getInstance().getModById((String)var6.next());
            if (this.containsMod(replacement)) {
               this.removeSingleMod(replacement);
               Minecraft.log.info("Usuwanie moda " + replacement.getName() + " ponieważ zastępuje go " + mod.getName());
            }
         }

         var6 = mod.getDependencies().iterator();

         while(var6.hasNext()) {
            Mod zaleznosc = RepositoryManager.getInstance().getModById((String)var6.next());
            if (!this.containsMod(zaleznosc)) {
               this.dependencyResolver(zaleznosc);
            }
         }

      }
   }

   private void removeSingleMod(Mod replacement) {
      this.mody.remove(replacement);
   }

   public List<Mod> removalResolver(Mod mod) {
      Iterator<Mod> it = this.mody.iterator();
      List<Mod> l = new ArrayList();

      while(it.hasNext()) {
         Mod inny = (Mod)it.next();
         if (inny.depends(mod)) {
            List<Mod> lista = this.removalResolver(inny);
            Iterator<Mod> it2 = lista.iterator();

            while(it2.hasNext()) {
               l.add((Mod)it2.next());
            }
         }
      }

      l.add(mod);
      return l;
   }

   public List<Mod> childOnlyRemovalResolver(Mod mod) {
      Iterator<Mod> it = this.mody.iterator();
      List<Mod> l = new ArrayList();

      while(it.hasNext()) {
         Mod inny = (Mod)it.next();
         if (inny.depends(mod)) {
            List<Mod> lista = this.removalResolver(inny);
            Iterator<Mod> it2 = lista.iterator();

            while(it2.hasNext()) {
               l.add((Mod)it2.next());
            }
         }
      }

      return l;
   }

   public List<Mod> realRemovalResolver(Mod mod) {
      List<Mod> l = this.removalResolver(mod);
      Iterator<String> it = mod.getReplacements().iterator();

      while(it.hasNext()) {
         Mod m = RepositoryManager.getInstance().getModById((String)it.next());
         List<Mod> l2 = this.childOnlyRemovalResolver(m);
         Iterator<Mod> it2 = l2.iterator();

         while(it2.hasNext()) {
            l.add((Mod)it2.next());
         }
      }

      return l;
   }

   public void commitRemoval(List<Mod> mody) {
      Iterator<Mod> it = this.getInternalMods().iterator();

      while(it.hasNext()) {
         Mod m = (Mod)it.next();
         if (mody.contains(m)) {
            Minecraft.log.info("Usuwanie moda " + m.getName());
            it.remove();
         }
      }

   }

   public Mod[] getInstallOrder() {
      Mod[] tmplist = new Mod[this.mody.size()];
      Iterator<Mod> it = this.mody.iterator();

      for(int i = 0; it.hasNext(); ++i) {
         tmplist[i] = (Mod)it.next();
      }

      Arrays.sort(tmplist, new PriorityComparator());

      for(int var8 = 0; var8 < tmplist.length; ++var8) {
         for(int j = var8; j < tmplist.length; ++j) {
            if (tmplist[var8].depends(tmplist[j])) {
               Mod t = tmplist[var8];
               tmplist[var8] = tmplist[j];
               tmplist[j] = t;
            } else {
               Iterator<String> itk = tmplist[j].getReplacements().iterator();

               while(itk.hasNext()) {
                  Mod mm = RepositoryManager.getInstance().getModById((String)itk.next());
                  if (tmplist[var8].depends(mm)) {
                     Mod t = tmplist[var8];
                     tmplist[var8] = tmplist[j];
                     tmplist[j] = t;
                  }
               }
            }
         }
      }

      for(int var9 = 0; var9 < tmplist.length; ++var9) {
         for(int j = var9; j < tmplist.length; ++j) {
            if (tmplist[var9].installAfter(tmplist[j])) {
               Mod t = tmplist[var9];
               tmplist[var9] = tmplist[j];
               tmplist[j] = t;
            }
         }
      }

      return tmplist;
   }

   public void removeReplacedMods(Mod mod) {
      Iterator<Mod> it = this.getInternalMods().iterator();

      while(it.hasNext()) {
         Mod m = (Mod)it.next();
         if (mod.replaces(m)) {
            it.remove();
            Minecraft.log.info("Usuwanie moda " + m.getName() + " ponieważ zastępuje go " + mod.getName());
         }
      }

   }

   public String getId() {
      return this.ajdi;
   }

   public void setId(String newajdi) {
      this.ajdi = newajdi;
   }

   public int getModCount() {
      return this.mody.size();
   }

   public void removeAllMods() {
      this.mody = new ArrayList();
      this.mody.add(RepositoryManager.getInstance().getModById("minecraft"));
   }
}
