package com.mojang.math;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPropertyJigsawOrientation;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.INamable;
import org.joml.Matrix3f;

public enum PointGroupO implements INamable {
   a("identity", PointGroupS.a, false, false, false),
   b("rot_180_face_xy", PointGroupS.a, true, true, false),
   c("rot_180_face_xz", PointGroupS.a, true, false, true),
   d("rot_180_face_yz", PointGroupS.a, false, true, true),
   e("rot_120_nnn", PointGroupS.d, false, false, false),
   f("rot_120_nnp", PointGroupS.e, true, false, true),
   g("rot_120_npn", PointGroupS.e, false, true, true),
   h("rot_120_npp", PointGroupS.d, true, false, true),
   i("rot_120_pnn", PointGroupS.e, true, true, false),
   j("rot_120_pnp", PointGroupS.d, true, true, false),
   k("rot_120_ppn", PointGroupS.d, false, true, true),
   l("rot_120_ppp", PointGroupS.e, false, false, false),
   m("rot_180_edge_xy_neg", PointGroupS.b, true, true, true),
   n("rot_180_edge_xy_pos", PointGroupS.b, false, false, true),
   o("rot_180_edge_xz_neg", PointGroupS.f, true, true, true),
   p("rot_180_edge_xz_pos", PointGroupS.f, false, true, false),
   q("rot_180_edge_yz_neg", PointGroupS.c, true, true, true),
   r("rot_180_edge_yz_pos", PointGroupS.c, true, false, false),
   s("rot_90_x_neg", PointGroupS.c, false, false, true),
   t("rot_90_x_pos", PointGroupS.c, false, true, false),
   u("rot_90_y_neg", PointGroupS.f, true, false, false),
   v("rot_90_y_pos", PointGroupS.f, false, false, true),
   w("rot_90_z_neg", PointGroupS.b, false, true, false),
   x("rot_90_z_pos", PointGroupS.b, true, false, false),
   y("inversion", PointGroupS.a, true, true, true),
   z("invert_x", PointGroupS.a, true, false, false),
   A("invert_y", PointGroupS.a, false, true, false),
   B("invert_z", PointGroupS.a, false, false, true),
   C("rot_60_ref_nnn", PointGroupS.e, true, true, true),
   D("rot_60_ref_nnp", PointGroupS.d, true, false, false),
   E("rot_60_ref_npn", PointGroupS.d, false, false, true),
   F("rot_60_ref_npp", PointGroupS.e, false, false, true),
   G("rot_60_ref_pnn", PointGroupS.d, false, true, false),
   H("rot_60_ref_pnp", PointGroupS.e, true, false, false),
   I("rot_60_ref_ppn", PointGroupS.e, false, true, false),
   J("rot_60_ref_ppp", PointGroupS.d, true, true, true),
   K("swap_xy", PointGroupS.b, false, false, false),
   L("swap_yz", PointGroupS.c, false, false, false),
   M("swap_xz", PointGroupS.f, false, false, false),
   N("swap_neg_xy", PointGroupS.b, true, true, false),
   O("swap_neg_yz", PointGroupS.c, false, true, true),
   P("swap_neg_xz", PointGroupS.f, true, false, true),
   Q("rot_90_ref_x_neg", PointGroupS.c, true, false, true),
   R("rot_90_ref_x_pos", PointGroupS.c, true, true, false),
   S("rot_90_ref_y_neg", PointGroupS.f, true, true, false),
   T("rot_90_ref_y_pos", PointGroupS.f, false, true, true),
   U("rot_90_ref_z_neg", PointGroupS.b, false, true, true),
   V("rot_90_ref_z_pos", PointGroupS.b, true, false, true);

   private final Matrix3f X;
   private final String Y;
   @Nullable
   private Map<EnumDirection, EnumDirection> Z;
   private final boolean aa;
   private final boolean ab;
   private final boolean ac;
   private final PointGroupS ad;
   private static final PointGroupO[][] ae = SystemUtils.a(
      new PointGroupO[values().length][values().length],
      var0 -> {
         Map<Pair<PointGroupS, BooleanList>, PointGroupO> var1 = Arrays.stream(values())
            .collect(Collectors.toMap(var0x -> Pair.of(var0x.ad, var0x.d()), var0x -> var0x));
   
         for(PointGroupO var5 : values()) {
            for(PointGroupO var9 : values()) {
               BooleanList var10 = var5.d();
               BooleanList var11 = var9.d();
               PointGroupS var12 = var9.ad.a(var5.ad);
               BooleanArrayList var13 = new BooleanArrayList(3);
   
               for(int var14 = 0; var14 < 3; ++var14) {
                  var13.add(var10.getBoolean(var14) ^ var11.getBoolean(var5.ad.a(var14)));
               }
   
               var0[var5.ordinal()][var9.ordinal()] = var1.get(Pair.of(var12, var13));
            }
         }
      }
   );
   private static final PointGroupO[] af = Arrays.stream(values())
      .map(var0 -> Arrays.stream(values()).filter(var1 -> var0.a(var1) == a).findAny().get())
      .toArray(var0 -> new PointGroupO[var0]);

   private PointGroupO(String var2, PointGroupS var3, boolean var4, boolean var5, boolean var6) {
      this.Y = var2;
      this.aa = var4;
      this.ab = var5;
      this.ac = var6;
      this.ad = var3;
      this.X = new Matrix3f().scaling(var4 ? -1.0F : 1.0F, var5 ? -1.0F : 1.0F, var6 ? -1.0F : 1.0F);
      this.X.mul(var3.a());
   }

   private BooleanList d() {
      return new BooleanArrayList(new boolean[]{this.aa, this.ab, this.ac});
   }

   public PointGroupO a(PointGroupO var0) {
      return ae[this.ordinal()][var0.ordinal()];
   }

   public PointGroupO a() {
      return af[this.ordinal()];
   }

   public Matrix3f b() {
      return new Matrix3f(this.X);
   }

   @Override
   public String toString() {
      return this.Y;
   }

   @Override
   public String c() {
      return this.Y;
   }

   public EnumDirection a(EnumDirection var0) {
      if (this.Z == null) {
         this.Z = Maps.newEnumMap(EnumDirection.class);
         EnumDirection.EnumAxis[] var1 = EnumDirection.EnumAxis.values();

         for(EnumDirection var5 : EnumDirection.values()) {
            EnumDirection.EnumAxis var6 = var5.o();
            EnumDirection.EnumAxisDirection var7 = var5.f();
            EnumDirection.EnumAxis var8 = var1[this.ad.a(var6.ordinal())];
            EnumDirection.EnumAxisDirection var9 = this.a(var8) ? var7.c() : var7;
            EnumDirection var10 = EnumDirection.a(var8, var9);
            this.Z.put(var5, var10);
         }
      }

      return this.Z.get(var0);
   }

   public boolean a(EnumDirection.EnumAxis var0) {
      switch(var0) {
         case a:
            return this.aa;
         case b:
            return this.ab;
         case c:
         default:
            return this.ac;
      }
   }

   public BlockPropertyJigsawOrientation a(BlockPropertyJigsawOrientation var0) {
      return BlockPropertyJigsawOrientation.a(this.a(var0.a()), this.a(var0.b()));
   }
}
