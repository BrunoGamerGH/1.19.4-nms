package net.minecraft.world.level.block.entity;

import com.mojang.logging.LogUtils;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.EndFeatures;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.projectile.EntityEnderPearl;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenEndGatewayConfiguration;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.slf4j.Logger;

public class TileEntityEndGateway extends TileEntityEnderPortal {
   private static final Logger a = LogUtils.getLogger();
   private static final int b = 200;
   private static final int c = 40;
   private static final int d = 2400;
   private static final int e = 1;
   private static final int f = 10;
   public long g;
   private int h;
   @Nullable
   public BlockPosition i;
   public boolean j;

   public TileEntityEndGateway(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.v, blockposition, iblockdata);
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Age", this.g);
      if (this.i != null) {
         nbttagcompound.a("ExitPortal", GameProfileSerializer.a(this.i));
      }

      if (this.j) {
         nbttagcompound.a("ExactTeleport", true);
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.g = nbttagcompound.i("Age");
      if (nbttagcompound.b("ExitPortal", 10)) {
         BlockPosition blockposition = GameProfileSerializer.b(nbttagcompound.p("ExitPortal"));
         if (World.k(blockposition)) {
            this.i = blockposition;
         }
      }

      this.j = nbttagcompound.q("ExactTeleport");
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityEndGateway tileentityendgateway) {
      ++tileentityendgateway.g;
      if (tileentityendgateway.d()) {
         --tileentityendgateway.h;
      }
   }

   public static void b(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityEndGateway tileentityendgateway) {
      boolean flag = tileentityendgateway.c();
      boolean flag1 = tileentityendgateway.d();
      ++tileentityendgateway.g;
      if (flag1) {
         --tileentityendgateway.h;
      } else {
         List<Entity> list = world.a(Entity.class, new AxisAlignedBB(blockposition), TileEntityEndGateway::a);
         if (!list.isEmpty()) {
            a(world, blockposition, iblockdata, list.get(world.z.a(list.size())), tileentityendgateway);
         }

         if (tileentityendgateway.g % 2400L == 0L) {
            c(world, blockposition, iblockdata, tileentityendgateway);
         }
      }

      if (flag != tileentityendgateway.c() || flag1 != tileentityendgateway.d()) {
         a(world, blockposition, iblockdata);
      }
   }

   public static boolean a(Entity entity) {
      return IEntitySelector.f.test(entity) && !entity.cS().ar();
   }

   public boolean c() {
      return this.g < 200L;
   }

   public boolean d() {
      return this.h > 0;
   }

   public float a(float f) {
      return MathHelper.a(((float)this.g + f) / 200.0F, 0.0F, 1.0F);
   }

   public float b(float f) {
      return 1.0F - MathHelper.a(((float)this.h - f) / 40.0F, 0.0F, 1.0F);
   }

   public PacketPlayOutTileEntityData f() {
      return PacketPlayOutTileEntityData.a(this);
   }

   @Override
   public NBTTagCompound aq_() {
      return this.o();
   }

   private static void c(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityEndGateway tileentityendgateway) {
      if (!world.B) {
         tileentityendgateway.h = 40;
         world.a(blockposition, iblockdata.b(), 1, 0);
         a(world, blockposition, iblockdata);
      }
   }

   @Override
   public boolean a_(int i, int j) {
      if (i == 1) {
         this.h = 40;
         return true;
      } else {
         return super.a_(i, j);
      }
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity, TileEntityEndGateway tileentityendgateway) {
      if (world instanceof WorldServer worldserver && !tileentityendgateway.d()) {
         tileentityendgateway.h = 100;
         if (tileentityendgateway.i == null && world.getTypeKey() == WorldDimension.d) {
            BlockPosition blockposition1 = a(worldserver, blockposition);
            blockposition1 = blockposition1.b(10);
            a.debug("Creating portal at {}", blockposition1);
            a(worldserver, blockposition1, WorldGenEndGatewayConfiguration.a(blockposition, false));
            tileentityendgateway.i = blockposition1;
         }

         if (tileentityendgateway.i != null) {
            BlockPosition blockposition1 = tileentityendgateway.j ? tileentityendgateway.i : a(world, tileentityendgateway.i);
            Entity entity1;
            if (entity instanceof EntityEnderPearl) {
               Entity entity2 = ((EntityEnderPearl)entity).v();
               if (entity2 instanceof EntityPlayer) {
                  CriterionTriggers.d.a((EntityPlayer)entity2, iblockdata);
               }

               if (entity2 != null) {
                  entity1 = entity2;
                  entity.ai();
               } else {
                  entity1 = entity;
               }
            } else {
               entity1 = entity.cS();
            }

            if (entity1 instanceof EntityPlayer) {
               CraftPlayer player = (CraftPlayer)entity1.getBukkitEntity();
               Location location = new Location(
                  world.getWorld(), (double)blockposition1.u() + 0.5, (double)blockposition1.v() + 0.5, (double)blockposition1.w() + 0.5
               );
               location.setPitch(player.getLocation().getPitch());
               location.setYaw(player.getLocation().getYaw());
               PlayerTeleportEvent teleEvent = new PlayerTeleportEvent(player, player.getLocation(), location, TeleportCause.END_GATEWAY);
               Bukkit.getPluginManager().callEvent(teleEvent);
               if (teleEvent.isCancelled()) {
                  return;
               }

               entity1.aq();
               ((EntityPlayer)entity1).b.teleport(teleEvent.getTo());
               c(world, blockposition, iblockdata, tileentityendgateway);
               return;
            }

            entity1.aq();
            entity1.n((double)blockposition1.u() + 0.5, (double)blockposition1.v(), (double)blockposition1.w() + 0.5);
         }

         c(world, blockposition, iblockdata, tileentityendgateway);
      }
   }

   private static BlockPosition a(World world, BlockPosition blockposition) {
      BlockPosition blockposition1 = a(world, blockposition.b(0, 2, 0), 5, false);
      a.debug("Best exit position for portal at {} is {}", blockposition, blockposition1);
      return blockposition1.c();
   }

   private static BlockPosition a(WorldServer worldserver, BlockPosition blockposition) {
      Vec3D vec3d = b(worldserver, blockposition);
      Chunk chunk = a((World)worldserver, vec3d);
      BlockPosition blockposition1 = a(chunk);
      if (blockposition1 == null) {
         BlockPosition blockposition2 = BlockPosition.a(vec3d.c + 0.5, 75.0, vec3d.e + 0.5);
         a.debug("Failed to find a suitable block to teleport to, spawning an island on {}", blockposition2);
         worldserver.u_()
            .c(Registries.aq)
            .flatMap(iregistry -> iregistry.b(EndFeatures.e))
            .ifPresent(holder_c -> holder_c.a().a(worldserver, worldserver.k().g(), RandomSource.a(blockposition2.a()), blockposition2));
         blockposition1 = blockposition2;
      } else {
         a.debug("Found suitable block to teleport to: {}", blockposition1);
      }

      return a(worldserver, blockposition1, 16, true);
   }

   private static Vec3D b(WorldServer worldserver, BlockPosition blockposition) {
      Vec3D vec3d = new Vec3D((double)blockposition.u(), 0.0, (double)blockposition.w()).d();
      boolean flag = true;
      Vec3D vec3d1 = vec3d.a(1024.0);

      for(int i = 16; !a(worldserver, vec3d1) && i-- > 0; vec3d1 = vec3d1.e(vec3d.a(-16.0))) {
         a.debug("Skipping backwards past nonempty chunk at {}", vec3d1);
      }

      for(int var6 = 16; a(worldserver, vec3d1) && var6-- > 0; vec3d1 = vec3d1.e(vec3d.a(16.0))) {
         a.debug("Skipping forward past empty chunk at {}", vec3d1);
      }

      a.debug("Found chunk at {}", vec3d1);
      return vec3d1;
   }

   private static boolean a(WorldServer worldserver, Vec3D vec3d) {
      return a((World)worldserver, vec3d).b() <= worldserver.v_();
   }

   private static BlockPosition a(IBlockAccess iblockaccess, BlockPosition blockposition, int i, boolean flag) {
      BlockPosition blockposition1 = null;

      for(int j = -i; j <= i; ++j) {
         for(int k = -i; k <= i; ++k) {
            if (j != 0 || k != 0 || flag) {
               for(int l = iblockaccess.ai() - 1; l > (blockposition1 == null ? iblockaccess.v_() : blockposition1.v()); --l) {
                  BlockPosition blockposition2 = new BlockPosition(blockposition.u() + j, l, blockposition.w() + k);
                  IBlockData iblockdata = iblockaccess.a_(blockposition2);
                  if (iblockdata.r(iblockaccess, blockposition2) && (flag || !iblockdata.a(Blocks.F))) {
                     blockposition1 = blockposition2;
                     break;
                  }
               }
            }
         }
      }

      return blockposition1 == null ? blockposition : blockposition1;
   }

   private static Chunk a(World world, Vec3D vec3d) {
      return world.d(MathHelper.a(vec3d.c / 16.0), MathHelper.a(vec3d.e / 16.0));
   }

   @Nullable
   private static BlockPosition a(Chunk chunk) {
      ChunkCoordIntPair chunkcoordintpair = chunk.f();
      BlockPosition blockposition = new BlockPosition(chunkcoordintpair.d(), 30, chunkcoordintpair.e());
      int i = chunk.b() + 16 - 1;
      BlockPosition blockposition1 = new BlockPosition(chunkcoordintpair.f(), i, chunkcoordintpair.g());
      BlockPosition blockposition2 = null;
      double d0 = 0.0;

      for(BlockPosition blockposition3 : BlockPosition.a(blockposition, blockposition1)) {
         IBlockData iblockdata = chunk.a_(blockposition3);
         BlockPosition blockposition4 = blockposition3.c();
         BlockPosition blockposition5 = blockposition3.b(2);
         if (iblockdata.a(Blocks.fy) && !chunk.a_(blockposition4).r(chunk, blockposition4) && !chunk.a_(blockposition5).r(chunk, blockposition5)) {
            double d1 = blockposition3.c(0.0, 0.0, 0.0);
            if (blockposition2 == null || d1 < d0) {
               blockposition2 = blockposition3;
               d0 = d1;
            }
         }
      }

      return blockposition2;
   }

   private static void a(WorldServer worldserver, BlockPosition blockposition, WorldGenEndGatewayConfiguration worldgenendgatewayconfiguration) {
      WorldGenerator.M.a(worldgenendgatewayconfiguration, worldserver, worldserver.k().g(), RandomSource.a(), blockposition);
   }

   @Override
   public boolean a(EnumDirection enumdirection) {
      return Block.a(this.q(), this.o, this.p(), enumdirection, this.p().a(enumdirection));
   }

   public int g() {
      int i = 0;

      for(EnumDirection enumdirection : EnumDirection.values()) {
         i += this.a(enumdirection) ? 1 : 0;
      }

      return i;
   }

   public void a(BlockPosition blockposition, boolean flag) {
      this.j = flag;
      this.i = blockposition;
   }
}
