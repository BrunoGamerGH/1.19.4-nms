package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Locale;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.util.MathHelper;
import org.joml.Vector3f;

public abstract class DustParticleOptionsBase implements ParticleParam {
   public static final float e = 0.01F;
   public static final float f = 4.0F;
   protected final Vector3f g;
   protected final float h;

   public DustParticleOptionsBase(Vector3f var0, float var1) {
      this.g = var0;
      this.h = MathHelper.a(var1, 0.01F, 4.0F);
   }

   public static Vector3f a(StringReader var0) throws CommandSyntaxException {
      var0.expect(' ');
      float var1 = var0.readFloat();
      var0.expect(' ');
      float var2 = var0.readFloat();
      var0.expect(' ');
      float var3 = var0.readFloat();
      return new Vector3f(var1, var2, var3);
   }

   public static Vector3f b(PacketDataSerializer var0) {
      return new Vector3f(var0.readFloat(), var0.readFloat(), var0.readFloat());
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeFloat(this.g.x());
      var0.writeFloat(this.g.y());
      var0.writeFloat(this.g.z());
      var0.writeFloat(this.h);
   }

   @Override
   public String a() {
      return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", BuiltInRegistries.k.b(this.b()), this.g.x(), this.g.y(), this.g.z(), this.h);
   }

   public Vector3f e() {
      return this.g;
   }

   public float f() {
      return this.h;
   }
}
