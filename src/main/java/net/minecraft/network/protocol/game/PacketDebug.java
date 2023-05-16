package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.UtilColor;
import net.minecraft.world.IInventory;
import net.minecraft.world.INamableTileEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BehaviorPositionEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import net.minecraft.world.entity.ai.gossip.ReputationType;
import net.minecraft.world.entity.ai.memory.ExpirableMemory;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.animal.EntityBee;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntityBeehive;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.phys.Vec3D;
import org.slf4j.Logger;

public class PacketDebug {
   private static final Logger a = LogUtils.getLogger();

   public static void a(WorldServer var0, BlockPosition var1, String var2, int var3, int var4) {
      PacketDataSerializer var5 = new PacketDataSerializer(Unpooled.buffer());
      var5.a(var1);
      var5.writeInt(var3);
      var5.a(var2);
      var5.writeInt(var4);
      a(var0, var5, PacketPlayOutCustomPayload.n);
   }

   public static void a(WorldServer var0) {
      PacketDataSerializer var1 = new PacketDataSerializer(Unpooled.buffer());
      a(var0, var1, PacketPlayOutCustomPayload.o);
   }

   public static void a(WorldServer var0, ChunkCoordIntPair var1) {
   }

   public static void a(WorldServer var0, BlockPosition var1) {
      d(var0, var1);
   }

   public static void b(WorldServer var0, BlockPosition var1) {
      d(var0, var1);
   }

   public static void c(WorldServer var0, BlockPosition var1) {
      d(var0, var1);
   }

   private static void d(WorldServer var0, BlockPosition var1) {
   }

   public static void a(World var0, EntityInsentient var1, @Nullable PathEntity var2, float var3) {
   }

   public static void a(World var0, BlockPosition var1) {
   }

   public static void a(GeneratorAccessSeed var0, StructureStart var1) {
   }

   public static void a(World var0, EntityInsentient var1, PathfinderGoalSelector var2) {
      if (var0 instanceof WorldServer) {
         ;
      }
   }

   public static void a(WorldServer var0, Collection<Raid> var1) {
   }

   public static void a(EntityLiving var0) {
   }

   public static void a(EntityBee var0) {
   }

   public static void a(World var0, GameEvent var1, Vec3D var2) {
   }

   public static void a(World var0, GameEventListener var1) {
   }

   public static void a(World var0, BlockPosition var1, IBlockData var2, TileEntityBeehive var3) {
   }

   private static void a(EntityLiving var0, PacketDataSerializer var1) {
      BehaviorController<?> var2 = var0.dH();
      long var3 = var0.H.U();
      if (var0 instanceof InventoryCarrier) {
         IInventory var5 = ((InventoryCarrier)var0).w();
         var1.a(var5.aa_() ? "" : var5.toString());
      } else {
         var1.a("");
      }

      var1.a(var2.a(MemoryModuleType.t) ? var2.c(MemoryModuleType.t) : Optional.empty(), (var0x, var1x) -> var1x.a(var0x));
      if (var0 instanceof EntityVillager var5) {
         boolean var6 = var5.a(var3);
         var1.writeBoolean(var6);
      } else {
         var1.writeBoolean(false);
      }

      if (var0.ae() == EntityTypes.bi) {
         Warden var5 = (Warden)var0;
         var1.writeInt(var5.r());
      } else {
         var1.writeInt(-1);
      }

      var1.a(var2.d(), (var0x, var1x) -> var0x.a(var1x.a()));
      Set<String> var5 = var2.e().stream().map(BehaviorControl::b).collect(Collectors.toSet());
      var1.a(var5, PacketDataSerializer::a);
      var1.a(a(var0, var3), (var0x, var1x) -> {
         String var2x = UtilColor.a(var1x, 255, true);
         var0x.a(var2x);
      });
      if (var0 instanceof EntityVillager) {
         Set<BlockPosition> var6 = Stream.of(MemoryModuleType.c, MemoryModuleType.b, MemoryModuleType.e)
            .map(var2::c)
            .flatMap(Optional::stream)
            .map(GlobalPos::b)
            .collect(Collectors.toSet());
         var1.a(var6, PacketDataSerializer::a);
      } else {
         var1.d(0);
      }

      if (var0 instanceof EntityVillager) {
         Set<BlockPosition> var6 = Stream.of(MemoryModuleType.d).map(var2::c).flatMap(Optional::stream).map(GlobalPos::b).collect(Collectors.toSet());
         var1.a(var6, PacketDataSerializer::a);
      } else {
         var1.d(0);
      }

      if (var0 instanceof EntityVillager) {
         Map<UUID, Object2IntMap<ReputationType>> var6 = ((EntityVillager)var0).gn().a();
         List<String> var7 = Lists.newArrayList();
         var6.forEach((var1x, var2x) -> {
            String var3x = DebugEntityNameGenerator.a(var1x);
            var2x.forEach((var2xx, var3xx) -> var7.add(var3x + ": " + var2xx + ": " + var3xx));
         });
         var1.a(var7, PacketDataSerializer::a);
      } else {
         var1.d(0);
      }
   }

   private static List<String> a(EntityLiving var0, long var1) {
      Map<MemoryModuleType<?>, Optional<? extends ExpirableMemory<?>>> var3 = var0.dH().b();
      List<String> var4 = Lists.newArrayList();

      for(Entry<MemoryModuleType<?>, Optional<? extends ExpirableMemory<?>>> var6 : var3.entrySet()) {
         MemoryModuleType<?> var7 = var6.getKey();
         Optional<? extends ExpirableMemory<?>> var8 = var6.getValue();
         String var9;
         if (var8.isPresent()) {
            ExpirableMemory<?> var10 = var8.get();
            Object var11 = var10.c();
            if (var7 == MemoryModuleType.D) {
               long var12 = var1 - (Long)var11;
               var9 = var12 + " ticks ago";
            } else if (var10.e()) {
               var9 = a((WorldServer)var0.H, var11) + " (ttl: " + var10.b() + ")";
            } else {
               var9 = a((WorldServer)var0.H, var11);
            }
         } else {
            var9 = "-";
         }

         var4.add(BuiltInRegistries.B.b(var7).a() + ": " + var9);
      }

      var4.sort(String::compareTo);
      return var4;
   }

   private static String a(WorldServer var0, @Nullable Object var1) {
      if (var1 == null) {
         return "-";
      } else if (var1 instanceof UUID) {
         return a(var0, var0.a((UUID)var1));
      } else if (var1 instanceof EntityLiving) {
         Entity var2 = (Entity)var1;
         return DebugEntityNameGenerator.a(var2);
      } else if (var1 instanceof INamableTileEntity) {
         return ((INamableTileEntity)var1).Z().getString();
      } else if (var1 instanceof MemoryTarget) {
         return a(var0, ((MemoryTarget)var1).a());
      } else if (var1 instanceof BehaviorPositionEntity) {
         return a(var0, ((BehaviorPositionEntity)var1).c());
      } else if (var1 instanceof GlobalPos) {
         return a(var0, ((GlobalPos)var1).b());
      } else if (var1 instanceof BehaviorTarget) {
         return a(var0, ((BehaviorTarget)var1).b());
      } else if (var1 instanceof DamageSource) {
         Entity var2 = ((DamageSource)var1).d();
         return var2 == null ? var1.toString() : a(var0, var2);
      } else if (!(var1 instanceof Collection)) {
         return var1.toString();
      } else {
         List<String> var2 = Lists.newArrayList();

         for(Object var4 : (Iterable)var1) {
            var2.add(a(var0, var4));
         }

         return var2.toString();
      }
   }

   private static void a(WorldServer var0, PacketDataSerializer var1, MinecraftKey var2) {
      Packet<?> var3 = new PacketPlayOutCustomPayload(var2, var1);

      for(EntityHuman var5 : var0.v()) {
         ((EntityPlayer)var5).b.a(var3);
      }
   }
}
