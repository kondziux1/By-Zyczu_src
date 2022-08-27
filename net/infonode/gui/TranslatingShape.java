package net.infonode.gui;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class TranslatingShape implements Shape {
   private Shape shape;
   private double dx;
   private double dy;

   public TranslatingShape(Shape var1, double var2, double var4) {
      super();
      this.shape = var1;
      this.dx = var2;
      this.dy = var4;
   }

   public Rectangle getBounds() {
      Rectangle var1 = this.shape.getBounds();
      var1.translate((int)this.dx, (int)this.dy);
      return var1;
   }

   public Rectangle2D getBounds2D() {
      Rectangle2D var1 = this.shape.getBounds2D();
      var1.setRect(var1.getMinX() + this.dx, var1.getMinY() + this.dy, var1.getWidth(), var1.getHeight());
      return var1;
   }

   public boolean contains(double var1, double var3) {
      return this.shape.contains(var1 - this.dx, var3 - this.dy);
   }

   public boolean contains(Point2D var1) {
      return this.contains(var1.getX(), var1.getY());
   }

   public boolean intersects(double var1, double var3, double var5, double var7) {
      return this.shape.intersects(var1 - this.dx, var3 - this.dy, var5, var7);
   }

   public boolean intersects(Rectangle2D var1) {
      return this.intersects(var1.getMinX(), var1.getMinY(), var1.getWidth(), var1.getHeight());
   }

   public boolean contains(double var1, double var3, double var5, double var7) {
      return this.shape.contains(var1 - this.dx, var3 - this.dy, var5, var7);
   }

   public boolean contains(Rectangle2D var1) {
      return this.contains(var1.getMinX() - this.dx, var1.getMinY() - this.dy, var1.getWidth(), var1.getHeight());
   }

   public PathIterator getPathIterator(AffineTransform var1) {
      return new TranslatingShape.Iterator(this.shape.getPathIterator(var1));
   }

   public PathIterator getPathIterator(AffineTransform var1, double var2) {
      return new TranslatingShape.Iterator(this.shape.getPathIterator(var1, var2));
   }

   private class Iterator implements PathIterator {
      private PathIterator iterator;

      Iterator(PathIterator var2) {
         super();
         this.iterator = var2;
      }

      public int getWindingRule() {
         return this.iterator.getWindingRule();
      }

      public boolean isDone() {
         return this.iterator.isDone();
      }

      public void next() {
         this.iterator.next();
      }

      public int currentSegment(float[] var1) {
         int var2 = this.iterator.currentSegment(var1);

         for(int var3 = 0; var3 < var1.length; ++var3) {
            int var10001 = var3++;
            var1[var10001] = (float)((double)var1[var10001] + TranslatingShape.this.dx);
            var1[var3] = (float)((double)var1[var3] + TranslatingShape.this.dy);
         }

         return var2;
      }

      public int currentSegment(double[] var1) {
         int var2 = this.iterator.currentSegment(var1);

         for(int var3 = 0; var3 < var1.length; ++var3) {
            int var10001 = var3++;
            var1[var10001] += TranslatingShape.this.dx;
            var1[var3] += TranslatingShape.this.dy;
         }

         return var2;
      }
   }
}
