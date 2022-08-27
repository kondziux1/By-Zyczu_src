package pl.zyczu.minecraft.launcher.gui;

public class MoreViewOption {
   private String tytul = null;
   private String opis = null;
   private Loadable widok = null;

   public MoreViewOption(String a, String b, Loadable l) {
      super();
      this.tytul = a;
      this.opis = b;
      this.widok = l;
   }

   public String getTitle() {
      return this.tytul;
   }

   public String getText() {
      return this.getDescription();
   }

   public String getName() {
      return this.getTitle();
   }

   public String getDescription() {
      return this.opis;
   }

   public Loadable getViewToLoad() {
      return this.getLoadable();
   }

   public Loadable getLoadable() {
      return this.widok;
   }
}
