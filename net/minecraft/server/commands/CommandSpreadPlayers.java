package net.minecraft.server.commands;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.coordinates.ArgumentVec2;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.scores.ScoreboardTeamBase;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class CommandSpreadPlayers {
   private static final int a = 10000;
   private static final Dynamic4CommandExceptionType b = new Dynamic4CommandExceptionType(
      (object, object1, object2, object3) -> IChatBaseComponent.a("commands.spreadplayers.failed.teams", object, object1, object2, object3)
   );
   private static final Dynamic4CommandExceptionType c = new Dynamic4CommandExceptionType(
      (object, object1, object2, object3) -> IChatBaseComponent.a("commands.spreadplayers.failed.entities", object, object1, object2, object3)
   );
   private static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType(
      (object, object1) -> IChatBaseComponent.a("commands.spreadplayers.failed.invalid.height", object, object1)
   );

   public static void a(CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
      commanddispatcher.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("spreadplayers")
               .requires(commandlistenerwrapper -> commandlistenerwrapper.c(2)))
            .then(
               net.minecraft.commands.CommandDispatcher.a("center", ArgumentVec2.a())
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("spreadDistance", FloatArgumentType.floatArg(0.0F))
                        .then(
                           ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("maxRange", FloatArgumentType.floatArg(1.0F))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("respectTeams", BoolArgumentType.bool())
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.b())
                                             .executes(
                                                commandcontext -> a(
                                                      (CommandListenerWrapper)commandcontext.getSource(),
                                                      ArgumentVec2.a(commandcontext, "center"),
                                                      FloatArgumentType.getFloat(commandcontext, "spreadDistance"),
                                                      FloatArgumentType.getFloat(commandcontext, "maxRange"),
                                                      ((CommandListenerWrapper)commandcontext.getSource()).e().ai(),
                                                      BoolArgumentType.getBool(commandcontext, "respectTeams"),
                                                      ArgumentEntity.b(commandcontext, "targets")
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("under")
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("maxHeight", IntegerArgumentType.integer())
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("respectTeams", BoolArgumentType.bool())
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.b())
                                                      .executes(
                                                         commandcontext -> a(
                                                               (CommandListenerWrapper)commandcontext.getSource(),
                                                               ArgumentVec2.a(commandcontext, "center"),
                                                               FloatArgumentType.getFloat(commandcontext, "spreadDistance"),
                                                               FloatArgumentType.getFloat(commandcontext, "maxRange"),
                                                               IntegerArgumentType.getInteger(commandcontext, "maxHeight"),
                                                               BoolArgumentType.getBool(commandcontext, "respectTeams"),
                                                               ArgumentEntity.b(commandcontext, "targets")
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
      CommandListenerWrapper commandlistenerwrapper, Vec2F vec2f, float f, float f1, int i, boolean flag, Collection<? extends Entity> collection
   ) throws CommandSyntaxException {
      WorldServer worldserver = commandlistenerwrapper.e();
      int j = worldserver.v_();
      if (i < j) {
         throw d.create(i, j);
      } else {
         RandomSource randomsource = RandomSource.a();
         double d0 = (double)(vec2f.i - f1);
         double d1 = (double)(vec2f.j - f1);
         double d2 = (double)(vec2f.i + f1);
         double d3 = (double)(vec2f.j + f1);
         CommandSpreadPlayers.a[] acommandspreadplayers_a = a(randomsource, flag ? a(collection) : collection.size(), d0, d1, d2, d3);
         a(vec2f, (double)f, worldserver, randomsource, d0, d1, d2, d3, i, acommandspreadplayers_a, flag);
         double d4 = a(collection, worldserver, acommandspreadplayers_a, i, flag);
         commandlistenerwrapper.a(
            IChatBaseComponent.a(
               "commands.spreadplayers.success." + (flag ? "teams" : "entities"),
               acommandspreadplayers_a.length,
               vec2f.i,
               vec2f.j,
               String.format(Locale.ROOT, "%.2f", d4)
            ),
            true
         );
         return acommandspreadplayers_a.length;
      }
   }

   private static int a(Collection<? extends Entity> collection) {
      Set<ScoreboardTeamBase> set = Sets.newHashSet();

      for(Entity entity : collection) {
         if (entity instanceof EntityHuman) {
            set.add(entity.cb());
         } else {
            set.add(null);
         }
      }

      return set.size();
   }

   private static void a(
      Vec2F vec2f,
      double d0,
      WorldServer worldserver,
      RandomSource randomsource,
      double d1,
      double d2,
      double d3,
      double d4,
      int i,
      CommandSpreadPlayers.a[] acommandspreadplayers_a,
      boolean flag
   ) throws CommandSyntaxException {
      boolean flag1 = true;
      double d5 = Float.MAX_VALUE;

      int j;
      for(j = 0; j < 10000 && flag1; ++j) {
         flag1 = false;
         d5 = Float.MAX_VALUE;

         for(int l = 0; l < acommandspreadplayers_a.length; ++l) {
            CommandSpreadPlayers.a commandspreadplayers_a1 = acommandspreadplayers_a[l];
            int k = 0;
            CommandSpreadPlayers.a commandspreadplayers_a = new CommandSpreadPlayers.a();

            for(int i1 = 0; i1 < acommandspreadplayers_a.length; ++i1) {
               if (l != i1) {
                  CommandSpreadPlayers.a commandspreadplayers_a2 = acommandspreadplayers_a[i1];
                  double d6 = commandspreadplayers_a1.a(commandspreadplayers_a2);
                  d5 = Math.min(d6, d5);
                  if (d6 < d0) {
                     ++k;
                     commandspreadplayers_a.a += commandspreadplayers_a2.a - commandspreadplayers_a1.a;
                     commandspreadplayers_a.b += commandspreadplayers_a2.b - commandspreadplayers_a1.b;
                  }
               }
            }

            if (k > 0) {
               commandspreadplayers_a.a /= (double)k;
               commandspreadplayers_a.b /= (double)k;
               double d7 = commandspreadplayers_a.b();
               if (d7 > 0.0) {
                  commandspreadplayers_a.a();
                  commandspreadplayers_a1.b(commandspreadplayers_a);
               } else {
                  commandspreadplayers_a1.a(randomsource, d1, d2, d3, d4);
               }

               flag1 = true;
            }

            if (commandspreadplayers_a1.a(d1, d2, d3, d4)) {
               flag1 = true;
            }
         }

         if (!flag1) {
            for(CommandSpreadPlayers.a commandspreadplayers_a : acommandspreadplayers_a) {
               if (!commandspreadplayers_a.b(worldserver, i)) {
                  commandspreadplayers_a.a(randomsource, d1, d2, d3, d4);
                  flag1 = true;
               }
            }
         }
      }

      if (d5 == Float.MAX_VALUE) {
         d5 = 0.0;
      }

      if (j >= 10000) {
         if (flag) {
            throw b.create(acommandspreadplayers_a.length, vec2f.i, vec2f.j, String.format(Locale.ROOT, "%.2f", d5));
         } else {
            throw c.create(acommandspreadplayers_a.length, vec2f.i, vec2f.j, String.format(Locale.ROOT, "%.2f", d5));
         }
      }
   }

   private static double a(
      Collection<? extends Entity> collection, WorldServer worldserver, CommandSpreadPlayers.a[] acommandspreadplayers_a, int i, boolean flag
   ) {
      double d0 = 0.0;
      int j = 0;
      Map<ScoreboardTeamBase, CommandSpreadPlayers.a> map = Maps.newHashMap();

      for(Entity entity : collection) {
         CommandSpreadPlayers.a commandspreadplayers_a;
         if (flag) {
            ScoreboardTeamBase scoreboardteambase = entity instanceof EntityHuman ? entity.cb() : null;
            if (!map.containsKey(scoreboardteambase)) {
               map.put(scoreboardteambase, acommandspreadplayers_a[j++]);
            }

            commandspreadplayers_a = map.get(scoreboardteambase);
         } else {
            commandspreadplayers_a = acommandspreadplayers_a[j++];
         }

         entity.teleportTo(
            worldserver,
            (double)MathHelper.a(commandspreadplayers_a.a) + 0.5,
            (double)commandspreadplayers_a.a(worldserver, i),
            (double)MathHelper.a(commandspreadplayers_a.b) + 0.5,
            Set.of(),
            entity.dw(),
            entity.dy(),
            TeleportCause.COMMAND
         );
         double d1 = Double.MAX_VALUE;

         for(CommandSpreadPlayers.a commandspreadplayers_a1 : acommandspreadplayers_a) {
            if (commandspreadplayers_a != commandspreadplayers_a1) {
               double d2 = commandspreadplayers_a.a(commandspreadplayers_a1);
               d1 = Math.min(d2, d1);
            }
         }

         d0 += d1;
      }

      return collection.size() < 2 ? 0.0 : d0 / (double)collection.size();
   }

   private static CommandSpreadPlayers.a[] a(RandomSource randomsource, int i, double d0, double d1, double d2, double d3) {
      CommandSpreadPlayers.a[] acommandspreadplayers_a = new CommandSpreadPlayers.a[i];

      for(int j = 0; j < acommandspreadplayers_a.length; ++j) {
         CommandSpreadPlayers.a commandspreadplayers_a = new CommandSpreadPlayers.a();
         commandspreadplayers_a.a(randomsource, d0, d1, d2, d3);
         acommandspreadplayers_a[j] = commandspreadplayers_a;
      }

      return acommandspreadplayers_a;
   }

   private static class a {
      double a;
      double b;

      a() {
      }

      double a(CommandSpreadPlayers.a commandspreadplayers_a) {
         double d0 = this.a - commandspreadplayers_a.a;
         double d1 = this.b - commandspreadplayers_a.b;
         return Math.sqrt(d0 * d0 + d1 * d1);
      }

      void a() {
         double d0 = this.b();
         this.a /= d0;
         this.b /= d0;
      }

      double b() {
         return Math.sqrt(this.a * this.a + this.b * this.b);
      }

      public void b(CommandSpreadPlayers.a commandspreadplayers_a) {
         this.a -= commandspreadplayers_a.a;
         this.b -= commandspreadplayers_a.b;
      }

      public boolean a(double d0, double d1, double d2, double d3) {
         boolean flag = false;
         if (this.a < d0) {
            this.a = d0;
            flag = true;
         } else if (this.a > d2) {
            this.a = d2;
            flag = true;
         }

         if (this.b < d1) {
            this.b = d1;
            flag = true;
         } else if (this.b > d3) {
            this.b = d3;
            flag = true;
         }

         return flag;
      }

      public int a(IBlockAccess iblockaccess, int i) {
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition(this.a, (double)(i + 1), this.b);
         boolean flag = iblockaccess.a_(blockposition_mutableblockposition).h();
         blockposition_mutableblockposition.c(EnumDirection.a);

         boolean flag1;
         for(boolean flag2 = iblockaccess.a_(blockposition_mutableblockposition).h();
            blockposition_mutableblockposition.v() > iblockaccess.v_();
            flag2 = flag1
         ) {
            blockposition_mutableblockposition.c(EnumDirection.a);
            flag1 = getBlockState(iblockaccess, blockposition_mutableblockposition).h();
            if (!flag1 && flag2 && flag) {
               return blockposition_mutableblockposition.v() + 1;
            }

            flag = flag2;
         }

         return i + 1;
      }

      public boolean b(IBlockAccess iblockaccess, int i) {
         BlockPosition blockposition = BlockPosition.a(this.a, (double)(this.a(iblockaccess, i) - 1), this.b);
         IBlockData iblockdata = getBlockState(iblockaccess, blockposition);
         Material material = iblockdata.d();
         return blockposition.v() < i && !material.a() && material != Material.n;
      }

      public void a(RandomSource randomsource, double d0, double d1, double d2, double d3) {
         this.a = MathHelper.a(randomsource, d0, d2);
         this.b = MathHelper.a(randomsource, d1, d3);
      }

      private static IBlockData getBlockState(IBlockAccess iblockaccess, BlockPosition position) {
         ((WorldServer)iblockaccess).k().a(position.u() >> 4, position.w() >> 4, true);
         return iblockaccess.a_(position);
      }
   }
}
