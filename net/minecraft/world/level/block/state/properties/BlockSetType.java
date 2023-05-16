package net.minecraft.world.level.block.state.properties;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.level.block.SoundEffectType;

public record BlockSetType(
   String name,
   SoundEffectType soundType,
   SoundEffect doorClose,
   SoundEffect doorOpen,
   SoundEffect trapdoorClose,
   SoundEffect trapdoorOpen,
   SoundEffect pressurePlateClickOff,
   SoundEffect pressurePlateClickOn,
   SoundEffect buttonClickOff,
   SoundEffect buttonClickOn
) {
   private final String p;
   private final SoundEffectType q;
   private final SoundEffect r;
   private final SoundEffect s;
   private final SoundEffect t;
   private final SoundEffect u;
   private final SoundEffect v;
   private final SoundEffect w;
   private final SoundEffect x;
   private final SoundEffect y;
   private static final Set<BlockSetType> z = new ObjectArraySet();
   public static final BlockSetType a = a(
      new BlockSetType(
         "iron",
         SoundEffectType.f,
         SoundEffects.lB,
         SoundEffects.lC,
         SoundEffects.lJ,
         SoundEffects.lK,
         SoundEffects.mU,
         SoundEffects.mV,
         SoundEffects.wS,
         SoundEffects.wT
      )
   );
   public static final BlockSetType b = a(
      new BlockSetType(
         "gold",
         SoundEffectType.f,
         SoundEffects.lB,
         SoundEffects.lC,
         SoundEffects.lJ,
         SoundEffects.lK,
         SoundEffects.mU,
         SoundEffects.mV,
         SoundEffects.wS,
         SoundEffects.wT
      )
   );
   public static final BlockSetType c = a(
      new BlockSetType(
         "stone",
         SoundEffectType.e,
         SoundEffects.lB,
         SoundEffects.lC,
         SoundEffects.lJ,
         SoundEffects.lK,
         SoundEffects.wX,
         SoundEffects.wY,
         SoundEffects.wS,
         SoundEffects.wT
      )
   );
   public static final BlockSetType d = a(
      new BlockSetType(
         "polished_blackstone",
         SoundEffectType.e,
         SoundEffects.lB,
         SoundEffects.lC,
         SoundEffects.lJ,
         SoundEffects.lK,
         SoundEffects.wX,
         SoundEffects.wY,
         SoundEffects.wS,
         SoundEffects.wT
      )
   );
   public static final BlockSetType e = a(new BlockSetType("oak"));
   public static final BlockSetType f = a(new BlockSetType("spruce"));
   public static final BlockSetType g = a(new BlockSetType("birch"));
   public static final BlockSetType h = a(new BlockSetType("acacia"));
   public static final BlockSetType i = a(
      new BlockSetType(
         "cherry",
         SoundEffectType.aP,
         SoundEffects.dT,
         SoundEffects.dU,
         SoundEffects.dV,
         SoundEffects.dW,
         SoundEffects.dZ,
         SoundEffects.ea,
         SoundEffects.dX,
         SoundEffects.dY
      )
   );
   public static final BlockSetType j = a(new BlockSetType("jungle"));
   public static final BlockSetType k = a(new BlockSetType("dark_oak"));
   public static final BlockSetType l = a(
      new BlockSetType(
         "crimson",
         SoundEffectType.aO,
         SoundEffects.oP,
         SoundEffects.oQ,
         SoundEffects.oR,
         SoundEffects.oS,
         SoundEffects.oV,
         SoundEffects.oW,
         SoundEffects.oT,
         SoundEffects.oU
      )
   );
   public static final BlockSetType m = a(
      new BlockSetType(
         "warped",
         SoundEffectType.aO,
         SoundEffects.oP,
         SoundEffects.oQ,
         SoundEffects.oR,
         SoundEffects.oS,
         SoundEffects.oV,
         SoundEffects.oW,
         SoundEffects.oT,
         SoundEffects.oU
      )
   );
   public static final BlockSetType n = a(new BlockSetType("mangrove"));
   public static final BlockSetType o = a(
      new BlockSetType(
         "bamboo",
         SoundEffectType.aN,
         SoundEffects.aY,
         SoundEffects.aZ,
         SoundEffects.ba,
         SoundEffects.bb,
         SoundEffects.be,
         SoundEffects.bf,
         SoundEffects.bc,
         SoundEffects.bd
      )
   );

   public BlockSetType(String var0) {
      this(
         var0,
         SoundEffectType.a,
         SoundEffects.zX,
         SoundEffects.zY,
         SoundEffects.zZ,
         SoundEffects.Aa,
         SoundEffects.Ad,
         SoundEffects.Ae,
         SoundEffects.Ab,
         SoundEffects.Ac
      );
   }

   public BlockSetType(
      String var0,
      SoundEffectType var1,
      SoundEffect var2,
      SoundEffect var3,
      SoundEffect var4,
      SoundEffect var5,
      SoundEffect var6,
      SoundEffect var7,
      SoundEffect var8,
      SoundEffect var9
   ) {
      this.p = var0;
      this.q = var1;
      this.r = var2;
      this.s = var3;
      this.t = var4;
      this.u = var5;
      this.v = var6;
      this.w = var7;
      this.x = var8;
      this.y = var9;
   }

   private static BlockSetType a(BlockSetType var0) {
      z.add(var0);
      return var0;
   }

   public static Stream<BlockSetType> a() {
      return z.stream();
   }

   public String b() {
      return this.p;
   }

   public SoundEffectType c() {
      return this.q;
   }

   public SoundEffect d() {
      return this.r;
   }

   public SoundEffect e() {
      return this.s;
   }

   public SoundEffect f() {
      return this.t;
   }

   public SoundEffect g() {
      return this.u;
   }

   public SoundEffect h() {
      return this.v;
   }

   public SoundEffect i() {
      return this.w;
   }

   public SoundEffect j() {
      return this.x;
   }

   public SoundEffect k() {
      return this.y;
   }
}
