package pl.zyczu.minecraft.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Properties {
   java.util.Properties p = null;
   private static Properties instance = null;

   public Properties() {
      super();
      File f = new File(Minecraft.getWorkingDirectory(), "MinecraftByZyczu.cfg");
      this.p = new java.util.Properties();

      try {
         FileInputStream in = new FileInputStream(f);
         this.p.load(in);
      } catch (IOException var3) {
         Minecraft.log.info("Tworzę nowy plik konfiguracyjny");
         this.loadDefaults();
      }

   }

   private void loadDefaults() {
      this.p.setProperty("v", "1");
      this.p.setProperty("ram", "1024");
      this.p.setProperty("nick", "twoj_nick");
      this.p.setProperty("repo_version", "0");
      this.p.setProperty("wyjdz", "tak");
      this.p.setProperty("current_modpack", "default");
      this.store();
   }

   private void store() {
      try {
         FileOutputStream out = new FileOutputStream(new File(Minecraft.getWorkingDirectory(), "MinecraftByZyczu.cfg"));
         this.p.store(out, "Minecraft Launcher by Zyczu\nPlik konfiguracyjny");
      } catch (IOException var2) {
         Minecraft.log.severe("Nie udało się zapisać pliku konfiguracyjnego!");
         System.exit(1);
      }

   }

   public String get(String name) {
      return this.p.getProperty(name, null);
   }

   public String get(String name, String defaultValue) {
      String result = this.p.getProperty(name);
      return result == null ? defaultValue : result;
   }

   public int getNumber(String name) {
      return new Integer(this.p.getProperty(name, "0"));
   }

   public void set(String name, String value) {
      this.p.setProperty(name, value);
      this.store();
   }

   public void set(String name, long value) {
      StringBuilder bld = new StringBuilder();
      bld.append(value);
      this.p.setProperty(name, bld.toString());
      this.store();
   }

   public static Properties getInstance() {
      if (instance == null) {
         instance = new Properties();
      }

      return instance;
   }

   public static void shutdownInstance() {
      instance = null;
   }
}
