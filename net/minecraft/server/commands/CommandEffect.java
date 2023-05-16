package net.minecraft.server.commands;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class CommandEffect {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.effect.give.failed"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.effect.clear.everything.failed"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.effect.clear.specific.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> commanddispatcher, CommandBuildContext commandbuildcontext) {
      commanddispatcher.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("effect")
                  .requires(commandlistenerwrapper -> commandlistenerwrapper.c(2)))
               .then(
                  ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("clear")
                        .executes(
                           commandcontext -> a(
                                 (CommandListenerWrapper)commandcontext.getSource(),
                                 ImmutableList.of(((CommandListenerWrapper)commandcontext.getSource()).g())
                              )
                        ))
                     .then(
                        ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.b())
                              .executes(commandcontext -> a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.b(commandcontext, "targets"))))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("effect", ResourceArgument.a(commandbuildcontext, Registries.N))
                                 .executes(
                                    commandcontext -> a(
                                          (CommandListenerWrapper)commandcontext.getSource(),
                                          ArgumentEntity.b(commandcontext, "targets"),
                                          ResourceArgument.f(commandcontext, "effect")
                                       )
                                 )
                           )
                     )
               ))
            .then(
               net.minecraft.commands.CommandDispatcher.a("give")
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.b())
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                       "effect", ResourceArgument.a(commandbuildcontext, Registries.N)
                                    )
                                    .executes(
                                       commandcontext -> a(
                                             (CommandListenerWrapper)commandcontext.getSource(),
                                             ArgumentEntity.b(commandcontext, "targets"),
                                             ResourceArgument.f(commandcontext, "effect"),
                                             null,
                                             0,
                                             true
                                          )
                                    ))
                                 .then(
                                    ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("seconds", IntegerArgumentType.integer(1, 1000000))
                                          .executes(
                                             commandcontext -> a(
                                                   (CommandListenerWrapper)commandcontext.getSource(),
                                                   ArgumentEntity.b(commandcontext, "targets"),
                                                   ResourceArgument.f(commandcontext, "effect"),
                                                   IntegerArgumentType.getInteger(commandcontext, "seconds"),
                                                   0,
                                                   true
                                                )
                                          ))
                                       .then(
                                          ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                   "amplifier", IntegerArgumentType.integer(0, 255)
                                                )
                                                .executes(
                                                   commandcontext -> a(
                                                         (CommandListenerWrapper)commandcontext.getSource(),
                                                         ArgumentEntity.b(commandcontext, "targets"),
                                                         ResourceArgument.f(commandcontext, "effect"),
                                                         IntegerArgumentType.getInteger(commandcontext, "seconds"),
                                                         IntegerArgumentType.getInteger(commandcontext, "amplifier"),
                                                         true
                                                      )
                                                ))
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("hideParticles", BoolArgumentType.bool())
                                                   .executes(
                                                      commandcontext -> a(
                                                            (CommandListenerWrapper)commandcontext.getSource(),
                                                            ArgumentEntity.b(commandcontext, "targets"),
                                                            ResourceArgument.f(commandcontext, "effect"),
                                                            IntegerArgumentType.getInteger(commandcontext, "seconds"),
                                                            IntegerArgumentType.getInteger(commandcontext, "amplifier"),
                                                            !BoolArgumentType.getBool(commandcontext, "hideParticles")
                                                         )
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("infinite")
                                       .executes(
                                          commandcontext -> a(
                                                (CommandListenerWrapper)commandcontext.getSource(),
                                                ArgumentEntity.b(commandcontext, "targets"),
                                                ResourceArgument.f(commandcontext, "effect"),
                                                -1,
                                                0,
                                                true
                                             )
                                       ))
                                    .then(
                                       ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("amplifier", IntegerArgumentType.integer(0, 255))
                                             .executes(
                                                commandcontext -> a(
                                                      (CommandListenerWrapper)commandcontext.getSource(),
                                                      ArgumentEntity.b(commandcontext, "targets"),
                                                      ResourceArgument.f(commandcontext, "effect"),
                                                      -1,
                                                      IntegerArgumentType.getInteger(commandcontext, "amplifier"),
                                                      true
                                                   )
                                             ))
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("hideParticles", BoolArgumentType.bool())
                                                .executes(
                                                   commandcontext -> a(
                                                         (CommandListenerWrapper)commandcontext.getSource(),
                                                         ArgumentEntity.b(commandcontext, "targets"),
                                                         ResourceArgument.f(commandcontext, "effect"),
                                                         -1,
                                                         IntegerArgumentType.getInteger(commandcontext, "amplifier"),
                                                         !BoolArgumentType.getBool(commandcontext, "hideParticles")
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
      CommandListenerWrapper commandlistenerwrapper,
      Collection<? extends Entity> collection,
      Holder<MobEffectList> holder,
      @Nullable Integer integer,
      int i,
      boolean flag
   ) throws CommandSyntaxException {
      MobEffectList mobeffectlist = holder.a();
      int j = 0;
      int k;
      if (integer != null) {
         if (mobeffectlist.a()) {
            k = integer;
         } else if (integer == -1) {
            k = -1;
         } else {
            k = integer * 20;
         }
      } else if (mobeffectlist.a()) {
         k = 1;
      } else {
         k = 600;
      }

      for(Entity entity : collection) {
         if (entity instanceof EntityLiving) {
            MobEffect mobeffect = new MobEffect(mobeffectlist, k, i, false, flag);
            if (((EntityLiving)entity).addEffect(mobeffect, commandlistenerwrapper.f(), Cause.COMMAND)) {
               ++j;
            }
         }
      }

      if (j == 0) {
         throw a.create();
      } else {
         if (collection.size() == 1) {
            commandlistenerwrapper.a(
               IChatBaseComponent.a("commands.effect.give.success.single", mobeffectlist.e(), collection.iterator().next().G_(), k / 20), true
            );
         } else {
            commandlistenerwrapper.a(IChatBaseComponent.a("commands.effect.give.success.multiple", mobeffectlist.e(), collection.size(), k / 20), true);
         }

         return j;
      }
   }

   private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<? extends Entity> collection) throws CommandSyntaxException {
      int i = 0;

      for(Entity entity : collection) {
         if (entity instanceof EntityLiving && ((EntityLiving)entity).removeAllEffects(Cause.COMMAND)) {
            ++i;
         }
      }

      if (i == 0) {
         throw b.create();
      } else {
         if (collection.size() == 1) {
            commandlistenerwrapper.a(IChatBaseComponent.a("commands.effect.clear.everything.success.single", collection.iterator().next().G_()), true);
         } else {
            commandlistenerwrapper.a(IChatBaseComponent.a("commands.effect.clear.everything.success.multiple", collection.size()), true);
         }

         return i;
      }
   }

   private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<? extends Entity> collection, Holder<MobEffectList> holder) throws CommandSyntaxException {
      MobEffectList mobeffectlist = holder.a();
      int i = 0;

      for(Entity entity : collection) {
         if (entity instanceof EntityLiving && ((EntityLiving)entity).removeEffect(mobeffectlist, Cause.COMMAND)) {
            ++i;
         }
      }

      if (i == 0) {
         throw c.create();
      } else {
         if (collection.size() == 1) {
            commandlistenerwrapper.a(
               IChatBaseComponent.a("commands.effect.clear.specific.success.single", mobeffectlist.e(), collection.iterator().next().G_()), true
            );
         } else {
            commandlistenerwrapper.a(IChatBaseComponent.a("commands.effect.clear.specific.success.multiple", mobeffectlist.e(), collection.size()), true);
         }

         return i;
      }
   }
}
