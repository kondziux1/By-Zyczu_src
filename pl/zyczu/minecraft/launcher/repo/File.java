package pl.zyczu.minecraft.launcher.repo;

public class File {
   private String nazwa;
   private InstallMethod metoda;
   private String sha1;

   public File() {
      super();
   }

   public void setName(String theValue1) {
      this.nazwa = theValue1;
   }

   public void setInstallMethod(String theValue1) {
      if (theValue1.equalsIgnoreCase("cp2root")) {
         this.setInstallMethod(InstallMethod.COPY_TO_ROOT);
      } else if (theValue1.equalsIgnoreCase("cp2mods")) {
         this.setInstallMethod(InstallMethod.COPY_TO_MODS);
      } else if (theValue1.equalsIgnoreCase("cp2jar")) {
         this.setInstallMethod(InstallMethod.MINECRAFT_JAR);
      } else {
         this.setInstallMethod(InstallMethod.EXTRACT_ROOT);
      }

   }

   public void setInstallMethod(InstallMethod theEnum1) {
      this.metoda = theEnum1;
   }

   public void setHash(String theValue1) {
      this.sha1 = theValue1;
   }

   public String getHash() {
      return this.sha1;
   }

   public InstallMethod getInstallMethod() {
      return this.metoda;
   }

   public String getName() {
      return this.nazwa;
   }
}
