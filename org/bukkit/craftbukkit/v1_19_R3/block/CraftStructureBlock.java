package org.bukkit.craftbukkit.v1_19_R3.block;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.BlockStructure;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.entity.TileEntityStructure;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyStructureMode;
import org.apache.commons.lang3.Validate;
import org.bukkit.World;
import org.bukkit.block.Structure;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.block.structure.UsageMode;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockVector;

public class CraftStructureBlock extends CraftBlockEntityState<TileEntityStructure> implements Structure {
   private static final int MAX_SIZE = 48;

   public CraftStructureBlock(World world, TileEntityStructure tileEntity) {
      super(world, tileEntity);
   }

   public String getStructureName() {
      return this.getSnapshot().d();
   }

   public void setStructureName(String name) {
      Preconditions.checkArgument(name != null, "Structure Name cannot be null");
      this.getSnapshot().a(name);
   }

   public String getAuthor() {
      return this.getSnapshot().f;
   }

   public void setAuthor(String author) {
      Preconditions.checkArgument(author != null && !author.isEmpty(), "Author name cannot be null nor empty");
      this.getSnapshot().f = author;
   }

   public void setAuthor(LivingEntity entity) {
      Preconditions.checkArgument(entity != null, "Structure Block author entity cannot be null");
      this.getSnapshot().a(((CraftLivingEntity)entity).getHandle());
   }

   public BlockVector getRelativePosition() {
      return new BlockVector(this.getSnapshot().h.u(), this.getSnapshot().h.v(), this.getSnapshot().h.w());
   }

   public void setRelativePosition(BlockVector vector) {
      Validate.isTrue(isBetween(vector.getBlockX(), -48, 48), "Structure Size (X) must be between -48 and 48", new Object[0]);
      Validate.isTrue(isBetween(vector.getBlockY(), -48, 48), "Structure Size (Y) must be between -48 and 48", new Object[0]);
      Validate.isTrue(isBetween(vector.getBlockZ(), -48, 48), "Structure Size (Z) must be between -48 and 48", new Object[0]);
      this.getSnapshot().h = new BlockPosition(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
   }

   public BlockVector getStructureSize() {
      return new BlockVector(this.getSnapshot().i.u(), this.getSnapshot().i.v(), this.getSnapshot().i.w());
   }

   public void setStructureSize(BlockVector vector) {
      Validate.isTrue(isBetween(vector.getBlockX(), 0, 48), "Structure Size (X) must be between 0 and 48", new Object[0]);
      Validate.isTrue(isBetween(vector.getBlockY(), 0, 48), "Structure Size (Y) must be between 0 and 48", new Object[0]);
      Validate.isTrue(isBetween(vector.getBlockZ(), 0, 48), "Structure Size (Z) must be between 0 and 48", new Object[0]);
      this.getSnapshot().i = new BlockPosition(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
   }

   public void setMirror(Mirror mirror) {
      this.getSnapshot().j = EnumBlockMirror.valueOf(mirror.name());
   }

   public Mirror getMirror() {
      return Mirror.valueOf(this.getSnapshot().j.name());
   }

   public void setRotation(StructureRotation rotation) {
      this.getSnapshot().k = EnumBlockRotation.valueOf(rotation.name());
   }

   public StructureRotation getRotation() {
      return StructureRotation.valueOf(this.getSnapshot().k.name());
   }

   public void setUsageMode(UsageMode mode) {
      this.getSnapshot().l = BlockPropertyStructureMode.valueOf(mode.name());
   }

   public UsageMode getUsageMode() {
      return UsageMode.valueOf(this.getSnapshot().y().name());
   }

   public void setIgnoreEntities(boolean flag) {
      this.getSnapshot().m = flag;
   }

   public boolean isIgnoreEntities() {
      return this.getSnapshot().m;
   }

   public void setShowAir(boolean showAir) {
      this.getSnapshot().r = showAir;
   }

   public boolean isShowAir() {
      return this.getSnapshot().r;
   }

   public void setBoundingBoxVisible(boolean showBoundingBox) {
      this.getSnapshot().s = showBoundingBox;
   }

   public boolean isBoundingBoxVisible() {
      return this.getSnapshot().s;
   }

   public void setIntegrity(float integrity) {
      Validate.isTrue(isBetween(integrity, 0.0F, 1.0F), "Integrity must be between 0.0f and 1.0f", new Object[0]);
      this.getSnapshot().t = integrity;
   }

   public float getIntegrity() {
      return this.getSnapshot().t;
   }

   public void setSeed(long seed) {
      this.getSnapshot().u = seed;
   }

   public long getSeed() {
      return this.getSnapshot().u;
   }

   public void setMetadata(String metadata) {
      Validate.notNull(metadata, "Structure metadata cannot be null", new Object[0]);
      if (this.getUsageMode() == UsageMode.DATA) {
         this.getSnapshot().g = metadata;
      }
   }

   public String getMetadata() {
      return this.getSnapshot().g;
   }

   protected void applyTo(TileEntityStructure tileEntity) {
      super.applyTo(tileEntity);
      GeneratorAccess access = this.getWorldHandle();
      if (access instanceof net.minecraft.world.level.World) {
         tileEntity.a(tileEntity.y());
      } else if (access != null) {
         IBlockData data = access.a_(this.getPosition());
         if (data.a(Blocks.oW)) {
            access.a(this.getPosition(), data.a(BlockStructure.a, tileEntity.y()), 2);
         }
      }
   }

   private static boolean isBetween(int num, int min, int max) {
      return num >= min && num <= max;
   }

   private static boolean isBetween(float num, float min, float max) {
      return num >= min && num <= max;
   }
}
