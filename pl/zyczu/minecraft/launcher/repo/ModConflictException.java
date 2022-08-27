package pl.zyczu.minecraft.launcher.repo;

public class ModConflictException extends Exception {
   private static final long serialVersionUID = 5053023014431495604L;
   private Mod first;
   private Mod second;

   public ModConflictException(String mesydż, Mod żółf, Mod źółf) {
      super(mesydż);
      this.first = żółf;
      this.second = źółf;
   }

   public Mod getFirst() {
      return this.first;
   }

   public Mod getSecond() {
      return this.second;
   }
}
