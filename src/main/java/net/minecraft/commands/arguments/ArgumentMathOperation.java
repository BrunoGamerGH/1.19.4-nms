package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.scores.ScoreboardScore;

public class ArgumentMathOperation implements ArgumentType<ArgumentMathOperation.a> {
   private static final Collection<String> a = Arrays.asList("=", ">", "<");
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("arguments.operation.invalid"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("arguments.operation.div0"));

   public static ArgumentMathOperation a() {
      return new ArgumentMathOperation();
   }

   public static ArgumentMathOperation.a a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (ArgumentMathOperation.a)var0.getArgument(var1, ArgumentMathOperation.a.class);
   }

   public ArgumentMathOperation.a a(StringReader var0) throws CommandSyntaxException {
      if (!var0.canRead()) {
         throw b.create();
      } else {
         int var1 = var0.getCursor();

         while(var0.canRead() && var0.peek() != ' ') {
            var0.skip();
         }

         return a(var0.getString().substring(var1, var0.getCursor()));
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return ICompletionProvider.a(new String[]{"=", "+=", "-=", "*=", "/=", "%=", "<", ">", "><"}, var1);
   }

   public Collection<String> getExamples() {
      return a;
   }

   private static ArgumentMathOperation.a a(String var0) throws CommandSyntaxException {
      return (ArgumentMathOperation.a)(var0.equals("><") ? (var0x, var1) -> {
         int var2 = var0x.b();
         var0x.b(var1.b());
         var1.b(var2);
      } : b(var0));
   }

   private static ArgumentMathOperation.b b(String var0) throws CommandSyntaxException {
      switch(var0) {
         case "=":
            return (var0x, var1) -> var1;
         case "+=":
            return (var0x, var1) -> var0x + var1;
         case "-=":
            return (var0x, var1) -> var0x - var1;
         case "*=":
            return (var0x, var1) -> var0x * var1;
         case "/=":
            return (var0x, var1) -> {
               if (var1 == 0) {
                  throw c.create();
               } else {
                  return MathHelper.a(var0x, var1);
               }
            };
         case "%=":
            return (var0x, var1) -> {
               if (var1 == 0) {
                  throw c.create();
               } else {
                  return MathHelper.b(var0x, var1);
               }
            };
         case "<":
            return Math::min;
         case ">":
            return Math::max;
         default:
            throw b.create();
      }
   }

   @FunctionalInterface
   public interface a {
      void apply(ScoreboardScore var1, ScoreboardScore var2) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface b extends ArgumentMathOperation.a {
      int apply(int var1, int var2) throws CommandSyntaxException;

      @Override
      default void apply(ScoreboardScore var0, ScoreboardScore var1) throws CommandSyntaxException {
         var0.b(this.apply(var0.b(), var1.b()));
      }
   }
}
