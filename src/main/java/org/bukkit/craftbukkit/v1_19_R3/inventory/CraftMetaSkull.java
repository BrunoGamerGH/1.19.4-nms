package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.authlib.GameProfile;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.block.entity.TileEntitySkull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.profile.CraftPlayerProfile;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaSkull extends CraftMetaItem implements SkullMeta {
   private static final Set<Material> SKULL_MATERIALS = Sets.newHashSet(
      new Material[]{
         Material.CREEPER_HEAD,
         Material.CREEPER_WALL_HEAD,
         Material.DRAGON_HEAD,
         Material.DRAGON_WALL_HEAD,
         Material.PIGLIN_HEAD,
         Material.PIGLIN_WALL_HEAD,
         Material.PLAYER_HEAD,
         Material.PLAYER_WALL_HEAD,
         Material.SKELETON_SKULL,
         Material.SKELETON_WALL_SKULL,
         Material.WITHER_SKELETON_SKULL,
         Material.WITHER_SKELETON_WALL_SKULL,
         Material.ZOMBIE_HEAD,
         Material.ZOMBIE_WALL_HEAD
      }
   );
   static final CraftMetaItem.ItemMetaKey SKULL_PROFILE = new CraftMetaItem.ItemMetaKey("SkullProfile");
   static final CraftMetaItem.ItemMetaKey SKULL_OWNER = new CraftMetaItem.ItemMetaKey("SkullOwner", "skull-owner");
   static final CraftMetaItem.ItemMetaKey BLOCK_ENTITY_TAG = new CraftMetaItem.ItemMetaKey("BlockEntityTag");
   static final CraftMetaItem.ItemMetaKey NOTE_BLOCK_SOUND = new CraftMetaItem.ItemMetaKey("note_block_sound");
   static final int MAX_OWNER_LENGTH = 16;
   private GameProfile profile;
   private NBTTagCompound serializedProfile;
   private MinecraftKey noteBlockSound;

   CraftMetaSkull(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaSkull) {
         CraftMetaSkull skullMeta = (CraftMetaSkull)meta;
         this.setProfile(skullMeta.profile);
         this.noteBlockSound = skullMeta.noteBlockSound;
      }
   }

   CraftMetaSkull(NBTTagCompound tag) {
      super(tag);
      if (tag.b(SKULL_OWNER.NBT, 10)) {
         this.setProfile(GameProfileSerializer.a(tag.p(SKULL_OWNER.NBT)));
      } else if (tag.b(SKULL_OWNER.NBT, 8) && !tag.l(SKULL_OWNER.NBT).isEmpty()) {
         this.setProfile(new GameProfile(null, tag.l(SKULL_OWNER.NBT)));
      }

      if (tag.b(BLOCK_ENTITY_TAG.NBT, 10)) {
         NBTTagCompound nbtTagCompound = tag.p(BLOCK_ENTITY_TAG.NBT).h();
         if (nbtTagCompound.b(NOTE_BLOCK_SOUND.NBT, 8)) {
            this.noteBlockSound = MinecraftKey.a(nbtTagCompound.l(NOTE_BLOCK_SOUND.NBT));
         }
      }
   }

   CraftMetaSkull(Map<String, Object> map) {
      super(map);
      if (this.profile == null) {
         Object object = map.get(SKULL_OWNER.BUKKIT);
         if (object instanceof PlayerProfile) {
            this.setOwnerProfile((PlayerProfile)object);
         } else {
            this.setOwner(CraftMetaItem.SerializableMeta.getString(map, SKULL_OWNER.BUKKIT, true));
         }
      }

      if (this.noteBlockSound == null) {
         Object object = map.get(NOTE_BLOCK_SOUND.BUKKIT);
         if (object instanceof NamespacedKey) {
            this.setNoteBlockSound((NamespacedKey)object);
         } else {
            this.setNoteBlockSound(CraftMetaItem.SerializableMeta.getObject(NamespacedKey.class, map, NOTE_BLOCK_SOUND.BUKKIT, true));
         }
      }
   }

   @Override
   void deserializeInternal(NBTTagCompound tag, Object context) {
      super.deserializeInternal(tag, context);
      if (tag.b(SKULL_PROFILE.NBT, 10)) {
         NBTTagCompound skullTag = tag.p(SKULL_PROFILE.NBT);
         if (skullTag.b("Id", 8)) {
            UUID uuid = UUID.fromString(skullTag.l("Id"));
            skullTag.a("Id", uuid);
         }

         this.setProfile(GameProfileSerializer.a(skullTag));
      }

      if (tag.b(BLOCK_ENTITY_TAG.NBT, 10)) {
         NBTTagCompound nbtTagCompound = tag.p(BLOCK_ENTITY_TAG.NBT).h();
         if (nbtTagCompound.b(NOTE_BLOCK_SOUND.NBT, 8)) {
            this.noteBlockSound = MinecraftKey.a(nbtTagCompound.l(NOTE_BLOCK_SOUND.NBT));
         }
      }
   }

   private void setProfile(GameProfile profile) {
      this.profile = profile;
      this.serializedProfile = profile == null ? null : GameProfileSerializer.a(new NBTTagCompound(), profile);
   }

   @Override
   void applyToItem(NBTTagCompound tag) {
      super.applyToItem(tag);
      if (this.profile != null) {
         tag.a(SKULL_OWNER.NBT, this.serializedProfile);
         TileEntitySkull.a(this.profile, filledProfile -> {
            this.setProfile(filledProfile);
            tag.a(SKULL_OWNER.NBT, this.serializedProfile);
         });
      }

      if (this.noteBlockSound != null) {
         NBTTagCompound nbtTagCompound = new NBTTagCompound();
         nbtTagCompound.a(NOTE_BLOCK_SOUND.NBT, this.noteBlockSound.toString());
         tag.a(BLOCK_ENTITY_TAG.NBT, nbtTagCompound);
      }
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.isSkullEmpty();
   }

   boolean isSkullEmpty() {
      return this.profile == null && this.noteBlockSound == null;
   }

   @Override
   boolean applicableTo(Material type) {
      return SKULL_MATERIALS.contains(type);
   }

   public CraftMetaSkull clone() {
      return (CraftMetaSkull)super.clone();
   }

   public boolean hasOwner() {
      return this.profile != null && this.profile.getName() != null;
   }

   public String getOwner() {
      return this.hasOwner() ? this.profile.getName() : null;
   }

   public OfflinePlayer getOwningPlayer() {
      if (this.hasOwner()) {
         if (this.profile.getId() != null) {
            return Bukkit.getOfflinePlayer(this.profile.getId());
         }

         if (this.profile.getName() != null) {
            return Bukkit.getOfflinePlayer(this.profile.getName());
         }
      }

      return null;
   }

   public boolean setOwner(String name) {
      if (name != null && name.length() > 16) {
         return false;
      } else {
         if (name == null) {
            this.setProfile(null);
         } else {
            this.setProfile(new GameProfile(null, name));
         }

         return true;
      }
   }

   public boolean setOwningPlayer(OfflinePlayer owner) {
      if (owner == null) {
         this.setProfile(null);
      } else if (owner instanceof CraftPlayer) {
         this.setProfile(((CraftPlayer)owner).getProfile());
      } else {
         this.setProfile(new GameProfile(owner.getUniqueId(), owner.getName()));
      }

      return true;
   }

   public PlayerProfile getOwnerProfile() {
      return !this.hasOwner() ? null : new CraftPlayerProfile(this.profile);
   }

   public void setOwnerProfile(PlayerProfile profile) {
      if (profile == null) {
         this.setProfile(null);
      } else {
         this.setProfile(CraftPlayerProfile.validateSkullProfile(((CraftPlayerProfile)profile).buildGameProfile()));
      }
   }

   public void setNoteBlockSound(NamespacedKey noteBlockSound) {
      if (noteBlockSound == null) {
         this.noteBlockSound = null;
      } else {
         this.noteBlockSound = CraftNamespacedKey.toMinecraft(noteBlockSound);
      }
   }

   public NamespacedKey getNoteBlockSound() {
      return this.noteBlockSound == null ? null : CraftNamespacedKey.fromMinecraft(this.noteBlockSound);
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.hasOwner()) {
         hash = 61 * hash + this.profile.hashCode();
      }

      if (this.noteBlockSound != null) {
         hash = 61 * hash + this.noteBlockSound.hashCode();
      }

      return original != hash ? CraftMetaSkull.class.hashCode() ^ hash : hash;
   }

   @Override
   boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (!(meta instanceof CraftMetaSkull)) {
         return true;
      } else {
         CraftMetaSkull that = (CraftMetaSkull)meta;
         return (this.profile != null ? that.profile != null && this.serializedProfile.equals(that.serializedProfile) : that.profile == null)
            && Objects.equals(this.noteBlockSound, that.noteBlockSound);
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaSkull || this.isSkullEmpty());
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      if (this.profile != null) {
         return builder.put(SKULL_OWNER.BUKKIT, new CraftPlayerProfile(this.profile));
      } else {
         NamespacedKey namespacedKeyNB = this.getNoteBlockSound();
         return namespacedKeyNB != null ? builder.put(NOTE_BLOCK_SOUND.BUKKIT, namespacedKeyNB) : builder;
      }
   }
}
