package net.minecraft.world.entity;

import com.google.common.collect.ImmutableSet;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.MathHelper;
import net.minecraft.util.datafix.fixes.DataConverterTypes;
import net.minecraft.world.entity.ambient.EntityBat;
import net.minecraft.world.entity.animal.EntityBee;
import net.minecraft.world.entity.animal.EntityCat;
import net.minecraft.world.entity.animal.EntityChicken;
import net.minecraft.world.entity.animal.EntityCod;
import net.minecraft.world.entity.animal.EntityCow;
import net.minecraft.world.entity.animal.EntityDolphin;
import net.minecraft.world.entity.animal.EntityFox;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.animal.EntityMushroomCow;
import net.minecraft.world.entity.animal.EntityOcelot;
import net.minecraft.world.entity.animal.EntityPanda;
import net.minecraft.world.entity.animal.EntityParrot;
import net.minecraft.world.entity.animal.EntityPig;
import net.minecraft.world.entity.animal.EntityPolarBear;
import net.minecraft.world.entity.animal.EntityPufferFish;
import net.minecraft.world.entity.animal.EntityRabbit;
import net.minecraft.world.entity.animal.EntitySalmon;
import net.minecraft.world.entity.animal.EntitySheep;
import net.minecraft.world.entity.animal.EntitySnowman;
import net.minecraft.world.entity.animal.EntitySquid;
import net.minecraft.world.entity.animal.EntityTropicalFish;
import net.minecraft.world.entity.animal.EntityTurtle;
import net.minecraft.world.entity.animal.EntityWolf;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.EntityHorse;
import net.minecraft.world.entity.animal.horse.EntityHorseDonkey;
import net.minecraft.world.entity.animal.horse.EntityHorseMule;
import net.minecraft.world.entity.animal.horse.EntityHorseSkeleton;
import net.minecraft.world.entity.animal.horse.EntityHorseZombie;
import net.minecraft.world.entity.animal.horse.EntityLlama;
import net.minecraft.world.entity.animal.horse.EntityLlamaTrader;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderCrystal;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.boss.wither.EntityWither;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.entity.decoration.EntityItemFrame;
import net.minecraft.world.entity.decoration.EntityLeash;
import net.minecraft.world.entity.decoration.EntityPainting;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.item.EntityFallingBlock;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.item.EntityTNTPrimed;
import net.minecraft.world.entity.monster.EntityBlaze;
import net.minecraft.world.entity.monster.EntityCaveSpider;
import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.entity.monster.EntityDrowned;
import net.minecraft.world.entity.monster.EntityEnderman;
import net.minecraft.world.entity.monster.EntityEndermite;
import net.minecraft.world.entity.monster.EntityEvoker;
import net.minecraft.world.entity.monster.EntityGhast;
import net.minecraft.world.entity.monster.EntityGiantZombie;
import net.minecraft.world.entity.monster.EntityGuardian;
import net.minecraft.world.entity.monster.EntityGuardianElder;
import net.minecraft.world.entity.monster.EntityIllagerIllusioner;
import net.minecraft.world.entity.monster.EntityMagmaCube;
import net.minecraft.world.entity.monster.EntityPhantom;
import net.minecraft.world.entity.monster.EntityPigZombie;
import net.minecraft.world.entity.monster.EntityPillager;
import net.minecraft.world.entity.monster.EntityRavager;
import net.minecraft.world.entity.monster.EntityShulker;
import net.minecraft.world.entity.monster.EntitySilverfish;
import net.minecraft.world.entity.monster.EntitySkeleton;
import net.minecraft.world.entity.monster.EntitySkeletonStray;
import net.minecraft.world.entity.monster.EntitySkeletonWither;
import net.minecraft.world.entity.monster.EntitySlime;
import net.minecraft.world.entity.monster.EntitySpider;
import net.minecraft.world.entity.monster.EntityStrider;
import net.minecraft.world.entity.monster.EntityVex;
import net.minecraft.world.entity.monster.EntityVindicator;
import net.minecraft.world.entity.monster.EntityWitch;
import net.minecraft.world.entity.monster.EntityZoglin;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.entity.monster.EntityZombieHusk;
import net.minecraft.world.entity.monster.EntityZombieVillager;
import net.minecraft.world.entity.monster.hoglin.EntityHoglin;
import net.minecraft.world.entity.monster.piglin.EntityPiglin;
import net.minecraft.world.entity.monster.piglin.EntityPiglinBrute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.EntityVillagerTrader;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityDragonFireball;
import net.minecraft.world.entity.projectile.EntityEgg;
import net.minecraft.world.entity.projectile.EntityEnderPearl;
import net.minecraft.world.entity.projectile.EntityEnderSignal;
import net.minecraft.world.entity.projectile.EntityEvokerFangs;
import net.minecraft.world.entity.projectile.EntityFireworks;
import net.minecraft.world.entity.projectile.EntityFishingHook;
import net.minecraft.world.entity.projectile.EntityLargeFireball;
import net.minecraft.world.entity.projectile.EntityLlamaSpit;
import net.minecraft.world.entity.projectile.EntityPotion;
import net.minecraft.world.entity.projectile.EntityShulkerBullet;
import net.minecraft.world.entity.projectile.EntitySmallFireball;
import net.minecraft.world.entity.projectile.EntitySnowball;
import net.minecraft.world.entity.projectile.EntitySpectralArrow;
import net.minecraft.world.entity.projectile.EntityThrownExpBottle;
import net.minecraft.world.entity.projectile.EntityThrownTrident;
import net.minecraft.world.entity.projectile.EntityTippedArrow;
import net.minecraft.world.entity.projectile.EntityWitherSkull;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.entity.vehicle.EntityMinecartChest;
import net.minecraft.world.entity.vehicle.EntityMinecartCommandBlock;
import net.minecraft.world.entity.vehicle.EntityMinecartFurnace;
import net.minecraft.world.entity.vehicle.EntityMinecartHopper;
import net.minecraft.world.entity.vehicle.EntityMinecartMobSpawner;
import net.minecraft.world.entity.vehicle.EntityMinecartRideable;
import net.minecraft.world.entity.vehicle.EntityMinecartTNT;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.pathfinder.PathfinderNormal;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.slf4j.Logger;

public class EntityTypes<T extends Entity> implements FeatureElement, EntityTypeTest<Entity, T> {
   private static final Logger bw = LogUtils.getLogger();
   public static final String a = "EntityTag";
   private final Holder.c<EntityTypes<?>> bx = BuiltInRegistries.h.f(this);
   private static final float by = 1.3964844F;
   private static final int bz = 10;
   public static final EntityTypes<Allay> b = a("allay", EntityTypes.Builder.a(Allay::new, EnumCreatureType.b).a(0.35F, 0.6F).a(8).b(2));
   public static final EntityTypes<EntityAreaEffectCloud> c = a(
      "area_effect_cloud", EntityTypes.Builder.a(EntityAreaEffectCloud::new, EnumCreatureType.h).c().a(6.0F, 0.5F).a(10).b(10)
   );
   public static final EntityTypes<EntityArmorStand> d = a(
      "armor_stand", EntityTypes.Builder.a(EntityArmorStand::new, EnumCreatureType.h).a(0.5F, 1.975F).a(10)
   );
   public static final EntityTypes<EntityTippedArrow> e = a(
      "arrow", EntityTypes.Builder.a(EntityTippedArrow::new, EnumCreatureType.h).a(0.5F, 0.5F).a(4).b(20)
   );
   public static final EntityTypes<Axolotl> f = a("axolotl", EntityTypes.Builder.a(Axolotl::new, EnumCreatureType.d).a(0.75F, 0.42F).a(10));
   public static final EntityTypes<EntityBat> g = a("bat", EntityTypes.Builder.a(EntityBat::new, EnumCreatureType.c).a(0.5F, 0.9F).a(5));
   public static final EntityTypes<EntityBee> h = a("bee", EntityTypes.Builder.a(EntityBee::new, EnumCreatureType.b).a(0.7F, 0.6F).a(8));
   public static final EntityTypes<EntityBlaze> i = a("blaze", EntityTypes.Builder.a(EntityBlaze::new, EnumCreatureType.a).c().a(0.6F, 1.8F).a(8));
   public static final EntityTypes<Display.BlockDisplay> j = a(
      "block_display", EntityTypes.Builder.a(Display.BlockDisplay::new, EnumCreatureType.h).a(0.0F, 0.0F).a(10).b(1)
   );
   public static final EntityTypes<EntityBoat> k = a("boat", EntityTypes.Builder.a(EntityBoat::new, EnumCreatureType.h).a(1.375F, 0.5625F).a(10));
   public static final EntityTypes<Camel> l = a("camel", EntityTypes.Builder.a(Camel::new, EnumCreatureType.b).a(1.7F, 2.375F).a(10).a(FeatureFlags.c));
   public static final EntityTypes<EntityCat> m = a("cat", EntityTypes.Builder.a(EntityCat::new, EnumCreatureType.b).a(0.6F, 0.7F).a(8));
   public static final EntityTypes<EntityCaveSpider> n = a("cave_spider", EntityTypes.Builder.a(EntityCaveSpider::new, EnumCreatureType.a).a(0.7F, 0.5F).a(8));
   public static final EntityTypes<ChestBoat> o = a("chest_boat", EntityTypes.Builder.a(ChestBoat::new, EnumCreatureType.h).a(1.375F, 0.5625F).a(10));
   public static final EntityTypes<EntityMinecartChest> p = a(
      "chest_minecart", EntityTypes.Builder.a(EntityMinecartChest::new, EnumCreatureType.h).a(0.98F, 0.7F).a(8)
   );
   public static final EntityTypes<EntityChicken> q = a("chicken", EntityTypes.Builder.a(EntityChicken::new, EnumCreatureType.b).a(0.4F, 0.7F).a(10));
   public static final EntityTypes<EntityCod> r = a("cod", EntityTypes.Builder.a(EntityCod::new, EnumCreatureType.g).a(0.5F, 0.3F).a(4));
   public static final EntityTypes<EntityMinecartCommandBlock> s = a(
      "command_block_minecart", EntityTypes.Builder.a(EntityMinecartCommandBlock::new, EnumCreatureType.h).a(0.98F, 0.7F).a(8)
   );
   public static final EntityTypes<EntityCow> t = a("cow", EntityTypes.Builder.a(EntityCow::new, EnumCreatureType.b).a(0.9F, 1.4F).a(10));
   public static final EntityTypes<EntityCreeper> u = a("creeper", EntityTypes.Builder.a(EntityCreeper::new, EnumCreatureType.a).a(0.6F, 1.7F).a(8));
   public static final EntityTypes<EntityDolphin> v = a("dolphin", EntityTypes.Builder.a(EntityDolphin::new, EnumCreatureType.f).a(0.9F, 0.6F));
   public static final EntityTypes<EntityHorseDonkey> w = a(
      "donkey", EntityTypes.Builder.a(EntityHorseDonkey::new, EnumCreatureType.b).a(1.3964844F, 1.5F).a(10)
   );
   public static final EntityTypes<EntityDragonFireball> x = a(
      "dragon_fireball", EntityTypes.Builder.a(EntityDragonFireball::new, EnumCreatureType.h).a(1.0F, 1.0F).a(4).b(10)
   );
   public static final EntityTypes<EntityDrowned> y = a("drowned", EntityTypes.Builder.a(EntityDrowned::new, EnumCreatureType.a).a(0.6F, 1.95F).a(8));
   public static final EntityTypes<EntityEgg> z = a("egg", EntityTypes.Builder.a(EntityEgg::new, EnumCreatureType.h).a(0.25F, 0.25F).a(4).b(10));
   public static final EntityTypes<EntityGuardianElder> A = a(
      "elder_guardian", EntityTypes.Builder.a(EntityGuardianElder::new, EnumCreatureType.a).a(1.9975F, 1.9975F).a(10)
   );
   public static final EntityTypes<EntityEnderCrystal> B = a(
      "end_crystal", EntityTypes.Builder.a(EntityEnderCrystal::new, EnumCreatureType.h).a(2.0F, 2.0F).a(16).b(Integer.MAX_VALUE)
   );
   public static final EntityTypes<EntityEnderDragon> C = a(
      "ender_dragon", EntityTypes.Builder.a(EntityEnderDragon::new, EnumCreatureType.a).c().a(16.0F, 8.0F).a(10)
   );
   public static final EntityTypes<EntityEnderPearl> D = a(
      "ender_pearl", EntityTypes.Builder.a(EntityEnderPearl::new, EnumCreatureType.h).a(0.25F, 0.25F).a(4).b(10)
   );
   public static final EntityTypes<EntityEnderman> E = a("enderman", EntityTypes.Builder.a(EntityEnderman::new, EnumCreatureType.a).a(0.6F, 2.9F).a(8));
   public static final EntityTypes<EntityEndermite> F = a("endermite", EntityTypes.Builder.a(EntityEndermite::new, EnumCreatureType.a).a(0.4F, 0.3F).a(8));
   public static final EntityTypes<EntityEvoker> G = a("evoker", EntityTypes.Builder.a(EntityEvoker::new, EnumCreatureType.a).a(0.6F, 1.95F).a(8));
   public static final EntityTypes<EntityEvokerFangs> H = a(
      "evoker_fangs", EntityTypes.Builder.a(EntityEvokerFangs::new, EnumCreatureType.h).a(0.5F, 0.8F).a(6).b(2)
   );
   public static final EntityTypes<EntityThrownExpBottle> I = a(
      "experience_bottle", EntityTypes.Builder.a(EntityThrownExpBottle::new, EnumCreatureType.h).a(0.25F, 0.25F).a(4).b(10)
   );
   public static final EntityTypes<EntityExperienceOrb> J = a(
      "experience_orb", EntityTypes.Builder.a(EntityExperienceOrb::new, EnumCreatureType.h).a(0.5F, 0.5F).a(6).b(20)
   );
   public static final EntityTypes<EntityEnderSignal> K = a(
      "eye_of_ender", EntityTypes.Builder.a(EntityEnderSignal::new, EnumCreatureType.h).a(0.25F, 0.25F).a(4).b(4)
   );
   public static final EntityTypes<EntityFallingBlock> L = a(
      "falling_block", EntityTypes.Builder.a(EntityFallingBlock::new, EnumCreatureType.h).a(0.98F, 0.98F).a(10).b(20)
   );
   public static final EntityTypes<EntityFireworks> M = a(
      "firework_rocket", EntityTypes.Builder.a(EntityFireworks::new, EnumCreatureType.h).a(0.25F, 0.25F).a(4).b(10)
   );
   public static final EntityTypes<EntityFox> N = a("fox", EntityTypes.Builder.a(EntityFox::new, EnumCreatureType.b).a(0.6F, 0.7F).a(8).a(Blocks.oe));
   public static final EntityTypes<Frog> O = a("frog", EntityTypes.Builder.a(Frog::new, EnumCreatureType.b).a(0.5F, 0.5F).a(10));
   public static final EntityTypes<EntityMinecartFurnace> P = a(
      "furnace_minecart", EntityTypes.Builder.a(EntityMinecartFurnace::new, EnumCreatureType.h).a(0.98F, 0.7F).a(8)
   );
   public static final EntityTypes<EntityGhast> Q = a("ghast", EntityTypes.Builder.a(EntityGhast::new, EnumCreatureType.a).c().a(4.0F, 4.0F).a(10));
   public static final EntityTypes<EntityGiantZombie> R = a("giant", EntityTypes.Builder.a(EntityGiantZombie::new, EnumCreatureType.a).a(3.6F, 12.0F).a(10));
   public static final EntityTypes<GlowItemFrame> S = a(
      "glow_item_frame", EntityTypes.Builder.a(GlowItemFrame::new, EnumCreatureType.h).a(0.5F, 0.5F).a(10).b(Integer.MAX_VALUE)
   );
   public static final EntityTypes<GlowSquid> T = a("glow_squid", EntityTypes.Builder.a(GlowSquid::new, EnumCreatureType.e).a(0.8F, 0.8F).a(10));
   public static final EntityTypes<Goat> U = a("goat", EntityTypes.Builder.a(Goat::new, EnumCreatureType.b).a(0.9F, 1.3F).a(10));
   public static final EntityTypes<EntityGuardian> V = a("guardian", EntityTypes.Builder.a(EntityGuardian::new, EnumCreatureType.a).a(0.85F, 0.85F).a(8));
   public static final EntityTypes<EntityHoglin> W = a("hoglin", EntityTypes.Builder.a(EntityHoglin::new, EnumCreatureType.a).a(1.3964844F, 1.4F).a(8));
   public static final EntityTypes<EntityMinecartHopper> X = a(
      "hopper_minecart", EntityTypes.Builder.a(EntityMinecartHopper::new, EnumCreatureType.h).a(0.98F, 0.7F).a(8)
   );
   public static final EntityTypes<EntityHorse> Y = a("horse", EntityTypes.Builder.a(EntityHorse::new, EnumCreatureType.b).a(1.3964844F, 1.6F).a(10));
   public static final EntityTypes<EntityZombieHusk> Z = a("husk", EntityTypes.Builder.a(EntityZombieHusk::new, EnumCreatureType.a).a(0.6F, 1.95F).a(8));
   public static final EntityTypes<EntityIllagerIllusioner> aa = a(
      "illusioner", EntityTypes.Builder.a(EntityIllagerIllusioner::new, EnumCreatureType.a).a(0.6F, 1.95F).a(8)
   );
   public static final EntityTypes<Interaction> ab = a("interaction", EntityTypes.Builder.a(Interaction::new, EnumCreatureType.h).a(0.0F, 0.0F).a(10));
   public static final EntityTypes<EntityIronGolem> ac = a("iron_golem", EntityTypes.Builder.a(EntityIronGolem::new, EnumCreatureType.h).a(1.4F, 2.7F).a(10));
   public static final EntityTypes<EntityItem> ad = a("item", EntityTypes.Builder.a(EntityItem::new, EnumCreatureType.h).a(0.25F, 0.25F).a(6).b(20));
   public static final EntityTypes<Display.ItemDisplay> ae = a(
      "item_display", EntityTypes.Builder.a(Display.ItemDisplay::new, EnumCreatureType.h).a(0.0F, 0.0F).a(10).b(1)
   );
   public static final EntityTypes<EntityItemFrame> af = a(
      "item_frame", EntityTypes.Builder.a(EntityItemFrame::new, EnumCreatureType.h).a(0.5F, 0.5F).a(10).b(Integer.MAX_VALUE)
   );
   public static final EntityTypes<EntityLargeFireball> ag = a(
      "fireball", EntityTypes.Builder.a(EntityLargeFireball::new, EnumCreatureType.h).a(1.0F, 1.0F).a(4).b(10)
   );
   public static final EntityTypes<EntityLeash> ah = a(
      "leash_knot", EntityTypes.Builder.a(EntityLeash::new, EnumCreatureType.h).b().a(0.375F, 0.5F).a(10).b(Integer.MAX_VALUE)
   );
   public static final EntityTypes<EntityLightning> ai = a(
      "lightning_bolt", EntityTypes.Builder.a(EntityLightning::new, EnumCreatureType.h).b().a(0.0F, 0.0F).a(16).b(Integer.MAX_VALUE)
   );
   public static final EntityTypes<EntityLlama> aj = a("llama", EntityTypes.Builder.a(EntityLlama::new, EnumCreatureType.b).a(0.9F, 1.87F).a(10));
   public static final EntityTypes<EntityLlamaSpit> ak = a(
      "llama_spit", EntityTypes.Builder.a(EntityLlamaSpit::new, EnumCreatureType.h).a(0.25F, 0.25F).a(4).b(10)
   );
   public static final EntityTypes<EntityMagmaCube> al = a(
      "magma_cube", EntityTypes.Builder.a(EntityMagmaCube::new, EnumCreatureType.a).c().a(2.04F, 2.04F).a(8)
   );
   public static final EntityTypes<Marker> am = a("marker", EntityTypes.Builder.a(Marker::new, EnumCreatureType.h).a(0.0F, 0.0F).a(0));
   public static final EntityTypes<EntityMinecartRideable> an = a(
      "minecart", EntityTypes.Builder.a(EntityMinecartRideable::new, EnumCreatureType.h).a(0.98F, 0.7F).a(8)
   );
   public static final EntityTypes<EntityMushroomCow> ao = a(
      "mooshroom", EntityTypes.Builder.a(EntityMushroomCow::new, EnumCreatureType.b).a(0.9F, 1.4F).a(10)
   );
   public static final EntityTypes<EntityHorseMule> ap = a("mule", EntityTypes.Builder.a(EntityHorseMule::new, EnumCreatureType.b).a(1.3964844F, 1.6F).a(8));
   public static final EntityTypes<EntityOcelot> aq = a("ocelot", EntityTypes.Builder.a(EntityOcelot::new, EnumCreatureType.b).a(0.6F, 0.7F).a(10));
   public static final EntityTypes<EntityPainting> ar = a(
      "painting", EntityTypes.Builder.a(EntityPainting::new, EnumCreatureType.h).a(0.5F, 0.5F).a(10).b(Integer.MAX_VALUE)
   );
   public static final EntityTypes<EntityPanda> as = a("panda", EntityTypes.Builder.a(EntityPanda::new, EnumCreatureType.b).a(1.3F, 1.25F).a(10));
   public static final EntityTypes<EntityParrot> at = a("parrot", EntityTypes.Builder.a(EntityParrot::new, EnumCreatureType.b).a(0.5F, 0.9F).a(8));
   public static final EntityTypes<EntityPhantom> au = a("phantom", EntityTypes.Builder.a(EntityPhantom::new, EnumCreatureType.a).a(0.9F, 0.5F).a(8));
   public static final EntityTypes<EntityPig> av = a("pig", EntityTypes.Builder.a(EntityPig::new, EnumCreatureType.b).a(0.9F, 0.9F).a(10));
   public static final EntityTypes<EntityPiglin> aw = a("piglin", EntityTypes.Builder.a(EntityPiglin::new, EnumCreatureType.a).a(0.6F, 1.95F).a(8));
   public static final EntityTypes<EntityPiglinBrute> ax = a(
      "piglin_brute", EntityTypes.Builder.a(EntityPiglinBrute::new, EnumCreatureType.a).a(0.6F, 1.95F).a(8)
   );
   public static final EntityTypes<EntityPillager> ay = a("pillager", EntityTypes.Builder.a(EntityPillager::new, EnumCreatureType.a).d().a(0.6F, 1.95F).a(8));
   public static final EntityTypes<EntityPolarBear> az = a(
      "polar_bear", EntityTypes.Builder.a(EntityPolarBear::new, EnumCreatureType.b).a(Blocks.qy).a(1.4F, 1.4F).a(10)
   );
   public static final EntityTypes<EntityPotion> aA = a("potion", EntityTypes.Builder.a(EntityPotion::new, EnumCreatureType.h).a(0.25F, 0.25F).a(4).b(10));
   public static final EntityTypes<EntityPufferFish> aB = a("pufferfish", EntityTypes.Builder.a(EntityPufferFish::new, EnumCreatureType.g).a(0.7F, 0.7F).a(4));
   public static final EntityTypes<EntityRabbit> aC = a("rabbit", EntityTypes.Builder.a(EntityRabbit::new, EnumCreatureType.b).a(0.4F, 0.5F).a(8));
   public static final EntityTypes<EntityRavager> aD = a("ravager", EntityTypes.Builder.a(EntityRavager::new, EnumCreatureType.a).a(1.95F, 2.2F).a(10));
   public static final EntityTypes<EntitySalmon> aE = a("salmon", EntityTypes.Builder.a(EntitySalmon::new, EnumCreatureType.g).a(0.7F, 0.4F).a(4));
   public static final EntityTypes<EntitySheep> aF = a("sheep", EntityTypes.Builder.a(EntitySheep::new, EnumCreatureType.b).a(0.9F, 1.3F).a(10));
   public static final EntityTypes<EntityShulker> aG = a("shulker", EntityTypes.Builder.a(EntityShulker::new, EnumCreatureType.a).c().d().a(1.0F, 1.0F).a(10));
   public static final EntityTypes<EntityShulkerBullet> aH = a(
      "shulker_bullet", EntityTypes.Builder.a(EntityShulkerBullet::new, EnumCreatureType.h).a(0.3125F, 0.3125F).a(8)
   );
   public static final EntityTypes<EntitySilverfish> aI = a("silverfish", EntityTypes.Builder.a(EntitySilverfish::new, EnumCreatureType.a).a(0.4F, 0.3F).a(8));
   public static final EntityTypes<EntitySkeleton> aJ = a("skeleton", EntityTypes.Builder.a(EntitySkeleton::new, EnumCreatureType.a).a(0.6F, 1.99F).a(8));
   public static final EntityTypes<EntityHorseSkeleton> aK = a(
      "skeleton_horse", EntityTypes.Builder.a(EntityHorseSkeleton::new, EnumCreatureType.b).a(1.3964844F, 1.6F).a(10)
   );
   public static final EntityTypes<EntitySlime> aL = a("slime", EntityTypes.Builder.a(EntitySlime::new, EnumCreatureType.a).a(2.04F, 2.04F).a(10));
   public static final EntityTypes<EntitySmallFireball> aM = a(
      "small_fireball", EntityTypes.Builder.a(EntitySmallFireball::new, EnumCreatureType.h).a(0.3125F, 0.3125F).a(4).b(10)
   );
   public static final EntityTypes<Sniffer> aN = a("sniffer", EntityTypes.Builder.a(Sniffer::new, EnumCreatureType.b).a(1.9F, 1.75F).a(10).a(FeatureFlags.c));
   public static final EntityTypes<EntitySnowman> aO = a(
      "snow_golem", EntityTypes.Builder.a(EntitySnowman::new, EnumCreatureType.h).a(Blocks.qy).a(0.7F, 1.9F).a(8)
   );
   public static final EntityTypes<EntitySnowball> aP = a(
      "snowball", EntityTypes.Builder.a(EntitySnowball::new, EnumCreatureType.h).a(0.25F, 0.25F).a(4).b(10)
   );
   public static final EntityTypes<EntityMinecartMobSpawner> aQ = a(
      "spawner_minecart", EntityTypes.Builder.a(EntityMinecartMobSpawner::new, EnumCreatureType.h).a(0.98F, 0.7F).a(8)
   );
   public static final EntityTypes<EntitySpectralArrow> aR = a(
      "spectral_arrow", EntityTypes.Builder.a(EntitySpectralArrow::new, EnumCreatureType.h).a(0.5F, 0.5F).a(4).b(20)
   );
   public static final EntityTypes<EntitySpider> aS = a("spider", EntityTypes.Builder.a(EntitySpider::new, EnumCreatureType.a).a(1.4F, 0.9F).a(8));
   public static final EntityTypes<EntitySquid> aT = a("squid", EntityTypes.Builder.a(EntitySquid::new, EnumCreatureType.f).a(0.8F, 0.8F).a(8));
   public static final EntityTypes<EntitySkeletonStray> aU = a(
      "stray", EntityTypes.Builder.a(EntitySkeletonStray::new, EnumCreatureType.a).a(0.6F, 1.99F).a(Blocks.qy).a(8)
   );
   public static final EntityTypes<EntityStrider> aV = a("strider", EntityTypes.Builder.a(EntityStrider::new, EnumCreatureType.b).c().a(0.9F, 1.7F).a(10));
   public static final EntityTypes<Tadpole> aW = a("tadpole", EntityTypes.Builder.a(Tadpole::new, EnumCreatureType.b).a(Tadpole.c, Tadpole.d).a(10));
   public static final EntityTypes<Display.TextDisplay> aX = a(
      "text_display", EntityTypes.Builder.a(Display.TextDisplay::new, EnumCreatureType.h).a(0.0F, 0.0F).a(10).b(1)
   );
   public static final EntityTypes<EntityTNTPrimed> aY = a(
      "tnt", EntityTypes.Builder.a(EntityTNTPrimed::new, EnumCreatureType.h).c().a(0.98F, 0.98F).a(10).b(10)
   );
   public static final EntityTypes<EntityMinecartTNT> aZ = a(
      "tnt_minecart", EntityTypes.Builder.a(EntityMinecartTNT::new, EnumCreatureType.h).a(0.98F, 0.7F).a(8)
   );
   public static final EntityTypes<EntityLlamaTrader> ba = a(
      "trader_llama", EntityTypes.Builder.a(EntityLlamaTrader::new, EnumCreatureType.b).a(0.9F, 1.87F).a(10)
   );
   public static final EntityTypes<EntityThrownTrident> bb = a(
      "trident", EntityTypes.Builder.a(EntityThrownTrident::new, EnumCreatureType.h).a(0.5F, 0.5F).a(4).b(20)
   );
   public static final EntityTypes<EntityTropicalFish> bc = a(
      "tropical_fish", EntityTypes.Builder.a(EntityTropicalFish::new, EnumCreatureType.g).a(0.5F, 0.4F).a(4)
   );
   public static final EntityTypes<EntityTurtle> bd = a("turtle", EntityTypes.Builder.a(EntityTurtle::new, EnumCreatureType.b).a(1.2F, 0.4F).a(10));
   public static final EntityTypes<EntityVex> be = a("vex", EntityTypes.Builder.a(EntityVex::new, EnumCreatureType.a).c().a(0.4F, 0.8F).a(8));
   public static final EntityTypes<EntityVillager> bf = a("villager", EntityTypes.Builder.a(EntityVillager::new, EnumCreatureType.h).a(0.6F, 1.95F).a(10));
   public static final EntityTypes<EntityVindicator> bg = a("vindicator", EntityTypes.Builder.a(EntityVindicator::new, EnumCreatureType.a).a(0.6F, 1.95F).a(8));
   public static final EntityTypes<EntityVillagerTrader> bh = a(
      "wandering_trader", EntityTypes.Builder.a(EntityVillagerTrader::new, EnumCreatureType.b).a(0.6F, 1.95F).a(10)
   );
   public static final EntityTypes<Warden> bi = a("warden", EntityTypes.Builder.a(Warden::new, EnumCreatureType.a).a(0.9F, 2.9F).a(16).c());
   public static final EntityTypes<EntityWitch> bj = a("witch", EntityTypes.Builder.a(EntityWitch::new, EnumCreatureType.a).a(0.6F, 1.95F).a(8));
   public static final EntityTypes<EntityWither> bk = a(
      "wither", EntityTypes.Builder.a(EntityWither::new, EnumCreatureType.a).c().a(Blocks.cc).a(0.9F, 3.5F).a(10)
   );
   public static final EntityTypes<EntitySkeletonWither> bl = a(
      "wither_skeleton", EntityTypes.Builder.a(EntitySkeletonWither::new, EnumCreatureType.a).c().a(Blocks.cc).a(0.7F, 2.4F).a(8)
   );
   public static final EntityTypes<EntityWitherSkull> bm = a(
      "wither_skull", EntityTypes.Builder.a(EntityWitherSkull::new, EnumCreatureType.h).a(0.3125F, 0.3125F).a(4).b(10)
   );
   public static final EntityTypes<EntityWolf> bn = a("wolf", EntityTypes.Builder.a(EntityWolf::new, EnumCreatureType.b).a(0.6F, 0.85F).a(10));
   public static final EntityTypes<EntityZoglin> bo = a("zoglin", EntityTypes.Builder.a(EntityZoglin::new, EnumCreatureType.a).c().a(1.3964844F, 1.4F).a(8));
   public static final EntityTypes<EntityZombie> bp = a("zombie", EntityTypes.Builder.a(EntityZombie::new, EnumCreatureType.a).a(0.6F, 1.95F).a(8));
   public static final EntityTypes<EntityHorseZombie> bq = a(
      "zombie_horse", EntityTypes.Builder.a(EntityHorseZombie::new, EnumCreatureType.b).a(1.3964844F, 1.6F).a(10)
   );
   public static final EntityTypes<EntityZombieVillager> br = a(
      "zombie_villager", EntityTypes.Builder.a(EntityZombieVillager::new, EnumCreatureType.a).a(0.6F, 1.95F).a(8)
   );
   public static final EntityTypes<EntityPigZombie> bs = a(
      "zombified_piglin", EntityTypes.Builder.a(EntityPigZombie::new, EnumCreatureType.a).c().a(0.6F, 1.95F).a(8)
   );
   public static final EntityTypes<EntityHuman> bt = a("player", EntityTypes.Builder.a(EnumCreatureType.h).b().a().a(0.6F, 1.8F).a(32).b(2));
   public static final EntityTypes<EntityFishingHook> bu = a(
      "fishing_bobber", EntityTypes.Builder.a(EntityFishingHook::new, EnumCreatureType.h).b().a().a(0.25F, 0.25F).a(4).b(5)
   );
   private final EntityTypes.b<T> bA;
   private final EnumCreatureType bB;
   private final ImmutableSet<Block> bC;
   private final boolean bD;
   private final boolean bE;
   private final boolean bF;
   private final boolean bG;
   private final int bH;
   private final int bI;
   @Nullable
   private String bJ;
   @Nullable
   private IChatBaseComponent bK;
   @Nullable
   private MinecraftKey bL;
   private final EntitySize bM;
   private final FeatureFlagSet bN;

   private static <T extends Entity> EntityTypes<T> a(String s, EntityTypes.Builder entitytypes_builder) {
      return IRegistry.a(BuiltInRegistries.h, s, entitytypes_builder.a(s));
   }

   public static MinecraftKey a(EntityTypes<?> entitytypes) {
      return BuiltInRegistries.h.b(entitytypes);
   }

   public static Optional<EntityTypes<?>> a(String s) {
      return BuiltInRegistries.h.b(MinecraftKey.a(s));
   }

   public EntityTypes(
      EntityTypes.b<T> entitytypes_b,
      EnumCreatureType enumcreaturetype,
      boolean flag,
      boolean flag1,
      boolean flag2,
      boolean flag3,
      ImmutableSet<Block> immutableset,
      EntitySize entitysize,
      int i,
      int j,
      FeatureFlagSet featureflagset
   ) {
      this.bA = entitytypes_b;
      this.bB = enumcreaturetype;
      this.bG = flag3;
      this.bD = flag;
      this.bE = flag1;
      this.bF = flag2;
      this.bC = immutableset;
      this.bM = entitysize;
      this.bH = i;
      this.bI = j;
      this.bN = featureflagset;
   }

   @Nullable
   public T a(
      WorldServer worldserver,
      @Nullable ItemStack itemstack,
      @Nullable EntityHuman entityhuman,
      BlockPosition blockposition,
      EnumMobSpawn enummobspawn,
      boolean flag,
      boolean flag1
   ) {
      return this.spawn(worldserver, itemstack, entityhuman, blockposition, enummobspawn, flag, flag1, SpawnReason.SPAWNER_EGG);
   }

   @Nullable
   public T spawn(
      WorldServer worldserver,
      @Nullable ItemStack itemstack,
      @Nullable EntityHuman entityhuman,
      BlockPosition blockposition,
      EnumMobSpawn enummobspawn,
      boolean flag,
      boolean flag1,
      SpawnReason spawnReason
   ) {
      NBTTagCompound nbttagcompound;
      Consumer<T> consumer;
      if (itemstack != null) {
         nbttagcompound = itemstack.u();
         consumer = a(worldserver, itemstack, entityhuman);
      } else {
         consumer = entity -> {
         };
         nbttagcompound = null;
      }

      return this.spawn(worldserver, nbttagcompound, consumer, blockposition, enummobspawn, flag, flag1, spawnReason);
   }

   public static <T extends Entity> Consumer<T> a(WorldServer worldserver, ItemStack itemstack, @Nullable EntityHuman entityhuman) {
      return a(entity -> {
      }, worldserver, itemstack, entityhuman);
   }

   public static <T extends Entity> Consumer<T> a(Consumer<T> consumer, WorldServer worldserver, ItemStack itemstack, @Nullable EntityHuman entityhuman) {
      return b(a(consumer, itemstack), worldserver, itemstack, entityhuman);
   }

   public static <T extends Entity> Consumer<T> a(Consumer<T> consumer, ItemStack itemstack) {
      return itemstack.z() ? consumer.andThen(entity -> entity.b(itemstack.x())) : consumer;
   }

   public static <T extends Entity> Consumer<T> b(Consumer<T> consumer, WorldServer worldserver, ItemStack itemstack, @Nullable EntityHuman entityhuman) {
      NBTTagCompound nbttagcompound = itemstack.u();
      return nbttagcompound != null ? consumer.andThen(entity -> {
         try {
            a(worldserver, entityhuman, entity, nbttagcompound);
         } catch (Throwable var5) {
            bw.warn("Error loading spawn egg NBT", var5);
         }
      }) : consumer;
   }

   @Nullable
   public T a(WorldServer worldserver, BlockPosition blockposition, EnumMobSpawn enummobspawn) {
      return this.spawn(worldserver, blockposition, enummobspawn, SpawnReason.DEFAULT);
   }

   @Nullable
   public T spawn(WorldServer worldserver, BlockPosition blockposition, EnumMobSpawn enummobspawn, SpawnReason spawnReason) {
      return this.spawn(worldserver, null, null, blockposition, enummobspawn, false, false, spawnReason);
   }

   @Nullable
   public T a(
      WorldServer worldserver,
      @Nullable NBTTagCompound nbttagcompound,
      @Nullable Consumer<T> consumer,
      BlockPosition blockposition,
      EnumMobSpawn enummobspawn,
      boolean flag,
      boolean flag1
   ) {
      return this.spawn(worldserver, nbttagcompound, consumer, blockposition, enummobspawn, flag, flag1, SpawnReason.DEFAULT);
   }

   @Nullable
   public T spawn(
      WorldServer worldserver,
      @Nullable NBTTagCompound nbttagcompound,
      @Nullable Consumer<T> consumer,
      BlockPosition blockposition,
      EnumMobSpawn enummobspawn,
      boolean flag,
      boolean flag1,
      SpawnReason spawnReason
   ) {
      T t0 = this.b(worldserver, nbttagcompound, consumer, blockposition, enummobspawn, flag, flag1);
      if (t0 != null) {
         worldserver.addFreshEntityWithPassengers(t0, spawnReason);
         return !t0.dB() ? t0 : null;
      } else {
         return t0;
      }
   }

   @Nullable
   public T b(
      WorldServer worldserver,
      @Nullable NBTTagCompound nbttagcompound,
      @Nullable Consumer<T> consumer,
      BlockPosition blockposition,
      EnumMobSpawn enummobspawn,
      boolean flag,
      boolean flag1
   ) {
      T t0 = this.a((World)worldserver);
      if (t0 == null) {
         return null;
      } else {
         double d0;
         if (flag) {
            t0.e((double)blockposition.u() + 0.5, (double)(blockposition.v() + 1), (double)blockposition.w() + 0.5);
            d0 = a(worldserver, blockposition, flag1, t0.cD());
         } else {
            d0 = 0.0;
         }

         t0.b((double)blockposition.u() + 0.5, (double)blockposition.v() + d0, (double)blockposition.w() + 0.5, MathHelper.g(worldserver.z.i() * 360.0F), 0.0F);
         if (t0 instanceof EntityInsentient entityinsentient) {
            entityinsentient.aV = entityinsentient.dw();
            entityinsentient.aT = entityinsentient.dw();
            entityinsentient.a(worldserver, worldserver.d_(entityinsentient.dg()), enummobspawn, null, nbttagcompound);
            entityinsentient.L();
         }

         if (consumer != null) {
            consumer.accept(t0);
         }

         return t0;
      }
   }

   protected static double a(IWorldReader iworldreader, BlockPosition blockposition, boolean flag, AxisAlignedBB axisalignedbb) {
      AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(blockposition);
      if (flag) {
         axisalignedbb1 = axisalignedbb1.b(0.0, -1.0, 0.0);
      }

      Iterable<VoxelShape> iterable = iworldreader.c(null, axisalignedbb1);
      return 1.0 + VoxelShapes.a(EnumDirection.EnumAxis.b, axisalignedbb, iterable, flag ? -2.0 : -1.0);
   }

   public static void a(World world, @Nullable EntityHuman entityhuman, @Nullable Entity entity, @Nullable NBTTagCompound nbttagcompound) {
      if (nbttagcompound != null && nbttagcompound.b("EntityTag", 10)) {
         MinecraftServer minecraftserver = world.n();
         if (minecraftserver != null && entity != null && (world.B || !entity.cJ() || entityhuman != null && minecraftserver.ac().f(entityhuman.fI()))) {
            NBTTagCompound nbttagcompound1 = entity.f(new NBTTagCompound());
            UUID uuid = entity.cs();
            nbttagcompound1.a(nbttagcompound.p("EntityTag"));
            entity.a_(uuid);
            entity.g(nbttagcompound1);
         }
      }
   }

   public boolean b() {
      return this.bD;
   }

   public boolean c() {
      return this.bE;
   }

   public boolean d() {
      return this.bF;
   }

   public boolean e() {
      return this.bG;
   }

   public EnumCreatureType f() {
      return this.bB;
   }

   public String g() {
      if (this.bJ == null) {
         this.bJ = SystemUtils.a("entity", BuiltInRegistries.h.b(this));
      }

      return this.bJ;
   }

   public IChatBaseComponent h() {
      if (this.bK == null) {
         this.bK = IChatBaseComponent.c(this.g());
      }

      return this.bK;
   }

   @Override
   public String toString() {
      return this.g();
   }

   public String i() {
      int i = this.g().lastIndexOf(46);
      return i == -1 ? this.g() : this.g().substring(i + 1);
   }

   public MinecraftKey j() {
      if (this.bL == null) {
         MinecraftKey minecraftkey = BuiltInRegistries.h.b(this);
         this.bL = minecraftkey.d("entities/");
      }

      return this.bL;
   }

   public float k() {
      return this.bM.a;
   }

   public float l() {
      return this.bM.b;
   }

   @Override
   public FeatureFlagSet m() {
      return this.bN;
   }

   @Nullable
   public T a(World world) {
      return !this.a(world.G()) ? null : this.bA.create(this, world);
   }

   public static Optional<Entity> a(NBTTagCompound nbttagcompound, World world) {
      return SystemUtils.a(
         a(nbttagcompound).map(entitytypes -> entitytypes.a(world)),
         entity -> entity.g(nbttagcompound),
         () -> bw.warn("Skipping Entity with id {}", nbttagcompound.l("id"))
      );
   }

   public AxisAlignedBB a(double d0, double d1, double d2) {
      float f = this.k() / 2.0F;
      return new AxisAlignedBB(d0 - (double)f, d1, d2 - (double)f, d0 + (double)f, d1 + (double)this.l(), d2 + (double)f);
   }

   public boolean a(IBlockData iblockdata) {
      return this.bC.contains(iblockdata.b())
         ? false
         : (
            !this.bF && PathfinderNormal.a(iblockdata)
               ? true
               : iblockdata.a(Blocks.cc) || iblockdata.a(Blocks.oe) || iblockdata.a(Blocks.dP) || iblockdata.a(Blocks.qy)
         );
   }

   public EntitySize n() {
      return this.bM;
   }

   public static Optional<EntityTypes<?>> a(NBTTagCompound nbttagcompound) {
      return BuiltInRegistries.h.b(new MinecraftKey(nbttagcompound.l("id")));
   }

   @Nullable
   public static Entity a(NBTTagCompound nbttagcompound, World world, Function<Entity, Entity> function) {
      return b(nbttagcompound, world).map(function).map(entity -> {
         if (nbttagcompound.b("Passengers", 9)) {
            NBTTagList nbttaglist = nbttagcompound.c("Passengers", 10);

            for(int i = 0; i < nbttaglist.size(); ++i) {
               Entity entity1 = a(nbttaglist.a(i), world, function);
               if (entity1 != null) {
                  entity1.a(entity, true);
               }
            }
         }

         return entity;
      }).orElse(null);
   }

   public static Stream<Entity> a(final List<? extends NBTBase> list, final World world) {
      final Spliterator<? extends NBTBase> spliterator = list.spliterator();
      return StreamSupport.stream(new Spliterator<Entity>() {
         @Override
         public boolean tryAdvance(Consumer<? super Entity> consumer) {
            return spliterator.tryAdvance(nbtbase -> EntityTypes.a((NBTTagCompound)nbtbase, world, entity -> {
                  consumer.accept(entity);
                  return entity;
               }));
         }

         @Override
         public Spliterator<Entity> trySplit() {
            return null;
         }

         @Override
         public long estimateSize() {
            return (long)list.size();
         }

         @Override
         public int characteristics() {
            return 1297;
         }
      }, false);
   }

   private static Optional<Entity> b(NBTTagCompound nbttagcompound, World world) {
      try {
         return a(nbttagcompound, world);
      } catch (RuntimeException var3) {
         bw.warn("Exception loading entity: ", var3);
         return Optional.empty();
      }
   }

   public int o() {
      return this.bH;
   }

   public int p() {
      return this.bI;
   }

   public boolean q() {
      return this != bt && this != ak && this != bk && this != g && this != af && this != S && this != ah && this != ar && this != B && this != H;
   }

   public boolean a(TagKey<EntityTypes<?>> tagkey) {
      return this.bx.a(tagkey);
   }

   @Nullable
   public T a(Entity entity) {
      return (T)(entity.ae() == this ? entity : null);
   }

   @Override
   public Class<? extends Entity> a() {
      return Entity.class;
   }

   @Deprecated
   public Holder.c<EntityTypes<?>> r() {
      return this.bx;
   }

   public static class Builder<T extends Entity> {
      private final EntityTypes.b<T> a;
      private final EnumCreatureType b;
      private ImmutableSet<Block> c = ImmutableSet.of();
      private boolean d = true;
      private boolean e = true;
      private boolean f;
      private boolean g;
      private int h = 5;
      private int i = 3;
      private EntitySize j = EntitySize.b(0.6F, 1.8F);
      private FeatureFlagSet k = FeatureFlags.f;

      private Builder(EntityTypes.b<T> entitytypes_b, EnumCreatureType enumcreaturetype) {
         this.a = entitytypes_b;
         this.b = enumcreaturetype;
         this.g = enumcreaturetype == EnumCreatureType.b || enumcreaturetype == EnumCreatureType.h;
      }

      public static <T extends Entity> EntityTypes.Builder<T> a(EntityTypes.b entitytypes_b, EnumCreatureType enumcreaturetype) {
         return new EntityTypes.Builder<>(entitytypes_b, enumcreaturetype);
      }

      public static <T extends Entity> EntityTypes.Builder<T> a(EnumCreatureType enumcreaturetype) {
         return new EntityTypes.Builder<>((entitytypes, world) -> null, enumcreaturetype);
      }

      public EntityTypes.Builder<T> a(float f, float f1) {
         this.j = EntitySize.b(f, f1);
         return this;
      }

      public EntityTypes.Builder<T> a() {
         this.e = false;
         return this;
      }

      public EntityTypes.Builder<T> b() {
         this.d = false;
         return this;
      }

      public EntityTypes.Builder<T> c() {
         this.f = true;
         return this;
      }

      public EntityTypes.Builder<T> a(Block... ablock) {
         this.c = ImmutableSet.copyOf(ablock);
         return this;
      }

      public EntityTypes.Builder<T> d() {
         this.g = true;
         return this;
      }

      public EntityTypes.Builder<T> a(int i) {
         this.h = i;
         return this;
      }

      public EntityTypes.Builder<T> b(int i) {
         this.i = i;
         return this;
      }

      public EntityTypes.Builder<T> a(FeatureFlag... afeatureflag) {
         this.k = FeatureFlags.d.a(afeatureflag);
         return this;
      }

      public EntityTypes<T> a(String s) {
         if (this.d) {
            SystemUtils.a(DataConverterTypes.p, s);
         }

         return new EntityTypes<>(this.a, this.b, this.d, this.e, this.f, this.g, this.c, this.j, this.h, this.i, this.k);
      }
   }

   public interface b<T extends Entity> {
      T create(EntityTypes<T> var1, World var2);
   }
}
