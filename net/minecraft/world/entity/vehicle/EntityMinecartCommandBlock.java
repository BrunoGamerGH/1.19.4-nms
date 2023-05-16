package net.minecraft.world.entity.vehicle;

import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.CommandBlockListenerAbstract;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftMinecartCommand;

public class EntityMinecartCommandBlock extends EntityMinecartAbstract {
   public static final DataWatcherObject<String> c = DataWatcher.a(EntityMinecartCommandBlock.class, DataWatcherRegistry.e);
   static final DataWatcherObject<IChatBaseComponent> d = DataWatcher.a(EntityMinecartCommandBlock.class, DataWatcherRegistry.f);
   private final CommandBlockListenerAbstract e = new EntityMinecartCommandBlock.a();
   private static final int f = 4;
   private int g;

   public EntityMinecartCommandBlock(EntityTypes<? extends EntityMinecartCommandBlock> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityMinecartCommandBlock(World world, double d0, double d1, double d2) {
      super(EntityTypes.s, world, d0, d1, d2);
   }

   @Override
   protected Item i() {
      return Items.mW;
   }

   @Override
   protected void a_() {
      super.a_();
      this.aj().a(c, "");
      this.aj().a(d, CommonComponents.a);
   }

   @Override
   protected void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.e.b(nbttagcompound);
      this.aj().b(c, this.z().l());
      this.aj().b(d, this.z().k());
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      this.e.a(nbttagcompound);
   }

   @Override
   public EntityMinecartAbstract.EnumMinecartType s() {
      return EntityMinecartAbstract.EnumMinecartType.g;
   }

   @Override
   public IBlockData v() {
      return Blocks.fM.o();
   }

   public CommandBlockListenerAbstract z() {
      return this.e;
   }

   @Override
   public void a(int i, int j, int k, boolean flag) {
      if (flag && this.ag - this.g >= 4) {
         this.z().a(this.H);
         this.g = this.ag;
      }
   }

   @Override
   public EnumInteractionResult a(EntityHuman entityhuman, EnumHand enumhand) {
      return this.e.a(entityhuman);
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      super.a(datawatcherobject);
      if (d.equals(datawatcherobject)) {
         try {
            this.e.c(this.aj().a(d));
         } catch (Throwable var3) {
         }
      } else if (c.equals(datawatcherobject)) {
         this.e.a(this.aj().a(c));
      }
   }

   @Override
   public boolean cJ() {
      return true;
   }

   public class a extends CommandBlockListenerAbstract {
      @Override
      public WorldServer e() {
         return (WorldServer)EntityMinecartCommandBlock.this.H;
      }

      @Override
      public void f() {
         EntityMinecartCommandBlock.this.aj().b(EntityMinecartCommandBlock.c, this.l());
         EntityMinecartCommandBlock.this.aj().b(EntityMinecartCommandBlock.d, this.k());
      }

      @Override
      public Vec3D g() {
         return EntityMinecartCommandBlock.this.de();
      }

      public EntityMinecartCommandBlock h() {
         return EntityMinecartCommandBlock.this;
      }

      @Override
      public CommandListenerWrapper i() {
         return new CommandListenerWrapper(
            this,
            EntityMinecartCommandBlock.this.de(),
            EntityMinecartCommandBlock.this.bD(),
            this.e(),
            2,
            this.m().getString(),
            EntityMinecartCommandBlock.this.G_(),
            this.e().n(),
            EntityMinecartCommandBlock.this
         );
      }

      @Override
      public CommandSender getBukkitSender(CommandListenerWrapper wrapper) {
         return (CraftMinecartCommand)EntityMinecartCommandBlock.this.getBukkitEntity();
      }
   }
}
