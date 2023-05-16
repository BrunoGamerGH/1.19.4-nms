package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.advancements.critereon.CriterionConditionValue;
import net.minecraft.commands.CommandListenerWrapper;

public interface ArgumentCriterionValue<T extends CriterionConditionValue<?>> extends ArgumentType<T> {
   static ArgumentCriterionValue.b a() {
      return new ArgumentCriterionValue.b();
   }

   static ArgumentCriterionValue.a b() {
      return new ArgumentCriterionValue.a();
   }

   public static class a implements ArgumentCriterionValue<CriterionConditionValue.DoubleRange> {
      private static final Collection<String> a = Arrays.asList("0..5.2", "0", "-5.4", "-100.76..", "..100");

      public static CriterionConditionValue.DoubleRange a(CommandContext<CommandListenerWrapper> var0, String var1) {
         return (CriterionConditionValue.DoubleRange)var0.getArgument(var1, CriterionConditionValue.DoubleRange.class);
      }

      public CriterionConditionValue.DoubleRange a(StringReader var0) throws CommandSyntaxException {
         return CriterionConditionValue.DoubleRange.a(var0);
      }

      public Collection<String> getExamples() {
         return a;
      }
   }

   public static class b implements ArgumentCriterionValue<CriterionConditionValue.IntegerRange> {
      private static final Collection<String> a = Arrays.asList("0..5", "0", "-5", "-100..", "..100");

      public static CriterionConditionValue.IntegerRange a(CommandContext<CommandListenerWrapper> var0, String var1) {
         return (CriterionConditionValue.IntegerRange)var0.getArgument(var1, CriterionConditionValue.IntegerRange.class);
      }

      public CriterionConditionValue.IntegerRange a(StringReader var0) throws CommandSyntaxException {
         return CriterionConditionValue.IntegerRange.a(var0);
      }

      public Collection<String> getExamples() {
         return a;
      }
   }
}
