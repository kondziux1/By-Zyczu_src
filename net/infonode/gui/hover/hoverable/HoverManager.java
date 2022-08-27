package net.infonode.gui.hover.hoverable;

import java.awt.AWTEvent;
import java.awt.AWTPermission;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import net.infonode.gui.ComponentUtil;
import net.infonode.util.ArrayUtil;

public class HoverManager {
   private static HoverManager INSTANCE = new HoverManager();
   private final HierarchyListener hierarchyListener = new HoverManager$1(this);
   private final MouseInputAdapter mouseAdapter = new HoverManager$3(this);
   private final HashSet hoverableComponents = new HashSet();
   private final ArrayList enteredComponents = new ArrayList();
   private boolean enabled = true;
   private boolean hasPermission = true;
   private boolean active = true;
   private boolean gotEnterAfterExit = false;
   private boolean isDrag = false;
   private final AWTEventListener eventListener = new HoverManager$4(this);

   private void eventDispatched(AWTEvent var1) {
      if (var1.getSource() instanceof Component && var1 instanceof MouseEvent) {
         MouseEvent var2 = (MouseEvent)var1;
         if (var2.getID() == 501 || var2.getID() == 502) {
            this.handleButtonEvent(var2);
         } else if (var2.getID() == 504 || var2.getID() == 503) {
            this.handleEnterEvent(var2);
         } else if (var2.getID() == 505) {
            this.handleExitEvent(var2);
         } else if (var2.getID() == 506) {
            this.isDrag = true;
         }
      }

   }

   private void handleButtonEvent(MouseEvent var1) {
      if (var1.getID() == 501 && var1.getButton() == 1) {
         this.enabled = false;
         this.isDrag = false;
      } else if (!this.enabled && var1.getID() == 502) {
         this.enabled = true;
         if (this.isDrag) {
            Component var2 = ComponentUtil.getTopLevelAncestor((Component)var1.getSource());
            if (var2 == null) {
               this.exitAll();
            } else if (!((Component)var1.getSource()).contains(var1.getPoint())) {
               Point var3 = SwingUtilities.convertPoint((Component)var1.getSource(), var1.getPoint(), var2);
               if (!var2.contains(var3.x, var3.y)) {
                  this.exitAll();
               } else if (var2 instanceof Container) {
                  SwingUtilities.invokeLater(new HoverManager$5(this, var3, var2));
               }
            }
         }
      }

   }

   private void handleEnterEvent(MouseEvent var1) {
      this.gotEnterAfterExit = true;
      ArrayList var2 = new ArrayList(this.enteredComponents);
      ArrayList var3 = new ArrayList();

      for(Object var4 = (Component)var1.getSource(); var4 != null; var4 = ((Component)var4).getParent()) {
         if (this.hoverableComponents.contains(var4)) {
            var2.remove(var4);
            var3.add(var4);
         }
      }

      if (var3.size() > 0) {
         Object[] var5 = var3.toArray();

         for(int var6 = var5.length - 1; var6 >= 0; --var6) {
            if (!((Hoverable)var5[var6]).acceptHover(var3)) {
               var3.remove(var5[var6]);
               var2.add(var5[var6]);
            }
         }
      }

      for(int var7 = var2.size() - 1; var7 >= 0; --var7) {
         this.dispatchExit((Hoverable)var2.get(var7));
      }

      for(int var8 = var3.size() - 1; var8 >= 0; --var8) {
         this.dispatchEnter((Hoverable)var3.get(var8));
      }

   }

   private void handleExitEvent(MouseEvent var1) {
      this.gotEnterAfterExit = false;
      SwingUtilities.invokeLater(new HoverManager$7(this));
   }

   public static HoverManager getInstance() {
      return INSTANCE;
   }

   private HoverManager() {
      super();

      try {
         SecurityManager var1 = System.getSecurityManager();
         if (var1 != null) {
            var1.checkPermission(new AWTPermission("listenToAllAWTEvents"));
         }
      } catch (SecurityException var2) {
         this.hasPermission = false;
      }

   }

   private void exitAll() {
      this.gotEnterAfterExit = false;
      Object[] var1 = this.enteredComponents.toArray();

      for(int var2 = var1.length - 1; var2 >= 0; --var2) {
         this.dispatchExit((Hoverable)var1[var2]);
      }

   }

   public void init() {
      this.gotEnterAfterExit = false;
      this.isDrag = false;
      this.enabled = true;
   }

   public void setEventListeningActive(boolean var1) {
      this.active = var1;
   }

   public void dispatchEvent(MouseEvent var1) {
      this.eventDispatched(var1);
   }

   private void addHoverListeners(Hoverable var1) {
      if (this.hoverableComponents.add(var1)) {
         Component var2 = (Component)var1;
         var2.addMouseListener(this.mouseAdapter);
         var2.addMouseMotionListener(this.mouseAdapter);
         if (this.active && this.hoverableComponents.size() == 1) {
            try {
               Toolkit.getDefaultToolkit().addAWTEventListener(this.eventListener, 48L);
               this.hasPermission = true;
            } catch (SecurityException var4) {
               this.hasPermission = false;
            }
         }
      }

   }

   private void removeHoverListeners(Hoverable var1) {
      if (this.hoverableComponents.remove(var1)) {
         ((Component)var1).removeMouseListener(this.mouseAdapter);
         ((Component)var1).removeMouseMotionListener(this.mouseAdapter);
         this.dispatchExit(var1);
         if (this.hasPermission && this.hoverableComponents.size() == 0) {
            Toolkit.getDefaultToolkit().removeAWTEventListener(this.eventListener);
         }
      }

   }

   public void addHoverable(Hoverable var1) {
      if (var1 instanceof Component) {
         Component var2 = (Component)var1;
         if (ArrayUtil.contains(var2.getHierarchyListeners(), this.hierarchyListener)) {
            return;
         }

         var2.addHierarchyListener(this.hierarchyListener);
         if (var2.isShowing()) {
            this.addHoverListeners(var1);
         }
      }

   }

   public void removeHoverable(Hoverable var1) {
      Component var2 = (Component)var1;
      var2.removeHierarchyListener(this.hierarchyListener);
      this.removeHoverListeners(var1);
   }

   public boolean isHovered(Hoverable var1) {
      return this.enteredComponents.contains(var1);
   }

   public boolean isEventListeningActive() {
      return this.active && this.hasPermission;
   }

   private void dispatchEnter(Hoverable var1) {
      if (this.enabled && !this.enteredComponents.contains(var1)) {
         this.enteredComponents.add(var1);
         var1.hoverEnter();
      }

   }

   private void dispatchExit(Hoverable var1) {
      if (this.enabled && this.enteredComponents.remove(var1)) {
         var1.hoverExit();
      }

   }
}
