package net.infonode.docking.drop;

public class AcceptAllDropFilter implements DropFilter {
   private static final long serialVersionUID = 1L;
   public static AcceptAllDropFilter INSTANCE = new AcceptAllDropFilter();

   private AcceptAllDropFilter() {
      super();
   }

   public boolean acceptDrop(DropInfo var1) {
      return true;
   }
}
