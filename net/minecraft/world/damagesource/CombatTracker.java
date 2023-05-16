package net.minecraft.world.damagesource;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.ChatClickable;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class CombatTracker {
   public static final int a = 100;
   public static final int b = 300;
   private static final ChatModifier c = ChatModifier.a
      .a(new ChatClickable(ChatClickable.EnumClickAction.a, "https://bugs.mojang.com/browse/MCPE-28723"))
      .a(new ChatHoverable(ChatHoverable.EnumHoverAction.a, IChatBaseComponent.b("MCPE-28723")));
   private final List<CombatEntry> d = Lists.newArrayList();
   private final EntityLiving e;
   private int f;
   private int g;
   private int h;
   private boolean i;
   private boolean j;
   @Nullable
   private String k;

   public CombatTracker(EntityLiving var0) {
      this.e = var0;
   }

   public void a() {
      this.l();
      Optional<BlockPosition> var0 = this.e.ez();
      if (var0.isPresent()) {
         IBlockData var1 = this.e.H.a_(var0.get());
         if (var1.a(Blocks.cN) || var1.a(TagsBlock.O)) {
            this.k = "ladder";
         } else if (var1.a(Blocks.fe)) {
            this.k = "vines";
         } else if (var1.a(Blocks.ov) || var1.a(Blocks.ow)) {
            this.k = "weeping_vines";
         } else if (var1.a(Blocks.ox) || var1.a(Blocks.oy)) {
            this.k = "twisting_vines";
         } else if (var1.a(Blocks.nO)) {
            this.k = "scaffolding";
         } else {
            this.k = "other_climbable";
         }
      } else if (this.e.aT()) {
         this.k = "water";
      }
   }

   public void a(DamageSource var0, float var1, float var2) {
      this.g();
      this.a();
      CombatEntry var3 = new CombatEntry(var0, this.e.ag, var1, var2, this.k, this.e.aa);
      this.d.add(var3);
      this.f = this.e.ag;
      this.j = true;
      if (var3.f() && !this.i && this.e.bq()) {
         this.i = true;
         this.g = this.e.ag;
         this.h = this.g;
         this.e.E_();
      }
   }

   public IChatBaseComponent b() {
      if (this.d.isEmpty()) {
         return IChatBaseComponent.a("death.attack.generic", this.e.G_());
      } else {
         CombatEntry var0 = this.k();
         CombatEntry var1 = this.d.get(this.d.size() - 1);
         IChatBaseComponent var3 = var1.h();
         DamageSource var4 = var1.a();
         Entity var5 = var4.d();
         DeathMessageType var6 = var4.j().e();
         IChatBaseComponent var2;
         if (var0 != null && var6 == DeathMessageType.b) {
            IChatBaseComponent var7 = var0.h();
            DamageSource var8 = var0.a();
            if (var8.a(DamageTypeTags.m) || var8.a(DamageTypeTags.s)) {
               var2 = IChatBaseComponent.a("death.fell.accident." + this.a(var0), this.e.G_());
            } else if (var7 != null && !var7.equals(var3)) {
               Entity var9 = var8.d();
               ItemStack var10 = var9 instanceof EntityLiving var11 ? var11.eK() : ItemStack.b;
               if (!var10.b() && var10.z()) {
                  var2 = IChatBaseComponent.a("death.fell.assist.item", this.e.G_(), var7, var10.I());
               } else {
                  var2 = IChatBaseComponent.a("death.fell.assist", this.e.G_(), var7);
               }
            } else if (var3 != null) {
               ItemStack var9 = var5 instanceof EntityLiving var10 ? var10.eK() : ItemStack.b;
               if (!var9.b() && var9.z()) {
                  var2 = IChatBaseComponent.a("death.fell.finish.item", this.e.G_(), var3, var9.I());
               } else {
                  var2 = IChatBaseComponent.a("death.fell.finish", this.e.G_(), var3);
               }
            } else {
               var2 = IChatBaseComponent.a("death.fell.killer", this.e.G_());
            }
         } else {
            if (var6 == DeathMessageType.c) {
               String var7 = "death.attack." + var4.e();
               IChatBaseComponent var8 = ChatComponentUtils.a((IChatBaseComponent)IChatBaseComponent.c(var7 + ".link")).c(c);
               return IChatBaseComponent.a(var7 + ".message", this.e.G_(), var8);
            }

            var2 = var4.a(this.e);
         }

         return var2;
      }
   }

   @Nullable
   public EntityLiving c() {
      EntityLiving var0 = null;
      EntityHuman var1 = null;
      float var2 = 0.0F;
      float var3 = 0.0F;

      for(CombatEntry var5 : this.d) {
         Entity var8 = var5.a().d();
         if (var8 instanceof EntityHuman var6 && (var1 == null || var5.c() > var3)) {
            var3 = var5.c();
            var1 = var6;
         }

         var8 = var5.a().d();
         if (var8 instanceof EntityLiving var6 && (var0 == null || var5.c() > var2)) {
            var2 = var5.c();
            var0 = var6;
         }
      }

      return (EntityLiving)(var1 != null && var3 >= var2 / 3.0F ? var1 : var0);
   }

   @Nullable
   private CombatEntry k() {
      CombatEntry var0 = null;
      CombatEntry var1 = null;
      float var2 = 0.0F;
      float var3 = 0.0F;

      for(int var4 = 0; var4 < this.d.size(); ++var4) {
         CombatEntry var5 = this.d.get(var4);
         CombatEntry var6 = var4 > 0 ? this.d.get(var4 - 1) : null;
         DamageSource var7 = var5.a();
         boolean var8 = var7.a(DamageTypeTags.s);
         float var9 = var8 ? Float.MAX_VALUE : var5.j();
         if ((var7.a(DamageTypeTags.m) || var8) && var9 > 0.0F && (var0 == null || var9 > var3)) {
            if (var4 > 0) {
               var0 = var6;
            } else {
               var0 = var5;
            }

            var3 = var9;
         }

         if (var5.g() != null && (var1 == null || var5.c() > var2)) {
            var1 = var5;
            var2 = var5.c();
         }
      }

      if (var3 > 5.0F && var0 != null) {
         return var0;
      } else {
         return var2 > 5.0F && var1 != null ? var1 : null;
      }
   }

   private String a(CombatEntry var0) {
      return var0.g() == null ? "generic" : var0.g();
   }

   public boolean d() {
      this.g();
      return this.j;
   }

   public boolean e() {
      this.g();
      return this.i;
   }

   public int f() {
      return this.i ? this.e.ag - this.g : this.h - this.g;
   }

   private void l() {
      this.k = null;
   }

   public void g() {
      int var0 = this.i ? 300 : 100;
      if (this.j && (!this.e.bq() || this.e.ag - this.f > var0)) {
         boolean var1 = this.i;
         this.j = false;
         this.i = false;
         this.h = this.e.ag;
         if (var1) {
            this.e.j();
         }

         this.d.clear();
      }
   }

   public EntityLiving h() {
      return this.e;
   }

   @Nullable
   public CombatEntry i() {
      return this.d.isEmpty() ? null : this.d.get(this.d.size() - 1);
   }

   public int j() {
      EntityLiving var0 = this.c();
      return var0 == null ? -1 : var0.af();
   }
}
