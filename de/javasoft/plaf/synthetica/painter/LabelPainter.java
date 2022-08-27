package de.javasoft.plaf.synthetica.painter;

import de.javasoft.plaf.synthetica.GraphicsUtils;
import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;
import java.lang.reflect.Field;
import javax.swing.CellRendererPane;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.synth.ColorType;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.plaf.synth.SynthStyle;
import javax.swing.table.JTableHeader;
import javax.swing.tree.DefaultTreeCellRenderer;

public class LabelPainter extends SyntheticaComponentPainter {
   public static final String UI_KEY = "Synthetica.LabelPainter";
   private static boolean cellRendererSelectionBackgroundEnabled;
   private static boolean listSelectionBackgroundEnabled;
   private static boolean treeSelectionBackgroundEnabled;
   private static boolean tableSelectionBackgroundEnabled;
   private static SynthStyle listStyle;
   private static int listHash;
   private static SynthStyle treeStyle;
   private static int treeHash;
   private static SynthStyle tableStyle;
   private static int tableHash;

   protected LabelPainter() {
      super();
   }

   public static LabelPainter getInstance() {
      return getInstance(null);
   }

   public static LabelPainter getInstance(SynthContext var0) {
      SyntheticaComponentPainter var1 = (SyntheticaComponentPainter)instances.get(getPainterClassName(var0, LabelPainter.class, "Synthetica.LabelPainter"));
      if (var1 == null) {
         var1 = getInstance(var0, LabelPainter.class, "Synthetica.LabelPainter");
      }

      return (LabelPainter)var1;
   }

   public static void reinitialize() {
      cellRendererSelectionBackgroundEnabled = SyntheticaLookAndFeel.getBoolean("Synthetica.cellRenderer.selectionBackground.enabled", null, false);
      listSelectionBackgroundEnabled = SyntheticaLookAndFeel.getBoolean("Synthetica.list.labelCellRenderer.selectionBackground.enabled", null, true);
      treeSelectionBackgroundEnabled = SyntheticaLookAndFeel.getBoolean("Synthetica.tree.labelCellRenderer.selectionBackground.enabled", null, true);
      tableSelectionBackgroundEnabled = SyntheticaLookAndFeel.getBoolean("Synthetica.table.labelCellRenderer.selectionBackground.enabled", null, true);
      listHash = 0;
      treeHash = 0;
      tableHash = 0;
   }

   public void paintLabelBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintLabelBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      JLabel var7 = (JLabel)var1.getComponent();
      Container var8 = var7.getParent();
      Container var9 = var8 == null ? null : var8.getParent();
      String var10 = var7.getName();
      if (var8 instanceof CellRendererPane) {
         if (var10 != null && var10.startsWith("ComboBox.") && !(var9 instanceof JComboBox)) {
            if (cellRendererSelectionBackgroundEnabled && var9 != null) {
               JComponent var22 = (JComponent)var9;
               SynthStyle var27 = SynthLookAndFeel.getStyle(var22, Region.LIST);
               SynthContext var32 = new SynthContext(var22, Region.LIST, var27, 512);
               String var36 = SyntheticaLookAndFeel.getString("Synthetica.comboBox.listSelectionBackground", var7);
               if (var36 != null && var7.getBackground().equals(var27.getColor(var32, ColorType.TEXT_BACKGROUND))) {
                  Insets var39 = SyntheticaLookAndFeel.getInsets("Synthetica.comboBox.listSelectionBackground.insets", var7, false);
                  ImagePainter var40 = new ImagePainter(var2, var3, var4, var5, var6, var36, var39, var39, 0, 0);
                  var40.draw();
               } else {
                  Color var38 = var2.getColor();
                  var2.setColor(var7.getBackground());
                  var2.fillRect(var3, var4, var5, var6);
                  var2.setColor(var38);
               }
            } else {
               Color var21 = var2.getColor();
               var2.setColor(var7.getBackground());
               var2.fillRect(var3, var4, var5, var6);
               var2.setColor(var21);
            }
         } else if (cellRendererSelectionBackgroundEnabled
            && listSelectionBackgroundEnabled
            && (this.isListCellRenderer(var10) || JAVA5)
            && var9 instanceof JList) {
            JList var20 = (JList)var9;
            int var26 = this.getHashCode(var20);
            if (var26 != listHash) {
               listHash = var26;
               listStyle = SynthLookAndFeel.getStyle(var20, Region.LIST);
            }

            SynthStyle var31 = listStyle;
            SynthContext var35 = new SynthContext(var20, Region.LIST, var31, 512);
            if (var7.getBackground().equals(var31.getColor(var35, ColorType.TEXT_BACKGROUND))) {
               ((GraphicsUtils)var31.getGraphicsUtils(var35)).paintListCellRendererSelectionBackground(var20, var7, var2, var3, var4, var5, var6);
            }
         } else if (cellRendererSelectionBackgroundEnabled
            && treeSelectionBackgroundEnabled
            && (this.isTreeCellRenderer(var10) || JAVA5)
            && var9 instanceof JTree
            && var7 instanceof DefaultTreeCellRenderer) {
            JTree var19 = (JTree)var9;
            int var25 = this.getHashCode(var19);
            if (var25 != treeHash) {
               treeHash = var25;
               treeStyle = SynthLookAndFeel.getStyle(var19, Region.TREE);
            }

            SynthStyle var30 = treeStyle;
            SynthContext var34 = new SynthContext(var19, Region.TREE, var30, 512);

            try {
               Field var15 = DefaultTreeCellRenderer.class.getDeclaredField("selected");
               var15.setAccessible(true);
               boolean var16 = var15.get(var7);
               if (var16) {
                  ((GraphicsUtils)var30.getGraphicsUtils(var34)).paintTreeCellRendererSelectionBackground(var19, var7, var2, var3, var4, var5, var6);
               }
            } catch (Exception var17) {
               throw new RuntimeException(var17);
            }
         } else if (cellRendererSelectionBackgroundEnabled && tableSelectionBackgroundEnabled && "Table.cellRenderer".equals(var10) && var9 instanceof JTable) {
            JTable var18 = (JTable)var9;
            int var24 = this.getHashCode(var18);
            if (var24 != tableHash) {
               tableHash = var24;
               tableStyle = SynthLookAndFeel.getStyle(var18, Region.TABLE);
            }

            SynthStyle var29 = tableStyle;
            SynthContext var14 = new SynthContext(var18, Region.TABLE, var29, 512);
            if (var7.getBackground().equals(var29.getColor(var14, ColorType.TEXT_BACKGROUND))) {
               ((GraphicsUtils)var29.getGraphicsUtils(var14)).paintTableCellRendererSelectionBackground(var18, var7, var2, var3, var4, var5, var6);
            }
         } else if ((var10 != null && var10.startsWith("TableHeader.") || "Table.cellRenderer".equals(var10)) && var9 instanceof JTableHeader) {
            JTableHeader var11 = (JTableHeader)var9;
            var7.setFont(var11.getFont());
            if (SyntheticaLookAndFeel.getBoolean("Synthetica.tableHeader.showVerticalGrid", var11, true)) {
               Color var12 = var2.getColor();
               Color var13 = SyntheticaLookAndFeel.getColor("Synthetica.tableHeader.gridColor", var11);
               var2.setColor(var13);
               var2.drawLine(var3 + var5 - 1, var4, var3 + var5 - 1, var4 + var6 - 1);
               var2.setColor(var12);
            }

            return;
         }
      }

      if (var8 instanceof CellRendererPane && var9 instanceof JComboBox) {
         JComboBox var23 = (JComboBox)var9;
         boolean var28 = SyntheticaLookAndFeel.isOpaque(var23);
         Color var33 = SyntheticaLookAndFeel.get("Synthetica.comboBox.border.locked", var23) == null
            ? SyntheticaLookAndFeel.getColor("Synthetica.comboBox.focused.backgroundColor", var23)
            : null;
         if (var23.isEnabled() && !var23.isEditable() && var33 == null || var28 && var33 == null) {
            return;
         }

         if (var23.hasFocus() && !var23.isEditable()) {
            if (!(var23.getBackground() instanceof UIResource)
               && SyntheticaLookAndFeel.getBoolean("Synthetica.comboBox.useBackgroundColorAsFocus", var23, false)) {
               return;
            }

            Color var37 = var2.getColor();
            var2.setColor(var33);
            var2.fillRect(var3, var4, var5, var6);
            var2.setColor(var37);
         }
      }

   }

   private boolean isListCellRenderer(String var1) {
      return var1 == null ? false : var1.startsWith("List.cellRenderer");
   }

   private boolean isTreeCellRenderer(String var1) {
      return var1 == null ? false : var1.startsWith("Tree.cellRenderer");
   }

   private int getHashCode(JComponent var1) {
      int var2 = var1.hashCode();
      String var3 = SyntheticaLookAndFeel.getStyleName(var1);
      return var3 == null ? var2 : 31 * var2 + var3.hashCode();
   }
}
