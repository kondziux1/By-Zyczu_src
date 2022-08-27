package net.infonode.tabbedpanel.titledtab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.PanelUI;
import net.infonode.gui.DimensionProvider;
import net.infonode.gui.InsetsUtil;
import net.infonode.gui.RotatableLabel;
import net.infonode.gui.TranslatingShape;
import net.infonode.gui.border.FocusBorder;
import net.infonode.gui.hover.HoverEvent;
import net.infonode.gui.hover.HoverListener;
import net.infonode.gui.hover.hoverable.HoverManager;
import net.infonode.gui.hover.hoverable.Hoverable;
import net.infonode.gui.icon.IconProvider;
import net.infonode.gui.layout.StackableLayout;
import net.infonode.gui.panel.SimplePanel;
import net.infonode.gui.shaped.panel.ShapedPanel;
import net.infonode.properties.gui.InternalPropertiesUtil;
import net.infonode.properties.gui.util.ComponentProperties;
import net.infonode.properties.gui.util.ShapedPanelProperties;
import net.infonode.properties.propertymap.PropertyMapTreeListener;
import net.infonode.properties.propertymap.PropertyMapWeakListenerManager;
import net.infonode.properties.util.PropertyChangeListener;
import net.infonode.tabbedpanel.Tab;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.util.Alignment;
import net.infonode.util.Direction;
import net.infonode.util.ValueChange;

public class TitledTab extends Tab implements IconProvider {
   private static PanelUI UI = new TitledTab$1();
   private final TitledTabProperties properties = TitledTabProperties.getDefaultProperties();
   private HoverListener hoverListener = this.properties.getHoverListener();
   private final TitledTab.HoverablePanel eventPanel = new TitledTab$3(this, new BorderLayout());
   private final TitledTab.StatePanel normalStatePanel;
   private final TitledTab.StatePanel highlightedStatePanel;
   private final TitledTab.StatePanel disabledStatePanel;
   private ArrayList mouseListeners;
   private ArrayList mouseMotionListeners;
   private final StackableLayout layout;
   private TitledTab.StatePanel currentStatePanel;
   private final FocusBorder focusBorder;
   private Direction lastTabAreaOrientation = Direction.UP;
   private final PropertyMapTreeListener propertiesListener = new TitledTab$4(this);
   private final PropertyChangeListener tabbedPanelPropertiesListener = new TitledTab$5(this);

   public boolean contains(int var1, int var2) {
      Point var3 = SwingUtilities.convertPoint(this, new Point(var1, var2), this.eventPanel);
      return this.eventPanel.contains(var3.x, var3.y);
   }

   public boolean inside(int var1, int var2) {
      Point var3 = SwingUtilities.convertPoint(this, new Point(var1, var2), this.eventPanel);
      return this.eventPanel.inside(var3.x, var3.y);
   }

   public TitledTab(String var1, Icon var2, JComponent var3, JComponent var4) {
      super(var3);
      super.setOpaque(false);
      this.addFocusListener(new TitledTab$6(this));
      this.focusBorder = new FocusBorder(this);
      this.normalStatePanel = new TitledTab.StatePanel(this.focusBorder);
      this.highlightedStatePanel = new TitledTab.StatePanel(this.focusBorder);
      this.disabledStatePanel = new TitledTab.StatePanel(this.focusBorder);
      this.layout = new TitledTab$7(this, this);
      this.setLayout(this.layout);
      this.add(this.normalStatePanel);
      this.add(this.highlightedStatePanel);
      this.add(this.disabledStatePanel);
      this.setText(var1);
      this.setIcon(var2);
      this.setTitleComponent(var4);
      this.eventPanel.addMouseListener(new TitledTab$8(this));
      this.setEventComponent(this.eventPanel);
      TitledTab$9 var5 = new TitledTab$9(this);
      TitledTab$10 var6 = new TitledTab$10(this);
      this.eventPanel.addMouseListener(var5);
      this.eventPanel.addMouseMotionListener(var6);
      PropertyMapWeakListenerManager.addWeakTreeListener(this.properties.getMap(), this.propertiesListener);
      this.addTabListener(new TitledTab$11(this));
      this.doUpdateTab(null);
      this.updateCurrentStatePanel();
   }

   public JComponent getNormalStateTitleComponent() {
      return this.normalStatePanel.getTitleComponent();
   }

   public JComponent getHighlightedStateTitleComponent() {
      return this.highlightedStatePanel.getTitleComponent();
   }

   public JComponent getDisabledStateTitleComponent() {
      return this.disabledStatePanel.getTitleComponent();
   }

   public void setTitleComponent(JComponent var1) {
      this.normalStatePanel.setTitleComponent(var1, this.properties.getNormalProperties());
      this.highlightedStatePanel.setTitleComponent(var1, this.properties.getHighlightedProperties());
      this.disabledStatePanel.setTitleComponent(var1, this.properties.getDisabledProperties());
   }

   public void setNormalStateTitleComponent(JComponent var1) {
      this.normalStatePanel.setTitleComponent(var1, this.properties.getNormalProperties());
   }

   public void setHighlightedStateTitleComponent(JComponent var1) {
      this.highlightedStatePanel.setTitleComponent(var1, this.properties.getHighlightedProperties());
   }

   public void setDisabledStateTitleComponent(JComponent var1) {
      this.disabledStatePanel.setTitleComponent(var1, this.properties.getDisabledProperties());
   }

   public void setHighlighted(boolean var1) {
      super.setHighlighted(var1);
      this.updateCurrentStatePanel();
   }

   public void setEnabled(boolean var1) {
      super.setEnabled(var1);
      this.updateCurrentStatePanel();
   }

   public String getText() {
      return this.properties.getNormalProperties().getText();
   }

   public void setText(String var1) {
      this.properties.getNormalProperties().setText(var1);
   }

   public Icon getIcon() {
      return this.properties.getNormalProperties().getIcon();
   }

   public void setIcon(Icon var1) {
      this.properties.getNormalProperties().setIcon(var1);
   }

   public TitledTabProperties getProperties() {
      return this.properties;
   }

   public String toString() {
      return this.getText();
   }

   public synchronized void addMouseListener(MouseListener var1) {
      if (this.mouseListeners == null) {
         this.mouseListeners = new ArrayList(2);
      }

      this.mouseListeners.add(var1);
   }

   public synchronized void removeMouseListener(MouseListener var1) {
      if (this.mouseListeners != null) {
         this.mouseListeners.remove(var1);
         if (this.mouseListeners.size() == 0) {
            this.mouseListeners = null;
         }
      }

   }

   public synchronized MouseListener[] getMouseListeners() {
      MouseListener[] var1 = new MouseListener[0];
      if (this.mouseListeners != null) {
         Object[] var2 = this.mouseListeners.toArray();
         var1 = new MouseListener[var2.length];

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var1[var3] = (MouseListener)var2[var3];
         }
      }

      return var1;
   }

   public synchronized void addMouseMotionListener(MouseMotionListener var1) {
      if (this.mouseMotionListeners == null) {
         this.mouseMotionListeners = new ArrayList(2);
      }

      this.mouseMotionListeners.add(var1);
   }

   public synchronized void removeMouseMotionListener(MouseMotionListener var1) {
      if (this.mouseMotionListeners != null) {
         this.mouseMotionListeners.remove(var1);
         if (this.mouseMotionListeners.size() == 0) {
            this.mouseMotionListeners = null;
         }
      }

   }

   public synchronized MouseMotionListener[] getMouseMotionListeners() {
      MouseMotionListener[] var1 = new MouseMotionListener[0];
      if (this.mouseMotionListeners != null) {
         Object[] var2 = this.mouseMotionListeners.toArray();
         var1 = new MouseMotionListener[var2.length];

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var1[var3] = (MouseMotionListener)var2[var3];
         }
      }

      return var1;
   }

   public Shape getShape() {
      Shape var1 = this.currentStatePanel.getShape();
      if (var1 == null) {
         return null;
      } else {
         Point var2 = SwingUtilities.convertPoint(this.currentStatePanel, 0, 0, this);
         return new TranslatingShape(var1, (double)var2.x, (double)var2.y);
      }
   }

   protected void setTabbedPanel(TabbedPanel var1) {
      if (var1 == null) {
         HoverManager.getInstance().removeHoverable(this.eventPanel);
      }

      super.setTabbedPanel(var1);
      if (var1 != null) {
         HoverManager.getInstance().addHoverable(this.eventPanel);
      }

   }

   private Insets getBorderInsets(Border var1) {
      return var1 == null ? InsetsUtil.EMPTY_INSETS : var1.getBorderInsets(this);
   }

   private void updateBorders() {
      Direction var1 = this.getTabAreaOrientation();
      int var2 = this.properties.getHighlightedRaised();
      Insets var3 = InsetsUtil.setInset(InsetsUtil.EMPTY_INSETS, var1, var2);
      EmptyBorder var4 = new EmptyBorder(var3);
      Insets var5 = this.properties.getBorderSizePolicy() == TitledTabBorderSizePolicy.INDIVIDUAL_SIZE
         ? null
         : InsetsUtil.max(
            this.getBorderInsets(this.properties.getNormalProperties().getComponentProperties().getBorder()),
            InsetsUtil.max(
               this.getBorderInsets(this.properties.getHighlightedProperties().getComponentProperties().getBorder()),
               this.getBorderInsets(this.properties.getDisabledProperties().getComponentProperties().getBorder())
            )
         );
      Insets var6 = InsetsUtil.rotate(
         this.properties.getNormalProperties().getDirection(), this.properties.getNormalProperties().getComponentProperties().getInsets()
      );
      Insets var7 = InsetsUtil.rotate(
         this.properties.getDisabledProperties().getDirection(), this.properties.getDisabledProperties().getComponentProperties().getInsets()
      );
      int var8 = Math.min(InsetsUtil.getInset(var6, var1.getOpposite()), InsetsUtil.getInset(var7, var1.getOpposite()));
      int var9 = Math.min(var8, var2);
      Border var10 = this.getInnerBorder(this.properties.getNormalProperties(), var1, -var9, var5);
      Border var11 = this.getInnerBorder(this.properties.getHighlightedProperties(), var1, var2 - var9, var5);
      Border var12 = this.getInnerBorder(this.properties.getDisabledProperties(), var1, -var9, var5);
      this.normalStatePanel.setBorders(var4, var10);
      this.highlightedStatePanel.setBorders(null, var11);
      this.disabledStatePanel.setBorders(var4, var12);
   }

   private void doUpdateTab(Map var1) {
      boolean var2 = false;
      if (var1 == null) {
         var2 = true;
         this.setFocusableComponent(this.properties.getFocusable() ? this : null);
         this.focusBorder.setEnabled(this.properties.getFocusMarkerEnabled());
         this.updateHoverListener(this.properties.getHoverListener());
         this.layout.setUseSelectedComponentSize(this.properties.getSizePolicy() == TitledTabSizePolicy.INDIVIDUAL_SIZE);
      } else {
         Map var3 = (Map)var1.get(this.properties.getMap());
         if (var3 != null) {
            Set var4 = var3.keySet();
            if (var4.contains(TitledTabProperties.FOCUSABLE)) {
               this.setFocusableComponent(this.properties.getFocusable() ? this : null);
            }

            if (var4.contains(TitledTabProperties.FOCUS_MARKER_ENABLED)) {
               this.focusBorder.setEnabled(this.properties.getFocusMarkerEnabled());
               this.currentStatePanel.getLabel().repaint();
            }

            if (var4.contains(TitledTabProperties.HOVER_LISTENER)) {
               this.updateHoverListener((HoverListener)((ValueChange)var3.get(TitledTabProperties.HOVER_LISTENER)).getNewValue());
            }

            if (var4.contains(TitledTabProperties.SIZE_POLICY)) {
               this.layout
                  .setUseSelectedComponentSize(
                     (TitledTabSizePolicy)((ValueChange)var3.get(TitledTabProperties.SIZE_POLICY)).getNewValue() == TitledTabSizePolicy.INDIVIDUAL_SIZE
                  );
            }

            if (var4.contains(TitledTabProperties.HIGHLIGHTED_RAISED_AMOUNT) || var4.contains(TitledTabProperties.BORDER_SIZE_POLICY)) {
               var2 = true;
            }

            if (var4.contains(TitledTabProperties.ENABLED)) {
               this.doSetEnabled(this.properties.getEnabled());
            }
         }
      }

      var2 = this.normalStatePanel.updateState(var1, this.properties.getNormalProperties()) || var2;
      var2 = this.highlightedStatePanel.updateState(var1, this.properties.getHighlightedProperties()) || var2;
      var2 = this.disabledStatePanel.updateState(var1, this.properties.getDisabledProperties()) || var2;
      if (var2) {
         this.updateBorders();
      }

   }

   private void updateHoverListener(HoverListener var1) {
      HoverListener var2 = this.hoverListener;
      this.hoverListener = var1;
      if (HoverManager.getInstance().isHovered(this.eventPanel)) {
         if (var2 != null) {
            var2.mouseExited(new HoverEvent(this));
         }

         if (this.hoverListener != null) {
            this.hoverListener.mouseEntered(new HoverEvent(this));
         }
      }

   }

   private Border getInnerBorder(TitledTabStateProperties var1, Direction var2, int var3, Insets var4) {
      Direction var5 = var1.getDirection();
      Insets var6 = InsetsUtil.rotate(var5, var1.getComponentProperties().getInsets());
      if (var4 != null) {
         var6 = InsetsUtil.add(var6, InsetsUtil.sub(var4, this.getBorderInsets(var1.getComponentProperties().getBorder())));
      }

      Border var7 = var1.getComponentProperties().getBorder();
      EmptyBorder var8 = new EmptyBorder(InsetsUtil.add(var6, InsetsUtil.setInset(InsetsUtil.EMPTY_INSETS, var2.getOpposite(), var3)));
      return (Border)(var7 == null ? var8 : new CompoundBorder(var7, var8));
   }

   private Direction getTabAreaOrientation() {
      return this.getTabbedPanel() == null ? this.lastTabAreaOrientation : this.getTabbedPanel().getProperties().getTabAreaOrientation();
   }

   private void updateTabAreaOrientation(Direction var1) {
      if (this.lastTabAreaOrientation != var1) {
         this.lastTabAreaOrientation = var1;
         this.updateBorders();
         this.normalStatePanel.updateShapedPanel(this.properties.getNormalProperties());
         this.highlightedStatePanel.updateShapedPanel(this.properties.getHighlightedProperties());
         this.disabledStatePanel.updateShapedPanel(this.properties.getDisabledProperties());
      }

   }

   private void updateCurrentStatePanel() {
      TitledTab.StatePanel var1 = this.normalStatePanel;
      if (!this.isEnabled()) {
         var1 = this.disabledStatePanel;
      } else if (this.isHighlighted()) {
         var1 = this.highlightedStatePanel;
      }

      this.eventPanel.setToolTipText(var1.getToolTipText());
      if (this.currentStatePanel != var1) {
         if (this.currentStatePanel != null) {
            this.currentStatePanel.deactivate();
         }

         this.currentStatePanel = var1;
         this.currentStatePanel.activate();
      }

      this.layout.showComponent(this.currentStatePanel);
   }

   private MouseEvent convertMouseEvent(MouseEvent var1) {
      Point var2 = SwingUtilities.convertPoint((JComponent)var1.getSource(), var1.getPoint(), this);
      return new MouseEvent(
         this,
         var1.getID(),
         var1.getWhen(),
         var1.getModifiers(),
         (int)var2.getX(),
         (int)var2.getY(),
         var1.getClickCount(),
         !var1.isConsumed() && var1.isPopupTrigger(),
         var1.getButton()
      );
   }

   private void doSetEnabled(boolean var1) {
      super.setEnabled(var1);
      this.updateCurrentStatePanel();
   }

   public void setUI(PanelUI var1) {
      if (this.getUI() != UI) {
         super.setUI(UI);
      }

   }

   public void updateUI() {
      this.setUI(UI);
   }

   public void setOpaque(boolean var1) {
   }

   private class HoverablePanel extends SimplePanel implements Hoverable {
      public HoverablePanel(LayoutManager var2) {
         super(var2);
      }

      public void hoverEnter() {
         if (TitledTab.this.hoverListener != null && TitledTab.this.getTabbedPanel() != null) {
            TitledTab.this.hoverListener.mouseEntered(new HoverEvent(TitledTab.this));
         }

      }

      public void hoverExit() {
         if (TitledTab.this.hoverListener != null) {
            TitledTab.this.hoverListener.mouseExited(new HoverEvent(TitledTab.this));
         }

      }

      public boolean acceptHover(ArrayList var1) {
         return true;
      }
   }

   private class StatePanel extends SimplePanel {
      private final ShapedPanel panel = new ShapedPanel();
      private final SimplePanel titleComponentPanel = new SimplePanel();
      private final RotatableLabel label = new TitledTab$2(this, null, null);
      private JComponent titleComponent;
      private Direction currentLayoutDirection;
      private int currentLayoutGap = -1;
      private Alignment currentLayoutAlignment;
      private String toolTipText;
      private Icon icon;

      public StatePanel(Border var2) {
         super(new BorderLayout());
         this.label.setBorder(var2);
         this.label.setMinimumSize(new Dimension(0, 0));
         this.panel.add(this.label, "Center");
         this.add(this.panel, "Center");
      }

      public String getToolTipText() {
         return this.toolTipText;
      }

      public JComponent getTitleComponent() {
         return this.titleComponent;
      }

      public Shape getShape() {
         return this.panel.getShape();
      }

      public JLabel getLabel() {
         return this.label;
      }

      public void setTitleComponent(JComponent var1, TitledTabStateProperties var2) {
         JComponent var3 = this.titleComponent;
         this.titleComponent = null;
         if (var3 != null && var3.getParent() == this.titleComponentPanel) {
            this.titleComponentPanel.remove(var3);
         }

         this.titleComponent = var1;
         this.updateLayout(var2, true);
      }

      public void activateTitleComponent() {
         if (this.titleComponent != null) {
            if (this.titleComponent.getParent() != this.titleComponentPanel) {
               if (this.titleComponent.getParent() != null) {
                  this.titleComponent.getParent().remove(this.titleComponent);
               }

               this.titleComponentPanel.add(this.titleComponent, "Center");
            }
         } else {
            this.titleComponentPanel.removeAll();
         }

      }

      public void activate() {
         this.remove(this.panel);
         TitledTab.this.eventPanel.add(this.panel, "Center");
         this.add(TitledTab.this.eventPanel, "Center");
      }

      public void deactivate() {
         this.remove(TitledTab.this.eventPanel);
         TitledTab.this.eventPanel.remove(this.panel);
         this.add(this.panel, "Center");
      }

      public Dimension getPreferredSize() {
         this.activateTitleComponent();
         return this.getAdjustedSize(super.getPreferredSize());
      }

      public Dimension getMinimumSize() {
         this.activateTitleComponent();
         return this.getAdjustedSize(super.getMinimumSize());
      }

      public Dimension getMaximumSize() {
         this.activateTitleComponent();
         return super.getMaximumSize();
      }

      private Dimension getAdjustedSize(Dimension var1) {
         DimensionProvider var2 = TitledTab.this.properties.getMinimumSizeProvider();
         if (var2 == null) {
            return var1;
         } else {
            Dimension var3 = TitledTab.this.properties.getMinimumSizeProvider().getDimension(this);
            return var3 == null ? var1 : new Dimension(Math.max(var3.width, var1.width), Math.max(var3.height, var1.height));
         }
      }

      public JComponent getFocusableComponent() {
         return this.label;
      }

      private void updateLayout(TitledTabStateProperties var1, boolean var2) {
         if (this.titleComponent != null && var1.getTitleComponentVisible()) {
            Direction var3 = var1.getDirection();
            int var4;
            if (!var1.getIconVisible() && !var1.getTextVisible()) {
               var4 = 0;
            } else {
               var4 = var1.getTextTitleComponentGap();
            }

            Alignment var5 = var1.getTitleComponentTextRelativeAlignment();
            if (this.titleComponentPanel.getComponentCount() == 0
               || this.titleComponentPanel.getComponentCount() > 0 && this.titleComponentPanel.getComponent(0) != this.titleComponent
               || var2
               || var4 != this.currentLayoutGap
               || var5 != this.currentLayoutAlignment
               || var3 != this.currentLayoutDirection) {
               var2 = false;
               this.currentLayoutDirection = var3;
               this.currentLayoutGap = var4;
               this.currentLayoutAlignment = var5;
               this.panel.remove(this.titleComponentPanel);
               if (var3 == Direction.UP) {
                  this.panel.add(this.titleComponentPanel, var5 == Alignment.LEFT ? "South" : "North");
                  this.titleComponentPanel.setBorder(new EmptyBorder(var5 == Alignment.LEFT ? var4 : 0, 0, var5 == Alignment.LEFT ? 0 : var4, 0));
               } else if (var3 == Direction.LEFT) {
                  this.panel.add(this.titleComponentPanel, var5 == Alignment.LEFT ? "East" : "West");
                  this.titleComponentPanel.setBorder(new EmptyBorder(0, var5 == Alignment.LEFT ? var4 : 0, 0, var5 == Alignment.LEFT ? 0 : var4));
               } else if (var3 == Direction.DOWN) {
                  this.panel.add(this.titleComponentPanel, var5 == Alignment.LEFT ? "North" : "South");
                  this.titleComponentPanel.setBorder(new EmptyBorder(var5 == Alignment.LEFT ? 0 : var4, 0, var5 == Alignment.LEFT ? var4 : 0, 0));
               } else {
                  this.panel.add(this.titleComponentPanel, var5 == Alignment.LEFT ? "West" : "East");
                  this.titleComponentPanel.setBorder(new EmptyBorder(0, var5 == Alignment.LEFT ? 0 : var4, 0, var5 == Alignment.LEFT ? var4 : 0));
               }

               this.panel.revalidate();
            }
         } else {
            this.panel.remove(this.titleComponentPanel);
            this.titleComponentPanel.removeAll();
            this.panel.revalidate();
         }

      }

      public void updateShapedPanel(TitledTabStateProperties var1) {
         Direction var2 = TitledTab.this.getTabAreaOrientation();
         ShapedPanelProperties var3 = var1.getShapedPanelProperties();
         InternalPropertiesUtil.applyTo(var3, this.panel, var2.getNextCW());
         this.panel.setHorizontalFlip(var2 != Direction.DOWN && var2 != Direction.LEFT ? var3.getHorizontalFlip() : !var3.getHorizontalFlip());
      }

      public void setBorders(Border var1, Border var2) {
         this.setBorder(var1);
         this.panel.setBorder(var2);
      }

      public boolean updateState(Map var1, TitledTabStateProperties var2) {
         boolean var3 = false;
         if (var1 == null) {
            this.label.setText(var2.getTextVisible() ? var2.getText() : null);
            this.icon = var2.getIcon();
            this.label.setIcon(var2.getIconVisible() ? var2.getIcon() : null);
            this.label.setIconTextGap(var2.getIconTextGap());
            this.label.setDirection(var2.getDirection());
            Alignment var4 = var2.getIconTextRelativeAlignment();
            this.label.setHorizontalTextPosition(var4 == Alignment.LEFT ? 4 : 2);
            var4 = var2.getHorizontalAlignment();
            this.label.setHorizontalAlignment(var4 == Alignment.LEFT ? 2 : (var4 == Alignment.CENTER ? 0 : 4));
            var4 = var2.getVerticalAlignment();
            this.label.setVerticalAlignment(var4 == Alignment.TOP ? 1 : (var4 == Alignment.CENTER ? 0 : 3));
            this.toolTipText = var2.getToolTipEnabled() ? var2.getToolTipText() : null;
            if (this.toolTipText != null && this.toolTipText.length() == 0) {
               this.toolTipText = null;
            }

            if (TitledTab.this.currentStatePanel == this) {
               TitledTab.this.eventPanel.setToolTipText(this.toolTipText);
            }

            this.updateLayout(var2, true);
            ComponentProperties var5 = var2.getComponentProperties();
            this.label.setFont(var5.getFont());
            Color var6 = var5.getForegroundColor();
            this.label.setForeground(var6);
            this.setForeground(var6);
            this.updateShapedPanel(var2);
            var3 = true;
         } else {
            Map var9 = (Map)var1.get(var2.getMap());
            if (var9 != null) {
               Set var12 = var9.keySet();
               if (var12.contains(TitledTabStateProperties.TEXT) || var12.contains(TitledTabStateProperties.TEXT_VISIBLE)) {
                  this.label.setText(var2.getTextVisible() ? var2.getText() : null);
               }

               if (var12.contains(TitledTabStateProperties.ICON) || var12.contains(TitledTabStateProperties.ICON_VISIBLE)) {
                  this.icon = var2.getIcon();
                  this.label.setIcon(var2.getIconVisible() ? var2.getIcon() : null);
               }

               if (var12.contains(TitledTabStateProperties.ICON_TEXT_GAP)) {
                  this.label.setIconTextGap(((ValueChange)var9.get(TitledTabStateProperties.ICON_TEXT_GAP)).getNewValue());
               }

               if (var12.contains(TitledTabStateProperties.ICON_TEXT_RELATIVE_ALIGNMENT)) {
                  Alignment var14 = (Alignment)((ValueChange)var9.get(TitledTabStateProperties.ICON_TEXT_RELATIVE_ALIGNMENT)).getNewValue();
                  this.label.setHorizontalTextPosition(var14 == Alignment.LEFT ? 4 : 2);
               }

               if (var12.contains(TitledTabStateProperties.HORIZONTAL_ALIGNMENT)) {
                  Alignment var15 = (Alignment)((ValueChange)var9.get(TitledTabStateProperties.HORIZONTAL_ALIGNMENT)).getNewValue();
                  this.label.setHorizontalAlignment(var15 == Alignment.LEFT ? 2 : (var15 == Alignment.CENTER ? 0 : 4));
               }

               if (var12.contains(TitledTabStateProperties.VERTICAL_ALIGNMENT)) {
                  Alignment var16 = (Alignment)((ValueChange)var9.get(TitledTabStateProperties.VERTICAL_ALIGNMENT)).getNewValue();
                  this.label.setVerticalAlignment(var16 == Alignment.TOP ? 1 : (var16 == Alignment.CENTER ? 0 : 3));
               }

               if (var12.contains(TitledTabStateProperties.TOOL_TIP_TEXT) || var12.contains(TitledTabStateProperties.TOOL_TIP_ENABLED)) {
                  this.toolTipText = var2.getToolTipEnabled() ? var2.getToolTipText() : null;
                  if (this.toolTipText != null && this.toolTipText.length() == 0) {
                     this.toolTipText = null;
                  }

                  if (TitledTab.this.currentStatePanel == this) {
                     TitledTab.this.eventPanel.setToolTipText(this.toolTipText);
                  }
               }

               if (var12.contains(TitledTabStateProperties.DIRECTION)
                  || var12.contains(TitledTabStateProperties.TITLE_COMPONENT_TEXT_RELATIVE_ALIGNMENT)
                  || var12.contains(TitledTabStateProperties.TITLE_COMPONENT_VISIBLE)
                  || var12.contains(TitledTabStateProperties.TEXT_TITLE_COMPONENT_GAP)
                  || var12.contains(TitledTabStateProperties.ICON_VISIBLE)
                  || var12.contains(TitledTabStateProperties.TEXT_VISIBLE)) {
                  this.label.setDirection(var2.getDirection());
                  this.updateLayout(var2, var12.contains(TitledTabStateProperties.TITLE_COMPONENT_VISIBLE));
               }

               if (var12.contains(TitledTabStateProperties.DIRECTION)) {
                  var3 = true;
               }
            }

            var9 = (Map)var1.get(var2.getComponentProperties().getMap());
            if (var9 != null) {
               Set var13 = var9.keySet();
               if (var13.contains(ComponentProperties.FONT)) {
                  this.label.setFont((Font)((ValueChange)var9.get(ComponentProperties.FONT)).getNewValue());
               }

               if (var13.contains(ComponentProperties.FOREGROUND_COLOR)) {
                  Color var17 = (Color)((ValueChange)var9.get(ComponentProperties.FOREGROUND_COLOR)).getNewValue();
                  this.label.setForeground(var17);
                  this.setForeground(var17);
               }

               if (var13.contains(ComponentProperties.BACKGROUND_COLOR)) {
                  Color var18 = (Color)((ValueChange)var9.get(ComponentProperties.BACKGROUND_COLOR)).getNewValue();
                  this.panel.setBackground(var18);
               }

               if (var13.contains(ComponentProperties.INSETS) || var13.contains(ComponentProperties.BORDER)) {
                  var3 = true;
               }
            }

            var9 = (Map)var1.get(var2.getShapedPanelProperties().getMap());
            if (var9 != null) {
               this.updateShapedPanel(var2);
            }
         }

         return var3;
      }
   }
}
