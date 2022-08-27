package pl.zyczu.minecraft.launcher.repo;

import java.util.Comparator;

public class DependencyComparator implements Comparator<Mod> {
   public DependencyComparator() {
      super();
   }

   public int compare(Mod o1, Mod o2) {
      if (o1.depends(o2)) {
         return 1;
      } else {
         return o2.depends(o1) ? -1 : 0;
      }
   }
}
