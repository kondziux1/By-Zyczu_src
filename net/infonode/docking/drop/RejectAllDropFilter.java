package net.infonode.docking.drop;

public class RejectAllDropFilter implements DropFilter {
   private static final long serialVersionUID = 1L;
   public static RejectAllDropFilter INSTANCE = new RejectAllDropFilter();

   private RejectAllDropFilter() {
      super();
   }

   public boolean acceptDrop(DropInfo var1) {
      return false;
   }
}
