package net.minecraft.world.entity.ai.behavior;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.level.block.Block;

public class LongJumpToPreferredBlock<E extends EntityInsentient> extends LongJumpToRandomPos<E> {
   private final TagKey<Block> m;
   private final float n;
   private final List<LongJumpToRandomPos.a> o = new ArrayList<>();
   private boolean p;

   public LongJumpToPreferredBlock(
      UniformInt var0, int var1, int var2, float var3, Function<E, SoundEffect> var4, TagKey<Block> var5, float var6, BiPredicate<E, BlockPosition> var7
   ) {
      super(var0, var1, var2, var3, var4, var7);
      this.m = var5;
      this.n = var6;
   }

   @Override
   protected void a(WorldServer var0, E var1, long var2) {
      super.a(var0, var1, var2);
      this.o.clear();
      this.p = var1.dZ().i() < this.n;
   }

   @Override
   protected Optional<LongJumpToRandomPos.a> a(WorldServer var0) {
      if (!this.p) {
         return super.a(var0);
      } else {
         BlockPosition.MutableBlockPosition var1 = new BlockPosition.MutableBlockPosition();

         while(!this.h.isEmpty()) {
            Optional<LongJumpToRandomPos.a> var2 = super.a(var0);
            if (var2.isPresent()) {
               LongJumpToRandomPos.a var3 = var2.get();
               if (var0.a_(var1.a(var3.b(), EnumDirection.a)).a(this.m)) {
                  return var2;
               }

               this.o.add(var3);
            }
         }

         return !this.o.isEmpty() ? Optional.of(this.o.remove(0)) : Optional.empty();
      }
   }
}
