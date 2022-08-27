package pl.zyczu.minecraft.launcher.repo;

public enum InstallMethod {
   UNKNOWN,
   COPY_TO_ROOT,
   COPY_TO_MODS,
   EXTRACT_ROOT,
   MINECRAFT_JAR;

   private InstallMethod() {
   }
}
