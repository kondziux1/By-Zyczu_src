package net.infonode.docking.mouse;

import java.awt.event.MouseEvent;
import java.io.Serializable;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.action.CloseWithAbortWindowAction;
import net.infonode.docking.action.DockingWindowAction;
import net.infonode.gui.mouse.MouseButtonListener;

public class DockingWindowActionMouseButtonListener implements MouseButtonListener, Serializable {
   private static final long serialVersionUID = 1L;
   private static final int MODIFIER_MASK = 960;
   public static final MouseButtonListener MIDDLE_BUTTON_CLOSE_LISTENER = new DockingWindowActionMouseButtonListener(2, CloseWithAbortWindowAction.INSTANCE);
   private int eventId;
   private int button;
   private int keyMask;
   private DockingWindowAction action;
   private boolean consumeEvent;

   public DockingWindowActionMouseButtonListener(int var1, DockingWindowAction var2) {
      this(500, var1, var2);
   }

   public DockingWindowActionMouseButtonListener(int var1, int var2, DockingWindowAction var3) {
      this(var1, var2, 0, var3, false);
   }

   public DockingWindowActionMouseButtonListener(int var1, int var2, int var3, DockingWindowAction var4, boolean var5) {
      super();
      this.eventId = var1;
      this.button = var2;
      this.keyMask = var3;
      this.action = var4;
      this.consumeEvent = var5;
   }

   public void mouseButtonEvent(MouseEvent var1) {
      if (!var1.isConsumed()) {
         int var2 = var1.getModifiersEx() & 960;
         if (var1.getButton() == 2) {
            var2 &= -513;
         }

         if (var1.getButton() == 3) {
            var2 &= -257;
         }

         if (var1.getID() == this.eventId && var1.getButton() == this.button && var2 == this.keyMask) {
            DockingWindow var3 = (DockingWindow)var1.getSource();
            this.action.perform(var3);
            if (this.consumeEvent) {
               var1.consume();
            }
         }

      }
   }
}
