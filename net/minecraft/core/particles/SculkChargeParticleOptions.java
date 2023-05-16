package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;

public record SculkChargeParticleOptions(float roll) implements ParticleParam {
   private final float c;
   public static final Codec<SculkChargeParticleOptions> a = RecordCodecBuilder.create(
      var0 -> var0.group(Codec.FLOAT.fieldOf("roll").forGetter(var0x -> var0x.c)).apply(var0, SculkChargeParticleOptions::new)
   );
   public static final ParticleParam.a<SculkChargeParticleOptions> b = new ParticleParam.a<SculkChargeParticleOptions>() {
      public SculkChargeParticleOptions a(Particle<SculkChargeParticleOptions> var0, StringReader var1) throws CommandSyntaxException {
         var1.expect(' ');
         float var2 = var1.readFloat();
         return new SculkChargeParticleOptions(var2);
      }

      public SculkChargeParticleOptions a(Particle<SculkChargeParticleOptions> var0, PacketDataSerializer var1) {
         return new SculkChargeParticleOptions(var1.readFloat());
      }
   };

   public SculkChargeParticleOptions(float var0) {
      this.c = var0;
   }

   @Override
   public Particle<SculkChargeParticleOptions> b() {
      return Particles.H;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeFloat(this.c);
   }

   @Override
   public String a() {
      return String.format(Locale.ROOT, "%s %.2f", BuiltInRegistries.k.b(this.b()), this.c);
   }
}
