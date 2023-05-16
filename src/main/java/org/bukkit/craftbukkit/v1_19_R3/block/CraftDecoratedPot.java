package org.bukkit.craftbukkit.v1_19_R3.block;

import com.google.common.base.Preconditions;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.DecoratedPot;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;

public class CraftDecoratedPot extends CraftBlockEntityState<DecoratedPotBlockEntity> implements DecoratedPot {
   public CraftDecoratedPot(World world, DecoratedPotBlockEntity tileEntity) {
      super(world, tileEntity);
   }

   public List<Material> getShards() {
      return this.getSnapshot().f().stream().map(CraftMagicNumbers::getMaterial).collect(Collectors.toUnmodifiableList());
   }

   public void addShard(Material material) {
      Preconditions.checkArgument(material != null && material.isItem(), "Material must be an item");
      this.getSnapshot().f().add(CraftMagicNumbers.getItem(material));
   }

   public void setShards(List<Material> shard) {
      this.getSnapshot().f().clear();

      for(Material material : shard) {
         this.addShard(material);
      }
   }
}
