package net.minecraft.world.entity.ai.attributes;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import java.util.Map;
import net.minecraft.SystemUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.ambient.EntityBat;
import net.minecraft.world.entity.animal.EntityBee;
import net.minecraft.world.entity.animal.EntityCat;
import net.minecraft.world.entity.animal.EntityChicken;
import net.minecraft.world.entity.animal.EntityCow;
import net.minecraft.world.entity.animal.EntityDolphin;
import net.minecraft.world.entity.animal.EntityFish;
import net.minecraft.world.entity.animal.EntityFox;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.animal.EntityOcelot;
import net.minecraft.world.entity.animal.EntityPanda;
import net.minecraft.world.entity.animal.EntityParrot;
import net.minecraft.world.entity.animal.EntityPig;
import net.minecraft.world.entity.animal.EntityPolarBear;
import net.minecraft.world.entity.animal.EntityRabbit;
import net.minecraft.world.entity.animal.EntitySheep;
import net.minecraft.world.entity.animal.EntitySnowman;
import net.minecraft.world.entity.animal.EntitySquid;
import net.minecraft.world.entity.animal.EntityTurtle;
import net.minecraft.world.entity.animal.EntityWolf;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.EntityHorseAbstract;
import net.minecraft.world.entity.animal.horse.EntityHorseChestedAbstract;
import net.minecraft.world.entity.animal.horse.EntityHorseSkeleton;
import net.minecraft.world.entity.animal.horse.EntityHorseZombie;
import net.minecraft.world.entity.animal.horse.EntityLlama;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.boss.wither.EntityWither;
import net.minecraft.world.entity.monster.EntityBlaze;
import net.minecraft.world.entity.monster.EntityCaveSpider;
import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.entity.monster.EntityEnderman;
import net.minecraft.world.entity.monster.EntityEndermite;
import net.minecraft.world.entity.monster.EntityEvoker;
import net.minecraft.world.entity.monster.EntityGhast;
import net.minecraft.world.entity.monster.EntityGiantZombie;
import net.minecraft.world.entity.monster.EntityGuardian;
import net.minecraft.world.entity.monster.EntityGuardianElder;
import net.minecraft.world.entity.monster.EntityIllagerIllusioner;
import net.minecraft.world.entity.monster.EntityMagmaCube;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.EntityPigZombie;
import net.minecraft.world.entity.monster.EntityPillager;
import net.minecraft.world.entity.monster.EntityRavager;
import net.minecraft.world.entity.monster.EntityShulker;
import net.minecraft.world.entity.monster.EntitySilverfish;
import net.minecraft.world.entity.monster.EntitySkeletonAbstract;
import net.minecraft.world.entity.monster.EntitySpider;
import net.minecraft.world.entity.monster.EntityStrider;
import net.minecraft.world.entity.monster.EntityVex;
import net.minecraft.world.entity.monster.EntityVindicator;
import net.minecraft.world.entity.monster.EntityWitch;
import net.minecraft.world.entity.monster.EntityZoglin;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.entity.monster.hoglin.EntityHoglin;
import net.minecraft.world.entity.monster.piglin.EntityPiglin;
import net.minecraft.world.entity.monster.piglin.EntityPiglinBrute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.player.EntityHuman;
import org.slf4j.Logger;

public class AttributeDefaults {
   private static final Logger a = LogUtils.getLogger();
   private static final Map<EntityTypes<? extends EntityLiving>, AttributeProvider> b = ImmutableMap.builder()
      .put(EntityTypes.b, Allay.q().a())
      .put(EntityTypes.d, EntityLiving.dJ().a())
      .put(EntityTypes.f, Axolotl.fZ().a())
      .put(EntityTypes.g, EntityBat.q().a())
      .put(EntityTypes.h, EntityBee.ge().a())
      .put(EntityTypes.i, EntityBlaze.q().a())
      .put(EntityTypes.m, EntityCat.ge().a())
      .put(EntityTypes.l, Camel.q().a())
      .put(EntityTypes.n, EntityCaveSpider.q().a())
      .put(EntityTypes.q, EntityChicken.q().a())
      .put(EntityTypes.r, EntityFish.q().a())
      .put(EntityTypes.t, EntityCow.q().a())
      .put(EntityTypes.u, EntityCreeper.q().a())
      .put(EntityTypes.v, EntityDolphin.fS().a())
      .put(EntityTypes.w, EntityHorseChestedAbstract.q().a())
      .put(EntityTypes.y, EntityZombie.fW().a())
      .put(EntityTypes.A, EntityGuardianElder.q().a())
      .put(EntityTypes.E, EntityEnderman.q().a())
      .put(EntityTypes.F, EntityEndermite.q().a())
      .put(EntityTypes.C, EntityEnderDragon.q().a())
      .put(EntityTypes.G, EntityEvoker.r().a())
      .put(EntityTypes.N, EntityFox.q().a())
      .put(EntityTypes.O, Frog.fS().a())
      .put(EntityTypes.Q, EntityGhast.w().a())
      .put(EntityTypes.R, EntityGiantZombie.q().a())
      .put(EntityTypes.T, GlowSquid.fS().a())
      .put(EntityTypes.U, Goat.r().a())
      .put(EntityTypes.V, EntityGuardian.fS().a())
      .put(EntityTypes.W, EntityHoglin.q().a())
      .put(EntityTypes.Y, EntityHorseAbstract.gs().a())
      .put(EntityTypes.Z, EntityZombie.fW().a())
      .put(EntityTypes.aa, EntityIllagerIllusioner.r().a())
      .put(EntityTypes.ac, EntityIronGolem.q().a())
      .put(EntityTypes.aj, EntityLlama.gd().a())
      .put(EntityTypes.al, EntityMagmaCube.q().a())
      .put(EntityTypes.ao, EntityCow.q().a())
      .put(EntityTypes.ap, EntityHorseChestedAbstract.q().a())
      .put(EntityTypes.aq, EntityOcelot.q().a())
      .put(EntityTypes.as, EntityPanda.gd().a())
      .put(EntityTypes.at, EntityParrot.fY().a())
      .put(EntityTypes.au, EntityMonster.fY().a())
      .put(EntityTypes.av, EntityPig.q().a())
      .put(EntityTypes.aw, EntityPiglin.fZ().a())
      .put(EntityTypes.ax, EntityPiglinBrute.w().a())
      .put(EntityTypes.ay, EntityPillager.r().a())
      .put(EntityTypes.bt, EntityHuman.fy().a())
      .put(EntityTypes.az, EntityPolarBear.q().a())
      .put(EntityTypes.aB, EntityFish.q().a())
      .put(EntityTypes.aC, EntityRabbit.r().a())
      .put(EntityTypes.aD, EntityRavager.q().a())
      .put(EntityTypes.aE, EntityFish.q().a())
      .put(EntityTypes.aF, EntitySheep.q().a())
      .put(EntityTypes.aG, EntityShulker.q().a())
      .put(EntityTypes.aI, EntitySilverfish.q().a())
      .put(EntityTypes.aJ, EntitySkeletonAbstract.q().a())
      .put(EntityTypes.aK, EntityHorseSkeleton.q().a())
      .put(EntityTypes.aL, EntityMonster.fY().a())
      .put(EntityTypes.aN, Sniffer.q().a())
      .put(EntityTypes.aO, EntitySnowman.q().a())
      .put(EntityTypes.aS, EntitySpider.r().a())
      .put(EntityTypes.aT, EntitySquid.fS().a())
      .put(EntityTypes.aU, EntitySkeletonAbstract.q().a())
      .put(EntityTypes.aV, EntityStrider.r().a())
      .put(EntityTypes.aW, Tadpole.fU().a())
      .put(EntityTypes.ba, EntityLlama.gd().a())
      .put(EntityTypes.bc, EntityFish.q().a())
      .put(EntityTypes.bd, EntityTurtle.w().a())
      .put(EntityTypes.be, EntityVex.q().a())
      .put(EntityTypes.bf, EntityVillager.gb().a())
      .put(EntityTypes.bg, EntityVindicator.r().a())
      .put(EntityTypes.bi, Warden.q().a())
      .put(EntityTypes.bh, EntityInsentient.y().a())
      .put(EntityTypes.bj, EntityWitch.r().a())
      .put(EntityTypes.bk, EntityWither.r().a())
      .put(EntityTypes.bl, EntitySkeletonAbstract.q().a())
      .put(EntityTypes.bn, EntityWolf.fY().a())
      .put(EntityTypes.bo, EntityZoglin.q().a())
      .put(EntityTypes.bp, EntityZombie.fW().a())
      .put(EntityTypes.bq, EntityHorseZombie.q().a())
      .put(EntityTypes.br, EntityZombie.fW().a())
      .put(EntityTypes.bs, EntityPigZombie.gc().a())
      .build();

   public static AttributeProvider a(EntityTypes<? extends EntityLiving> var0) {
      return b.get(var0);
   }

   public static boolean b(EntityTypes<?> var0) {
      return b.containsKey(var0);
   }

   public static void a() {
      BuiltInRegistries.h
         .s()
         .filter(var0 -> var0.f() != EnumCreatureType.h)
         .filter(var0 -> !b(var0))
         .map(BuiltInRegistries.h::b)
         .forEach(var0 -> SystemUtils.a("Entity " + var0 + " has no attributes"));
   }
}
