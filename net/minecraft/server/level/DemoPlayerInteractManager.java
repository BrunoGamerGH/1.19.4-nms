package net.minecraft.server.level;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayInBlockDig;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class DemoPlayerInteractManager extends PlayerInteractManager {
   public static final int a = 5;
   public static final int b = 120500;
   private boolean e;
   private boolean f;
   private int g;
   private int h;

   public DemoPlayerInteractManager(EntityPlayer var0) {
      super(var0);
   }

   @Override
   public void a() {
      super.a();
      ++this.h;
      long var0 = this.c.U();
      long var2 = var0 / 24000L + 1L;
      if (!this.e && this.h > 20) {
         this.e = true;
         this.d.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.f, 0.0F));
      }

      this.f = var0 > 120500L;
      if (this.f) {
         ++this.g;
      }

      if (var0 % 24000L == 500L) {
         if (var2 <= 6L) {
            if (var2 == 6L) {
               this.d.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.f, 104.0F));
            } else {
               this.d.a(IChatBaseComponent.c("demo.day." + var2));
            }
         }
      } else if (var2 == 1L) {
         if (var0 == 100L) {
            this.d.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.f, 101.0F));
         } else if (var0 == 175L) {
            this.d.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.f, 102.0F));
         } else if (var0 == 250L) {
            this.d.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.f, 103.0F));
         }
      } else if (var2 == 5L && var0 % 24000L == 22000L) {
         this.d.a(IChatBaseComponent.c("demo.day.warning"));
      }
   }

   private void f() {
      if (this.g > 100) {
         this.d.a(IChatBaseComponent.c("demo.reminder"));
         this.g = 0;
      }
   }

   @Override
   public void a(BlockPosition var0, PacketPlayInBlockDig.EnumPlayerDigType var1, EnumDirection var2, int var3, int var4) {
      if (this.f) {
         this.f();
      } else {
         super.a(var0, var1, var2, var3, var4);
      }
   }

   @Override
   public EnumInteractionResult a(EntityPlayer var0, World var1, ItemStack var2, EnumHand var3) {
      if (this.f) {
         this.f();
         return EnumInteractionResult.d;
      } else {
         return super.a(var0, var1, var2, var3);
      }
   }

   @Override
   public EnumInteractionResult a(EntityPlayer var0, World var1, ItemStack var2, EnumHand var3, MovingObjectPositionBlock var4) {
      if (this.f) {
         this.f();
         return EnumInteractionResult.d;
      } else {
         return super.a(var0, var1, var2, var3, var4);
      }
   }
}
