package net.minecraft.world.item.context;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;

public class ItemActionContext {
   @Nullable
   private final EntityHuman a;
   private final EnumHand b;
   private final MovingObjectPositionBlock c;
   private final World d;
   private final ItemStack e;

   public ItemActionContext(EntityHuman var0, EnumHand var1, MovingObjectPositionBlock var2) {
      this(var0.H, var0, var1, var0.b(var1), var2);
   }

   public ItemActionContext(World var0, @Nullable EntityHuman var1, EnumHand var2, ItemStack var3, MovingObjectPositionBlock var4) {
      this.a = var1;
      this.b = var2;
      this.c = var4;
      this.e = var3;
      this.d = var0;
   }

   protected final MovingObjectPositionBlock j() {
      return this.c;
   }

   public BlockPosition a() {
      return this.c.a();
   }

   public EnumDirection k() {
      return this.c.b();
   }

   public Vec3D l() {
      return this.c.e();
   }

   public boolean m() {
      return this.c.d();
   }

   public ItemStack n() {
      return this.e;
   }

   @Nullable
   public EntityHuman o() {
      return this.a;
   }

   public EnumHand p() {
      return this.b;
   }

   public World q() {
      return this.d;
   }

   public EnumDirection g() {
      return this.a == null ? EnumDirection.c : this.a.cA();
   }

   public boolean h() {
      return this.a != null && this.a.fz();
   }

   public float i() {
      return this.a == null ? 0.0F : this.a.dw();
   }
}
