package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;

public class ShriekParticleOption implements ParticleParam {
   public static final Codec<ShriekParticleOption> a = RecordCodecBuilder.create(
      var0 -> var0.group(Codec.INT.fieldOf("delay").forGetter(var0x -> var0x.c)).apply(var0, ShriekParticleOption::new)
   );
   public static final ParticleParam.a<ShriekParticleOption> b = new ParticleParam.a<ShriekParticleOption>() {
      public ShriekParticleOption a(Particle<ShriekParticleOption> var0, StringReader var1) throws CommandSyntaxException {
         var1.expect(' ');
         int var2 = var1.readInt();
         return new ShriekParticleOption(var2);
      }

      public ShriekParticleOption a(Particle<ShriekParticleOption> var0, PacketDataSerializer var1) {
         return new ShriekParticleOption(var1.m());
      }
   };
   private final int c;

   public ShriekParticleOption(int var0) {
      this.c = var0;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.c);
   }

   @Override
   public String a() {
      return String.format(Locale.ROOT, "%s %d", BuiltInRegistries.k.b(this.b()), this.c);
   }

   @Override
   public Particle<ShriekParticleOption> b() {
      return Particles.aR;
   }

   public int c() {
      return this.c;
   }
}
