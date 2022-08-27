package de.javasoft.plaf.synthetica.styles;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.Box.Filler;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthStyle;

public class ToolBarStyle extends StyleWrapper {
   private static final String ORG_FILLERS = "Synthetica.toolBarStyle.orgFillers";
   private static final String ORG_X_ALIGNMENTS = "Synthetica.toolBarStyle.orgXAlignments";
   private static final String ORG_Y_ALIGNMENTS = "Synthetica.toolBarStyle.orgYAlignments";
   private static final String ORG_MARGIN = "Synthetica.toolBarStyle.orgMargin";
   private static final String ORG_LAYOUT_MANAGER = "Synthetica.toolBarStyle.orgLayoutManager";
   private static final String ORIENTATION_LISTENER = "Synthetica.toolBarStyle.orientationListener";
   private static final String CONTAINER_LISTENER = "Synthetica.toolBarStyle.containerListener";
   private static ToolBarStyle instance = new ToolBarStyle();

   private ToolBarStyle() {
      super();
   }

   public static SynthStyle getStyle(SynthStyle var0, JComponent var1, Region var2) {
      if (SyntheticaLookAndFeel.getStyleName(var1) == null) {
         instance.setStyle(var0);
         return instance;
      } else {
         ToolBarStyle var3 = new ToolBarStyle();
         var3.setStyle(var0);
         return var3;
      }
   }

   @Override
   public void installDefaults(SynthContext var1) {
      this.synthStyle.installDefaults(var1);
      JToolBar var2 = (JToolBar)var1.getComponent();
      if (var2.getClientProperty("Synthetica.toolBarStyle.orgFillers") == null) {
         var2.putClientProperty("Synthetica.toolBarStyle.orgFillers", new HashMap());
      }

      HashMap var3 = (HashMap)var2.getClientProperty("Synthetica.toolBarStyle.orgFillers");
      if (var2.getClientProperty("Synthetica.toolBarStyle.orgXAlignments") == null) {
         var2.putClientProperty("Synthetica.toolBarStyle.orgXAlignments", new HashMap());
      }

      HashMap var4 = (HashMap)var2.getClientProperty("Synthetica.toolBarStyle.orgXAlignments");
      if (var2.getClientProperty("Synthetica.toolBarStyle.orgYAlignments") == null) {
         var2.putClientProperty("Synthetica.toolBarStyle.orgYAlignments", new HashMap());
      }

      HashMap var5 = (HashMap)var2.getClientProperty("Synthetica.toolBarStyle.orgYAlignments");
      var2.putClientProperty("Synthetica.toolBarStyle.orgLayoutManager", var2.getLayout());
      var2.putClientProperty("Synthetica.toolBarStyle.orgMargin", var2.getMargin());
      var2.addPropertyChangeListener(this.getOrientationListener(var2));
      Component[] var6 = var2.getComponents();

      for(int var7 = 0; var7 < var6.length; ++var7) {
         Component var8 = var6[var7];
         var4.put(var8, var8.getAlignmentX());
         var5.put(var8, var8.getAlignmentY());
         if (var8 instanceof Filler) {
            var3.put(var7, var8);
         }

         ((JComponent)var8).setAlignmentX(0.5F);
         ((JComponent)var8).setAlignmentY(0.5F);
      }

      var2.addContainerListener(this.getContainerListener(var2));
      this.setLayoutManager(var2);
   }

   private PropertyChangeListener getOrientationListener(final JToolBar var1) {
      PropertyChangeListener var2 = (PropertyChangeListener)var1.getClientProperty("Synthetica.toolBarStyle.orientationListener");
      if (var2 == null) {
         var2 = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent var1x) {
               String var2 = var1x.getPropertyName();
               if (var2.equals("orientation")) {
                  ToolBarStyle.this.setLayoutManager(var1);
               } else if (var2.equals("floatable")) {
                  ToolBarStyle.this.setMargin(var1, var1x.getNewValue());
               } else if (var2.equals("componentOrientation")) {
                  ToolBarStyle.this.setMargin(var1, var1.isFloatable());
               }

            }
         };
         var1.putClientProperty("Synthetica.toolBarStyle.orientationListener", var2);
      }

      return var2;
   }

   private ContainerListener getContainerListener(final JToolBar var1) {
      ContainerListener var2 = (ContainerListener)var1.getClientProperty("Synthetica.toolBarStyle.containerListener");
      if (var2 == null) {
         var2 = new ContainerListener() {
            public void componentAdded(ContainerEvent var1x) {
               HashMap var2 = (HashMap)var1.getClientProperty("Synthetica.toolBarStyle.orgFillers");
               HashMap var3 = (HashMap)var1.getClientProperty("Synthetica.toolBarStyle.orgXAlignments");
               HashMap var4 = (HashMap)var1.getClientProperty("Synthetica.toolBarStyle.orgYAlignments");
               Component var5 = var1x.getChild();
               var3.put(var5, var5.getAlignmentX());
               var4.put(var5, var5.getAlignmentY());
               if (var5 instanceof Filler) {
                  var2.put(var1.getComponents().length - 1, var5);
               }

               if (var5 instanceof JComponent) {
                  ((JComponent)var5).setAlignmentX(0.5F);
                  ((JComponent)var5).setAlignmentY(0.5F);
               }

            }

            public void componentRemoved(ContainerEvent var1x) {
               HashMap var2 = (HashMap)var1.getClientProperty("Synthetica.toolBarStyle.orgXAlignments");
               var2.remove(var1x.getChild());
               HashMap var3 = (HashMap)var1.getClientProperty("Synthetica.toolBarStyle.orgYAlignments");
               var3.remove(var1x.getChild());
            }
         };
         var1.putClientProperty("Synthetica.toolBarStyle.containerListener", var2);
      }

      return var2;
   }

   @Override
   public void uninstallDefaults(SynthContext var1) {
      this.synthStyle.uninstallDefaults(var1);
      JToolBar var2 = (JToolBar)var1.getComponent();
      var2.removePropertyChangeListener(this.getOrientationListener(var2));
      var2.removeContainerListener(this.getContainerListener(var2));
      HashMap var3 = (HashMap)var2.getClientProperty("Synthetica.toolBarStyle.orgFillers");
      HashMap var4 = (HashMap)var2.getClientProperty("Synthetica.toolBarStyle.orgXAlignments");
      HashMap var5 = (HashMap)var2.getClientProperty("Synthetica.toolBarStyle.orgYAlignments");

      for(Entry var7 : var3.entrySet()) {
         int var8 = var7.getKey();
         Component var9 = (Component)var7.getValue();
         var2.remove(var8);
         var2.add(var9, var8);
      }

      for(Entry var12 : var4.entrySet()) {
         JComponent var14 = (JComponent)var12.getKey();
         float var16 = var12.getValue();
         var14.setAlignmentX(var16);
      }

      for(Entry var13 : var5.entrySet()) {
         JComponent var15 = (JComponent)var13.getKey();
         float var17 = var13.getValue();
         var15.setAlignmentY(var17);
      }

      var2.setMargin((Insets)var2.getClientProperty("Synthetica.toolBarStyle.orgMargin"));
      var2.setLayout((LayoutManager)var2.getClientProperty("Synthetica.toolBarStyle.orgLayoutManager"));
      var2.putClientProperty("Synthetica.toolBarStyle.orgFillers", null);
      var2.putClientProperty("Synthetica.toolBarStyle.orgXAlignments", null);
      var2.putClientProperty("Synthetica.toolBarStyle.orgYAlignments", null);
      var2.putClientProperty("Synthetica.toolBarStyle.orgMargin", null);
      var2.putClientProperty("Synthetica.toolBarStyle.orgLayoutManager", null);
      var2.putClientProperty("Synthetica.toolBarStyle.orientationListener", null);
      var2.putClientProperty("Synthetica.toolBarStyle.containerListener", null);
   }

   private void setLayoutManager(JToolBar var1) {
      if (!SyntheticaLookAndFeel.getBoolean("Synthetica.toolBar.useSynthLayoutManager", var1)) {
         HashMap var2 = (HashMap)var1.getClientProperty("Synthetica.toolBarStyle.orgFillers");
         var1.removeContainerListener(this.getContainerListener(var1));
         Component[] var3 = var1.getComponents();
         Object var4 = null;
         ToolBarStyle.SyntheticaToolBarLayout var9;
         if (var1.getOrientation() == 0) {
            var9 = new ToolBarStyle.SyntheticaToolBarLayout(var1, 2);

            for(int var5 = 0; var5 < var3.length; ++var5) {
               if (var3[var5] instanceof Filler && this.fillerIsGlue((Filler)var3[var5])) {
                  var1.remove(var3[var5]);
                  var1.add(Box.createHorizontalGlue(), var5);
               } else if (var3[var5] instanceof Filler && !this.fillerIsGlue((Filler)var3[var5])) {
                  var1.remove(var3[var5]);
                  Component var6 = (Component)var2.get(var5);
                  Dimension var7 = var6.getPreferredSize();
                  int var8 = Math.max(var7.width, var7.height);
                  var1.add(Box.createHorizontalStrut(var8), var5);
               }
            }

            this.setMargin(var1, var1.isFloatable());

            for(int var10 = 0; var10 < var3.length; ++var10) {
               ((JComponent)var3[var10]).setAlignmentY(0.5F);
            }
         } else {
            var9 = new ToolBarStyle.SyntheticaToolBarLayout(var1, 3);

            for(int var11 = 0; var11 < var3.length; ++var11) {
               if (var3[var11] instanceof Filler && this.fillerIsGlue((Filler)var3[var11])) {
                  var1.remove(var3[var11]);
                  var1.add(Box.createVerticalGlue(), var11);
               } else if (var3[var11] instanceof Filler && !this.fillerIsGlue((Filler)var3[var11])) {
                  var1.remove(var3[var11]);
                  Component var14 = (Component)var2.get(var11);
                  Dimension var15 = var14.getPreferredSize();
                  int var16 = Math.max(var15.width, var15.height);
                  var1.add(Box.createVerticalStrut(var16), var11);
               }
            }

            this.setMargin(var1, var1.isFloatable());

            for(int var12 = 0; var12 < var3.length; ++var12) {
               ((JComponent)var3[var12]).setAlignmentX(0.5F);
            }
         }

         LayoutManager var13 = var1.getLayout();
         if (var13 != null && (var13.getClass().getName().contains("SynthToolBar") || var13 instanceof ToolBarStyle.SyntheticaToolBarLayout)) {
            var1.setLayout(var9);
         }

         var1.addContainerListener(this.getContainerListener(var1));
      }
   }

   private void setMargin(JToolBar var1, boolean var2) {
      if (!SyntheticaLookAndFeel.getBoolean("Synthetica.toolBar.useSynthLayoutManager", var1)) {
         Object var3 = null;
         Insets var5;
         if (var1.getOrientation() == 0) {
            var5 = (Insets)SyntheticaLookAndFeel.getInsets("Synthetica.toolBar.margin.horizontal", var1).clone();
            if (var2 && var1.getComponentOrientation().isLeftToRight()) {
               var5.left += SyntheticaLookAndFeel.getInt("Synthetica.toolBar.handle.size", var1, 0);
            } else if (var2) {
               var5.right += SyntheticaLookAndFeel.getInt("Synthetica.toolBar.handle.size", var1, 0);
            }
         } else {
            var5 = (Insets)SyntheticaLookAndFeel.getInsets("Synthetica.toolBar.margin.vertical", var1).clone();
            if (var2) {
               var5.top += SyntheticaLookAndFeel.getInt("Synthetica.toolBar.handle.size", var1, 0);
            }
         }

         Insets var4 = (Insets)var1.getClientProperty("Synthetica.toolBarStyle.orgMargin");
         if (var4 != null && var5 != null) {
            var5.bottom += var4.bottom;
            var5.left += var4.left;
            var5.top += var4.top;
            var5.right += var4.right;
            var1.setMargin(var5);
         }

      }
   }

   private boolean fillerIsGlue(Filler var1) {
      Dimension var2 = new Dimension(0, 0);
      return var1.getMinimumSize().equals(var2) && var1.getPreferredSize().equals(var2);
   }

   private static class SyntheticaToolBarLayout extends BoxLayout {
      private static final long serialVersionUID = 6806218476946368742L;

      SyntheticaToolBarLayout(Container var1, int var2) {
         super(var1, var2);
      }
   }
}
