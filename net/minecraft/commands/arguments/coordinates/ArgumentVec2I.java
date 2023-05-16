package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandDispatcher;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.BlockPosition2D;

public class ArgumentVec2I implements ArgumentType<IVectorPosition> {
   private static final Collection<String> b = Arrays.asList("0 0", "~ ~", "~1 ~-2", "^ ^", "^-1 ^0");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.pos2d.incomplete"));

   public static ArgumentVec2I a() {
      return new ArgumentVec2I();
   }

   public static BlockPosition2D a(CommandContext<CommandListenerWrapper> var0, String var1) {
      BlockPosition var2 = ((IVectorPosition)var0.getArgument(var1, IVectorPosition.class)).c((CommandListenerWrapper)var0.getSource());
      return new BlockPosition2D(var2.u(), var2.w());
   }

   public IVectorPosition a(StringReader var0) throws CommandSyntaxException {
      int var1 = var0.getCursor();
      if (!var0.canRead()) {
         throw a.createWithContext(var0);
      } else {
         ArgumentParserPosition var2 = ArgumentParserPosition.a(var0);
         if (var0.canRead() && var0.peek() == ' ') {
            var0.skip();
            ArgumentParserPosition var3 = ArgumentParserPosition.a(var0);
            return new VectorPosition(var2, new ArgumentParserPosition(true, 0.0), var3);
         } else {
            var0.setCursor(var1);
            throw a.createWithContext(var0);
         }
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      if (!(var0.getSource() instanceof ICompletionProvider)) {
         return Suggestions.empty();
      } else {
         String var2 = var1.getRemaining();
         Collection<ICompletionProvider.b> var3;
         if (!var2.isEmpty() && var2.charAt(0) == '^') {
            var3 = Collections.singleton(ICompletionProvider.b.a);
         } else {
            var3 = ((ICompletionProvider)var0.getSource()).y();
         }

         return ICompletionProvider.b(var2, var3, var1, CommandDispatcher.a(this::a));
      }
   }

   public Collection<String> getExamples() {
      return b;
   }
}
