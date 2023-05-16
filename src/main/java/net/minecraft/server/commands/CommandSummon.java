package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentNBTTag;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.coordinates.ArgumentVec3;
import net.minecraft.commands.synchronization.CompletionProviders;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class CommandSummon {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.summon.failed"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.summon.failed.uuid"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.summon.invalidPosition"));

   public static void a(CommandDispatcher<CommandListenerWrapper> commanddispatcher, CommandBuildContext commandbuildcontext) {
      commanddispatcher.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("summon")
               .requires(commandlistenerwrapper -> commandlistenerwrapper.c(2)))
            .then(
               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("entity", ResourceArgument.a(commandbuildcontext, Registries.r))
                     .suggests(CompletionProviders.d)
                     .executes(
                        commandcontext -> b(
                              (CommandListenerWrapper)commandcontext.getSource(),
                              ResourceArgument.e(commandcontext, "entity"),
                              ((CommandListenerWrapper)commandcontext.getSource()).d(),
                              new NBTTagCompound(),
                              true
                           )
                     ))
                  .then(
                     ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("pos", ArgumentVec3.a())
                           .executes(
                              commandcontext -> b(
                                    (CommandListenerWrapper)commandcontext.getSource(),
                                    ResourceArgument.e(commandcontext, "entity"),
                                    ArgumentVec3.a(commandcontext, "pos"),
                                    new NBTTagCompound(),
                                    true
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("nbt", ArgumentNBTTag.a())
                              .executes(
                                 commandcontext -> b(
                                       (CommandListenerWrapper)commandcontext.getSource(),
                                       ResourceArgument.e(commandcontext, "entity"),
                                       ArgumentVec3.a(commandcontext, "pos"),
                                       ArgumentNBTTag.a(commandcontext, "nbt"),
                                       false
                                    )
                              )
                        )
                  )
            )
      );
   }

   public static Entity a(
      CommandListenerWrapper commandlistenerwrapper, Holder.c<EntityTypes<?>> holder_c, Vec3D vec3d, NBTTagCompound nbttagcompound, boolean flag
   ) throws CommandSyntaxException {
      BlockPosition blockposition = BlockPosition.a(vec3d);
      if (!World.k(blockposition)) {
         throw c.create();
      } else {
         NBTTagCompound nbttagcompound1 = nbttagcompound.h();
         nbttagcompound1.a("id", holder_c.g().a().toString());
         WorldServer worldserver = commandlistenerwrapper.e();
         Entity entity = EntityTypes.a(nbttagcompound1, worldserver, entity1 -> {
            entity1.b(vec3d.c, vec3d.d, vec3d.e, entity1.dw(), entity1.dy());
            return entity1;
         });
         if (entity == null) {
            throw a.create();
         } else {
            if (flag && entity instanceof EntityInsentient) {
               ((EntityInsentient)entity).a(commandlistenerwrapper.e(), commandlistenerwrapper.e().d_(entity.dg()), EnumMobSpawn.n, null, null);
            }

            if (!worldserver.tryAddFreshEntityWithPassengers(entity, SpawnReason.COMMAND)) {
               throw b.create();
            } else {
               return entity;
            }
         }
      }
   }

   private static int b(
      CommandListenerWrapper commandlistenerwrapper, Holder.c<EntityTypes<?>> holder_c, Vec3D vec3d, NBTTagCompound nbttagcompound, boolean flag
   ) throws CommandSyntaxException {
      Entity entity = a(commandlistenerwrapper, holder_c, vec3d, nbttagcompound, flag);
      commandlistenerwrapper.a(IChatBaseComponent.a("commands.summon.success", entity.G_()), true);
      return 1;
   }
}
