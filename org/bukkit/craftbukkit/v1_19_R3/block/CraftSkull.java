package org.bukkit.craftbukkit.v1_19_R3.block;

import com.google.common.base.Preconditions;
import com.mojang.authlib.GameProfile;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.entity.TileEntitySkull;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.profile.CraftPlayerProfile;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.Nullable;

public class CraftSkull extends CraftBlockEntityState<TileEntitySkull> implements Skull {
   private static final int MAX_OWNER_LENGTH = 16;
   private GameProfile profile;

   public CraftSkull(World world, TileEntitySkull tileEntity) {
      super(world, tileEntity);
   }

   public void load(TileEntitySkull skull) {
      super.load(skull);
      this.profile = skull.f;
   }

   static int getSkullType(SkullType type) {
      switch(type) {
         case SKELETON:
         default:
            return 0;
         case WITHER:
            return 1;
         case ZOMBIE:
            return 2;
         case PLAYER:
            return 3;
         case CREEPER:
            return 4;
         case DRAGON:
            return 5;
      }
   }

   public boolean hasOwner() {
      return this.profile != null;
   }

   public String getOwner() {
      return this.hasOwner() ? this.profile.getName() : null;
   }

   public boolean setOwner(String name) {
      if (name != null && name.length() <= 16) {
         GameProfile profile = (GameProfile)MinecraftServer.getServer().ap().a(name).orElse(null);
         if (profile == null) {
            return false;
         } else {
            this.profile = profile;
            return true;
         }
      } else {
         return false;
      }
   }

   public OfflinePlayer getOwningPlayer() {
      if (this.profile != null) {
         if (this.profile.getId() != null) {
            return Bukkit.getOfflinePlayer(this.profile.getId());
         }

         if (this.profile.getName() != null) {
            return Bukkit.getOfflinePlayer(this.profile.getName());
         }
      }

      return null;
   }

   public void setOwningPlayer(OfflinePlayer player) {
      Preconditions.checkNotNull(player, "player");
      if (player instanceof CraftPlayer) {
         this.profile = ((CraftPlayer)player).getProfile();
      } else {
         this.profile = new GameProfile(player.getUniqueId(), player.getName());
      }
   }

   public PlayerProfile getOwnerProfile() {
      return !this.hasOwner() ? null : new CraftPlayerProfile(this.profile);
   }

   public void setOwnerProfile(PlayerProfile profile) {
      if (profile == null) {
         this.profile = null;
      } else {
         this.profile = CraftPlayerProfile.validateSkullProfile(((CraftPlayerProfile)profile).buildGameProfile());
      }
   }

   public NamespacedKey getNoteBlockSound() {
      MinecraftKey key = this.getSnapshot().f();
      return key != null ? CraftNamespacedKey.fromMinecraft(key) : null;
   }

   public void setNoteBlockSound(@Nullable NamespacedKey namespacedKey) {
      if (namespacedKey == null) {
         this.getSnapshot().g = null;
      } else {
         this.getSnapshot().g = CraftNamespacedKey.toMinecraft(namespacedKey);
      }
   }

   public BlockFace getRotation() {
      BlockData blockData = this.getBlockData();
      return blockData instanceof Rotatable ? ((Rotatable)blockData).getRotation() : ((Directional)blockData).getFacing();
   }

   public void setRotation(BlockFace rotation) {
      BlockData blockData = this.getBlockData();
      if (blockData instanceof Rotatable) {
         ((Rotatable)blockData).setRotation(rotation);
      } else {
         ((Directional)blockData).setFacing(rotation);
      }

      this.setBlockData(blockData);
   }

   public SkullType getSkullType() {
      switch($SWITCH_TABLE$org$bukkit$Material()[this.getType().ordinal()]) {
         case 1054:
         case 1300:
            return SkullType.SKELETON;
         case 1055:
         case 1301:
            return SkullType.WITHER;
         case 1056:
         case 1303:
            return SkullType.PLAYER;
         case 1057:
         case 1302:
            return SkullType.ZOMBIE;
         case 1058:
         case 1304:
            return SkullType.CREEPER;
         case 1059:
         case 1305:
            return SkullType.DRAGON;
         case 1060:
         case 1306:
            return SkullType.PIGLIN;
         default:
            throw new IllegalArgumentException("Unknown SkullType for " + this.getType());
      }
   }

   public void setSkullType(SkullType skullType) {
      throw new UnsupportedOperationException("Must change block type");
   }

   public void applyTo(TileEntitySkull skull) {
      super.applyTo(skull);
      if (this.getSkullType() == SkullType.PLAYER) {
         skull.a(this.profile);
      }
   }
}
