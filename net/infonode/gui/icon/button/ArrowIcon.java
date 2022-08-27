package net.infonode.gui.icon.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import net.infonode.util.Direction;

public class ArrowIcon extends AbstractButtonIcon {
   private Direction direction;

   public ArrowIcon(Direction var1) {
      super();
      this.direction = var1;
   }

   public ArrowIcon(Color var1, Direction var2) {
      super(var1);
      this.direction = var2;
   }

   public ArrowIcon(Color var1, int var2, Direction var3) {
      super(var1, var2);
      this.direction = var3;
   }

   public ArrowIcon(int var1, Direction var2) {
      this(var1, var2, true);
   }

   public ArrowIcon(int var1, Direction var2, boolean var3) {
      super(var1, var3);
      this.direction = var2;
   }

   public Direction getDirection() {
      return this.direction;
   }

   protected void paintIcon(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      int var7 = var5 - var3 + 1 + (var5 - var3 + 1) % 2 - 1;
      int var8 = (this.direction.isHorizontal() ? var3 : var4)
         + (this.direction != Direction.RIGHT && this.direction != Direction.DOWN ? (var7 - (var7 + 1) / 2) / 2 : (var7 + 1) / 4);
      int var9 = this.direction.isHorizontal() ? var4 : var3;
      int[] var10 = this.direction != Direction.DOWN && this.direction != Direction.RIGHT
         ? new int[]{var8 + var7 / 2 + 1, var8 + var7 / 2 + 1, var8 - (this.direction == Direction.UP ? 1 : 0)}
         : new int[]{var8, var8, var8 + var7 / 2 + 1};
      int[] var11 = new int[]{var9 + (this.direction == Direction.DOWN ? 0 : -1), var9 + var7 + (this.direction == Direction.UP ? 1 : 0), var9 + var7 / 2};
      var2.fillPolygon(this.direction.isHorizontal() ? var10 : var11, this.direction.isHorizontal() ? var11 : var10, 3);
   }
}
