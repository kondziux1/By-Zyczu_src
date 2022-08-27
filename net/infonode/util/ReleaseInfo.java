package net.infonode.util;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class ReleaseInfo implements Serializable {
   private static final long serialVersionUID = 1L;
   private String productName;
   private String productVendor;
   private String license;
   private long buildTime;
   private ProductVersion productVersion;
   private URL homepage;

   public ReleaseInfo(String var1, String var2, long var3, ProductVersion var5, String var6, String var7) {
      super();
      this.productName = var1;
      this.productVendor = var2;
      this.buildTime = var3;
      this.productVersion = var5;
      this.license = var6;

      try {
         this.homepage = new URL(var7);
      } catch (MalformedURLException var9) {
         throw new RuntimeException(var9);
      }
   }

   public String getProductName() {
      return this.productName;
   }

   public String getProductVendor() {
      return this.productVendor;
   }

   public String getLicense() {
      return this.license;
   }

   public long getBuildTime() {
      return this.buildTime;
   }

   public ProductVersion getProductVersion() {
      return this.productVersion;
   }

   public URL getHomepage() {
      return this.homepage;
   }
}
