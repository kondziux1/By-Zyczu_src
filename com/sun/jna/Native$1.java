package com.sun.jna;

final class Native$1 implements Callback.UncaughtExceptionHandler {
   Native$1() {
      super();
   }

   public void uncaughtException(Callback c, Throwable e) {
      System.err.println("JNA: Callback " + c + " threw the following exception:");
      e.printStackTrace();
   }
}
