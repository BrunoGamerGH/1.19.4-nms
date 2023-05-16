package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.phys.Vec3D;
import org.joml.Vector3f;

public class DustColorTransitionOptions extends DustParticleOptionsBase {
   public static final Vector3f a = Vec3D.a(3790560).j();
   public static final DustColorTransitionOptions b = new DustColorTransitionOptions(a, ParticleParamRedstone.a, 1.0F);
   public static final Codec<DustColorTransitionOptions> c = RecordCodecBuilder.create(
      var0 -> var0.group(
               ExtraCodecs.c.fieldOf("fromColor").forGetter(var0x -> var0x.g),
               ExtraCodecs.c.fieldOf("toColor").forGetter(var0x -> var0x.i),
               Codec.FLOAT.fieldOf("scale").forGetter(var0x -> var0x.h)
            )
            .apply(var0, DustColorTransitionOptions::new)
   );
   public static final ParticleParam.a<DustColorTransitionOptions> d = new ParticleParam.a<DustColorTransitionOptions>() {
      public DustColorTransitionOptions a(Particle<DustColorTransitionOptions> var0, StringReader var1) throws CommandSyntaxException {
         Vector3f var2 = DustParticleOptionsBase.a(var1);
         var1.expect(' ');
         float var3 = var1.readFloat();
         Vector3f var4 = DustParticleOptionsBase.a(var1);
         return new DustColorTransitionOptions(var2, var4, var3);
      }

      public DustColorTransitionOptions a(Particle<DustColorTransitionOptions> var0, PacketDataSerializer var1) {
         Vector3f var2 = DustParticleOptionsBase.b(var1);
         float var3 = var1.readFloat();
         Vector3f var4 = DustParticleOptionsBase.b(var1);
         return new DustColorTransitionOptions(var2, var4, var3);
      }
   };
   private final Vector3f i;

   public DustColorTransitionOptions(Vector3f var0, Vector3f var1, float var2) {
      super(var0, var2);
      this.i = var1;
   }

   public Vector3f c() {
      return this.g;
   }

   public Vector3f d() {
      return this.i;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      super.a(var0);
      var0.writeFloat(this.i.x());
      var0.writeFloat(this.i.y());
      var0.writeFloat(this.i.z());
   }

   @Override
   public String a() {
      return String.format(
         Locale.ROOT,
         "%s %.2f %.2f %.2f %.2f %.2f %.2f %.2f",
         BuiltInRegistries.k.b(this.b()),
         this.g.x(),
         this.g.y(),
         this.g.z(),
         this.h,
         this.i.x(),
         this.i.y(),
         this.i.z()
      );
   }

   @Override
   public Particle<DustColorTransitionOptions> b() {
      return Particles.p;
   }
}
