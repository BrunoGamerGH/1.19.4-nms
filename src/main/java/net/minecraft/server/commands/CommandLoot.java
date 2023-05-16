package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ArgumentInventorySlot;
import net.minecraft.commands.arguments.ArgumentMinecraftKeyRegistered;
import net.minecraft.commands.arguments.coordinates.ArgumentPosition;
import net.minecraft.commands.arguments.coordinates.ArgumentVec3;
import net.minecraft.commands.arguments.item.ArgumentItemStack;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.LootTableRegistry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.Vec3D;

public class CommandLoot {
   public static final SuggestionProvider<CommandListenerWrapper> a = (commandcontext, suggestionsbuilder) -> {
      LootTableRegistry loottableregistry = ((CommandListenerWrapper)commandcontext.getSource()).l().aH();
      return ICompletionProvider.a(loottableregistry.a(), suggestionsbuilder);
   };
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> IChatBaseComponent.a("commands.drop.no_held_items", object));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(object -> IChatBaseComponent.a("commands.drop.no_loot_table", object));

   public static void a(CommandDispatcher<CommandListenerWrapper> commanddispatcher, CommandBuildContext commandbuildcontext) {
      commanddispatcher.register(
         a(
            (LiteralArgumentBuilder<CommandListenerWrapper>)net.minecraft.commands.CommandDispatcher.a("loot")
               .requires(commandlistenerwrapper -> commandlistenerwrapper.c(2)),
            (argumentbuilder, commandloot_b) -> argumentbuilder.then(
                     net.minecraft.commands.CommandDispatcher.a("fish")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("loot_table", ArgumentMinecraftKeyRegistered.a())
                              .suggests(a)
                              .then(
                                 ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                "pos", ArgumentPosition.a()
                                             )
                                             .executes(
                                                commandcontext -> a(
                                                      commandcontext,
                                                      ArgumentMinecraftKeyRegistered.e(commandcontext, "loot_table"),
                                                      ArgumentPosition.a(commandcontext, "pos"),
                                                      ItemStack.b,
                                                      commandloot_b
                                                   )
                                             ))
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("tool", ArgumentItemStack.a(commandbuildcontext))
                                                .executes(
                                                   commandcontext -> a(
                                                         commandcontext,
                                                         ArgumentMinecraftKeyRegistered.e(commandcontext, "loot_table"),
                                                         ArgumentPosition.a(commandcontext, "pos"),
                                                         ArgumentItemStack.a(commandcontext, "tool").a(1, false),
                                                         commandloot_b
                                                      )
                                                )
                                          ))
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("mainhand")
                                             .executes(
                                                commandcontext -> a(
                                                      commandcontext,
                                                      ArgumentMinecraftKeyRegistered.e(commandcontext, "loot_table"),
                                                      ArgumentPosition.a(commandcontext, "pos"),
                                                      a((CommandListenerWrapper)commandcontext.getSource(), EnumItemSlot.a),
                                                      commandloot_b
                                                   )
                                             )
                                       ))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("offhand")
                                          .executes(
                                             commandcontext -> a(
                                                   commandcontext,
                                                   ArgumentMinecraftKeyRegistered.e(commandcontext, "loot_table"),
                                                   ArgumentPosition.a(commandcontext, "pos"),
                                                   a((CommandListenerWrapper)commandcontext.getSource(), EnumItemSlot.b),
                                                   commandloot_b
                                                )
                                          )
                                    )
                              )
                        )
                  )
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("loot")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("loot_table", ArgumentMinecraftKeyRegistered.a())
                              .suggests(a)
                              .executes(commandcontext -> a(commandcontext, ArgumentMinecraftKeyRegistered.e(commandcontext, "loot_table"), commandloot_b))
                        )
                  )
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("kill")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("target", ArgumentEntity.a())
                              .executes(commandcontext -> a(commandcontext, ArgumentEntity.a(commandcontext, "target"), commandloot_b))
                        )
                  )
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("mine")
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                          "pos", ArgumentPosition.a()
                                       )
                                       .executes(commandcontext -> a(commandcontext, ArgumentPosition.a(commandcontext, "pos"), ItemStack.b, commandloot_b)))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("tool", ArgumentItemStack.a(commandbuildcontext))
                                          .executes(
                                             commandcontext -> a(
                                                   commandcontext,
                                                   ArgumentPosition.a(commandcontext, "pos"),
                                                   ArgumentItemStack.a(commandcontext, "tool").a(1, false),
                                                   commandloot_b
                                                )
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("mainhand")
                                       .executes(
                                          commandcontext -> a(
                                                commandcontext,
                                                ArgumentPosition.a(commandcontext, "pos"),
                                                a((CommandListenerWrapper)commandcontext.getSource(), EnumItemSlot.a),
                                                commandloot_b
                                             )
                                       )
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("offhand")
                                    .executes(
                                       commandcontext -> a(
                                             commandcontext,
                                             ArgumentPosition.a(commandcontext, "pos"),
                                             a((CommandListenerWrapper)commandcontext.getSource(), EnumItemSlot.b),
                                             commandloot_b
                                          )
                                    )
                              )
                        )
                  )
         )
      );
   }

   private static <T extends ArgumentBuilder<CommandListenerWrapper, T>> T a(T t0, CommandLoot.c commandloot_c) {
      return (T)t0.then(
            ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("replace")
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("entity")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("entities", ArgumentEntity.b())
                              .then(
                                 commandloot_c.construct(
                                       net.minecraft.commands.CommandDispatcher.a("slot", ArgumentInventorySlot.a()),
                                       (commandcontext, list, commandloot_a) -> a(
                                             ArgumentEntity.b(commandcontext, "entities"),
                                             ArgumentInventorySlot.a(commandcontext, "slot"),
                                             list.size(),
                                             list,
                                             commandloot_a
                                          )
                                    )
                                    .then(
                                       commandloot_c.construct(
                                          net.minecraft.commands.CommandDispatcher.a("count", IntegerArgumentType.integer(0)),
                                          (commandcontext, list, commandloot_a) -> a(
                                                ArgumentEntity.b(commandcontext, "entities"),
                                                ArgumentInventorySlot.a(commandcontext, "slot"),
                                                IntegerArgumentType.getInteger(commandcontext, "count"),
                                                list,
                                                commandloot_a
                                             )
                                       )
                                    )
                              )
                        )
                  ))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("block")
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("targetPos", ArgumentPosition.a())
                           .then(
                              commandloot_c.construct(
                                    net.minecraft.commands.CommandDispatcher.a("slot", ArgumentInventorySlot.a()),
                                    (commandcontext, list, commandloot_a) -> a(
                                          (CommandListenerWrapper)commandcontext.getSource(),
                                          ArgumentPosition.a(commandcontext, "targetPos"),
                                          ArgumentInventorySlot.a(commandcontext, "slot"),
                                          list.size(),
                                          list,
                                          commandloot_a
                                       )
                                 )
                                 .then(
                                    commandloot_c.construct(
                                       net.minecraft.commands.CommandDispatcher.a("count", IntegerArgumentType.integer(0)),
                                       (commandcontext, list, commandloot_a) -> a(
                                             (CommandListenerWrapper)commandcontext.getSource(),
                                             ArgumentPosition.a(commandcontext, "targetPos"),
                                             IntegerArgumentType.getInteger(commandcontext, "slot"),
                                             IntegerArgumentType.getInteger(commandcontext, "count"),
                                             list,
                                             commandloot_a
                                          )
                                    )
                                 )
                           )
                     )
               )
         )
         .then(
            net.minecraft.commands.CommandDispatcher.a("insert")
               .then(
                  commandloot_c.construct(
                     net.minecraft.commands.CommandDispatcher.a("targetPos", ArgumentPosition.a()),
                     (commandcontext, list, commandloot_a) -> a(
                           (CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "targetPos"), list, commandloot_a
                        )
                  )
               )
         )
         .then(
            net.minecraft.commands.CommandDispatcher.a("give")
               .then(
                  commandloot_c.construct(
                     net.minecraft.commands.CommandDispatcher.a("players", ArgumentEntity.d()),
                     (commandcontext, list, commandloot_a) -> a(ArgumentEntity.f(commandcontext, "players"), list, commandloot_a)
                  )
               )
         )
         .then(
            net.minecraft.commands.CommandDispatcher.a("spawn")
               .then(
                  commandloot_c.construct(
                     net.minecraft.commands.CommandDispatcher.a("targetPos", ArgumentVec3.a()),
                     (commandcontext, list, commandloot_a) -> a(
                           (CommandListenerWrapper)commandcontext.getSource(), ArgumentVec3.a(commandcontext, "targetPos"), list, commandloot_a
                        )
                  )
               )
         );
   }

   private static IInventory a(CommandListenerWrapper commandlistenerwrapper, BlockPosition blockposition) throws CommandSyntaxException {
      TileEntity tileentity = commandlistenerwrapper.e().c_(blockposition);
      if (!(tileentity instanceof IInventory)) {
         throw ItemCommands.a.create(blockposition.u(), blockposition.v(), blockposition.w());
      } else {
         return (IInventory)tileentity;
      }
   }

   private static int a(CommandListenerWrapper commandlistenerwrapper, BlockPosition blockposition, List<ItemStack> list, CommandLoot.a commandloot_a) throws CommandSyntaxException {
      IInventory iinventory = a(commandlistenerwrapper, blockposition);
      List<ItemStack> list1 = Lists.newArrayListWithCapacity(list.size());

      for(ItemStack itemstack : list) {
         if (a(iinventory, itemstack.o())) {
            iinventory.e();
            list1.add(itemstack);
         }
      }

      commandloot_a.accept(list1);
      return list1.size();
   }

   private static boolean a(IInventory iinventory, ItemStack itemstack) {
      boolean flag = false;

      for(int i = 0; i < iinventory.b() && !itemstack.b(); ++i) {
         ItemStack itemstack1 = iinventory.a(i);
         if (iinventory.b(i, itemstack)) {
            if (itemstack1.b()) {
               iinventory.a(i, itemstack);
               flag = true;
               break;
            }

            if (a(itemstack1, itemstack)) {
               int j = itemstack.f() - itemstack1.K();
               int k = Math.min(itemstack.K(), j);
               itemstack.h(k);
               itemstack1.g(k);
               flag = true;
            }
         }
      }

      return flag;
   }

   private static int a(
      CommandListenerWrapper commandlistenerwrapper, BlockPosition blockposition, int i, int j, List<ItemStack> list, CommandLoot.a commandloot_a
   ) throws CommandSyntaxException {
      IInventory iinventory = a(commandlistenerwrapper, blockposition);
      int k = iinventory.b();
      if (i >= 0 && i < k) {
         List<ItemStack> list1 = Lists.newArrayListWithCapacity(list.size());

         for(int l = 0; l < j; ++l) {
            int i1 = i + l;
            ItemStack itemstack = l < list.size() ? list.get(l) : ItemStack.b;
            if (iinventory.b(i1, itemstack)) {
               iinventory.a(i1, itemstack);
               list1.add(itemstack);
            }
         }

         commandloot_a.accept(list1);
         return list1.size();
      } else {
         throw ItemCommands.b.create(i);
      }
   }

   private static boolean a(ItemStack itemstack, ItemStack itemstack1) {
      return itemstack.a(itemstack1.c()) && itemstack.j() == itemstack1.j() && itemstack.K() <= itemstack.f() && Objects.equals(itemstack.u(), itemstack1.u());
   }

   private static int a(Collection<EntityPlayer> collection, List<ItemStack> list, CommandLoot.a commandloot_a) throws CommandSyntaxException {
      List<ItemStack> list1 = Lists.newArrayListWithCapacity(list.size());

      for(ItemStack itemstack : list) {
         for(EntityPlayer entityplayer : collection) {
            if (entityplayer.fJ().e(itemstack.o())) {
               list1.add(itemstack);
            }
         }
      }

      commandloot_a.accept(list1);
      return list1.size();
   }

   private static void a(Entity entity, List<ItemStack> list, int i, int j, List<ItemStack> list1) {
      for(int k = 0; k < j; ++k) {
         ItemStack itemstack = k < list.size() ? list.get(k) : ItemStack.b;
         SlotAccess slotaccess = entity.a_(i + k);
         if (slotaccess != SlotAccess.b && slotaccess.a(itemstack.o())) {
            list1.add(itemstack);
         }
      }
   }

   private static int a(Collection<? extends Entity> collection, int i, int j, List<ItemStack> list, CommandLoot.a commandloot_a) throws CommandSyntaxException {
      List<ItemStack> list1 = Lists.newArrayListWithCapacity(list.size());

      for(Entity entity : collection) {
         if (entity instanceof EntityPlayer entityplayer) {
            a(entity, list, i, j, list1);
            entityplayer.bP.d();
         } else {
            a(entity, list, i, j, list1);
         }
      }

      commandloot_a.accept(list1);
      return list1.size();
   }

   private static int a(CommandListenerWrapper commandlistenerwrapper, Vec3D vec3d, List<ItemStack> list, CommandLoot.a commandloot_a) throws CommandSyntaxException {
      WorldServer worldserver = commandlistenerwrapper.e();
      list.removeIf(ItemStack::b);
      list.forEach(itemstack -> {
         EntityItem entityitem = new EntityItem(worldserver, vec3d.c, vec3d.d, vec3d.e, itemstack.o());
         entityitem.k();
         worldserver.b(entityitem);
      });
      commandloot_a.accept(list);
      return list.size();
   }

   private static void a(CommandListenerWrapper commandlistenerwrapper, List<ItemStack> list) {
      if (list.size() == 1) {
         ItemStack itemstack = list.get(0);
         commandlistenerwrapper.a(IChatBaseComponent.a("commands.drop.success.single", itemstack.K(), itemstack.I()), false);
      } else {
         commandlistenerwrapper.a(IChatBaseComponent.a("commands.drop.success.multiple", list.size()), false);
      }
   }

   private static void a(CommandListenerWrapper commandlistenerwrapper, List<ItemStack> list, MinecraftKey minecraftkey) {
      if (list.size() == 1) {
         ItemStack itemstack = list.get(0);
         commandlistenerwrapper.a(IChatBaseComponent.a("commands.drop.success.single_with_table", itemstack.K(), itemstack.I(), minecraftkey), false);
      } else {
         commandlistenerwrapper.a(IChatBaseComponent.a("commands.drop.success.multiple_with_table", list.size(), minecraftkey), false);
      }
   }

   private static ItemStack a(CommandListenerWrapper commandlistenerwrapper, EnumItemSlot enumitemslot) throws CommandSyntaxException {
      Entity entity = commandlistenerwrapper.g();
      if (entity instanceof EntityLiving) {
         return ((EntityLiving)entity).c(enumitemslot);
      } else {
         throw b.create(entity.G_());
      }
   }

   private static int a(CommandContext<CommandListenerWrapper> commandcontext, BlockPosition blockposition, ItemStack itemstack, CommandLoot.b commandloot_b) throws CommandSyntaxException {
      CommandListenerWrapper commandlistenerwrapper = (CommandListenerWrapper)commandcontext.getSource();
      WorldServer worldserver = commandlistenerwrapper.e();
      IBlockData iblockdata = worldserver.a_(blockposition);
      TileEntity tileentity = worldserver.c_(blockposition);
      LootTableInfo.Builder loottableinfo_builder = new LootTableInfo.Builder(worldserver)
         .a(LootContextParameters.f, Vec3D.b(blockposition))
         .a(LootContextParameters.g, iblockdata)
         .b(LootContextParameters.h, tileentity)
         .b(LootContextParameters.a, commandlistenerwrapper.f())
         .a(LootContextParameters.i, itemstack);
      List<ItemStack> list = iblockdata.a(loottableinfo_builder);
      return commandloot_b.accept(commandcontext, list, list1 -> a(commandlistenerwrapper, list1, iblockdata.b().s()));
   }

   private static int a(CommandContext<CommandListenerWrapper> commandcontext, Entity entity, CommandLoot.b commandloot_b) throws CommandSyntaxException {
      if (!(entity instanceof EntityLiving)) {
         throw c.create(entity.G_());
      } else {
         MinecraftKey minecraftkey = ((EntityLiving)entity).et();
         CommandListenerWrapper commandlistenerwrapper = (CommandListenerWrapper)commandcontext.getSource();
         LootTableInfo.Builder loottableinfo_builder = new LootTableInfo.Builder(commandlistenerwrapper.e());
         Entity entity1 = commandlistenerwrapper.f();
         if (entity1 instanceof EntityHuman) {
            loottableinfo_builder.a(LootContextParameters.b, (EntityHuman)entity1);
         }

         loottableinfo_builder.a(LootContextParameters.c, entity.dG().o());
         loottableinfo_builder.b(LootContextParameters.e, entity1);
         loottableinfo_builder.b(LootContextParameters.d, entity1);
         loottableinfo_builder.a(LootContextParameters.a, entity);
         loottableinfo_builder.a(LootContextParameters.f, commandlistenerwrapper.d());
         LootTable loottable = commandlistenerwrapper.l().aH().a(minecraftkey);
         List<ItemStack> list = loottable.a(loottableinfo_builder.a(LootContextParameterSets.f));
         return commandloot_b.accept(commandcontext, list, list1 -> a(commandlistenerwrapper, list1, minecraftkey));
      }
   }

   private static int a(CommandContext<CommandListenerWrapper> commandcontext, MinecraftKey minecraftkey, CommandLoot.b commandloot_b) throws CommandSyntaxException {
      CommandListenerWrapper commandlistenerwrapper = (CommandListenerWrapper)commandcontext.getSource();
      LootTableInfo.Builder loottableinfo_builder = new LootTableInfo.Builder(commandlistenerwrapper.e())
         .b(LootContextParameters.a, commandlistenerwrapper.f())
         .a(LootContextParameters.f, commandlistenerwrapper.d());
      return a(commandcontext, minecraftkey, loottableinfo_builder.a(LootContextParameterSets.b), commandloot_b);
   }

   private static int a(
      CommandContext<CommandListenerWrapper> commandcontext,
      MinecraftKey minecraftkey,
      BlockPosition blockposition,
      ItemStack itemstack,
      CommandLoot.b commandloot_b
   ) throws CommandSyntaxException {
      CommandListenerWrapper commandlistenerwrapper = (CommandListenerWrapper)commandcontext.getSource();
      LootTableInfo loottableinfo = new LootTableInfo.Builder(commandlistenerwrapper.e())
         .a(LootContextParameters.f, Vec3D.b(blockposition))
         .a(LootContextParameters.i, itemstack)
         .b(LootContextParameters.a, commandlistenerwrapper.f())
         .a(LootContextParameterSets.e);
      return a(commandcontext, minecraftkey, loottableinfo, commandloot_b);
   }

   private static int a(
      CommandContext<CommandListenerWrapper> commandcontext, MinecraftKey minecraftkey, LootTableInfo loottableinfo, CommandLoot.b commandloot_b
   ) throws CommandSyntaxException {
      CommandListenerWrapper commandlistenerwrapper = (CommandListenerWrapper)commandcontext.getSource();
      LootTable loottable = commandlistenerwrapper.l().aH().a(minecraftkey);
      List<ItemStack> list = loottable.a(loottableinfo);
      return commandloot_b.accept(commandcontext, list, list1 -> a(commandlistenerwrapper, list1));
   }

   @FunctionalInterface
   private interface a {
      void accept(List<ItemStack> var1) throws CommandSyntaxException;
   }

   @FunctionalInterface
   private interface b {
      int accept(CommandContext<CommandListenerWrapper> var1, List<ItemStack> var2, CommandLoot.a var3) throws CommandSyntaxException;
   }

   @FunctionalInterface
   private interface c {
      ArgumentBuilder<CommandListenerWrapper, ?> construct(ArgumentBuilder<CommandListenerWrapper, ?> var1, CommandLoot.b var2);
   }
}
