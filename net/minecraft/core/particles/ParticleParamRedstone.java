package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.phys.Vec3D;
import org.joml.Vector3f;

public class ParticleParamRedstone extends DustParticleOptionsBase {
   public static final Vector3f a = Vec3D.a(16711680).j();
   public static final ParticleParamRedstone b = new ParticleParamRedstone(a, 1.0F);
   public static final Codec<ParticleParamRedstone> c = RecordCodecBuilder.create(
      var0 -> var0.group(ExtraCodecs.c.fieldOf("color").forGetter(var0x -> var0x.g), Codec.FLOAT.fieldOf("scale").forGetter(var0x -> var0x.h))
            .apply(var0, ParticleParamRedstone::new)
   );
   public static final ParticleParam.a<ParticleParamRedstone> d = new ParticleParam.a<ParticleParamRedstone>() {
      public ParticleParamRedstone a(Particle<ParticleParamRedstone> var0, StringReader var1) throws CommandSyntaxException {
         Vector3f var2 = DustParticleOptionsBase.a(var1);
         var1.expect(' ');
         float var3 = var1.readFloat();
         return new ParticleParamRedstone(var2, var3);
      }

      public ParticleParamRedstone a(Particle<ParticleParamRedstone> var0, PacketDataSerializer var1) {
         return new ParticleParamRedstone(DustParticleOptionsBase.b(var1), var1.readFloat());
      }
   };

   public ParticleParamRedstone(Vector3f var0, float var1) {
      super(var0, var1);
   }

   @Override
   public Particle<ParticleParamRedstone> b() {
      return Particles.o;
   }
}
