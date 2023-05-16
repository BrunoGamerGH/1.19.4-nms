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
import net.minecraft.world.scores.ScoreboardObjective;

public class ArgumentScoreboardObjective implements ArgumentType<String> {
   private static final Collection<String> a = Arrays.asList("foo", "*", "012");
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("arguments.objective.notFound", var0));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("arguments.objective.readonly", var0));

   public static ArgumentScoreboardObjective a() {
      return new ArgumentScoreboardObjective();
   }

   public static ScoreboardObjective a(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      String var2 = (String)var0.getArgument(var1, String.class);
      Scoreboard var3 = ((CommandListenerWrapper)var0.getSource()).l().aF();
      ScoreboardObjective var4 = var3.d(var2);
      if (var4 == null) {
         throw b.create(var2);
      } else {
         return var4;
      }
   }

   public static ScoreboardObjective b(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      ScoreboardObjective var2 = a(var0, var1);
      if (var2.c().e()) {
         throw c.create(var2.b());
      } else {
         return var2;
      }
   }

   public String a(StringReader var0) throws CommandSyntaxException {
      return var0.readUnquotedString();
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      S var2 = (S)var0.getSource();
      if (var2 instanceof CommandListenerWrapper var3) {
         return ICompletionProvider.b(var3.l().aF().d(), var1);
      } else {
         return var2 instanceof ICompletionProvider var4 ? var4.a(var0) : Suggestions.empty();
      }
   }

   public Collection<String> getExamples() {
      return a;
   }
}
