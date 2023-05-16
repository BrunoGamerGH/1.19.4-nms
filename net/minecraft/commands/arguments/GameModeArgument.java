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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.level.EnumGamemode;

public class GameModeArgument implements ArgumentType<EnumGamemode> {
   private static final Collection<String> a = Stream.of(EnumGamemode.a, EnumGamemode.b).map(EnumGamemode::b).collect(Collectors.toList());
   private static final EnumGamemode[] b = EnumGamemode.values();
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("argument.gamemode.invalid", var0));

   public EnumGamemode a(StringReader var0) throws CommandSyntaxException {
      String var1 = var0.readUnquotedString();
      EnumGamemode var2 = EnumGamemode.a(var1, null);
      if (var2 == null) {
         throw c.createWithContext(var0, var1);
      } else {
         return var2;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return var0.getSource() instanceof ICompletionProvider ? ICompletionProvider.b(Arrays.stream(b).map(EnumGamemode::b), var1) : Suggestions.empty();
   }

   public Collection<String> getExamples() {
      return a;
   }

   public static GameModeArgument a() {
      return new GameModeArgument();
   }

   public static EnumGamemode a(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return (EnumGamemode)var0.getArgument(var1, EnumGamemode.class);
   }
}
