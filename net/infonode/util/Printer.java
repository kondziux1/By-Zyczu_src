package net.infonode.util;

import java.io.PrintStream;

public class Printer {
   private PrintStream out;
   private String indent = "";
   private boolean newLine = true;

   public Printer() {
      this(System.out);
   }

   public Printer(PrintStream var1) {
      super();
      this.out = var1;
   }

   public void beginSection() {
      this.indent = this.indent + "  ";
   }

   public void beginSection(String var1) {
      this.println(var1);
      this.indent = this.indent + "  ";
   }

   public void endSection() {
      this.indent = this.indent.substring(2);
   }

   public void print(String var1) {
      if (this.newLine) {
         this.out.print(this.indent);
      }

      this.out.print(var1);
      this.newLine = false;
   }

   public void println(String var1) {
      if (this.newLine) {
         this.out.print(this.indent);
      }

      this.out.println(var1);
      this.newLine = true;
   }

   public void println() {
      if (this.newLine) {
         this.out.print(this.indent);
      }

      this.out.println();
      this.newLine = true;
   }
}
