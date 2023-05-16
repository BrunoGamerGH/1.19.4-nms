package net.minecraft.server.advancements;

import it.unimi.dsi.fastutil.Stack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.function.Predicate;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementDisplay;

public class AdvancementVisibilityEvaluator {
   private static final int a = 2;

   private static AdvancementVisibilityEvaluator.b a(Advancement var0, boolean var1) {
      AdvancementDisplay var2 = var0.d();
      if (var2 == null) {
         return AdvancementVisibilityEvaluator.b.b;
      } else if (var1) {
         return AdvancementVisibilityEvaluator.b.a;
      } else {
         return var2.j() ? AdvancementVisibilityEvaluator.b.b : AdvancementVisibilityEvaluator.b.c;
      }
   }

   private static boolean a(Stack<AdvancementVisibilityEvaluator.b> var0) {
      for(int var1 = 0; var1 <= 2; ++var1) {
         AdvancementVisibilityEvaluator.b var2 = (AdvancementVisibilityEvaluator.b)var0.peek(var1);
         if (var2 == AdvancementVisibilityEvaluator.b.a) {
            return true;
         }

         if (var2 == AdvancementVisibilityEvaluator.b.b) {
            return false;
         }
      }

      return false;
   }

   private static boolean a(Advancement var0, Stack<AdvancementVisibilityEvaluator.b> var1, Predicate<Advancement> var2, AdvancementVisibilityEvaluator.a var3) {
      boolean var4 = var2.test(var0);
      AdvancementVisibilityEvaluator.b var5 = a(var0, var4);
      boolean var6 = var4;
      var1.push(var5);

      for(Advancement var8 : var0.f()) {
         var6 |= a(var8, var1, var2, var3);
      }

      boolean var7 = var6 || a(var1);
      var1.pop();
      var3.accept(var0, var7);
      return var6;
   }

   public static void a(Advancement var0, Predicate<Advancement> var1, AdvancementVisibilityEvaluator.a var2) {
      Advancement var3 = var0.c();
      Stack<AdvancementVisibilityEvaluator.b> var4 = new ObjectArrayList();

      for(int var5 = 0; var5 <= 2; ++var5) {
         var4.push(AdvancementVisibilityEvaluator.b.c);
      }

      a(var3, var4, var1, var2);
   }

   @FunctionalInterface
   public interface a {
      void accept(Advancement var1, boolean var2);
   }

   static enum b {
      a,
      b,
      c;
   }
}
