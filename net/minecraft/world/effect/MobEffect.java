package net.minecraft.world.effect;

import com.google.common.collect.ComparisonChain;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityLiving;
import org.slf4j.Logger;

public class MobEffect implements Comparable<MobEffect> {
   private static final Logger b = LogUtils.getLogger();
   public static final int a = -1;
   private final MobEffectList c;
   private int d;
   private int e;
   private boolean f;
   private boolean g;
   private boolean h;
   @Nullable
   private MobEffect i;
   private final Optional<MobEffect.a> j;

   public MobEffect(MobEffectList var0) {
      this(var0, 0, 0);
   }

   public MobEffect(MobEffectList var0, int var1) {
      this(var0, var1, 0);
   }

   public MobEffect(MobEffectList var0, int var1, int var2) {
      this(var0, var1, var2, false, true);
   }

   public MobEffect(MobEffectList var0, int var1, int var2, boolean var3, boolean var4) {
      this(var0, var1, var2, var3, var4, var4);
   }

   public MobEffect(MobEffectList var0, int var1, int var2, boolean var3, boolean var4, boolean var5) {
      this(var0, var1, var2, var3, var4, var5, null, var0.b());
   }

   public MobEffect(MobEffectList var0, int var1, int var2, boolean var3, boolean var4, boolean var5, @Nullable MobEffect var6, Optional<MobEffect.a> var7) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3;
      this.g = var4;
      this.h = var5;
      this.i = var6;
      this.j = var7;
   }

   public MobEffect(MobEffect var0) {
      this.c = var0.c;
      this.j = this.c.b();
      this.a(var0);
   }

   public Optional<MobEffect.a> a() {
      return this.j;
   }

   void a(MobEffect var0) {
      this.d = var0.d;
      this.e = var0.e;
      this.f = var0.f;
      this.g = var0.g;
      this.h = var0.h;
   }

   public boolean b(MobEffect var0) {
      if (this.c != var0.c) {
         b.warn("This method should only be called for matching effects!");
      }

      int var1 = this.d;
      boolean var2 = false;
      if (var0.e > this.e) {
         if (var0.d(this)) {
            MobEffect var3 = this.i;
            this.i = new MobEffect(this);
            this.i.i = var3;
         }

         this.e = var0.e;
         this.d = var0.d;
         var2 = true;
      } else if (this.d(var0)) {
         if (var0.e == this.e) {
            this.d = var0.d;
            var2 = true;
         } else if (this.i == null) {
            this.i = new MobEffect(var0);
         } else {
            this.i.b(var0);
         }
      }

      if (!var0.f && this.f || var2) {
         this.f = var0.f;
         var2 = true;
      }

      if (var0.g != this.g) {
         this.g = var0.g;
         var2 = true;
      }

      if (var0.h != this.h) {
         this.h = var0.h;
         var2 = true;
      }

      return var2;
   }

   private boolean d(MobEffect var0) {
      return !this.b() && (this.d < var0.d || var0.b());
   }

   public boolean b() {
      return this.d == -1;
   }

   public boolean a(int var0) {
      return !this.b() && this.d <= var0;
   }

   public int a(Int2IntFunction var0) {
      return !this.b() && this.d != 0 ? var0.applyAsInt(this.d) : this.d;
   }

   public MobEffectList c() {
      return this.c;
   }

   public int d() {
      return this.d;
   }

   public int e() {
      return this.e;
   }

   public boolean f() {
      return this.f;
   }

   public boolean g() {
      return this.g;
   }

   public boolean h() {
      return this.h;
   }

   public boolean a(EntityLiving var0, Runnable var1) {
      if (this.j()) {
         int var2 = this.b() ? var0.ag : this.d;
         if (this.c.a(var2, this.e)) {
            this.a(var0);
         }

         this.k();
         if (this.d == 0 && this.i != null) {
            this.a(this.i);
            this.i = this.i.i;
            var1.run();
         }
      }

      this.j.ifPresent(var0x -> var0x.a(this));
      return this.j();
   }

   private boolean j() {
      return this.b() || this.d > 0;
   }

   private int k() {
      if (this.i != null) {
         this.i.k();
      }

      return this.d = this.a(var0 -> var0 - 1);
   }

   public void a(EntityLiving var0) {
      if (this.j()) {
         this.c.a(var0, this.e);
      }
   }

   public String i() {
      return this.c.d();
   }

   @Override
   public String toString() {
      String var0;
      if (this.e > 0) {
         var0 = this.i() + " x " + (this.e + 1) + ", Duration: " + this.l();
      } else {
         var0 = this.i() + ", Duration: " + this.l();
      }

      if (!this.g) {
         var0 = var0 + ", Particles: false";
      }

      if (!this.h) {
         var0 = var0 + ", Show Icon: false";
      }

      return var0;
   }

   private String l() {
      return this.b() ? "infinite" : Integer.toString(this.d);
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof MobEffect)) {
         return false;
      } else {
         MobEffect var1 = (MobEffect)var0;
         return this.d == var1.d && this.e == var1.e && this.f == var1.f && this.c.equals(var1.c);
      }
   }

   @Override
   public int hashCode() {
      int var0 = this.c.hashCode();
      var0 = 31 * var0 + this.d;
      var0 = 31 * var0 + this.e;
      return 31 * var0 + (this.f ? 1 : 0);
   }

   public NBTTagCompound a(NBTTagCompound var0) {
      var0.a("Id", MobEffectList.a(this.c()));
      this.c(var0);
      return var0;
   }

   private void c(NBTTagCompound var0) {
      var0.a("Amplifier", (byte)this.e());
      var0.a("Duration", this.d());
      var0.a("Ambient", this.f());
      var0.a("ShowParticles", this.g());
      var0.a("ShowIcon", this.h());
      if (this.i != null) {
         NBTTagCompound var1 = new NBTTagCompound();
         this.i.a(var1);
         var0.a("HiddenEffect", var1);
      }

      this.j
         .ifPresent(
            var1x -> MobEffect.a.a.encodeStart(DynamicOpsNBT.a, var1x).resultOrPartial(b::error).ifPresent(var1xx -> var0.a("FactorCalculationData", var1xx))
         );
   }

   @Nullable
   public static MobEffect b(NBTTagCompound var0) {
      int var1 = var0.h("Id");
      MobEffectList var2 = MobEffectList.a(var1);
      return var2 == null ? null : a(var2, var0);
   }

   private static MobEffect a(MobEffectList var0, NBTTagCompound var1) {
      int var2 = var1.f("Amplifier");
      int var3 = var1.h("Duration");
      boolean var4 = var1.q("Ambient");
      boolean var5 = true;
      if (var1.b("ShowParticles", 1)) {
         var5 = var1.q("ShowParticles");
      }

      boolean var6 = var5;
      if (var1.b("ShowIcon", 1)) {
         var6 = var1.q("ShowIcon");
      }

      MobEffect var7 = null;
      if (var1.b("HiddenEffect", 10)) {
         var7 = a(var0, var1.p("HiddenEffect"));
      }

      Optional<MobEffect.a> var8;
      if (var1.b("FactorCalculationData", 10)) {
         var8 = MobEffect.a.a.parse(new Dynamic(DynamicOpsNBT.a, var1.p("FactorCalculationData"))).resultOrPartial(b::error);
      } else {
         var8 = Optional.empty();
      }

      return new MobEffect(var0, var3, Math.max(var2, 0), var4, var5, var6, var7, var8);
   }

   public int c(MobEffect var0) {
      int var1 = 32147;
      return (this.d() <= 32147 || var0.d() <= 32147) && (!this.f() || !var0.f())
         ? ComparisonChain.start()
            .compareFalseFirst(this.f(), var0.f())
            .compareFalseFirst(this.b(), var0.b())
            .compare(this.d(), var0.d())
            .compare(this.c().g(), var0.c().g())
            .result()
         : ComparisonChain.start().compare(this.f(), var0.f()).compare(this.c().g(), var0.c().g()).result();
   }

   public static class a {
      public static final Codec<MobEffect.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  ExtraCodecs.h.fieldOf("padding_duration").forGetter(var0x -> var0x.b),
                  Codec.FLOAT.fieldOf("factor_start").orElse(0.0F).forGetter(var0x -> var0x.c),
                  Codec.FLOAT.fieldOf("factor_target").orElse(1.0F).forGetter(var0x -> var0x.d),
                  Codec.FLOAT.fieldOf("factor_current").orElse(0.0F).forGetter(var0x -> var0x.e),
                  ExtraCodecs.h.fieldOf("ticks_active").orElse(0).forGetter(var0x -> var0x.f),
                  Codec.FLOAT.fieldOf("factor_previous_frame").orElse(0.0F).forGetter(var0x -> var0x.g),
                  Codec.BOOL.fieldOf("had_effect_last_tick").orElse(false).forGetter(var0x -> var0x.h)
               )
               .apply(var0, MobEffect.a::new)
      );
      private final int b;
      private float c;
      private float d;
      private float e;
      private int f;
      private float g;
      private boolean h;

      public a(int var0, float var1, float var2, float var3, int var4, float var5, boolean var6) {
         this.b = var0;
         this.c = var1;
         this.d = var2;
         this.e = var3;
         this.f = var4;
         this.g = var5;
         this.h = var6;
      }

      public a(int var0) {
         this(var0, 0.0F, 1.0F, 0.0F, 0, 0.0F, false);
      }

      public void a(MobEffect var0) {
         this.g = this.e;
         boolean var1 = !var0.a(this.b);
         ++this.f;
         if (this.h != var1) {
            this.h = var1;
            this.f = 0;
            this.c = this.e;
            this.d = var1 ? 1.0F : 0.0F;
         }

         float var2 = MathHelper.a((float)this.f / (float)this.b, 0.0F, 1.0F);
         this.e = MathHelper.i(var2, this.c, this.d);
      }

      public float a(EntityLiving var0, float var1) {
         if (var0.dB()) {
            this.g = this.e;
         }

         return MathHelper.i(var1, this.g, this.e);
      }
   }
}
