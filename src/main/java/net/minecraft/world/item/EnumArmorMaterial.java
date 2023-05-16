package net.minecraft.world.item;

import java.util.EnumMap;
import java.util.function.Supplier;
import net.minecraft.SystemUtils;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.INamable;
import net.minecraft.util.LazyInitVar;
import net.minecraft.world.item.crafting.RecipeItemStack;

public enum EnumArmorMaterial implements INamable, ArmorMaterial {
   a("leather", 5, SystemUtils.a(new EnumMap<>(ItemArmor.a.class), var0 -> {
      var0.put(ItemArmor.a.d, 1);
      var0.put(ItemArmor.a.c, 2);
      var0.put(ItemArmor.a.b, 3);
      var0.put(ItemArmor.a.a, 1);
   }), 15, SoundEffects.ah, 0.0F, 0.0F, () -> RecipeItemStack.a(Items.pL)),
   b("chainmail", 15, SystemUtils.a(new EnumMap<>(ItemArmor.a.class), var0 -> {
      var0.put(ItemArmor.a.d, 1);
      var0.put(ItemArmor.a.c, 4);
      var0.put(ItemArmor.a.b, 5);
      var0.put(ItemArmor.a.a, 2);
   }), 12, SoundEffects.ab, 0.0F, 0.0F, () -> RecipeItemStack.a(Items.nM)),
   c("iron", 15, SystemUtils.a(new EnumMap<>(ItemArmor.a.class), var0 -> {
      var0.put(ItemArmor.a.d, 2);
      var0.put(ItemArmor.a.c, 5);
      var0.put(ItemArmor.a.b, 6);
      var0.put(ItemArmor.a.a, 2);
   }), 9, SoundEffects.ag, 0.0F, 0.0F, () -> RecipeItemStack.a(Items.nM)),
   d("gold", 7, SystemUtils.a(new EnumMap<>(ItemArmor.a.class), var0 -> {
      var0.put(ItemArmor.a.d, 1);
      var0.put(ItemArmor.a.c, 3);
      var0.put(ItemArmor.a.b, 5);
      var0.put(ItemArmor.a.a, 2);
   }), 25, SoundEffects.af, 0.0F, 0.0F, () -> RecipeItemStack.a(Items.nQ)),
   e("diamond", 33, SystemUtils.a(new EnumMap<>(ItemArmor.a.class), var0 -> {
      var0.put(ItemArmor.a.d, 3);
      var0.put(ItemArmor.a.c, 6);
      var0.put(ItemArmor.a.b, 8);
      var0.put(ItemArmor.a.a, 3);
   }), 10, SoundEffects.ac, 2.0F, 0.0F, () -> RecipeItemStack.a(Items.nG)),
   f("turtle", 25, SystemUtils.a(new EnumMap<>(ItemArmor.a.class), var0 -> {
      var0.put(ItemArmor.a.d, 2);
      var0.put(ItemArmor.a.c, 5);
      var0.put(ItemArmor.a.b, 6);
      var0.put(ItemArmor.a.a, 2);
   }), 9, SoundEffects.aj, 0.0F, 0.0F, () -> RecipeItemStack.a(Items.nz)),
   g("netherite", 37, SystemUtils.a(new EnumMap<>(ItemArmor.a.class), var0 -> {
      var0.put(ItemArmor.a.d, 3);
      var0.put(ItemArmor.a.c, 6);
      var0.put(ItemArmor.a.b, 8);
      var0.put(ItemArmor.a.a, 3);
   }), 15, SoundEffects.ai, 3.0F, 0.1F, () -> RecipeItemStack.a(Items.nR));

   public static final INamable.a<EnumArmorMaterial> h = INamable.a(EnumArmorMaterial::values);
   private static final EnumMap<ItemArmor.a, Integer> i = SystemUtils.a(new EnumMap<>(ItemArmor.a.class), var0 -> {
      var0.put(ItemArmor.a.d, 13);
      var0.put(ItemArmor.a.c, 15);
      var0.put(ItemArmor.a.b, 16);
      var0.put(ItemArmor.a.a, 11);
   });
   private final String j;
   private final int k;
   private final EnumMap<ItemArmor.a, Integer> l;
   private final int m;
   private final SoundEffect n;
   private final float o;
   private final float p;
   private final LazyInitVar<RecipeItemStack> q;

   private EnumArmorMaterial(String var2, int var3, EnumMap var4, int var5, SoundEffect var6, float var7, float var8, Supplier var9) {
      this.j = var2;
      this.k = var3;
      this.l = var4;
      this.m = var5;
      this.n = var6;
      this.o = var7;
      this.p = var8;
      this.q = new LazyInitVar<>(var9);
   }

   @Override
   public int a(ItemArmor.a var0) {
      return i.get(var0) * this.k;
   }

   @Override
   public int b(ItemArmor.a var0) {
      return this.l.get(var0);
   }

   @Override
   public int a() {
      return this.m;
   }

   @Override
   public SoundEffect b() {
      return this.n;
   }

   @Override
   public RecipeItemStack d() {
      return this.q.a();
   }

   @Override
   public String e() {
      return this.j;
   }

   @Override
   public float f() {
      return this.o;
   }

   @Override
   public float g() {
      return this.p;
   }

   @Override
   public String c() {
      return this.j;
   }
}
