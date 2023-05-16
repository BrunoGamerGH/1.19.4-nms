package net.minecraft.world.level.block.entity;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.Vec3D;
import org.slf4j.Logger;

public class SuspiciousSandBlockEntity extends TileEntity {
   private static final Logger a = LogUtils.getLogger();
   private static final String b = "loot_table";
   private static final String c = "loot_table_seed";
   private static final String d = "hit_direction";
   private static final String e = "item";
   private static final int f = 10;
   private static final int g = 40;
   private static final int h = 10;
   private int i;
   private long j;
   private long k;
   public ItemStack l = ItemStack.b;
   @Nullable
   private EnumDirection m;
   @Nullable
   public MinecraftKey n;
   public long r;

   public SuspiciousSandBlockEntity(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.M, var0, var1);
   }

   public boolean a(long var0, EntityHuman var2, EnumDirection var3) {
      if (this.m == null) {
         this.m = var3;
      }

      this.j = var0 + 40L;
      if (var0 >= this.k && this.o instanceof WorldServer) {
         this.k = var0 + 10L;
         this.a(var2);
         int var4 = this.i();
         if (++this.i >= 10) {
            this.b(var2);
            return true;
         } else {
            this.o.a(this.p(), Blocks.J, 40);
            int var5 = this.i();
            if (var4 != var5) {
               IBlockData var6 = this.q();
               IBlockData var7 = var6.a(BlockProperties.bv, Integer.valueOf(var5));
               this.o.a(this.p(), var7, 3);
            }

            return false;
         }
      } else {
         return false;
      }
   }

   public void a(EntityHuman var0) {
      if (this.n != null && this.o != null && !this.o.k_() && this.o.n() != null) {
         LootTable var1 = this.o.n().aH().a(this.n);
         if (var0 instanceof EntityPlayer var2) {
            CriterionTriggers.N.a(var2, this.n);
         }

         LootTableInfo.Builder var2 = new LootTableInfo.Builder((WorldServer)this.o)
            .a(LootContextParameters.f, Vec3D.b(this.p))
            .a(this.r)
            .a(var0.gf())
            .a(LootContextParameters.a, var0);
         ObjectArrayList<ItemStack> var3 = var1.a(var2.a(LootContextParameterSets.b));

         this.l = switch(var3.size()) {
            case 0 -> ItemStack.b;
            case 1 -> (ItemStack)var3.get(0);
            default -> {
               a.warn("Expected max 1 loot from loot table " + this.n + " got " + var3.size());
               yield (ItemStack)var3.get(0);
            }
         };
         this.n = null;
         this.e();
      }
   }

   private void b(EntityHuman var0) {
      if (this.o != null && this.o.n() != null) {
         this.c(var0);
         this.o.c(3008, this.p(), Block.i(this.q()));
         this.o.a(this.p, Blocks.I.o(), 3);
      }
   }

   private void c(EntityHuman var0) {
      if (this.o != null && this.o.n() != null) {
         this.a(var0);
         if (!this.l.b()) {
            double var1 = (double)EntityTypes.ad.k();
            double var3 = 1.0 - var1;
            double var5 = var1 / 2.0;
            EnumDirection var7 = Objects.requireNonNullElse(this.m, EnumDirection.b);
            BlockPosition var8 = this.p.a(var7, 1);
            double var9 = Math.floor((double)var8.u()) + 0.5 * var3 + var5;
            double var11 = Math.floor((double)var8.v() + 0.5) + (double)(EntityTypes.ad.l() / 2.0F);
            double var13 = Math.floor((double)var8.w()) + 0.5 * var3 + var5;
            EntityItem var15 = new EntityItem(this.o, var9, var11, var13, this.l.a(this.o.z.a(21) + 10));
            var15.f(Vec3D.b);
            this.o.b(var15);
            this.l = ItemStack.b;
         }
      }
   }

   public void c() {
      if (this.o != null) {
         if (this.i != 0 && this.o.U() >= this.j) {
            int var0 = this.i();
            this.i = Math.max(0, this.i - 2);
            int var1 = this.i();
            if (var0 != var1) {
               this.o.a(this.p(), this.q().a(BlockProperties.bv, Integer.valueOf(var1)), 3);
            }

            int var2 = 4;
            this.j = this.o.U() + 4L;
         }

         if (this.i == 0) {
            this.m = null;
            this.j = 0L;
            this.k = 0L;
         } else {
            this.o.a(this.p(), Blocks.J, (int)(this.j - this.o.U()));
         }
      }
   }

   private boolean d(NBTTagCompound var0) {
      if (var0.b("loot_table", 8)) {
         this.n = new MinecraftKey(var0.l("loot_table"));
         this.r = var0.i("loot_table_seed");
         return true;
      } else {
         return false;
      }
   }

   private boolean e(NBTTagCompound var0) {
      if (this.n == null) {
         return false;
      } else {
         var0.a("loot_table", this.n.toString());
         if (this.r != 0L) {
            var0.a("loot_table_seed", this.r);
         }

         return true;
      }
   }

   @Override
   public NBTTagCompound aq_() {
      NBTTagCompound var0 = super.aq_();
      if (this.m != null) {
         var0.a("hit_direction", this.m.ordinal());
      }

      var0.a("item", this.l.b(new NBTTagCompound()));
      return var0;
   }

   public PacketPlayOutTileEntityData d() {
      return PacketPlayOutTileEntityData.a(this);
   }

   @Override
   public void a(NBTTagCompound var0) {
      if (!this.d(var0) && var0.e("item")) {
         this.l = ItemStack.a(var0.p("item"));
      }

      if (var0.e("hit_direction")) {
         this.m = EnumDirection.values()[var0.h("hit_direction")];
      }
   }

   @Override
   protected void b(NBTTagCompound var0) {
      if (!this.e(var0)) {
         var0.a("item", this.l.b(new NBTTagCompound()));
      }
   }

   public void a(MinecraftKey var0, long var1) {
      this.n = var0;
      this.r = var1;
   }

   private int i() {
      if (this.i == 0) {
         return 0;
      } else if (this.i < 3) {
         return 1;
      } else {
         return this.i < 6 ? 2 : 3;
      }
   }

   @Nullable
   public EnumDirection f() {
      return this.m;
   }

   public ItemStack g() {
      return this.l;
   }
}
