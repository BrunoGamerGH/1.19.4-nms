package net.minecraft.world.level.block.state.properties;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.INamable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Material;

public enum BlockPropertyInstrument implements INamable {
   a("harp", SoundEffects.pZ, BlockPropertyInstrument.a.a),
   b("basedrum", SoundEffects.pT, BlockPropertyInstrument.a.a),
   c("snare", SoundEffects.qc, BlockPropertyInstrument.a.a),
   d("hat", SoundEffects.qa, BlockPropertyInstrument.a.a),
   e("bass", SoundEffects.pU, BlockPropertyInstrument.a.a),
   f("flute", SoundEffects.pX, BlockPropertyInstrument.a.a),
   g("bell", SoundEffects.pV, BlockPropertyInstrument.a.a),
   h("guitar", SoundEffects.pY, BlockPropertyInstrument.a.a),
   i("chime", SoundEffects.pW, BlockPropertyInstrument.a.a),
   j("xylophone", SoundEffects.qd, BlockPropertyInstrument.a.a),
   k("iron_xylophone", SoundEffects.qe, BlockPropertyInstrument.a.a),
   l("cow_bell", SoundEffects.qf, BlockPropertyInstrument.a.a),
   m("didgeridoo", SoundEffects.qg, BlockPropertyInstrument.a.a),
   n("bit", SoundEffects.qh, BlockPropertyInstrument.a.a),
   o("banjo", SoundEffects.qi, BlockPropertyInstrument.a.a),
   p("pling", SoundEffects.qb, BlockPropertyInstrument.a.a),
   q("zombie", SoundEffects.qj, BlockPropertyInstrument.a.b),
   r("skeleton", SoundEffects.qk, BlockPropertyInstrument.a.b),
   s("creeper", SoundEffects.ql, BlockPropertyInstrument.a.b),
   t("dragon", SoundEffects.qm, BlockPropertyInstrument.a.b),
   u("wither_skeleton", SoundEffects.qn, BlockPropertyInstrument.a.b),
   v("piglin", SoundEffects.qo, BlockPropertyInstrument.a.b),
   w("custom_head", SoundEffects.xV, BlockPropertyInstrument.a.c);

   private final String x;
   private final Holder<SoundEffect> y;
   private final BlockPropertyInstrument.a z;

   private BlockPropertyInstrument(String var2, Holder var3, BlockPropertyInstrument.a var4) {
      this.x = var2;
      this.y = var3;
      this.z = var4;
   }

   @Override
   public String c() {
      return this.x;
   }

   public Holder<SoundEffect> a() {
      return this.y;
   }

   public boolean b() {
      return this.z == BlockPropertyInstrument.a.a;
   }

   public boolean d() {
      return this.z == BlockPropertyInstrument.a.c;
   }

   public boolean e() {
      return this.z == BlockPropertyInstrument.a.a;
   }

   public static Optional<BlockPropertyInstrument> a(IBlockData var0) {
      if (var0.a(Blocks.gH)) {
         return Optional.of(q);
      } else if (var0.a(Blocks.gD)) {
         return Optional.of(r);
      } else if (var0.a(Blocks.gL)) {
         return Optional.of(s);
      } else if (var0.a(Blocks.gN)) {
         return Optional.of(t);
      } else if (var0.a(Blocks.gF)) {
         return Optional.of(u);
      } else if (var0.a(Blocks.gP)) {
         return Optional.of(v);
      } else {
         return var0.a(Blocks.gJ) ? Optional.of(w) : Optional.empty();
      }
   }

   public static BlockPropertyInstrument b(IBlockData var0) {
      if (var0.a(Blocks.dQ)) {
         return f;
      } else if (var0.a(Blocks.cg)) {
         return g;
      } else if (var0.a(TagsBlock.a)) {
         return h;
      } else if (var0.a(Blocks.iB)) {
         return i;
      } else if (var0.a(Blocks.kJ)) {
         return j;
      } else if (var0.a(Blocks.ch)) {
         return k;
      } else if (var0.a(Blocks.dW)) {
         return l;
      } else if (var0.a(Blocks.dU)) {
         return m;
      } else if (var0.a(Blocks.fI)) {
         return n;
      } else if (var0.a(Blocks.ii)) {
         return o;
      } else if (var0.a(Blocks.ec)) {
         return p;
      } else {
         Material var1 = var0.d();
         if (var1 == Material.J) {
            return b;
         } else if (var1 == Material.w) {
            return c;
         } else if (var1 == Material.G) {
            return d;
         } else {
            return var1 != Material.z && var1 != Material.A ? a : e;
         }
      }
   }

   static enum a {
      a,
      b,
      c;
   }
}
