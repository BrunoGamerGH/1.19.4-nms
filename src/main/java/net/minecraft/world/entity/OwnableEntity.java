package net.minecraft.world.entity;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.world.level.IEntityAccess;

public interface OwnableEntity {
   @Nullable
   UUID T_();

   IEntityAccess e();

   @Nullable
   default EntityLiving H_() {
      UUID var0 = this.T_();
      return var0 == null ? null : this.e().b(var0);
   }
}
