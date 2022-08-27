package net.infonode.util;

import java.io.Serializable;

public class ProductVersion implements Serializable {
   private static final long serialVersionUID = 1L;
   private int major;
   private int minor;
   private int patch;

   public ProductVersion(int var1, int var2, int var3) {
      super();
      this.major = var1;
      this.minor = var2;
      this.patch = var3;
   }

   public int getMajor() {
      return this.major;
   }

   public int getMinor() {
      return this.minor;
   }

   public int getPatch() {
      return this.patch;
   }

   public String toString() {
      return this.major + "." + this.minor + "." + this.patch;
   }
}
