package net.minecraft.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.World;

public final class GlobalPos {
   public static final Codec<GlobalPos> a = RecordCodecBuilder.create(
      var0 -> var0.group(World.g.fieldOf("dimension").forGetter(GlobalPos::a), BlockPosition.a.fieldOf("pos").forGetter(GlobalPos::b))
            .apply(var0, GlobalPos::a)
   );
   private final ResourceKey<World> b;
   private final BlockPosition c;

   private GlobalPos(ResourceKey<World> var0, BlockPosition var1) {
      this.b = var0;
      this.c = var1;
   }

   public static GlobalPos a(ResourceKey<World> var0, BlockPosition var1) {
      return new GlobalPos(var0, var1);
   }

   public ResourceKey<World> a() {
      return this.b;
   }

   public BlockPosition b() {
      return this.c;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (var0 != null && this.getClass() == var0.getClass()) {
         GlobalPos var1 = (GlobalPos)var0;
         return Objects.equals(this.b, var1.b) && Objects.equals(this.c, var1.c);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.b, this.c);
   }

   @Override
   public String toString() {
      return this.b + " " + this.c;
   }
}
