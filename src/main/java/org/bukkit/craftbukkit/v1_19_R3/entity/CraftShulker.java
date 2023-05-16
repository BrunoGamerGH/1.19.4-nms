package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.monster.EntityShulker;
import org.bukkit.DyeColor;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Shulker;

public class CraftShulker extends CraftGolem implements Shulker, CraftEnemy {
   public CraftShulker(CraftServer server, EntityShulker entity) {
      super(server, entity);
   }

   @Override
   public String toString() {
      return "CraftShulker";
   }

   @Override
   public EntityType getType() {
      return EntityType.SHULKER;
   }

   public EntityShulker getHandle() {
      return (EntityShulker)this.entity;
   }

   public DyeColor getColor() {
      return DyeColor.getByWoolData(this.getHandle().aj().a(EntityShulker.d));
   }

   public void setColor(DyeColor color) {
      this.getHandle().aj().b(EntityShulker.d, color == null ? 16 : color.getWoolData());
   }

   public float getPeek() {
      return (float)this.getHandle().fZ() / 100.0F;
   }

   public void setPeek(float value) {
      Preconditions.checkArgument(value >= 0.0F && value <= 1.0F, "value needs to be in between or equal to 0 and 1");
      this.getHandle().b((int)(value * 100.0F));
   }

   public BlockFace getAttachedFace() {
      return CraftBlock.notchToBlockFace(this.getHandle().w());
   }

   public void setAttachedFace(BlockFace face) {
      Preconditions.checkNotNull(face, "face cannot be null");
      Preconditions.checkArgument(face.isCartesian(), "%s is not a valid block face to attach a shulker to, a cartesian block face is expected", face);
      this.getHandle().a(CraftBlock.blockFaceToNotch(face));
   }
}
