package net.minecraft.gametest.framework;

import com.mojang.brigadier.Message;
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

public class GameTestHarnessTestClassArgument implements ArgumentType<String> {
   private static final Collection<String> a = Arrays.asList("techtests", "mobtests");

   public String a(StringReader var0) throws CommandSyntaxException {
      String var1 = var0.readUnquotedString();
      if (GameTestHarnessRegistry.b(var1)) {
         return var1;
      } else {
         Message var2 = IChatBaseComponent.b("No such test class: " + var1);
         throw new CommandSyntaxException(new SimpleCommandExceptionType(var2), var2);
      }
   }

   public static GameTestHarnessTestClassArgument a() {
      return new GameTestHarnessTestClassArgument();
   }

   public static String a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (String)var0.getArgument(var1, String.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return ICompletionProvider.b(GameTestHarnessRegistry.b().stream(), var1);
   }

   public Collection<String> getExamples() {
      return a;
   }
}
