package net.minecraft.commands.arguments.item;

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
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class ArgumentItemStack implements ArgumentType<ArgumentPredicateItemStack> {
   private static final Collection<String> a = Arrays.asList("stick", "minecraft:stick", "stick{foo=bar}");
   private final HolderLookup<Item> b;

   public ArgumentItemStack(CommandBuildContext var0) {
      this.b = var0.a(Registries.C);
   }

   public static ArgumentItemStack a(CommandBuildContext var0) {
      return new ArgumentItemStack(var0);
   }

   public ArgumentPredicateItemStack a(StringReader var0) throws CommandSyntaxException {
      ArgumentParserItemStack.a var1 = ArgumentParserItemStack.a(this.b, var0);
      return new ArgumentPredicateItemStack(var1.a(), var1.b());
   }

   public static <S> ArgumentPredicateItemStack a(CommandContext<S> var0, String var1) {
      return (ArgumentPredicateItemStack)var0.getArgument(var1, ArgumentPredicateItemStack.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return ArgumentParserItemStack.a(this.b, var1, false);
   }

   public Collection<String> getExamples() {
      return a;
   }
}
