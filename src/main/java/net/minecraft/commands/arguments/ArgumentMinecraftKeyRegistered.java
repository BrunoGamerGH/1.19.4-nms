package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.advancements.Advancement;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.crafting.CraftingManager;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.level.storage.loot.ItemModifierManager;
import net.minecraft.world.level.storage.loot.LootPredicateManager;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ArgumentMinecraftKeyRegistered implements ArgumentType<MinecraftKey> {
   private static final Collection<String> a = Arrays.asList("foo", "foo:bar", "012");
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("advancement.advancementNotFound", var0));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("recipe.notFound", var0));
   private static final DynamicCommandExceptionType d = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("predicate.unknown", var0));
   private static final DynamicCommandExceptionType e = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("item_modifier.unknown", var0));

   public static ArgumentMinecraftKeyRegistered a() {
      return new ArgumentMinecraftKeyRegistered();
   }

   public static Advancement a(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      MinecraftKey var2 = e(var0, var1);
      Advancement var3 = ((CommandListenerWrapper)var0.getSource()).l().az().a(var2);
      if (var3 == null) {
         throw b.create(var2);
      } else {
         return var3;
      }
   }

   public static IRecipe<?> b(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      CraftingManager var2 = ((CommandListenerWrapper)var0.getSource()).l().aE();
      MinecraftKey var3 = e(var0, var1);
      return var2.a(var3).orElseThrow(() -> c.create(var3));
   }

   public static LootItemCondition c(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      MinecraftKey var2 = e(var0, var1);
      LootPredicateManager var3 = ((CommandListenerWrapper)var0.getSource()).l().aI();
      LootItemCondition var4 = var3.a(var2);
      if (var4 == null) {
         throw d.create(var2);
      } else {
         return var4;
      }
   }

   public static LootItemFunction d(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      MinecraftKey var2 = e(var0, var1);
      ItemModifierManager var3 = ((CommandListenerWrapper)var0.getSource()).l().aJ();
      LootItemFunction var4 = var3.a(var2);
      if (var4 == null) {
         throw e.create(var2);
      } else {
         return var4;
      }
   }

   public static MinecraftKey e(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (MinecraftKey)var0.getArgument(var1, MinecraftKey.class);
   }

   public MinecraftKey a(StringReader var0) throws CommandSyntaxException {
      return MinecraftKey.a(var0);
   }

   public Collection<String> getExamples() {
      return a;
   }
}
