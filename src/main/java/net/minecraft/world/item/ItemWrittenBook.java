package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.UtilColor;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockLectern;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class ItemWrittenBook extends Item {
   public static final int a = 16;
   public static final int b = 32;
   public static final int c = 1024;
   public static final int d = 32767;
   public static final int e = 100;
   public static final int f = 2;
   public static final String g = "title";
   public static final String h = "filtered_title";
   public static final String i = "author";
   public static final String j = "pages";
   public static final String k = "filtered_pages";
   public static final String r = "generation";
   public static final String s = "resolved";

   public ItemWrittenBook(Item.Info var0) {
      super(var0);
   }

   public static boolean a(@Nullable NBTTagCompound var0) {
      if (!ItemBookAndQuill.a(var0)) {
         return false;
      } else if (!var0.b("title", 8)) {
         return false;
      } else {
         String var1 = var0.l("title");
         return var1.length() > 32 ? false : var0.b("author", 8);
      }
   }

   public static int d(ItemStack var0) {
      return var0.u().h("generation");
   }

   public static int k(ItemStack var0) {
      NBTTagCompound var1 = var0.u();
      return var1 != null ? var1.c("pages", 8).size() : 0;
   }

   @Override
   public IChatBaseComponent m(ItemStack var0) {
      NBTTagCompound var1 = var0.u();
      if (var1 != null) {
         String var2 = var1.l("title");
         if (!UtilColor.b(var2)) {
            return IChatBaseComponent.b(var2);
         }
      }

      return super.m(var0);
   }

   @Override
   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      if (var0.t()) {
         NBTTagCompound var4 = var0.u();
         String var5 = var4.l("author");
         if (!UtilColor.b(var5)) {
            var2.add(IChatBaseComponent.a("book.byAuthor", var5).a(EnumChatFormat.h));
         }

         var2.add(IChatBaseComponent.c("book.generation." + var4.h("generation")).a(EnumChatFormat.h));
      }
   }

   @Override
   public EnumInteractionResult a(ItemActionContext var0) {
      World var1 = var0.q();
      BlockPosition var2 = var0.a();
      IBlockData var3 = var1.a_(var2);
      if (var3.a(Blocks.nW)) {
         return BlockLectern.a(var0.o(), var1, var2, var3, var0.n()) ? EnumInteractionResult.a(var1.B) : EnumInteractionResult.d;
      } else {
         return EnumInteractionResult.d;
      }
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      ItemStack var3 = var1.b(var2);
      var1.a(var3, var2);
      var1.b(StatisticList.c.b(this));
      return InteractionResultWrapper.a(var3, var0.k_());
   }

   public static boolean a(ItemStack var0, @Nullable CommandListenerWrapper var1, @Nullable EntityHuman var2) {
      NBTTagCompound var3 = var0.u();
      if (var3 != null && !var3.q("resolved")) {
         var3.a("resolved", true);
         if (!a(var3)) {
            return false;
         } else {
            NBTTagList var4 = var3.c("pages", 8);
            NBTTagList var5 = new NBTTagList();

            for(int var6 = 0; var6 < var4.size(); ++var6) {
               String var7 = a(var1, var2, var4.j(var6));
               if (var7.length() > 32767) {
                  return false;
               }

               var5.c(var6, NBTTagString.a(var7));
            }

            if (var3.b("filtered_pages", 10)) {
               NBTTagCompound var6 = var3.p("filtered_pages");
               NBTTagCompound var7 = new NBTTagCompound();

               for(String var9 : var6.e()) {
                  String var10 = a(var1, var2, var6.l(var9));
                  if (var10.length() > 32767) {
                     return false;
                  }

                  var7.a(var9, var10);
               }

               var3.a("filtered_pages", var7);
            }

            var3.a("pages", var5);
            return true;
         }
      } else {
         return false;
      }
   }

   private static String a(@Nullable CommandListenerWrapper var0, @Nullable EntityHuman var1, String var2) {
      IChatBaseComponent var3;
      try {
         var3 = IChatBaseComponent.ChatSerializer.b(var2);
         var3 = ChatComponentUtils.a(var0, var3, var1, 0);
      } catch (Exception var5) {
         var3 = IChatBaseComponent.b(var2);
      }

      return IChatBaseComponent.ChatSerializer.a(var3);
   }

   @Override
   public boolean i(ItemStack var0) {
      return true;
   }
}
