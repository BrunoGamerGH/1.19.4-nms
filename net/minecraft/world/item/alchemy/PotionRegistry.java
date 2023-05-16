package net.minecraft.world.item.alchemy;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.effect.MobEffect;

public class PotionRegistry {
   @Nullable
   private final String a;
   private final ImmutableList<MobEffect> b;

   public static PotionRegistry a(String var0) {
      return BuiltInRegistries.j.a(MinecraftKey.a(var0));
   }

   public PotionRegistry(MobEffect... var0) {
      this(null, var0);
   }

   public PotionRegistry(@Nullable String var0, MobEffect... var1) {
      this.a = var0;
      this.b = ImmutableList.copyOf(var1);
   }

   public String b(String var0) {
      return var0 + (this.a == null ? BuiltInRegistries.j.b(this).a() : this.a);
   }

   public List<MobEffect> a() {
      return this.b;
   }

   public boolean b() {
      if (!this.b.isEmpty()) {
         UnmodifiableIterator var1 = this.b.iterator();

         while(var1.hasNext()) {
            MobEffect var1x = (MobEffect)var1.next();
            if (var1x.c().a()) {
               return true;
            }
         }
      }

      return false;
   }
}
