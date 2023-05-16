package net.minecraft.world.entity.monster.warden;

import java.util.Arrays;
import net.minecraft.SystemUtils;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;

public enum AngerLevel {
   a(0, SoundEffects.yX, SoundEffects.zf),
   b(40, SoundEffects.yW, SoundEffects.zg),
   c(80, SoundEffects.yY, SoundEffects.zg);

   private static final AngerLevel[] d = SystemUtils.a(values(), var0 -> Arrays.sort(var0, (var0x, var1) -> Integer.compare(var1.e, var0x.e)));
   private final int e;
   private final SoundEffect f;
   private final SoundEffect g;

   private AngerLevel(int var2, SoundEffect var3, SoundEffect var4) {
      this.e = var2;
      this.f = var3;
      this.g = var4;
   }

   public int a() {
      return this.e;
   }

   public SoundEffect b() {
      return this.f;
   }

   public SoundEffect c() {
      return this.g;
   }

   public static AngerLevel a(int var0) {
      for(AngerLevel var4 : d) {
         if (var0 >= var4.e) {
            return var4;
         }
      }

      return a;
   }

   public boolean d() {
      return this == c;
   }
}
