package net.infonode.docking.util;

import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import net.infonode.docking.AbstractTabWindow;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.docking.SplitWindow;
import net.infonode.docking.TabWindow;
import net.infonode.docking.WindowBar;
import net.infonode.docking.WindowPopupMenuFactory;
import net.infonode.docking.action.CloseOthersWindowAction;
import net.infonode.docking.action.CloseWithAbortWindowAction;
import net.infonode.docking.action.DockWithAbortWindowAction;
import net.infonode.docking.action.MaximizeWithAbortWindowAction;
import net.infonode.docking.action.MinimizeWithAbortWindowAction;
import net.infonode.docking.action.RestoreWithAbortWindowAction;
import net.infonode.docking.action.UndockWithAbortWindowAction;
import net.infonode.gui.icon.button.ArrowIcon;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;
import net.infonode.util.Direction;

public final class WindowMenuUtil {
   private static final Icon[] ARROW_ICONS = new Icon[4];

   private WindowMenuUtil() {
      super();
   }

   private static AbstractTabWindow getTabWindowFor(DockingWindow var0) {
      return (AbstractTabWindow)(
         var0 instanceof AbstractTabWindow
            ? var0
            : (var0.getWindowParent() != null && var0.getWindowParent() instanceof AbstractTabWindow ? var0.getWindowParent() : null)
      );
   }

   private static JMenu getMoveToMenuItems(DockingWindow var0) {
      JMenu var1 = new JMenu("Move to Window Bar");
      if (var0.isMinimizable()) {
         RootWindow var2 = var0.getRootWindow();
         Direction[] var3 = Direction.getDirections();

         for(int var4 = 0; var4 < 4; ++var4) {
            Direction var5 = var3[var4];
            if (!DockingUtil.isAncestor(var2.getWindowBar(var5), var0) && var2.getWindowBar(var5).isEnabled()) {
               var1.add(new JMenuItem(var5.getName(), ARROW_ICONS[var4])).addActionListener(new WindowMenuUtil$1(var2, var5, var0));
            }
         }
      }

      return var1;
   }

   private static void addWindowMenuItems(JPopupMenu var0, DockingWindow var1) {
      var0.add(UndockWithAbortWindowAction.INSTANCE.getAction(var1).toSwingAction());
      var0.add(DockWithAbortWindowAction.INSTANCE.getAction(var1).toSwingAction());
      var0.add(RestoreWithAbortWindowAction.INSTANCE.getAction(var1).toSwingAction());
      var0.add(MinimizeWithAbortWindowAction.INSTANCE.getAction(var1).toSwingAction());
      if (var1 instanceof TabWindow) {
         var0.add(MaximizeWithAbortWindowAction.INSTANCE.getAction(var1).toSwingAction());
      }

      var0.add(CloseWithAbortWindowAction.INSTANCE.getAction(var1).toSwingAction());
      if (var1.getWindowParent() instanceof AbstractTabWindow) {
         var0.add(CloseOthersWindowAction.INSTANCE.getAction(var1).toSwingAction());
      }

      JMenu var2 = getMoveToMenuItems(var1);
      if (var2.getItemCount() > 0) {
         var0.add(var2);
      }

   }

   private static void addNewViewMenuItems(JPopupMenu var0, DockingWindow var1, ViewFactoryManager var2) {
      ViewFactory[] var3 = var2.getViewFactories();
      if (var3.length != 0) {
         JMenu var4 = new JMenu("Show View");

         for(int var5 = 0; var5 < var3.length; ++var5) {
            ViewFactory var6 = var3[var5];
            var4.add(new JMenuItem(var6.getTitle(), var6.getIcon())).addActionListener(new WindowMenuUtil$2(var6, var1));
         }

         var0.add(var4);
      }
   }

   private static void addTabOrientationMenuItems(JPopupMenu var0, DockingWindow var1) {
      AbstractTabWindow var2 = getTabWindowFor(var1);
      if (var2 != null && !(var2 instanceof WindowBar)) {
         JMenu var3 = new JMenu("Tab Orientation");
         TabbedPanelProperties var4 = var2.getTabWindowProperties().getTabbedPanelProperties();
         Direction[] var5 = Direction.getDirections();

         for(int var6 = 0; var6 < var5.length; ++var6) {
            Direction var7 = var5[var6];
            JMenuItem var8 = var3.add(new JMenuItem(var7.getName(), ARROW_ICONS[var6]));
            var8.setEnabled(var7 != var4.getTabAreaOrientation());
            var8.addActionListener(new WindowMenuUtil$3(var2, var7));
         }

         var0.add(var3);
      }
   }

   private static void addTabDirectionMenuItems(JPopupMenu var0, DockingWindow var1) {
      AbstractTabWindow var2 = getTabWindowFor(var1);
      if (var2 != null) {
         JMenu var3 = new JMenu("Tab Direction");
         TitledTabProperties var4 = TitledTabProperties.getDefaultProperties();
         var4.addSuperObject(var2.getTabWindowProperties().getTabProperties().getTitledTabProperties());
         Direction[] var5 = Direction.getDirections();

         for(int var6 = 0; var6 < var5.length; ++var6) {
            Direction var7 = var5[var6];
            if (var7 != Direction.LEFT) {
               JMenuItem var8 = var3.add(new JMenuItem(var7.getName(), ARROW_ICONS[var6]));
               var8.setEnabled(var7 != var4.getNormalProperties().getDirection());
               var8.addActionListener(new WindowMenuUtil$4(var2, var7));
            }
         }

         var0.add(var3);
      }
   }

   private static void addSplitWindowMenuItems(JPopupMenu var0, DockingWindow var1) {
      if (var1 instanceof SplitWindow) {
         JMenu var2 = new JMenu("Split Window");
         var2.add("25%").addActionListener(new WindowMenuUtil$5(var1));
         var2.add("50%").addActionListener(new WindowMenuUtil$6(var1));
         var2.add("75%").addActionListener(new WindowMenuUtil$7(var1));
         var2.addSeparator();
         var2.add("Flip Orientation").addActionListener(new WindowMenuUtil$8(var1));
         var2.add("Mirror").addActionListener(new WindowMenuUtil$9(var1));
         var0.add(var2);
      }

   }

   public static WindowPopupMenuFactory createWindowMenuFactory(ViewFactoryManager var0, boolean var1) {
      return createWindowMenuFactory(var0, var1, true);
   }

   public static WindowPopupMenuFactory createWindowMenuFactory(ViewFactoryManager var0, boolean var1, boolean var2) {
      return new WindowMenuUtil$10(var1, var2, var0);
   }

   static {
      Direction[] var0 = Direction.getDirections();

      for(int var1 = 0; var1 < var0.length; ++var1) {
         ARROW_ICONS[var1] = new ArrowIcon(11, var0[var1]);
      }

   }
}
