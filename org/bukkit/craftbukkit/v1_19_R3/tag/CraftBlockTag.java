package org.bukkit.craftbukkit.v1_19_R3.tag;

import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;

public class CraftBlockTag extends CraftTag<Block, Material> {
   public CraftBlockTag(IRegistry<Block> registry, TagKey<Block> tag) {
      super(registry, tag);
   }

   public boolean isTagged(Material item) {
      Block block = CraftMagicNumbers.getBlock(item);
      return block == null ? false : block.r().a(this.tag);
   }

   public Set<Material> getValues() {
      return this.getHandle().a().map(block -> CraftMagicNumbers.getMaterial((Block)block.a())).collect(Collectors.toUnmodifiableSet());
   }
}
