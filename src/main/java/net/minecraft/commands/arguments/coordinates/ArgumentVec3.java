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
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.phys.Vec3D;

public class ArgumentVec3 implements ArgumentType<IVectorPosition> {
   private static final Collection<String> c = Arrays.asList("0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "0.1 -0.5 .9", "~0.5 ~1 ~-5");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.pos3d.incomplete"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.pos.mixed"));
   private final boolean d;

   public ArgumentVec3(boolean var0) {
      this.d = var0;
   }

   public static ArgumentVec3 a() {
      return new ArgumentVec3(true);
   }

   public static ArgumentVec3 a(boolean var0) {
      return new ArgumentVec3(var0);
   }

   public static Vec3D a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return ((IVectorPosition)var0.getArgument(var1, IVectorPosition.class)).a((CommandListenerWrapper)var0.getSource());
   }

   public static IVectorPosition b(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (IVectorPosition)var0.getArgument(var1, IVectorPosition.class);
   }

   public IVectorPosition a(StringReader var0) throws CommandSyntaxException {
      return (IVectorPosition)(var0.canRead() && var0.peek() == '^' ? ArgumentVectorPosition.a(var0) : VectorPosition.a(var0, this.d));
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
            var3 = ((ICompletionProvider)var0.getSource()).z();
         }

         return ICompletionProvider.a(var2, var3, var1, CommandDispatcher.a(this::a));
      }
   }

   public Collection<String> getExamples() {
      return c;
   }
}
