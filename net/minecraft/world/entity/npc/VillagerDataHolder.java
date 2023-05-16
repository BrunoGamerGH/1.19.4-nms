package net.minecraft.world.entity.npc;

import net.minecraft.world.entity.VariantHolder;

public interface VillagerDataHolder extends VariantHolder<VillagerType> {
   VillagerData gd();

   void a(VillagerData var1);

   default VillagerType a() {
      return this.gd().a();
   }

   default void a(VillagerType var0) {
      this.a(this.gd().a(var0));
   }
}
