package org.bukkit.craftbukkit.v1_19_R3;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityAreaEffectCloud;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.entity.decoration.EntityHanging;
import net.minecraft.world.entity.decoration.EntityItemFrame;
import net.minecraft.world.entity.decoration.EntityLeash;
import net.minecraft.world.entity.decoration.EntityPainting;
import net.minecraft.world.entity.item.EntityFallingBlock;
import net.minecraft.world.entity.item.EntityTNTPrimed;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.entity.projectile.EntityEgg;
import net.minecraft.world.entity.projectile.EntityEnderSignal;
import net.minecraft.world.entity.projectile.EntityEvokerFangs;
import net.minecraft.world.entity.projectile.EntityFireball;
import net.minecraft.world.entity.projectile.EntityFireworks;
import net.minecraft.world.entity.projectile.EntityPotion;
import net.minecraft.world.entity.projectile.EntitySnowball;
import net.minecraft.world.entity.projectile.EntityTippedArrow;
import net.minecraft.world.entity.vehicle.EntityMinecartChest;
import net.minecraft.world.entity.vehicle.EntityMinecartCommandBlock;
import net.minecraft.world.entity.vehicle.EntityMinecartFurnace;
import net.minecraft.world.entity.vehicle.EntityMinecartHopper;
import net.minecraft.world.entity.vehicle.EntityMinecartMobSpawner;
import net.minecraft.world.entity.vehicle.EntityMinecartRideable;
import net.minecraft.world.entity.vehicle.EntityMinecartTNT;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.BlockChorusFlower;
import net.minecraft.world.level.block.BlockDiodeAbstract;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.RegionAccessor;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.potion.CraftPotionUtil;
import org.bukkit.craftbukkit.v1_19_R3.util.BlockStateListPopulator;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_19_R3.util.RandomSourceWrapper;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Ambient;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Camel;
import org.bukkit.entity.Cat;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.ChestBoat;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cod;
import org.bukkit.entity.ComplexLivingEntity;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Display;
import org.bukkit.entity.Dolphin;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Egg;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.GlowItemFrame;
import org.bukkit.entity.GlowSquid;
import org.bukkit.entity.Goat;
import org.bukkit.entity.Golem;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Illager;
import org.bukkit.entity.Illusioner;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Marker;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Mule;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.PolarBear;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.PufferFish;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Ravager;
import org.bukkit.entity.Salmon;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.Slime;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Sniffer;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.Spellcaster;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Stray;
import org.bukkit.entity.Strider;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Tadpole;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.TippedArrow;
import org.bukkit.entity.TraderLlama;
import org.bukkit.entity.Trident;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.Turtle;
import org.bukkit.entity.Vex;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.entity.Warden;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zoglin;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Consumer;
import org.bukkit.util.Vector;

public abstract class CraftRegionAccessor implements RegionAccessor {
   public abstract GeneratorAccessSeed getHandle();

   public boolean isNormalWorld() {
      return this.getHandle() instanceof WorldServer;
   }

   public Biome getBiome(Location location) {
      return this.getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ());
   }

   public Biome getBiome(int x, int y, int z) {
      return CraftBlock.biomeBaseToBiome(this.getHandle().u_().d(Registries.an), this.getHandle().getNoiseBiome(x >> 2, y >> 2, z >> 2));
   }

   public void setBiome(Location location, Biome biome) {
      this.setBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ(), biome);
   }

   public void setBiome(int x, int y, int z, Biome biome) {
      Preconditions.checkArgument(biome != Biome.CUSTOM, "Cannot set the biome to %s", biome);
      Holder<BiomeBase> biomeBase = CraftBlock.biomeToBiomeBase(this.getHandle().u_().d(Registries.an), biome);
      this.setBiome(x, y, z, biomeBase);
   }

   public abstract void setBiome(int var1, int var2, int var3, Holder<BiomeBase> var4);

   public BlockState getBlockState(Location location) {
      return this.getBlockState(location.getBlockX(), location.getBlockY(), location.getBlockZ());
   }

   public BlockState getBlockState(int x, int y, int z) {
      return CraftBlock.at(this.getHandle(), new BlockPosition(x, y, z)).getState();
   }

   public BlockData getBlockData(Location location) {
      return this.getBlockData(location.getBlockX(), location.getBlockY(), location.getBlockZ());
   }

   public BlockData getBlockData(int x, int y, int z) {
      return CraftBlockData.fromData(this.getData(x, y, z));
   }

   public Material getType(Location location) {
      return this.getType(location.getBlockX(), location.getBlockY(), location.getBlockZ());
   }

   public Material getType(int x, int y, int z) {
      return CraftMagicNumbers.getMaterial(this.getData(x, y, z).b());
   }

   private IBlockData getData(int x, int y, int z) {
      return this.getHandle().a_(new BlockPosition(x, y, z));
   }

   public void setBlockData(Location location, BlockData blockData) {
      this.setBlockData(location.getBlockX(), location.getBlockY(), location.getBlockZ(), blockData);
   }

   public void setBlockData(int x, int y, int z, BlockData blockData) {
      GeneratorAccessSeed world = this.getHandle();
      BlockPosition pos = new BlockPosition(x, y, z);
      IBlockData old = this.getHandle().a_(pos);
      CraftBlock.setTypeAndData(world, pos, old, ((CraftBlockData)blockData).getState(), true);
   }

   public void setType(Location location, Material material) {
      this.setType(location.getBlockX(), location.getBlockY(), location.getBlockZ(), material);
   }

   public void setType(int x, int y, int z, Material material) {
      this.setBlockData(x, y, z, material.createBlockData());
   }

   public boolean generateTree(Location location, Random random, TreeType treeType) {
      BlockPosition pos = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
      return this.generateTree(this.getHandle(), this.getHandle().getMinecraftWorld().k().g(), pos, new RandomSourceWrapper(random), treeType);
   }

   public boolean generateTree(Location location, Random random, TreeType treeType, Consumer<BlockState> consumer) {
      return this.generateTree(location, random, treeType, consumer == null ? null : block -> {
         consumer.accept(block);
         return true;
      });
   }

   public boolean generateTree(Location location, Random random, TreeType treeType, Predicate<BlockState> predicate) {
      BlockPosition pos = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
      BlockStateListPopulator populator = new BlockStateListPopulator(this.getHandle());
      boolean result = this.generateTree(populator, this.getHandle().getMinecraftWorld().k().g(), pos, new RandomSourceWrapper(random), treeType);
      populator.refreshTiles();

      for(BlockState blockState : populator.getList()) {
         if (predicate == null || predicate.test(blockState)) {
            blockState.update(true, true);
         }
      }

      return result;
   }

   public boolean generateTree(GeneratorAccessSeed access, ChunkGenerator chunkGenerator, BlockPosition pos, RandomSource random, TreeType treeType) {
      ResourceKey<WorldGenFeatureConfigured<?, ?>> gen;
      switch(treeType) {
         case TREE:
         default:
            gen = TreeFeatures.g;
            break;
         case BIG_TREE:
            gen = TreeFeatures.n;
            break;
         case REDWOOD:
            gen = TreeFeatures.k;
            break;
         case TALL_REDWOOD:
            gen = TreeFeatures.l;
            break;
         case BIRCH:
            gen = TreeFeatures.i;
            break;
         case JUNGLE:
            gen = TreeFeatures.p;
            break;
         case SMALL_JUNGLE:
            gen = TreeFeatures.o;
            break;
         case COCOA_TREE:
            gen = TreeFeatures.m;
            break;
         case JUNGLE_BUSH:
            gen = TreeFeatures.v;
            break;
         case RED_MUSHROOM:
            gen = TreeFeatures.f;
            break;
         case BROWN_MUSHROOM:
            gen = TreeFeatures.e;
            break;
         case SWAMP:
            gen = TreeFeatures.u;
            break;
         case ACACIA:
            gen = TreeFeatures.j;
            break;
         case DARK_OAK:
            gen = TreeFeatures.h;
            break;
         case MEGA_REDWOOD:
            gen = TreeFeatures.r;
            break;
         case TALL_BIRCH:
            gen = TreeFeatures.s;
            break;
         case CHORUS_PLANT:
            BlockChorusFlower.a(access, pos, random, 8);
            return true;
         case CRIMSON_FUNGUS:
            gen = TreeFeatures.b;
            break;
         case WARPED_FUNGUS:
            gen = TreeFeatures.d;
            break;
         case AZALEA:
            gen = TreeFeatures.w;
            break;
         case MANGROVE:
            gen = TreeFeatures.x;
            break;
         case TALL_MANGROVE:
            gen = TreeFeatures.y;
            break;
         case CHERRY:
            gen = TreeFeatures.z;
      }

      Holder<WorldGenFeatureConfigured<?, ?>> holder = access.u_().d(Registries.aq).b(gen).orElse(null);
      return holder != null ? holder.a().a(access, chunkGenerator, random, pos) : false;
   }

   public Entity spawnEntity(Location location, EntityType entityType) {
      return this.spawn(location, entityType.getEntityClass());
   }

   public Entity spawnEntity(Location loc, EntityType type, boolean randomizeData) {
      return this.spawn(loc, type.getEntityClass(), null, SpawnReason.CUSTOM, randomizeData);
   }

   public List<Entity> getEntities() {
      List<Entity> list = new ArrayList();
      this.getNMSEntities().forEach(entity -> {
         Entity bukkitEntity = entity.getBukkitEntity();
         if (bukkitEntity != null && (!this.isNormalWorld() || bukkitEntity.isValid())) {
            list.add(bukkitEntity);
         }
      });
      return list;
   }

   public List<LivingEntity> getLivingEntities() {
      List<LivingEntity> list = new ArrayList();
      this.getNMSEntities().forEach(entity -> {
         Entity bukkitEntity = entity.getBukkitEntity();
         if (bukkitEntity != null && bukkitEntity instanceof LivingEntity && (!this.isNormalWorld() || bukkitEntity.isValid())) {
            list.add((LivingEntity)bukkitEntity);
         }
      });
      return list;
   }

   public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz) {
      Collection<T> list = new ArrayList();
      this.getNMSEntities().forEach(entity -> {
         Entity bukkitEntity = entity.getBukkitEntity();
         if (bukkitEntity != null) {
            Class<?> bukkitClass = bukkitEntity.getClass();
            if (clazz.isAssignableFrom(bukkitClass) && (!this.isNormalWorld() || bukkitEntity.isValid())) {
               list.add(bukkitEntity);
            }
         }
      });
      return list;
   }

   public Collection<Entity> getEntitiesByClasses(Class<?>... classes) {
      Collection<Entity> list = new ArrayList();
      this.getNMSEntities().forEach(entity -> {
         Entity bukkitEntity = entity.getBukkitEntity();
         if (bukkitEntity != null) {
            Class<?> bukkitClass = bukkitEntity.getClass();

            for(Class<?> clazz : classes) {
               if (clazz.isAssignableFrom(bukkitClass)) {
                  if (!this.isNormalWorld() || bukkitEntity.isValid()) {
                     list.add(bukkitEntity);
                  }
                  break;
               }
            }
         }
      });
      return list;
   }

   public abstract Iterable<net.minecraft.world.entity.Entity> getNMSEntities();

   public <T extends Entity> T spawn(Location location, Class<T> clazz) throws IllegalArgumentException {
      return this.spawn(location, clazz, null, SpawnReason.CUSTOM);
   }

   public <T extends Entity> T spawn(Location location, Class<T> clazz, Consumer<T> function) throws IllegalArgumentException {
      return this.spawn(location, clazz, function, SpawnReason.CUSTOM);
   }

   public <T extends Entity> T spawn(Location location, Class<T> clazz, boolean randomizeData, Consumer<T> function) throws IllegalArgumentException {
      return this.spawn(location, clazz, function, SpawnReason.CUSTOM, randomizeData);
   }

   public <T extends Entity> T spawn(Location location, Class<T> clazz, Consumer<T> function, SpawnReason reason) throws IllegalArgumentException {
      return this.spawn(location, clazz, function, reason, true);
   }

   public <T extends Entity> T spawn(Location location, Class<T> clazz, Consumer<T> function, SpawnReason reason, boolean randomizeData) throws IllegalArgumentException {
      net.minecraft.world.entity.Entity entity = this.createEntity(location, clazz, randomizeData);
      return this.addEntity(entity, reason, function, randomizeData);
   }

   public <T extends Entity> T addEntity(net.minecraft.world.entity.Entity entity, SpawnReason reason) throws IllegalArgumentException {
      return this.addEntity(entity, reason, null, true);
   }

   public <T extends Entity> T addEntity(net.minecraft.world.entity.Entity entity, SpawnReason reason, Consumer<T> function, boolean randomizeData) throws IllegalArgumentException {
      Preconditions.checkArgument(entity != null, "Cannot spawn null entity");
      if (randomizeData && entity instanceof EntityInsentient) {
         ((EntityInsentient)entity).a(this.getHandle(), this.getHandle().d_(entity.dg()), EnumMobSpawn.n, null, null);
      }

      if (!this.isNormalWorld()) {
         entity.generation = true;
      }

      if (function != null) {
         function.accept(entity.getBukkitEntity());
      }

      this.addEntityToWorld(entity, reason);
      return (T)entity.getBukkitEntity();
   }

   public abstract void addEntityToWorld(net.minecraft.world.entity.Entity var1, SpawnReason var2);

   public net.minecraft.world.entity.Entity createEntity(Location location, Class<? extends Entity> clazz) throws IllegalArgumentException {
      return this.createEntity(location, clazz, true);
   }

   public net.minecraft.world.entity.Entity createEntity(Location location, Class<? extends Entity> clazz, boolean randomizeData) throws IllegalArgumentException {
      if (location != null && clazz != null) {
         net.minecraft.world.entity.Entity entity = null;
         World world = this.getHandle().getMinecraftWorld();
         double x = location.getX();
         double y = location.getY();
         double z = location.getZ();
         float pitch = location.getPitch();
         float yaw = location.getYaw();
         if (Boat.class.isAssignableFrom(clazz)) {
            if (ChestBoat.class.isAssignableFrom(clazz)) {
               entity = EntityTypes.o.a(world);
            } else {
               entity = EntityTypes.k.a(world);
            }

            entity.b(x, y, z, yaw, pitch);
         } else if (FallingBlock.class.isAssignableFrom(clazz)) {
            BlockPosition pos = BlockPosition.a(x, y, z);
            entity = EntityFallingBlock.a(world, pos, this.getHandle().a_(pos));
         } else if (Projectile.class.isAssignableFrom(clazz)) {
            if (Snowball.class.isAssignableFrom(clazz)) {
               entity = new EntitySnowball(world, x, y, z);
            } else if (Egg.class.isAssignableFrom(clazz)) {
               entity = new EntityEgg(world, x, y, z);
            } else if (AbstractArrow.class.isAssignableFrom(clazz)) {
               if (TippedArrow.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.e.a(world);
                  ((EntityTippedArrow)entity).setPotionType(CraftPotionUtil.fromBukkit(new PotionData(PotionType.WATER, false, false)));
               } else if (SpectralArrow.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.aR.a(world);
               } else if (Trident.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.bb.a(world);
               } else {
                  entity = EntityTypes.e.a(world);
               }

               entity.b(x, y, z, 0.0F, 0.0F);
            } else if (ThrownExpBottle.class.isAssignableFrom(clazz)) {
               entity = EntityTypes.I.a(world);
               entity.b(x, y, z, 0.0F, 0.0F);
            } else if (EnderPearl.class.isAssignableFrom(clazz)) {
               entity = EntityTypes.D.a(world);
               entity.b(x, y, z, 0.0F, 0.0F);
            } else if (ThrownPotion.class.isAssignableFrom(clazz)) {
               if (LingeringPotion.class.isAssignableFrom(clazz)) {
                  entity = new EntityPotion(world, x, y, z);
                  ((EntityPotion)entity).a(CraftItemStack.asNMSCopy(new ItemStack(Material.LINGERING_POTION, 1)));
               } else {
                  entity = new EntityPotion(world, x, y, z);
                  ((EntityPotion)entity).a(CraftItemStack.asNMSCopy(new ItemStack(Material.SPLASH_POTION, 1)));
               }
            } else if (Fireball.class.isAssignableFrom(clazz)) {
               if (SmallFireball.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.aM.a(world);
               } else if (WitherSkull.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.bm.a(world);
               } else if (DragonFireball.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.x.a(world);
               } else {
                  entity = EntityTypes.ag.a(world);
               }

               entity.b(x, y, z, yaw, pitch);
               Vector direction = location.getDirection().multiply(10);
               ((EntityFireball)entity).setDirection(direction.getX(), direction.getY(), direction.getZ());
            } else if (ShulkerBullet.class.isAssignableFrom(clazz)) {
               entity = EntityTypes.aH.a(world);
               entity.b(x, y, z, yaw, pitch);
            } else if (LlamaSpit.class.isAssignableFrom(clazz)) {
               entity = EntityTypes.ak.a(world);
               entity.b(x, y, z, yaw, pitch);
            } else if (Firework.class.isAssignableFrom(clazz)) {
               entity = new EntityFireworks(world, x, y, z, net.minecraft.world.item.ItemStack.b);
            }
         } else if (Minecart.class.isAssignableFrom(clazz)) {
            if (PoweredMinecart.class.isAssignableFrom(clazz)) {
               entity = new EntityMinecartFurnace(world, x, y, z);
            } else if (StorageMinecart.class.isAssignableFrom(clazz)) {
               entity = new EntityMinecartChest(world, x, y, z);
            } else if (ExplosiveMinecart.class.isAssignableFrom(clazz)) {
               entity = new EntityMinecartTNT(world, x, y, z);
            } else if (HopperMinecart.class.isAssignableFrom(clazz)) {
               entity = new EntityMinecartHopper(world, x, y, z);
            } else if (SpawnerMinecart.class.isAssignableFrom(clazz)) {
               entity = new EntityMinecartMobSpawner(world, x, y, z);
            } else if (CommandMinecart.class.isAssignableFrom(clazz)) {
               entity = new EntityMinecartCommandBlock(world, x, y, z);
            } else {
               entity = new EntityMinecartRideable(world, x, y, z);
            }
         } else if (EnderSignal.class.isAssignableFrom(clazz)) {
            entity = new EntityEnderSignal(world, x, y, z);
         } else if (EnderCrystal.class.isAssignableFrom(clazz)) {
            entity = EntityTypes.B.a(world);
            entity.b(x, y, z, 0.0F, 0.0F);
         } else if (LivingEntity.class.isAssignableFrom(clazz)) {
            if (Chicken.class.isAssignableFrom(clazz)) {
               entity = EntityTypes.q.a(world);
            } else if (Cow.class.isAssignableFrom(clazz)) {
               if (MushroomCow.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.ao.a(world);
               } else {
                  entity = EntityTypes.t.a(world);
               }
            } else if (Golem.class.isAssignableFrom(clazz)) {
               if (Snowman.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.aO.a(world);
               } else if (IronGolem.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.ac.a(world);
               } else if (Shulker.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.aG.a(world);
               }
            } else if (Creeper.class.isAssignableFrom(clazz)) {
               entity = EntityTypes.u.a(world);
            } else if (Ghast.class.isAssignableFrom(clazz)) {
               entity = EntityTypes.Q.a(world);
            } else if (Pig.class.isAssignableFrom(clazz)) {
               entity = EntityTypes.av.a(world);
            } else if (!Player.class.isAssignableFrom(clazz)) {
               if (Sheep.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.aF.a(world);
               } else if (AbstractHorse.class.isAssignableFrom(clazz)) {
                  if (ChestedHorse.class.isAssignableFrom(clazz)) {
                     if (Donkey.class.isAssignableFrom(clazz)) {
                        entity = EntityTypes.w.a(world);
                     } else if (Mule.class.isAssignableFrom(clazz)) {
                        entity = EntityTypes.ap.a(world);
                     } else if (Llama.class.isAssignableFrom(clazz)) {
                        if (TraderLlama.class.isAssignableFrom(clazz)) {
                           entity = EntityTypes.ba.a(world);
                        } else {
                           entity = EntityTypes.aj.a(world);
                        }
                     }
                  } else if (SkeletonHorse.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.aK.a(world);
                  } else if (ZombieHorse.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.bq.a(world);
                  } else if (Camel.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.l.a(world);
                  } else {
                     entity = EntityTypes.Y.a(world);
                  }
               } else if (AbstractSkeleton.class.isAssignableFrom(clazz)) {
                  if (Stray.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.aU.a(world);
                  } else if (WitherSkeleton.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.bl.a(world);
                  } else if (Skeleton.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.aJ.a(world);
                  }
               } else if (Slime.class.isAssignableFrom(clazz)) {
                  if (MagmaCube.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.al.a(world);
                  } else {
                     entity = EntityTypes.aL.a(world);
                  }
               } else if (Spider.class.isAssignableFrom(clazz)) {
                  if (CaveSpider.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.n.a(world);
                  } else {
                     entity = EntityTypes.aS.a(world);
                  }
               } else if (Squid.class.isAssignableFrom(clazz)) {
                  if (GlowSquid.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.T.a(world);
                  } else {
                     entity = EntityTypes.aT.a(world);
                  }
               } else if (Tameable.class.isAssignableFrom(clazz)) {
                  if (Wolf.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.bn.a(world);
                  } else if (Parrot.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.at.a(world);
                  } else if (Cat.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.m.a(world);
                  }
               } else if (PigZombie.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.bs.a(world);
               } else if (Zombie.class.isAssignableFrom(clazz)) {
                  if (Husk.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.Z.a(world);
                  } else if (ZombieVillager.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.br.a(world);
                  } else if (Drowned.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.y.a(world);
                  } else {
                     entity = new EntityZombie(world);
                  }
               } else if (Giant.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.R.a(world);
               } else if (Silverfish.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.aI.a(world);
               } else if (Enderman.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.E.a(world);
               } else if (Blaze.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.i.a(world);
               } else if (AbstractVillager.class.isAssignableFrom(clazz)) {
                  if (Villager.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.bf.a(world);
                  } else if (WanderingTrader.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.bh.a(world);
                  }
               } else if (Witch.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.bj.a(world);
               } else if (Wither.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.bk.a(world);
               } else if (ComplexLivingEntity.class.isAssignableFrom(clazz)) {
                  if (EnderDragon.class.isAssignableFrom(clazz)) {
                     if (!this.isNormalWorld()) {
                        throw new IllegalArgumentException("Cannot spawn entity " + clazz.getName() + " during world generation");
                     }

                     entity = EntityTypes.C.a((World)this.getHandle().getMinecraftWorld());
                  }
               } else if (Ambient.class.isAssignableFrom(clazz)) {
                  if (Bat.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.g.a(world);
                  }
               } else if (Rabbit.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.aC.a(world);
               } else if (Endermite.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.F.a(world);
               } else if (Guardian.class.isAssignableFrom(clazz)) {
                  if (ElderGuardian.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.A.a(world);
                  } else {
                     entity = EntityTypes.V.a(world);
                  }
               } else if (ArmorStand.class.isAssignableFrom(clazz)) {
                  entity = new EntityArmorStand(world, x, y, z);
               } else if (PolarBear.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.az.a(world);
               } else if (Vex.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.be.a(world);
               } else if (Illager.class.isAssignableFrom(clazz)) {
                  if (Spellcaster.class.isAssignableFrom(clazz)) {
                     if (Evoker.class.isAssignableFrom(clazz)) {
                        entity = EntityTypes.G.a(world);
                     } else if (Illusioner.class.isAssignableFrom(clazz)) {
                        entity = EntityTypes.aa.a(world);
                     }
                  } else if (Vindicator.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.bg.a(world);
                  } else if (Pillager.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.ay.a(world);
                  }
               } else if (Turtle.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.bd.a(world);
               } else if (Phantom.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.au.a(world);
               } else if (Fish.class.isAssignableFrom(clazz)) {
                  if (Cod.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.r.a(world);
                  } else if (PufferFish.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.aB.a(world);
                  } else if (Salmon.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.aE.a(world);
                  } else if (TropicalFish.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.bc.a(world);
                  } else if (Tadpole.class.isAssignableFrom(clazz)) {
                     entity = EntityTypes.aW.a(world);
                  }
               } else if (Dolphin.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.v.a(world);
               } else if (Ocelot.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.aq.a(world);
               } else if (Ravager.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.aD.a(world);
               } else if (Panda.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.as.a(world);
               } else if (Fox.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.N.a(world);
               } else if (Bee.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.h.a(world);
               } else if (Hoglin.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.W.a(world);
               } else if (Piglin.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.aw.a(world);
               } else if (PiglinBrute.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.ax.a(world);
               } else if (Strider.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.aV.a(world);
               } else if (Zoglin.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.bo.a(world);
               } else if (Axolotl.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.f.a(world);
               } else if (Goat.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.U.a(world);
               } else if (Allay.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.b.a(world);
               } else if (Frog.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.O.a(world);
               } else if (Warden.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.bi.a(world);
               } else if (Sniffer.class.isAssignableFrom(clazz)) {
                  entity = EntityTypes.aN.a(world);
               }
            }

            if (entity != null) {
               entity.a(x, y, z, yaw, pitch);
               entity.r(yaw);
            }
         } else if (Hanging.class.isAssignableFrom(clazz)) {
            if (LeashHitch.class.isAssignableFrom(clazz)) {
               entity = new EntityLeash(world, BlockPosition.a(x, y, z));
            } else {
               BlockFace face = BlockFace.SELF;
               BlockFace[] faces = new BlockFace[]{BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH};
               int width = 16;
               int height = 16;
               if (ItemFrame.class.isAssignableFrom(clazz)) {
                  width = 12;
                  height = 12;
                  faces = new BlockFace[]{BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH, BlockFace.UP, BlockFace.DOWN};
               }

               BlockPosition pos = BlockPosition.a(x, y, z);

               for(BlockFace dir : faces) {
                  IBlockData nmsBlock = this.getHandle().a_(pos.a(CraftBlock.blockFaceToNotch(dir)));
                  if (nmsBlock.d().b() || BlockDiodeAbstract.n(nmsBlock)) {
                     boolean taken = false;
                     AxisAlignedBB bb = ItemFrame.class.isAssignableFrom(clazz)
                        ? EntityItemFrame.calculateBoundingBox(null, pos, CraftBlock.blockFaceToNotch(dir).g(), width, height)
                        : EntityHanging.calculateBoundingBox(null, pos, CraftBlock.blockFaceToNotch(dir).g(), width, height);
                     List<net.minecraft.world.entity.Entity> list = this.getHandle().a_(null, bb);
                     Iterator<net.minecraft.world.entity.Entity> it = list.iterator();

                     while(!taken && it.hasNext()) {
                        net.minecraft.world.entity.Entity e = it.next();
                        if (e instanceof EntityHanging) {
                           taken = true;
                        }
                     }

                     if (!taken) {
                        face = dir;
                        break;
                     }
                  }
               }

               if (face == BlockFace.SELF) {
                  face = BlockFace.SOUTH;
                  randomizeData = false;
               }

               EnumDirection dir = CraftBlock.blockFaceToNotch(face).g();
               if (Painting.class.isAssignableFrom(clazz)) {
                  if (this.isNormalWorld() && randomizeData) {
                     entity = EntityPainting.a(world, pos, dir).orElse(null);
                  } else {
                     entity = new EntityPainting(EntityTypes.ar, this.getHandle().getMinecraftWorld());
                     entity.a(x, y, z, yaw, pitch);
                     ((EntityPainting)entity).a(dir);
                  }
               } else if (ItemFrame.class.isAssignableFrom(clazz)) {
                  if (GlowItemFrame.class.isAssignableFrom(clazz)) {
                     entity = new net.minecraft.world.entity.decoration.GlowItemFrame(world, BlockPosition.a(x, y, z), dir);
                  } else {
                     entity = new EntityItemFrame(world, BlockPosition.a(x, y, z), dir);
                  }
               }
            }
         } else if (TNTPrimed.class.isAssignableFrom(clazz)) {
            entity = new EntityTNTPrimed(world, x, y, z, null);
         } else if (ExperienceOrb.class.isAssignableFrom(clazz)) {
            entity = new EntityExperienceOrb(world, x, y, z, 0);
         } else if (LightningStrike.class.isAssignableFrom(clazz)) {
            entity = EntityTypes.ai.a(world);
            entity.d(location.getX(), location.getY(), location.getZ());
         } else if (AreaEffectCloud.class.isAssignableFrom(clazz)) {
            entity = new EntityAreaEffectCloud(world, x, y, z);
         } else if (EvokerFangs.class.isAssignableFrom(clazz)) {
            entity = new EntityEvokerFangs(world, x, y, z, (float)Math.toRadians((double)yaw), 0, null);
         } else if (Marker.class.isAssignableFrom(clazz)) {
            entity = EntityTypes.am.a(world);
            entity.e(x, y, z);
         } else if (Interaction.class.isAssignableFrom(clazz)) {
            entity = EntityTypes.ab.a(world);
            entity.e(x, y, z);
         } else if (Display.class.isAssignableFrom(clazz)) {
            if (BlockDisplay.class.isAssignableFrom(clazz)) {
               entity = EntityTypes.j.a(world);
            } else if (ItemDisplay.class.isAssignableFrom(clazz)) {
               entity = EntityTypes.ae.a(world);
            } else if (TextDisplay.class.isAssignableFrom(clazz)) {
               entity = EntityTypes.aX.a(world);
            }

            if (entity != null) {
               entity.e(x, y, z);
            }
         }

         if (entity != null) {
            return entity;
         } else {
            throw new IllegalArgumentException("Cannot spawn an entity for " + clazz.getName());
         }
      } else {
         throw new IllegalArgumentException("Location or entity class cannot be null");
      }
   }
}
