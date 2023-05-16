package net.minecraft.world.level.levelgen.structure.pieces;

import net.minecraft.core.IRegistryCustom;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.packs.resources.IResourceManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public record StructurePieceSerializationContext(
   IResourceManager resourceManager, IRegistryCustom registryAccess, StructureTemplateManager structureTemplateManager
) {
   private final IResourceManager a;
   private final IRegistryCustom b;
   private final StructureTemplateManager c;

   public StructurePieceSerializationContext(IResourceManager var0, IRegistryCustom var1, StructureTemplateManager var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public static StructurePieceSerializationContext a(WorldServer var0) {
      MinecraftServer var1 = var0.n();
      return new StructurePieceSerializationContext(var1.ba(), var1.aX(), var1.aV());
   }
}
