package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Optional;
import net.minecraft.ResourceKeyInvalidException;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.ArgumentMinecraftKeyRegistered;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.commands.arguments.TemplateMirrorArgument;
import net.minecraft.commands.arguments.TemplateRotationArgument;
import net.minecraft.commands.arguments.coordinates.ArgumentPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.entity.TileEntityStructure;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructureJigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorRotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class PlaceCommand {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.place.feature.failed"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.place.jigsaw.failed"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.place.structure.failed"));
   private static final DynamicCommandExceptionType d = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.place.template.invalid", var0));
   private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.place.template.failed"));
   private static final SuggestionProvider<CommandListenerWrapper> f = (var0, var1) -> {
      StructureTemplateManager var2 = ((CommandListenerWrapper)var0.getSource()).e().p();
      return ICompletionProvider.a(var2.a(), var1);
   };

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                           "place"
                        )
                        .requires(var0x -> var0x.c(2)))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("feature")
                           .then(
                              ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("feature", ResourceKeyArgument.a(Registries.aq))
                                    .executes(
                                       var0x -> a(
                                             (CommandListenerWrapper)var0x.getSource(),
                                             ResourceKeyArgument.a(var0x, "feature"),
                                             BlockPosition.a(((CommandListenerWrapper)var0x.getSource()).d())
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("pos", ArgumentPosition.a())
                                       .executes(
                                          var0x -> a(
                                                (CommandListenerWrapper)var0x.getSource(),
                                                ResourceKeyArgument.a(var0x, "feature"),
                                                ArgumentPosition.a(var0x, "pos")
                                             )
                                       )
                                 )
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("jigsaw")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("pool", ResourceKeyArgument.a(Registries.aA))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("target", ArgumentMinecraftKeyRegistered.a())
                                    .then(
                                       ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("max_depth", IntegerArgumentType.integer(1, 7))
                                             .executes(
                                                var0x -> a(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      ResourceKeyArgument.c(var0x, "pool"),
                                                      ArgumentMinecraftKeyRegistered.e(var0x, "target"),
                                                      IntegerArgumentType.getInteger(var0x, "max_depth"),
                                                      BlockPosition.a(((CommandListenerWrapper)var0x.getSource()).d())
                                                   )
                                             ))
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("position", ArgumentPosition.a())
                                                .executes(
                                                   var0x -> a(
                                                         (CommandListenerWrapper)var0x.getSource(),
                                                         ResourceKeyArgument.c(var0x, "pool"),
                                                         ArgumentMinecraftKeyRegistered.e(var0x, "target"),
                                                         IntegerArgumentType.getInteger(var0x, "max_depth"),
                                                         ArgumentPosition.a(var0x, "position")
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  ))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("structure")
                     .then(
                        ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("structure", ResourceKeyArgument.a(Registries.ax))
                              .executes(
                                 var0x -> b(
                                       (CommandListenerWrapper)var0x.getSource(),
                                       ResourceKeyArgument.b(var0x, "structure"),
                                       BlockPosition.a(((CommandListenerWrapper)var0x.getSource()).d())
                                    )
                              ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("pos", ArgumentPosition.a())
                                 .executes(
                                    var0x -> b(
                                          (CommandListenerWrapper)var0x.getSource(),
                                          ResourceKeyArgument.b(var0x, "structure"),
                                          ArgumentPosition.a(var0x, "pos")
                                       )
                                 )
                           )
                     )
               ))
            .then(
               net.minecraft.commands.CommandDispatcher.a("template")
                  .then(
                     ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("template", ArgumentMinecraftKeyRegistered.a())
                           .suggests(f)
                           .executes(
                              var0x -> a(
                                    (CommandListenerWrapper)var0x.getSource(),
                                    ArgumentMinecraftKeyRegistered.e(var0x, "template"),
                                    BlockPosition.a(((CommandListenerWrapper)var0x.getSource()).d()),
                                    EnumBlockRotation.a,
                                    EnumBlockMirror.a,
                                    1.0F,
                                    0
                                 )
                           ))
                        .then(
                           ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("pos", ArgumentPosition.a())
                                 .executes(
                                    var0x -> a(
                                          (CommandListenerWrapper)var0x.getSource(),
                                          ArgumentMinecraftKeyRegistered.e(var0x, "template"),
                                          ArgumentPosition.a(var0x, "pos"),
                                          EnumBlockRotation.a,
                                          EnumBlockMirror.a,
                                          1.0F,
                                          0
                                       )
                                 ))
                              .then(
                                 ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("rotation", TemplateRotationArgument.a())
                                       .executes(
                                          var0x -> a(
                                                (CommandListenerWrapper)var0x.getSource(),
                                                ArgumentMinecraftKeyRegistered.e(var0x, "template"),
                                                ArgumentPosition.a(var0x, "pos"),
                                                TemplateRotationArgument.a(var0x, "rotation"),
                                                EnumBlockMirror.a,
                                                1.0F,
                                                0
                                             )
                                       ))
                                    .then(
                                       ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("mirror", TemplateMirrorArgument.a())
                                             .executes(
                                                var0x -> a(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      ArgumentMinecraftKeyRegistered.e(var0x, "template"),
                                                      ArgumentPosition.a(var0x, "pos"),
                                                      TemplateRotationArgument.a(var0x, "rotation"),
                                                      TemplateMirrorArgument.a(var0x, "mirror"),
                                                      1.0F,
                                                      0
                                                   )
                                             ))
                                          .then(
                                             ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                      "integrity", FloatArgumentType.floatArg(0.0F, 1.0F)
                                                   )
                                                   .executes(
                                                      var0x -> a(
                                                            (CommandListenerWrapper)var0x.getSource(),
                                                            ArgumentMinecraftKeyRegistered.e(var0x, "template"),
                                                            ArgumentPosition.a(var0x, "pos"),
                                                            TemplateRotationArgument.a(var0x, "rotation"),
                                                            TemplateMirrorArgument.a(var0x, "mirror"),
                                                            FloatArgumentType.getFloat(var0x, "integrity"),
                                                            0
                                                         )
                                                   ))
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("seed", IntegerArgumentType.integer())
                                                      .executes(
                                                         var0x -> a(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               ArgumentMinecraftKeyRegistered.e(var0x, "template"),
                                                               ArgumentPosition.a(var0x, "pos"),
                                                               TemplateRotationArgument.a(var0x, "rotation"),
                                                               TemplateMirrorArgument.a(var0x, "mirror"),
                                                               FloatArgumentType.getFloat(var0x, "integrity"),
                                                               IntegerArgumentType.getInteger(var0x, "seed")
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

   public static int a(CommandListenerWrapper var0, Holder.c<WorldGenFeatureConfigured<?, ?>> var1, BlockPosition var2) throws CommandSyntaxException {
      WorldServer var3 = var0.e();
      WorldGenFeatureConfigured<?, ?> var4 = var1.a();
      ChunkCoordIntPair var5 = new ChunkCoordIntPair(var2);
      a(var3, new ChunkCoordIntPair(var5.e - 1, var5.f - 1), new ChunkCoordIntPair(var5.e + 1, var5.f + 1));
      if (!var4.a(var3, var3.k().g(), var3.r_(), var2)) {
         throw a.create();
      } else {
         String var6 = var1.g().a().toString();
         var0.a(IChatBaseComponent.a("commands.place.feature.success", var6, var2.u(), var2.v(), var2.w()), true);
         return 1;
      }
   }

   public static int a(CommandListenerWrapper var0, Holder<WorldGenFeatureDefinedStructurePoolTemplate> var1, MinecraftKey var2, int var3, BlockPosition var4) throws CommandSyntaxException {
      WorldServer var5 = var0.e();
      if (!WorldGenFeatureDefinedStructureJigsawPlacement.a(var5, var1, var2, var3, var4, false)) {
         throw b.create();
      } else {
         var0.a(IChatBaseComponent.a("commands.place.jigsaw.success", var4.u(), var4.v(), var4.w()), true);
         return 1;
      }
   }

   public static int b(CommandListenerWrapper var0, Holder.c<Structure> var1, BlockPosition var2) throws CommandSyntaxException {
      WorldServer var3 = var0.e();
      Structure var4 = var1.a();
      ChunkGenerator var5 = var3.k().g();
      StructureStart var6 = var4.a(var0.u(), var5, var5.c(), var3.k().i(), var3.p(), var3.A(), new ChunkCoordIntPair(var2), 0, var3, var0x -> true);
      if (!var6.b()) {
         throw c.create();
      } else {
         StructureBoundingBox var7 = var6.a();
         ChunkCoordIntPair var8 = new ChunkCoordIntPair(SectionPosition.a(var7.g()), SectionPosition.a(var7.i()));
         ChunkCoordIntPair var9 = new ChunkCoordIntPair(SectionPosition.a(var7.j()), SectionPosition.a(var7.l()));
         a(var3, var8, var9);
         ChunkCoordIntPair.a(var8, var9)
            .forEach(
               var3x -> var6.a(
                     var3, var3.a(), var5, var3.r_(), new StructureBoundingBox(var3x.d(), var3.v_(), var3x.e(), var3x.f(), var3.ai(), var3x.g()), var3x
                  )
            );
         String var10 = var1.g().a().toString();
         var0.a(IChatBaseComponent.a("commands.place.structure.success", var10, var2.u(), var2.v(), var2.w()), true);
         return 1;
      }
   }

   public static int a(CommandListenerWrapper var0, MinecraftKey var1, BlockPosition var2, EnumBlockRotation var3, EnumBlockMirror var4, float var5, int var6) throws CommandSyntaxException {
      WorldServer var7 = var0.e();
      StructureTemplateManager var8 = var7.p();

      Optional<DefinedStructure> var9;
      try {
         var9 = var8.b(var1);
      } catch (ResourceKeyInvalidException var13) {
         throw d.create(var1);
      }

      if (var9.isEmpty()) {
         throw d.create(var1);
      } else {
         DefinedStructure var10 = var9.get();
         a(var7, new ChunkCoordIntPair(var2), new ChunkCoordIntPair(var2.a(var10.a())));
         DefinedStructureInfo var11 = new DefinedStructureInfo().a(var4).a(var3);
         if (var5 < 1.0F) {
            var11.b().a(new DefinedStructureProcessorRotation(var5)).a(TileEntityStructure.b((long)var6));
         }

         boolean var12 = var10.a(var7, var2, var2, var11, TileEntityStructure.b((long)var6), 2);
         if (!var12) {
            throw e.create();
         } else {
            var0.a(IChatBaseComponent.a("commands.place.template.success", var1, var2.u(), var2.v(), var2.w()), true);
            return 1;
         }
      }
   }

   private static void a(WorldServer var0, ChunkCoordIntPair var1, ChunkCoordIntPair var2) throws CommandSyntaxException {
      if (ChunkCoordIntPair.a(var1, var2).filter(var1x -> !var0.o(var1x.l())).findAny().isPresent()) {
         throw ArgumentPosition.a.create();
      }
   }
}
