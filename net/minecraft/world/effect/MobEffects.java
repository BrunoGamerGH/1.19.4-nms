package net.minecraft.world.effect;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import org.bukkit.craftbukkit.v1_19_R3.potion.CraftPotionEffectType;
import org.bukkit.potion.PotionEffectType;

public class MobEffects {
   private static final int H = 22;
   public static final MobEffectList a = a(
      1,
      "speed",
      new MobEffectList(MobEffectInfo.a, 3402751).a(GenericAttributes.d, "91AEAA56-376B-4498-935B-2F7F68070635", 0.2F, AttributeModifier.Operation.c)
   );
   public static final MobEffectList b = a(
      2,
      "slowness",
      new MobEffectList(MobEffectInfo.b, 9154528).a(GenericAttributes.d, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15F, AttributeModifier.Operation.c)
   );
   public static final MobEffectList c = a(
      3,
      "haste",
      new MobEffectList(MobEffectInfo.a, 14270531).a(GenericAttributes.h, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 0.1F, AttributeModifier.Operation.c)
   );
   public static final MobEffectList d = a(
      4,
      "mining_fatigue",
      new MobEffectList(MobEffectInfo.b, 4866583).a(GenericAttributes.h, "55FCED67-E92A-486E-9800-B47F202C4386", -0.1F, AttributeModifier.Operation.c)
   );
   public static final MobEffectList e = a(
      5,
      "strength",
      new MobEffectAttackDamage(MobEffectInfo.a, 16762624, 3.0)
         .a(GenericAttributes.f, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0, AttributeModifier.Operation.a)
   );
   public static final MobEffectList f = a(6, "instant_health", new InstantMobEffect(MobEffectInfo.a, 16262179));
   public static final MobEffectList g = a(7, "instant_damage", new InstantMobEffect(MobEffectInfo.b, 11101546));
   public static final MobEffectList h = a(8, "jump_boost", new MobEffectList(MobEffectInfo.a, 16646020));
   public static final MobEffectList i = a(9, "nausea", new MobEffectList(MobEffectInfo.b, 5578058));
   public static final MobEffectList j = a(10, "regeneration", new MobEffectList(MobEffectInfo.a, 13458603));
   public static final MobEffectList k = a(11, "resistance", new MobEffectList(MobEffectInfo.a, 9520880));
   public static final MobEffectList l = a(12, "fire_resistance", new MobEffectList(MobEffectInfo.a, 16750848));
   public static final MobEffectList m = a(13, "water_breathing", new MobEffectList(MobEffectInfo.a, 10017472));
   public static final MobEffectList n = a(14, "invisibility", new MobEffectList(MobEffectInfo.a, 16185078));
   public static final MobEffectList o = a(15, "blindness", new MobEffectList(MobEffectInfo.b, 2039587));
   public static final MobEffectList p = a(16, "night_vision", new MobEffectList(MobEffectInfo.a, 12779366));
   public static final MobEffectList q = a(17, "hunger", new MobEffectList(MobEffectInfo.b, 5797459));
   public static final MobEffectList r = a(
      18,
      "weakness",
      new MobEffectAttackDamage(MobEffectInfo.b, 4738376, -4.0)
         .a(GenericAttributes.f, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0, AttributeModifier.Operation.a)
   );
   public static final MobEffectList s = a(19, "poison", new MobEffectList(MobEffectInfo.b, 8889187));
   public static final MobEffectList t = a(20, "wither", new MobEffectList(MobEffectInfo.b, 3484199));
   public static final MobEffectList u = a(
      21,
      "health_boost",
      new MobEffectHealthBoost(MobEffectInfo.a, 16284963).a(GenericAttributes.a, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0, AttributeModifier.Operation.a)
   );
   public static final MobEffectList v = a(22, "absorption", new MobEffectAbsorption(MobEffectInfo.a, 2445989));
   public static final MobEffectList w = a(23, "saturation", new InstantMobEffect(MobEffectInfo.a, 16262179));
   public static final MobEffectList x = a(24, "glowing", new MobEffectList(MobEffectInfo.c, 9740385));
   public static final MobEffectList y = a(25, "levitation", new MobEffectList(MobEffectInfo.b, 13565951));
   public static final MobEffectList z = a(
      26,
      "luck",
      new MobEffectList(MobEffectInfo.a, 5882118).a(GenericAttributes.k, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 1.0, AttributeModifier.Operation.a)
   );
   public static final MobEffectList A = a(
      27,
      "unluck",
      new MobEffectList(MobEffectInfo.b, 12624973).a(GenericAttributes.k, "CC5AF142-2BD2-4215-B636-2605AED11727", -1.0, AttributeModifier.Operation.a)
   );
   public static final MobEffectList B = a(28, "slow_falling", new MobEffectList(MobEffectInfo.a, 16773073));
   public static final MobEffectList C = a(29, "conduit_power", new MobEffectList(MobEffectInfo.a, 1950417));
   public static final MobEffectList D = a(30, "dolphins_grace", new MobEffectList(MobEffectInfo.a, 8954814));
   public static final MobEffectList E = a(31, "bad_omen", new MobEffectList(MobEffectInfo.c, 745784) {
      @Override
      public boolean a(int i, int j) {
         return true;
      }

      @Override
      public void a(EntityLiving entityliving, int i) {
         if (entityliving instanceof EntityPlayer entityplayer && !entityliving.F_()) {
            WorldServer worldserver = entityplayer.x();
            if (worldserver.ah() == EnumDifficulty.a) {
               return;
            }

            if (worldserver.b(entityliving.dg())) {
               worldserver.x().a(entityplayer);
            }
         }
      }
   });
   public static final MobEffectList F = a(32, "hero_of_the_village", new MobEffectList(MobEffectInfo.a, 4521796));
   public static final MobEffectList G = a(33, "darkness", new MobEffectList(MobEffectInfo.b, 2696993).a(() -> new MobEffect.a(22)));

   private static MobEffectList a(int i, String s, MobEffectList mobeffectlist) {
      mobeffectlist = IRegistry.a(BuiltInRegistries.e, i, s, mobeffectlist);
      PotionEffectType.registerPotionEffectType(new CraftPotionEffectType(mobeffectlist));
      return mobeffectlist;
   }
}
