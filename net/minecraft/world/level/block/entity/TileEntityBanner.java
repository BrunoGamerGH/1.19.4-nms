package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.world.INamableTileEntity;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BlockBanner;
import net.minecraft.world.level.block.BlockBannerAbstract;
import net.minecraft.world.level.block.state.IBlockData;

public class TileEntityBanner extends TileEntity implements INamableTileEntity {
   public static final int a = 6;
   public static final String b = "Patterns";
   public static final String c = "Pattern";
   public static final String d = "Color";
   @Nullable
   private IChatBaseComponent e;
   public EnumColor f;
   @Nullable
   public NBTTagList g;
   @Nullable
   private List<Pair<Holder<EnumBannerPatternType>, EnumColor>> h;

   public TileEntityBanner(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.t, blockposition, iblockdata);
      this.f = ((BlockBannerAbstract)iblockdata.b()).b();
   }

   public TileEntityBanner(BlockPosition blockposition, IBlockData iblockdata, EnumColor enumcolor) {
      this(blockposition, iblockdata);
      this.f = enumcolor;
   }

   @Nullable
   public static NBTTagList a(ItemStack itemstack) {
      NBTTagList nbttaglist = null;
      NBTTagCompound nbttagcompound = ItemBlock.a(itemstack);
      if (nbttagcompound != null && nbttagcompound.b("Patterns", 9)) {
         nbttaglist = nbttagcompound.c("Patterns", 10).e();
      }

      return nbttaglist;
   }

   public void a(ItemStack itemstack, EnumColor enumcolor) {
      this.f = enumcolor;
      this.b(itemstack);
   }

   public void b(ItemStack itemstack) {
      this.g = a(itemstack);
      this.h = null;
      this.e = itemstack.z() ? itemstack.x() : null;
   }

   @Override
   public IChatBaseComponent Z() {
      return (IChatBaseComponent)(this.e != null ? this.e : IChatBaseComponent.c("block.minecraft.banner"));
   }

   @Nullable
   @Override
   public IChatBaseComponent ab() {
      return this.e;
   }

   public void a(IChatBaseComponent ichatbasecomponent) {
      this.e = ichatbasecomponent;
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (this.g != null) {
         nbttagcompound.a("Patterns", this.g);
      }

      if (this.e != null) {
         nbttagcompound.a("CustomName", IChatBaseComponent.ChatSerializer.a(this.e));
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("CustomName", 8)) {
         this.e = IChatBaseComponent.ChatSerializer.a(nbttagcompound.l("CustomName"));
      }

      this.g = nbttagcompound.c("Patterns", 10);

      while(this.g.size() > 20) {
         this.g.c(20);
      }

      this.h = null;
   }

   public PacketPlayOutTileEntityData a() {
      return PacketPlayOutTileEntityData.a(this);
   }

   @Override
   public NBTTagCompound aq_() {
      return this.o();
   }

   public static int c(ItemStack itemstack) {
      NBTTagCompound nbttagcompound = ItemBlock.a(itemstack);
      return nbttagcompound != null && nbttagcompound.e("Patterns") ? nbttagcompound.c("Patterns", 10).size() : 0;
   }

   public List<Pair<Holder<EnumBannerPatternType>, EnumColor>> c() {
      if (this.h == null) {
         this.h = a(this.f, this.g);
      }

      return this.h;
   }

   public static List<Pair<Holder<EnumBannerPatternType>, EnumColor>> a(EnumColor enumcolor, @Nullable NBTTagList nbttaglist) {
      List<Pair<Holder<EnumBannerPatternType>, EnumColor>> list = Lists.newArrayList();
      list.add(Pair.of(BuiltInRegistries.ak.f(BannerPatterns.a), enumcolor));
      if (nbttaglist != null) {
         for(int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.a(i);
            Holder<EnumBannerPatternType> holder = EnumBannerPatternType.a(nbttagcompound.l("Pattern"));
            if (holder != null) {
               int j = nbttagcompound.h("Color");
               list.add(Pair.of(holder, EnumColor.a(j)));
            }
         }
      }

      return list;
   }

   public static void d(ItemStack itemstack) {
      NBTTagCompound nbttagcompound = ItemBlock.a(itemstack);
      if (nbttagcompound != null && nbttagcompound.b("Patterns", 9)) {
         NBTTagList nbttaglist = nbttagcompound.c("Patterns", 10);
         if (!nbttaglist.isEmpty()) {
            nbttaglist.c(nbttaglist.size() - 1);
            if (nbttaglist.isEmpty()) {
               nbttagcompound.r("Patterns");
            }

            ItemBlock.a(itemstack, TileEntityTypes.t, nbttagcompound);
         }
      }
   }

   public ItemStack f() {
      ItemStack itemstack = new ItemStack(BlockBanner.a(this.f));
      if (this.g != null && !this.g.isEmpty()) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.a("Patterns", this.g.e());
         ItemBlock.a(itemstack, this.u(), nbttagcompound);
      }

      if (this.e != null) {
         itemstack.a(this.e);
      }

      return itemstack;
   }

   public EnumColor g() {
      return this.f;
   }
}
