package net.minecraft;

import com.mojang.serialization.DataResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;

public class FileUtils {
   private static final Pattern a = Pattern.compile("(<name>.*) \\((<count>\\d*)\\)", 66);
   private static final int b = 255;
   private static final Pattern c = Pattern.compile(".*\\.|(?:COM|CLOCK\\$|CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])(?:\\..*)?", 2);
   private static final Pattern d = Pattern.compile("[-._a-z0-9]+");

   public static String a(Path var0, String var1, String var2) throws IOException {
      for(char var6 : SharedConstants.aV) {
         var1 = var1.replace(var6, '_');
      }

      var1 = var1.replaceAll("[./\"]", "_");
      if (c.matcher(var1).matches()) {
         var1 = "_" + var1 + "_";
      }

      Matcher var3 = a.matcher(var1);
      int var4 = 0;
      if (var3.matches()) {
         var1 = var3.group("name");
         var4 = Integer.parseInt(var3.group("count"));
      }

      if (var1.length() > 255 - var2.length()) {
         var1 = var1.substring(0, 255 - var2.length());
      }

      while(true) {
         String var5 = var1;
         if (var4 != 0) {
            String var6 = " (" + var4 + ")";
            int var7 = 255 - var6.length();
            if (var1.length() > var7) {
               var5 = var1.substring(0, var7);
            }

            var5 = var5 + var6;
         }

         var5 = var5 + var2;
         Path var6 = var0.resolve(var5);

         try {
            Path var7 = Files.createDirectory(var6);
            Files.deleteIfExists(var7);
            return var0.relativize(var7).toString();
         } catch (FileAlreadyExistsException var8) {
            ++var4;
         }
      }
   }

   public static boolean a(Path var0) {
      Path var1 = var0.normalize();
      return var1.equals(var0);
   }

   public static boolean b(Path var0) {
      for(Path var2 : var0) {
         if (c.matcher(var2.toString()).matches()) {
            return false;
         }
      }

      return true;
   }

   public static Path b(Path var0, String var1, String var2) {
      String var3 = var1 + var2;
      Path var4 = Paths.get(var3);
      if (var4.endsWith(var2)) {
         throw new InvalidPathException(var3, "empty resource name");
      } else {
         return var0.resolve(var4);
      }
   }

   public static String a(String var0) {
      return FilenameUtils.getFullPath(var0).replace(File.separator, "/");
   }

   public static String b(String var0) {
      return FilenameUtils.normalize(var0).replace(File.separator, "/");
   }

   public static DataResult<List<String>> c(String var0) {
      int var1 = var0.indexOf(47);
      if (var1 == -1) {
         return switch(var0) {
            case "", ".", ".." -> DataResult.error(() -> "Invalid path '" + var0 + "'");
            default -> !d(var0) ? DataResult.error(() -> "Invalid path '" + var0 + "'") : DataResult.success(List.of(var0));
         };
      } else {
         List<String> var2 = new ArrayList<>();
         int var3 = 0;
         boolean var4 = false;

         while(true) {
            String var5 = var0.substring(var3, var1);
            switch(var5) {
               case "":
               case ".":
               case "..":
                  return DataResult.error(() -> "Invalid segment '" + var5 + "' in path '" + var0 + "'");
            }
         }
      }
   }

   public static Path a(Path var0, List<String> var1) {
      int var2 = var1.size();

      return switch(var2) {
         case 0 -> var0;
         case 1 -> var0.resolve(var1.get(0));
         default -> {
            String[] var3 = new String[var2 - 1];

            for(int var4 = 1; var4 < var2; ++var4) {
               var3[var4 - 1] = var1.get(var4);
            }

            yield var0.resolve(var0.getFileSystem().getPath(var1.get(0), var3));
         }
      };
   }

   public static boolean d(String var0) {
      return d.matcher(var0).matches();
   }

   public static void a(String... var0) {
      if (var0.length == 0) {
         throw new IllegalArgumentException("Path must have at least one element");
      } else {
         for(String var4 : var0) {
            if (var4.equals("..") || var4.equals(".") || !d(var4)) {
               throw new IllegalArgumentException("Illegal segment " + var4 + " in path " + Arrays.toString((Object[])var0));
            }
         }
      }
   }

   public static void c(Path var0) throws IOException {
      Files.createDirectories(Files.exists(var0) ? var0.toRealPath() : var0);
   }
}
