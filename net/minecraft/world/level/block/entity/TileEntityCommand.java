package net.minecraft.world.level.block.entity;

import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.CommandBlockListenerAbstract;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockCommand;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R3.command.CraftBlockCommandSender;

public class TileEntityCommand extends TileEntity {
   private boolean a;
   private boolean b;
   private boolean c;
   private final CommandBlockListenerAbstract d = new CommandBlockListenerAbstract() {
      @Override
      public CommandSender getBukkitSender(CommandListenerWrapper wrapper) {
         return new CraftBlockCommandSender(wrapper, TileEntityCommand.this);
      }

      @Override
      public void a(String s) {
         super.a(s);
         TileEntityCommand.this.e();
      }

      @Override
      public WorldServer e() {
         return (WorldServer)TileEntityCommand.this.o;
      }

      @Override
      public void f() {
         IBlockData iblockdata = TileEntityCommand.this.o.a_(TileEntityCommand.this.p);
         this.e().a(TileEntityCommand.this.p, iblockdata, iblockdata, 3);
      }

      @Override
      public Vec3D g() {
         return Vec3D.b(TileEntityCommand.this.p);
      }

      @Override
      public CommandListenerWrapper i() {
         EnumDirection enumdirection = TileEntityCommand.this.q().c(BlockCommand.a);
         return new CommandListenerWrapper(
            this, Vec3D.b(TileEntityCommand.this.p), new Vec2F(0.0F, enumdirection.p()), this.e(), 2, this.m().getString(), this.m(), this.e().n(), null
         );
      }
   };

   public TileEntityCommand(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.w, blockposition, iblockdata);
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      this.d.a(nbttagcompound);
      nbttagcompound.a("powered", this.d());
      nbttagcompound.a("conditionMet", this.i());
      nbttagcompound.a("auto", this.f());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.d.b(nbttagcompound);
      this.a = nbttagcompound.q("powered");
      this.c = nbttagcompound.q("conditionMet");
      this.b(nbttagcompound.q("auto"));
   }

   @Override
   public boolean t() {
      return true;
   }

   public CommandBlockListenerAbstract c() {
      return this.d;
   }

   public void a(boolean flag) {
      this.a = flag;
   }

   public boolean d() {
      return this.a;
   }

   public boolean f() {
      return this.b;
   }

   public void b(boolean flag) {
      boolean flag1 = this.b;
      this.b = flag;
      if (!flag1 && flag && !this.a && this.o != null && this.v() != TileEntityCommand.Type.a) {
         this.x();
      }
   }

   public void g() {
      TileEntityCommand.Type tileentitycommand_type = this.v();
      if (tileentitycommand_type == TileEntityCommand.Type.b && (this.a || this.b) && this.o != null) {
         this.x();
      }
   }

   private void x() {
      Block block = this.q().b();
      if (block instanceof BlockCommand) {
         this.j();
         this.o.a(this.p, block, 1);
      }
   }

   public boolean i() {
      return this.c;
   }

   public boolean j() {
      this.c = true;
      if (this.w()) {
         BlockPosition blockposition = this.p.a(this.o.a_(this.p).c(BlockCommand.a).g());
         if (this.o.a_(blockposition).b() instanceof BlockCommand) {
            TileEntity tileentity = this.o.c_(blockposition);
            this.c = tileentity instanceof TileEntityCommand && ((TileEntityCommand)tileentity).c().j() > 0;
         } else {
            this.c = false;
         }
      }

      return this.c;
   }

   public TileEntityCommand.Type v() {
      IBlockData iblockdata = this.q();
      return iblockdata.a(Blocks.fM)
         ? TileEntityCommand.Type.c
         : (iblockdata.a(Blocks.kD) ? TileEntityCommand.Type.b : (iblockdata.a(Blocks.kE) ? TileEntityCommand.Type.a : TileEntityCommand.Type.c));
   }

   public boolean w() {
      IBlockData iblockdata = this.o.a_(this.p());
      return iblockdata.b() instanceof BlockCommand ? iblockdata.c(BlockCommand.b) : false;
   }

   public static enum Type {
      a,
      b,
      c;
   }
}
