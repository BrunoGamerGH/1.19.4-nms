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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.network.chat.IChatBaseComponent;

public class GameTestHarnessTestFunctionArgument implements ArgumentType<GameTestHarnessTestFunction> {
   private static final Collection<String> a = Arrays.asList("techtests.piston", "techtests");

   public GameTestHarnessTestFunction a(StringReader var0) throws CommandSyntaxException {
      String var1 = var0.readUnquotedString();
      Optional<GameTestHarnessTestFunction> var2 = GameTestHarnessRegistry.e(var1);
      if (var2.isPresent()) {
         return var2.get();
      } else {
         Message var3 = IChatBaseComponent.b("No such test: " + var1);
         throw new CommandSyntaxException(new SimpleCommandExceptionType(var3), var3);
      }
   }

   public static GameTestHarnessTestFunctionArgument a() {
      return new GameTestHarnessTestFunctionArgument();
   }

   public static GameTestHarnessTestFunction a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (GameTestHarnessTestFunction)var0.getArgument(var1, GameTestHarnessTestFunction.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      Stream<String> var2 = GameTestHarnessRegistry.a().stream().map(GameTestHarnessTestFunction::a);
      return ICompletionProvider.b(var2, var1);
   }

   public Collection<String> getExamples() {
      return a;
   }
}
