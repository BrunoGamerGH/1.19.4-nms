package net.minecraft.core.particles;

import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public class Particles {
   public static final ParticleType a = a("ambient_entity_effect", false);
   public static final ParticleType b = a("angry_villager", false);
   public static final Particle<ParticleParamBlock> c = a("block", false, ParticleParamBlock.a, ParticleParamBlock::a);
   public static final Particle<ParticleParamBlock> d = a("block_marker", false, ParticleParamBlock.a, ParticleParamBlock::a);
   public static final ParticleType e = a("bubble", false);
   public static final ParticleType f = a("cloud", false);
   public static final ParticleType g = a("crit", false);
   public static final ParticleType h = a("damage_indicator", true);
   public static final ParticleType i = a("dragon_breath", false);
   public static final ParticleType j = a("dripping_lava", false);
   public static final ParticleType k = a("falling_lava", false);
   public static final ParticleType l = a("landing_lava", false);
   public static final ParticleType m = a("dripping_water", false);
   public static final ParticleType n = a("falling_water", false);
   public static final Particle<ParticleParamRedstone> o = a("dust", false, ParticleParamRedstone.d, var0 -> ParticleParamRedstone.c);
   public static final Particle<DustColorTransitionOptions> p = a(
      "dust_color_transition", false, DustColorTransitionOptions.d, var0 -> DustColorTransitionOptions.c
   );
   public static final ParticleType q = a("effect", false);
   public static final ParticleType r = a("elder_guardian", true);
   public static final ParticleType s = a("enchanted_hit", false);
   public static final ParticleType t = a("enchant", false);
   public static final ParticleType u = a("end_rod", false);
   public static final ParticleType v = a("entity_effect", false);
   public static final ParticleType w = a("explosion_emitter", true);
   public static final ParticleType x = a("explosion", true);
   public static final ParticleType y = a("sonic_boom", true);
   public static final Particle<ParticleParamBlock> z = a("falling_dust", false, ParticleParamBlock.a, ParticleParamBlock::a);
   public static final ParticleType A = a("firework", false);
   public static final ParticleType B = a("fishing", false);
   public static final ParticleType C = a("flame", false);
   public static final ParticleType D = a("dripping_cherry_leaves", false);
   public static final ParticleType E = a("falling_cherry_leaves", false);
   public static final ParticleType F = a("landing_cherry_leaves", false);
   public static final ParticleType G = a("sculk_soul", false);
   public static final Particle<SculkChargeParticleOptions> H = a("sculk_charge", true, SculkChargeParticleOptions.b, var0 -> SculkChargeParticleOptions.a);
   public static final ParticleType I = a("sculk_charge_pop", true);
   public static final ParticleType J = a("soul_fire_flame", false);
   public static final ParticleType K = a("soul", false);
   public static final ParticleType L = a("flash", false);
   public static final ParticleType M = a("happy_villager", false);
   public static final ParticleType N = a("composter", false);
   public static final ParticleType O = a("heart", false);
   public static final ParticleType P = a("instant_effect", false);
   public static final Particle<ParticleParamItem> Q = a("item", false, ParticleParamItem.a, ParticleParamItem::a);
   public static final Particle<VibrationParticleOption> R = a("vibration", true, VibrationParticleOption.b, var0 -> VibrationParticleOption.a);
   public static final ParticleType S = a("item_slime", false);
   public static final ParticleType T = a("item_snowball", false);
   public static final ParticleType U = a("large_smoke", false);
   public static final ParticleType V = a("lava", false);
   public static final ParticleType W = a("mycelium", false);
   public static final ParticleType X = a("note", false);
   public static final ParticleType Y = a("poof", true);
   public static final ParticleType Z = a("portal", false);
   public static final ParticleType aa = a("rain", false);
   public static final ParticleType ab = a("smoke", false);
   public static final ParticleType ac = a("sneeze", false);
   public static final ParticleType ad = a("spit", true);
   public static final ParticleType ae = a("squid_ink", true);
   public static final ParticleType af = a("sweep_attack", true);
   public static final ParticleType ag = a("totem_of_undying", false);
   public static final ParticleType ah = a("underwater", false);
   public static final ParticleType ai = a("splash", false);
   public static final ParticleType aj = a("witch", false);
   public static final ParticleType ak = a("bubble_pop", false);
   public static final ParticleType al = a("current_down", false);
   public static final ParticleType am = a("bubble_column_up", false);
   public static final ParticleType an = a("nautilus", false);
   public static final ParticleType ao = a("dolphin", false);
   public static final ParticleType ap = a("campfire_cosy_smoke", true);
   public static final ParticleType aq = a("campfire_signal_smoke", true);
   public static final ParticleType ar = a("dripping_honey", false);
   public static final ParticleType as = a("falling_honey", false);
   public static final ParticleType at = a("landing_honey", false);
   public static final ParticleType au = a("falling_nectar", false);
   public static final ParticleType av = a("falling_spore_blossom", false);
   public static final ParticleType aw = a("ash", false);
   public static final ParticleType ax = a("crimson_spore", false);
   public static final ParticleType ay = a("warped_spore", false);
   public static final ParticleType az = a("spore_blossom_air", false);
   public static final ParticleType aA = a("dripping_obsidian_tear", false);
   public static final ParticleType aB = a("falling_obsidian_tear", false);
   public static final ParticleType aC = a("landing_obsidian_tear", false);
   public static final ParticleType aD = a("reverse_portal", false);
   public static final ParticleType aE = a("white_ash", false);
   public static final ParticleType aF = a("small_flame", false);
   public static final ParticleType aG = a("snowflake", false);
   public static final ParticleType aH = a("dripping_dripstone_lava", false);
   public static final ParticleType aI = a("falling_dripstone_lava", false);
   public static final ParticleType aJ = a("dripping_dripstone_water", false);
   public static final ParticleType aK = a("falling_dripstone_water", false);
   public static final ParticleType aL = a("glow_squid_ink", true);
   public static final ParticleType aM = a("glow", true);
   public static final ParticleType aN = a("wax_on", true);
   public static final ParticleType aO = a("wax_off", true);
   public static final ParticleType aP = a("electric_spark", true);
   public static final ParticleType aQ = a("scrape", true);
   public static final Particle<ShriekParticleOption> aR = a("shriek", false, ShriekParticleOption.b, var0 -> ShriekParticleOption.a);
   public static final Codec<ParticleParam> aS = BuiltInRegistries.k.q().dispatch("type", ParticleParam::b, Particle::e);

   private static ParticleType a(String var0, boolean var1) {
      return IRegistry.a(BuiltInRegistries.k, var0, new ParticleType(var1));
   }

   private static <T extends ParticleParam> Particle<T> a(String var0, boolean var1, ParticleParam.a<T> var2, final Function<Particle<T>, Codec<T>> var3) {
      return IRegistry.a(BuiltInRegistries.k, var0, new Particle<T>(var1, var2) {
         @Override
         public Codec<T> e() {
            return (Codec<T>)var3.apply(this);
         }
      });
   }
}
