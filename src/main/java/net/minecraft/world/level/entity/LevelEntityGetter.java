package net.minecraft.world.level.entity;

import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.world.phys.AxisAlignedBB;

public interface LevelEntityGetter<T extends EntityAccess> {
   @Nullable
   T a(int var1);

   @Nullable
   T a(UUID var1);

   Iterable<T> a();

   <U extends T> void a(EntityTypeTest<T, U> var1, AbortableIterationConsumer<U> var2);

   void a(AxisAlignedBB var1, Consumer<T> var2);

   <U extends T> void a(EntityTypeTest<T, U> var1, AxisAlignedBB var2, AbortableIterationConsumer<U> var3);
}
