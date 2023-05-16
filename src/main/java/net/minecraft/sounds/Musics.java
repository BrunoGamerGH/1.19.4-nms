package net.minecraft.sounds;

import net.minecraft.core.Holder;

public class Musics {
   private static final int h = 20;
   private static final int i = 600;
   private static final int j = 12000;
   private static final int k = 24000;
   private static final int l = 6000;
   public static final Music a = new Music(SoundEffects.oj, 20, 600, true);
   public static final Music b = new Music(SoundEffects.nP, 12000, 24000, false);
   public static final Music c = new Music(SoundEffects.nQ, 0, 0, true);
   public static final Music d = new Music(SoundEffects.og, 0, 0, true);
   public static final Music e = new Music(SoundEffects.oh, 6000, 24000, true);
   public static final Music f = a(SoundEffects.oC);
   public static final Music g = a(SoundEffects.oi);

   public static Music a(Holder<SoundEffect> var0) {
      return new Music(var0, 12000, 24000, false);
   }
}
