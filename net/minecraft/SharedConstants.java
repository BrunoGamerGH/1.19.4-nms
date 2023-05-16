package net.minecraft;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DSL.TypeReference;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import java.time.Duration;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandExceptionProvider;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkCoordIntPair;

public class SharedConstants {
   @Deprecated
   public static final boolean a = false;
   @Deprecated
   public static final int b = 3337;
   @Deprecated
   public static final String c = "main";
   @Deprecated
   public static final String d = "1.19.4";
   @Deprecated
   public static final int e = 762;
   @Deprecated
   public static final int f = 126;
   public static final int g = 3318;
   private static final int bc = 30;
   public static final boolean h = false;
   @Deprecated
   public static final int i = 13;
   @Deprecated
   public static final int j = 12;
   @Deprecated
   public static final int k = 1;
   public static final int l = 1;
   public static final String m = "DataVersion";
   public static final boolean n = false;
   public static final boolean o = false;
   public static final boolean p = false;
   public static final boolean q = false;
   public static final boolean r = false;
   public static final boolean s = false;
   public static final boolean t = false;
   public static final boolean u = false;
   public static final boolean v = false;
   public static final boolean w = false;
   public static final boolean x = false;
   public static final boolean y = false;
   public static final boolean z = false;
   public static final boolean A = false;
   public static final boolean B = false;
   public static final boolean C = false;
   public static final boolean D = false;
   public static final boolean E = false;
   public static final boolean F = false;
   public static final boolean G = false;
   public static final boolean H = false;
   public static final boolean I = false;
   public static final boolean J = false;
   public static final boolean K = false;
   public static final boolean L = false;
   public static final boolean M = false;
   public static final boolean N = false;
   public static final boolean O = false;
   public static final boolean P = false;
   public static final boolean Q = false;
   public static final boolean R = false;
   public static final boolean S = false;
   public static final boolean T = false;
   public static final boolean U = false;
   public static final boolean V = false;
   public static final boolean W = false;
   public static final boolean X = false;
   public static final boolean Y = false;
   public static final boolean Z = false;
   public static final boolean aa = false;
   public static final boolean ab = false;
   public static final boolean ac = false;
   public static final boolean ad = false;
   public static final boolean ae = false;
   public static final boolean af = false;
   public static final boolean ag = false;
   public static final boolean ah = false;
   public static final boolean ai = false;
   public static final boolean aj = false;
   public static final boolean ak = false;
   public static final boolean al = false;
   public static final boolean am = false;
   public static boolean an = false;
   public static boolean ao = false;
   public static final boolean ap = false;
   public static final boolean aq = false;
   public static final boolean ar = false;
   public static final boolean as = false;
   public static final boolean at = false;
   public static final boolean au = false;
   public static final boolean av = false;
   public static final boolean aw = false;
   public static final boolean ax = false;
   public static final boolean ay = false;
   public static final int az = 25565;
   public static final boolean aA = false;
   public static final boolean aB = false;
   public static final int aC = 0;
   public static final int aD = 0;
   public static final Level aE = Level.DISABLED;
   public static final boolean aF = false;
   public static final boolean aG = false;
   public static final boolean aH = false;
   public static final boolean aI = false;
   public static final boolean aJ = false;
   public static final boolean aK = false;
   public static final boolean aL = false;
   public static final long aM = Duration.ofMillis(300L).toNanos();
   public static boolean aN = true;
   public static boolean aO;
   public static Set<TypeReference> aP = Set.of();
   public static final int aQ = 16;
   public static final int aR = 256;
   public static final int aS = 32500;
   public static final int aT = 1000000;
   public static final int aU = 32;
   public static final char[] aV = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':'};
   public static final int aW = 20;
   public static final int aX = 1200;
   public static final int aY = 24000;
   public static final float aZ = 1365.3334F;
   public static final float ba = 0.87890625F;
   public static final float bb = 17.578125F;
   @Nullable
   private static WorldVersion bd;

   public static boolean a(char var0) {
      return var0 != 167 && var0 >= ' ' && var0 != 127;
   }

   public static String a(String var0) {
      return a(var0, false);
   }

   public static String a(String var0, boolean var1) {
      StringBuilder var2 = new StringBuilder();

      for(char var6 : var0.toCharArray()) {
         if (a(var6)) {
            var2.append(var6);
         } else if (var1 && var6 == '\n') {
            var2.append(var6);
         }
      }

      return var2.toString();
   }

   public static void a(WorldVersion var0) {
      if (bd == null) {
         bd = var0;
      } else if (var0 != bd) {
         throw new IllegalStateException("Cannot override the current game version!");
      }
   }

   public static void a() {
      if (bd == null) {
         bd = MinecraftVersion.a();
      }
   }

   public static WorldVersion b() {
      if (bd == null) {
         throw new IllegalStateException("Game version not set");
      } else {
         return bd;
      }
   }

   public static int c() {
      return 762;
   }

   public static boolean a(ChunkCoordIntPair var0) {
      int var1 = var0.d();
      int var2 = var0.e();
      if (!an) {
         return false;
      } else {
         return var1 > 8192 || var1 < 0 || var2 > 1024 || var2 < 0;
      }
   }

   public static void d() {
      aP = DataFixTypes.m;
   }

   static {
      ResourceLeakDetector.setLevel(aE);
      CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES = false;
      CommandSyntaxException.BUILT_IN_EXCEPTIONS = new CommandExceptionProvider();
   }
}
