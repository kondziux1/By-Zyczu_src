package de.javasoft.plaf.synthetica.painter;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.Window;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;

class AnimationThreadFactory {
   static final int DISABLED_TYPE = -1;
   static final int DEFAULT_TYPE = 0;
   static final int HOVER_IN_TYPE = 1;
   static final int HOVER_OUT_TYPE = 2;
   static final int FOCUSED_TYPE = 3;
   static final int DISPOSABLE_TYPE = 4;
   static final int ACTIVE_TYPE = 5;
   static final int INACTIVE_TYPE = 6;
   private static HashMap<Integer, AnimationThreadFactory.AnimationThread> threads = new HashMap();
   private static AnimationThreadFactory instance = new AnimationThreadFactory();

   private AnimationThreadFactory() {
      super();
   }

   public static AnimationThreadFactory.AnimationThread createThread(int var0) {
      return threads.containsKey(var0) ? (AnimationThreadFactory.AnimationThread)threads.get(var0) : instance.newThread(var0);
   }

   private AnimationThreadFactory.AnimationThread newThread(int var1) {
      AnimationThreadFactory.AnimationThread var2 = new AnimationThreadFactory.AnimationThread(var1);
      threads.put(var1, var2);
      var2.setDaemon(true);
      var2.start();
      return var2;
   }

   static class AnimationThread extends Thread {
      private int delay = 0;
      private HashMap<AnimationThreadFactory.AnimationThread.ComponentKey, AnimationThreadFactory.AnimationThread.ComponentAnimation> componentAnimations = new HashMap(
         
      );
      private ReferenceQueue<JComponent> queue = new ReferenceQueue();

      public AnimationThread(int var1) {
         super();
         this.delay = var1;
         this.setPriority(7);
         this.setName("SyntheticaAnimation " + var1);
      }

      public void run() {
         while(!this.isInterrupted()) {
            try {
               sleep((long)this.delay);
               Object var1 = null;
               HashMap var16;
               synchronized(this.componentAnimations) {
                  for(Reference var3 = this.queue.poll(); var3 != null; var3 = this.queue.poll()) {
                     this.componentAnimations.remove(((AnimationThreadFactory.AnimationThread.WeakComponent)var3).key);
                  }

                  var16 = new HashMap(this.componentAnimations);
               }

               for(Entry var17 : var16.entrySet()) {
                  JComponent var4 = (JComponent)((AnimationThreadFactory.AnimationThread.ComponentKey)var17.getKey()).wc.get();
                  if (var4 != null) {
                     AnimationThreadFactory.AnimationThread.ComponentAnimation var5 = (AnimationThreadFactory.AnimationThread.ComponentAnimation)var17.getValue(
                        
                     );
                     if (var4 != null && var5 != null) {
                        synchronized(var5) {
                           boolean var7 = (var5.state & 2) > 0;
                           boolean var8 = var4.hasFocus();
                           Rectangle var9 = var5.repaintRect;
                           switch(var5.type) {
                              case 1:
                                 if (var5.state != -1 && var7 && var5.next()) {
                                    this.repaint(var4, var9);
                                 } else if (var5.state != -1 && !var7) {
                                    var5.reset();
                                 }
                                 break;
                              case 2:
                                 if (var5.state != -1 && !var7 && var5.next()) {
                                    this.repaint(var4, var9);
                                 } else if (var5.state != -1 && var7) {
                                    var5.reset();
                                 }
                                 break;
                              case 3:
                                 if (var4 instanceof JComboBox && ((JComboBox)var4).isEditable() && ((JComboBox)var4).getEditor() != null) {
                                    Component var20 = ((JComboBox)var4).getEditor().getEditorComponent();
                                    if (var20 != null) {
                                       var8 = var20.hasFocus();
                                    }
                                 } else if (var4 instanceof JSpinner) {
                                    Component var10 = SyntheticaLookAndFeel.findComponent("Spinner.formattedTextField", var4);
                                    if (var10 != null) {
                                       var8 = var10.hasFocus();
                                    }
                                 } else if (var4 instanceof JScrollPane) {
                                    JScrollPane var19 = (JScrollPane)var4;
                                    if (var19.getViewport().getView() != null) {
                                       var8 = var19.getViewport().getView().hasFocus();
                                    }

                                    int var11 = SyntheticaLookAndFeel.getInt("Synthetica.focus.scrollPane.animation.maxWidth", var19, 400);
                                    int var12 = SyntheticaLookAndFeel.getInt("Synthetica.focus.scrollPane.animation.maxHeight", var19, 200);
                                    if (var19.getWidth() > var11 || var19.getHeight() > var12) {
                                       break;
                                    }
                                 }

                                 if (var5.next() && var8 && this.isActive(var4)) {
                                    this.repaint(var4, var9);
                                 } else if (!var8) {
                                    var5.reset();
                                 }
                                 break;
                              case 4:
                              case 5:
                              case 6:
                                 if (var5.next()) {
                                    this.repaint(var4, var9);
                                 }
                                 break;
                              default:
                                 if (var5.next() && this.isActive(var4)) {
                                    this.repaint(var4, var9);
                                 }
                           }
                        }
                     }
                  }
               }
            } catch (InterruptedException var15) {
               this.interrupt();
            }
         }

      }

      private void repaint(JComponent var1, Rectangle var2) {
         if (var2 == null) {
            var1.repaint();
         } else {
            var1.repaint(0L, var2.x, var2.y, var2.width, var2.height);
         }

      }

      private boolean isActive(JComponent var1) {
         Container var2 = var1.getTopLevelAncestor();
         return var2 instanceof Window && !"###focusableSwingPopup###".equals(var2.getName()) ? ((Window)var2).isActive() : true;
      }

      public void addComponent(JComponent var1, String var2, int var3, int var4, String[] var5, int var6, Rectangle var7) {
         boolean var8 = (var3 & 2) > 0;
         AnimationThreadFactory.AnimationThread.ComponentKey var9 = new AnimationThreadFactory.AnimationThread.ComponentKey(var1, var2);
         if (!this.componentAnimations.containsKey(var9)) {
            var9.setComponent(var1);
            AnimationThreadFactory.AnimationThread.ComponentAnimation var13 = new AnimationThreadFactory.AnimationThread.ComponentAnimation(null);
            var13.imagePaths = var5;
            var13.cyles = (long)var6;
            var13.repaintRect = var7;
            var13.type = var4;
            if (var8) {
               var13.state = var3;
            } else if (var4 == 6) {
               var13.cylesDone = (long)var6;
            }

            synchronized(this.componentAnimations) {
               this.componentAnimations.put(var9, var13);
            }
         } else {
            AnimationThreadFactory.AnimationThread.ComponentAnimation var10 = (AnimationThreadFactory.AnimationThread.ComponentAnimation)this.componentAnimations
               .get(var9);
            var10.repaintRect = var7;
            if (var10.type != var4 || !Arrays.toString(var5).equals(Arrays.toString(var10.imagePaths))) {
               var10.type = var4;
               var10.imagePaths = var5;
               var10.cyles = (long)var6;
               var10.reset();
            }

            if (var10.state != -1 || var8) {
               var10.state = var3;
            }

         }
      }

      void rotateRepaintRect(JComponent var1, String var2) {
         AnimationThreadFactory.AnimationThread.ComponentKey var3 = new AnimationThreadFactory.AnimationThread.ComponentKey(var1, var2);
         AnimationThreadFactory.AnimationThread.ComponentAnimation var4 = (AnimationThreadFactory.AnimationThread.ComponentAnimation)this.componentAnimations
            .get(var3);
         if (var4 != null) {
            int var5 = var4.repaintRect.height;
            var4.repaintRect.height = var4.repaintRect.width;
            var4.repaintRect.width = var5;
         }

      }

      public String getImagePath(JComponent var1, String var2) {
         AnimationThreadFactory.AnimationThread.ComponentAnimation var3 = (AnimationThreadFactory.AnimationThread.ComponentAnimation)this.componentAnimations
            .get(new AnimationThreadFactory.AnimationThread.ComponentKey(var1, var2));
         Object var4 = null;
         synchronized(var3) {
            String var7;
            if (var3.type != 2 || var3.state != -1 && !var3.isComplete()) {
               if (var3.type == 6 && var3.isComplete()) {
                  var7 = var3.imagePaths[var3.imagePaths.length - 1];
               } else {
                  var7 = var3.imagePaths[var3.index];
               }
            } else {
               var7 = var3.imagePaths[var3.imagePaths.length - 1];
            }

            return var7;
         }
      }

      private static class ComponentAnimation {
         private int state = -1;
         private int type = 0;
         private int index = 0;
         private String[] imagePaths;
         private Rectangle repaintRect;
         private long cyles = 0L;
         private long cylesDone = 0L;

         private ComponentAnimation() {
            super();
         }

         boolean next() {
            if (this.cyles != 0L && this.cylesDone != this.cyles && this.imagePaths.length != 1) {
               ++this.index;
               if (this.index == this.imagePaths.length - 1) {
                  ++this.cylesDone;
               } else if (this.index == this.imagePaths.length) {
                  this.index = 0;
               }

               return true;
            } else {
               return false;
            }
         }

         void reset() {
            this.index = 0;
            this.cylesDone = 0L;
         }

         boolean isComplete() {
            return this.cyles == this.cylesDone;
         }
      }

      private class ComponentKey {
         private int hashCode;
         private AnimationThreadFactory.AnimationThread.WeakComponent wc;

         ComponentKey(JComponent var2, String var3) {
            super();
            this.hashCode = var2.hashCode() * 31 + var3.hashCode();
         }

         void setComponent(JComponent var1) {
            this.wc = AnimationThread.this.new WeakComponent(var1, this);
         }

         public boolean equals(Object var1) {
            AnimationThreadFactory.AnimationThread.ComponentKey var2 = (AnimationThreadFactory.AnimationThread.ComponentKey)var1;
            return var2.hashCode == this.hashCode;
         }

         public int hashCode() {
            return this.hashCode;
         }
      }

      private class WeakComponent extends WeakReference<JComponent> {
         private AnimationThreadFactory.AnimationThread.ComponentKey key;

         public WeakComponent(JComponent var2, AnimationThreadFactory.AnimationThread.ComponentKey var3) {
            super(var2, AnimationThread.this.queue);
            this.key = var3;
         }
      }
   }
}
