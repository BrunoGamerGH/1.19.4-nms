package net.minecraft.world.level.gameevent.vibrations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;

public class VibrationSelector {
   public static final Codec<VibrationSelector> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               VibrationInfo.a.optionalFieldOf("event").forGetter(var0x -> var0x.b.map(Pair::getLeft)),
               Codec.LONG.fieldOf("tick").forGetter(var0x -> var0x.b.<Long>map(Pair::getRight).orElse(-1L))
            )
            .apply(var0, VibrationSelector::new)
   );
   private Optional<Pair<VibrationInfo, Long>> b;

   public VibrationSelector(Optional<VibrationInfo> var0, long var1) {
      this.b = var0.map(var2x -> Pair.of(var2x, var1));
   }

   public VibrationSelector() {
      this.b = Optional.empty();
   }

   public void a(VibrationInfo var0, long var1) {
      if (this.b(var0, var1)) {
         this.b = Optional.of(Pair.of(var0, var1));
      }
   }

   private boolean b(VibrationInfo var0, long var1) {
      if (this.b.isEmpty()) {
         return true;
      } else {
         Pair<VibrationInfo, Long> var3 = (Pair)this.b.get();
         long var4 = var3.getRight();
         if (var1 != var4) {
            return false;
         } else {
            VibrationInfo var6 = (VibrationInfo)var3.getLeft();
            if (var0.b() < var6.b()) {
               return true;
            } else if (var0.b() > var6.b()) {
               return false;
            } else {
               return VibrationListener.a(var0.a()) > VibrationListener.a(var6.a());
            }
         }
      }
   }

   public Optional<VibrationInfo> a(long var0) {
      if (this.b.isEmpty()) {
         return Optional.empty();
      } else {
         return ((Pair)this.b.get()).getRight() < var0 ? Optional.of((VibrationInfo)((Pair)this.b.get()).getLeft()) : Optional.empty();
      }
   }

   public void a() {
      this.b = Optional.empty();
   }
}
