package net.minecraft.world.level.entity;

import java.util.UUID;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AxisAlignedBB;

public interface EntityAccess {
   int af();

   UUID cs();

   BlockPosition dg();

   AxisAlignedBB cD();

   void a(EntityInLevelCallback var1);

   Stream<? extends EntityAccess> cO();

   Stream<? extends EntityAccess> cP();

   void b(Entity.RemovalReason var1);

   boolean dE();

   boolean dF();
}
