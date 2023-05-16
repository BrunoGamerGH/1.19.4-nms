package net.minecraft.world.level.block.state.properties;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.level.block.SoundEffectType;

public record BlockPropertyWood(
   String name, BlockSetType setType, SoundEffectType soundType, SoundEffectType hangingSignSoundType, SoundEffect fenceGateClose, SoundEffect fenceGateOpen
) {
   private final String l;
   private final BlockSetType m;
   private final SoundEffectType n;
   private final SoundEffectType o;
   private final SoundEffect p;
   private final SoundEffect q;
   private static final Set<BlockPropertyWood> r = new ObjectArraySet();
   public static final BlockPropertyWood a = a(new BlockPropertyWood("oak", BlockSetType.e));
   public static final BlockPropertyWood b = a(new BlockPropertyWood("spruce", BlockSetType.f));
   public static final BlockPropertyWood c = a(new BlockPropertyWood("birch", BlockSetType.g));
   public static final BlockPropertyWood d = a(new BlockPropertyWood("acacia", BlockSetType.h));
   public static final BlockPropertyWood e = a(
      new BlockPropertyWood("cherry", BlockSetType.i, SoundEffectType.aP, SoundEffectType.aS, SoundEffects.eb, SoundEffects.ec)
   );
   public static final BlockPropertyWood f = a(new BlockPropertyWood("jungle", BlockSetType.j));
   public static final BlockPropertyWood g = a(new BlockPropertyWood("dark_oak", BlockSetType.k));
   public static final BlockPropertyWood h = a(
      new BlockPropertyWood("crimson", BlockSetType.l, SoundEffectType.aO, SoundEffectType.aL, SoundEffects.oX, SoundEffects.oY)
   );
   public static final BlockPropertyWood i = a(
      new BlockPropertyWood("warped", BlockSetType.m, SoundEffectType.aO, SoundEffectType.aL, SoundEffects.oX, SoundEffects.oY)
   );
   public static final BlockPropertyWood j = a(new BlockPropertyWood("mangrove", BlockSetType.n));
   public static final BlockPropertyWood k = a(
      new BlockPropertyWood("bamboo", BlockSetType.o, SoundEffectType.aN, SoundEffectType.aM, SoundEffects.bg, SoundEffects.bh)
   );

   public BlockPropertyWood(String var0, BlockSetType var1) {
      this(var0, var1, SoundEffectType.a, SoundEffectType.aK, SoundEffects.hx, SoundEffects.hy);
   }

   public BlockPropertyWood(String var0, BlockSetType var1, SoundEffectType var2, SoundEffectType var3, SoundEffect var4, SoundEffect var5) {
      this.l = var0;
      this.m = var1;
      this.n = var2;
      this.o = var3;
      this.p = var4;
      this.q = var5;
   }

   private static BlockPropertyWood a(BlockPropertyWood var0) {
      r.add(var0);
      return var0;
   }

   public static Stream<BlockPropertyWood> a() {
      return r.stream();
   }

   public String b() {
      return this.l;
   }

   public BlockSetType c() {
      return this.m;
   }

   public SoundEffectType d() {
      return this.n;
   }

   public SoundEffectType e() {
      return this.o;
   }

   public SoundEffect f() {
      return this.p;
   }

   public SoundEffect g() {
      return this.q;
   }
}
