package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ArgumentMinecraftKeyRegistered;
import net.minecraft.commands.arguments.coordinates.ArgumentVec3;
import net.minecraft.commands.synchronization.CompletionProviders;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutNamedSoundEffect;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.phys.Vec3D;

public class CommandPlaySound {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.playsound.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      RequiredArgumentBuilder<CommandListenerWrapper, MinecraftKey> var1 = net.minecraft.commands.CommandDispatcher.a(
            "sound", ArgumentMinecraftKeyRegistered.a()
         )
         .suggests(CompletionProviders.c);

      for(SoundCategory var5 : SoundCategory.values()) {
         var1.then(a(var5));
      }

      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("playsound")
               .requires(var0x -> var0x.c(2)))
            .then(var1)
      );
   }

   private static LiteralArgumentBuilder<CommandListenerWrapper> a(SoundCategory var0) {
      return (LiteralArgumentBuilder<CommandListenerWrapper>)net.minecraft.commands.CommandDispatcher.a(var0.a())
         .then(
            ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.d())
                  .executes(
                     var1 -> a(
                           (CommandListenerWrapper)var1.getSource(),
                           ArgumentEntity.f(var1, "targets"),
                           ArgumentMinecraftKeyRegistered.e(var1, "sound"),
                           var0,
                           ((CommandListenerWrapper)var1.getSource()).d(),
                           1.0F,
                           1.0F,
                           0.0F
                        )
                  ))
               .then(
                  ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("pos", ArgumentVec3.a())
                        .executes(
                           var1 -> a(
                                 (CommandListenerWrapper)var1.getSource(),
                                 ArgumentEntity.f(var1, "targets"),
                                 ArgumentMinecraftKeyRegistered.e(var1, "sound"),
                                 var0,
                                 ArgumentVec3.a(var1, "pos"),
                                 1.0F,
                                 1.0F,
                                 0.0F
                              )
                        ))
                     .then(
                        ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("volume", FloatArgumentType.floatArg(0.0F))
                              .executes(
                                 var1 -> a(
                                       (CommandListenerWrapper)var1.getSource(),
                                       ArgumentEntity.f(var1, "targets"),
                                       ArgumentMinecraftKeyRegistered.e(var1, "sound"),
                                       var0,
                                       ArgumentVec3.a(var1, "pos"),
                                       var1.getArgument("volume", Float.class),
                                       1.0F,
                                       0.0F
                                    )
                              ))
                           .then(
                              ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("pitch", FloatArgumentType.floatArg(0.0F, 2.0F))
                                    .executes(
                                       var1 -> a(
                                             (CommandListenerWrapper)var1.getSource(),
                                             ArgumentEntity.f(var1, "targets"),
                                             ArgumentMinecraftKeyRegistered.e(var1, "sound"),
                                             var0,
                                             ArgumentVec3.a(var1, "pos"),
                                             var1.getArgument("volume", Float.class),
                                             var1.getArgument("pitch", Float.class),
                                             0.0F
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("minVolume", FloatArgumentType.floatArg(0.0F, 1.0F))
                                       .executes(
                                          var1 -> a(
                                                (CommandListenerWrapper)var1.getSource(),
                                                ArgumentEntity.f(var1, "targets"),
                                                ArgumentMinecraftKeyRegistered.e(var1, "sound"),
                                                var0,
                                                ArgumentVec3.a(var1, "pos"),
                                                var1.getArgument("volume", Float.class),
                                                var1.getArgument("pitch", Float.class),
                                                var1.getArgument("minVolume", Float.class)
                                             )
                                       )
                                 )
                           )
                     )
               )
         );
   }

   private static int a(
      CommandListenerWrapper var0, Collection<EntityPlayer> var1, MinecraftKey var2, SoundCategory var3, Vec3D var4, float var5, float var6, float var7
   ) throws CommandSyntaxException {
      Holder<SoundEffect> var8 = Holder.a(SoundEffect.a(var2));
      double var9 = (double)MathHelper.k(var8.a().a(var5));
      int var11 = 0;
      long var12 = var0.e().r_().g();

      for(EntityPlayer var15 : var1) {
         double var16 = var4.c - var15.dl();
         double var18 = var4.d - var15.dn();
         double var20 = var4.e - var15.dr();
         double var22 = var16 * var16 + var18 * var18 + var20 * var20;
         Vec3D var24 = var4;
         float var25 = var5;
         if (var22 > var9) {
            if (var7 <= 0.0F) {
               continue;
            }

            double var26 = Math.sqrt(var22);
            var24 = new Vec3D(var15.dl() + var16 / var26 * 2.0, var15.dn() + var18 / var26 * 2.0, var15.dr() + var20 / var26 * 2.0);
            var25 = var7;
         }

         var15.b.a(new PacketPlayOutNamedSoundEffect(var8, var3, var24.a(), var24.b(), var24.c(), var25, var6, var12));
         ++var11;
      }

      if (var11 == 0) {
         throw a.create();
      } else {
         if (var1.size() == 1) {
            var0.a(IChatBaseComponent.a("commands.playsound.success.single", var2, var1.iterator().next().G_()), true);
         } else {
            var0.a(IChatBaseComponent.a("commands.playsound.success.multiple", var2, var1.size()), true);
         }

         return var11;
      }
   }
}
