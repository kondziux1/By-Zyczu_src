package net.infonode.gui;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import net.infonode.gui.border.HighlightBorder;
import net.infonode.util.ColorUtil;

public class ButtonFactory {
   private static final Border normalBorder = new CompoundBorder(
      new LineBorder(new Color(70, 70, 70)), new CompoundBorder(new HighlightBorder(), new EmptyBorder(1, 6, 1, 6))
   );
   private static final Border pressedBorder = new CompoundBorder(
      new LineBorder(new Color(70, 70, 70)), new CompoundBorder(new HighlightBorder(true), new EmptyBorder(2, 7, 0, 5))
   );

   private ButtonFactory() {
      super();
   }

   private static JButton initButton(JButton var0) {
      var0.setMargin(null);
      var0.setBorder(normalBorder);
      var0.addMouseListener(new ButtonFactory$2(var0));
      return var0;
   }

   private static JButton newButton(String var0) {
      return initButton(new JButton(var0));
   }

   private static JButton newButton(Icon var0) {
      return initButton(new JButton(var0));
   }

   private static JButton newButton(Icon var0, String var1) {
      return initButton(new JButton(var1, var0));
   }

   public static final JButton createDialogButton(String var0, ActionListener var1) {
      JButton var2 = new JButton(var0);
      var2.setFont(var2.getFont().deriveFont(1));
      var2.addActionListener(var1);
      return var2;
   }

   public static final JButton createButton(String var0, ActionListener var1) {
      return createButton(var0, true, var1);
   }

   public static final JButton createButton(String var0, boolean var1, ActionListener var2) {
      JButton var3 = newButton(var0);
      var3.setOpaque(var1);
      var3.addActionListener(var2);
      return var3;
   }

   public static final JButton createButton(String var0, String var1, ActionListener var2) {
      URL var3 = (class$net$infonode$gui$ButtonFactory == null
            ? (class$net$infonode$gui$ButtonFactory = class$("net.infonode.gui.ButtonFactory"))
            : class$net$infonode$gui$ButtonFactory)
         .getClassLoader()
         .getResource(var0);
      return createButton(var3 == null ? null : new ImageIcon(var3), var1, var2);
   }

   public static final JButton createButton(Icon var0, String var1, ActionListener var2) {
      JButton var3;
      if (var0 != null) {
         var3 = newButton(var0);
         var3.setToolTipText(var1);
      } else {
         var3 = newButton(var1);
      }

      var3.addActionListener(var2);
      return var3;
   }

   public static final JButton createButton(Icon var0, String var1, boolean var2, ActionListener var3) {
      JButton var4 = newButton(var0);
      var4.setToolTipText(var1);
      var4.addActionListener(var3);
      var4.setOpaque(var2);
      return var4;
   }

   public static final JButton createFlatHighlightButton(Icon var0, String var1, int var2, ActionListener var3) {
      ButtonFactory$3 var4 = new ButtonFactory$3(var0);
      var4.setVerticalAlignment(0);
      var4.setToolTipText(var1);
      var4.setMargin(new Insets(0, 0, 0, 0));
      new ButtonFactory.ButtonHighlighter(var4, var2);
      var4.setRolloverEnabled(true);
      if (var3 != null) {
         var4.addActionListener(var3);
      }

      return var4;
   }

   public static final void applyButtonHighlighter(JButton var0, int var1) {
      var0.setVerticalAlignment(0);
      var0.setMargin(new Insets(0, 0, 0, 0));
      new ButtonFactory.ButtonHighlighter(var0, var1);
      var0.setRolloverEnabled(true);
   }

   public static final JButton createFlatHighlightButton(Icon var0, String var1, int var2, boolean var3, ActionListener var4) {
      JButton var5 = createFlatHighlightButton(var0, var1, var2, var4);
      var5.setFocusable(var3);
      return var5;
   }

   public static final JButton createHighlightButton(String var0, ActionListener var1) {
      JButton var2 = newButton(var0);
      var2.addActionListener(var1);
      return var2;
   }

   public static final JButton createHighlightButton(Icon var0, ActionListener var1) {
      JButton var2 = newButton(var0);
      var2.addActionListener(var1);
      return var2;
   }

   public static final JButton createHighlightButton(Icon var0, String var1, ActionListener var2) {
      JButton var3 = newButton(var0, var1);
      var3.addActionListener(var2);
      return var3;
   }

   public static final JButton createFlatIconHoverButton(Icon var0, Icon var1, Icon var2) {
      ButtonFactory$4 var3 = new ButtonFactory$4(var0);
      var3.setPressedIcon(var2);
      var3.setRolloverEnabled(true);
      var3.setRolloverIcon(var1);
      var3.setVerticalAlignment(0);
      return var3;
   }

   private static class ButtonHighlighter implements ComponentListener, HierarchyListener {
      private JButton button;
      private Border pressedBorder;
      private Border highlightedBorder;
      private Border normalBorder;
      private boolean rollover;
      private long rolloverStart;

      ButtonHighlighter(JButton var1, int var2) {
         super();
         this.button = var1;
         this.normalBorder = new EmptyBorder(var2 + 2, var2 + 2, var2 + 2, var2 + 2);
         this.pressedBorder = new EmptyBorder(var2 + 2, var2 + 2, var2, var2);
         this.highlightedBorder = new EmptyBorder(var2 + 1, var2 + 1, var2 + 1, var2 + 1);
         var1.setContentAreaFilled(false);
         this.setNormalState();
         var1.addChangeListener(new ButtonFactory$1(this));
         var1.addHierarchyListener(this);
         var1.addComponentListener(this);
      }

      private void setNormalState() {
         this.button.setBackground(null);
         this.button.setOpaque(false);
         this.button.setBorder(this.normalBorder);
         this.rollover = false;
      }

      public void componentHidden(ComponentEvent var1) {
         this.setNormalState();
         this.rolloverStart = System.currentTimeMillis();
      }

      public void componentMoved(ComponentEvent var1) {
         this.setNormalState();
         this.rolloverStart = System.currentTimeMillis();
      }

      public void componentResized(ComponentEvent var1) {
         this.setNormalState();
         this.rolloverStart = System.currentTimeMillis();
      }

      public void componentShown(ComponentEvent var1) {
         this.setNormalState();
         this.rolloverStart = System.currentTimeMillis();
      }

      public void hierarchyChanged(HierarchyEvent var1) {
         this.setNormalState();
         this.rolloverStart = System.currentTimeMillis();
      }

      private void update() {
         boolean var1 = this.button.getModel().isArmed();
         if (this.button.isEnabled() && (this.rollover || var1)) {
            this.button.setOpaque(true);
            Color var2 = ComponentUtil.getBackgroundColor(this.button.getParent());
            var2 = var2 == null ? UIManagerUtil.getColor("control", Color.LIGHT_GRAY) : var2;
            this.button.setBackground(ColorUtil.mult(var2, var1 ? 0.8 : 1.15));
            this.button
               .setBorder(
                  var1
                     ? new CompoundBorder(new LineBorder(ColorUtil.mult(var2, 0.3)), this.pressedBorder)
                     : new CompoundBorder(new LineBorder(ColorUtil.mult(var2, 0.5)), this.highlightedBorder)
               );
         } else {
            this.setNormalState();
         }

      }
   }
}
