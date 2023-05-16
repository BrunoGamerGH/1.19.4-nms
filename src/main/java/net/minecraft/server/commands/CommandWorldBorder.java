package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.coordinates.ArgumentVec2;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.Vec2F;

public class CommandWorldBorder {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.worldborder.center.failed"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.worldborder.set.failed.nochange"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.worldborder.set.failed.small"));
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(IChatBaseComponent.a("commands.worldborder.set.failed.big", 5.999997E7F));
   private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(IChatBaseComponent.a("commands.worldborder.set.failed.far", 2.9999984E7));
   private static final SimpleCommandExceptionType f = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.worldborder.warning.time.failed"));
   private static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.worldborder.warning.distance.failed"));
   private static final SimpleCommandExceptionType h = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.worldborder.damage.buffer.failed"));
   private static final SimpleCommandExceptionType i = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.worldborder.damage.amount.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
      commanddispatcher.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                 "worldborder"
                              )
                              .requires(commandlistenerwrapper -> commandlistenerwrapper.c(2)))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("add")
                                 .then(
                                    ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                             "distance", DoubleArgumentType.doubleArg(-5.999997E7F, 5.999997E7F)
                                          )
                                          .executes(
                                             commandcontext -> a(
                                                   (CommandListenerWrapper)commandcontext.getSource(),
                                                   ((CommandListenerWrapper)commandcontext.getSource()).e().p_().i()
                                                      + DoubleArgumentType.getDouble(commandcontext, "distance"),
                                                   0L
                                                )
                                          ))
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("time", IntegerArgumentType.integer(0))
                                             .executes(
                                                commandcontext -> a(
                                                      (CommandListenerWrapper)commandcontext.getSource(),
                                                      ((CommandListenerWrapper)commandcontext.getSource()).e().p_().i()
                                                         + DoubleArgumentType.getDouble(commandcontext, "distance"),
                                                      ((CommandListenerWrapper)commandcontext.getSource()).e().p_().j()
                                                         + (long)IntegerArgumentType.getInteger(commandcontext, "time") * 1000L
                                                   )
                                             )
                                       )
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("set")
                              .then(
                                 ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                          "distance", DoubleArgumentType.doubleArg(-5.999997E7F, 5.999997E7F)
                                       )
                                       .executes(
                                          commandcontext -> a(
                                                (CommandListenerWrapper)commandcontext.getSource(),
                                                DoubleArgumentType.getDouble(commandcontext, "distance"),
                                                0L
                                             )
                                       ))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("time", IntegerArgumentType.integer(0))
                                          .executes(
                                             commandcontext -> a(
                                                   (CommandListenerWrapper)commandcontext.getSource(),
                                                   DoubleArgumentType.getDouble(commandcontext, "distance"),
                                                   (long)IntegerArgumentType.getInteger(commandcontext, "time") * 1000L
                                                )
                                          )
                                    )
                              )
                        ))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("center")
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("pos", ArgumentVec2.a())
                                 .executes(commandcontext -> a((CommandListenerWrapper)commandcontext.getSource(), ArgumentVec2.a(commandcontext, "pos")))
                           )
                     ))
                  .then(
                     ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("damage")
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("amount")
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("damagePerBlock", FloatArgumentType.floatArg(0.0F))
                                       .executes(
                                          commandcontext -> b(
                                                (CommandListenerWrapper)commandcontext.getSource(),
                                                FloatArgumentType.getFloat(commandcontext, "damagePerBlock")
                                             )
                                       )
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("buffer")
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("distance", FloatArgumentType.floatArg(0.0F))
                                    .executes(
                                       commandcontext -> a(
                                             (CommandListenerWrapper)commandcontext.getSource(), FloatArgumentType.getFloat(commandcontext, "distance")
                                          )
                                    )
                              )
                        )
                  ))
               .then(net.minecraft.commands.CommandDispatcher.a("get").executes(commandcontext -> a((CommandListenerWrapper)commandcontext.getSource()))))
            .then(
               ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("warning")
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("distance")
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("distance", IntegerArgumentType.integer(0))
                                 .executes(
                                    commandcontext -> b(
                                          (CommandListenerWrapper)commandcontext.getSource(), IntegerArgumentType.getInteger(commandcontext, "distance")
                                       )
                                 )
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("time")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("time", IntegerArgumentType.integer(0))
                              .executes(
                                 commandcontext -> a(
                                       (CommandListenerWrapper)commandcontext.getSource(), IntegerArgumentType.getInteger(commandcontext, "time")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper commandlistenerwrapper, float f) throws CommandSyntaxException {
      WorldBorder worldborder = commandlistenerwrapper.e().p_();
      if (worldborder.n() == (double)f) {
         throw h.create();
      } else {
         worldborder.b((double)f);
         commandlistenerwrapper.a(IChatBaseComponent.a("commands.worldborder.damage.buffer.success", String.format(Locale.ROOT, "%.2f", f)), true);
         return (int)f;
      }
   }

   private static int b(CommandListenerWrapper commandlistenerwrapper, float f) throws CommandSyntaxException {
      WorldBorder worldborder = commandlistenerwrapper.e().p_();
      if (worldborder.o() == (double)f) {
         throw i.create();
      } else {
         worldborder.c((double)f);
         commandlistenerwrapper.a(IChatBaseComponent.a("commands.worldborder.damage.amount.success", String.format(Locale.ROOT, "%.2f", f)), true);
         return (int)f;
      }
   }

   private static int a(CommandListenerWrapper commandlistenerwrapper, int i) throws CommandSyntaxException {
      WorldBorder worldborder = commandlistenerwrapper.e().p_();
      if (worldborder.q() == i) {
         throw f.create();
      } else {
         worldborder.b(i);
         commandlistenerwrapper.a(IChatBaseComponent.a("commands.worldborder.warning.time.success", i), true);
         return i;
      }
   }

   private static int b(CommandListenerWrapper commandlistenerwrapper, int i) throws CommandSyntaxException {
      WorldBorder worldborder = commandlistenerwrapper.e().p_();
      if (worldborder.r() == i) {
         throw g.create();
      } else {
         worldborder.c(i);
         commandlistenerwrapper.a(IChatBaseComponent.a("commands.worldborder.warning.distance.success", i), true);
         return i;
      }
   }

   private static int a(CommandListenerWrapper commandlistenerwrapper) {
      double d0 = commandlistenerwrapper.e().p_().i();
      commandlistenerwrapper.a(IChatBaseComponent.a("commands.worldborder.get", String.format(Locale.ROOT, "%.0f", d0)), false);
      return MathHelper.a(d0 + 0.5);
   }

   private static int a(CommandListenerWrapper commandlistenerwrapper, Vec2F vec2f) throws CommandSyntaxException {
      WorldBorder worldborder = commandlistenerwrapper.e().p_();
      if (worldborder.a() == (double)vec2f.i && worldborder.b() == (double)vec2f.j) {
         throw a.create();
      } else if ((double)Math.abs(vec2f.i) <= 2.9999984E7 && (double)Math.abs(vec2f.j) <= 2.9999984E7) {
         worldborder.c((double)vec2f.i, (double)vec2f.j);
         commandlistenerwrapper.a(
            IChatBaseComponent.a(
               "commands.worldborder.center.success", String.format(Locale.ROOT, "%.2f", vec2f.i), String.format(Locale.ROOT, "%.2f", vec2f.j)
            ),
            true
         );
         return 0;
      } else {
         throw e.create();
      }
   }

   private static int a(CommandListenerWrapper commandlistenerwrapper, double d0, long i) throws CommandSyntaxException {
      WorldBorder worldborder = commandlistenerwrapper.e().p_();
      double d1 = worldborder.i();
      if (d1 == d0) {
         throw b.create();
      } else if (d0 < 1.0) {
         throw c.create();
      } else if (d0 > 5.999997E7F) {
         throw d.create();
      } else {
         if (i > 0L) {
            worldborder.a(d1, d0, i);
            if (d0 > d1) {
               commandlistenerwrapper.a(
                  IChatBaseComponent.a("commands.worldborder.set.grow", String.format(Locale.ROOT, "%.1f", d0), Long.toString(i / 1000L)), true
               );
            } else {
               commandlistenerwrapper.a(
                  IChatBaseComponent.a("commands.worldborder.set.shrink", String.format(Locale.ROOT, "%.1f", d0), Long.toString(i / 1000L)), true
               );
            }
         } else {
            worldborder.a(d0);
            commandlistenerwrapper.a(IChatBaseComponent.a("commands.worldborder.set.immediate", String.format(Locale.ROOT, "%.1f", d0)), true);
         }

         return (int)(d0 - d1);
      }
   }
}
