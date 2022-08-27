package net.infonode.gui.panel;

import java.awt.Component;
import net.infonode.gui.layout.DirectionLayout;
import net.infonode.util.Direction;

public class DirectionPanel extends BaseContainer {
   public DirectionPanel() {
      this(Direction.RIGHT, 0);
   }

   public DirectionPanel(Direction var1, int var2) {
      super(new DirectionLayout(var1, var2));
      this.setForcedOpaque(false);
   }

   public DirectionPanel(Component[] var1) {
      this(Direction.RIGHT, var1);
   }

   public DirectionPanel(int var1, Component[] var2) {
      this(Direction.RIGHT, var1, var2);
   }

   public DirectionPanel(Direction var1, Component[] var2) {
      this(var1, 0, var2);
   }

   public DirectionPanel(Direction var1, int var2, Component[] var3) {
      this(var1, var2);

      for(int var4 = 0; var4 < var3.length; ++var4) {
         this.add(var3[var4]);
      }

   }

   public void setDirection(Direction var1) {
      ((DirectionLayout)this.getLayout()).setDirection(var1);
   }
}
