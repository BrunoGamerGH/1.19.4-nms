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
import net.minecraft.EnumChatFormat;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.network.chat.IChatBaseComponent;

public class ArgumentChatFormat implements ArgumentType<EnumChatFormat> {
   private static final Collection<String> b = Arrays.asList("red", "green");
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("argument.color.invalid", var0));

   private ArgumentChatFormat() {
   }

   public static ArgumentChatFormat a() {
      return new ArgumentChatFormat();
   }

   public static EnumChatFormat a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (EnumChatFormat)var0.getArgument(var1, EnumChatFormat.class);
   }

   public EnumChatFormat a(StringReader var0) throws CommandSyntaxException {
      String var1 = var0.readUnquotedString();
      EnumChatFormat var2 = EnumChatFormat.b(var1);
      if (var2 != null && !var2.d()) {
         return var2;
      } else {
         throw a.create(var1);
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return ICompletionProvider.b(EnumChatFormat.a(true, false), var1);
   }

   public Collection<String> getExamples() {
      return b;
   }
}
