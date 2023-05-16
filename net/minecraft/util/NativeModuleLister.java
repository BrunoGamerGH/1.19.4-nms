package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.logging.LogUtils;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.Version;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.Tlhelp32.MODULEENTRY32W;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import net.minecraft.CrashReportSystemDetails;
import org.slf4j.Logger;

public class NativeModuleLister {
   private static final Logger a = LogUtils.getLogger();
   private static final int b = 65535;
   private static final int c = 1033;
   private static final int d = -65536;
   private static final int e = 78643200;

   public static List<NativeModuleLister.a> a() {
      if (!Platform.isWindows()) {
         return ImmutableList.of();
      } else {
         int var0 = Kernel32.INSTANCE.GetCurrentProcessId();
         Builder<NativeModuleLister.a> var1 = ImmutableList.builder();

         for(MODULEENTRY32W var4 : Kernel32Util.getModules(var0)) {
            String var5 = var4.szModule();
            Optional<NativeModuleLister.b> var6 = a(var4.szExePath());
            var1.add(new NativeModuleLister.a(var5, var6));
         }

         return var1.build();
      }
   }

   private static Optional<NativeModuleLister.b> a(String var0) {
      try {
         IntByReference var1 = new IntByReference();
         int var2 = Version.INSTANCE.GetFileVersionInfoSize(var0, var1);
         if (var2 == 0) {
            int var3 = Native.getLastError();
            if (var3 != 1813 && var3 != 1812) {
               throw new Win32Exception(var3);
            } else {
               return Optional.empty();
            }
         } else {
            Pointer var3 = new Memory((long)var2);
            if (!Version.INSTANCE.GetFileVersionInfo(var0, 0, var2, var3)) {
               throw new Win32Exception(Native.getLastError());
            } else {
               IntByReference var4 = new IntByReference();
               Pointer var5 = a(var3, "\\VarFileInfo\\Translation", var4);
               int[] var6 = var5.getIntArray(0L, var4.getValue() / 4);
               OptionalInt var7 = a(var6);
               if (!var7.isPresent()) {
                  return Optional.empty();
               } else {
                  int var8 = var7.getAsInt();
                  int var9 = var8 & 65535;
                  int var10 = (var8 & -65536) >> 16;
                  String var11 = b(var3, a("FileDescription", var9, var10), var4);
                  String var12 = b(var3, a("CompanyName", var9, var10), var4);
                  String var13 = b(var3, a("FileVersion", var9, var10), var4);
                  return Optional.of(new NativeModuleLister.b(var11, var13, var12));
               }
            }
         }
      } catch (Exception var14) {
         a.info("Failed to find module info for {}", var0, var14);
         return Optional.empty();
      }
   }

   private static String a(String var0, int var1, int var2) {
      return String.format(Locale.ROOT, "\\StringFileInfo\\%04x%04x\\%s", var1, var2, var0);
   }

   private static OptionalInt a(int[] var0) {
      OptionalInt var1 = OptionalInt.empty();

      for(int var5 : var0) {
         if ((var5 & -65536) == 78643200 && (var5 & 65535) == 1033) {
            return OptionalInt.of(var5);
         }

         var1 = OptionalInt.of(var5);
      }

      return var1;
   }

   private static Pointer a(Pointer var0, String var1, IntByReference var2) {
      PointerByReference var3 = new PointerByReference();
      if (!Version.INSTANCE.VerQueryValue(var0, var1, var3, var2)) {
         throw new UnsupportedOperationException("Can't get version value " + var1);
      } else {
         return var3.getValue();
      }
   }

   private static String b(Pointer var0, String var1, IntByReference var2) {
      try {
         Pointer var3 = a(var0, var1, var2);
         byte[] var4 = var3.getByteArray(0L, (var2.getValue() - 1) * 2);
         return new String(var4, StandardCharsets.UTF_16LE);
      } catch (Exception var5) {
         return "";
      }
   }

   public static void a(CrashReportSystemDetails var0) {
      var0.a("Modules", () -> a().stream().sorted(Comparator.comparing(var0x -> var0x.a)).map(var0x -> "\n\t\t" + var0x).collect(Collectors.joining()));
   }

   public static class a {
      public final String a;
      public final Optional<NativeModuleLister.b> b;

      public a(String var0, Optional<NativeModuleLister.b> var1) {
         this.a = var0;
         this.b = var1;
      }

      @Override
      public String toString() {
         return this.b.<String>map(var0 -> this.a + ":" + var0).orElse(this.a);
      }
   }

   public static class b {
      public final String a;
      public final String b;
      public final String c;

      public b(String var0, String var1, String var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      @Override
      public String toString() {
         return this.a + ":" + this.b + ":" + this.c;
      }
   }
}
