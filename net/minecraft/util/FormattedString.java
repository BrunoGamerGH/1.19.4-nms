package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.List;
import net.minecraft.network.chat.ChatModifier;

@FunctionalInterface
public interface FormattedString {
   FormattedString a = var0 -> true;

   boolean accept(FormattedStringEmpty var1);

   static FormattedString codepoint(int var0, ChatModifier var1) {
      return var2 -> var2.accept(0, var1, var0);
   }

   static FormattedString forward(String var0, ChatModifier var1) {
      return var0.isEmpty() ? a : var2 -> StringDecomposer.a(var0, var1, var2);
   }

   static FormattedString forward(String var0, ChatModifier var1, Int2IntFunction var2) {
      return var0.isEmpty() ? a : var3 -> StringDecomposer.a(var0, var1, decorateOutput(var3, var2));
   }

   static FormattedString backward(String var0, ChatModifier var1) {
      return var0.isEmpty() ? a : var2 -> StringDecomposer.b(var0, var1, var2);
   }

   static FormattedString backward(String var0, ChatModifier var1, Int2IntFunction var2) {
      return var0.isEmpty() ? a : var3 -> StringDecomposer.b(var0, var1, decorateOutput(var3, var2));
   }

   static FormattedStringEmpty decorateOutput(FormattedStringEmpty var0, Int2IntFunction var1) {
      return (var2, var3, var4) -> var0.accept(var2, var3, var1.apply(var4));
   }

   static FormattedString composite() {
      return a;
   }

   static FormattedString composite(FormattedString var0) {
      return var0;
   }

   static FormattedString composite(FormattedString var0, FormattedString var1) {
      return fromPair(var0, var1);
   }

   static FormattedString composite(FormattedString... var0) {
      return fromList(ImmutableList.copyOf(var0));
   }

   static FormattedString composite(List<FormattedString> var0) {
      int var1 = var0.size();
      switch(var1) {
         case 0:
            return a;
         case 1:
            return var0.get(0);
         case 2:
            return fromPair(var0.get(0), var0.get(1));
         default:
            return fromList(ImmutableList.copyOf(var0));
      }
   }

   static FormattedString fromPair(FormattedString var0, FormattedString var1) {
      return var2 -> var0.accept(var2) && var1.accept(var2);
   }

   static FormattedString fromList(List<FormattedString> var0) {
      return var1 -> {
         for(FormattedString var3 : var0) {
            if (!var3.accept(var1)) {
               return false;
            }
         }

         return true;
      };
   }
}
