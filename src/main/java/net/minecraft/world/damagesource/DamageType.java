package net.minecraft.world.damagesource;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DamageType(String msgId, DamageScaling scaling, float exhaustion, DamageEffects effects, DeathMessageType deathMessageType) {
   private final String b;
   private final DamageScaling c;
   private final float d;
   private final DamageEffects e;
   private final DeathMessageType f;
   public static final Codec<DamageType> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.STRING.fieldOf("message_id").forGetter(DamageType::a),
               DamageScaling.d.fieldOf("scaling").forGetter(DamageType::b),
               Codec.FLOAT.fieldOf("exhaustion").forGetter(DamageType::c),
               DamageEffects.g.optionalFieldOf("effects", DamageEffects.a).forGetter(DamageType::d),
               DeathMessageType.d.optionalFieldOf("death_message_type", DeathMessageType.a).forGetter(DamageType::e)
            )
            .apply(var0, DamageType::new)
   );

   public DamageType(String var0, DamageScaling var1, float var2) {
      this(var0, var1, var2, DamageEffects.a, DeathMessageType.a);
   }

   public DamageType(String var0, DamageScaling var1, float var2, DamageEffects var3) {
      this(var0, var1, var2, var3, DeathMessageType.a);
   }

   public DamageType(String var0, float var1, DamageEffects var2) {
      this(var0, DamageScaling.b, var1, var2);
   }

   public DamageType(String var0, float var1) {
      this(var0, DamageScaling.b, var1);
   }

   public DamageType(String var0, DamageScaling var1, float var2, DamageEffects var3, DeathMessageType var4) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
   }

   public String a() {
      return this.b;
   }

   public DamageScaling b() {
      return this.c;
   }

   public float c() {
      return this.d;
   }

   public DamageEffects d() {
      return this.e;
   }

   public DeathMessageType e() {
      return this.f;
   }
}
