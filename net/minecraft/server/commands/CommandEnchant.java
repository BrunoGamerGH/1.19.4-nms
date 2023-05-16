package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentManager;

public class CommandEnchant {
   private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.enchant.failed.entity", var0));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.enchant.failed.itemless", var0));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(
      var0 -> IChatBaseComponent.a("commands.enchant.failed.incompatible", var0)
   );
   private static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("commands.enchant.failed.level", var0, var1)
   );
   private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.enchant.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0, CommandBuildContext var1) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("enchant")
               .requires(var0x -> var0x.c(2)))
            .then(
               net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.b())
                  .then(
                     ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("enchantment", ResourceArgument.a(var1, Registries.q))
                           .executes(
                              var0x -> a(
                                    (CommandListenerWrapper)var0x.getSource(), ArgumentEntity.b(var0x, "targets"), ResourceArgument.g(var0x, "enchantment"), 1
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("level", IntegerArgumentType.integer(0))
                              .executes(
                                 var0x -> a(
                                       (CommandListenerWrapper)var0x.getSource(),
                                       ArgumentEntity.b(var0x, "targets"),
                                       ResourceArgument.g(var0x, "enchantment"),
                                       IntegerArgumentType.getInteger(var0x, "level")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, Collection<? extends Entity> var1, Holder<Enchantment> var2, int var3) throws CommandSyntaxException {
      Enchantment var4 = var2.a();
      if (var3 > var4.a()) {
         throw d.create(var3, var4.a());
      } else {
         int var5 = 0;

         for(Entity var7 : var1) {
            if (var7 instanceof EntityLiving var8) {
               ItemStack var9 = var8.eK();
               if (!var9.b()) {
                  if (var4.a(var9) && EnchantmentManager.a(EnchantmentManager.a(var9).keySet(), var4)) {
                     var9.a(var4, var3);
                     ++var5;
                  } else if (var1.size() == 1) {
                     throw c.create(var9.c().m(var9).getString());
                  }
               } else if (var1.size() == 1) {
                  throw b.create(var8.Z().getString());
               }
            } else if (var1.size() == 1) {
               throw a.create(var7.Z().getString());
            }
         }

         if (var5 == 0) {
            throw e.create();
         } else {
            if (var1.size() == 1) {
               var0.a(IChatBaseComponent.a("commands.enchant.success.single", var4.d(var3), var1.iterator().next().G_()), true);
            } else {
               var0.a(IChatBaseComponent.a("commands.enchant.success.multiple", var4.d(var3), var1.size()), true);
            }

            return var5;
         }
      }
   }
}
