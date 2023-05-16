package net.minecraft.commands.arguments.blocks;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

public class ArgumentTile implements ArgumentType<ArgumentTileLocation> {
   private static final Collection<String> a = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "foo{bar=baz}");
   private final HolderLookup<Block> b;

   public ArgumentTile(CommandBuildContext var0) {
      this.b = var0.a(Registries.e);
   }

   public static ArgumentTile a(CommandBuildContext var0) {
      return new ArgumentTile(var0);
   }

   public ArgumentTileLocation a(StringReader var0) throws CommandSyntaxException {
      ArgumentBlock.a var1 = ArgumentBlock.a(this.b, var0, true);
      return new ArgumentTileLocation(var1.a(), var1.b().keySet(), var1.c());
   }

   public static ArgumentTileLocation a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (ArgumentTileLocation)var0.getArgument(var1, ArgumentTileLocation.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return ArgumentBlock.a(this.b, var1, false, true);
   }

   public Collection<String> getExamples() {
      return a;
   }
}
