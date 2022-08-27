package net.infonode.docking.theme.internal.laftheme;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.UIManager;
import net.infonode.tabbedpanel.theme.internal.laftheme.SizeIcon;

class TitleBarUI$4 implements Runnable {
   TitleBarUI$4(TitleBarUI var1) {
      super();
      this.this$0 = var1;
   }

   public void run() {
      TitleBarUI.access$200(this.this$0).setClosable(false);
      TitleBarUI.access$200(this.this$0).setMaximizable(false);
      TitleBarUI.access$200(this.this$0).setIconifiable(false);
      TitleBarUI.access$200(this.this$0).setBounds(0, 0, 50, 50);
      TitleBarUI.access$200(this.this$0).setResizable(false);
      TitleBarUI.access$200(this.this$0).setVisible(true);
      TitleBarUI.access$200(this.this$0).setTitle(" ");
      TitleBarUI.access$200(this.this$0).setFrameIcon(SizeIcon.EMPTY);
      TitleBarUI.access$302(this.this$0, (Insets)TitleBarUI.access$200(this.this$0).getInsets().clone());
      if (UIManager.getLookAndFeel().getClass().getName().indexOf(".MotifLookAndFeel") != -1) {
         Insets var10000 = TitleBarUI.access$300(this.this$0);
         var10000.left += 19;
      }

      TitleBarUI.access$402(this.this$0, TitleBarUI.access$200(this.this$0).getPreferredSize());
      TitleBarUI.access$502(
         this.this$0,
         new Dimension(
            Math.max(0, TitleBarUI.access$400(this.this$0).width - TitleBarUI.access$300(this.this$0).left - TitleBarUI.access$300(this.this$0).right),
            TitleBarUI.access$400(this.this$0).height - TitleBarUI.access$300(this.this$0).top - TitleBarUI.access$300(this.this$0).bottom
         )
      );
      String var1 = UIManager.getLookAndFeel().getClass().getName();
      TitleBarUI.access$602(
         this.this$0,
         var1.indexOf("GTKLookAndFeel") != -1
            || (var1.indexOf(".WindowsLookAndFeel") != -1 || UIManager.getLookAndFeel().getClass().getName().indexOf(".Office2003LookAndFeel") != -1)
               && Toolkit.getDefaultToolkit().getDesktopProperty("win.xpstyle.themeActive") != null
      );
      TitleBarUI.access$700(this.this$0);
      this.this$0.setEnabled(true);
      TitleBarUI.access$100(this.this$0).updated();
   }
}
