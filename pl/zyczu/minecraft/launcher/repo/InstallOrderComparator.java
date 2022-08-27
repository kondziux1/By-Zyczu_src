package pl.zyczu.minecraft.launcher.repo;

import java.util.Comparator;

public class InstallOrderComparator implements Comparator<Mod> {
   public InstallOrderComparator() {
      super();
   }

   public int compare(Mod o1, Mod o2) {
      if (o1.installAfter(o2)) {
         return -1;
      } else {
         return o2.installAfter(o1) ? 1 : 0;
      }
   }
}
