package org.bukkit.craftbukkit.v1_19_R3.generator.strucutre;

import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.Registries;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;

public class CraftStructure extends Structure {
   private final NamespacedKey key;
   private final net.minecraft.world.level.levelgen.structure.Structure structure;
   private final StructureType structureType;

   public static Structure minecraftToBukkit(net.minecraft.world.level.levelgen.structure.Structure minecraft, IRegistryCustom registryHolder) {
      return minecraft == null ? null : (Structure)Registry.STRUCTURE.get(CraftNamespacedKey.fromMinecraft(registryHolder.d(Registries.ax).b(minecraft)));
   }

   public static net.minecraft.world.level.levelgen.structure.Structure bukkitToMinecraft(Structure bukkit) {
      return bukkit == null ? null : ((CraftStructure)bukkit).getHandle();
   }

   public CraftStructure(NamespacedKey key, net.minecraft.world.level.levelgen.structure.Structure structure) {
      this.key = key;
      this.structure = structure;
      this.structureType = CraftStructureType.minecraftToBukkit(structure.e());
   }

   public net.minecraft.world.level.levelgen.structure.Structure getHandle() {
      return this.structure;
   }

   public StructureType getStructureType() {
      return this.structureType;
   }

   public NamespacedKey getKey() {
      return this.key;
   }
}
