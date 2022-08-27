package de.javasoft.plaf.synthetica;

import de.javasoft.plaf.synthetica.painter.TablePainter;
import de.javasoft.util.JavaVersion;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.UIManager;
import javax.swing.RowSorter.SortKey;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.plaf.synth.SynthStyle;
import sun.swing.table.DefaultTableCellHeaderRenderer;

class SyntheticaHeaderRenderer extends DefaultTableCellHeaderRenderer implements UIResource {
   private static final long serialVersionUID = -4089492349514455249L;
   private JTable table;
   private int column;

   SyntheticaHeaderRenderer() {
      super();
      this.setHorizontalAlignment(SyntheticaLookAndFeel.getInt("Synthetica.tableHeader.horizontalAlignment", this, 10));
      this.setName("TableHeader.renderer");
   }

   public Component getTableCellRendererComponent(JTable var1, Object var2, boolean var3, boolean var4, int var5, int var6) {
      this.table = var1;
      this.column = var6;
      Component var7 = super.getTableCellRendererComponent(var1, var2, var3, var4, var5, var6);
      if (!var4 && var7 instanceof JComponent && var1 != null && var1.getTableHeader() != null && var7 instanceof JComponent) {
         JComponent var8 = (JComponent)var7;
         SynthStyle var9 = SynthLookAndFeel.getStyle(var1.getTableHeader(), Region.TABLE_HEADER);
         SynthContext var10 = new SynthContext(var1.getTableHeader(), Region.TABLE_HEADER, var9, 0);
         Insets var11 = var9.getInsets(var10, null);
         Border var12 = var8.getBorder();
         Border var13 = UIManager.getBorder("TableHeader.cellBorder");
         if (var13 != null) {
            if (var12 != var13) {
               var8.setBorder(var13);
            }
         } else if (var11.equals(new Insets(0, 0, 0, 0))) {
            if (var12 == null) {
               var8.setBorder(noFocusBorder);
            }
         } else {
            var8.setBorder(new EmptyBorder(var11));
         }
      }

      if (JavaVersion.JAVA6U10_OR_ABOVE && var1 != null && var1.getRowSorter() != null) {
         for(SortKey var14 : this.getSortKeys(var1)) {
            if (var14.getColumn() == var1.convertColumnIndexToModel(var6)) {
               Object var16 = null;
               Icon var17;
               switch(var14.getSortOrder()) {
                  case ASCENDING:
                     var17 = UIManager.getIcon("Table.ascendingSortIcon");
                     break;
                  case DESCENDING:
                     var17 = UIManager.getIcon("Table.descendingSortIcon");
                     break;
                  default:
                     var17 = UIManager.getIcon("Table.naturalSortIcon");
               }

               this.setIcon(var17);
            }
         }
      }

      return var7;
   }

   public void paintComponent(Graphics var1) {
      if (this.table != null) {
         byte var2 = 0;
         boolean var3 = false;
         int[] var4 = (int[])this.table.getClientProperty("SORTABLE_TABLE_SORTED_COLUMNS");
         Boolean var5 = (Boolean)this.table.getClientProperty("SORTABLE_TABLE_PAINT_SORT_BACKGROUND");
         if (var4 != null && var5 != null && var5) {
            for(int var10 : var4) {
               if (var10 == this.column) {
                  TablePainter.getInstance()
                     .paintTableHeaderCellBackground(this.table, new SyntheticaState(0), var1, 0, 0, this.getWidth(), this.getHeight(), 1);
                  var3 = true;
                  break;
               }
            }
         } else if (JavaVersion.JAVA6U10_OR_ABOVE && this.table.getRowSorter() != null) {
            for(SortKey var6 : this.getSortKeys(this.table)) {
               if (var6.getColumn() == this.table.convertColumnIndexToModel(this.column)) {
                  switch(var6.getSortOrder()) {
                     case ASCENDING:
                        var2 = 1;
                        break;
                     case DESCENDING:
                        var2 = 2;
                  }

                  TablePainter.getInstance()
                     .paintTableHeaderCellBackground(this.table, new SyntheticaState(0), var1, 0, 0, this.getWidth(), this.getHeight(), var2);
                  var3 = true;
               }
            }
         }

         if (!var3) {
            TablePainter.getInstance().paintTableHeaderCellBackground(this.table, new SyntheticaState(0), var1, 0, 0, this.getWidth(), this.getHeight(), var2);
         }
      }

      super.paintComponent(var1);
   }

   private List<SortKey> getSortKeys(JTable var1) {
      RowSorter var2 = var1.getRowSorter();
      Object var3 = var2.getSortKeys();
      if (((List)var3).size() > 0) {
         var3 = new ArrayList();
         ((List)var3).add((SortKey)var2.getSortKeys().get(0));
      }

      return (List<SortKey>)var3;
   }

   public boolean isOpaque() {
      Boolean var1 = SyntheticaLookAndFeel.getBoolean("Synthetica.tableHeader.opaque", this.table);
      return var1 == null ? super.isOpaque() : var1;
   }
}
