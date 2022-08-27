package pl.zyczu.minecraft.launcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import pl.zyczu.minecraft.launcher.repo.Mod;
import pl.zyczu.minecraft.launcher.repo.ModPack;
import pl.zyczu.minecraft.launcher.repo.Repository;
import pl.zyczu.minecraft.launcher.repo.RepositoryManager;

public class UserModPacks {
   private File plik = null;
   private ArrayList<ModPack> userPacks = null;
   private static UserModPacks instance = null;

   public UserModPacks() {
      super();
      this.userPacks = new ArrayList();
      File f = new File(Minecraft.getWorkingDirectory(), "UserModPacks.cfg");
      this.plik = f;
      if (!f.exists()) {
         Minecraft.log.info("Tworzę nowy plik z paczkami modów użytkownika");

         try {
            f.createNewFile();
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      } else {
         this.load();
      }

   }

   public void addNewPack(String nazwa, Repository wersja) {
      RepositoryManager repositoryManager = RepositoryManager.getInstance();
      ModPack paczka = new ModPack();
      paczka.setName(nazwa);
      paczka.attachRepository(wersja);
      paczka.addMod(repositoryManager.getModById("minecraft"));
      this.userPacks.add(paczka);
   }

   public List<ModPack> getModPacks() {
      return Collections.unmodifiableList(this.userPacks);
   }

   public void remove(ModPack currentModPack) {
      this.userPacks.remove(currentModPack);
   }

   private void load() {
      Minecraft.log.log("UserModPacks", "Ładowanie modów użytkownika...");

      try {
         RepositoryManager repositoryManager = RepositoryManager.getInstance();
         BufferedReader fr = new BufferedReader(new FileReader(this.plik));
         String linia = null;
         this.userPacks.clear();

         while((linia = fr.readLine()) != null) {
            if (linia.length() >= 3) {
               ModPack paczka = new ModPack();
               paczka.setName(linia);
               Minecraft.log.debug("Załadowano mod użytkownika: " + linia);
               paczka.attachRepository(repositoryManager.getRepositoryById(fr.readLine()));
               String[] mody = fr.readLine().split("\\|");

               for(int i = 0; i < mody.length; ++i) {
                  paczka.addMod(repositoryManager.getModById(mody[i]));
               }

               this.userPacks.add(paczka);
            }
         }

         fr.close();
      } catch (IOException var7) {
         var7.printStackTrace();
      } catch (NullPointerException var8) {
         var8.printStackTrace();
      }

   }

   public void save() {
      Minecraft.log.log("UserModPacks", "Zapisywanie modów użytkownika...");

      try {
         BufferedWriter fw = new BufferedWriter(new FileWriter(this.plik));

         for(ModPack paczka : this.userPacks) {
            fw.write(paczka.getUserName() + "\n");
            fw.write(paczka.getRepository().getId() + "\n");
            Iterator<Mod> inny = paczka.getMods().iterator();
            StringBuilder modyBuilder = new StringBuilder();
            boolean bylo = false;

            while(inny.hasNext()) {
               if (!bylo) {
                  bylo = true;
                  modyBuilder.append(((Mod)inny.next()).getId());
               } else {
                  modyBuilder.append("|" + ((Mod)inny.next()).getId());
               }
            }

            String mody = modyBuilder.toString();
            fw.write(mody + "\n");
         }

         fw.close();
      } catch (IOException var8) {
         var8.printStackTrace();
      }

   }

   public int getUserModPacksCount() {
      return this.userPacks.size();
   }

   public static UserModPacks getInstance() {
      if (instance == null) {
         instance = new UserModPacks();
      }

      return instance;
   }

   public static void shutdownInstance() {
      instance = null;
   }
}
