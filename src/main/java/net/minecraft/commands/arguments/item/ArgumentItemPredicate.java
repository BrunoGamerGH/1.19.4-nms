package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.datafixers.util.Either;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ArgumentItemPredicate implements ArgumentType<ArgumentItemPredicate.a> {
   private static final Collection<String> a = Arrays.asList("stick", "minecraft:stick", "#stick", "#stick{foo=bar}");
   private final HolderLookup<Item> b;

   public ArgumentItemPredicate(CommandBuildContext var0) {
      this.b = var0.a(Registries.C);
   }

   public static ArgumentItemPredicate a(CommandBuildContext var0) {
      return new ArgumentItemPredicate(var0);
   }

   public ArgumentItemPredicate.a a(StringReader var0) throws CommandSyntaxException {
      Either<ArgumentParserItemStack.a, ArgumentParserItemStack.b> var1 = ArgumentParserItemStack.b(this.b, var0);
      return (ArgumentItemPredicate.a)var1.map(var0x -> a(var1x -> var1x == var0x.a(), var0x.b()), var0x -> a(var0x.a()::a, var0x.b()));
   }

   public static Predicate<ItemStack> a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (Predicate<ItemStack>)var0.getArgument(var1, ArgumentItemPredicate.a.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return ArgumentParserItemStack.a(this.b, var1, true);
   }

   public Collection<String> getExamples() {
      return a;
   }

   private static ArgumentItemPredicate.a a(Predicate<Holder<Item>> var0, @Nullable NBTTagCompound var1) {
      return var1 != null ? var2 -> var2.a(var0) && GameProfileSerializer.a(var1, var2.u(), true) : var1x -> var1x.a(var0);
   }

   public interface a extends Predicate<ItemStack> {
   }
}
