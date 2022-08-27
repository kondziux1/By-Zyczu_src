package net.infonode.tabbedpanel.theme.internal.laftheme;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import net.infonode.gui.InsetsUtil;
import net.infonode.gui.draggable.DraggableComponentBox;
import net.infonode.tabbedpanel.Tab;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedPanelContentPanel;
import net.infonode.util.Direction;

public class PaneUI {
   private static final boolean PAINT_TAB_AREA = true;
   private static final boolean PAINT_CONTENT_AREA = true;
   private static final boolean PAINT_TAB = true;
   private static final boolean TEXT_ICON_GAP_COMPENSATE = true;
   private static final int DEFAULT_SELECTED_INDEX = 3;
   private static final int DEFAULT_TAB_COUNT = 7;
   private static final int EXTRA_SIZE = 2;
   private static final String EMPTY_STRING = "";
   private static final Direction[] DIRECTIONS = new Direction[]{Direction.UP, Direction.LEFT, Direction.DOWN, Direction.RIGHT};
   private final Insets[] areaInsets = new Insets[DIRECTIONS.length];
   private final Insets[] normalInsets = new Insets[DIRECTIONS.length];
   private final Insets[] selectedInsets = new Insets[DIRECTIONS.length];
   private final Insets[] adjustedContentInsets = new Insets[DIRECTIONS.length];
   private final Insets[] adjustedContentInsetsTabAreaHidden = new Insets[DIRECTIONS.length];
   private final Insets[] contentInsets = new Insets[DIRECTIONS.length];
   private final Dimension[] minimumSizes = new Dimension[DIRECTIONS.length];
   private final Dimension[] tabMinimumSizes = new Dimension[DIRECTIONS.length];
   private final int[] spacings = new int[DIRECTIONS.length];
   private final int[] raiseds = new int[DIRECTIONS.length];
   private final Insets[] tabInsets = new Insets[DIRECTIONS.length];
   private final Color[] contentTabAreaBorderColors = new Color[DIRECTIONS.length];
   private final boolean[] swapWidthHeights = new boolean[DIRECTIONS.length];
   private boolean tabAreaNotVisibleFix = false;
   private int scrollOffset = 0;
   private int textIconGap;
   private final PaneUIListener listener;
   private static ComponentCache componentCache = new ComponentCache();
   private final PaneHandler paneHandler = new PaneHandler(new PaneUI$1(this));
   private Tab hoveredTab;
   private final TabData tabData = new TabData();
   private boolean tabAreaOpaque;
   private boolean contentOpaque;
   private boolean opaque;
   private boolean tabAreaComponentsOpaque;
   private boolean enabled = true;

   public PaneUI(PaneUIListener var1) {
      super();
      this.listener = var1;
   }

   public void init() {
      this.paneHandler.update();
   }

   private void doInit() {
      this.initPreCommonValues();

      for(int var1 = 0; var1 < DIRECTIONS.length; ++var1) {
         PanePainter var2 = this.paneHandler.getPainter(DIRECTIONS[var1]);
         this.initValues(var2, var1, DIRECTIONS[var1]);
         this.reset(var2);
      }

      this.initPostCommonValues();
   }

   public void dispose() {
      this.enabled = false;
      this.paneHandler.dispose();
   }

   public void setEnabled(boolean var1) {
      this.enabled = var1;
   }

   private void initPreCommonValues() {
      this.tabAreaNotVisibleFix = UIManager.getLookAndFeel().getClass().getName().indexOf(".WindowsLookAndFeel") > -1;
      this.textIconGap = UIManager.getInt("TabbedPane.textIconGap");
      if (this.textIconGap <= 0) {
         this.textIconGap = 4;
      }

      this.opaque = this.paneHandler.getPainter(Direction.UP).isOpaque();
      Boolean var1 = (Boolean)UIManager.get("TabbedPane.contentOpaque");
      if (var1 == null) {
         this.contentOpaque = this.opaque;
      } else {
         this.contentOpaque = var1;
      }

      this.tabAreaOpaque = this.opaque;
      this.tabAreaComponentsOpaque = false;
   }

   private void initPostCommonValues() {
      for(int var1 = 0; var1 < DIRECTIONS.length; ++var1) {
         this.scrollOffset = Math.max(this.scrollOffset, Math.max(this.minimumSizes[var1].width, this.minimumSizes[var1].height));
      }

   }

   private void initValues(PanePainter var1, int var2, Direction var3) {
      this.estimateSwappedTabDirection(var1, var2, var3);
      this.reset(var1);
      var1.setSize(1000, 1000);
      boolean var4 = !var3.isHorizontal();

      for(int var5 = 0; var5 < 7; ++var5) {
         var1.addTab("", this.getComponent());
      }

      var1.setSelectedIndex(3);
      var1.doValidation();
      Insets var15 = UIManager.getInsets("TabbedPane.tabInsets");
      if (var15 == null) {
         var15 = new Insets(0, 0, 0, 0);
      }

      if (!var4) {
         this.tabInsets[var2] = new Insets(0, var15.left, 0, var15.right);
      } else {
         this.tabInsets[var2] = InsetsUtil.EMPTY_INSETS;
      }

      Rectangle var16 = var1.getBoundsAt(0);
      Rectangle var6 = var1.getBoundsAt(var1.getSelectedIndex());
      if (var3 == Direction.UP) {
         this.raiseds[var2] = Math.max(0, var16.y - var6.y);
      } else if (var3 == Direction.LEFT) {
         this.raiseds[var2] = Math.max(0, var16.x - var6.x);
      } else if (var3 == Direction.DOWN) {
         this.raiseds[var2] = this.raiseds[this.getDirectionIndex(Direction.UP)];
      } else {
         this.raiseds[var2] = this.raiseds[this.getDirectionIndex(Direction.LEFT)];
      }

      Insets var17 = this.getCalculatedInsets(var1, 0, false, var3);
      Insets var21 = this.getCalculatedInsets(var1, 0, true, var3);
      if (var4) {
         this.spacings[var2] = var17.left + var17.right - var21.left - var21.right;
      } else {
         this.spacings[var2] = var17.top + var17.bottom - var21.top - var21.bottom;
      }

      this.normalInsets[var2] = this.getCalculatedInsets(var1, 0, false, var3);
      var17 = this.getCalculatedInsets(var1, 0, true, var3);
      int var22 = this.spacings[var2];
      int var7 = var22 / 2;
      int var8 = var22 / 2 + var22 % 2;
      if (var3 == Direction.UP) {
         var17.bottom = this.normalInsets[var2].bottom;
         var17.top = this.normalInsets[var2].top;
         var17.left += var7;
         var17.right += var8;
      } else if (var3 == Direction.LEFT) {
         var17.right = this.normalInsets[var2].right;
         var17.left = this.normalInsets[var2].left;
         var17.top += var7;
         var17.bottom += var8;
      } else if (var3 == Direction.RIGHT) {
         var17.right = this.normalInsets[var2].right;
         var17.left = this.normalInsets[var2].left;
         var17.top += var7;
         var17.bottom += var8;
      } else {
         var17.bottom = this.normalInsets[var2].bottom;
         var17.top = this.normalInsets[var2].top;
         var17.left += var7;
         var17.right += var8;
      }

      this.selectedInsets[var2] = var17;
      JPanel var19 = new JPanel();
      var1.addTab("", var19);
      var1.setSelectedIndex(var1.getTabCount() - 1);
      var1.doValidation();
      Point var23 = SwingUtilities.convertPoint(var19.getParent(), var19.getLocation(), var1);
      Rectangle var24 = var1.getBoundsAt(0);
      var8 = 0;
      int var9 = 0;
      int var10 = 0;
      int var11 = 0;
      if (var3 == Direction.UP) {
         var8 = var23.y - var24.height - var24.y;
         var9 = var23.x;
         var10 = var1.getHeight() - var23.y - var19.getHeight();
         var11 = var1.getWidth() - var23.x - var19.getWidth();
      } else if (var3 == Direction.DOWN) {
         var8 = var23.y;
         var9 = var23.x;
         var10 = var1.getHeight() - var19.getHeight() - var23.y - (var1.getHeight() - var24.y);
         var11 = var1.getWidth() - var23.x - var19.getWidth();
      } else if (var3 == Direction.LEFT) {
         var8 = var23.y;
         var9 = var23.x - var24.width - var24.x;
         var10 = var1.getHeight() - var23.y - var19.getHeight();
         var11 = var1.getWidth() - var23.x - var19.getWidth();
      } else {
         var8 = var23.y;
         var9 = var23.x;
         var10 = var1.getHeight() - var23.y - var19.getHeight();
         var11 = var1.getWidth() - var19.getWidth() - var23.x - (var1.getWidth() - var24.x);
      }

      this.contentInsets[var2] = new Insets(var8, var9, var10, var11);
      Insets var12 = this.contentInsets[0];
      Insets var13 = InsetsUtil.rotate(var3.getNextCW(), var12);
      Insets var14 = InsetsUtil.max(var12, var13);
      this.adjustedContentInsets[var2] = var14;
      this.adjustedContentInsetsTabAreaHidden[var2] = new Insets(
         var3 == Direction.UP ? var14.left : var14.top,
         var3 == Direction.LEFT ? var14.top : var14.left,
         var3 == Direction.DOWN ? var14.right : var14.bottom,
         var3 == Direction.RIGHT ? var14.bottom : var14.right
      );
      var1.removeTabAt(var1.getTabCount() - 1);
      var1.setSelectedIndex(3);
      var1.doValidation();
      Rectangle var20 = var1.getBoundsAt(3);
      this.tabMinimumSizes[var2] = new Dimension(var20.width, var20.height);
      this.minimumSizes[var2] = new Dimension(
         var20.width - this.tabInsets[var2].left - this.tabInsets[var2].right, var20.height - this.tabInsets[var2].top - this.tabInsets[var2].bottom
      );
      this.calculateAreaInsets(var1, var2, var3);
      this.estimateContentTabAreaBorderColor(var1, var2, var3);
   }

   private void calculateAreaInsets(PanePainter var1, int var2, Direction var3) {
      var1.setSelectedIndex(0);
      Rectangle var4 = var1.getBoundsAt(0);
      var1.setSelectedIndex(3);
      Rectangle var5 = var1.getBoundsAt(0);
      int var6 = 0;
      int var7 = 0;
      int var8 = 0;
      int var9 = 0;
      if (var3 == Direction.UP) {
         var6 = Math.min(var4.x, var5.x);
         var7 = Math.min(var4.y, var5.y);
         var8 = 0;
      } else if (var3 == Direction.DOWN) {
         var6 = Math.min(var4.x, var5.x);
         var7 = 0;
         var8 = var1.getHeight() - Math.max(var4.y + var4.height, var5.y + var5.height);
      } else if (var3 == Direction.LEFT) {
         var7 = Math.min(var4.y, var5.y);
         var6 = Math.min(var4.x, var5.x);
         var9 = 0;
      } else {
         var7 = Math.min(var4.y, var5.y);
         var6 = 0;
         var9 = var1.getWidth() - Math.max(var4.x + var4.width, var5.x + var5.width);
      }

      Dimension var10 = var1.getSize();
      this.reset(var1);

      for(int var11 = 0; var11 < 4; ++var11) {
         var1.addTab("", SizeIcon.EMPTY, this.getComponent());
      }

      var1.setSelectedIndex(-1);
      var1.setSize(var1.getMinimumSize());
      var1.doValidation();
      if (!var3.isHorizontal()) {
         int var16 = var1.getWidth() - 1;

         for(boolean var12 = false; !var12; var12 = var1.getBoundsAt(0).y == var1.getBoundsAt(3).y) {
            var1.setSize(++var16, var1.getHeight());
            var1.doValidation();
         }

         Rectangle var13 = var1.getBoundsAt(3);
         var9 = var1.getWidth() - var13.x - var13.width - this.spacings[var2];
      } else {
         int var17 = var1.getHeight() - 1;

         for(boolean var18 = false; !var18; var18 = var1.getBoundsAt(0).x == var1.getBoundsAt(3).x) {
            var1.setSize(var1.getWidth(), ++var17);
            var1.doValidation();
         }

         Rectangle var19 = var1.getBoundsAt(3);
         var8 = var1.getHeight() - var19.y - var19.height - this.spacings[var2];
      }

      this.areaInsets[var2] = new Insets(var7, var6, var8, var9);
      var1.setSize(var10);
      var1.doValidation();
   }

   private void estimateContentTabAreaBorderColor(PanePainter var1, int var2, Direction var3) {
      Dimension var4 = var1.getSize();
      this.reset(var1);
      var1.addTab("", SizeIcon.EMPTY, this.getComponent());
      var1.setSelectedIndex(-1);
      Dimension var5 = var1.getMinimumSize();
      if (var3.isHorizontal()) {
         var1.setSize(var5.width, var5.height * 2);
      } else {
         var1.setSize(var5.width * 2, var5.height);
      }

      var1.doValidation();
      Rectangle var6 = var1.getBoundsAt(0);
      BufferedImage var7 = new BufferedImage(var1.getWidth(), var1.getHeight(), 2);
      int var8 = 0;
      int var9 = 0;
      if (var3 == Direction.UP) {
         var8 = var6.x + var6.width / 2;
         var9 = var1.getHeight() - this.contentInsets[var2].top - this.contentInsets[var2].bottom - 1;
      } else if (var3 == Direction.DOWN) {
         var8 = var6.x + var6.width / 2;
         var9 = this.contentInsets[var2].top + this.contentInsets[var2].bottom;
      } else if (var3 == Direction.LEFT) {
         var8 = var1.getWidth() - this.contentInsets[var2].left - this.contentInsets[var2].right - 1;
         var9 = var6.y + var6.height / 2;
      } else {
         var8 += this.contentInsets[var2].left + this.contentInsets[var2].right;
         var9 = var6.y + var6.height / 2;
      }

      PaneUI$2 var12 = new PaneUI$2(this, var8, var9, var3);
      FilteredImageSource var13 = new FilteredImageSource(var7.getSource(), var12);
      var1.paint(var7.getGraphics());
      BufferedImage var14 = new BufferedImage(var1.getWidth(), var1.getHeight(), 2);
      var14.getGraphics().drawImage(Toolkit.getDefaultToolkit().createImage(var13), 0, 0, null);
      var1.setSize(var4);
      var1.doValidation();
   }

   private void estimateSwappedTabDirection(PanePainter var1, int var2, Direction var3) {
      this.reset(var1);
      SizeIcon var4 = new SizeIcon(80, 80);
      SizeIcon var5 = new SizeIcon(160, 80);
      var1.addTab("", var4, this.getComponent());
      var1.doValidation();
      Rectangle var6 = var1.getBoundsAt(0);
      var1.setIconAt(0, var5);
      var1.doValidation();
      Rectangle var7 = var1.getBoundsAt(0);
      this.swapWidthHeights[var2] = (double)var7.height > 1.5 * (double)var6.height;
   }

   public boolean isContentOpaque() {
      return this.contentOpaque;
   }

   public boolean isOpaque() {
      return this.opaque;
   }

   public boolean isTabAreaComponentsOpaque() {
      return this.tabAreaComponentsOpaque;
   }

   public boolean isTabAreaOpaque() {
      return this.tabAreaOpaque;
   }

   public Font getFont() {
      return this.paneHandler.getPainter(Direction.UP).getFont();
   }

   public boolean isSwapWidthHeight(Direction var1) {
      return this.swapWidthHeights[this.getDirectionIndex(var1)];
   }

   public Insets getNormalInsets(Direction var1) {
      return this.normalInsets[this.getDirectionIndex(var1)];
   }

   public Insets getSelectedInsets(Direction var1) {
      return this.selectedInsets[this.getDirectionIndex(var1)];
   }

   public Insets getNormalTabInsets(Direction var1, Direction var2) {
      return this.getRealTabInsets(var1, var2, this.getNormalInsets(var1));
   }

   public Insets getSelectedTabInsets(Direction var1, Direction var2) {
      return this.getRealTabInsets(var1, var2, this.getSelectedInsets(var1));
   }

   private Insets getRealTabInsets(Direction var1, Direction var2, Insets var3) {
      var3 = InsetsUtil.rotate(var2, var3);
      if (this.swapWidthHeights[this.getDirectionIndex(var1)]) {
         var3 = InsetsUtil.rotate(var1.getNextCCW(), var3);
      }

      return var3;
   }

   public Insets getContentInsets(Direction var1, boolean var2) {
      return var2 ? this.adjustedContentInsets[this.getDirectionIndex(var1)] : this.adjustedContentInsetsTabAreaHidden[this.getDirectionIndex(var1)];
   }

   public Insets getTabAreaInsets(Direction var1) {
      return this.areaInsets[this.getDirectionIndex(var1)];
   }

   public Dimension getTabExternalMinSize(Direction var1) {
      return this.minimumSizes[this.getDirectionIndex(var1)];
   }

   public Insets getTabInsets(Direction var1) {
      return this.tabInsets[this.getDirectionIndex(var1)];
   }

   public int getTabSpacing(Direction var1) {
      return this.spacings[this.getDirectionIndex(var1)];
   }

   public int getSelectedRaised(Direction var1) {
      return this.raiseds[this.getDirectionIndex(var1)];
   }

   public Color getContentTabAreaBorderColor(Direction var1) {
      return this.contentTabAreaBorderColors[this.getDirectionIndex(var1)];
   }

   public int getTabSpacing() {
      return 0;
   }

   public int getTextIconGap() {
      return this.textIconGap;
   }

   public int getScrollOffset() {
      return this.scrollOffset;
   }

   private int getWidthCompensate(Direction var1) {
      return this.swapWidthHeights[this.getDirectionIndex(var1)] ? 0 : this.getTextIconGap();
   }

   private int getHeightCompensate(Direction var1) {
      return !this.swapWidthHeights[this.getDirectionIndex(var1)] ? 0 : this.getTextIconGap();
   }

   private int getDirectionIndex(Direction var1) {
      for(int var2 = 0; var2 < DIRECTIONS.length; ++var2) {
         if (DIRECTIONS[var2] == var1) {
            return var2;
         }
      }

      return 0;
   }

   private Insets getCalculatedInsets(PanePainter var1, int var2, boolean var3, Direction var4) {
      Rectangle var5 = var1.getBoundsAt(var2);
      int var6 = var5.height + var5.width;
      Icon var7 = var1.getIconAt(var2);
      var1.setIconAt(var2, new SizeIcon(var6, var6));
      if (var3) {
         var1.setSelectedIndex(var2);
      }

      Rectangle var8 = var1.getBoundsAt(var2);
      var1.setIconAt(var2, var7);
      var1.setSelectedIndex(3);
      int var9 = var8.height - var6 - this.getHeightCompensate(var4);
      int var10 = var8.width - var6 - this.getWidthCompensate(var4);
      int var11 = var9 / 2;
      int var12 = var10 / 2 + var10 % 2;
      int var13 = var9 / 2 + var9 % 2;
      int var14 = var10 / 2;
      return new Insets(var11, var12, var13, var14);
   }

   public void setHoveredTab(Tab var1) {
      if (this.enabled && var1 != this.hoveredTab) {
         if (this.hoveredTab != null && this.hoveredTab.getTabbedPanel() != null) {
            this.findDraggableComponentBox(this.hoveredTab).getParent().repaint();
         }

         this.hoveredTab = var1;
         if (this.hoveredTab != null && this.hoveredTab.getTabbedPanel() != null) {
            this.findDraggableComponentBox(this.hoveredTab).getParent().repaint();
         }
      }

   }

   public void paintTabArea(TabbedPanel var1, Graphics var2, int var3, int var4, int var5, int var6) {
      if (this.enabled && var1.isTabAreaVisible()) {
         this.tabData.initialize(var1);
         PanePainter var7 = this.paneHandler.getPainter(this.tabData.getAreaOrientation());
         this.initTabLocations(var7);
         Insets var8 = this.getTabAreaInsets(this.tabData.getAreaOrientation());
         if (var1.getTabCount() > 0) {
            if (this.tabData.getAreaOrientation() == Direction.DOWN) {
               var4 += this.tabData.getTabbedPanelHeight() - var6;
            } else if (this.tabData.getAreaOrientation() == Direction.RIGHT) {
               var3 += this.tabData.getTabbedPanelWidth() - var5;
            }

            var5 = var3 < 0 ? var5 + var3 : var5;
            var6 = var4 < 0 ? var6 + var4 : var6;
            var3 = Math.max(0, var3);
            var4 = Math.max(0, var4);
            if (this.tabData.isHorizontalLayout()) {
               var7.setSize(this.tabData.getTabbedPanelSize().width, this.getTabbedPanelExtraSize());
            } else {
               var7.setSize(this.getTabbedPanelExtraSize(), this.tabData.getTabbedPanelHeight());
            }

            if (var7.getTabCount() != 0 || this.tabData.getTabCount() <= 0) {
               Shape var9 = var2.getClip();
               int var10 = -var3
                  - (this.tabData.getAreaOrientation() == Direction.RIGHT ? -this.tabData.getTabbedPanelWidth() + this.getTabbedPanelExtraSize() : 0);
               int var11 = -var4
                  - (this.tabData.getAreaOrientation() == Direction.DOWN ? -this.tabData.getTabbedPanelHeight() + this.getTabbedPanelExtraSize() : 0);
               Rectangle var12 = (Rectangle)this.tabData.getVisibleTabRects().get(0);
               Rectangle var13 = (Rectangle)this.tabData.getVisibleTabRects().get(this.tabData.getTabCount() - 1);
               Tab var14 = (Tab)this.tabData.getTabList().get(this.tabData.getTabCount() - 1);
               if (this.tabData.isHorizontalLayout()) {
                  int var23 = var14.getWidth() == var13.width ? 0 : 2 * this.tabData.getTabbedPanelSize().width - this.tabData.getTabAreaWidth();
                  var7.setSize(var7.getWidth() + var23, var7.getHeight());
                  var7.doValidation();
                  var2.clipRect(0, 0, var8.left + (var12.width > 0 && var12.x == 0 ? 1 : 0), var6);
                  var7.paint(var2, var10, var11);
                  var2.setClip(var9);
                  var10 -= var23;
                  int var24 = var23 == 0 ? 1 : 0;
                  var2.clipRect(var8.left + this.tabData.getTabAreaWidth() - var24, 0, var5 - var8.left - this.tabData.getTabAreaWidth() + var24, var6);
                  var7.paint(var2, var10, var11);
                  var2.setClip(var9);
               } else {
                  int var15 = var14.getHeight() == var13.height ? 0 : 2 * this.tabData.getTabbedPanelSize().height - this.tabData.getTabAreaHeight();
                  var7.setSize(var7.getWidth(), var7.getHeight() + var15);
                  var7.doValidation();
                  var2.clipRect(0, 0, var5, var8.top + (var12.height > 0 && var12.y == 0 ? 1 : 0));
                  var7.paint(var2, var10, var11);
                  var2.setClip(var9);
                  var11 -= var15;
                  int var16 = var15 == 0 ? 1 : 0;
                  var2.clipRect(0, var8.top + this.tabData.getTabAreaHeight() - var16, var5, var6 - var8.top - this.tabData.getTabAreaHeight() + var16);
                  var7.paint(var2, var10, var11);
                  var2.setClip(var9);
               }
            }

            this.paintTabs(var7, this.tabData, var2, var3, var4, var5, var6, true);
            this.tabData.reset();
            this.reset(var7);
         }
      }

   }

   private void paintTabs(PanePainter var1, TabData var2, Graphics var3, int var4, int var5, int var6, int var7, boolean var8) {
      if (this.enabled) {
         Tab var9 = (Tab)var2.getTabList().get(var2.getTabList().size() - 1);
         Rectangle var10 = (Rectangle)var2.getVisibleTabRects().get(var2.getTabCount() - 1);
         this.initPaintableTabLocations(var1);
         Insets var11 = this.getTabAreaInsets(var2.getAreaOrientation());
         Point var12 = this.getLocationInTabbedPanel(var9, var2.getTabbedPanel());
         if (var2.isHorizontalLayout()) {
            int var19 = var11.left + var11.right + Math.max(0, var2.getTabAreaWidth() - var12.x - var10.width) + 2;

            for(int var21 = 0; var21 < var2.getTabList().size(); ++var21) {
               var19 += ((Tab)var2.getTabList().get(var21)).getWidth();
            }

            var1.setSize(var19, this.getTabbedPanelExtraSize());
         } else {
            int var13 = var11.top + var11.bottom + Math.max(0, var2.getTabAreaHeight() - var12.y - var10.height) + 2;

            for(int var14 = 0; var14 < var2.getTabList().size(); ++var14) {
               var13 += ((Tab)var2.getTabList().get(var14)).getHeight();
            }

            var1.setSize(this.getTabbedPanelExtraSize(), var13);
         }

         var1.doValidation();
         int var20 = var2.getPreTab() == null ? 0 : (var2.getTabCount() > 1 ? 1 : 0);
         Shape var22 = var3.getClip();
         int var15 = -var4 - (var2.getAreaOrientation() == Direction.RIGHT ? -var2.getTabbedPanelWidth() + this.getTabbedPanelExtraSize() : 0);
         int var16 = -var5 - (var2.getAreaOrientation() == Direction.DOWN ? -var2.getTabbedPanelHeight() + this.getTabbedPanelExtraSize() : 0);
         Rectangle var17 = (Rectangle)var2.getVisibleTabRects().get(var20);
         Tab var18 = (Tab)var2.getTabList().get(var20);
         if (var2.isHorizontalLayout()) {
            var15 -= var2.getPreTab() != null ? var18.getX() - var2.getPreTab().getX() + var17.x : var17.x;
            var3.clipRect(var11.left, 0, var2.getTabAreaWidth(), var7);
         } else {
            var16 -= var2.getPreTab() != null ? var18.getY() - var2.getPreTab().getY() + var17.y : var17.y;
            var3.clipRect(0, var11.top, var6, var2.getTabAreaHeight());
         }

         this.applyFocusAndHover(var1, true);
         var1.paint(var3, var15, var16);
         this.applyFocusAndHover(var1, false);
         var3.setClip(var22);
      }

   }

   private int getTabbedPanelExtraSize() {
      Insets var1 = this.getContentInsets(this.tabData.getAreaOrientation(), this.tabData.getTabbedPanel().isTabAreaVisible());
      return this.tabData.isHorizontalLayout()
         ? this.tabData.getTabAreaHeight() + var1.top + var1.bottom + 2
         : this.tabData.getTabAreaWidth() + var1.left + var1.right + 2;
   }

   public void paintContentArea(TabbedPanelContentPanel var1, Graphics var2, int var3, int var4, int var5, int var6) {
      if (this.enabled) {
         this.tabData.initialize(var1.getTabbedPanel());
         PanePainter var7 = this.paneHandler.getPainter(this.tabData.getAreaOrientation());
         this.initTabLocations(var7);
         int var8 = 0;
         int var9 = 0;
         if (this.tabData.getTabbedPanel().hasContentArea()) {
            Point var10 = this.getLocationInTabbedPanel(var1, this.tabData.getTabbedPanel());
            int var11 = 0;
            int var12 = 0;
            if (var7.getTabCount() == 0 && this.tabData.getTabCount() > 0) {
               if (this.tabData.getAreaOrientation() == Direction.UP) {
                  var11 = this.tabData.getTabAreaHeight();
               } else if (this.tabData.getAreaOrientation() == Direction.DOWN) {
                  var11 = -this.tabData.getTabAreaHeight();
               } else if (this.tabData.getAreaOrientation() == Direction.LEFT) {
                  var12 = this.tabData.getTabAreaWidth();
               } else {
                  var12 = -this.tabData.getTabAreaWidth();
               }
            }

            var8 = -var10.x + (var12 > 0 ? var12 : 0);
            var9 = -var10.y + (var11 > 0 ? var11 : 0);
            int var13 = 0;
            int var14 = 0;
            if (this.tabAreaNotVisibleFix && !this.tabData.getTabbedPanel().isTabAreaVisible()) {
               var13 = !this.tabData.isHorizontalLayout()
                  ? this.tabMinimumSizes[this.getDirectionIndex(this.tabData.getAreaOrientation())].width
                     - this.raiseds[this.getDirectionIndex(this.tabData.getAreaOrientation())]
                     + (
                        this.tabData.getAreaOrientation() == Direction.LEFT
                           ? this.areaInsets[this.getDirectionIndex(Direction.LEFT)].left
                           : this.areaInsets[this.getDirectionIndex(Direction.RIGHT)].right
                     )
                  : 0;
               var14 = this.tabData.isHorizontalLayout()
                  ? this.tabMinimumSizes[this.getDirectionIndex(this.tabData.getAreaOrientation())].height
                     - this.raiseds[this.getDirectionIndex(this.tabData.getAreaOrientation())]
                     + (
                        this.tabData.getAreaOrientation() == Direction.UP
                           ? this.areaInsets[this.getDirectionIndex(Direction.UP)].top
                           : this.areaInsets[this.getDirectionIndex(Direction.DOWN)].bottom
                     )
                  : 0;
            }

            var8 -= this.tabData.getAreaOrientation() == Direction.LEFT ? var13 : 0;
            var9 -= this.tabData.getAreaOrientation() == Direction.UP ? var14 : 0;
            var7.setSize(this.tabData.getTabbedPanelSize().width - Math.abs(var12) + var13, this.tabData.getTabbedPanelSize().height - Math.abs(var11) + var14);
            var7.doValidation();
         } else {
            if (this.tabData.isHorizontalLayout()) {
               var7.setSize(var1.getWidth(), var1.getHeight() + this.tabData.getTabAreaHeight());
            } else {
               var7.setSize(var1.getWidth() + this.tabData.getTabAreaWidth(), var1.getHeight());
            }

            var7.doValidation();
            if (this.tabData.getAreaOrientation() == Direction.UP) {
               var9 -= this.tabData.getTabAreaHeight();
            } else if (this.tabData.getAreaOrientation() == Direction.LEFT) {
               var8 -= this.tabData.getTabAreaWidth();
            }
         }

         var7.paint(var2, var8, var9);
         this.tabData.reset();
         this.reset(var7);
      }

   }

   private Component getComponent() {
      return componentCache.getComponent();
   }

   private void reset(PanePainter var1) {
      var1.removeAllTabs();
      componentCache.reset();
   }

   private Point getLocationInTabbedPanel(Component var1, TabbedPanel var2) {
      Point var3 = SwingUtilities.convertPoint(var1.getParent(), var1.getLocation(), var2);
      Insets var4 = var2.getInsets();
      var3.x -= var4.left;
      var3.y -= var4.top;
      return var3;
   }

   private void initPaintableTabLocations(PanePainter var1) {
      this.reset(var1);
      if (this.tabData.getPreTab() != null) {
         this.tabData.getTabList().add(0, this.tabData.getPreTab());
         this.tabData.getVisibleTabRects().add(0, new Rectangle(0, 0, 0, 0));
      }

      if (this.tabData.getPostTab() != null) {
         this.tabData.getTabList().add(this.tabData.getPostTab());
         this.tabData.getVisibleTabRects().add(new Rectangle(0, 0, 0, 0));
      }

      int var2 = 0;
      int var3 = -1;

      for(int var4 = 0; var4 < this.tabData.getTabCount(); ++var4) {
         Tab var5 = (Tab)this.tabData.getTabList().get(var4);
         SizeIcon var6 = new SizeIcon(
            this.getInternalTabWidth(var5) - this.getWidthCompensate(this.tabData.getAreaOrientation()),
            this.getInternalTabHeight(var5) - this.getHeightCompensate(this.tabData.getAreaOrientation()),
            this.isSwapWidthHeight(this.tabData.getAreaOrientation())
         );
         var1.addTab("", var6, this.getComponent());
         if (var5.isHighlighted()) {
            var3 = var1.getTabCount() - 1;
         }

         if (!var5.isEnabled()) {
            var1.setEnabledAt(var4, false);
            var1.setDisabledIconAt(var4, var6);
         }

         var2 += this.tabData.isHorizontalLayout() ? var5.getWidth() : var5.getHeight();
      }

      var1.setSelectedIndex(var3);
      var1.doValidation();
   }

   private void applyFocusAndHover(PanePainter var1, boolean var2) {
      if (var2) {
         for(int var3 = 0; var3 < this.tabData.getTabCount(); ++var3) {
            Tab var4 = (Tab)this.tabData.getTabList().get(var3);
            if (var4.getFocusableComponent() != null && var4.getFocusableComponent().hasFocus()) {
               var1.setMouseEntered(true);
               var1.setFocusActive(true);
               break;
            }
         }

         if (this.hoveredTab != null) {
            for(int var5 = 0; var5 < this.tabData.getTabCount(); ++var5) {
               Tab var6 = (Tab)this.tabData.getTabList().get(var5);
               if (var6 == this.hoveredTab) {
                  var1.setMouseEntered(true);
                  var1.setHoveredTab(var5);
                  break;
               }
            }
         }
      } else {
         var1.setFocusActive(false);
         var1.setMouseEntered(false);
      }

   }

   private int getInternalTabWidth(Tab var1) {
      Direction var2 = var1.getTabbedPanel().getProperties().getTabAreaOrientation();
      Insets var3 = var1.isHighlighted() ? this.getSelectedInsets(var2) : this.getNormalInsets(var2);
      int var4 = var1.getWidth();
      var4 -= var3.left + var3.right;
      if (var2 == Direction.LEFT || var2 == Direction.RIGHT) {
         var4 -= this.getSelectedRaised(var2);
      }

      return var4;
   }

   private int getInternalTabHeight(Tab var1) {
      Direction var2 = var1.getTabbedPanel().getProperties().getTabAreaOrientation();
      Insets var3 = var1.isHighlighted() ? this.getSelectedInsets(var2) : this.getNormalInsets(var2);
      int var4 = var1.getHeight();
      var4 -= var3.top + var3.bottom;
      if (var2 == Direction.UP || var2 == Direction.DOWN) {
         var4 -= this.getSelectedRaised(var2);
      }

      return var4;
   }

   private void initTabLocations(PanePainter var1) {
      this.findPaintableTabs();
      Dimension var2 = this.getTabExternalMinSize(this.tabData.getAreaOrientation());
      Insets var3 = this.getTabAreaInsets(this.tabData.getAreaOrientation());
      int var4 = -1;
      if (this.tabData.getTabbedPanel().isTabAreaVisible()) {
         for(int var5 = 0; var5 < this.tabData.getTabCount(); ++var5) {
            Tab var6 = (Tab)this.tabData.getTabList().get(var5);
            Rectangle var7 = (Rectangle)this.tabData.getVisibleTabRects().get(var5);
            Insets var8 = this.getTabInsets(this.tabData.getAreaOrientation());
            int var9 = Math.max(-var8.left - var8.right, this.getInternalTabWidth(var6) - (var6.getWidth() - var7.width));
            int var10 = Math.max(-var8.top - var8.bottom, this.getInternalTabHeight(var6) - (var6.getHeight() - var7.height));
            Point var11 = this.getLocationInTabbedPanel(var6, this.tabData.getTabbedPanel());
            if (this.tabData.isHorizontalLayout() && (var7.width >= var2.width || var2.width < this.tabData.getTabbedPanelWidth() - var11.x - var3.right)
               || !this.tabData.isHorizontalLayout()
                  && (var7.height >= var2.height || var2.height < this.tabData.getTabbedPanelHeight() - var11.y - var3.bottom)) {
               SizeIcon var14 = new SizeIcon(
                  var9 - this.getWidthCompensate(this.tabData.getAreaOrientation()),
                  var10 - this.getHeightCompensate(this.tabData.getAreaOrientation()),
                  this.isSwapWidthHeight(this.tabData.getAreaOrientation())
               );
               int var15 = var1.getTabCount();
               var1.addTab("", var14, this.getComponent());
               if (var5 == this.tabData.getSelectedTabPainterIndex()) {
                  var4 = var15;
               }

               if (!var6.isEnabled()) {
                  var1.setEnabledAt(var15, false);
                  var1.setDisabledIconAt(var15, var14);
               }
            }
         }
      } else if (this.tabAreaNotVisibleFix) {
         var1.addTab("", componentCache.getComponent());
      }

      if (var1.getTabCount() > 0) {
         var1.setSelectedIndex(var4);
      }

      var1.doValidation();
   }

   private void findPaintableTabs() {
      Tab var1 = null;
      Rectangle var2 = null;
      Tab var3 = null;
      int var4 = 0;
      boolean var5 = false;
      if (this.tabData.getTabbedPanel().isTabAreaVisible()) {
         while(true) {
            if (var4 < this.tabData.getTabbedPanel().getTabCount()) {
               Tab var6 = this.tabData.getTabbedPanel().getTabAt(var4);
               Rectangle var7 = var6.getVisibleRect();
               if (var4 == 0) {
                  var1 = var6;
                  var2 = var7;
               }

               ++var4;
               if (var7.width > 0 && var7.height > 0) {
                  var5 = true;
                  this.tabData.getTabList().add(var6);
                  this.tabData.getVisibleTabRects().add(var7);
                  if (this.tabData.getTabCount() == 1) {
                     this.tabData.setPreTab(var3);
                  }

                  if (var6.isHighlighted()) {
                     this.tabData.setSelectedTabPainterIndex(this.tabData.getTabCount() - 1);
                  }
               } else if (this.tabData.getTabList().size() > 0 && (var7.width == 0 || var7.height == 0)) {
                  this.tabData.setPostTab(var6);
               }

               if (!var5
                  || var7.x != 0
                  || var7.y != 0
                  || (!this.tabData.isHorizontalLayout() || var7.width >= var6.getWidth())
                     && (this.tabData.isHorizontalLayout() || var7.height >= var6.getHeight())) {
                  var3 = var6;
                  continue;
               }
            }

            if (var1 != null) {
               Component var8 = this.findDraggableComponentBox(var1);
               if (var8 != null) {
                  if (this.tabData.isHorizontalLayout()) {
                     this.tabData.setTabAreaWidth(var8.getWidth());
                     this.tabData.setTabAreaHeight(var8.getParent().getHeight());
                  } else {
                     this.tabData.setTabAreaWidth(var8.getParent().getWidth());
                     this.tabData.setTabAreaHeight(var8.getHeight());
                  }
               }

               if (this.tabData.getTabCount() == 0) {
                  this.tabData.getTabList().add(var1);
                  this.tabData.getVisibleTabRects().add(var2);
               }
            }
            break;
         }
      }

   }

   private Component findDraggableComponentBox(Component var1) {
      return var1 != null && !(var1 instanceof DraggableComponentBox) ? this.findDraggableComponentBox(var1.getParent()) : var1;
   }
}
