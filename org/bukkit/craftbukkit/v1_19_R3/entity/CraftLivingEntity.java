package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.EnumHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.boss.wither.EntityWither;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.EntityDragonFireball;
import net.minecraft.world.entity.projectile.EntityEgg;
import net.minecraft.world.entity.projectile.EntityEnderPearl;
import net.minecraft.world.entity.projectile.EntityFireball;
import net.minecraft.world.entity.projectile.EntityFireworks;
import net.minecraft.world.entity.projectile.EntityFishingHook;
import net.minecraft.world.entity.projectile.EntityLargeFireball;
import net.minecraft.world.entity.projectile.EntityLlamaSpit;
import net.minecraft.world.entity.projectile.EntityPotion;
import net.minecraft.world.entity.projectile.EntityProjectile;
import net.minecraft.world.entity.projectile.EntityShulkerBullet;
import net.minecraft.world.entity.projectile.EntitySmallFireball;
import net.minecraft.world.entity.projectile.EntitySnowball;
import net.minecraft.world.entity.projectile.EntitySpectralArrow;
import net.minecraft.world.entity.projectile.EntityThrownExpBottle;
import net.minecraft.world.entity.projectile.EntityThrownTrident;
import net.minecraft.world.entity.projectile.EntityTippedArrow;
import net.minecraft.world.entity.projectile.EntityWitherSkull;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import org.apache.commons.lang.Validate;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftSound;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.memory.CraftMemoryKey;
import org.bukkit.craftbukkit.v1_19_R3.entity.memory.CraftMemoryMapper;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftEntityEquipment;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.potion.CraftPotionUtil;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.TippedArrow;
import org.bukkit.entity.Trident;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class CraftLivingEntity extends CraftEntity implements LivingEntity {
   private CraftEntityEquipment equipment;

   public CraftLivingEntity(CraftServer server, EntityLiving entity) {
      super(server, entity);
      if (entity instanceof EntityInsentient || entity instanceof EntityArmorStand) {
         this.equipment = new CraftEntityEquipment(this);
      }
   }

   public double getHealth() {
      return Math.min((double)Math.max(0.0F, this.getHandle().eo()), this.getMaxHealth());
   }

   public void setHealth(double health) {
      health = (double)((float)health);
      if (health < 0.0 || health > this.getMaxHealth()) {
         throw new IllegalArgumentException("Health must be between 0 and " + this.getMaxHealth() + "(" + health + ")");
      } else if (this.getHandle().generation && health == 0.0) {
         this.getHandle().ai();
      } else {
         this.getHandle().c((float)health);
         if (health == 0.0) {
            this.getHandle().a(this.getHandle().dG().n());
         }
      }
   }

   public double getAbsorptionAmount() {
      return (double)this.getHandle().fb();
   }

   public void setAbsorptionAmount(double amount) {
      Preconditions.checkArgument(amount >= 0.0 && Double.isFinite(amount), "amount < 0 or non-finite");
      this.getHandle().x((float)amount);
   }

   public double getMaxHealth() {
      return (double)this.getHandle().eE();
   }

   public void setMaxHealth(double amount) {
      Validate.isTrue(amount > 0.0, "Max health must be greater than 0");
      this.getHandle().a(GenericAttributes.a).a(amount);
      if (this.getHealth() > amount) {
         this.setHealth(amount);
      }
   }

   public void resetMaxHealth() {
      this.setMaxHealth(this.getHandle().a(GenericAttributes.a).a().a());
   }

   public double getEyeHeight() {
      return (double)this.getHandle().cE();
   }

   public double getEyeHeight(boolean ignorePose) {
      return this.getEyeHeight();
   }

   private List<Block> getLineOfSight(Set<Material> transparent, int maxDistance, int maxLength) {
      Preconditions.checkState(!this.getHandle().generation, "Cannot get line of sight during world generation");
      if (transparent == null) {
         transparent = Sets.newHashSet(new Material[]{Material.AIR, Material.CAVE_AIR, Material.VOID_AIR});
      }

      if (maxDistance > 120) {
         maxDistance = 120;
      }

      ArrayList<Block> blocks = new ArrayList();
      Iterator<Block> itr = new BlockIterator(this, maxDistance);

      while(itr.hasNext()) {
         Block block = (Block)itr.next();
         blocks.add(block);
         if (maxLength != 0 && blocks.size() > maxLength) {
            blocks.remove(0);
         }

         Material material = block.getType();
         if (!transparent.contains(material)) {
            break;
         }
      }

      return blocks;
   }

   public List<Block> getLineOfSight(Set<Material> transparent, int maxDistance) {
      return this.getLineOfSight(transparent, maxDistance, 0);
   }

   public Block getTargetBlock(Set<Material> transparent, int maxDistance) {
      List<Block> blocks = this.getLineOfSight(transparent, maxDistance, 1);
      return (Block)blocks.get(0);
   }

   public List<Block> getLastTwoTargetBlocks(Set<Material> transparent, int maxDistance) {
      return this.getLineOfSight(transparent, maxDistance, 2);
   }

   public Block getTargetBlockExact(int maxDistance) {
      return this.getTargetBlockExact(maxDistance, FluidCollisionMode.NEVER);
   }

   public Block getTargetBlockExact(int maxDistance, FluidCollisionMode fluidCollisionMode) {
      RayTraceResult hitResult = this.rayTraceBlocks((double)maxDistance, fluidCollisionMode);
      return hitResult != null ? hitResult.getHitBlock() : null;
   }

   public RayTraceResult rayTraceBlocks(double maxDistance) {
      return this.rayTraceBlocks(maxDistance, FluidCollisionMode.NEVER);
   }

   public RayTraceResult rayTraceBlocks(double maxDistance, FluidCollisionMode fluidCollisionMode) {
      Preconditions.checkState(!this.getHandle().generation, "Cannot ray tray blocks during world generation");
      Location eyeLocation = this.getEyeLocation();
      Vector direction = eyeLocation.getDirection();
      return this.getWorld().rayTraceBlocks(eyeLocation, direction, maxDistance, fluidCollisionMode, false);
   }

   public int getRemainingAir() {
      return this.getHandle().cd();
   }

   public void setRemainingAir(int ticks) {
      this.getHandle().i(ticks);
   }

   public int getMaximumAir() {
      return this.getHandle().maxAirTicks;
   }

   public void setMaximumAir(int ticks) {
      this.getHandle().maxAirTicks = ticks;
   }

   public int getArrowCooldown() {
      return this.getHandle().aH;
   }

   public void setArrowCooldown(int ticks) {
      this.getHandle().aH = ticks;
   }

   public int getArrowsInBody() {
      return this.getHandle().eF();
   }

   public void setArrowsInBody(int count) {
      Preconditions.checkArgument(count >= 0, "New arrow amount must be >= 0");
      this.getHandle().aj().b(EntityLiving.bI, count);
   }

   public void damage(double amount) {
      this.damage(amount, null);
   }

   public void damage(double amount, Entity source) {
      Preconditions.checkState(!this.getHandle().generation, "Cannot damage entity during world generation");
      DamageSource reason = this.getHandle().dG().n();
      if (source instanceof HumanEntity) {
         reason = this.getHandle().dG().a(((CraftHumanEntity)source).getHandle());
      } else if (source instanceof LivingEntity) {
         reason = this.getHandle().dG().b(((CraftLivingEntity)source).getHandle());
      }

      this.entity.a(reason, (float)amount);
   }

   public Location getEyeLocation() {
      Location loc = this.getLocation();
      loc.setY(loc.getY() + this.getEyeHeight());
      return loc;
   }

   public int getMaximumNoDamageTicks() {
      return this.getHandle().aQ;
   }

   public void setMaximumNoDamageTicks(int ticks) {
      this.getHandle().aQ = ticks;
   }

   public double getLastDamage() {
      return (double)this.getHandle().bh;
   }

   public void setLastDamage(double damage) {
      this.getHandle().bh = (float)damage;
   }

   public int getNoDamageTicks() {
      return this.getHandle().ak;
   }

   public void setNoDamageTicks(int ticks) {
      this.getHandle().ak = ticks;
   }

   public EntityLiving getHandle() {
      return (EntityLiving)this.entity;
   }

   public void setHandle(EntityLiving entity) {
      super.setHandle(entity);
   }

   @Override
   public String toString() {
      return "CraftLivingEntity{id=" + this.getEntityId() + 125;
   }

   public Player getKiller() {
      return this.getHandle().aX == null ? null : (Player)this.getHandle().aX.getBukkitEntity();
   }

   public boolean addPotionEffect(PotionEffect effect) {
      return this.addPotionEffect(effect, false);
   }

   public boolean addPotionEffect(PotionEffect effect, boolean force) {
      this.getHandle()
         .addEffect(
            new MobEffect(MobEffectList.a(effect.getType().getId()), effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), effect.hasParticles()),
            Cause.PLUGIN
         );
      return true;
   }

   public boolean addPotionEffects(Collection<PotionEffect> effects) {
      boolean success = true;

      for(PotionEffect effect : effects) {
         success &= this.addPotionEffect(effect);
      }

      return success;
   }

   public boolean hasPotionEffect(PotionEffectType type) {
      return this.getHandle().a(MobEffectList.a(type.getId()));
   }

   public PotionEffect getPotionEffect(PotionEffectType type) {
      MobEffect handle = this.getHandle().b(MobEffectList.a(type.getId()));
      return handle == null ? null : new PotionEffect(PotionEffectType.getById(MobEffectList.a(handle.c())), handle.d(), handle.e(), handle.f(), handle.g());
   }

   public void removePotionEffect(PotionEffectType type) {
      this.getHandle().removeEffect(MobEffectList.a(type.getId()), Cause.PLUGIN);
   }

   public Collection<PotionEffect> getActivePotionEffects() {
      List<PotionEffect> effects = new ArrayList();

      for(MobEffect handle : this.getHandle().bO.values()) {
         effects.add(new PotionEffect(PotionEffectType.getById(MobEffectList.a(handle.c())), handle.d(), handle.e(), handle.f(), handle.g()));
      }

      return effects;
   }

   public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
      return this.launchProjectile(projectile, null);
   }

   public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity) {
      Preconditions.checkState(!this.getHandle().generation, "Cannot launch projectile during world generation");
      World world = ((CraftWorld)this.getWorld()).getHandle();
      net.minecraft.world.entity.Entity launch = null;
      if (Snowball.class.isAssignableFrom(projectile)) {
         launch = new EntitySnowball(world, this.getHandle());
         ((EntityProjectile)launch).a(this.getHandle(), this.getHandle().dy(), this.getHandle().dw(), 0.0F, 1.5F, 1.0F);
      } else if (Egg.class.isAssignableFrom(projectile)) {
         launch = new EntityEgg(world, this.getHandle());
         ((EntityProjectile)launch).a(this.getHandle(), this.getHandle().dy(), this.getHandle().dw(), 0.0F, 1.5F, 1.0F);
      } else if (EnderPearl.class.isAssignableFrom(projectile)) {
         launch = new EntityEnderPearl(world, this.getHandle());
         ((EntityProjectile)launch).a(this.getHandle(), this.getHandle().dy(), this.getHandle().dw(), 0.0F, 1.5F, 1.0F);
      } else if (AbstractArrow.class.isAssignableFrom(projectile)) {
         if (TippedArrow.class.isAssignableFrom(projectile)) {
            launch = new EntityTippedArrow(world, this.getHandle());
            ((EntityTippedArrow)launch).setPotionType(CraftPotionUtil.fromBukkit(new PotionData(PotionType.WATER, false, false)));
         } else if (SpectralArrow.class.isAssignableFrom(projectile)) {
            launch = new EntitySpectralArrow(world, this.getHandle());
         } else if (Trident.class.isAssignableFrom(projectile)) {
            launch = new EntityThrownTrident(world, this.getHandle(), new ItemStack(Items.uP));
         } else {
            launch = new EntityTippedArrow(world, this.getHandle());
         }

         ((EntityArrow)launch).a(this.getHandle(), this.getHandle().dy(), this.getHandle().dw(), 0.0F, 3.0F, 1.0F);
      } else if (ThrownPotion.class.isAssignableFrom(projectile)) {
         if (LingeringPotion.class.isAssignableFrom(projectile)) {
            launch = new EntityPotion(world, this.getHandle());
            ((EntityPotion)launch).a(CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.LINGERING_POTION, 1)));
         } else {
            launch = new EntityPotion(world, this.getHandle());
            ((EntityPotion)launch).a(CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.SPLASH_POTION, 1)));
         }

         ((EntityProjectile)launch).a(this.getHandle(), this.getHandle().dy(), this.getHandle().dw(), -20.0F, 0.5F, 1.0F);
      } else if (ThrownExpBottle.class.isAssignableFrom(projectile)) {
         launch = new EntityThrownExpBottle(world, this.getHandle());
         ((EntityProjectile)launch).a(this.getHandle(), this.getHandle().dy(), this.getHandle().dw(), -20.0F, 0.7F, 1.0F);
      } else if (FishHook.class.isAssignableFrom(projectile) && this.getHandle() instanceof EntityHuman) {
         launch = new EntityFishingHook((EntityHuman)this.getHandle(), world, 0, 0);
      } else if (Fireball.class.isAssignableFrom(projectile)) {
         Location location = this.getEyeLocation();
         Vector direction = location.getDirection().multiply(10);
         if (SmallFireball.class.isAssignableFrom(projectile)) {
            launch = new EntitySmallFireball(world, this.getHandle(), direction.getX(), direction.getY(), direction.getZ());
         } else if (WitherSkull.class.isAssignableFrom(projectile)) {
            launch = new EntityWitherSkull(world, this.getHandle(), direction.getX(), direction.getY(), direction.getZ());
         } else if (DragonFireball.class.isAssignableFrom(projectile)) {
            launch = new EntityDragonFireball(world, this.getHandle(), direction.getX(), direction.getY(), direction.getZ());
         } else {
            launch = new EntityLargeFireball(world, this.getHandle(), direction.getX(), direction.getY(), direction.getZ(), 1);
         }

         ((EntityFireball)launch).projectileSource = this;
         launch.b(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
      } else if (LlamaSpit.class.isAssignableFrom(projectile)) {
         Location location = this.getEyeLocation();
         Vector direction = location.getDirection();
         launch = EntityTypes.ak.a(world);
         ((EntityLlamaSpit)launch).b((net.minecraft.world.entity.Entity)this.getHandle());
         ((EntityLlamaSpit)launch).c(direction.getX(), direction.getY(), direction.getZ(), 1.5F, 10.0F);
         launch.b(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
      } else if (ShulkerBullet.class.isAssignableFrom(projectile)) {
         Location location = this.getEyeLocation();
         launch = new EntityShulkerBullet(world, this.getHandle(), null, null);
         launch.b(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
      } else if (Firework.class.isAssignableFrom(projectile)) {
         Location location = this.getEyeLocation();
         launch = new EntityFireworks(world, ItemStack.b, this.getHandle());
         launch.b(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
      }

      Validate.notNull(launch, "Projectile not supported");
      if (velocity != null) {
         ((Projectile)launch.getBukkitEntity()).setVelocity(velocity);
      }

      world.b(launch);
      return (T)launch.getBukkitEntity();
   }

   public EntityType getType() {
      return EntityType.UNKNOWN;
   }

   public boolean hasLineOfSight(Entity other) {
      Preconditions.checkState(!this.getHandle().generation, "Cannot check line of sight during world generation");
      return this.getHandle().B(((CraftEntity)other).getHandle());
   }

   public boolean getRemoveWhenFarAway() {
      return this.getHandle() instanceof EntityInsentient && !((EntityInsentient)this.getHandle()).fB();
   }

   public void setRemoveWhenFarAway(boolean remove) {
      if (this.getHandle() instanceof EntityInsentient) {
         ((EntityInsentient)this.getHandle()).setPersistenceRequired(!remove);
      }
   }

   public EntityEquipment getEquipment() {
      return this.equipment;
   }

   public void setCanPickupItems(boolean pickup) {
      if (this.getHandle() instanceof EntityInsentient) {
         ((EntityInsentient)this.getHandle()).s(pickup);
      } else {
         this.getHandle().bukkitPickUpLoot = pickup;
      }
   }

   public boolean getCanPickupItems() {
      return this.getHandle() instanceof EntityInsentient ? ((EntityInsentient)this.getHandle()).fA() : this.getHandle().bukkitPickUpLoot;
   }

   @Override
   public boolean teleport(Location location, TeleportCause cause) {
      return this.getHealth() == 0.0 ? false : super.teleport(location, cause);
   }

   public boolean isLeashed() {
      if (!(this.getHandle() instanceof EntityInsentient)) {
         return false;
      } else {
         return ((EntityInsentient)this.getHandle()).fJ() != null;
      }
   }

   public Entity getLeashHolder() throws IllegalStateException {
      if (!this.isLeashed()) {
         throw new IllegalStateException("Entity not leashed");
      } else {
         return ((EntityInsentient)this.getHandle()).fJ().getBukkitEntity();
      }
   }

   private boolean unleash() {
      if (!this.isLeashed()) {
         return false;
      } else {
         ((EntityInsentient)this.getHandle()).a(true, false);
         return true;
      }
   }

   public boolean setLeashHolder(Entity holder) {
      if (this.getHandle().generation || this.getHandle() instanceof EntityWither || !(this.getHandle() instanceof EntityInsentient)) {
         return false;
      } else if (holder == null) {
         return this.unleash();
      } else if (holder.isDead()) {
         return false;
      } else {
         this.unleash();
         ((EntityInsentient)this.getHandle()).b(((CraftEntity)holder).getHandle(), true);
         return true;
      }
   }

   public boolean isGliding() {
      return this.getHandle().h(7);
   }

   public void setGliding(boolean gliding) {
      this.getHandle().b(7, gliding);
   }

   public boolean isSwimming() {
      return this.getHandle().bV();
   }

   public void setSwimming(boolean swimming) {
      this.getHandle().h(swimming);
   }

   public boolean isRiptiding() {
      return this.getHandle().fa();
   }

   public boolean isSleeping() {
      return this.getHandle().fu();
   }

   public boolean isClimbing() {
      Preconditions.checkState(!this.getHandle().generation, "Cannot check if climbing during world generation");
      return this.getHandle().z_();
   }

   public AttributeInstance getAttribute(Attribute attribute) {
      return this.getHandle().craftAttributes.getAttribute(attribute);
   }

   public void setAI(boolean ai) {
      if (this.getHandle() instanceof EntityInsentient) {
         ((EntityInsentient)this.getHandle()).t(!ai);
      }
   }

   public boolean hasAI() {
      return this.getHandle() instanceof EntityInsentient ? !((EntityInsentient)this.getHandle()).fK() : false;
   }

   public void attack(Entity target) {
      Preconditions.checkArgument(target != null, "target == null");
      Preconditions.checkState(!this.getHandle().generation, "Cannot attack during world generation");
      if (this.getHandle() instanceof EntityHuman) {
         ((EntityHuman)this.getHandle()).d(((CraftEntity)target).getHandle());
      } else {
         this.getHandle().z(((CraftEntity)target).getHandle());
      }
   }

   public void swingMainHand() {
      Preconditions.checkState(!this.getHandle().generation, "Cannot swing hand during world generation");
      this.getHandle().a(EnumHand.a, true);
   }

   public void swingOffHand() {
      Preconditions.checkState(!this.getHandle().generation, "Cannot swing hand during world generation");
      this.getHandle().a(EnumHand.b, true);
   }

   public void setCollidable(boolean collidable) {
      this.getHandle().collides = collidable;
   }

   public boolean isCollidable() {
      return this.getHandle().collides;
   }

   public Set<UUID> getCollidableExemptions() {
      return this.getHandle().collidableExemptions;
   }

   public <T> T getMemory(MemoryKey<T> memoryKey) {
      return this.getHandle().dH().c(CraftMemoryKey.fromMemoryKey(memoryKey)).map(CraftMemoryMapper::fromNms).orElse((T)null);
   }

   public <T> void setMemory(MemoryKey<T> memoryKey, T t) {
      this.getHandle().dH().a(CraftMemoryKey.fromMemoryKey(memoryKey), CraftMemoryMapper.toNms(t));
   }

   public Sound getHurtSound() {
      SoundEffect sound = this.getHandle().getHurtSound0(this.getHandle().dG().n());
      return sound != null ? CraftSound.getBukkit(sound) : null;
   }

   public Sound getDeathSound() {
      SoundEffect sound = this.getHandle().getDeathSound0();
      return sound != null ? CraftSound.getBukkit(sound) : null;
   }

   public Sound getFallDamageSound(int fallHeight) {
      return CraftSound.getBukkit(this.getHandle().getFallDamageSound0(fallHeight));
   }

   public Sound getFallDamageSoundSmall() {
      return CraftSound.getBukkit(this.getHandle().ey().a());
   }

   public Sound getFallDamageSoundBig() {
      return CraftSound.getBukkit(this.getHandle().ey().b());
   }

   public Sound getDrinkingSound(org.bukkit.inventory.ItemStack itemStack) {
      Preconditions.checkArgument(itemStack != null, "itemStack must not be null");
      return CraftSound.getBukkit(this.getHandle().getDrinkingSound0(CraftItemStack.asNMSCopy(itemStack)));
   }

   public Sound getEatingSound(org.bukkit.inventory.ItemStack itemStack) {
      Preconditions.checkArgument(itemStack != null, "itemStack must not be null");
      return CraftSound.getBukkit(this.getHandle().getEatingSound0(CraftItemStack.asNMSCopy(itemStack)));
   }

   public boolean canBreatheUnderwater() {
      return this.getHandle().dK();
   }

   public EntityCategory getCategory() {
      EnumMonsterType type = this.getHandle().eJ();
      if (type == EnumMonsterType.a) {
         return EntityCategory.NONE;
      } else if (type == EnumMonsterType.b) {
         return EntityCategory.UNDEAD;
      } else if (type == EnumMonsterType.c) {
         return EntityCategory.ARTHROPOD;
      } else if (type == EnumMonsterType.d) {
         return EntityCategory.ILLAGER;
      } else if (type == EnumMonsterType.e) {
         return EntityCategory.WATER;
      } else {
         throw new UnsupportedOperationException("Unsupported monster type: " + type + ". This is a bug, report this to Spigot.");
      }
   }

   public boolean isInvisible() {
      return this.getHandle().ca();
   }

   public void setInvisible(boolean invisible) {
      this.getHandle().persistentInvisibility = invisible;
      this.getHandle().b(5, invisible);
   }
}
