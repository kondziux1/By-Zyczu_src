package net.infonode.util;

import java.io.IOException;
import java.io.ObjectInputStream;

public final class Direction extends Enum {
   private static final long serialVersionUID = 1L;
   public static final Direction UP = new Direction(0, "Up", false);
   public static final Direction RIGHT = new Direction(1, "Right", true);
   public static final Direction DOWN = new Direction(2, "Down", false);
   public static final Direction LEFT = new Direction(3, "Left", true);
   public static final Direction[] DIRECTIONS = new Direction[]{UP, RIGHT, DOWN, LEFT};
   private transient Direction rotateCW;
   private transient boolean isHorizontal;

   private Direction(int var1, String var2, boolean var3) {
      super(var1, var2);
      this.isHorizontal = var3;
   }

   public Direction getNextCW() {
      return this.rotateCW;
   }

   public Direction getNextCCW() {
      return this.rotateCW.rotateCW.rotateCW;
   }

   public boolean isHorizontal() {
      return this.isHorizontal;
   }

   public Direction getOpposite() {
      return this.getNextCW().getNextCW();
   }

   public static Direction[] getDirections() {
      return (Direction[])DIRECTIONS.clone();
   }

   public static Direction decode(ObjectInputStream var0) throws IOException {
      return (Direction)decode(
         class$net$infonode$util$Direction == null
            ? (class$net$infonode$util$Direction = class$("net.infonode.util.Direction"))
            : class$net$infonode$util$Direction,
         var0
      );
   }

   static {
      UP.rotateCW = RIGHT;
      RIGHT.rotateCW = DOWN;
      DOWN.rotateCW = LEFT;
      LEFT.rotateCW = UP;
   }
}
