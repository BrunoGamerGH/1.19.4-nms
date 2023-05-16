package net.minecraft.world.level.gameevent;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;

public interface PositionSource {
   Codec<PositionSource> b = BuiltInRegistries.v.q().dispatch(PositionSource::a, PositionSourceType::a);

   Optional<Vec3D> a(World var1);

   PositionSourceType<?> a();
}
