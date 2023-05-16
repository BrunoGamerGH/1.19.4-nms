package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.scores.Scoreboard;

public class ArgumentScoreboardSlot implements ArgumentType<Integer> {
   private static final Collection<String> b = Arrays.asList("sidebar", "foo.bar");
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(
      var0 -> IChatBaseComponent.a("argument.scoreboardDisplaySlot.invalid", var0)
   );

   private ArgumentScoreboardSlot() {
   }

   public static ArgumentScoreboardSlot a() {
      return new ArgumentScoreboardSlot();
   }

   public static int a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return var0.getArgument(var1, Integer.class);
   }

   public Integer a(StringReader var0) throws CommandSyntaxException {
      String var1 = var0.readUnquotedString();
      int var2 = Scoreboard.j(var1);
      if (var2 == -1) {
         throw a.create(var1);
      } else {
         return var2;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return ICompletionProvider.a(Scoreboard.h(), var1);
   }

   public Collection<String> getExamples() {
      return b;
   }
}
