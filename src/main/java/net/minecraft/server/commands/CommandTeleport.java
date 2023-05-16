package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentAnchor;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.coordinates.ArgumentRotation;
import net.minecraft.commands.arguments.coordinates.ArgumentVec3;
import net.minecraft.commands.arguments.coordinates.IVectorPosition;
import net.minecraft.commands.arguments.coordinates.VectorPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class CommandTeleport {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.teleport.invalidPosition"));

   public static void a(CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
      LiteralCommandNode<CommandListenerWrapper> literalcommandnode = commanddispatcher.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                        "teleport"
                     )
                     .requires(commandlistenerwrapper -> commandlistenerwrapper.c(2)))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("location", ArgumentVec3.a())
                        .executes(
                           commandcontext -> a(
                                 (CommandListenerWrapper)commandcontext.getSource(),
                                 Collections.singleton(((CommandListenerWrapper)commandcontext.getSource()).g()),
                                 ((CommandListenerWrapper)commandcontext.getSource()).e(),
                                 ArgumentVec3.b(commandcontext, "location"),
                                 VectorPosition.d(),
                                 null
                              )
                        )
                  ))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("destination", ArgumentEntity.a())
                     .executes(
                        commandcontext -> a(
                              (CommandListenerWrapper)commandcontext.getSource(),
                              Collections.singleton(((CommandListenerWrapper)commandcontext.getSource()).g()),
                              ArgumentEntity.a(commandcontext, "destination")
                           )
                     )
               ))
            .then(
               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.b())
                     .then(
                        ((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("location", ArgumentVec3.a())
                                 .executes(
                                    commandcontext -> a(
                                          (CommandListenerWrapper)commandcontext.getSource(),
                                          ArgumentEntity.b(commandcontext, "targets"),
                                          ((CommandListenerWrapper)commandcontext.getSource()).e(),
                                          ArgumentVec3.b(commandcontext, "location"),
                                          null,
                                          null
                                       )
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("rotation", ArgumentRotation.a())
                                    .executes(
                                       commandcontext -> a(
                                             (CommandListenerWrapper)commandcontext.getSource(),
                                             ArgumentEntity.b(commandcontext, "targets"),
                                             ((CommandListenerWrapper)commandcontext.getSource()).e(),
                                             ArgumentVec3.b(commandcontext, "location"),
                                             ArgumentRotation.a(commandcontext, "rotation"),
                                             null
                                          )
                                    )
                              ))
                           .then(
                              ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("facing")
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("entity")
                                          .then(
                                             ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("facingEntity", ArgumentEntity.a())
                                                   .executes(
                                                      commandcontext -> a(
                                                            (CommandListenerWrapper)commandcontext.getSource(),
                                                            ArgumentEntity.b(commandcontext, "targets"),
                                                            ((CommandListenerWrapper)commandcontext.getSource()).e(),
                                                            ArgumentVec3.b(commandcontext, "location"),
                                                            null,
                                                            new CommandTeleport.a(ArgumentEntity.a(commandcontext, "facingEntity"), ArgumentAnchor.Anchor.a)
                                                         )
                                                   ))
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("facingAnchor", ArgumentAnchor.a())
                                                      .executes(
                                                         commandcontext -> a(
                                                               (CommandListenerWrapper)commandcontext.getSource(),
                                                               ArgumentEntity.b(commandcontext, "targets"),
                                                               ((CommandListenerWrapper)commandcontext.getSource()).e(),
                                                               ArgumentVec3.b(commandcontext, "location"),
                                                               null,
                                                               new CommandTeleport.a(
                                                                  ArgumentEntity.a(commandcontext, "facingEntity"),
                                                                  ArgumentAnchor.a(commandcontext, "facingAnchor")
                                                               )
                                                            )
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("facingLocation", ArgumentVec3.a())
                                       .executes(
                                          commandcontext -> a(
                                                (CommandListenerWrapper)commandcontext.getSource(),
                                                ArgumentEntity.b(commandcontext, "targets"),
                                                ((CommandListenerWrapper)commandcontext.getSource()).e(),
                                                ArgumentVec3.b(commandcontext, "location"),
                                                null,
                                                new CommandTeleport.a(ArgumentVec3.a(commandcontext, "facingLocation"))
                                             )
                                       )
                                 )
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("destination", ArgumentEntity.a())
                        .executes(
                           commandcontext -> a(
                                 (CommandListenerWrapper)commandcontext.getSource(),
                                 ArgumentEntity.b(commandcontext, "targets"),
                                 ArgumentEntity.a(commandcontext, "destination")
                              )
                        )
                  )
            )
      );
      commanddispatcher.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("tp")
               .requires(commandlistenerwrapper -> commandlistenerwrapper.c(2)))
            .redirect(literalcommandnode)
      );
   }

   private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<? extends Entity> collection, Entity entity) throws CommandSyntaxException {
      for(Entity entity1 : collection) {
         a(
            commandlistenerwrapper,
            entity1,
            (WorldServer)entity.H,
            entity.dl(),
            entity.dn(),
            entity.dr(),
            EnumSet.noneOf(RelativeMovement.class),
            entity.dw(),
            entity.dy(),
            null
         );
      }

      if (collection.size() == 1) {
         commandlistenerwrapper.a(IChatBaseComponent.a("commands.teleport.success.entity.single", collection.iterator().next().G_(), entity.G_()), true);
      } else {
         commandlistenerwrapper.a(IChatBaseComponent.a("commands.teleport.success.entity.multiple", collection.size(), entity.G_()), true);
      }

      return collection.size();
   }

   private static int a(
      CommandListenerWrapper commandlistenerwrapper,
      Collection<? extends Entity> collection,
      WorldServer worldserver,
      IVectorPosition ivectorposition,
      @Nullable IVectorPosition ivectorposition1,
      @Nullable CommandTeleport.a commandteleport_a
   ) throws CommandSyntaxException {
      Vec3D vec3d = ivectorposition.a(commandlistenerwrapper);
      Vec2F vec2f = ivectorposition1 == null ? null : ivectorposition1.b(commandlistenerwrapper);
      Set<RelativeMovement> set = EnumSet.noneOf(RelativeMovement.class);
      if (ivectorposition.a()) {
         set.add(RelativeMovement.a);
      }

      if (ivectorposition.b()) {
         set.add(RelativeMovement.b);
      }

      if (ivectorposition.c()) {
         set.add(RelativeMovement.c);
      }

      if (ivectorposition1 == null) {
         set.add(RelativeMovement.e);
         set.add(RelativeMovement.d);
      } else {
         if (ivectorposition1.a()) {
            set.add(RelativeMovement.e);
         }

         if (ivectorposition1.b()) {
            set.add(RelativeMovement.d);
         }
      }

      for(Entity entity : collection) {
         if (ivectorposition1 == null) {
            a(commandlistenerwrapper, entity, worldserver, vec3d.c, vec3d.d, vec3d.e, set, entity.dw(), entity.dy(), commandteleport_a);
         } else {
            a(commandlistenerwrapper, entity, worldserver, vec3d.c, vec3d.d, vec3d.e, set, vec2f.j, vec2f.i, commandteleport_a);
         }
      }

      if (collection.size() == 1) {
         commandlistenerwrapper.a(
            IChatBaseComponent.a("commands.teleport.success.location.single", collection.iterator().next().G_(), a(vec3d.c), a(vec3d.d), a(vec3d.e)), true
         );
      } else {
         commandlistenerwrapper.a(
            IChatBaseComponent.a("commands.teleport.success.location.multiple", collection.size(), a(vec3d.c), a(vec3d.d), a(vec3d.e)), true
         );
      }

      return collection.size();
   }

   private static String a(double d0) {
      return String.format(Locale.ROOT, "%f", d0);
   }

   private static void a(
      CommandListenerWrapper commandlistenerwrapper,
      Entity entity,
      WorldServer worldserver,
      double d0,
      double d1,
      double d2,
      Set<RelativeMovement> set,
      float f,
      float f1,
      @Nullable CommandTeleport.a commandteleport_a
   ) throws CommandSyntaxException {
      BlockPosition blockposition = BlockPosition.a(d0, d1, d2);
      if (!World.k(blockposition)) {
         throw a.create();
      } else {
         float f2 = MathHelper.g(f);
         float f3 = MathHelper.g(f1);
         boolean result;
         EntityPlayer player;
         if (entity instanceof EntityPlayer && (player = (EntityPlayer)entity) == (EntityPlayer)entity) {
            result = player.teleportTo(worldserver, d0, d1, d2, set, f2, f3, TeleportCause.COMMAND);
         } else {
            Location to = new Location(worldserver.getWorld(), d0, d1, d2, f2, f3);
            EntityTeleportEvent event = new EntityTeleportEvent(entity.getBukkitEntity(), entity.getBukkitEntity().getLocation(), to);
            worldserver.getCraftServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
               return;
            }

            d0 = to.getX();
            d1 = to.getY();
            d2 = to.getZ();
            f2 = to.getYaw();
            f3 = to.getPitch();
            worldserver = ((CraftWorld)to.getWorld()).getHandle();
            result = entity.a(worldserver, d0, d1, d2, set, f2, f3);
         }

         if (result) {
            if (commandteleport_a != null) {
               commandteleport_a.a(commandlistenerwrapper, entity);
            }

            if (!(entity instanceof EntityLiving entityliving) || !entityliving.fn()) {
               entity.f(entity.dj().d(1.0, 0.0, 1.0));
               entity.c(true);
            }

            if (entity instanceof EntityCreature entitycreature) {
               entitycreature.G().n();
            }
         }
      }
   }

   private static class a {
      private final Vec3D a;
      private final Entity b;
      private final ArgumentAnchor.Anchor c;

      public a(Entity entity, ArgumentAnchor.Anchor argumentanchor_anchor) {
         this.b = entity;
         this.c = argumentanchor_anchor;
         this.a = argumentanchor_anchor.a(entity);
      }

      public a(Vec3D vec3d) {
         this.b = null;
         this.a = vec3d;
         this.c = null;
      }

      public void a(CommandListenerWrapper commandlistenerwrapper, Entity entity) {
         if (this.b != null) {
            if (entity instanceof EntityPlayer) {
               ((EntityPlayer)entity).a(commandlistenerwrapper.m(), this.b, this.c);
            } else {
               entity.a(commandlistenerwrapper.m(), this.a);
            }
         } else {
            entity.a(commandlistenerwrapper.m(), this.a);
         }
      }
   }
}
