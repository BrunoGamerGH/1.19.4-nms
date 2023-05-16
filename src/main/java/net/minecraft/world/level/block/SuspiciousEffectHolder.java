package net.minecraft.world.level.block;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.level.IMaterial;

public interface SuspiciousEffectHolder {
   MobEffectList b();

   int c();

   static List<SuspiciousEffectHolder> a() {
      return BuiltInRegistries.i.s().map(SuspiciousEffectHolder::a).filter(Objects::nonNull).collect(Collectors.toList());
   }

   @Nullable
   static SuspiciousEffectHolder a(IMaterial var0) {
      Item var3 = var0.k();
      if (var3 instanceof ItemBlock var1) {
         Block var6 = var1.e();
         if (var6 instanceof SuspiciousEffectHolder var2) {
            return var2;
         }
      }

      Item var2 = var0.k();
      return var2 instanceof SuspiciousEffectHolder var1 ? var1 : null;
   }
}
