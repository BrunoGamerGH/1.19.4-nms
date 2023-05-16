package net.minecraft.world.level.entity;

import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.world.phys.AxisAlignedBB;

public class LevelEntityGetterAdapter<T extends EntityAccess> implements LevelEntityGetter<T> {
   private final EntityLookup<T> a;
   private final EntitySectionStorage<T> b;

   public LevelEntityGetterAdapter(EntityLookup<T> var0, EntitySectionStorage<T> var1) {
      this.a = var0;
      this.b = var1;
   }

   @Nullable
   @Override
   public T a(int var0) {
      return this.a.a(var0);
   }

   @Nullable
   @Override
   public T a(UUID var0) {
      return this.a.a(var0);
   }

   @Override
   public Iterable<T> a() {
      return this.a.a();
   }

   @Override
   public <U extends T> void a(EntityTypeTest<T, U> var0, AbortableIterationConsumer<U> var1) {
      this.a.a(var0, var1);
   }

   @Override
   public void a(AxisAlignedBB var0, Consumer<T> var1) {
      this.b.b(var0, AbortableIterationConsumer.forConsumer(var1));
   }

   @Override
   public <U extends T> void a(EntityTypeTest<T, U> var0, AxisAlignedBB var1, AbortableIterationConsumer<U> var2) {
      this.b.a(var0, var1, var2);
   }
}
