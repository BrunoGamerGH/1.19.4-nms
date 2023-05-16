package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;

public class ParticleType extends Particle<ParticleType> implements ParticleParam {
   private static final ParticleParam.a<ParticleType> a = new ParticleParam.a<ParticleType>() {
      public ParticleType a(Particle<ParticleType> var0, StringReader var1) {
         return (ParticleType)var0;
      }

      public ParticleType a(Particle<ParticleType> var0, PacketDataSerializer var1) {
         return (ParticleType)var0;
      }
   };
   private final Codec<ParticleType> b = Codec.unit(this::f);

   protected ParticleType(boolean var0) {
      super(var0, a);
   }

   public ParticleType f() {
      return this;
   }

   @Override
   public Codec<ParticleType> e() {
      return this.b;
   }

   @Override
   public void a(PacketDataSerializer var0) {
   }

   @Override
   public String a() {
      return BuiltInRegistries.k.b(this).toString();
   }
}
