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
import net.minecraft.world.scores.ScoreboardTeam;

public class ArgumentScoreboardTeam implements ArgumentType<String> {
   private static final Collection<String> a = Arrays.asList("foo", "123");
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("team.notFound", var0));

   public static ArgumentScoreboardTeam a() {
      return new ArgumentScoreboardTeam();
   }

   public static ScoreboardTeam a(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      String var2 = (String)var0.getArgument(var1, String.class);
      Scoreboard var3 = ((CommandListenerWrapper)var0.getSource()).l().aF();
      ScoreboardTeam var4 = var3.f(var2);
      if (var4 == null) {
         throw b.create(var2);
      } else {
         return var4;
      }
   }

   public String a(StringReader var0) throws CommandSyntaxException {
      return var0.readUnquotedString();
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return var0.getSource() instanceof ICompletionProvider ? ICompletionProvider.b(((ICompletionProvider)var0.getSource()).q(), var1) : Suggestions.empty();
   }

   public Collection<String> getExamples() {
      return a;
   }
}
