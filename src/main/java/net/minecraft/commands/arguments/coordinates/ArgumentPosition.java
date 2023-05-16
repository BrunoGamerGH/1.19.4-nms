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
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.World;

public class ArgumentPosition implements ArgumentType<IVectorPosition> {
   private static final Collection<String> d = Arrays.asList("0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "~0.5 ~1 ~-5");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.pos.unloaded"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.pos.outofworld"));
   public static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.pos.outofbounds"));

   public static ArgumentPosition a() {
      return new ArgumentPosition();
   }

   public static BlockPosition a(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      WorldServer var2 = ((CommandListenerWrapper)var0.getSource()).e();
      return a(var0, var2, var1);
   }

   public static BlockPosition a(CommandContext<CommandListenerWrapper> var0, WorldServer var1, String var2) throws CommandSyntaxException {
      BlockPosition var3 = b(var0, var2);
      if (!var1.D(var3)) {
         throw a.create();
      } else if (!var1.j(var3)) {
         throw b.create();
      } else {
         return var3;
      }
   }

   public static BlockPosition b(CommandContext<CommandListenerWrapper> var0, String var1) {
      return ((IVectorPosition)var0.getArgument(var1, IVectorPosition.class)).c((CommandListenerWrapper)var0.getSource());
   }

   public static BlockPosition c(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      BlockPosition var2 = b(var0, var1);
      if (!World.k(var2)) {
         throw c.create();
      } else {
         return var2;
      }
   }

   public IVectorPosition a(StringReader var0) throws CommandSyntaxException {
      return (IVectorPosition)(var0.canRead() && var0.peek() == '^' ? ArgumentVectorPosition.a(var0) : VectorPosition.a(var0));
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

         return ICompletionProvider.a(var2, var3, var1, CommandDispatcher.a(this::a));
      }
   }

   public Collection<String> getExamples() {
      return d;
   }
}
