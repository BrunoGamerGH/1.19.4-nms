package net.minecraft.world.entity.ai.village.poi;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.util.VisibleForDebug;

public class VillagePlaceRecord {
   private final BlockPosition a;
   private final Holder<VillagePlaceType> b;
   private int c;
   private final Runnable d;

   public static Codec<VillagePlaceRecord> a(Runnable var0) {
      return RecordCodecBuilder.create(
         var1 -> var1.group(
                  BlockPosition.a.fieldOf("pos").forGetter(var0xx -> var0xx.a),
                  RegistryFixedCodec.a(Registries.R).fieldOf("type").forGetter(var0xx -> var0xx.b),
                  Codec.INT.fieldOf("free_tickets").orElse(0).forGetter(var0xx -> var0xx.c),
                  RecordCodecBuilder.point(var0)
               )
               .apply(var1, VillagePlaceRecord::new)
      );
   }

   private VillagePlaceRecord(BlockPosition var0, Holder<VillagePlaceType> var1, int var2, Runnable var3) {
      this.a = var0.i();
      this.b = var1;
      this.c = var2;
      this.d = var3;
   }

   public VillagePlaceRecord(BlockPosition var0, Holder<VillagePlaceType> var1, Runnable var2) {
      this(var0, var1, var1.a().b(), var2);
   }

   @Deprecated
   @VisibleForDebug
   public int a() {
      return this.c;
   }

   protected boolean b() {
      if (this.c <= 0) {
         return false;
      } else {
         --this.c;
         this.d.run();
         return true;
      }
   }

   protected boolean c() {
      if (this.c >= this.b.a().b()) {
         return false;
      } else {
         ++this.c;
         this.d.run();
         return true;
      }
   }

   public boolean d() {
      return this.c > 0;
   }

   public boolean e() {
      return this.c != this.b.a().b();
   }

   public BlockPosition f() {
      return this.a;
   }

   public Holder<VillagePlaceType> g() {
      return this.b;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         return var0 != null && this.getClass() == var0.getClass() ? Objects.equals(this.a, ((VillagePlaceRecord)var0).a) : false;
      }
   }

   @Override
   public int hashCode() {
      return this.a.hashCode();
   }
}
