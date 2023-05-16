package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.DataResult;
import java.util.Map;
import java.util.Optional;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.inventory.meta.CompassMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaCompass extends CraftMetaItem implements CompassMeta {
   static final CraftMetaItem.ItemMetaKey LODESTONE_DIMENSION = new CraftMetaItem.ItemMetaKey("LodestoneDimension");
   static final CraftMetaItem.ItemMetaKey LODESTONE_POS = new CraftMetaItem.ItemMetaKey("LodestonePos", "lodestone");
   static final CraftMetaItem.ItemMetaKey LODESTONE_POS_WORLD = new CraftMetaItem.ItemMetaKey("LodestonePosWorld");
   static final CraftMetaItem.ItemMetaKey LODESTONE_POS_X = new CraftMetaItem.ItemMetaKey("LodestonePosX");
   static final CraftMetaItem.ItemMetaKey LODESTONE_POS_Y = new CraftMetaItem.ItemMetaKey("LodestonePosY");
   static final CraftMetaItem.ItemMetaKey LODESTONE_POS_Z = new CraftMetaItem.ItemMetaKey("LodestonePosZ");
   static final CraftMetaItem.ItemMetaKey LODESTONE_TRACKED = new CraftMetaItem.ItemMetaKey("LodestoneTracked");
   private NBTTagString lodestoneWorld;
   private int lodestoneX;
   private int lodestoneY;
   private int lodestoneZ;
   private Boolean tracked;

   CraftMetaCompass(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaCompass) {
         CraftMetaCompass compassMeta = (CraftMetaCompass)meta;
         this.lodestoneWorld = compassMeta.lodestoneWorld;
         this.lodestoneX = compassMeta.lodestoneX;
         this.lodestoneY = compassMeta.lodestoneY;
         this.lodestoneZ = compassMeta.lodestoneZ;
         this.tracked = compassMeta.tracked;
      }
   }

   CraftMetaCompass(NBTTagCompound tag) {
      super(tag);
      if (tag.e(LODESTONE_DIMENSION.NBT) && tag.e(LODESTONE_POS.NBT)) {
         this.lodestoneWorld = (NBTTagString)tag.c(LODESTONE_DIMENSION.NBT);
         NBTTagCompound pos = tag.p(LODESTONE_POS.NBT);
         this.lodestoneX = pos.h("X");
         this.lodestoneY = pos.h("Y");
         this.lodestoneZ = pos.h("Z");
      }

      if (tag.e(LODESTONE_TRACKED.NBT)) {
         this.tracked = tag.q(LODESTONE_TRACKED.NBT);
      }
   }

   CraftMetaCompass(Map<String, Object> map) {
      super(map);
      String lodestoneWorldString = CraftMetaItem.SerializableMeta.getString(map, LODESTONE_POS_WORLD.BUKKIT, true);
      if (lodestoneWorldString != null) {
         this.lodestoneWorld = NBTTagString.a(lodestoneWorldString);
         this.lodestoneX = map.get(LODESTONE_POS_X.BUKKIT);
         this.lodestoneY = map.get(LODESTONE_POS_Y.BUKKIT);
         this.lodestoneZ = map.get(LODESTONE_POS_Z.BUKKIT);
      } else {
         Location lodestone = CraftMetaItem.SerializableMeta.getObject(Location.class, map, LODESTONE_POS.BUKKIT, true);
         if (lodestone != null && lodestone.getWorld() != null) {
            this.setLodestone(lodestone);
         }
      }

      this.tracked = CraftMetaItem.SerializableMeta.getBoolean(map, LODESTONE_TRACKED.BUKKIT);
   }

   @Override
   void applyToItem(NBTTagCompound tag) {
      super.applyToItem(tag);
      if (this.lodestoneWorld != null) {
         NBTTagCompound pos = new NBTTagCompound();
         pos.a("X", this.lodestoneX);
         pos.a("Y", this.lodestoneY);
         pos.a("Z", this.lodestoneZ);
         tag.a(LODESTONE_POS.NBT, pos);
         tag.a(LODESTONE_DIMENSION.NBT, this.lodestoneWorld);
      }

      if (this.tracked != null) {
         tag.a(LODESTONE_TRACKED.NBT, this.tracked);
      }
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.isCompassEmpty();
   }

   boolean isCompassEmpty() {
      return !this.hasLodestone() && !this.hasLodestoneTracked();
   }

   @Override
   boolean applicableTo(Material type) {
      return type == Material.COMPASS;
   }

   public CraftMetaCompass clone() {
      return (CraftMetaCompass)super.clone();
   }

   public boolean hasLodestone() {
      return this.lodestoneWorld != null;
   }

   public Location getLodestone() {
      if (this.lodestoneWorld == null) {
         return null;
      } else {
         Optional<ResourceKey<World>> key = World.g.parse(DynamicOpsNBT.a, this.lodestoneWorld).result();
         WorldServer worldServer = key.isPresent() ? MinecraftServer.getServer().a(key.get()) : null;
         org.bukkit.World world = worldServer != null ? worldServer.getWorld() : null;
         return new Location(world, (double)this.lodestoneX, (double)this.lodestoneY, (double)this.lodestoneZ);
      }
   }

   public void setLodestone(Location lodestone) {
      Preconditions.checkArgument(lodestone == null || lodestone.getWorld() != null, "world is null");
      if (lodestone == null) {
         this.lodestoneWorld = null;
      } else {
         ResourceKey<World> key = ((CraftWorld)lodestone.getWorld()).getHandle().ab();
         DataResult<NBTBase> dataresult = World.g.encodeStart(DynamicOpsNBT.a, key);
         this.lodestoneWorld = (NBTTagString)dataresult.get().orThrow();
         this.lodestoneX = lodestone.getBlockX();
         this.lodestoneY = lodestone.getBlockY();
         this.lodestoneZ = lodestone.getBlockZ();
      }
   }

   boolean hasLodestoneTracked() {
      return this.tracked != null;
   }

   public boolean isLodestoneTracked() {
      return this.hasLodestoneTracked() && this.tracked;
   }

   public void setLodestoneTracked(boolean tracked) {
      this.tracked = tracked;
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.hasLodestone()) {
         hash = 73 * hash + this.lodestoneWorld.hashCode();
         hash = 73 * hash + this.lodestoneX;
         hash = 73 * hash + this.lodestoneY;
         hash = 73 * hash + this.lodestoneZ;
      }

      if (this.hasLodestoneTracked()) {
         hash = 73 * hash + (this.isLodestoneTracked() ? 1231 : 1237);
      }

      return original != hash ? CraftMetaCompass.class.hashCode() ^ hash : hash;
   }

   @Override
   boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (!(meta instanceof CraftMetaCompass)) {
         return true;
      } else {
         CraftMetaCompass that = (CraftMetaCompass)meta;
         return (
               this.hasLodestone()
                  ? that.hasLodestone()
                     && this.lodestoneWorld.f_().equals(that.lodestoneWorld.f_())
                     && this.lodestoneX == that.lodestoneX
                     && this.lodestoneY == that.lodestoneY
                     && this.lodestoneZ == that.lodestoneZ
                  : !that.hasLodestone()
            )
            && this.tracked == that.tracked;
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaCompass || this.isCompassEmpty());
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      if (this.hasLodestone()) {
         builder.put(LODESTONE_POS_WORLD.BUKKIT, this.lodestoneWorld.f_());
         builder.put(LODESTONE_POS_X.BUKKIT, this.lodestoneX);
         builder.put(LODESTONE_POS_Y.BUKKIT, this.lodestoneY);
         builder.put(LODESTONE_POS_Z.BUKKIT, this.lodestoneZ);
      }

      if (this.hasLodestoneTracked()) {
         builder.put(LODESTONE_TRACKED.BUKKIT, this.tracked);
      }

      return builder;
   }
}
