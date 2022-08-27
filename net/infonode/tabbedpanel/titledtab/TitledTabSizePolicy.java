package net.infonode.tabbedpanel.titledtab;

import net.infonode.util.Enum;

public final class TitledTabSizePolicy extends Enum {
   private static final long serialVersionUID = -7834501681762485226L;
   public static final TitledTabSizePolicy EQUAL_SIZE = new TitledTabSizePolicy(0, "Equal Size");
   public static final TitledTabSizePolicy INDIVIDUAL_SIZE = new TitledTabSizePolicy(1, "Individual Size");
   public static final TitledTabSizePolicy[] SIZE_POLICIES = new TitledTabSizePolicy[]{EQUAL_SIZE, INDIVIDUAL_SIZE};

   private TitledTabSizePolicy(int var1, String var2) {
      super(var1, var2);
   }

   public static TitledTabSizePolicy[] getSizePolicies() {
      return (TitledTabSizePolicy[])SIZE_POLICIES.clone();
   }
}
