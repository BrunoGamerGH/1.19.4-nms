package org.bukkit.craftbukkit.v1_19_R3.generator.strucutre;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;

public class CraftStructureType extends org.bukkit.generator.structure.StructureType {
   private final NamespacedKey key;
   private final StructureType<?> structureType;

   public static org.bukkit.generator.structure.StructureType minecraftToBukkit(StructureType<?> minecraft) {
      return minecraft == null
         ? null
         : (org.bukkit.generator.structure.StructureType)Registry.STRUCTURE_TYPE.get(CraftNamespacedKey.fromMinecraft(BuiltInRegistries.T.b(minecraft)));
   }

   public static StructureType<?> bukkitToMinecraft(org.bukkit.generator.structure.StructureType bukkit) {
      return bukkit == null ? null : ((CraftStructureType)bukkit).getHandle();
   }

   public CraftStructureType(NamespacedKey key, StructureType<?> structureType) {
      this.key = key;
      this.structureType = structureType;
   }

   public StructureType<?> getHandle() {
      return this.structureType;
   }

   public NamespacedKey getKey() {
      return this.key;
   }
}
