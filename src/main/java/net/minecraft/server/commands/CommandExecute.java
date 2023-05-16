package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;
import net.minecraft.advancements.critereon.CriterionConditionValue;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.ArgumentAnchor;
import net.minecraft.commands.arguments.ArgumentCriterionValue;
import net.minecraft.commands.arguments.ArgumentDimension;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ArgumentMinecraftKeyRegistered;
import net.minecraft.commands.arguments.ArgumentNBTKey;
import net.minecraft.commands.arguments.ArgumentScoreboardObjective;
import net.minecraft.commands.arguments.ArgumentScoreholder;
import net.minecraft.commands.arguments.HeightmapTypeArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.ResourceOrTagArgument;
import net.minecraft.commands.arguments.blocks.ArgumentBlockPredicate;
import net.minecraft.commands.arguments.coordinates.ArgumentPosition;
import net.minecraft.commands.arguments.coordinates.ArgumentRotation;
import net.minecraft.commands.arguments.coordinates.ArgumentRotationAxis;
import net.minecraft.commands.arguments.coordinates.ArgumentVec3;
import net.minecraft.commands.synchronization.CompletionProviders;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.bossevents.BossBattleCustom;
import net.minecraft.server.commands.data.CommandData;
import net.minecraft.server.commands.data.CommandDataAccessor;
import net.minecraft.server.level.PlayerChunk;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBlock;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.storage.loot.LootPredicateManager;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.ScoreboardScore;

public class CommandExecute {
   private static final int a = 32768;
   private static final Dynamic2CommandExceptionType b = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("commands.execute.blocks.toobig", var0, var1)
   );
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.execute.conditional.fail"));
   private static final DynamicCommandExceptionType d = new DynamicCommandExceptionType(
      var0 -> IChatBaseComponent.a("commands.execute.conditional.fail_count", var0)
   );
   private static final BinaryOperator<ResultConsumer<CommandListenerWrapper>> e = (var0, var1) -> (var2, var3, var4) -> {
         var0.onCommandComplete(var2, var3, var4);
         var1.onCommandComplete(var2, var3, var4);
      };
   private static final SuggestionProvider<CommandListenerWrapper> f = (var0, var1) -> {
      LootPredicateManager var2 = ((CommandListenerWrapper)var0.getSource()).l().aI();
      return ICompletionProvider.a(var2.a(), var1);
   };

   public static void a(CommandDispatcher<CommandListenerWrapper> var0, CommandBuildContext var1) {
      LiteralCommandNode<CommandListenerWrapper> var2 = var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)net.minecraft.commands.CommandDispatcher.a("execute").requires(var0x -> var0x.c(2))
      );
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                         "execute"
                                                      )
                                                      .requires(var0x -> var0x.c(2)))
                                                   .then(net.minecraft.commands.CommandDispatcher.a("run").redirect(var0.getRoot())))
                                                .then(a(var2, net.minecraft.commands.CommandDispatcher.a("if"), true, var1)))
                                             .then(a(var2, net.minecraft.commands.CommandDispatcher.a("unless"), false, var1)))
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("as")
                                                .then(net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.b()).fork(var2, var0x -> {
                                                   List<CommandListenerWrapper> var1x = Lists.newArrayList();
                                          
                                                   for(Entity var3 : ArgumentEntity.c(var0x, "targets")) {
                                                      var1x.add(((CommandListenerWrapper)var0x.getSource()).a(var3));
                                                   }
                                          
                                                   return var1x;
                                                }))
                                          ))
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("at")
                                             .then(net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.b()).fork(var2, var0x -> {
                                                List<CommandListenerWrapper> var1x = Lists.newArrayList();
                                       
                                                for(Entity var3 : ArgumentEntity.c(var0x, "targets")) {
                                                   var1x.add(((CommandListenerWrapper)var0x.getSource()).a((WorldServer)var3.H).a(var3.de()).a(var3.bD()));
                                                }
                                       
                                                return var1x;
                                             }))
                                       ))
                                    .then(
                                       ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("store")
                                             .then(a(var2, net.minecraft.commands.CommandDispatcher.a("result"), true)))
                                          .then(a(var2, net.minecraft.commands.CommandDispatcher.a("success"), false))
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("positioned")
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("pos", ArgumentVec3.a())
                                                   .redirect(
                                                      var2,
                                                      var0x -> ((CommandListenerWrapper)var0x.getSource())
                                                            .a(ArgumentVec3.a(var0x, "pos"))
                                                            .a(ArgumentAnchor.Anchor.a)
                                                   )
                                             ))
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("as")
                                                .then(net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.b()).fork(var2, var0x -> {
                                                   List<CommandListenerWrapper> var1x = Lists.newArrayList();
                                          
                                                   for(Entity var3 : ArgumentEntity.c(var0x, "targets")) {
                                                      var1x.add(((CommandListenerWrapper)var0x.getSource()).a(var3.de()));
                                                   }
                                          
                                                   return var1x;
                                                }))
                                          ))
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("over")
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("heightmap", HeightmapTypeArgument.a()).redirect(var2, var0x -> {
                                                   Vec3D var1x = ((CommandListenerWrapper)var0x.getSource()).d();
                                                   WorldServer var2x = ((CommandListenerWrapper)var0x.getSource()).e();
                                                   double var3 = var1x.a();
                                                   double var5 = var1x.c();
                                                   if (!var2x.b(SectionPosition.b(var3), SectionPosition.b(var5))) {
                                                      throw ArgumentPosition.a.create();
                                                   } else {
                                                      int var7 = var2x.a(HeightmapTypeArgument.a(var0x, "heightmap"), MathHelper.a(var3), MathHelper.a(var5));
                                                      return ((CommandListenerWrapper)var0x.getSource()).a(new Vec3D(var3, (double)var7, var5));
                                                   }
                                                })
                                             )
                                       )
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("rotated")
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("rot", ArgumentRotation.a())
                                             .redirect(
                                                var2,
                                                var0x -> ((CommandListenerWrapper)var0x.getSource())
                                                      .a(ArgumentRotation.a(var0x, "rot").b((CommandListenerWrapper)var0x.getSource()))
                                             )
                                       ))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("as")
                                          .then(net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.b()).fork(var2, var0x -> {
                                             List<CommandListenerWrapper> var1x = Lists.newArrayList();
                                    
                                             for(Entity var3 : ArgumentEntity.c(var0x, "targets")) {
                                                var1x.add(((CommandListenerWrapper)var0x.getSource()).a(var3.bD()));
                                             }
                                    
                                             return var1x;
                                          }))
                                    )
                              ))
                           .then(
                              ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("facing")
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("entity")
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.b())
                                                .then(net.minecraft.commands.CommandDispatcher.a("anchor", ArgumentAnchor.a()).fork(var2, var0x -> {
                                                   List<CommandListenerWrapper> var1x = Lists.newArrayList();
                                                   ArgumentAnchor.Anchor var2x = ArgumentAnchor.a(var0x, "anchor");
                                          
                                                   for(Entity var4 : ArgumentEntity.c(var0x, "targets")) {
                                                      var1x.add(((CommandListenerWrapper)var0x.getSource()).a(var4, var2x));
                                                   }
                                          
                                                   return var1x;
                                                }))
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("pos", ArgumentVec3.a())
                                       .redirect(var2, var0x -> ((CommandListenerWrapper)var0x.getSource()).b(ArgumentVec3.a(var0x, "pos")))
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("align")
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("axes", ArgumentRotationAxis.a())
                                    .redirect(
                                       var2,
                                       var0x -> ((CommandListenerWrapper)var0x.getSource())
                                             .a(((CommandListenerWrapper)var0x.getSource()).d().a(ArgumentRotationAxis.a(var0x, "axes")))
                                    )
                              )
                        ))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("anchored")
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("anchor", ArgumentAnchor.a())
                                 .redirect(var2, var0x -> ((CommandListenerWrapper)var0x.getSource()).a(ArgumentAnchor.a(var0x, "anchor")))
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("in")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("dimension", ArgumentDimension.a())
                              .redirect(var2, var0x -> ((CommandListenerWrapper)var0x.getSource()).a(ArgumentDimension.a(var0x, "dimension")))
                        )
                  ))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("summon")
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("entity", ResourceArgument.a(var1, Registries.r))
                           .suggests(CompletionProviders.d)
                           .redirect(var2, var0x -> a((CommandListenerWrapper)var0x.getSource(), ResourceArgument.e(var0x, "entity")))
                     )
               ))
            .then(a(var2, net.minecraft.commands.CommandDispatcher.a("on")))
      );
   }

   private static ArgumentBuilder<CommandListenerWrapper, ?> a(
      LiteralCommandNode<CommandListenerWrapper> var0, LiteralArgumentBuilder<CommandListenerWrapper> var1, boolean var2
   ) {
      var1.then(
         net.minecraft.commands.CommandDispatcher.a("score")
            .then(
               net.minecraft.commands.CommandDispatcher.a("targets", ArgumentScoreholder.b())
                  .suggests(ArgumentScoreholder.a)
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("objective", ArgumentScoreboardObjective.a())
                        .redirect(
                           var0,
                           var1x -> a(
                                 (CommandListenerWrapper)var1x.getSource(),
                                 ArgumentScoreholder.c(var1x, "targets"),
                                 ArgumentScoreboardObjective.a(var1x, "objective"),
                                 var2
                              )
                        )
                  )
            )
      );
      var1.then(
         net.minecraft.commands.CommandDispatcher.a("bossbar")
            .then(
               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("id", ArgumentMinecraftKeyRegistered.a())
                     .suggests(CommandBossBar.a)
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("value")
                           .redirect(var0, var1x -> a((CommandListenerWrapper)var1x.getSource(), CommandBossBar.a(var1x), true, var2))
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("max")
                        .redirect(var0, var1x -> a((CommandListenerWrapper)var1x.getSource(), CommandBossBar.a(var1x), false, var2))
                  )
            )
      );

      for(CommandData.c var4 : CommandData.b) {
         var4.a(
            var1,
            var3 -> var3.then(
                  ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                       "path", ArgumentNBTKey.a()
                                    )
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("int")
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("scale", DoubleArgumentType.doubleArg())
                                                .redirect(
                                                   var0,
                                                   var2xx -> a(
                                                         (CommandListenerWrapper)var2xx.getSource(),
                                                         var4.a(var2xx),
                                                         ArgumentNBTKey.a(var2xx, "path"),
                                                         var1xxx -> NBTTagInt.a((int)((double)var1xxx * DoubleArgumentType.getDouble(var2xx, "scale"))),
                                                         var2
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("float")
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("scale", DoubleArgumentType.doubleArg())
                                             .redirect(
                                                var0,
                                                var2xx -> a(
                                                      (CommandListenerWrapper)var2xx.getSource(),
                                                      var4.a(var2xx),
                                                      ArgumentNBTKey.a(var2xx, "path"),
                                                      var1xxx -> NBTTagFloat.a((float)((double)var1xxx * DoubleArgumentType.getDouble(var2xx, "scale"))),
                                                      var2
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("short")
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("scale", DoubleArgumentType.doubleArg())
                                          .redirect(
                                             var0,
                                             var2xx -> a(
                                                   (CommandListenerWrapper)var2xx.getSource(),
                                                   var4.a(var2xx),
                                                   ArgumentNBTKey.a(var2xx, "path"),
                                                   var1xxx -> NBTTagShort.a((short)((int)((double)var1xxx * DoubleArgumentType.getDouble(var2xx, "scale")))),
                                                   var2
                                                )
                                          )
                                    )
                              ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("long")
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("scale", DoubleArgumentType.doubleArg())
                                       .redirect(
                                          var0,
                                          var2xx -> a(
                                                (CommandListenerWrapper)var2xx.getSource(),
                                                var4.a(var2xx),
                                                ArgumentNBTKey.a(var2xx, "path"),
                                                var1xxx -> NBTTagLong.a((long)((double)var1xxx * DoubleArgumentType.getDouble(var2xx, "scale"))),
                                                var2
                                             )
                                       )
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("double")
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("scale", DoubleArgumentType.doubleArg())
                                    .redirect(
                                       var0,
                                       var2xx -> a(
                                             (CommandListenerWrapper)var2xx.getSource(),
                                             var4.a(var2xx),
                                             ArgumentNBTKey.a(var2xx, "path"),
                                             var1xxx -> NBTTagDouble.a((double)var1xxx * DoubleArgumentType.getDouble(var2xx, "scale")),
                                             var2
                                          )
                                    )
                              )
                        ))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("byte")
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("scale", DoubleArgumentType.doubleArg())
                                 .redirect(
                                    var0,
                                    var2xx -> a(
                                          (CommandListenerWrapper)var2xx.getSource(),
                                          var4.a(var2xx),
                                          ArgumentNBTKey.a(var2xx, "path"),
                                          var1xxx -> NBTTagByte.a((byte)((int)((double)var1xxx * DoubleArgumentType.getDouble(var2xx, "scale")))),
                                          var2
                                       )
                                 )
                           )
                     )
               )
         );
      }

      return var1;
   }

   private static CommandListenerWrapper a(CommandListenerWrapper var0, Collection<String> var1, ScoreboardObjective var2, boolean var3) {
      Scoreboard var4 = var0.l().aF();
      return var0.a((var4x, var5, var6) -> {
         for(String var8 : var1) {
            ScoreboardScore var9 = var4.c(var8, var2);
            int var10 = var3 ? var6 : (var5 ? 1 : 0);
            var9.b(var10);
         }
      }, e);
   }

   private static CommandListenerWrapper a(CommandListenerWrapper var0, BossBattleCustom var1, boolean var2, boolean var3) {
      return var0.a((var3x, var4, var5) -> {
         int var6 = var3 ? var5 : (var4 ? 1 : 0);
         if (var2) {
            var1.a(var6);
         } else {
            var1.b(var6);
         }
      }, e);
   }

   private static CommandListenerWrapper a(
      CommandListenerWrapper var0, CommandDataAccessor var1, ArgumentNBTKey.g var2, IntFunction<NBTBase> var3, boolean var4
   ) {
      return var0.a((var4x, var5, var6) -> {
         try {
            NBTTagCompound var7 = var1.a();
            int var8 = var4 ? var6 : (var5 ? 1 : 0);
            var2.a(var7, var3.apply(var8));
            var1.a(var7);
         } catch (CommandSyntaxException var9) {
         }
      }, e);
   }

   private static boolean a(WorldServer var0, BlockPosition var1) {
      int var2 = SectionPosition.a(var1.u());
      int var3 = SectionPosition.a(var1.w());
      Chunk var4 = var0.k().a(var2, var3);
      if (var4 != null) {
         return var4.B() == PlayerChunk.State.d;
      } else {
         return false;
      }
   }

   private static ArgumentBuilder<CommandListenerWrapper, ?> a(
      CommandNode<CommandListenerWrapper> var0, LiteralArgumentBuilder<CommandListenerWrapper> var1, boolean var2, CommandBuildContext var3
   ) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)var1.then(
                              net.minecraft.commands.CommandDispatcher.a("block")
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("pos", ArgumentPosition.a())
                                       .then(
                                          a(
                                             var0,
                                             net.minecraft.commands.CommandDispatcher.a("block", ArgumentBlockPredicate.a(var3)),
                                             var2,
                                             var0x -> ArgumentBlockPredicate.a(var0x, "block")
                                                   .test(
                                                      new ShapeDetectorBlock(
                                                         ((CommandListenerWrapper)var0x.getSource()).e(), ArgumentPosition.a(var0x, "pos"), true
                                                      )
                                                   )
                                          )
                                       )
                                 )
                           ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("biome")
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("pos", ArgumentPosition.a())
                                       .then(
                                          a(
                                             var0,
                                             net.minecraft.commands.CommandDispatcher.a("biome", ResourceOrTagArgument.a(var3, Registries.an)),
                                             var2,
                                             var0x -> ResourceOrTagArgument.a(var0x, "biome", Registries.an)
                                                   .test(((CommandListenerWrapper)var0x.getSource()).e().v(ArgumentPosition.a(var0x, "pos")))
                                          )
                                       )
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("loaded")
                              .then(
                                 a(
                                    var0,
                                    net.minecraft.commands.CommandDispatcher.a("pos", ArgumentPosition.a()),
                                    var2,
                                    var0x -> a(((CommandListenerWrapper)var0x.getSource()).e(), ArgumentPosition.b(var0x, "pos"))
                                 )
                              )
                        ))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("dimension")
                           .then(
                              a(
                                 var0,
                                 net.minecraft.commands.CommandDispatcher.a("dimension", ArgumentDimension.a()),
                                 var2,
                                 var0x -> ArgumentDimension.a(var0x, "dimension") == ((CommandListenerWrapper)var0x.getSource()).e()
                              )
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("score")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("target", ArgumentScoreholder.a())
                              .suggests(ArgumentScoreholder.a)
                              .then(
                                 ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                      "targetObjective", ArgumentScoreboardObjective.a()
                                                   )
                                                   .then(
                                                      net.minecraft.commands.CommandDispatcher.a("=")
                                                         .then(
                                                            net.minecraft.commands.CommandDispatcher.a("source", ArgumentScoreholder.a())
                                                               .suggests(ArgumentScoreholder.a)
                                                               .then(
                                                                  a(
                                                                     var0,
                                                                     net.minecraft.commands.CommandDispatcher.a(
                                                                        "sourceObjective", ArgumentScoreboardObjective.a()
                                                                     ),
                                                                     var2,
                                                                     var0x -> a(var0x, Integer::equals)
                                                                  )
                                                               )
                                                         )
                                                   ))
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("<")
                                                      .then(
                                                         net.minecraft.commands.CommandDispatcher.a("source", ArgumentScoreholder.a())
                                                            .suggests(ArgumentScoreholder.a)
                                                            .then(
                                                               a(
                                                                  var0,
                                                                  net.minecraft.commands.CommandDispatcher.a(
                                                                     "sourceObjective", ArgumentScoreboardObjective.a()
                                                                  ),
                                                                  var2,
                                                                  var0x -> a(var0x, (BiPredicate<Integer, Integer>)((var0xx, var1x) -> var0xx < var1x))
                                                               )
                                                            )
                                                      )
                                                ))
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("<=")
                                                   .then(
                                                      net.minecraft.commands.CommandDispatcher.a("source", ArgumentScoreholder.a())
                                                         .suggests(ArgumentScoreholder.a)
                                                         .then(
                                                            a(
                                                               var0,
                                                               net.minecraft.commands.CommandDispatcher.a("sourceObjective", ArgumentScoreboardObjective.a()),
                                                               var2,
                                                               var0x -> a(var0x, (BiPredicate<Integer, Integer>)((var0xx, var1x) -> var0xx <= var1x))
                                                            )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a(">")
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("source", ArgumentScoreholder.a())
                                                      .suggests(ArgumentScoreholder.a)
                                                      .then(
                                                         a(
                                                            var0,
                                                            net.minecraft.commands.CommandDispatcher.a("sourceObjective", ArgumentScoreboardObjective.a()),
                                                            var2,
                                                            var0x -> a(var0x, (BiPredicate<Integer, Integer>)((var0xx, var1x) -> var0xx > var1x))
                                                         )
                                                      )
                                                )
                                          ))
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a(">=")
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("source", ArgumentScoreholder.a())
                                                   .suggests(ArgumentScoreholder.a)
                                                   .then(
                                                      a(
                                                         var0,
                                                         net.minecraft.commands.CommandDispatcher.a("sourceObjective", ArgumentScoreboardObjective.a()),
                                                         var2,
                                                         var0x -> a(var0x, (BiPredicate<Integer, Integer>)((var0xx, var1x) -> var0xx >= var1x))
                                                      )
                                                   )
                                             )
                                       ))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("matches")
                                          .then(
                                             a(
                                                var0,
                                                net.minecraft.commands.CommandDispatcher.a("range", ArgumentCriterionValue.a()),
                                                var2,
                                                var0x -> a(var0x, ArgumentCriterionValue.b.a(var0x, "range"))
                                             )
                                          )
                                    )
                              )
                        )
                  ))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("blocks")
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("start", ArgumentPosition.a())
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("end", ArgumentPosition.a())
                                 .then(
                                    ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("destination", ArgumentPosition.a())
                                          .then(a(var0, net.minecraft.commands.CommandDispatcher.a("all"), var2, false)))
                                       .then(a(var0, net.minecraft.commands.CommandDispatcher.a("masked"), var2, true))
                                 )
                           )
                     )
               ))
            .then(
               net.minecraft.commands.CommandDispatcher.a("entity")
                  .then(
                     ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("entities", ArgumentEntity.b())
                           .fork(var0, var1x -> a(var1x, var2, !ArgumentEntity.c(var1x, "entities").isEmpty())))
                        .executes(a(var2, (CommandExecute.a)(var0x -> ArgumentEntity.c(var0x, "entities").size())))
                  )
            ))
         .then(
            net.minecraft.commands.CommandDispatcher.a("predicate")
               .then(
                  a(
                     var0,
                     net.minecraft.commands.CommandDispatcher.a("predicate", ArgumentMinecraftKeyRegistered.a()).suggests(f),
                     var2,
                     var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentMinecraftKeyRegistered.c(var0x, "predicate"))
                  )
               )
         );

      for(CommandData.c var5 : CommandData.c) {
         var1.then(
            var5.a(
               net.minecraft.commands.CommandDispatcher.a("data"),
               var3x -> var3x.then(
                     ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("path", ArgumentNBTKey.a())
                           .fork(var0, var2xx -> a(var2xx, var2, a(var5.a(var2xx), ArgumentNBTKey.a(var2xx, "path")) > 0)))
                        .executes(a(var2, (CommandExecute.a)(var1xx -> a(var5.a(var1xx), ArgumentNBTKey.a(var1xx, "path")))))
                  )
            )
         );
      }

      return var1;
   }

   private static Command<CommandListenerWrapper> a(boolean var0, CommandExecute.a var1) {
      return var0 ? var1x -> {
         int var2 = var1.test(var1x);
         if (var2 > 0) {
            ((CommandListenerWrapper)var1x.getSource()).a(IChatBaseComponent.a("commands.execute.conditional.pass_count", var2), false);
            return var2;
         } else {
            throw c.create();
         }
      } : var1x -> {
         int var2 = var1.test(var1x);
         if (var2 == 0) {
            ((CommandListenerWrapper)var1x.getSource()).a(IChatBaseComponent.c("commands.execute.conditional.pass"), false);
            return 1;
         } else {
            throw d.create(var2);
         }
      };
   }

   private static int a(CommandDataAccessor var0, ArgumentNBTKey.g var1) throws CommandSyntaxException {
      return var1.b(var0.a());
   }

   private static boolean a(CommandContext<CommandListenerWrapper> var0, BiPredicate<Integer, Integer> var1) throws CommandSyntaxException {
      String var2 = ArgumentScoreholder.a(var0, "target");
      ScoreboardObjective var3 = ArgumentScoreboardObjective.a(var0, "targetObjective");
      String var4 = ArgumentScoreholder.a(var0, "source");
      ScoreboardObjective var5 = ArgumentScoreboardObjective.a(var0, "sourceObjective");
      Scoreboard var6 = ((CommandListenerWrapper)var0.getSource()).l().aF();
      if (var6.b(var2, var3) && var6.b(var4, var5)) {
         ScoreboardScore var7 = var6.c(var2, var3);
         ScoreboardScore var8 = var6.c(var4, var5);
         return var1.test(var7.b(), var8.b());
      } else {
         return false;
      }
   }

   private static boolean a(CommandContext<CommandListenerWrapper> var0, CriterionConditionValue.IntegerRange var1) throws CommandSyntaxException {
      String var2 = ArgumentScoreholder.a(var0, "target");
      ScoreboardObjective var3 = ArgumentScoreboardObjective.a(var0, "targetObjective");
      Scoreboard var4 = ((CommandListenerWrapper)var0.getSource()).l().aF();
      return !var4.b(var2, var3) ? false : var1.d(var4.c(var2, var3).b());
   }

   private static boolean a(CommandListenerWrapper var0, LootItemCondition var1) {
      WorldServer var2 = var0.e();
      LootTableInfo.Builder var3 = new LootTableInfo.Builder(var2).a(LootContextParameters.f, var0.d()).b(LootContextParameters.a, var0.f());
      return var1.test(var3.a(LootContextParameterSets.c));
   }

   private static Collection<CommandListenerWrapper> a(CommandContext<CommandListenerWrapper> var0, boolean var1, boolean var2) {
      return (Collection<CommandListenerWrapper>)(var2 == var1 ? Collections.singleton((CommandListenerWrapper)var0.getSource()) : Collections.emptyList());
   }

   private static ArgumentBuilder<CommandListenerWrapper, ?> a(
      CommandNode<CommandListenerWrapper> var0, ArgumentBuilder<CommandListenerWrapper, ?> var1, boolean var2, CommandExecute.b var3
   ) {
      return var1.fork(var0, var2x -> a(var2x, var2, var3.test(var2x))).executes(var2x -> {
         if (var2 == var3.test(var2x)) {
            ((CommandListenerWrapper)var2x.getSource()).a(IChatBaseComponent.c("commands.execute.conditional.pass"), false);
            return 1;
         } else {
            throw c.create();
         }
      });
   }

   private static ArgumentBuilder<CommandListenerWrapper, ?> a(
      CommandNode<CommandListenerWrapper> var0, ArgumentBuilder<CommandListenerWrapper, ?> var1, boolean var2, boolean var3
   ) {
      return var1.fork(var0, var2x -> a(var2x, var2, c(var2x, var3).isPresent())).executes(var2 ? var1x -> a(var1x, var3) : var1x -> b(var1x, var3));
   }

   private static int a(CommandContext<CommandListenerWrapper> var0, boolean var1) throws CommandSyntaxException {
      OptionalInt var2 = c(var0, var1);
      if (var2.isPresent()) {
         ((CommandListenerWrapper)var0.getSource()).a(IChatBaseComponent.a("commands.execute.conditional.pass_count", var2.getAsInt()), false);
         return var2.getAsInt();
      } else {
         throw c.create();
      }
   }

   private static int b(CommandContext<CommandListenerWrapper> var0, boolean var1) throws CommandSyntaxException {
      OptionalInt var2 = c(var0, var1);
      if (var2.isPresent()) {
         throw d.create(var2.getAsInt());
      } else {
         ((CommandListenerWrapper)var0.getSource()).a(IChatBaseComponent.c("commands.execute.conditional.pass"), false);
         return 1;
      }
   }

   private static OptionalInt c(CommandContext<CommandListenerWrapper> var0, boolean var1) throws CommandSyntaxException {
      return a(
         ((CommandListenerWrapper)var0.getSource()).e(),
         ArgumentPosition.a(var0, "start"),
         ArgumentPosition.a(var0, "end"),
         ArgumentPosition.a(var0, "destination"),
         var1
      );
   }

   private static OptionalInt a(WorldServer var0, BlockPosition var1, BlockPosition var2, BlockPosition var3, boolean var4) throws CommandSyntaxException {
      StructureBoundingBox var5 = StructureBoundingBox.a(var1, var2);
      StructureBoundingBox var6 = StructureBoundingBox.a(var3, var3.a(var5.b()));
      BlockPosition var7 = new BlockPosition(var6.g() - var5.g(), var6.h() - var5.h(), var6.i() - var5.i());
      int var8 = var5.c() * var5.d() * var5.e();
      if (var8 > 32768) {
         throw b.create(32768, var8);
      } else {
         int var9 = 0;

         for(int var10 = var5.i(); var10 <= var5.l(); ++var10) {
            for(int var11 = var5.h(); var11 <= var5.k(); ++var11) {
               for(int var12 = var5.g(); var12 <= var5.j(); ++var12) {
                  BlockPosition var13 = new BlockPosition(var12, var11, var10);
                  BlockPosition var14 = var13.a(var7);
                  IBlockData var15 = var0.a_(var13);
                  if (!var4 || !var15.a(Blocks.a)) {
                     if (var15 != var0.a_(var14)) {
                        return OptionalInt.empty();
                     }

                     TileEntity var16 = var0.c_(var13);
                     TileEntity var17 = var0.c_(var14);
                     if (var16 != null) {
                        if (var17 == null) {
                           return OptionalInt.empty();
                        }

                        if (var17.u() != var16.u()) {
                           return OptionalInt.empty();
                        }

                        NBTTagCompound var18 = var16.o();
                        NBTTagCompound var19 = var17.o();
                        if (!var18.equals(var19)) {
                           return OptionalInt.empty();
                        }
                     }

                     ++var9;
                  }
               }
            }
         }

         return OptionalInt.of(var9);
      }
   }

   private static RedirectModifier<CommandListenerWrapper> a(Function<Entity, Optional<Entity>> var0) {
      return var1 -> {
         CommandListenerWrapper var2 = (CommandListenerWrapper)var1.getSource();
         Entity var3 = var2.f();
         return (Collection)(var3 == null ? List.of() : var0.apply(var3).filter(var0xx -> !var0xx.dB()).map(var1x -> List.of(var2.a(var1x))).orElse(List.of()));
      };
   }

   private static RedirectModifier<CommandListenerWrapper> b(Function<Entity, Stream<Entity>> var0) {
      return var1 -> {
         CommandListenerWrapper var2 = (CommandListenerWrapper)var1.getSource();
         Entity var3 = var2.f();
         return var3 == null ? List.of() : var0.apply(var3).filter(var0xx -> !var0xx.dB()).map(var2::a).toList();
      };
   }

   private static LiteralArgumentBuilder<CommandListenerWrapper> a(
      CommandNode<CommandListenerWrapper> var0, LiteralArgumentBuilder<CommandListenerWrapper> var1
   ) {
      return (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)var1.then(
                              net.minecraft.commands.CommandDispatcher.a("owner")
                                 .fork(
                                    var0,
                                    a(
                                       (Function<Entity, Optional<Entity>>)(var0x -> var0x instanceof OwnableEntity var1x
                                             ? Optional.ofNullable(var1x.H_())
                                             : Optional.empty())
                                    )
                                 )
                           ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("leasher")
                                 .fork(
                                    var0,
                                    a(
                                       (Function<Entity, Optional<Entity>>)(var0x -> var0x instanceof EntityInsentient var1x
                                             ? Optional.ofNullable(var1x.fJ())
                                             : Optional.empty())
                                    )
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("target")
                              .fork(
                                 var0,
                                 a(
                                    (Function<Entity, Optional<Entity>>)(var0x -> var0x instanceof Targeting var1x
                                          ? Optional.ofNullable(var1x.P_())
                                          : Optional.empty())
                                 )
                              )
                        ))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("attacker")
                           .fork(
                              var0,
                              a(
                                 (Function<Entity, Optional<Entity>>)(var0x -> var0x instanceof Attackable var1x
                                       ? Optional.ofNullable(var1x.L_())
                                       : Optional.empty())
                              )
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("vehicle")
                        .fork(var0, a((Function<Entity, Optional<Entity>>)(var0x -> Optional.ofNullable(var0x.cV()))))
                  ))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("controller")
                     .fork(var0, a((Function<Entity, Optional<Entity>>)(var0x -> Optional.ofNullable(var0x.cK()))))
               ))
            .then(
               net.minecraft.commands.CommandDispatcher.a("origin")
                  .fork(
                     var0,
                     a(
                        (Function<Entity, Optional<Entity>>)(var0x -> var0x instanceof TraceableEntity var1x
                              ? Optional.ofNullable(var1x.v())
                              : Optional.empty())
                     )
                  )
            ))
         .then(net.minecraft.commands.CommandDispatcher.a("passengers").fork(var0, b((Function<Entity, Stream<Entity>>)(var0x -> var0x.cM().stream()))));
   }

   private static CommandListenerWrapper a(CommandListenerWrapper var0, Holder.c<EntityTypes<?>> var1) throws CommandSyntaxException {
      Entity var2 = CommandSummon.a(var0, var1, var0.d(), new NBTTagCompound(), true);
      return var0.a(var2);
   }

   @FunctionalInterface
   interface a {
      int test(CommandContext<CommandListenerWrapper> var1) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface b {
      boolean test(CommandContext<CommandListenerWrapper> var1) throws CommandSyntaxException;
   }
}
