package pl.zyczu.minecraft.launcher;

public enum ServiceStatus {
   UNKNOWN,
   CONNECTING,
   SYNCING,
   DOWNLOADING,
   DLOAD_REPO,
   DLOAD_FILES,
   DLOAD_MODPACKS,
   SAVING,
   INTERPRETING,
   READY,
   ERROR,
   FIRSTRUN_INIT,
   UPDATE_ZYCZU,
   UPDATE_MINECRAFTSP_EXE;

   private ServiceStatus() {
   }
}
