package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketDataSerializer;

public interface ParticleParam {
   Particle<?> b();

   void a(PacketDataSerializer var1);

   String a();

   @Deprecated
   public interface a<T extends ParticleParam> {
      T b(Particle<T> var1, StringReader var2) throws CommandSyntaxException;

      T b(Particle<T> var1, PacketDataSerializer var2);
   }
}
