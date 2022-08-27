package pl.zyczu.minecraft.launcher.repo;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Mod> {
   public PriorityComparator() {
      super();
   }

   public int compare(Mod arg0, Mod arg1) {
      if (arg0.getPriority() < arg1.getPriority()) {
         return 1;
      } else {
         return arg0.getPriority() > arg1.getPriority() ? -1 : 0;
      }
   }
}
