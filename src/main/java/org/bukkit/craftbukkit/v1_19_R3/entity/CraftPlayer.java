package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.io.BaseEncoding;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.shorts.ShortArraySet;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.PacketPlayOutBlockBreakAnimation;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.network.protocol.game.PacketPlayOutCustomPayload;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment;
import net.minecraft.network.protocol.game.PacketPlayOutEntitySound;
import net.minecraft.network.protocol.game.PacketPlayOutExperience;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.network.protocol.game.PacketPlayOutMap;
import net.minecraft.network.protocol.game.PacketPlayOutMultiBlockChange;
import net.minecraft.network.protocol.game.PacketPlayOutNamedSoundEffect;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnPosition;
import net.minecraft.network.protocol.game.PacketPlayOutStopSound;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateAttributes;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateHealth;
import net.minecraft.network.protocol.game.PacketPlayOutWorldEvent;
import net.minecraft.network.protocol.game.PacketPlayOutWorldParticles;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.AdvancementDataPlayer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerChunkMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.server.players.WhiteListEntry;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntitySign;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.border.IWorldBorderListener;
import net.minecraft.world.level.saveddata.maps.MapIcon;
import net.minecraft.world.level.saveddata.maps.WorldMap;
import net.minecraft.world.phys.Vec3D;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Note;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.WeatherType;
import org.bukkit.WorldBorder;
import org.bukkit.BanList.Type;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ManuallyAbandonedConversationCanceller;
import org.bukkit.craftbukkit.v1_19_R3.CraftEffect;
import org.bukkit.craftbukkit.v1_19_R3.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_19_R3.CraftOfflinePlayer;
import org.bukkit.craftbukkit.v1_19_R3.CraftParticle;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftSound;
import org.bukkit.craftbukkit.v1_19_R3.CraftStatistic;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorldBorder;
import org.bukkit.craftbukkit.v1_19_R3.advancement.CraftAdvancement;
import org.bukkit.craftbukkit.v1_19_R3.advancement.CraftAdvancementProgress;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftSign;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.conversations.ConversationTracker;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.map.CraftMapView;
import org.bukkit.craftbukkit.v1_19_R3.map.RenderData;
import org.bukkit.craftbukkit.v1_19_R3.profile.CraftPlayerProfile;
import org.bukkit.craftbukkit.v1_19_R3.scoreboard.CraftScoreboard;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Player.Spigot;
import org.bukkit.event.player.PlayerHideEntityEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerShowEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.scoreboard.Scoreboard;
import org.spigotmc.AsyncCatcher;

@DelegateDeserialization(CraftOfflinePlayer.class)
public class CraftPlayer extends CraftHumanEntity implements Player {
   private long firstPlayed = 0L;
   private long lastPlayed = 0L;
   private boolean hasPlayedBefore = false;
   private final ConversationTracker conversationTracker = new ConversationTracker();
   private final Set<String> channels = new HashSet<>();
   private final Map<UUID, Set<WeakReference<Plugin>>> invertedVisibilityEntities = new HashMap<>();
   private static final WeakHashMap<Plugin, WeakReference<Plugin>> pluginWeakReferences = new WeakHashMap<>();
   private int hash = 0;
   private double health = 20.0;
   private boolean scaledHealth = false;
   private double healthScale = 20.0;
   private CraftWorldBorder clientWorldBorder = null;
   private IWorldBorderListener clientWorldBorderListener = this.createWorldBorderListener();
   private IChatBaseComponent playerListHeader;
   private IChatBaseComponent playerListFooter;
   private final Spigot spigot = new Spigot() {
      public InetSocketAddress getRawAddress() {
         return (InetSocketAddress)CraftPlayer.this.getHandle().b.getRawAddress();
      }

      public boolean getCollidesWithEntities() {
         return CraftPlayer.this.isCollidable();
      }

      public void setCollidesWithEntities(boolean collides) {
         CraftPlayer.this.setCollidable(collides);
      }

      public void respawn() {
         if (CraftPlayer.this.getHealth() <= 0.0 && CraftPlayer.this.isOnline()) {
            CraftPlayer.this.server.getServer().bi().a(CraftPlayer.this.getHandle(), false);
         }
      }

      public Set<Player> getHiddenPlayers() {
         Set<Player> ret = new HashSet();

         for(Player p : CraftPlayer.this.getServer().getOnlinePlayers()) {
            if (!CraftPlayer.this.canSee(p)) {
               ret.add(p);
            }
         }

         return Collections.unmodifiableSet(ret);
      }

      public void sendMessage(BaseComponent component) {
         this.sendMessage(component);
      }

      public void sendMessage(BaseComponent... components) {
         this.sendMessage(ChatMessageType.SYSTEM, components);
      }

      public void sendMessage(UUID sender, BaseComponent component) {
         this.sendMessage(ChatMessageType.CHAT, sender, component);
      }

      public void sendMessage(UUID sender, BaseComponent... components) {
         this.sendMessage(ChatMessageType.CHAT, sender, components);
      }

      public void sendMessage(ChatMessageType position, BaseComponent component) {
         this.sendMessage(position, component);
      }

      public void sendMessage(ChatMessageType position, BaseComponent... components) {
         this.sendMessage(position, null, components);
      }

      public void sendMessage(ChatMessageType position, UUID sender, BaseComponent component) {
         this.sendMessage(position, sender, component);
      }

      public void sendMessage(ChatMessageType position, UUID sender, BaseComponent... components) {
         if (CraftPlayer.this.getHandle().b != null) {
            CraftPlayer.this.getHandle().b.a(new ClientboundSystemChatPacket(components, position == ChatMessageType.ACTION_BAR));
         }
      }
   };

   public CraftPlayer(CraftServer server, EntityPlayer entity) {
      super(server, entity);
      this.firstPlayed = System.currentTimeMillis();
   }

   public GameProfile getProfile() {
      return this.getHandle().fI();
   }

   @Override
   public boolean isOp() {
      return this.server.getHandle().f(this.getProfile());
   }

   @Override
   public void setOp(boolean value) {
      if (value != this.isOp()) {
         if (value) {
            this.server.getHandle().a(this.getProfile());
         } else {
            this.server.getHandle().b(this.getProfile());
         }

         this.perm.recalculatePermissions();
      }
   }

   public boolean isOnline() {
      return this.server.getPlayer(this.getUniqueId()) != null;
   }

   public PlayerProfile getPlayerProfile() {
      return new CraftPlayerProfile(this.getProfile());
   }

   public InetSocketAddress getAddress() {
      if (this.getHandle().b == null) {
         return null;
      } else {
         SocketAddress addr = this.getHandle().b.e();
         return addr instanceof InetSocketAddress ? (InetSocketAddress)addr : null;
      }
   }

   @Override
   public double getEyeHeight(boolean ignorePose) {
      return ignorePose ? 1.62 : this.getEyeHeight();
   }

   public void sendRawMessage(String message) {
      if (this.getHandle().b != null) {
         IChatBaseComponent[] var5;
         for(IChatBaseComponent component : var5 = CraftChatMessage.fromString(message)) {
            this.getHandle().a(component);
         }
      }
   }

   public void sendRawMessage(UUID sender, String message) {
      if (this.getHandle().b != null) {
         IChatBaseComponent[] var6;
         for(IChatBaseComponent component : var6 = CraftChatMessage.fromString(message)) {
            this.getHandle().a(component);
         }
      }
   }

   @Override
   public void sendMessage(String message) {
      if (!this.conversationTracker.isConversingModaly()) {
         this.sendRawMessage(message);
      }
   }

   @Override
   public void sendMessage(String... messages) {
      for(String message : messages) {
         this.sendMessage(message);
      }
   }

   @Override
   public void sendMessage(UUID sender, String message) {
      if (!this.conversationTracker.isConversingModaly()) {
         this.sendRawMessage(sender, message);
      }
   }

   @Override
   public void sendMessage(UUID sender, String... messages) {
      for(String message : messages) {
         this.sendMessage(sender, message);
      }
   }

   public String getDisplayName() {
      return this.getHandle().displayName;
   }

   public void setDisplayName(String name) {
      this.getHandle().displayName = name == null ? this.getName() : name;
   }

   public String getPlayerListName() {
      return this.getHandle().listName == null ? this.getName() : CraftChatMessage.fromComponent(this.getHandle().listName);
   }

   public void setPlayerListName(String name) {
      if (name == null) {
         name = this.getName();
      }

      this.getHandle().listName = name.equals(this.getName()) ? null : CraftChatMessage.fromStringOrNull(name);

      for(EntityPlayer player : this.server.getHandle().k) {
         if (player.getBukkitEntity().canSee((Player)this)) {
            player.b.a(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.a.f, this.getHandle()));
         }
      }
   }

   public String getPlayerListHeader() {
      return this.playerListHeader == null ? null : CraftChatMessage.fromComponent(this.playerListHeader);
   }

   public String getPlayerListFooter() {
      return this.playerListFooter == null ? null : CraftChatMessage.fromComponent(this.playerListFooter);
   }

   public void setPlayerListHeader(String header) {
      this.playerListHeader = CraftChatMessage.fromStringOrNull(header, true);
      this.updatePlayerListHeaderFooter();
   }

   public void setPlayerListFooter(String footer) {
      this.playerListFooter = CraftChatMessage.fromStringOrNull(footer, true);
      this.updatePlayerListHeaderFooter();
   }

   public void setPlayerListHeaderFooter(String header, String footer) {
      this.playerListHeader = CraftChatMessage.fromStringOrNull(header, true);
      this.playerListFooter = CraftChatMessage.fromStringOrNull(footer, true);
      this.updatePlayerListHeaderFooter();
   }

   private void updatePlayerListHeaderFooter() {
      if (this.getHandle().b != null) {
         PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(
            (IChatBaseComponent)(this.playerListHeader == null ? IChatBaseComponent.h() : this.playerListHeader),
            (IChatBaseComponent)(this.playerListFooter == null ? IChatBaseComponent.h() : this.playerListFooter)
         );
         this.getHandle().b.a(packet);
      }
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof OfflinePlayer)) {
         return false;
      } else {
         OfflinePlayer other = (OfflinePlayer)obj;
         if (this.getUniqueId() != null && other.getUniqueId() != null) {
            boolean uuidEquals = this.getUniqueId().equals(other.getUniqueId());
            boolean idEquals = true;
            if (other instanceof CraftPlayer) {
               idEquals = this.getEntityId() == ((CraftPlayer)other).getEntityId();
            }

            return uuidEquals && idEquals;
         } else {
            return false;
         }
      }
   }

   public void kickPlayer(String message) {
      AsyncCatcher.catchOp("player kick");
      if (this.getHandle().b != null) {
         this.getHandle().b.disconnect(message == null ? "" : message);
      }
   }

   public void setCompassTarget(Location loc) {
      if (this.getHandle().b != null) {
         this.getHandle().b.a(new PacketPlayOutSpawnPosition(new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), loc.getYaw()));
      }
   }

   public Location getCompassTarget() {
      return this.getHandle().compassTarget;
   }

   public void chat(String msg) {
      if (this.getHandle().b != null) {
         this.getHandle().b.chat(msg, PlayerChatMessage.a(msg), false);
      }
   }

   public boolean performCommand(String command) {
      return this.server.dispatchCommand(this, command);
   }

   public void playNote(Location loc, byte instrument, byte note) {
      if (this.getHandle().b != null) {
         String instrumentName = null;
         switch(instrument) {
            case 0:
               instrumentName = "harp";
               break;
            case 1:
               instrumentName = "basedrum";
               break;
            case 2:
               instrumentName = "snare";
               break;
            case 3:
               instrumentName = "hat";
               break;
            case 4:
               instrumentName = "bass";
               break;
            case 5:
               instrumentName = "flute";
               break;
            case 6:
               instrumentName = "bell";
               break;
            case 7:
               instrumentName = "guitar";
               break;
            case 8:
               instrumentName = "chime";
               break;
            case 9:
               instrumentName = "xylophone";
         }

         float f = (float)Math.pow(2.0, ((double)note - 12.0) / 12.0);
         this.getHandle()
            .b
            .a(
               new PacketPlayOutNamedSoundEffect(
                  BuiltInRegistries.c.d(CraftSound.getSoundEffect("block.note_block." + instrumentName)),
                  SoundCategory.c,
                  (double)loc.getBlockX(),
                  (double)loc.getBlockY(),
                  (double)loc.getBlockZ(),
                  3.0F,
                  f,
                  this.getHandle().dZ().g()
               )
            );
      }
   }

   public void playNote(Location loc, Instrument instrument, Note note) {
      if (this.getHandle().b != null) {
         String instrumentName = null;
         switch(instrument.ordinal()) {
            case 0:
               instrumentName = "harp";
               break;
            case 1:
               instrumentName = "basedrum";
               break;
            case 2:
               instrumentName = "snare";
               break;
            case 3:
               instrumentName = "hat";
               break;
            case 4:
               instrumentName = "bass";
               break;
            case 5:
               instrumentName = "flute";
               break;
            case 6:
               instrumentName = "bell";
               break;
            case 7:
               instrumentName = "guitar";
               break;
            case 8:
               instrumentName = "chime";
               break;
            case 9:
               instrumentName = "xylophone";
               break;
            case 10:
               instrumentName = "iron_xylophone";
               break;
            case 11:
               instrumentName = "cow_bell";
               break;
            case 12:
               instrumentName = "didgeridoo";
               break;
            case 13:
               instrumentName = "bit";
               break;
            case 14:
               instrumentName = "banjo";
               break;
            case 15:
               instrumentName = "pling";
               break;
            case 16:
               instrumentName = "xylophone";
         }

         float f = (float)Math.pow(2.0, ((double)note.getId() - 12.0) / 12.0);
         this.getHandle()
            .b
            .a(
               new PacketPlayOutNamedSoundEffect(
                  BuiltInRegistries.c.d(CraftSound.getSoundEffect("block.note_block." + instrumentName)),
                  SoundCategory.c,
                  (double)loc.getBlockX(),
                  (double)loc.getBlockY(),
                  (double)loc.getBlockZ(),
                  3.0F,
                  f,
                  this.getHandle().dZ().g()
               )
            );
      }
   }

   public void playSound(Location loc, Sound sound, float volume, float pitch) {
      this.playSound(loc, sound, org.bukkit.SoundCategory.MASTER, volume, pitch);
   }

   public void playSound(Location loc, String sound, float volume, float pitch) {
      this.playSound(loc, sound, org.bukkit.SoundCategory.MASTER, volume, pitch);
   }

   public void playSound(Location loc, Sound sound, org.bukkit.SoundCategory category, float volume, float pitch) {
      if (loc != null && sound != null && category != null && this.getHandle().b != null) {
         PacketPlayOutNamedSoundEffect packet = new PacketPlayOutNamedSoundEffect(
            BuiltInRegistries.c.d(CraftSound.getSoundEffect(sound)),
            SoundCategory.valueOf(category.name()),
            loc.getX(),
            loc.getY(),
            loc.getZ(),
            volume,
            pitch,
            this.getHandle().dZ().g()
         );
         this.getHandle().b.a(packet);
      }
   }

   public void playSound(Location loc, String sound, org.bukkit.SoundCategory category, float volume, float pitch) {
      if (loc != null && sound != null && category != null && this.getHandle().b != null) {
         PacketPlayOutNamedSoundEffect packet = new PacketPlayOutNamedSoundEffect(
            Holder.a(SoundEffect.a(new MinecraftKey(sound))),
            SoundCategory.valueOf(category.name()),
            loc.getX(),
            loc.getY(),
            loc.getZ(),
            volume,
            pitch,
            this.getHandle().dZ().g()
         );
         this.getHandle().b.a(packet);
      }
   }

   public void playSound(Entity entity, Sound sound, float volume, float pitch) {
      this.playSound(entity, sound, org.bukkit.SoundCategory.MASTER, volume, pitch);
   }

   public void playSound(Entity entity, String sound, float volume, float pitch) {
      this.playSound(entity, sound, org.bukkit.SoundCategory.MASTER, volume, pitch);
   }

   public void playSound(Entity entity, Sound sound, org.bukkit.SoundCategory category, float volume, float pitch) {
      CraftEntity craftEntity;
      if (entity instanceof CraftEntity
         && (craftEntity = (CraftEntity)entity) == (CraftEntity)entity
         && sound != null
         && category != null
         && this.getHandle().b != null) {
         PacketPlayOutEntitySound packet = new PacketPlayOutEntitySound(
            BuiltInRegistries.c.d(CraftSound.getSoundEffect(sound)),
            SoundCategory.valueOf(category.name()),
            craftEntity.getHandle(),
            volume,
            pitch,
            this.getHandle().dZ().g()
         );
         this.getHandle().b.a(packet);
      }
   }

   public void playSound(Entity entity, String sound, org.bukkit.SoundCategory category, float volume, float pitch) {
      CraftEntity craftEntity;
      if (entity instanceof CraftEntity
         && (craftEntity = (CraftEntity)entity) == (CraftEntity)entity
         && sound != null
         && category != null
         && this.getHandle().b != null) {
         PacketPlayOutEntitySound packet = new PacketPlayOutEntitySound(
            Holder.a(SoundEffect.a(new MinecraftKey(sound))),
            SoundCategory.valueOf(category.name()),
            craftEntity.getHandle(),
            volume,
            pitch,
            this.getHandle().dZ().g()
         );
         this.getHandle().b.a(packet);
      }
   }

   public void stopSound(Sound sound) {
      this.stopSound(sound, null);
   }

   public void stopSound(String sound) {
      this.stopSound(sound, null);
   }

   public void stopSound(Sound sound, org.bukkit.SoundCategory category) {
      this.stopSound(sound.getKey().getKey(), category);
   }

   public void stopSound(String sound, org.bukkit.SoundCategory category) {
      if (this.getHandle().b != null) {
         this.getHandle()
            .b
            .a(new PacketPlayOutStopSound(new MinecraftKey(sound), category == null ? SoundCategory.a : SoundCategory.valueOf(category.name())));
      }
   }

   public void stopSound(org.bukkit.SoundCategory category) {
      if (this.getHandle().b != null) {
         this.getHandle().b.a(new PacketPlayOutStopSound(null, SoundCategory.valueOf(category.name())));
      }
   }

   public void stopAllSounds() {
      if (this.getHandle().b != null) {
         this.getHandle().b.a(new PacketPlayOutStopSound(null, null));
      }
   }

   public void playEffect(Location loc, Effect effect, int data) {
      if (this.getHandle().b != null) {
         int packetData = effect.getId();
         PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(
            packetData, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), data, false
         );
         this.getHandle().b.a(packet);
      }
   }

   public <T> void playEffect(Location loc, Effect effect, T data) {
      if (data != null) {
         Validate.isTrue(effect.getData() != null && effect.getData().isAssignableFrom(data.getClass()), "Wrong kind of data for this effect!");
      } else {
         Validate.isTrue(effect.getData() == null || effect == Effect.ELECTRIC_SPARK, "Wrong kind of data for this effect!");
      }

      int datavalue = CraftEffect.getDataValue(effect, data);
      this.playEffect(loc, effect, datavalue);
   }

   public boolean breakBlock(Block block) {
      Preconditions.checkArgument(block != null, "Block cannot be null");
      Preconditions.checkArgument(block.getWorld().equals(this.getWorld()), "Cannot break blocks across worlds");
      return this.getHandle().d.a(new BlockPosition(block.getX(), block.getY(), block.getZ()));
   }

   public void sendBlockChange(Location loc, Material material, byte data) {
      if (this.getHandle().b != null) {
         PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(
            new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), CraftMagicNumbers.getBlock(material, data)
         );
         this.getHandle().b.a(packet);
      }
   }

   public void sendBlockChange(Location loc, BlockData block) {
      if (this.getHandle().b != null) {
         PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(
            new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), ((CraftBlockData)block).getState()
         );
         this.getHandle().b.a(packet);
      }
   }

   public void sendBlockChanges(Collection<BlockState> blocks, boolean suppressLightUpdates) {
      Preconditions.checkArgument(blocks != null, "blocks must not be null");
      if (this.getHandle().b != null && !blocks.isEmpty()) {
         Map<SectionPosition, CraftPlayer.ChunkSectionChanges> changes = new HashMap<>();

         for(BlockState state : blocks) {
            CraftBlockState cstate = (CraftBlockState)state;
            BlockPosition blockPosition = cstate.getPosition();
            SectionPosition sectionPosition = SectionPosition.a(blockPosition);
            CraftPlayer.ChunkSectionChanges sectionChanges = changes.computeIfAbsent(sectionPosition, ignore -> new CraftPlayer.ChunkSectionChanges());
            sectionChanges.positions().add(SectionPosition.b(blockPosition));
            sectionChanges.blockData().add(cstate.getHandle());
         }

         for(Entry<SectionPosition, CraftPlayer.ChunkSectionChanges> entry : changes.entrySet()) {
            CraftPlayer.ChunkSectionChanges chunkChanges = entry.getValue();
            PacketPlayOutMultiBlockChange packet = new PacketPlayOutMultiBlockChange(
               entry.getKey(), chunkChanges.positions(), chunkChanges.blockData().toArray(var0 -> new IBlockData[var0]), suppressLightUpdates
            );
            this.getHandle().b.a(packet);
         }
      }
   }

   public void sendBlockDamage(Location loc, float progress) {
      Preconditions.checkArgument(loc != null, "loc must not be null");
      Preconditions.checkArgument((double)progress >= 0.0 && (double)progress <= 1.0, "progress must be between 0.0 and 1.0 (inclusive)");
      if (this.getHandle().b != null) {
         int stage = (int)(9.0F * progress);
         PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(
            this.getHandle().af(), new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), stage
         );
         this.getHandle().b.a(packet);
      }
   }

   public void sendSignChange(Location loc, String[] lines) {
      this.sendSignChange(loc, lines, DyeColor.BLACK);
   }

   public void sendSignChange(Location loc, String[] lines, DyeColor dyeColor) {
      this.sendSignChange(loc, lines, dyeColor, false);
   }

   public void sendSignChange(Location loc, String[] lines, DyeColor dyeColor, boolean hasGlowingText) {
      if (this.getHandle().b != null) {
         if (lines == null) {
            lines = new String[4];
         }

         Validate.notNull(loc, "Location can not be null");
         Validate.notNull(dyeColor, "DyeColor can not be null");
         if (lines.length < 4) {
            throw new IllegalArgumentException("Must have at least 4 lines");
         } else {
            IChatBaseComponent[] components = CraftSign.sanitizeLines(lines);
            TileEntitySign sign = new TileEntitySign(new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), Blocks.cD.o());
            sign.a(EnumColor.a(dyeColor.getWoolData()));
            sign.b(hasGlowingText);

            for(int i = 0; i < components.length; ++i) {
               sign.a(i, components[i]);
            }

            this.getHandle().b.a(sign.f());
         }
      }
   }

   public void sendEquipmentChange(LivingEntity entity, EquipmentSlot slot, ItemStack item) {
      this.sendEquipmentChange(entity, Map.of(slot, item));
   }

   public void sendEquipmentChange(LivingEntity entity, Map<EquipmentSlot, ItemStack> items) {
      Preconditions.checkArgument(entity != null, "entity must not be null");
      Preconditions.checkArgument(items != null, "items must not be null");
      if (this.getHandle().b != null) {
         List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> equipment = new ArrayList(items.size());

         for(Entry<EquipmentSlot, ItemStack> entry : items.entrySet()) {
            EquipmentSlot slot = (EquipmentSlot)entry.getKey();
            Preconditions.checkArgument(slot != null, "Cannot set null EquipmentSlot");
            equipment.add(new Pair(CraftEquipmentSlot.getNMS(slot), CraftItemStack.asNMSCopy((ItemStack)entry.getValue())));
         }

         this.getHandle().b.a(new PacketPlayOutEntityEquipment(entity.getEntityId(), equipment));
      }
   }

   public WorldBorder getWorldBorder() {
      return this.clientWorldBorder;
   }

   public void setWorldBorder(WorldBorder border) {
      CraftWorldBorder craftBorder = (CraftWorldBorder)border;
      if (border != null && !craftBorder.isVirtual() && !craftBorder.getWorld().equals(this.getWorld())) {
         throw new UnsupportedOperationException("Cannot set player world border to that of another world");
      } else {
         if (this.clientWorldBorder != null) {
            this.clientWorldBorder.getHandle().b(this.clientWorldBorderListener);
         }

         net.minecraft.world.level.border.WorldBorder newWorldBorder;
         if (craftBorder != null && craftBorder.isVirtual()) {
            this.clientWorldBorder = craftBorder;
            this.clientWorldBorder.getHandle().a(this.clientWorldBorderListener);
            newWorldBorder = this.clientWorldBorder.getHandle();
         } else {
            this.clientWorldBorder = null;
            newWorldBorder = ((CraftWorldBorder)this.getWorld().getWorldBorder()).getHandle();
         }

         PlayerConnection connection = this.getHandle().b;
         connection.a(new ClientboundSetBorderSizePacket(newWorldBorder));
         connection.a(new ClientboundSetBorderLerpSizePacket(newWorldBorder));
         connection.a(new ClientboundSetBorderCenterPacket(newWorldBorder));
         connection.a(new ClientboundSetBorderWarningDelayPacket(newWorldBorder));
         connection.a(new ClientboundSetBorderWarningDistancePacket(newWorldBorder));
      }
   }

   private IWorldBorderListener createWorldBorderListener() {
      return new IWorldBorderListener() {
         @Override
         public void a(net.minecraft.world.level.border.WorldBorder border, double size) {
            CraftPlayer.this.getHandle().b.a(new ClientboundSetBorderSizePacket(border));
         }

         @Override
         public void a(net.minecraft.world.level.border.WorldBorder border, double size, double newSize, long time) {
            CraftPlayer.this.getHandle().b.a(new ClientboundSetBorderLerpSizePacket(border));
         }

         @Override
         public void a(net.minecraft.world.level.border.WorldBorder border, double x, double z) {
            CraftPlayer.this.getHandle().b.a(new ClientboundSetBorderCenterPacket(border));
         }

         @Override
         public void a(net.minecraft.world.level.border.WorldBorder border, int warningTime) {
            CraftPlayer.this.getHandle().b.a(new ClientboundSetBorderWarningDelayPacket(border));
         }

         @Override
         public void b(net.minecraft.world.level.border.WorldBorder border, int warningBlocks) {
            CraftPlayer.this.getHandle().b.a(new ClientboundSetBorderWarningDistancePacket(border));
         }

         @Override
         public void b(net.minecraft.world.level.border.WorldBorder border, double damage) {
         }

         @Override
         public void c(net.minecraft.world.level.border.WorldBorder border, double blocks) {
         }
      };
   }

   public boolean hasClientWorldBorder() {
      return this.clientWorldBorder != null;
   }

   public void sendMap(MapView map) {
      if (this.getHandle().b != null) {
         RenderData data = ((CraftMapView)map).render(this);
         Collection<MapIcon> icons = new ArrayList<>();

         for(MapCursor cursor : data.cursors) {
            if (cursor.isVisible()) {
               icons.add(
                  new MapIcon(
                     MapIcon.Type.a(cursor.getRawType()),
                     cursor.getX(),
                     cursor.getY(),
                     cursor.getDirection(),
                     CraftChatMessage.fromStringOrNull(cursor.getCaption())
                  )
               );
            }
         }

         PacketPlayOutMap packet = new PacketPlayOutMap(
            map.getId(), map.getScale().getValue(), map.isLocked(), icons, new WorldMap.b(0, 0, 128, 128, data.buffer)
         );
         this.getHandle().b.a(packet);
      }
   }

   public void addCustomChatCompletions(Collection<String> completions) {
      this.sendCustomChatCompletionPacket(completions, ClientboundCustomChatCompletionsPacket.Action.a);
   }

   public void removeCustomChatCompletions(Collection<String> completions) {
      this.sendCustomChatCompletionPacket(completions, ClientboundCustomChatCompletionsPacket.Action.b);
   }

   public void setCustomChatCompletions(Collection<String> completions) {
      this.sendCustomChatCompletionPacket(completions, ClientboundCustomChatCompletionsPacket.Action.c);
   }

   private void sendCustomChatCompletionPacket(Collection<String> completions, ClientboundCustomChatCompletionsPacket.Action action) {
      if (this.getHandle().b != null) {
         ClientboundCustomChatCompletionsPacket packet = new ClientboundCustomChatCompletionsPacket(action, new ArrayList<>(completions));
         this.getHandle().b.a(packet);
      }
   }

   @Override
   public void setRotation(float yaw, float pitch) {
      throw new UnsupportedOperationException("Cannot set rotation of players. Consider teleporting instead.");
   }

   @Override
   public boolean teleport(Location location, TeleportCause cause) {
      Preconditions.checkArgument(location != null, "location");
      Preconditions.checkArgument(location.getWorld() != null, "location.world");
      location.checkFinite();
      EntityPlayer entity = this.getHandle();
      if (this.getHealth() == 0.0 || entity.dB()) {
         return false;
      } else if (entity.b == null) {
         return false;
      } else if (entity.bM()) {
         return false;
      } else {
         Location from = this.getLocation();
         PlayerTeleportEvent event = new PlayerTeleportEvent(this, from, location, cause);
         this.server.getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return false;
         } else {
            entity.bz();
            if (this.isSleeping()) {
               this.wakeup(false);
            }

            from = event.getFrom();
            Location to = event.getTo();
            WorldServer fromWorld = ((CraftWorld)from.getWorld()).getHandle();
            WorldServer toWorld = ((CraftWorld)to.getWorld()).getHandle();
            if (this.getHandle().bP != this.getHandle().bO) {
               this.getHandle().q();
            }

            if (fromWorld == toWorld) {
               entity.b.teleport(to);
            } else {
               this.server.getHandle().respawn(entity, toWorld, true, to, true);
            }

            return true;
         }
      }
   }

   public void setSneaking(boolean sneak) {
      this.getHandle().f(sneak);
   }

   public boolean isSneaking() {
      return this.getHandle().bO();
   }

   public boolean isSprinting() {
      return this.getHandle().bU();
   }

   public void setSprinting(boolean sprinting) {
      this.getHandle().g(sprinting);
   }

   public void loadData() {
      this.server.getHandle().s.b(this.getHandle());
   }

   public void saveData() {
      this.server.getHandle().s.a(this.getHandle());
   }

   @Deprecated
   public void updateInventory() {
      this.getHandle().bP.b();
   }

   public void setSleepingIgnored(boolean isSleeping) {
      this.getHandle().fauxSleeping = isSleeping;
      ((CraftWorld)this.getWorld()).getHandle().e();
   }

   public boolean isSleepingIgnored() {
      return this.getHandle().fauxSleeping;
   }

   public Location getBedSpawnLocation() {
      WorldServer world = this.getHandle().c.a(this.getHandle().P());
      BlockPosition bed = this.getHandle().N();
      if (world != null && bed != null) {
         Optional<Vec3D> spawnLoc = EntityHuman.a(world, bed, this.getHandle().O(), this.getHandle().Q(), true);
         if (spawnLoc.isPresent()) {
            Vec3D vec = spawnLoc.get();
            return new Location(world.getWorld(), vec.c, vec.d, vec.e, this.getHandle().O(), 0.0F);
         }
      }

      return null;
   }

   public void setBedSpawnLocation(Location location) {
      this.setBedSpawnLocation(location, false);
   }

   public void setBedSpawnLocation(Location location, boolean override) {
      if (location == null) {
         this.getHandle().a(null, null, 0.0F, override, false);
      } else {
         this.getHandle()
            .a(
               ((CraftWorld)location.getWorld()).getHandle().ab(),
               new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
               location.getYaw(),
               override,
               false
            );
      }
   }

   @Override
   public Location getBedLocation() {
      Preconditions.checkState(this.isSleeping(), "Not sleeping");
      BlockPosition bed = this.getHandle().N();
      return new Location(this.getWorld(), (double)bed.u(), (double)bed.v(), (double)bed.w());
   }

   @Override
   public boolean hasDiscoveredRecipe(NamespacedKey recipe) {
      Preconditions.checkArgument(recipe != null, "recipe cannot be null");
      return this.getHandle().E().b(CraftNamespacedKey.toMinecraft(recipe));
   }

   @Override
   public Set<NamespacedKey> getDiscoveredRecipes() {
      Builder<NamespacedKey> bukkitRecipeKeys = ImmutableSet.builder();
      this.getHandle().E().a.forEach(key -> bukkitRecipeKeys.add(CraftNamespacedKey.fromMinecraft(key)));
      return bukkitRecipeKeys.build();
   }

   public void incrementStatistic(Statistic statistic) {
      CraftStatistic.incrementStatistic(this.getHandle().D(), statistic);
   }

   public void decrementStatistic(Statistic statistic) {
      CraftStatistic.decrementStatistic(this.getHandle().D(), statistic);
   }

   public int getStatistic(Statistic statistic) {
      return CraftStatistic.getStatistic(this.getHandle().D(), statistic);
   }

   public void incrementStatistic(Statistic statistic, int amount) {
      CraftStatistic.incrementStatistic(this.getHandle().D(), statistic, amount);
   }

   public void decrementStatistic(Statistic statistic, int amount) {
      CraftStatistic.decrementStatistic(this.getHandle().D(), statistic, amount);
   }

   public void setStatistic(Statistic statistic, int newValue) {
      CraftStatistic.setStatistic(this.getHandle().D(), statistic, newValue);
   }

   public void incrementStatistic(Statistic statistic, Material material) {
      CraftStatistic.incrementStatistic(this.getHandle().D(), statistic, material);
   }

   public void decrementStatistic(Statistic statistic, Material material) {
      CraftStatistic.decrementStatistic(this.getHandle().D(), statistic, material);
   }

   public int getStatistic(Statistic statistic, Material material) {
      return CraftStatistic.getStatistic(this.getHandle().D(), statistic, material);
   }

   public void incrementStatistic(Statistic statistic, Material material, int amount) {
      CraftStatistic.incrementStatistic(this.getHandle().D(), statistic, material, amount);
   }

   public void decrementStatistic(Statistic statistic, Material material, int amount) {
      CraftStatistic.decrementStatistic(this.getHandle().D(), statistic, material, amount);
   }

   public void setStatistic(Statistic statistic, Material material, int newValue) {
      CraftStatistic.setStatistic(this.getHandle().D(), statistic, material, newValue);
   }

   public void incrementStatistic(Statistic statistic, EntityType entityType) {
      CraftStatistic.incrementStatistic(this.getHandle().D(), statistic, entityType);
   }

   public void decrementStatistic(Statistic statistic, EntityType entityType) {
      CraftStatistic.decrementStatistic(this.getHandle().D(), statistic, entityType);
   }

   public int getStatistic(Statistic statistic, EntityType entityType) {
      return CraftStatistic.getStatistic(this.getHandle().D(), statistic, entityType);
   }

   public void incrementStatistic(Statistic statistic, EntityType entityType, int amount) {
      CraftStatistic.incrementStatistic(this.getHandle().D(), statistic, entityType, amount);
   }

   public void decrementStatistic(Statistic statistic, EntityType entityType, int amount) {
      CraftStatistic.decrementStatistic(this.getHandle().D(), statistic, entityType, amount);
   }

   public void setStatistic(Statistic statistic, EntityType entityType, int newValue) {
      CraftStatistic.setStatistic(this.getHandle().D(), statistic, entityType, newValue);
   }

   public void setPlayerTime(long time, boolean relative) {
      this.getHandle().timeOffset = time;
      this.getHandle().relativeTime = relative;
   }

   public long getPlayerTimeOffset() {
      return this.getHandle().timeOffset;
   }

   public long getPlayerTime() {
      return this.getHandle().getPlayerTime();
   }

   public boolean isPlayerTimeRelative() {
      return this.getHandle().relativeTime;
   }

   public void resetPlayerTime() {
      this.setPlayerTime(0L, true);
   }

   public void setPlayerWeather(WeatherType type) {
      this.getHandle().setPlayerWeather(type, true);
   }

   public WeatherType getPlayerWeather() {
      return this.getHandle().getPlayerWeather();
   }

   public void resetPlayerWeather() {
      this.getHandle().resetPlayerWeather();
   }

   public boolean isBanned() {
      return this.server.getBanList(Type.NAME).isBanned(this.getName());
   }

   public boolean isWhitelisted() {
      return this.server.getHandle().i().a(this.getProfile());
   }

   public void setWhitelisted(boolean value) {
      if (value) {
         this.server.getHandle().i().a(new WhiteListEntry(this.getProfile()));
      } else {
         this.server.getHandle().i().c(this.getProfile());
      }
   }

   @Override
   public void setGameMode(GameMode mode) {
      if (this.getHandle().b != null) {
         if (mode == null) {
            throw new IllegalArgumentException("Mode cannot be null");
         } else {
            this.getHandle().a(EnumGamemode.a(mode.getValue()));
         }
      }
   }

   @Override
   public GameMode getGameMode() {
      return GameMode.getByValue(this.getHandle().d.b().a());
   }

   public GameMode getPreviousGameMode() {
      EnumGamemode previousGameMode = this.getHandle().d.c();
      return previousGameMode == null ? null : GameMode.getByValue(previousGameMode.a());
   }

   public void giveExp(int exp) {
      this.getHandle().d(exp);
   }

   public void giveExpLevels(int levels) {
      this.getHandle().c(levels);
   }

   public float getExp() {
      return this.getHandle().ce;
   }

   public void setExp(float exp) {
      Preconditions.checkArgument((double)exp >= 0.0 && (double)exp <= 1.0, "Experience progress must be between 0.0 and 1.0 (%s)", exp);
      this.getHandle().ce = exp;
      this.getHandle().cx = -1;
   }

   public int getLevel() {
      return this.getHandle().cc;
   }

   public void setLevel(int level) {
      Preconditions.checkArgument(level >= 0, "Experience level must not be negative (%s)", level);
      this.getHandle().cc = level;
      this.getHandle().cx = -1;
   }

   public int getTotalExperience() {
      return this.getHandle().cd;
   }

   public void setTotalExperience(int exp) {
      Preconditions.checkArgument(exp >= 0, "Total experience points must not be negative (%s)", exp);
      this.getHandle().cd = exp;
   }

   public void sendExperienceChange(float progress) {
      this.sendExperienceChange(progress, this.getLevel());
   }

   public void sendExperienceChange(float progress, int level) {
      Preconditions.checkArgument((double)progress >= 0.0 && (double)progress <= 1.0, "Experience progress must be between 0.0 and 1.0 (%s)", progress);
      Preconditions.checkArgument(level >= 0, "Experience level must not be negative (%s)", level);
      if (this.getHandle().b != null) {
         PacketPlayOutExperience packet = new PacketPlayOutExperience(progress, this.getTotalExperience(), level);
         this.getHandle().b.a(packet);
      }
   }

   @Nullable
   private static WeakReference<Plugin> getPluginWeakReference(@Nullable Plugin plugin) {
      return plugin == null ? null : pluginWeakReferences.computeIfAbsent(plugin, WeakReference::new);
   }

   @Deprecated
   public void hidePlayer(Player player) {
      this.hideEntity0(null, player);
   }

   public void hidePlayer(Plugin plugin, Player player) {
      this.hideEntity(plugin, player);
   }

   public void hideEntity(Plugin plugin, Entity entity) {
      Validate.notNull(plugin, "Plugin cannot be null");
      Validate.isTrue(plugin.isEnabled(), "Plugin attempted to hide player while disabled");
      this.hideEntity0(plugin, entity);
   }

   private void hideEntity0(@Nullable Plugin plugin, Entity entity) {
      Validate.notNull(entity, "hidden entity cannot be null");
      if (this.getHandle().b != null) {
         if (!this.equals(entity)) {
            boolean shouldHide;
            if (entity.isVisibleByDefault()) {
               shouldHide = this.addInvertedVisibility(plugin, entity);
            } else {
               shouldHide = this.removeInvertedVisiblity(plugin, entity);
            }

            if (shouldHide) {
               this.untrackAndHideEntity(entity);
            }
         }
      }
   }

   private boolean addInvertedVisibility(@Nullable Plugin plugin, Entity entity) {
      Set<WeakReference<Plugin>> invertedPlugins = this.invertedVisibilityEntities.get(entity.getUniqueId());
      if (invertedPlugins != null) {
         invertedPlugins.add(getPluginWeakReference(plugin));
         return false;
      } else {
         Set<WeakReference<Plugin>> var4 = new HashSet();
         var4.add(getPluginWeakReference(plugin));
         this.invertedVisibilityEntities.put(entity.getUniqueId(), var4);
         return true;
      }
   }

   private void untrackAndHideEntity(Entity entity) {
      PlayerChunkMap tracker = ((WorldServer)this.getHandle().H).k().a;
      net.minecraft.world.entity.Entity other = ((CraftEntity)entity).getHandle();
      PlayerChunkMap.EntityTracker entry = (PlayerChunkMap.EntityTracker)tracker.L.get(other.af());
      if (entry != null) {
         entry.a(this.getHandle());
      }

      if (other instanceof EntityPlayer otherPlayer && otherPlayer.sentListPacket) {
         this.getHandle().b.a(new ClientboundPlayerInfoRemovePacket(List.of(otherPlayer.cs())));
      }

      this.server.getPluginManager().callEvent(new PlayerHideEntityEvent(this, entity));
   }

   void resetAndHideEntity(Entity entity) {
      if (!this.equals(entity)) {
         if (this.invertedVisibilityEntities.remove(entity.getUniqueId()) == null) {
            this.untrackAndHideEntity(entity);
         }
      }
   }

   @Deprecated
   public void showPlayer(Player player) {
      this.showEntity0(null, player);
   }

   public void showPlayer(Plugin plugin, Player player) {
      this.showEntity(plugin, player);
   }

   public void showEntity(Plugin plugin, Entity entity) {
      Validate.notNull(plugin, "Plugin cannot be null");
      this.showEntity0(plugin, entity);
   }

   private void showEntity0(@Nullable Plugin plugin, Entity entity) {
      Validate.notNull(entity, "shown entity cannot be null");
      if (this.getHandle().b != null) {
         if (!this.equals(entity)) {
            boolean shouldShow;
            if (entity.isVisibleByDefault()) {
               shouldShow = this.removeInvertedVisiblity(plugin, entity);
            } else {
               shouldShow = this.addInvertedVisibility(plugin, entity);
            }

            if (shouldShow) {
               this.trackAndShowEntity(entity);
            }
         }
      }
   }

   private boolean removeInvertedVisiblity(@Nullable Plugin plugin, Entity entity) {
      Set<WeakReference<Plugin>> invertedPlugins = this.invertedVisibilityEntities.get(entity.getUniqueId());
      if (invertedPlugins == null) {
         return false;
      } else {
         invertedPlugins.remove(getPluginWeakReference(plugin));
         if (!invertedPlugins.isEmpty()) {
            return false;
         } else {
            this.invertedVisibilityEntities.remove(entity.getUniqueId());
            return true;
         }
      }
   }

   private void trackAndShowEntity(Entity entity) {
      PlayerChunkMap tracker = ((WorldServer)this.getHandle().H).k().a;
      net.minecraft.world.entity.Entity other = ((CraftEntity)entity).getHandle();
      if (other instanceof EntityPlayer otherPlayer) {
         this.getHandle().b.a(ClientboundPlayerInfoUpdatePacket.a(List.of(otherPlayer)));
      }

      PlayerChunkMap.EntityTracker entry = (PlayerChunkMap.EntityTracker)tracker.L.get(other.af());
      if (entry != null && !entry.f.contains(this.getHandle().b)) {
         entry.b(this.getHandle());
      }

      this.server.getPluginManager().callEvent(new PlayerShowEntityEvent(this, entity));
   }

   void resetAndShowEntity(Entity entity) {
      if (!this.equals(entity)) {
         if (this.invertedVisibilityEntities.remove(entity.getUniqueId()) == null) {
            this.trackAndShowEntity(entity);
         }
      }
   }

   public void onEntityRemove(net.minecraft.world.entity.Entity entity) {
      this.invertedVisibilityEntities.remove(entity.cs());
   }

   public boolean canSee(Player player) {
      return this.canSee((Entity)player);
   }

   public boolean canSee(Entity entity) {
      return this.equals(entity) || entity.isVisibleByDefault() ^ this.invertedVisibilityEntities.containsKey(entity.getUniqueId());
   }

   public boolean canSee(UUID uuid) {
      Entity entity = this.getServer().getPlayer(uuid);
      if (entity == null) {
         entity = this.getServer().getEntity(uuid);
      }

      return entity != null ? this.canSee(entity) : false;
   }

   public Map<String, Object> serialize() {
      Map<String, Object> result = new LinkedHashMap<>();
      result.put("name", this.getName());
      return result;
   }

   public Player getPlayer() {
      return this;
   }

   public EntityPlayer getHandle() {
      return (EntityPlayer)this.entity;
   }

   public void setHandle(EntityPlayer entity) {
      super.setHandle(entity);
   }

   @Override
   public String toString() {
      return "CraftPlayer{name=" + this.getName() + 125;
   }

   @Override
   public int hashCode() {
      if (this.hash == 0 || this.hash == 485) {
         this.hash = 485 + (this.getUniqueId() != null ? this.getUniqueId().hashCode() : 0);
      }

      return this.hash;
   }

   public long getFirstPlayed() {
      return this.firstPlayed;
   }

   public long getLastPlayed() {
      return this.lastPlayed;
   }

   public boolean hasPlayedBefore() {
      return this.hasPlayedBefore;
   }

   public void setFirstPlayed(long firstPlayed) {
      this.firstPlayed = firstPlayed;
   }

   public void readExtraData(NBTTagCompound nbttagcompound) {
      this.hasPlayedBefore = true;
      if (nbttagcompound.e("bukkit")) {
         NBTTagCompound data = nbttagcompound.p("bukkit");
         if (data.e("firstPlayed")) {
            this.firstPlayed = data.i("firstPlayed");
            this.lastPlayed = data.i("lastPlayed");
         }

         if (data.e("newExp")) {
            EntityPlayer handle = this.getHandle();
            handle.newExp = data.h("newExp");
            handle.newTotalExp = data.h("newTotalExp");
            handle.newLevel = data.h("newLevel");
            handle.expToDrop = data.h("expToDrop");
            handle.keepLevel = data.q("keepLevel");
         }
      }
   }

   public void setExtraData(NBTTagCompound nbttagcompound) {
      if (!nbttagcompound.e("bukkit")) {
         nbttagcompound.a("bukkit", new NBTTagCompound());
      }

      NBTTagCompound data = nbttagcompound.p("bukkit");
      EntityPlayer handle = this.getHandle();
      data.a("newExp", handle.newExp);
      data.a("newTotalExp", handle.newTotalExp);
      data.a("newLevel", handle.newLevel);
      data.a("expToDrop", handle.expToDrop);
      data.a("keepLevel", handle.keepLevel);
      data.a("firstPlayed", this.getFirstPlayed());
      data.a("lastPlayed", System.currentTimeMillis());
      data.a("lastKnownName", handle.cu());
   }

   public boolean beginConversation(Conversation conversation) {
      return this.conversationTracker.beginConversation(conversation);
   }

   public void abandonConversation(Conversation conversation) {
      this.conversationTracker.abandonConversation(conversation, new ConversationAbandonedEvent(conversation, new ManuallyAbandonedConversationCanceller()));
   }

   public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
      this.conversationTracker.abandonConversation(conversation, details);
   }

   public void acceptConversationInput(String input) {
      this.conversationTracker.acceptConversationInput(input);
   }

   public boolean isConversing() {
      return this.conversationTracker.isConversing();
   }

   public void sendPluginMessage(Plugin source, String channel, byte[] message) {
      StandardMessenger.validatePluginMessage(this.server.getMessenger(), source, channel, message);
      if (this.getHandle().b != null) {
         if (this.channels.contains(channel)) {
            channel = StandardMessenger.validateAndCorrectChannel(channel);
            PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(
               new MinecraftKey(channel), new PacketDataSerializer(Unpooled.wrappedBuffer(message))
            );
            this.getHandle().b.a(packet);
         }
      }
   }

   public void setTexturePack(String url) {
      this.setResourcePack(url);
   }

   public void setResourcePack(String url) {
      this.setResourcePack(url, null);
   }

   public void setResourcePack(String url, byte[] hash) {
      this.setResourcePack(url, hash, false);
   }

   public void setResourcePack(String url, byte[] hash, String prompt) {
      this.setResourcePack(url, hash, prompt, false);
   }

   public void setResourcePack(String url, byte[] hash, boolean force) {
      this.setResourcePack(url, hash, null, force);
   }

   public void setResourcePack(String url, byte[] hash, String prompt, boolean force) {
      Validate.notNull(url, "Resource pack URL cannot be null");
      if (hash != null) {
         Validate.isTrue(hash.length == 20, "Resource pack hash should be 20 bytes long but was " + hash.length);
         this.getHandle().a(url, BaseEncoding.base16().lowerCase().encode(hash), force, CraftChatMessage.fromStringOrNull(prompt, true));
      } else {
         this.getHandle().a(url, "", force, CraftChatMessage.fromStringOrNull(prompt, true));
      }
   }

   public void addChannel(String channel) {
      Preconditions.checkState(this.channels.size() < 128, "Cannot register channel '%s'. Too many channels registered!", channel);
      channel = StandardMessenger.validateAndCorrectChannel(channel);
      if (this.channels.add(channel)) {
         this.server.getPluginManager().callEvent(new PlayerRegisterChannelEvent(this, channel));
      }
   }

   public void removeChannel(String channel) {
      channel = StandardMessenger.validateAndCorrectChannel(channel);
      if (this.channels.remove(channel)) {
         this.server.getPluginManager().callEvent(new PlayerUnregisterChannelEvent(this, channel));
      }
   }

   public Set<String> getListeningPluginChannels() {
      return ImmutableSet.copyOf(this.channels);
   }

   public void sendSupportedChannels() {
      if (this.getHandle().b != null) {
         Set<String> listening = this.server.getMessenger().getIncomingChannels();
         if (!listening.isEmpty()) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            for(String channel : listening) {
               try {
                  stream.write(channel.getBytes("UTF8"));
                  stream.write(0);
               } catch (IOException var6) {
                  Logger.getLogger(CraftPlayer.class.getName())
                     .log(Level.SEVERE, "Could not send Plugin Channel REGISTER to " + this.getName(), (Throwable)var6);
               }
            }

            this.getHandle()
               .b
               .a(new PacketPlayOutCustomPayload(new MinecraftKey("register"), new PacketDataSerializer(Unpooled.wrappedBuffer(stream.toByteArray()))));
         }
      }
   }

   @Override
   public EntityType getType() {
      return EntityType.PLAYER;
   }

   @Override
   public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
      this.server.getPlayerMetadata().setMetadata(this, metadataKey, newMetadataValue);
   }

   @Override
   public List<MetadataValue> getMetadata(String metadataKey) {
      return this.server.getPlayerMetadata().getMetadata(this, metadataKey);
   }

   @Override
   public boolean hasMetadata(String metadataKey) {
      return this.server.getPlayerMetadata().hasMetadata(this, metadataKey);
   }

   @Override
   public void removeMetadata(String metadataKey, Plugin owningPlugin) {
      this.server.getPlayerMetadata().removeMetadata(this, metadataKey, owningPlugin);
   }

   @Override
   public boolean setWindowProperty(Property prop, int value) {
      Container container = this.getHandle().bP;
      if (container.getBukkitView().getType() != prop.getType()) {
         return false;
      } else {
         container.a(prop.getId(), value);
         return true;
      }
   }

   public void disconnect(String reason) {
      this.conversationTracker.abandonAllConversations();
      this.perm.clearPermissions();
   }

   public boolean isFlying() {
      return this.getHandle().fK().b;
   }

   public void setFlying(boolean value) {
      if (!this.getAllowFlight() && value) {
         throw new IllegalArgumentException("Cannot make player fly if getAllowFlight() is false");
      } else {
         this.getHandle().fK().b = value;
         this.getHandle().w();
      }
   }

   public boolean getAllowFlight() {
      return this.getHandle().fK().c;
   }

   public void setAllowFlight(boolean value) {
      if (this.isFlying() && !value) {
         this.getHandle().fK().b = false;
      }

      this.getHandle().fK().c = value;
      this.getHandle().w();
   }

   @Override
   public int getNoDamageTicks() {
      return this.getHandle().cy > 0 ? Math.max(this.getHandle().cy, this.getHandle().ak) : this.getHandle().ak;
   }

   @Override
   public void setNoDamageTicks(int ticks) {
      super.setNoDamageTicks(ticks);
      this.getHandle().cy = ticks;
   }

   public void setFlySpeed(float value) {
      this.validateSpeed(value);
      EntityPlayer player = this.getHandle();
      player.fK().f = value / 2.0F;
      player.w();
   }

   public void setWalkSpeed(float value) {
      this.validateSpeed(value);
      EntityPlayer player = this.getHandle();
      player.fK().g = value / 2.0F;
      player.w();
      this.getHandle().a(GenericAttributes.d).a((double)player.fK().g);
   }

   public float getFlySpeed() {
      return this.getHandle().fK().f * 2.0F;
   }

   public float getWalkSpeed() {
      return this.getHandle().fK().g * 2.0F;
   }

   private void validateSpeed(float value) {
      if (value < 0.0F) {
         if (value < -1.0F) {
            throw new IllegalArgumentException(value + " is too low");
         }
      } else if (value > 1.0F) {
         throw new IllegalArgumentException(value + " is too high");
      }
   }

   @Override
   public void setMaxHealth(double amount) {
      super.setMaxHealth(amount);
      this.health = Math.min(this.health, this.health);
      this.getHandle().u();
   }

   @Override
   public void resetMaxHealth() {
      super.resetMaxHealth();
      this.getHandle().u();
   }

   public CraftScoreboard getScoreboard() {
      return this.server.getScoreboardManager().getPlayerBoard(this);
   }

   public void setScoreboard(Scoreboard scoreboard) {
      Validate.notNull(scoreboard, "Scoreboard cannot be null");
      PlayerConnection playerConnection = this.getHandle().b;
      if (playerConnection == null) {
         throw new IllegalStateException("Cannot set scoreboard yet");
      } else {
         playerConnection.isDisconnected();
         this.server.getScoreboardManager().setPlayerBoard(this, scoreboard);
      }
   }

   public void setHealthScale(double value) {
      Validate.isTrue((float)value > 0.0F, "Must be greater than 0");
      this.healthScale = value;
      this.scaledHealth = true;
      this.updateScaledHealth();
   }

   public double getHealthScale() {
      return this.healthScale;
   }

   public void setHealthScaled(boolean scale) {
      if (this.scaledHealth != (this.scaledHealth = scale)) {
         this.updateScaledHealth();
      }
   }

   public boolean isHealthScaled() {
      return this.scaledHealth;
   }

   public float getScaledHealth() {
      return (float)(this.isHealthScaled() ? this.getHealth() * this.getHealthScale() / this.getMaxHealth() : this.getHealth());
   }

   @Override
   public double getHealth() {
      return this.health;
   }

   public void setRealHealth(double health) {
      this.health = health;
   }

   public void updateScaledHealth() {
      this.updateScaledHealth(true);
   }

   public void updateScaledHealth(boolean sendHealth) {
      AttributeMapBase attributemapserver = this.getHandle().eI();
      Collection<AttributeModifiable> set = attributemapserver.b();
      this.injectScaledMaxHealth(set, true);
      if (this.getHandle().b != null) {
         this.getHandle().b.a(new PacketPlayOutUpdateAttributes(this.getHandle().af(), set));
         if (sendHealth) {
            this.sendHealthUpdate();
         }
      }

      this.getHandle().aj().b(EntityLiving.bF, this.getScaledHealth());
      this.getHandle().maxHealthCache = this.getMaxHealth();
   }

   public void sendHealthUpdate() {
      this.getHandle().b.a(new PacketPlayOutUpdateHealth(this.getScaledHealth(), this.getHandle().fT().a(), this.getHandle().fT().e()));
   }

   public void injectScaledMaxHealth(Collection<AttributeModifiable> collection, boolean force) {
      if (this.scaledHealth || force) {
         for(AttributeModifiable genericInstance : collection) {
            if (genericInstance.a() == GenericAttributes.a) {
               collection.remove(genericInstance);
               break;
            }
         }

         AttributeModifiable dummy = new AttributeModifiable(GenericAttributes.a, attribute -> {
         });
         double healthMod = this.scaledHealth ? this.healthScale : this.getMaxHealth();
         if (healthMod >= Float.MAX_VALUE || healthMod <= 0.0) {
            healthMod = 20.0;
            this.getServer().getLogger().warning(this.getName() + " tried to crash the server with a large health attribute");
         }

         dummy.a(healthMod);
         collection.add(dummy);
      }
   }

   public Entity getSpectatorTarget() {
      net.minecraft.world.entity.Entity followed = this.getHandle().G();
      return followed == this.getHandle() ? null : followed.getBukkitEntity();
   }

   public void setSpectatorTarget(Entity entity) {
      Preconditions.checkArgument(this.getGameMode() == GameMode.SPECTATOR, "Player must be in spectator mode");
      this.getHandle().c(entity == null ? null : ((CraftEntity)entity).getHandle());
   }

   public void sendTitle(String title, String subtitle) {
      this.sendTitle(title, subtitle, 10, 70, 20);
   }

   public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
      ClientboundSetTitlesAnimationPacket times = new ClientboundSetTitlesAnimationPacket(fadeIn, stay, fadeOut);
      this.getHandle().b.a(times);
      if (title != null) {
         ClientboundSetTitleTextPacket packetTitle = new ClientboundSetTitleTextPacket(CraftChatMessage.fromString(title)[0]);
         this.getHandle().b.a(packetTitle);
      }

      if (subtitle != null) {
         ClientboundSetSubtitleTextPacket packetSubtitle = new ClientboundSetSubtitleTextPacket(CraftChatMessage.fromString(subtitle)[0]);
         this.getHandle().b.a(packetSubtitle);
      }
   }

   public void resetTitle() {
      ClientboundClearTitlesPacket packetReset = new ClientboundClearTitlesPacket(true);
      this.getHandle().b.a(packetReset);
   }

   public void spawnParticle(Particle particle, Location location, int count) {
      this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count);
   }

   public void spawnParticle(Particle particle, double x, double y, double z, int count) {
      this.spawnParticle(particle, x, y, z, count, null);
   }

   public <T> void spawnParticle(Particle particle, Location location, int count, T data) {
      this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, data);
   }

   public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, T data) {
      this.spawnParticle(particle, x, y, z, count, 0.0, 0.0, 0.0, data);
   }

   public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ) {
      this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ);
   }

   public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ) {
      this.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, null);
   }

   public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, T data) {
      this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, data);
   }

   public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, T data) {
      this.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, 1.0, data);
   }

   public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra) {
      this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra);
   }

   public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra) {
      this.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, null);
   }

   public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra, T data) {
      this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra, data);
   }

   public <T> void spawnParticle(
      Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, T data
   ) {
      if (data != null && !particle.getDataType().isInstance(data)) {
         throw new IllegalArgumentException("data should be " + particle.getDataType() + " got " + data.getClass());
      } else {
         PacketPlayOutWorldParticles packetplayoutworldparticles = new PacketPlayOutWorldParticles(
            CraftParticle.toNMS(particle, data),
            true,
            (double)((float)x),
            (double)((float)y),
            (double)((float)z),
            (float)offsetX,
            (float)offsetY,
            (float)offsetZ,
            (float)extra,
            count
         );
         this.getHandle().b.a(packetplayoutworldparticles);
      }
   }

   public AdvancementProgress getAdvancementProgress(Advancement advancement) {
      Preconditions.checkArgument(advancement != null, "advancement");
      CraftAdvancement craft = (CraftAdvancement)advancement;
      AdvancementDataPlayer data = this.getHandle().M();
      net.minecraft.advancements.AdvancementProgress progress = data.b(craft.getHandle());
      return new CraftAdvancementProgress(craft, data, progress);
   }

   public int getClientViewDistance() {
      return this.getHandle().clientViewDistance == null ? Bukkit.getViewDistance() : this.getHandle().clientViewDistance;
   }

   public int getPing() {
      return this.getHandle().e;
   }

   public String getLocale() {
      return this.getHandle().locale;
   }

   public void updateCommands() {
      if (this.getHandle().b != null) {
         this.getHandle().c.aC().a(this.getHandle());
      }
   }

   public void openBook(ItemStack book) {
      Validate.isTrue(book != null, "book == null");
      Validate.isTrue(book.getType() == Material.WRITTEN_BOOK, "Book must be Material.WRITTEN_BOOK");
      ItemStack hand = this.getInventory().getItemInMainHand();
      this.getInventory().setItemInMainHand(book);
      this.getHandle().a(CraftItemStack.asNMSCopy(book), EnumHand.a);
      this.getInventory().setItemInMainHand(hand);
   }

   public void openSign(Sign sign) {
      CraftSign.openSign(sign, this);
   }

   public void showDemoScreen() {
      if (this.getHandle().b != null) {
         this.getHandle().b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.f, 0.0F));
      }
   }

   public boolean isAllowingServerListings() {
      return this.getHandle().V();
   }

   public Spigot spigot() {
      return this.spigot;
   }

   private static record ChunkSectionChanges(ShortSet positions, List<IBlockData> blockData) {
      public ChunkSectionChanges() {
         this(new ShortArraySet(), new ArrayList());
      }
   }
}
