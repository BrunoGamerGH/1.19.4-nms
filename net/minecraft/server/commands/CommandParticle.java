package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ArgumentParticle;
import net.minecraft.commands.arguments.coordinates.ArgumentVec3;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.phys.Vec3D;

public class CommandParticle {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.particle.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0, CommandBuildContext var1) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("particle")
               .requires(var0x -> var0x.c(2)))
            .then(
               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("name", ArgumentParticle.a(var1))
                     .executes(
                        var0x -> a(
                              (CommandListenerWrapper)var0x.getSource(),
                              ArgumentParticle.a(var0x, "name"),
                              ((CommandListenerWrapper)var0x.getSource()).d(),
                              Vec3D.b,
                              0.0F,
                              0,
                              false,
                              ((CommandListenerWrapper)var0x.getSource()).l().ac().t()
                           )
                     ))
                  .then(
                     ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("pos", ArgumentVec3.a())
                           .executes(
                              var0x -> a(
                                    (CommandListenerWrapper)var0x.getSource(),
                                    ArgumentParticle.a(var0x, "name"),
                                    ArgumentVec3.a(var0x, "pos"),
                                    Vec3D.b,
                                    0.0F,
                                    0,
                                    false,
                                    ((CommandListenerWrapper)var0x.getSource()).l().ac().t()
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("delta", ArgumentVec3.a(false))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("speed", FloatArgumentType.floatArg(0.0F))
                                    .then(
                                       ((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                   "count", IntegerArgumentType.integer(0)
                                                )
                                                .executes(
                                                   var0x -> a(
                                                         (CommandListenerWrapper)var0x.getSource(),
                                                         ArgumentParticle.a(var0x, "name"),
                                                         ArgumentVec3.a(var0x, "pos"),
                                                         ArgumentVec3.a(var0x, "delta"),
                                                         FloatArgumentType.getFloat(var0x, "speed"),
                                                         IntegerArgumentType.getInteger(var0x, "count"),
                                                         false,
                                                         ((CommandListenerWrapper)var0x.getSource()).l().ac().t()
                                                      )
                                                ))
                                             .then(
                                                ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("force")
                                                      .executes(
                                                         var0x -> a(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               ArgumentParticle.a(var0x, "name"),
                                                               ArgumentVec3.a(var0x, "pos"),
                                                               ArgumentVec3.a(var0x, "delta"),
                                                               FloatArgumentType.getFloat(var0x, "speed"),
                                                               IntegerArgumentType.getInteger(var0x, "count"),
                                                               true,
                                                               ((CommandListenerWrapper)var0x.getSource()).l().ac().t()
                                                            )
                                                      ))
                                                   .then(
                                                      net.minecraft.commands.CommandDispatcher.a("viewers", ArgumentEntity.d())
                                                         .executes(
                                                            var0x -> a(
                                                                  (CommandListenerWrapper)var0x.getSource(),
                                                                  ArgumentParticle.a(var0x, "name"),
                                                                  ArgumentVec3.a(var0x, "pos"),
                                                                  ArgumentVec3.a(var0x, "delta"),
                                                                  FloatArgumentType.getFloat(var0x, "speed"),
                                                                  IntegerArgumentType.getInteger(var0x, "count"),
                                                                  true,
                                                                  ArgumentEntity.f(var0x, "viewers")
                                                               )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("normal")
                                                   .executes(
                                                      var0x -> a(
                                                            (CommandListenerWrapper)var0x.getSource(),
                                                            ArgumentParticle.a(var0x, "name"),
                                                            ArgumentVec3.a(var0x, "pos"),
                                                            ArgumentVec3.a(var0x, "delta"),
                                                            FloatArgumentType.getFloat(var0x, "speed"),
                                                            IntegerArgumentType.getInteger(var0x, "count"),
                                                            false,
                                                            ((CommandListenerWrapper)var0x.getSource()).l().ac().t()
                                                         )
                                                   ))
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("viewers", ArgumentEntity.d())
                                                      .executes(
                                                         var0x -> a(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               ArgumentParticle.a(var0x, "name"),
                                                               ArgumentVec3.a(var0x, "pos"),
                                                               ArgumentVec3.a(var0x, "delta"),
                                                               FloatArgumentType.getFloat(var0x, "speed"),
                                                               IntegerArgumentType.getInteger(var0x, "count"),
                                                               false,
                                                               ArgumentEntity.f(var0x, "viewers")
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(
      CommandListenerWrapper var0, ParticleParam var1, Vec3D var2, Vec3D var3, float var4, int var5, boolean var6, Collection<EntityPlayer> var7
   ) throws CommandSyntaxException {
      int var8 = 0;

      for(EntityPlayer var10 : var7) {
         if (var0.e().a(var10, var1, var6, var2.c, var2.d, var2.e, var5, var3.c, var3.d, var3.e, (double)var4)) {
            ++var8;
         }
      }

      if (var8 == 0) {
         throw a.create();
      } else {
         var0.a(IChatBaseComponent.a("commands.particle.success", BuiltInRegistries.k.b(var1.b()).toString()), true);
         return var8;
      }
   }
}
