package net.infonode.util.math;

public final class Int4 {
   private int a;
   private int b;
   private int c;
   private int d;

   public Int4() {
      this(0, 0, 0, 0);
   }

   public Int4(Int4 var1) {
      this(var1.a, var1.b, var1.c, var1.d);
   }

   public Int4(int var1, int var2, int var3, int var4) {
      super();
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public int getA() {
      return this.a;
   }

   public void setA(int var1) {
      this.a = var1;
   }

   public int getB() {
      return this.b;
   }

   public void setB(int var1) {
      this.b = var1;
   }

   public int getC() {
      return this.c;
   }

   public void setC(int var1) {
      this.c = var1;
   }

   public int getD() {
      return this.d;
   }

   public void setD(int var1) {
      this.d = var1;
   }

   public Int4 set(Int4 var1) {
      this.a = var1.a;
      this.b = var1.b;
      this.c = var1.c;
      this.d = var1.d;
      return this;
   }

   public Int4 add(Int4 var1) {
      this.a += var1.a;
      this.b += var1.b;
      this.c += var1.c;
      this.d += var1.d;
      return this;
   }

   public Int4 sub(Int4 var1) {
      this.a -= var1.a;
      this.b -= var1.b;
      this.c -= var1.c;
      this.d -= var1.d;
      return this;
   }

   public Int4 div(long var1) {
      this.a = (int)((long)this.a / var1);
      this.b = (int)((long)this.b / var1);
      this.c = (int)((long)this.c / var1);
      this.d = (int)((long)this.d / var1);
      return this;
   }

   public Int4 mul(long var1) {
      this.a = (int)((long)this.a * var1);
      this.b = (int)((long)this.b * var1);
      this.c = (int)((long)this.c * var1);
      this.d = (int)((long)this.d * var1);
      return this;
   }

   public String toString() {
      return this.a + ", " + this.b + ", " + this.c + ", " + this.d;
   }
}
