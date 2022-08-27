package net.infonode.tabbedpanel.titledtab;

import net.infonode.util.Enum;

public final class TitledTabBorderSizePolicy extends Enum {
   private static final long serialVersionUID = 5570620861429391549L;
   public static final TitledTabBorderSizePolicy EQUAL_SIZE = new TitledTabBorderSizePolicy(0, "Equal Size");
   public static final TitledTabBorderSizePolicy INDIVIDUAL_SIZE = new TitledTabBorderSizePolicy(1, "Individual Size");
   public static final TitledTabBorderSizePolicy[] SIZE_POLICIES = new TitledTabBorderSizePolicy[]{EQUAL_SIZE, INDIVIDUAL_SIZE};

   private TitledTabBorderSizePolicy(int var1, String var2) {
      super(var1, var2);
   }

   public static TitledTabBorderSizePolicy[] getSizePolicies() {
      return (TitledTabBorderSizePolicy[])SIZE_POLICIES.clone();
   }
}
