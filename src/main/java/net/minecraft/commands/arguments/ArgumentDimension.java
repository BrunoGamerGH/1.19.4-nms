package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.World;

public class ArgumentDimension implements ArgumentType<MinecraftKey> {
   private static final Collection<String> a = Stream.of(World.h, World.i).map(var0 -> var0.a().toString()).collect(Collectors.toList());
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("argument.dimension.invalid", var0));

   public MinecraftKey a(StringReader var0) throws CommandSyntaxException {
      return MinecraftKey.a(var0);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return var0.getSource() instanceof ICompletionProvider
         ? ICompletionProvider.a(((ICompletionProvider)var0.getSource()).t().stream().map(ResourceKey::a), var1)
         : Suggestions.empty();
   }

   public Collection<String> getExamples() {
      return a;
   }

   public static ArgumentDimension a() {
      return new ArgumentDimension();
   }

   public static WorldServer a(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      MinecraftKey var2 = (MinecraftKey)var0.getArgument(var1, MinecraftKey.class);
      ResourceKey<World> var3 = ResourceKey.a(Registries.aF, var2);
      WorldServer var4 = ((CommandListenerWrapper)var0.getSource()).l().a(var3);
      if (var4 == null) {
         throw b.create(var2);
      } else {
         return var4;
      }
   }
}
