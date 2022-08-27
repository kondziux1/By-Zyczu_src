package net.infonode.gui.icon.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.UIManager;
import net.infonode.gui.ComponentUtil;
import net.infonode.util.ColorUtil;

public abstract class AbstractButtonIcon implements Icon, Serializable {
   private static final long serialVersionUID = 1L;
   private int size = 10;
   private Color defaultColor = null;
   private boolean shadowEnabled = true;
   private float shadowStrength = 0.3F;
   private boolean enabled = true;

   public AbstractButtonIcon() {
      super();
   }

   public AbstractButtonIcon(Color var1) {
      super();
      this.defaultColor = var1;
   }

   public AbstractButtonIcon(Color var1, int var2) {
      this(var1);
      this.size = var2;
   }

   public AbstractButtonIcon(int var1) {
      this(var1, true);
   }

   public AbstractButtonIcon(int var1, boolean var2) {
      this();
      this.size = var1;
      this.enabled = var2;
   }

   public int getIconWidth() {
      return this.size;
   }

   public int getIconHeight() {
      return this.size;
   }

   public boolean isShadowEnabled() {
      return this.shadowEnabled;
   }

   public void setShadowEnabled(boolean var1) {
      this.shadowEnabled = var1;
   }

   public float getShadowStrength() {
      return this.shadowStrength;
   }

   public void setShadowStrength(float var1) {
      this.shadowStrength = var1;
   }

   public void paintIcon(Component var1, Graphics var2, int var3, int var4) {
      Color var5 = var2.getColor();
      Color var6 = this.defaultColor == null ? (this.enabled ? var1.getForeground() : UIManager.getColor("Button.disabledForeground")) : this.defaultColor;
      if (var6 == null) {
         var6 = ColorUtil.blend(ComponentUtil.getBackgroundColor(var1), var1.getForeground(), 0.5);
      }

      if (this.shadowEnabled) {
         Color var7 = ComponentUtil.getBackgroundColor(var1);
         var2.setColor(ColorUtil.blend(var7 == null ? Color.BLACK : var7, Color.BLACK, (double)this.shadowStrength));
         this.paintIcon(var1, var2, var3 + 2, var4 + 2, var3 + this.size - 1, var4 + this.size - 1, true);
         var2.setColor(var6);
         this.paintIcon(var1, var2, var3 + 1, var4 + 1, var3 + this.size - 2, var4 + this.size - 2, false);
      } else {
         var2.setColor(var6);
         this.paintIcon(var1, var2, var3, var4, var3 + this.size - 1, var4 + this.size - 1, false);
      }

      var2.setColor(var5);
   }

   protected void paintIcon(Component var1, Graphics var2, int var3, int var4, int var5, int var6, boolean var7) {
      this.paintIcon(var1, var2, var3, var4, var5, var6);
   }

   protected void paintIcon(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }
}
