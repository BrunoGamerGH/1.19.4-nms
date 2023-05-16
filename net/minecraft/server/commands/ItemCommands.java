package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ArgumentInventorySlot;
import net.minecraft.commands.arguments.ArgumentMinecraftKeyRegistered;
import net.minecraft.commands.arguments.coordinates.ArgumentPosition;
import net.minecraft.commands.arguments.item.ArgumentItemStack;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.storage.loot.ItemModifierManager;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;

public class ItemCommands {
   static final Dynamic3CommandExceptionType a = new Dynamic3CommandExceptionType(
      (var0, var1, var2) -> IChatBaseComponent.a("commands.item.target.not_a_container", var0, var1, var2)
   );
   private static final Dynamic3CommandExceptionType c = new Dynamic3CommandExceptionType(
      (var0, var1, var2) -> IChatBaseComponent.a("commands.item.source.not_a_container", var0, var1, var2)
   );
   static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.item.target.no_such_slot", var0));
   private static final DynamicCommandExceptionType d = new DynamicCommandExceptionType(
      var0 -> IChatBaseComponent.a("commands.item.source.no_such_slot", var0)
   );
   private static final DynamicCommandExceptionType e = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.item.target.no_changes", var0));
   private static final Dynamic2CommandExceptionType f = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("commands.item.target.no_changed.known_item", var0, var1)
   );
   private static final SuggestionProvider<CommandListenerWrapper> g = (var0, var1) -> {
      ItemModifierManager var2 = ((CommandListenerWrapper)var0.getSource()).l().aJ();
      return ICompletionProvider.a(var2.a(), var1);
   };

   public static void a(CommandDispatcher<CommandListenerWrapper> var0, CommandBuildContext var1) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("item")
                  .requires(var0x -> var0x.c(2)))
               .then(
                  ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("replace")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("block")
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("pos", ArgumentPosition.a())
                                    .then(
                                       ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("slot", ArgumentInventorySlot.a())
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("with")
                                                   .then(
                                                      ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("item", ArgumentItemStack.a(var1))
                                                            .executes(
                                                               var0x -> a(
                                                                     (CommandListenerWrapper)var0x.getSource(),
                                                                     ArgumentPosition.a(var0x, "pos"),
                                                                     ArgumentInventorySlot.a(var0x, "slot"),
                                                                     ArgumentItemStack.a(var0x, "item").a(1, false)
                                                                  )
                                                            ))
                                                         .then(
                                                            net.minecraft.commands.CommandDispatcher.a("count", IntegerArgumentType.integer(1, 64))
                                                               .executes(
                                                                  var0x -> a(
                                                                        (CommandListenerWrapper)var0x.getSource(),
                                                                        ArgumentPosition.a(var0x, "pos"),
                                                                        ArgumentInventorySlot.a(var0x, "slot"),
                                                                        ArgumentItemStack.a(var0x, "item")
                                                                           .a(IntegerArgumentType.getInteger(var0x, "count"), true)
                                                                     )
                                                               )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("from")
                                                   .then(
                                                      net.minecraft.commands.CommandDispatcher.a("block")
                                                         .then(
                                                            net.minecraft.commands.CommandDispatcher.a("source", ArgumentPosition.a())
                                                               .then(
                                                                  ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                                           "sourceSlot", ArgumentInventorySlot.a()
                                                                        )
                                                                        .executes(
                                                                           var0x -> a(
                                                                                 (CommandListenerWrapper)var0x.getSource(),
                                                                                 ArgumentPosition.a(var0x, "source"),
                                                                                 ArgumentInventorySlot.a(var0x, "sourceSlot"),
                                                                                 ArgumentPosition.a(var0x, "pos"),
                                                                                 ArgumentInventorySlot.a(var0x, "slot")
                                                                              )
                                                                        ))
                                                                     .then(
                                                                        net.minecraft.commands.CommandDispatcher.a(
                                                                              "modifier", ArgumentMinecraftKeyRegistered.a()
                                                                           )
                                                                           .suggests(g)
                                                                           .executes(
                                                                              var0x -> a(
                                                                                    (CommandListenerWrapper)var0x.getSource(),
                                                                                    ArgumentPosition.a(var0x, "source"),
                                                                                    ArgumentInventorySlot.a(var0x, "sourceSlot"),
                                                                                    ArgumentPosition.a(var0x, "pos"),
                                                                                    ArgumentInventorySlot.a(var0x, "slot"),
                                                                                    ArgumentMinecraftKeyRegistered.d(var0x, "modifier")
                                                                                 )
                                                                           )
                                                                     )
                                                               )
                                                         )
                                                   ))
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("entity")
                                                      .then(
                                                         net.minecraft.commands.CommandDispatcher.a("source", ArgumentEntity.a())
                                                            .then(
                                                               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                                        "sourceSlot", ArgumentInventorySlot.a()
                                                                     )
                                                                     .executes(
                                                                        var0x -> a(
                                                                              (CommandListenerWrapper)var0x.getSource(),
                                                                              ArgumentEntity.a(var0x, "source"),
                                                                              ArgumentInventorySlot.a(var0x, "sourceSlot"),
                                                                              ArgumentPosition.a(var0x, "pos"),
                                                                              ArgumentInventorySlot.a(var0x, "slot")
                                                                           )
                                                                     ))
                                                                  .then(
                                                                     net.minecraft.commands.CommandDispatcher.a("modifier", ArgumentMinecraftKeyRegistered.a())
                                                                        .suggests(g)
                                                                        .executes(
                                                                           var0x -> a(
                                                                                 (CommandListenerWrapper)var0x.getSource(),
                                                                                 ArgumentEntity.a(var0x, "source"),
                                                                                 ArgumentInventorySlot.a(var0x, "sourceSlot"),
                                                                                 ArgumentPosition.a(var0x, "pos"),
                                                                                 ArgumentInventorySlot.a(var0x, "slot"),
                                                                                 ArgumentMinecraftKeyRegistered.d(var0x, "modifier")
                                                                              )
                                                                        )
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        ))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("entity")
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.b())
                                 .then(
                                    ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("slot", ArgumentInventorySlot.a())
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("with")
                                                .then(
                                                   ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("item", ArgumentItemStack.a(var1))
                                                         .executes(
                                                            var0x -> a(
                                                                  (CommandListenerWrapper)var0x.getSource(),
                                                                  ArgumentEntity.b(var0x, "targets"),
                                                                  ArgumentInventorySlot.a(var0x, "slot"),
                                                                  ArgumentItemStack.a(var0x, "item").a(1, false)
                                                               )
                                                         ))
                                                      .then(
                                                         net.minecraft.commands.CommandDispatcher.a("count", IntegerArgumentType.integer(1, 64))
                                                            .executes(
                                                               var0x -> a(
                                                                     (CommandListenerWrapper)var0x.getSource(),
                                                                     ArgumentEntity.b(var0x, "targets"),
                                                                     ArgumentInventorySlot.a(var0x, "slot"),
                                                                     ArgumentItemStack.a(var0x, "item")
                                                                        .a(IntegerArgumentType.getInteger(var0x, "count"), true)
                                                                  )
                                                            )
                                                      )
                                                )
                                          ))
                                       .then(
                                          ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("from")
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("block")
                                                      .then(
                                                         net.minecraft.commands.CommandDispatcher.a("source", ArgumentPosition.a())
                                                            .then(
                                                               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                                        "sourceSlot", ArgumentInventorySlot.a()
                                                                     )
                                                                     .executes(
                                                                        var0x -> a(
                                                                              (CommandListenerWrapper)var0x.getSource(),
                                                                              ArgumentPosition.a(var0x, "source"),
                                                                              ArgumentInventorySlot.a(var0x, "sourceSlot"),
                                                                              ArgumentEntity.b(var0x, "targets"),
                                                                              ArgumentInventorySlot.a(var0x, "slot")
                                                                           )
                                                                     ))
                                                                  .then(
                                                                     net.minecraft.commands.CommandDispatcher.a("modifier", ArgumentMinecraftKeyRegistered.a())
                                                                        .suggests(g)
                                                                        .executes(
                                                                           var0x -> a(
                                                                                 (CommandListenerWrapper)var0x.getSource(),
                                                                                 ArgumentPosition.a(var0x, "source"),
                                                                                 ArgumentInventorySlot.a(var0x, "sourceSlot"),
                                                                                 ArgumentEntity.b(var0x, "targets"),
                                                                                 ArgumentInventorySlot.a(var0x, "slot"),
                                                                                 ArgumentMinecraftKeyRegistered.d(var0x, "modifier")
                                                                              )
                                                                        )
                                                                  )
                                                            )
                                                      )
                                                ))
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("entity")
                                                   .then(
                                                      net.minecraft.commands.CommandDispatcher.a("source", ArgumentEntity.a())
                                                         .then(
                                                            ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                                     "sourceSlot", ArgumentInventorySlot.a()
                                                                  )
                                                                  .executes(
                                                                     var0x -> a(
                                                                           (CommandListenerWrapper)var0x.getSource(),
                                                                           ArgumentEntity.a(var0x, "source"),
                                                                           ArgumentInventorySlot.a(var0x, "sourceSlot"),
                                                                           ArgumentEntity.b(var0x, "targets"),
                                                                           ArgumentInventorySlot.a(var0x, "slot")
                                                                        )
                                                                  ))
                                                               .then(
                                                                  net.minecraft.commands.CommandDispatcher.a("modifier", ArgumentMinecraftKeyRegistered.a())
                                                                     .suggests(g)
                                                                     .executes(
                                                                        var0x -> a(
                                                                              (CommandListenerWrapper)var0x.getSource(),
                                                                              ArgumentEntity.a(var0x, "source"),
                                                                              ArgumentInventorySlot.a(var0x, "sourceSlot"),
                                                                              ArgumentEntity.b(var0x, "targets"),
                                                                              ArgumentInventorySlot.a(var0x, "slot"),
                                                                              ArgumentMinecraftKeyRegistered.d(var0x, "modifier")
                                                                           )
                                                                     )
                                                               )
                                                         )
                                                   )
                                             )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("modify")
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("block")
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("pos", ArgumentPosition.a())
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("slot", ArgumentInventorySlot.a())
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("modifier", ArgumentMinecraftKeyRegistered.a())
                                             .suggests(g)
                                             .executes(
                                                var0x -> a(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      ArgumentPosition.a(var0x, "pos"),
                                                      ArgumentInventorySlot.a(var0x, "slot"),
                                                      ArgumentMinecraftKeyRegistered.d(var0x, "modifier")
                                                   )
                                             )
                                       )
                                 )
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("entity")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.b())
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("slot", ArgumentInventorySlot.a())
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("modifier", ArgumentMinecraftKeyRegistered.a())
                                          .suggests(g)
                                          .executes(
                                             var0x -> a(
                                                   (CommandListenerWrapper)var0x.getSource(),
                                                   ArgumentEntity.b(var0x, "targets"),
                                                   ArgumentInventorySlot.a(var0x, "slot"),
                                                   ArgumentMinecraftKeyRegistered.d(var0x, "modifier")
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, BlockPosition var1, int var2, LootItemFunction var3) throws CommandSyntaxException {
      IInventory var4 = a(var0, var1, a);
      if (var2 >= 0 && var2 < var4.b()) {
         ItemStack var5 = a(var0, var3, var4.a(var2));
         var4.a(var2, var5);
         var0.a(IChatBaseComponent.a("commands.item.block.set.success", var1.u(), var1.v(), var1.w(), var5.I()), true);
         return 1;
      } else {
         throw b.create(var2);
      }
   }

   private static int a(CommandListenerWrapper var0, Collection<? extends Entity> var1, int var2, LootItemFunction var3) throws CommandSyntaxException {
      Map<Entity, ItemStack> var4 = Maps.newHashMapWithExpectedSize(var1.size());

      for(Entity var6 : var1) {
         SlotAccess var7 = var6.a_(var2);
         if (var7 != SlotAccess.b) {
            ItemStack var8 = a(var0, var3, var7.a().o());
            if (var7.a(var8)) {
               var4.put(var6, var8);
               if (var6 instanceof EntityPlayer) {
                  ((EntityPlayer)var6).bP.d();
               }
            }
         }
      }

      if (var4.isEmpty()) {
         throw e.create(var2);
      } else {
         if (var4.size() == 1) {
            Entry<Entity, ItemStack> var5 = var4.entrySet().iterator().next();
            var0.a(IChatBaseComponent.a("commands.item.entity.set.success.single", var5.getKey().G_(), var5.getValue().I()), true);
         } else {
            var0.a(IChatBaseComponent.a("commands.item.entity.set.success.multiple", var4.size()), true);
         }

         return var4.size();
      }
   }

   private static int a(CommandListenerWrapper var0, BlockPosition var1, int var2, ItemStack var3) throws CommandSyntaxException {
      IInventory var4 = a(var0, var1, a);
      if (var2 >= 0 && var2 < var4.b()) {
         var4.a(var2, var3);
         var0.a(IChatBaseComponent.a("commands.item.block.set.success", var1.u(), var1.v(), var1.w(), var3.I()), true);
         return 1;
      } else {
         throw b.create(var2);
      }
   }

   private static IInventory a(CommandListenerWrapper var0, BlockPosition var1, Dynamic3CommandExceptionType var2) throws CommandSyntaxException {
      TileEntity var3 = var0.e().c_(var1);
      if (!(var3 instanceof IInventory)) {
         throw var2.create(var1.u(), var1.v(), var1.w());
      } else {
         return (IInventory)var3;
      }
   }

   private static int a(CommandListenerWrapper var0, Collection<? extends Entity> var1, int var2, ItemStack var3) throws CommandSyntaxException {
      List<Entity> var4 = Lists.newArrayListWithCapacity(var1.size());

      for(Entity var6 : var1) {
         SlotAccess var7 = var6.a_(var2);
         if (var7 != SlotAccess.b && var7.a(var3.o())) {
            var4.add(var6);
            if (var6 instanceof EntityPlayer) {
               ((EntityPlayer)var6).bP.d();
            }
         }
      }

      if (var4.isEmpty()) {
         throw f.create(var3.I(), var2);
      } else {
         if (var4.size() == 1) {
            var0.a(IChatBaseComponent.a("commands.item.entity.set.success.single", var4.iterator().next().G_(), var3.I()), true);
         } else {
            var0.a(IChatBaseComponent.a("commands.item.entity.set.success.multiple", var4.size(), var3.I()), true);
         }

         return var4.size();
      }
   }

   private static int a(CommandListenerWrapper var0, BlockPosition var1, int var2, Collection<? extends Entity> var3, int var4) throws CommandSyntaxException {
      return a(var0, var3, var4, a(var0, var1, var2));
   }

   private static int a(CommandListenerWrapper var0, BlockPosition var1, int var2, Collection<? extends Entity> var3, int var4, LootItemFunction var5) throws CommandSyntaxException {
      return a(var0, var3, var4, a(var0, var5, a(var0, var1, var2)));
   }

   private static int a(CommandListenerWrapper var0, BlockPosition var1, int var2, BlockPosition var3, int var4) throws CommandSyntaxException {
      return a(var0, var3, var4, a(var0, var1, var2));
   }

   private static int a(CommandListenerWrapper var0, BlockPosition var1, int var2, BlockPosition var3, int var4, LootItemFunction var5) throws CommandSyntaxException {
      return a(var0, var3, var4, a(var0, var5, a(var0, var1, var2)));
   }

   private static int a(CommandListenerWrapper var0, Entity var1, int var2, BlockPosition var3, int var4) throws CommandSyntaxException {
      return a(var0, var3, var4, a(var1, var2));
   }

   private static int a(CommandListenerWrapper var0, Entity var1, int var2, BlockPosition var3, int var4, LootItemFunction var5) throws CommandSyntaxException {
      return a(var0, var3, var4, a(var0, var5, a(var1, var2)));
   }

   private static int a(CommandListenerWrapper var0, Entity var1, int var2, Collection<? extends Entity> var3, int var4) throws CommandSyntaxException {
      return a(var0, var3, var4, a(var1, var2));
   }

   private static int a(CommandListenerWrapper var0, Entity var1, int var2, Collection<? extends Entity> var3, int var4, LootItemFunction var5) throws CommandSyntaxException {
      return a(var0, var3, var4, a(var0, var5, a(var1, var2)));
   }

   private static ItemStack a(CommandListenerWrapper var0, LootItemFunction var1, ItemStack var2) {
      WorldServer var3 = var0.e();
      LootTableInfo.Builder var4 = new LootTableInfo.Builder(var3).a(LootContextParameters.f, var0.d()).b(LootContextParameters.a, var0.f());
      return var1.apply(var2, var4.a(LootContextParameterSets.c));
   }

   private static ItemStack a(Entity var0, int var1) throws CommandSyntaxException {
      SlotAccess var2 = var0.a_(var1);
      if (var2 == SlotAccess.b) {
         throw d.create(var1);
      } else {
         return var2.a().o();
      }
   }

   private static ItemStack a(CommandListenerWrapper var0, BlockPosition var1, int var2) throws CommandSyntaxException {
      IInventory var3 = a(var0, var1, c);
      if (var2 >= 0 && var2 < var3.b()) {
         return var3.a(var2).o();
      } else {
         throw d.create(var2);
      }
   }
}
