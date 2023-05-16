package org.bukkit.craftbukkit.bootstrap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Main {
   public static void main(String[] argv) {
      new Main().run(argv);
   }

   private void run(String[] argv) {
      try {
         String defaultMainClassName = this.readResource("main-class", BufferedReader::readLine);
         String mainClassName = System.getProperty("bundlerMainClass", defaultMainClassName);
         String repoDir = System.getProperty("bundlerRepoDir", "bundler");
         Path outputDir = Paths.get(repoDir).toAbsolutePath();
         if (!Files.isDirectory(outputDir)) {
            Files.createDirectories(outputDir);
         }

         System.out.println("Unbundling libraries to " + outputDir);
         boolean readOnly = Boolean.getBoolean("bundlerReadOnly");
         List<URL> extractedUrls = new ArrayList<>();
         this.readAndExtractDir("versions", outputDir, extractedUrls, readOnly);
         this.readAndExtractDir("libraries", outputDir, extractedUrls, readOnly);
         if (mainClassName == null || mainClassName.isEmpty()) {
            System.out.println("Empty main class specified, exiting");
            System.exit(0);
         }

         URLClassLoader classLoader = new URLClassLoader(extractedUrls.toArray(new URL[0]));
         System.out.println("Starting server");
         Thread runThread = new Thread(() -> {
            try {
               Class<?> mainClass = Class.forName(mainClassName, true, classLoader);
               MethodHandle mainHandle = MethodHandles.lookup().findStatic(mainClass, "main", MethodType.methodType(Void.TYPE, String[].class)).asFixedArity();
               mainHandle.invoke(argv);
            } catch (Throwable var5x) {
               Main.Thrower.INSTANCE.sneakyThrow(var5x);
            }
         }, "ServerMain");
         runThread.setContextClassLoader(classLoader);
         runThread.start();
      } catch (Exception var10) {
         var10.printStackTrace(System.out);
         System.out.println("Failed to extract server libraries, exiting");
      }
   }

   // $QF: Could not inline inconsistent finally blocks
   // Please report this to the Quiltflower issue tracker, at https://github.com/QuiltMC/quiltflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private <T> T readResource(String resource, Main.ResourceParser<T> parser) throws Exception {
      String fullPath = "/META-INF/" + resource;
      Throwable var4 = null;
      Object var5 = null;

      try {
         InputStream is = this.getClass().getResourceAsStream(fullPath);

         Object var10000;
         try {
            if (is == null) {
               throw new IllegalStateException("Resource " + fullPath + " not found");
            }

            var10000 = parser.parse(new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)));
         } finally {
            var10000 = is;
            if (is != null) {
               var10000 = is;
               is.close();
            }
         }

         return (T)var10000;
      } catch (Throwable var12) {
         if (var4 == null) {
            var4 = var12;
         } else if (var4 != var12) {
            var4.addSuppressed(var12);
         }

         throw var4;
      }
   }

   private void readAndExtractDir(String subdir, Path outputDir, List<URL> extractedUrls, boolean readOnly) throws Exception {
      List<Main.FileEntry> entries = this.readResource(subdir + ".list", reader -> reader.lines().map(Main.FileEntry::parseLine).toList());
      Path subdirPath = outputDir.resolve(subdir);

      for(Main.FileEntry entry : entries) {
         if (!entry.path.startsWith("minecraft-server")) {
            Path outputFile = subdirPath.resolve(entry.path);
            if (!readOnly) {
               this.checkAndExtractJar(subdir, entry, outputFile);
            }

            extractedUrls.add(outputFile.toUri().toURL());
         }
      }
   }

   private void checkAndExtractJar(String subdir, Main.FileEntry entry, Path outputFile) throws Exception {
      if (!Files.exists(outputFile) || !checkIntegrity(outputFile, entry.hash())) {
         System.out.printf("Unpacking %s (%s:%s) to %s%n", entry.path, subdir, entry.id, outputFile);
         this.extractJar(subdir, entry.path, outputFile);
      }
   }

   // $QF: Could not inline inconsistent finally blocks
   // Please report this to the Quiltflower issue tracker, at https://github.com/QuiltMC/quiltflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void extractJar(String subdir, String jarPath, Path outputFile) throws IOException {
      Files.createDirectories(outputFile.getParent());
      Throwable var4 = null;
      Object var5 = null;

      try {
         InputStream input = this.getClass().getResourceAsStream("/META-INF/" + subdir + "/" + jarPath);

         try {
            if (input == null) {
               throw new IllegalStateException("Declared library " + jarPath + " not found");
            }

            Files.copy(input, outputFile, StandardCopyOption.REPLACE_EXISTING);
         } finally {
            if (input != null) {
               input.close();
            }
         }
      } catch (Throwable var12) {
         if (var4 == null) {
            var4 = var12;
         } else if (var4 != var12) {
            var4.addSuppressed(var12);
         }

         throw var4;
      }
   }

   // $QF: Could not inline inconsistent finally blocks
   // Please report this to the Quiltflower issue tracker, at https://github.com/QuiltMC/quiltflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static boolean checkIntegrity(Path file, String expectedHash) throws Exception {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      Throwable var3 = null;
      Object var4 = null;

      try {
         InputStream output = Files.newInputStream(file);

         try {
            output.transferTo(new DigestOutputStream(OutputStream.nullOutputStream(), digest));
            String actualHash = byteToHex(digest.digest());
            if (!actualHash.equalsIgnoreCase(expectedHash)) {
               System.out.printf("Expected file %s to have hash %s, but got %s%n", file, expectedHash, actualHash);
               return false;
            }
         } finally {
            if (output != null) {
               output.close();
            }
         }

         return true;
      } catch (Throwable var12) {
         if (var3 == null) {
            var3 = var12;
         } else if (var3 != var12) {
            var3.addSuppressed(var12);
         }

         throw var3;
      }
   }

   private static String byteToHex(byte[] bytes) {
      StringBuilder result = new StringBuilder(bytes.length * 2);

      for(byte b : bytes) {
         result.append(Character.forDigit(b >> 4 & 15, 16));
         result.append(Character.forDigit(b >> 0 & 15, 16));
      }

      return result.toString();
   }

   private static record FileEntry(String hash, String id, String path) {
      public static Main.FileEntry parseLine(String line) {
         String[] fields = line.split(" ");
         if (fields.length != 2) {
            throw new IllegalStateException("Malformed library entry: " + line);
         } else {
            String path = fields[1].substring(1);
            return new Main.FileEntry(fields[0], path, path);
         }
      }
   }

   @FunctionalInterface
   private interface ResourceParser<T> {
      T parse(BufferedReader var1) throws Exception;
   }

   private static class Thrower<T extends Throwable> {
      private static final Main.Thrower<RuntimeException> INSTANCE = new Main.Thrower<>();

      public void sneakyThrow(Throwable exception) throws T {
         throw exception;
      }
   }
}
