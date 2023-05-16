package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBanner;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockEntityState;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockStates;
import org.bukkit.inventory.meta.BlockStateMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaBlockState extends CraftMetaItem implements BlockStateMeta {
   private static final Set<Material> SHULKER_BOX_MATERIALS = Sets.newHashSet(
      new Material[]{
         Material.SHULKER_BOX,
         Material.WHITE_SHULKER_BOX,
         Material.ORANGE_SHULKER_BOX,
         Material.MAGENTA_SHULKER_BOX,
         Material.LIGHT_BLUE_SHULKER_BOX,
         Material.YELLOW_SHULKER_BOX,
         Material.LIME_SHULKER_BOX,
         Material.PINK_SHULKER_BOX,
         Material.GRAY_SHULKER_BOX,
         Material.LIGHT_GRAY_SHULKER_BOX,
         Material.CYAN_SHULKER_BOX,
         Material.PURPLE_SHULKER_BOX,
         Material.BLUE_SHULKER_BOX,
         Material.BROWN_SHULKER_BOX,
         Material.GREEN_SHULKER_BOX,
         Material.RED_SHULKER_BOX,
         Material.BLACK_SHULKER_BOX
      }
   );
   private static final Set<Material> BLOCK_STATE_MATERIALS = Sets.newHashSet(
      new Material[]{
         Material.FURNACE,
         Material.CHEST,
         Material.TRAPPED_CHEST,
         Material.JUKEBOX,
         Material.DISPENSER,
         Material.DROPPER,
         Material.ACACIA_HANGING_SIGN,
         Material.ACACIA_SIGN,
         Material.ACACIA_WALL_HANGING_SIGN,
         Material.ACACIA_WALL_SIGN,
         Material.BAMBOO_HANGING_SIGN,
         Material.BAMBOO_SIGN,
         Material.BAMBOO_WALL_HANGING_SIGN,
         Material.BAMBOO_WALL_SIGN,
         Material.BIRCH_HANGING_SIGN,
         Material.BIRCH_SIGN,
         Material.BIRCH_WALL_HANGING_SIGN,
         Material.BIRCH_WALL_SIGN,
         Material.CHERRY_HANGING_SIGN,
         Material.CHERRY_SIGN,
         Material.CHERRY_WALL_HANGING_SIGN,
         Material.CHERRY_WALL_SIGN,
         Material.CRIMSON_HANGING_SIGN,
         Material.CRIMSON_SIGN,
         Material.CRIMSON_WALL_HANGING_SIGN,
         Material.CRIMSON_WALL_SIGN,
         Material.DARK_OAK_HANGING_SIGN,
         Material.DARK_OAK_SIGN,
         Material.DARK_OAK_WALL_HANGING_SIGN,
         Material.DARK_OAK_WALL_SIGN,
         Material.JUNGLE_HANGING_SIGN,
         Material.JUNGLE_SIGN,
         Material.JUNGLE_WALL_HANGING_SIGN,
         Material.JUNGLE_WALL_SIGN,
         Material.MANGROVE_HANGING_SIGN,
         Material.MANGROVE_SIGN,
         Material.MANGROVE_WALL_HANGING_SIGN,
         Material.MANGROVE_WALL_SIGN,
         Material.OAK_HANGING_SIGN,
         Material.OAK_SIGN,
         Material.OAK_WALL_HANGING_SIGN,
         Material.OAK_WALL_SIGN,
         Material.SPRUCE_HANGING_SIGN,
         Material.SPRUCE_SIGN,
         Material.SPRUCE_WALL_HANGING_SIGN,
         Material.SPRUCE_WALL_SIGN,
         Material.WARPED_HANGING_SIGN,
         Material.WARPED_SIGN,
         Material.WARPED_WALL_HANGING_SIGN,
         Material.WARPED_WALL_SIGN,
         Material.SPAWNER,
         Material.BREWING_STAND,
         Material.ENCHANTING_TABLE,
         Material.COMMAND_BLOCK,
         Material.REPEATING_COMMAND_BLOCK,
         Material.CHAIN_COMMAND_BLOCK,
         Material.BEACON,
         Material.DAYLIGHT_DETECTOR,
         Material.HOPPER,
         Material.COMPARATOR,
         Material.SHIELD,
         Material.STRUCTURE_BLOCK,
         Material.ENDER_CHEST,
         Material.BARREL,
         Material.BELL,
         Material.BLAST_FURNACE,
         Material.CAMPFIRE,
         Material.SOUL_CAMPFIRE,
         Material.JIGSAW,
         Material.LECTERN,
         Material.SMOKER,
         Material.BEEHIVE,
         Material.BEE_NEST,
         Material.SCULK_CATALYST,
         Material.SCULK_SHRIEKER,
         Material.SCULK_SENSOR,
         Material.CHISELED_BOOKSHELF,
         Material.DECORATED_POT,
         Material.SUSPICIOUS_SAND
      }
   );
   static final CraftMetaItem.ItemMetaKey BLOCK_ENTITY_TAG = new CraftMetaItem.ItemMetaKey("BlockEntityTag");
   final Material material;
   NBTTagCompound blockEntityTag;

   static {
      BLOCK_STATE_MATERIALS.addAll(SHULKER_BOX_MATERIALS);
   }

   CraftMetaBlockState(CraftMetaItem meta, Material material) {
      super(meta);
      this.material = material;
      if (meta instanceof CraftMetaBlockState te && ((CraftMetaBlockState)meta).material == material) {
         this.blockEntityTag = te.blockEntityTag;
      } else {
         this.blockEntityTag = null;
      }
   }

   CraftMetaBlockState(NBTTagCompound tag, Material material) {
      super(tag);
      this.material = material;
      if (tag.b(BLOCK_ENTITY_TAG.NBT, 10)) {
         this.blockEntityTag = tag.p(BLOCK_ENTITY_TAG.NBT).h();
      } else {
         this.blockEntityTag = null;
      }
   }

   CraftMetaBlockState(Map<String, Object> map) {
      super(map);
      String matName = CraftMetaItem.SerializableMeta.getString(map, "blockMaterial", true);
      Material m = Material.getMaterial(matName);
      if (m != null) {
         this.material = m;
      } else {
         this.material = Material.AIR;
      }
   }

   @Override
   void applyToItem(NBTTagCompound tag) {
      super.applyToItem(tag);
      if (this.blockEntityTag != null) {
         tag.a(BLOCK_ENTITY_TAG.NBT, this.blockEntityTag);
      }
   }

   @Override
   void deserializeInternal(NBTTagCompound tag, Object context) {
      super.deserializeInternal(tag, context);
      if (tag.b(BLOCK_ENTITY_TAG.NBT, 10)) {
         this.blockEntityTag = tag.p(BLOCK_ENTITY_TAG.NBT);
      }
   }

   @Override
   void serializeInternal(Map<String, NBTBase> internalTags) {
      if (this.blockEntityTag != null) {
         internalTags.put(BLOCK_ENTITY_TAG.NBT, this.blockEntityTag);
      }
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      builder.put("blockMaterial", this.material.name());
      return builder;
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.blockEntityTag != null) {
         hash = 61 * hash + this.blockEntityTag.hashCode();
      }

      return original != hash ? CraftMetaBlockState.class.hashCode() ^ hash : hash;
   }

   @Override
   public boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else {
         return meta instanceof CraftMetaBlockState that ? Objects.equal(this.blockEntityTag, that.blockEntityTag) : true;
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaBlockState || this.blockEntityTag == null);
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.blockEntityTag == null;
   }

   @Override
   boolean applicableTo(Material type) {
      return BLOCK_STATE_MATERIALS.contains(type);
   }

   public CraftMetaBlockState clone() {
      CraftMetaBlockState meta = (CraftMetaBlockState)super.clone();
      if (this.blockEntityTag != null) {
         meta.blockEntityTag = this.blockEntityTag.h();
      }

      return meta;
   }

   public boolean hasBlockState() {
      return this.blockEntityTag != null;
   }

   public BlockState getBlockState() {
      Material stateMaterial = this.material != Material.SHIELD ? this.material : shieldToBannerHack(this.blockEntityTag);
      if (this.blockEntityTag != null) {
         if (this.material == Material.SHIELD) {
            this.blockEntityTag.a("id", "minecraft:banner");
         } else if (this.material == Material.BEE_NEST || this.material == Material.BEEHIVE) {
            this.blockEntityTag.a("id", "minecraft:beehive");
         } else if (SHULKER_BOX_MATERIALS.contains(this.material)) {
            this.blockEntityTag.a("id", "minecraft:shulker_box");
         }
      }

      return CraftBlockStates.getBlockState(stateMaterial, this.blockEntityTag);
   }

   public void setBlockState(BlockState blockState) {
      Validate.notNull(blockState, "blockState must not be null");
      Material stateMaterial = this.material != Material.SHIELD ? this.material : shieldToBannerHack(this.blockEntityTag);
      Class<?> blockStateType = CraftBlockStates.getBlockStateType(stateMaterial);
      Validate.isTrue(blockStateType == blockState.getClass() && blockState instanceof CraftBlockEntityState, "Invalid blockState for " + this.material);
      this.blockEntityTag = ((CraftBlockEntityState)blockState).getSnapshotNBT();
      if (this.material == Material.SHIELD) {
         this.blockEntityTag.a(CraftMetaBanner.BASE.NBT, ((CraftBanner)blockState).getBaseColor().getWoolData());
      }
   }

   private static Material shieldToBannerHack(NBTTagCompound tag) {
      if (tag != null && tag.b(CraftMetaBanner.BASE.NBT, 3)) {
         switch(tag.h(CraftMetaBanner.BASE.NBT)) {
            case 0:
               return Material.WHITE_BANNER;
            case 1:
               return Material.ORANGE_BANNER;
            case 2:
               return Material.MAGENTA_BANNER;
            case 3:
               return Material.LIGHT_BLUE_BANNER;
            case 4:
               return Material.YELLOW_BANNER;
            case 5:
               return Material.LIME_BANNER;
            case 6:
               return Material.PINK_BANNER;
            case 7:
               return Material.GRAY_BANNER;
            case 8:
               return Material.LIGHT_GRAY_BANNER;
            case 9:
               return Material.CYAN_BANNER;
            case 10:
               return Material.PURPLE_BANNER;
            case 11:
               return Material.BLUE_BANNER;
            case 12:
               return Material.BROWN_BANNER;
            case 13:
               return Material.GREEN_BANNER;
            case 14:
               return Material.RED_BANNER;
            case 15:
               return Material.BLACK_BANNER;
            default:
               throw new IllegalArgumentException("Unknown banner colour");
         }
      } else {
         return Material.WHITE_BANNER;
      }
   }
}
