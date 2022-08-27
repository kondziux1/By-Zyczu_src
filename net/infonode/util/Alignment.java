package net.infonode.util;

import java.io.IOException;
import java.io.ObjectInputStream;

public final class Alignment extends Enum {
   private static final long serialVersionUID = 4945539895437047593L;
   public static final Alignment LEFT = new Alignment(0, "Left");
   public static final Alignment CENTER = new Alignment(1, "Center");
   public static final Alignment RIGHT = new Alignment(2, "Right");
   public static final Alignment TOP = new Alignment(3, "Top");
   public static final Alignment BOTTOM = new Alignment(4, "Bottom");
   public static final Alignment[] ALIGNMENTS = new Alignment[]{LEFT, CENTER, RIGHT, TOP, BOTTOM};
   public static final Alignment[] HORIZONTAL_ALIGNMENTS = new Alignment[]{LEFT, CENTER, RIGHT};
   public static final Alignment[] VERTICAL_ALIGNMENTS = new Alignment[]{TOP, CENTER, BOTTOM};

   private Alignment(int var1, String var2) {
      super(var1, var2);
   }

   public static Alignment[] getAlignments() {
      return (Alignment[])ALIGNMENTS.clone();
   }

   public static Alignment[] getHorizontalAlignments() {
      return (Alignment[])HORIZONTAL_ALIGNMENTS.clone();
   }

   public static Alignment[] getVerticalAlignments() {
      return (Alignment[])VERTICAL_ALIGNMENTS.clone();
   }

   public static Alignment decode(ObjectInputStream var0) throws IOException {
      return (Alignment)decode(
         class$net$infonode$util$Alignment == null
            ? (class$net$infonode$util$Alignment = class$("net.infonode.util.Alignment"))
            : class$net$infonode$util$Alignment,
         var0
      );
   }
}
