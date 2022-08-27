package com.sun.jna;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class NativeLibrary {
   private long handle;
   private final String libraryName;
   private final String libraryPath;
   private final Map functions = new HashMap();
   final int callFlags;
   final Map options;
   private static final Map libraries = new HashMap();
   private static final Map searchPaths = Collections.synchronizedMap(new HashMap());
   private static final List librarySearchPath = new LinkedList();
   private static final int DEFAULT_OPEN_OPTIONS = -1;

   private static String functionKey(String name, int flags) {
      return name + "|" + flags;
   }

   private NativeLibrary(String libraryName, String libraryPath, long handle, Map options) {
      super();
      this.libraryName = this.getLibraryName(libraryName);
      this.libraryPath = libraryPath;
      this.handle = handle;
      Object option = options.get("calling-convention");
      int callingConvention = option instanceof Integer ? (Integer)option : 0;
      this.callFlags = callingConvention;
      this.options = options;
      if (Platform.isWindows() && "kernel32".equals(this.libraryName.toLowerCase())) {
         synchronized(this.functions) {
            Function f = new NativeLibrary$1(this, this, "GetLastError", 1);
            this.functions.put(functionKey("GetLastError", this.callFlags), f);
         }
      }

   }

   private static int openFlags(Map options) {
      try {
         return options.get("open-flags");
      } catch (Throwable var2) {
         return -1;
      }
   }

   private static NativeLibrary loadLibrary(String libraryName, Map options) {
      List searchPath = new LinkedList();
      int openFlags = openFlags(options);
      String webstartPath = Native.getWebStartLibraryPath(libraryName);
      if (webstartPath != null) {
         searchPath.add(webstartPath);
      }

      List customPaths = (List)searchPaths.get(libraryName);
      if (customPaths != null) {
         synchronized(customPaths) {
            searchPath.addAll(0, customPaths);
         }
      }

      searchPath.addAll(initPaths("jna.library.path"));
      String libraryPath = findLibraryPath(libraryName, searchPath);
      long handle = 0L;

      try {
         handle = Native.open(libraryPath, openFlags);
      } catch (UnsatisfiedLinkError var15) {
         searchPath.addAll(librarySearchPath);
      }

      try {
         if (handle == 0L) {
            libraryPath = findLibraryPath(libraryName, searchPath);
            handle = Native.open(libraryPath, openFlags);
            if (handle == 0L) {
               throw new UnsatisfiedLinkError("Failed to load library '" + libraryName + "'");
            }
         }
      } catch (UnsatisfiedLinkError var17) {
         UnsatisfiedLinkError e = var17;
         if (Platform.isAndroid()) {
            try {
               System.loadLibrary(libraryName);
               handle = Native.open(libraryPath, openFlags);
            } catch (UnsatisfiedLinkError var14) {
               e = var14;
            }
         } else if (Platform.isLinux()) {
            libraryPath = matchLibrary(libraryName, searchPath);
            if (libraryPath != null) {
               try {
                  handle = Native.open(libraryPath, openFlags);
               } catch (UnsatisfiedLinkError var13) {
                  e = var13;
               }
            }
         } else if (Platform.isMac() && !libraryName.endsWith(".dylib")) {
            libraryPath = matchFramework(libraryName);
            if (libraryPath != null) {
               try {
                  handle = Native.open(libraryPath, openFlags);
               } catch (UnsatisfiedLinkError var12) {
                  e = var12;
               }
            }
         } else if (Platform.isWindows()) {
            libraryPath = findLibraryPath("lib" + libraryName, searchPath);

            try {
               handle = Native.open(libraryPath, openFlags);
            } catch (UnsatisfiedLinkError var11) {
               e = var11;
            }
         }

         if (handle == 0L) {
            throw new UnsatisfiedLinkError("Unable to load library '" + libraryName + "': " + e.getMessage());
         }
      }

      return new NativeLibrary(libraryName, libraryPath, handle, options);
   }

   static String matchFramework(String libraryName) {
      String[] PREFIXES = new String[]{System.getProperty("user.home"), "", "/System"};
      String suffix = libraryName.indexOf(".framework") == -1 ? libraryName + ".framework/" + libraryName : libraryName;

      for(int i = 0; i < PREFIXES.length; ++i) {
         String libraryPath = PREFIXES[i] + "/Library/Frameworks/" + suffix;
         if (new File(libraryPath).exists()) {
            return libraryPath;
         }
      }

      return null;
   }

   private String getLibraryName(String libraryName) {
      String simplified = libraryName;
      String BASE = "---";
      String template = mapLibraryName("---");
      int prefixEnd = template.indexOf("---");
      if (prefixEnd > 0 && libraryName.startsWith(template.substring(0, prefixEnd))) {
         simplified = libraryName.substring(prefixEnd);
      }

      String suffix = template.substring(prefixEnd + "---".length());
      int suffixStart = simplified.indexOf(suffix);
      if (suffixStart != -1) {
         simplified = simplified.substring(0, suffixStart);
      }

      return simplified;
   }

   public static final NativeLibrary getInstance(String libraryName) {
      return getInstance(libraryName, Collections.EMPTY_MAP);
   }

   public static final NativeLibrary getInstance(String libraryName, Map options) {
      options = new HashMap(options);
      if (options.get("calling-convention") == null) {
         options.put("calling-convention", new Integer(0));
      }

      if ((Platform.isLinux() || Platform.isAix()) && Platform.C_LIBRARY_NAME.equals(libraryName)) {
         libraryName = null;
      }

      synchronized(libraries) {
         WeakReference ref = (WeakReference)libraries.get(libraryName + options);
         NativeLibrary library = ref != null ? (NativeLibrary)ref.get() : null;
         if (library == null) {
            if (libraryName == null) {
               library = new NativeLibrary("<process>", null, Native.open(null, openFlags(options)), options);
            } else {
               library = loadLibrary(libraryName, options);
            }

            ref = new WeakReference(library);
            libraries.put(library.getName() + options, ref);
            File file = library.getFile();
            if (file != null) {
               libraries.put(file.getAbsolutePath() + options, ref);
               libraries.put(file.getName() + options, ref);
            }
         }

         return library;
      }
   }

   public static final synchronized NativeLibrary getProcess() {
      return getInstance(null);
   }

   public static final synchronized NativeLibrary getProcess(Map options) {
      return getInstance(null, options);
   }

   public static final void addSearchPath(String libraryName, String path) {
      synchronized(searchPaths) {
         List customPaths = (List)searchPaths.get(libraryName);
         if (customPaths == null) {
            customPaths = Collections.synchronizedList(new LinkedList());
            searchPaths.put(libraryName, customPaths);
         }

         customPaths.add(path);
      }
   }

   public Function getFunction(String functionName) {
      return this.getFunction(functionName, this.callFlags);
   }

   Function getFunction(String name, Method method) {
      int flags = this.callFlags;
      Class[] etypes = method.getExceptionTypes();

      for(int i = 0; i < etypes.length; ++i) {
         if (LastErrorException.class.isAssignableFrom(etypes[i])) {
            flags |= 4;
         }
      }

      return this.getFunction(name, flags);
   }

   public Function getFunction(String functionName, int callFlags) {
      if (functionName == null) {
         throw new NullPointerException("Function name may not be null");
      } else {
         synchronized(this.functions) {
            String key = functionKey(functionName, callFlags);
            Function function = (Function)this.functions.get(key);
            if (function == null) {
               function = new Function(this, functionName, callFlags);
               this.functions.put(key, function);
            }

            return function;
         }
      }
   }

   public Map getOptions() {
      return this.options;
   }

   public Pointer getGlobalVariableAddress(String symbolName) {
      try {
         return new Pointer(this.getSymbolAddress(symbolName));
      } catch (UnsatisfiedLinkError var3) {
         throw new UnsatisfiedLinkError("Error looking up '" + symbolName + "': " + var3.getMessage());
      }
   }

   long getSymbolAddress(String name) {
      if (this.handle == 0L) {
         throw new UnsatisfiedLinkError("Library has been unloaded");
      } else {
         return Native.findSymbol(this.handle, name);
      }
   }

   public String toString() {
      return "Native Library <" + this.libraryPath + "@" + this.handle + ">";
   }

   public String getName() {
      return this.libraryName;
   }

   public File getFile() {
      return this.libraryPath == null ? null : new File(this.libraryPath);
   }

   protected void finalize() {
      this.dispose();
   }

   static void disposeAll() {
      Set values;
      synchronized(libraries) {
         values = new HashSet(libraries.values());
      }

      for(Reference ref : values) {
         NativeLibrary lib = (NativeLibrary)ref.get();
         if (lib != null) {
            lib.dispose();
         }
      }

   }

   public void dispose() {
      synchronized(libraries) {
         Iterator i = libraries.values().iterator();

         while(i.hasNext()) {
            Reference ref = (WeakReference)i.next();
            if (ref.get() == this) {
               i.remove();
            }
         }
      }

      synchronized(this) {
         if (this.handle != 0L) {
            Native.close(this.handle);
            this.handle = 0L;
         }

      }
   }

   private static List initPaths(String key) {
      String value = System.getProperty(key, "");
      if ("".equals(value)) {
         return Collections.EMPTY_LIST;
      } else {
         StringTokenizer st = new StringTokenizer(value, File.pathSeparator);
         List list = new ArrayList();

         while(st.hasMoreTokens()) {
            String path = st.nextToken();
            if (!"".equals(path)) {
               list.add(path);
            }
         }

         return list;
      }
   }

   private static String findLibraryPath(String libName, List searchPath) {
      if (new File(libName).isAbsolute()) {
         return libName;
      } else {
         String name = mapLibraryName(libName);

         for(String path : searchPath) {
            File file = new File(path, name);
            if (file.exists()) {
               return file.getAbsolutePath();
            }

            if (Platform.isMac() && name.endsWith(".dylib")) {
               file = new File(path, name.substring(0, name.lastIndexOf(".dylib")) + ".jnilib");
               if (file.exists()) {
                  return file.getAbsolutePath();
               }
            }
         }

         return name;
      }
   }

   private static String mapLibraryName(String libName) {
      if (Platform.isMac()) {
         if (!libName.startsWith("lib") || !libName.endsWith(".dylib") && !libName.endsWith(".jnilib")) {
            String name = System.mapLibraryName(libName);
            return name.endsWith(".jnilib") ? name.substring(0, name.lastIndexOf(".jnilib")) + ".dylib" : name;
         } else {
            return libName;
         }
      } else {
         if (Platform.isLinux()) {
            if (isVersionedName(libName) || libName.endsWith(".so")) {
               return libName;
            }
         } else if (Platform.isAix()) {
            if (libName.startsWith("lib")) {
               return libName;
            }
         } else if (Platform.isWindows() && (libName.endsWith(".drv") || libName.endsWith(".dll"))) {
            return libName;
         }

         return System.mapLibraryName(libName);
      }
   }

   private static boolean isVersionedName(String name) {
      if (name.startsWith("lib")) {
         int so = name.lastIndexOf(".so.");
         if (so != -1 && so + 4 < name.length()) {
            for(int i = so + 4; i < name.length(); ++i) {
               char ch = name.charAt(i);
               if (!Character.isDigit(ch) && ch != '.') {
                  return false;
               }
            }

            return true;
         }
      }

      return false;
   }

   static String matchLibrary(String libName, List searchPath) {
      File lib = new File(libName);
      if (lib.isAbsolute()) {
         searchPath = Arrays.asList(lib.getParent());
      }

      FilenameFilter filter = new NativeLibrary$2(libName);
      List matches = new LinkedList();
      Iterator it = searchPath.iterator();

      while(it.hasNext()) {
         File[] files = new File((String)it.next()).listFiles(filter);
         if (files != null && files.length > 0) {
            matches.addAll(Arrays.asList(files));
         }
      }

      double bestVersion = -1.0;
      String bestMatch = null;
      Iterator itx = matches.iterator();

      while(itx.hasNext()) {
         String path = ((File)itx.next()).getAbsolutePath();
         String ver = path.substring(path.lastIndexOf(".so.") + 4);
         double version = parseVersion(ver);
         if (version > bestVersion) {
            bestVersion = version;
            bestMatch = path;
         }
      }

      return bestMatch;
   }

   static double parseVersion(String ver) {
      double v = 0.0;
      double divisor = 1.0;

      for(int dot = ver.indexOf("."); ver != null; divisor *= 100.0) {
         String num;
         if (dot != -1) {
            num = ver.substring(0, dot);
            ver = ver.substring(dot + 1);
            dot = ver.indexOf(".");
         } else {
            num = ver;
            ver = null;
         }

         try {
            v += (double)Integer.parseInt(num) / divisor;
         } catch (NumberFormatException var8) {
            return 0.0;
         }
      }

      return v;
   }

   private static String getMultiArchPath() {
      String cpu = System.getProperty("os.arch").toLowerCase().trim();
      String kernel = Platform.iskFreeBSD() ? "-kfreebsd" : (Platform.isGNU() ? "" : "-linux");
      String libc = "-gnu";
      if (Platform.isIntel()) {
         cpu = Platform.is64Bit() ? "x86_64" : "i386";
      } else if (Platform.isPPC()) {
         cpu = Platform.is64Bit() ? "powerpc64" : "powerpc";
      } else if (Platform.isARM()) {
         cpu = "arm";
         libc = "-gnueabi";
      }

      return cpu + kernel + libc;
   }

   static {
      if (Native.POINTER_SIZE == 0) {
         throw new Error("Native library not initialized");
      } else {
         String webstartPath = Native.getWebStartLibraryPath("jnidispatch");
         if (webstartPath != null) {
            librarySearchPath.add(webstartPath);
         }

         if (System.getProperty("jna.platform.library.path") == null && !Platform.isWindows()) {
            String platformPath = "";
            String sep = "";
            String archPath = "";
            if (Platform.isLinux() || Platform.isSolaris() || Platform.isFreeBSD() || Platform.iskFreeBSD()) {
               archPath = (Platform.isSolaris() ? "/" : "") + Pointer.SIZE * 8;
            }

            String[] paths = new String[]{"/usr/lib" + archPath, "/lib" + archPath, "/usr/lib", "/lib"};
            if (Platform.isLinux() || Platform.iskFreeBSD() || Platform.isGNU()) {
               String multiArchPath = getMultiArchPath();
               paths = new String[]{"/usr/lib/" + multiArchPath, "/lib/" + multiArchPath, "/usr/lib" + archPath, "/lib" + archPath, "/usr/lib", "/lib"};
            }

            for(int i = 0; i < paths.length; ++i) {
               File dir = new File(paths[i]);
               if (dir.exists() && dir.isDirectory()) {
                  platformPath = platformPath + sep + paths[i];
                  sep = File.pathSeparator;
               }
            }

            if (!"".equals(platformPath)) {
               System.setProperty("jna.platform.library.path", platformPath);
            }
         }

         librarySearchPath.addAll(initPaths("jna.platform.library.path"));
      }
   }
}
