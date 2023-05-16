package net.minecraft.commands.arguments;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.SystemUtils;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.EnumItemSlot;

public class ArgumentInventorySlot implements ArgumentType<Integer> {
   private static final Collection<String> a = Arrays.asList("container.5", "12", "weapon");
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("slot.unknown", var0));
   private static final Map<String, Integer> c = SystemUtils.a(Maps.newHashMap(), var0 -> {
      for(int var1 = 0; var1 < 54; ++var1) {
         var0.put("container." + var1, var1);
      }

      for(int var1 = 0; var1 < 9; ++var1) {
         var0.put("hotbar." + var1, var1);
      }

      for(int var1 = 0; var1 < 27; ++var1) {
         var0.put("inventory." + var1, 9 + var1);
      }

      for(int var1 = 0; var1 < 27; ++var1) {
         var0.put("enderchest." + var1, 200 + var1);
      }

      for(int var1 = 0; var1 < 8; ++var1) {
         var0.put("villager." + var1, 300 + var1);
      }

      for(int var1 = 0; var1 < 15; ++var1) {
         var0.put("horse." + var1, 500 + var1);
      }

      var0.put("weapon", EnumItemSlot.a.a(98));
      var0.put("weapon.mainhand", EnumItemSlot.a.a(98));
      var0.put("weapon.offhand", EnumItemSlot.b.a(98));
      var0.put("armor.head", EnumItemSlot.f.a(100));
      var0.put("armor.chest", EnumItemSlot.e.a(100));
      var0.put("armor.legs", EnumItemSlot.d.a(100));
      var0.put("armor.feet", EnumItemSlot.c.a(100));
      var0.put("horse.saddle", 400);
      var0.put("horse.armor", 401);
      var0.put("horse.chest", 499);
   });

   public static ArgumentInventorySlot a() {
      return new ArgumentInventorySlot();
   }

   public static int a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return var0.getArgument(var1, Integer.class);
   }

   public Integer a(StringReader var0) throws CommandSyntaxException {
      String var1 = var0.readUnquotedString();
      if (!c.containsKey(var1)) {
         throw b.create(var1);
      } else {
         return c.get(var1);
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return ICompletionProvider.b(c.keySet(), var1);
   }

   public Collection<String> getExamples() {
      return a;
   }
}
