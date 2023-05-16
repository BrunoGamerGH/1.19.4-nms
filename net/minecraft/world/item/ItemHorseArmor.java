package net.minecraft.world.item;

import net.minecraft.resources.MinecraftKey;

public class ItemHorseArmor extends Item {
   private static final String a = "textures/entity/horse/";
   private final int b;
   private final String c;

   public ItemHorseArmor(int var0, String var1, Item.Info var2) {
      super(var2);
      this.b = var0;
      this.c = "textures/entity/horse/armor/horse_armor_" + var1 + ".png";
   }

   public MinecraftKey h() {
      return new MinecraftKey(this.c);
   }

   public int i() {
      return this.b;
   }
}
