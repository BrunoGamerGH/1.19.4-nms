package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.tags.TagsItem;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockDispenser;

public class ItemShield extends Item implements Equipable {
   public static final int a = 5;
   public static final float b = 3.0F;
   public static final String c = "Base";

   public ItemShield(Item.Info var0) {
      super(var0);
      BlockDispenser.a(this, ItemArmor.a);
   }

   @Override
   public String j(ItemStack var0) {
      return ItemBlock.a(var0) != null ? this.a() + "." + d(var0).b() : super.j(var0);
   }

   @Override
   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      ItemBanner.a(var0, var2);
   }

   @Override
   public EnumAnimation c(ItemStack var0) {
      return EnumAnimation.d;
   }

   @Override
   public int b(ItemStack var0) {
      return 72000;
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      ItemStack var3 = var1.b(var2);
      var1.c(var2);
      return InteractionResultWrapper.b(var3);
   }

   @Override
   public boolean a(ItemStack var0, ItemStack var1) {
      return var1.a(TagsItem.b) || super.a(var0, var1);
   }

   public static EnumColor d(ItemStack var0) {
      NBTTagCompound var1 = ItemBlock.a(var0);
      return var1 != null ? EnumColor.a(var1.h("Base")) : EnumColor.a;
   }

   @Override
   public EnumItemSlot g() {
      return EnumItemSlot.b;
   }
}
