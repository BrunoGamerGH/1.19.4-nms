package net.minecraft;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.state.IBlockData;

public class CrashReportSystemDetails {
   private final String a;
   private final List<CrashReportSystemDetails.CrashReportDetail> b = Lists.newArrayList();
   private StackTraceElement[] c = new StackTraceElement[0];

   public CrashReportSystemDetails(String var0) {
      this.a = var0;
   }

   public static String a(LevelHeightAccessor var0, double var1, double var3, double var5) {
      return String.format(Locale.ROOT, "%.2f,%.2f,%.2f - %s", var1, var3, var5, a(var0, BlockPosition.a(var1, var3, var5)));
   }

   public static String a(LevelHeightAccessor var0, BlockPosition var1) {
      return a(var0, var1.u(), var1.v(), var1.w());
   }

   public static String a(LevelHeightAccessor var0, int var1, int var2, int var3) {
      StringBuilder var4 = new StringBuilder();

      try {
         var4.append(String.format(Locale.ROOT, "World: (%d,%d,%d)", var1, var2, var3));
      } catch (Throwable var19) {
         var4.append("(Error finding world loc)");
      }

      var4.append(", ");

      try {
         int var5 = SectionPosition.a(var1);
         int var6 = SectionPosition.a(var2);
         int var7 = SectionPosition.a(var3);
         int var8 = var1 & 15;
         int var9 = var2 & 15;
         int var10 = var3 & 15;
         int var11 = SectionPosition.c(var5);
         int var12 = var0.v_();
         int var13 = SectionPosition.c(var7);
         int var14 = SectionPosition.c(var5 + 1) - 1;
         int var15 = var0.ai() - 1;
         int var16 = SectionPosition.c(var7 + 1) - 1;
         var4.append(
            String.format(
               Locale.ROOT,
               "Section: (at %d,%d,%d in %d,%d,%d; chunk contains blocks %d,%d,%d to %d,%d,%d)",
               var8,
               var9,
               var10,
               var5,
               var6,
               var7,
               var11,
               var12,
               var13,
               var14,
               var15,
               var16
            )
         );
      } catch (Throwable var18) {
         var4.append("(Error finding chunk loc)");
      }

      var4.append(", ");

      try {
         int var5 = var1 >> 9;
         int var6 = var3 >> 9;
         int var7 = var5 << 5;
         int var8 = var6 << 5;
         int var9 = (var5 + 1 << 5) - 1;
         int var10 = (var6 + 1 << 5) - 1;
         int var11 = var5 << 9;
         int var12 = var0.v_();
         int var13 = var6 << 9;
         int var14 = (var5 + 1 << 9) - 1;
         int var15 = var0.ai() - 1;
         int var16 = (var6 + 1 << 9) - 1;
         var4.append(
            String.format(
               Locale.ROOT,
               "Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,%d,%d to %d,%d,%d)",
               var5,
               var6,
               var7,
               var8,
               var9,
               var10,
               var11,
               var12,
               var13,
               var14,
               var15,
               var16
            )
         );
      } catch (Throwable var17) {
         var4.append("(Error finding world loc)");
      }

      return var4.toString();
   }

   public CrashReportSystemDetails a(String var0, CrashReportCallable<String> var1) {
      try {
         this.a(var0, var1.call());
      } catch (Throwable var4) {
         this.a(var0, var4);
      }

      return this;
   }

   public CrashReportSystemDetails a(String var0, Object var1) {
      this.b.add(new CrashReportSystemDetails.CrashReportDetail(var0, var1));
      return this;
   }

   public void a(String var0, Throwable var1) {
      this.a(var0, (Object)var1);
   }

   public int a(int var0) {
      StackTraceElement[] var1 = Thread.currentThread().getStackTrace();
      if (var1.length <= 0) {
         return 0;
      } else {
         this.c = new StackTraceElement[var1.length - 3 - var0];
         System.arraycopy(var1, 3 + var0, this.c, 0, this.c.length);
         return this.c.length;
      }
   }

   public boolean a(StackTraceElement var0, StackTraceElement var1) {
      if (this.c.length != 0 && var0 != null) {
         StackTraceElement var2 = this.c[0];
         if (var2.isNativeMethod() == var0.isNativeMethod()
            && var2.getClassName().equals(var0.getClassName())
            && var2.getFileName().equals(var0.getFileName())
            && var2.getMethodName().equals(var0.getMethodName())) {
            if (var1 != null != this.c.length > 1) {
               return false;
            } else if (var1 != null && !this.c[1].equals(var1)) {
               return false;
            } else {
               this.c[0] = var0;
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void b(int var0) {
      StackTraceElement[] var1 = new StackTraceElement[this.c.length - var0];
      System.arraycopy(this.c, 0, var1, 0, var1.length);
      this.c = var1;
   }

   public void a(StringBuilder var0) {
      var0.append("-- ").append(this.a).append(" --\n");
      var0.append("Details:");

      for(CrashReportSystemDetails.CrashReportDetail var2 : this.b) {
         var0.append("\n\t");
         var0.append(var2.a());
         var0.append(": ");
         var0.append(var2.b());
      }

      if (this.c != null && this.c.length > 0) {
         var0.append("\nStacktrace:");

         for(StackTraceElement var4 : this.c) {
            var0.append("\n\tat ");
            var0.append(var4);
         }
      }
   }

   public StackTraceElement[] a() {
      return this.c;
   }

   public static void a(CrashReportSystemDetails var0, LevelHeightAccessor var1, BlockPosition var2, @Nullable IBlockData var3) {
      if (var3 != null) {
         var0.a("Block", var3::toString);
      }

      var0.a("Block location", () -> a(var1, var2));
   }

   static class CrashReportDetail {
      private final String a;
      private final String b;

      public CrashReportDetail(String var0, @Nullable Object var1) {
         this.a = var0;
         if (var1 == null) {
            this.b = "~~NULL~~";
         } else if (var1 instanceof Throwable var2) {
            this.b = "~~ERROR~~ " + var2.getClass().getSimpleName() + ": " + var2.getMessage();
         } else {
            this.b = var1.toString();
         }
      }

      public String a() {
         return this.a;
      }

      public String b() {
         return this.b;
      }
   }
}
