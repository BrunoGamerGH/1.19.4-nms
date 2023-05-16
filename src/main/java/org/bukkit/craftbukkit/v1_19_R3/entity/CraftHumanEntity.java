package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayInCloseWindow;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMainHand;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityFireworks;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.Containers;
import net.minecraft.world.item.ItemCooldown;
import net.minecraft.world.item.crafting.CraftingManager;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.item.trading.IMerchant;
import net.minecraft.world.level.block.BlockBed;
import net.minecraft.world.level.block.BlockEnchantmentTable;
import net.minecraft.world.level.block.BlockWorkbench;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.entity.memory.CraftMemoryMapper;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftContainer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryDoubleChest;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryLectern;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryPlayer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftMerchantCustom;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.entity.Firework;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

public class CraftHumanEntity extends CraftLivingEntity implements HumanEntity {
   private CraftInventoryPlayer inventory;
   private final CraftInventory enderChest;
   protected final PermissibleBase perm = new PermissibleBase(this);
   private boolean op;
   private GameMode mode;

   public CraftHumanEntity(CraftServer server, EntityHuman entity) {
      super(server, entity);
      this.mode = server.getDefaultGameMode();
      this.inventory = new CraftInventoryPlayer(entity.fJ());
      this.enderChest = new CraftInventory(entity.fW());
   }

   public PlayerInventory getInventory() {
      return this.inventory;
   }

   @Override
   public EntityEquipment getEquipment() {
      return this.inventory;
   }

   public Inventory getEnderChest() {
      return this.enderChest;
   }

   public MainHand getMainHand() {
      return this.getHandle().fd() == EnumMainHand.a ? MainHand.LEFT : MainHand.RIGHT;
   }

   public ItemStack getItemInHand() {
      return this.getInventory().getItemInHand();
   }

   public void setItemInHand(ItemStack item) {
      this.getInventory().setItemInHand(item);
   }

   public ItemStack getItemOnCursor() {
      return CraftItemStack.asCraftMirror(this.getHandle().bP.g());
   }

   public void setItemOnCursor(ItemStack item) {
      net.minecraft.world.item.ItemStack stack = CraftItemStack.asNMSCopy(item);
      this.getHandle().bP.b(stack);
      if (this instanceof CraftPlayer) {
         this.getHandle().bP.broadcastCarriedItem();
      }
   }

   public int getSleepTicks() {
      return this.getHandle().cl;
   }

   public boolean sleep(Location location, boolean force) {
      Preconditions.checkArgument(location != null, "Location cannot be null");
      Preconditions.checkArgument(location.getWorld() != null, "Location needs to be in a world");
      Preconditions.checkArgument(location.getWorld().equals(this.getWorld()), "Cannot sleep across worlds");
      BlockPosition blockposition = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
      IBlockData iblockdata = this.getHandle().H.a_(blockposition);
      if (!(iblockdata.b() instanceof BlockBed)) {
         return false;
      } else if (this.getHandle().startSleepInBed(blockposition, force).left().isPresent()) {
         return false;
      } else {
         iblockdata = iblockdata.a(BlockBed.b, Boolean.valueOf(true));
         this.getHandle().H.a(blockposition, iblockdata, 4);
         return true;
      }
   }

   public void wakeup(boolean setSpawnLocation) {
      Preconditions.checkState(this.isSleeping(), "Cannot wakeup if not sleeping");
      this.getHandle().a(true, setSpawnLocation);
   }

   public Location getBedLocation() {
      Preconditions.checkState(this.isSleeping(), "Not sleeping");
      BlockPosition bed = this.getHandle().fs().get();
      return new Location(this.getWorld(), (double)bed.u(), (double)bed.v(), (double)bed.w());
   }

   @Override
   public String getName() {
      return this.getHandle().cu();
   }

   @Override
   public boolean isOp() {
      return this.op;
   }

   @Override
   public boolean isPermissionSet(String name) {
      return this.perm.isPermissionSet(name);
   }

   @Override
   public boolean isPermissionSet(Permission perm) {
      return this.perm.isPermissionSet(perm);
   }

   @Override
   public boolean hasPermission(String name) {
      return this.perm.hasPermission(name);
   }

   @Override
   public boolean hasPermission(Permission perm) {
      return this.perm.hasPermission(perm);
   }

   @Override
   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
      return this.perm.addAttachment(plugin, name, value);
   }

   @Override
   public PermissionAttachment addAttachment(Plugin plugin) {
      return this.perm.addAttachment(plugin);
   }

   @Override
   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
      return this.perm.addAttachment(plugin, name, value, ticks);
   }

   @Override
   public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
      return this.perm.addAttachment(plugin, ticks);
   }

   @Override
   public void removeAttachment(PermissionAttachment attachment) {
      this.perm.removeAttachment(attachment);
   }

   @Override
   public void recalculatePermissions() {
      this.perm.recalculatePermissions();
   }

   @Override
   public void setOp(boolean value) {
      this.op = value;
      this.perm.recalculatePermissions();
   }

   @Override
   public Set<PermissionAttachmentInfo> getEffectivePermissions() {
      return this.perm.getEffectivePermissions();
   }

   public GameMode getGameMode() {
      return this.mode;
   }

   public void setGameMode(GameMode mode) {
      if (mode == null) {
         throw new IllegalArgumentException("Mode cannot be null");
      } else {
         this.mode = mode;
      }
   }

   public EntityHuman getHandle() {
      return (EntityHuman)this.entity;
   }

   public void setHandle(EntityHuman entity) {
      super.setHandle(entity);
      this.inventory = new CraftInventoryPlayer(entity.fJ());
   }

   @Override
   public String toString() {
      return "CraftHumanEntity{id=" + this.getEntityId() + "name=" + this.getName() + 125;
   }

   public InventoryView getOpenInventory() {
      return this.getHandle().bP.getBukkitView();
   }

   public InventoryView openInventory(Inventory inventory) {
      if (!(this.getHandle() instanceof EntityPlayer)) {
         return null;
      } else {
         EntityPlayer player = (EntityPlayer)this.getHandle();
         Container formerContainer = this.getHandle().bP;
         ITileInventory iinventory = null;
         if (inventory instanceof CraftInventoryDoubleChest) {
            iinventory = ((CraftInventoryDoubleChest)inventory).tile;
         } else if (inventory instanceof CraftInventoryLectern) {
            iinventory = ((CraftInventoryLectern)inventory).tile;
         } else if (inventory instanceof CraftInventory craft && craft.getInventory() instanceof ITileInventory) {
            iinventory = (ITileInventory)craft.getInventory();
         }

         if (iinventory instanceof ITileInventory && iinventory instanceof TileEntity te && !te.l()) {
            te.a(this.getHandle().H);
         }

         Containers<?> container = CraftContainer.getNotchInventoryType(inventory);
         if (iinventory instanceof ITileInventory) {
            this.getHandle().a(iinventory);
         } else {
            openCustomInventory(inventory, player, container);
         }

         if (this.getHandle().bP == formerContainer) {
            return null;
         } else {
            this.getHandle().bP.checkReachable = false;
            return this.getHandle().bP.getBukkitView();
         }
      }
   }

   private static void openCustomInventory(Inventory inventory, EntityPlayer player, Containers<?> windowType) {
      if (player.b != null) {
         Preconditions.checkArgument(windowType != null, "Unknown windowType");
         Container container = new CraftContainer(inventory, player, player.nextContainerCounter());
         Container var5 = CraftEventFactory.callInventoryOpenEvent(player, container);
         if (var5 != null) {
            String title = var5.getBukkitView().getTitle();
            player.b.a(new PacketPlayOutOpenWindow(var5.j, windowType, CraftChatMessage.fromString(title)[0]));
            player.bP = var5;
            player.a(var5);
         }
      }
   }

   public InventoryView openWorkbench(Location location, boolean force) {
      if (location == null) {
         location = this.getLocation();
      }

      if (!force) {
         Block block = location.getBlock();
         if (block.getType() != Material.CRAFTING_TABLE) {
            return null;
         }
      }

      this.getHandle()
         .a(((BlockWorkbench)Blocks.cz).b(null, this.getHandle().H, new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ())));
      if (force) {
         this.getHandle().bP.checkReachable = false;
      }

      return this.getHandle().bP.getBukkitView();
   }

   public InventoryView openEnchanting(Location location, boolean force) {
      if (location == null) {
         location = this.getLocation();
      }

      if (!force) {
         Block block = location.getBlock();
         if (block.getType() != Material.ENCHANTING_TABLE) {
            return null;
         }
      }

      BlockPosition pos = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
      this.getHandle().a(((BlockEnchantmentTable)Blocks.fq).b(null, this.getHandle().H, pos));
      if (force) {
         this.getHandle().bP.checkReachable = false;
      }

      return this.getHandle().bP.getBukkitView();
   }

   public void openInventory(InventoryView inventory) {
      if (this.getHandle() instanceof EntityPlayer) {
         if (((EntityPlayer)this.getHandle()).b != null) {
            if (this.getHandle().bP != this.getHandle().bO) {
               ((EntityPlayer)this.getHandle()).b.a(new PacketPlayInCloseWindow(this.getHandle().bP.j));
            }

            EntityPlayer player = (EntityPlayer)this.getHandle();
            Container container;
            if (inventory instanceof CraftInventoryView) {
               container = ((CraftInventoryView)inventory).getHandle();
            } else {
               container = new CraftContainer(inventory, this.getHandle(), player.nextContainerCounter());
            }

            container = CraftEventFactory.callInventoryOpenEvent(player, container);
            if (container != null) {
               Containers<?> windowType = CraftContainer.getNotchInventoryType(inventory.getTopInventory());
               String title = inventory.getTitle();
               player.b.a(new PacketPlayOutOpenWindow(container.j, windowType, CraftChatMessage.fromString(title)[0]));
               player.bP = container;
               player.a(container);
            }
         }
      }
   }

   public InventoryView openMerchant(Villager villager, boolean force) {
      Preconditions.checkNotNull(villager, "villager cannot be null");
      return this.openMerchant((Merchant)villager, force);
   }

   public InventoryView openMerchant(Merchant merchant, boolean force) {
      Preconditions.checkNotNull(merchant, "merchant cannot be null");
      if (!force && merchant.isTrading()) {
         return null;
      } else {
         if (merchant.isTrading()) {
            merchant.getTrader().closeInventory();
         }

         int level = 1;
         IMerchant mcMerchant;
         IChatBaseComponent name;
         if (merchant instanceof CraftAbstractVillager) {
            mcMerchant = ((CraftAbstractVillager)merchant).getHandle();
            name = ((CraftAbstractVillager)merchant).getHandle().G_();
            if (merchant instanceof CraftVillager) {
               level = ((CraftVillager)merchant).getHandle().gd().c();
            }
         } else {
            if (!(merchant instanceof CraftMerchantCustom)) {
               throw new IllegalArgumentException("Can't open merchant " + merchant.toString());
            }

            mcMerchant = ((CraftMerchantCustom)merchant).getMerchant();
            name = ((CraftMerchantCustom)merchant).getMerchant().getScoreboardDisplayName();
         }

         mcMerchant.e(this.getHandle());
         mcMerchant.a(this.getHandle(), name, level);
         return this.getHandle().bP.getBukkitView();
      }
   }

   public void closeInventory() {
      this.getHandle().q();
   }

   public boolean isBlocking() {
      return this.getHandle().fl();
   }

   public boolean isHandRaised() {
      return this.getHandle().fe();
   }

   public ItemStack getItemInUse() {
      net.minecraft.world.item.ItemStack item = this.getHandle().fg();
      return item.b() ? null : CraftItemStack.asCraftMirror(item);
   }

   public boolean setWindowProperty(Property prop, int value) {
      return false;
   }

   public int getEnchantmentSeed() {
      return this.getHandle().cf;
   }

   public void setEnchantmentSeed(int i) {
      this.getHandle().cf = i;
   }

   public int getExpToLevel() {
      return this.getHandle().fS();
   }

   public float getAttackCooldown() {
      return this.getHandle().z(0.5F);
   }

   public boolean hasCooldown(Material material) {
      Preconditions.checkArgument(material != null, "Material cannot be null");
      Preconditions.checkArgument(material.isItem(), "Material %s is not an item", material);
      return this.getHandle().ge().a(CraftMagicNumbers.getItem(material));
   }

   public int getCooldown(Material material) {
      Preconditions.checkArgument(material != null, "Material cannot be null");
      Preconditions.checkArgument(material.isItem(), "Material %s is not an item", material);
      ItemCooldown.Info cooldown = this.getHandle().ge().a.get(CraftMagicNumbers.getItem(material));
      return cooldown == null ? 0 : Math.max(0, cooldown.b - this.getHandle().ge().b);
   }

   public void setCooldown(Material material, int ticks) {
      Preconditions.checkArgument(material != null, "Material cannot be null");
      Preconditions.checkArgument(material.isItem(), "Material %s is not an item", material);
      Preconditions.checkArgument(ticks >= 0, "Cannot have negative cooldown");
      this.getHandle().ge().a(CraftMagicNumbers.getItem(material), ticks);
   }

   public boolean discoverRecipe(NamespacedKey recipe) {
      return this.discoverRecipes(Arrays.asList(recipe)) != 0;
   }

   public int discoverRecipes(Collection<NamespacedKey> recipes) {
      return this.getHandle().a(this.bukkitKeysToMinecraftRecipes(recipes));
   }

   public boolean undiscoverRecipe(NamespacedKey recipe) {
      return this.undiscoverRecipes(Arrays.asList(recipe)) != 0;
   }

   public int undiscoverRecipes(Collection<NamespacedKey> recipes) {
      return this.getHandle().b(this.bukkitKeysToMinecraftRecipes(recipes));
   }

   public boolean hasDiscoveredRecipe(NamespacedKey recipe) {
      return false;
   }

   public Set<NamespacedKey> getDiscoveredRecipes() {
      return ImmutableSet.of();
   }

   private Collection<IRecipe<?>> bukkitKeysToMinecraftRecipes(Collection<NamespacedKey> recipeKeys) {
      Collection<IRecipe<?>> recipes = new ArrayList<>();
      CraftingManager manager = this.getHandle().H.n().aE();

      for(NamespacedKey recipeKey : recipeKeys) {
         Optional<? extends IRecipe<?>> recipe = manager.a(CraftNamespacedKey.toMinecraft(recipeKey));
         if (recipe.isPresent()) {
            recipes.add(recipe.get());
         }
      }

      return recipes;
   }

   public org.bukkit.entity.Entity getShoulderEntityLeft() {
      if (!this.getHandle().ga().g()) {
         Optional<Entity> shoulder = EntityTypes.a(this.getHandle().ga(), this.getHandle().H);
         return !shoulder.isPresent() ? null : shoulder.get().getBukkitEntity();
      } else {
         return null;
      }
   }

   public void setShoulderEntityLeft(org.bukkit.entity.Entity entity) {
      this.getHandle().i(entity == null ? new NBTTagCompound() : ((CraftEntity)entity).save());
      if (entity != null) {
         entity.remove();
      }
   }

   public org.bukkit.entity.Entity getShoulderEntityRight() {
      if (!this.getHandle().gb().g()) {
         Optional<Entity> shoulder = EntityTypes.a(this.getHandle().gb(), this.getHandle().H);
         return !shoulder.isPresent() ? null : shoulder.get().getBukkitEntity();
      } else {
         return null;
      }
   }

   public void setShoulderEntityRight(org.bukkit.entity.Entity entity) {
      this.getHandle().j(entity == null ? new NBTTagCompound() : ((CraftEntity)entity).save());
      if (entity != null) {
         entity.remove();
      }
   }

   public boolean dropItem(boolean dropAll) {
      return !(this.getHandle() instanceof EntityPlayer) ? false : ((EntityPlayer)this.getHandle()).a(dropAll);
   }

   public float getExhaustion() {
      return this.getHandle().fT().c;
   }

   public void setExhaustion(float value) {
      this.getHandle().fT().c = value;
   }

   public float getSaturation() {
      return this.getHandle().fT().b;
   }

   public void setSaturation(float value) {
      this.getHandle().fT().b = value;
   }

   public int getFoodLevel() {
      return this.getHandle().fT().a;
   }

   public void setFoodLevel(int value) {
      this.getHandle().fT().a = value;
   }

   public int getSaturatedRegenRate() {
      return this.getHandle().fT().saturatedRegenRate;
   }

   public void setSaturatedRegenRate(int i) {
      this.getHandle().fT().saturatedRegenRate = i;
   }

   public int getUnsaturatedRegenRate() {
      return this.getHandle().fT().unsaturatedRegenRate;
   }

   public void setUnsaturatedRegenRate(int i) {
      this.getHandle().fT().unsaturatedRegenRate = i;
   }

   public int getStarvationRate() {
      return this.getHandle().fT().starvationRate;
   }

   public void setStarvationRate(int i) {
      this.getHandle().fT().starvationRate = i;
   }

   public Location getLastDeathLocation() {
      return (Location)this.getHandle().gi().map(CraftMemoryMapper::fromNms).orElse(null);
   }

   public void setLastDeathLocation(Location location) {
      if (location == null) {
         this.getHandle().a(Optional.empty());
      } else {
         this.getHandle().a(Optional.of(CraftMemoryMapper.toNms(location)));
      }
   }

   public Firework fireworkBoost(ItemStack fireworkItemStack) {
      Preconditions.checkArgument(fireworkItemStack != null, "fireworkItemStack must not be null");
      Preconditions.checkArgument(fireworkItemStack.getType() == Material.FIREWORK_ROCKET, "fireworkItemStack must be of type %s", Material.FIREWORK_ROCKET);
      EntityFireworks fireworks = new EntityFireworks(this.getHandle().H, CraftItemStack.asNMSCopy(fireworkItemStack), this.getHandle());
      boolean success = this.getHandle().H.addFreshEntity(fireworks, SpawnReason.CUSTOM);
      return success ? (Firework)fireworks.getBukkitEntity() : null;
   }
}
