package net.minecraft.world.level.gameevent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;

public class BlockPositionSource implements PositionSource {
   public static final Codec<BlockPositionSource> a = RecordCodecBuilder.create(
      var0 -> var0.group(BlockPosition.a.fieldOf("pos").forGetter(var0x -> var0x.c)).apply(var0, BlockPositionSource::new)
   );
   final BlockPosition c;

   public BlockPositionSource(BlockPosition var0) {
      this.c = var0;
   }

   @Override
   public Optional<Vec3D> a(World var0) {
      return Optional.of(Vec3D.b(this.c));
   }

   @Override
   public PositionSourceType<?> a() {
      return PositionSourceType.a;
   }

   public static class a implements PositionSourceType<BlockPositionSource> {
      public BlockPositionSource a(PacketDataSerializer var0) {
         return new BlockPositionSource(var0.f());
      }

      public void a(PacketDataSerializer var0, BlockPositionSource var1) {
         var0.a(var1.c);
      }

      @Override
      public Codec<BlockPositionSource> a() {
         return BlockPositionSource.a;
      }
   }
}
