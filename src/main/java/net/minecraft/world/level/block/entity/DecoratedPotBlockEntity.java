package net.minecraft.world.level.block.entity;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsItem;
import net.minecraft.world.InventoryUtils;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;

public class DecoratedPotBlockEntity extends TileEntity {
   private static final String a = "shards";
   private static final int b = 4;
   private boolean c = false;
   private final List<Item> d = SystemUtils.a(new ArrayList<>(4), var0x -> {
      var0x.add(Items.pT);
      var0x.add(Items.pT);
      var0x.add(Items.pT);
      var0x.add(Items.pT);
   });

   public DecoratedPotBlockEntity(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.N, var0, var1);
   }

   @Override
   protected void b(NBTTagCompound var0) {
      super.b(var0);
      a(this.d, var0);
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      if (var0.b("shards", 9)) {
         NBTTagList var1 = var0.c("shards", 8);
         this.d.clear();
         int var2 = Math.min(4, var1.size());

         for(int var3 = 0; var3 < var2; ++var3) {
            NBTBase var6 = var1.k(var3);
            if (var6 instanceof NBTTagString var4) {
               this.d.add(BuiltInRegistries.i.a(new MinecraftKey(var4.f_())));
            } else {
               this.d.add(Items.pT);
            }
         }

         int var3 = 4 - var2;

         for(int var4 = 0; var4 < var3; ++var4) {
            this.d.add(Items.pT);
         }
      }
   }

   public PacketPlayOutTileEntityData c() {
      return PacketPlayOutTileEntityData.a(this);
   }

   @Override
   public NBTTagCompound aq_() {
      return this.o();
   }

   public static void a(List<Item> var0, NBTTagCompound var1) {
      NBTTagList var2 = new NBTTagList();

      for(Item var4 : var0) {
         var2.add(NBTTagString.a(BuiltInRegistries.i.b(var4).toString()));
      }

      var1.a("shards", var2);
   }

   public ItemStack d() {
      ItemStack var0 = new ItemStack(Blocks.sj);
      NBTTagCompound var1 = new NBTTagCompound();
      a(this.d, var1);
      ItemBlock.a(var0, TileEntityTypes.N, var1);
      return var0;
   }

   public List<Item> f() {
      return this.d;
   }

   public void a(World var0, BlockPosition var1, ItemStack var2, EntityHuman var3) {
      if (var3.f()) {
         this.c = true;
      } else {
         if (var2.a(TagsItem.aR) && !EnchantmentManager.f(var2)) {
            List<Item> var4 = this.f();
            NonNullList<ItemStack> var5 = NonNullList.a(var4.size());
            var5.addAll(0, var4.stream().map(Item::ad_).toList());
            InventoryUtils.a(var0, var1, var5);
            this.c = true;
            var0.a(null, var1, SoundEffects.fs, SoundCategory.h, 1.0F, 1.0F);
         }
      }
   }

   public boolean g() {
      return this.c;
   }

   public EnumDirection i() {
      return this.q().c(BlockProperties.R);
   }

   public void a(ItemStack var0) {
      NBTTagCompound var1 = ItemBlock.a(var0);
      if (var1 != null) {
         this.a(var1);
      }
   }
}
