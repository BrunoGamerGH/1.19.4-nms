package net.minecraft.world.level.gameevent;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;

public class EntityPositionSource implements PositionSource {
   public static final Codec<EntityPositionSource> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               UUIDUtil.a.fieldOf("source_entity").forGetter(EntityPositionSource::b),
               Codec.FLOAT.fieldOf("y_offset").orElse(0.0F).forGetter(var0x -> var0x.d)
            )
            .apply(var0, (var0x, var1) -> new EntityPositionSource(Either.right(Either.left(var0x)), var1))
   );
   private Either<Entity, Either<UUID, Integer>> c;
   final float d;

   public EntityPositionSource(Entity var0, float var1) {
      this(Either.left(var0), var1);
   }

   EntityPositionSource(Either<Entity, Either<UUID, Integer>> var0, float var1) {
      this.c = var0;
      this.d = var1;
   }

   @Override
   public Optional<Vec3D> a(World var0) {
      if (this.c.left().isEmpty()) {
         this.b(var0);
      }

      return this.c.left().map(var0x -> var0x.de().b(0.0, (double)this.d, 0.0));
   }

   private void b(World var0) {
      ((Optional)this.c
            .map(Optional::of, var1x -> Optional.ofNullable((Entity)var1x.map(var1xx -> var0 instanceof WorldServer var2 ? var2.a(var1xx) : null, var0::a))))
         .ifPresent(var0x -> this.c = Either.left(var0x));
   }

   private UUID b() {
      return (UUID)this.c.map(Entity::cs, var0 -> (UUID)var0.map(Function.identity(), var0x -> {
            throw new RuntimeException("Unable to get entityId from uuid");
         }));
   }

   int c() {
      return this.c.map(Entity::af, var0 -> (Integer)var0.map(var0x -> {
            throw new IllegalStateException("Unable to get entityId from uuid");
         }, Function.identity()));
   }

   @Override
   public PositionSourceType<?> a() {
      return PositionSourceType.b;
   }

   public static class a implements PositionSourceType<EntityPositionSource> {
      public EntityPositionSource a(PacketDataSerializer var0) {
         return new EntityPositionSource(Either.right(Either.right(var0.m())), var0.readFloat());
      }

      public void a(PacketDataSerializer var0, EntityPositionSource var1) {
         var0.d(var1.c());
         var0.writeFloat(var1.d);
      }

      @Override
      public Codec<EntityPositionSource> a() {
         return EntityPositionSource.a;
      }
   }
}
