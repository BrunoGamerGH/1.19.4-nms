package net.minecraft.network.syncher;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vector3f;
import net.minecraft.core.particles.Particle;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.RegistryID;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import org.joml.Quaternionf;

public class DataWatcherRegistry {
   private static final RegistryID<DataWatcherSerializer<?>> C = RegistryID.c(16);
   public static final DataWatcherSerializer<Byte> a = DataWatcherSerializer.a((var0, var1) -> var0.writeByte(var1), PacketDataSerializer::readByte);
   public static final DataWatcherSerializer<Integer> b = DataWatcherSerializer.a(PacketDataSerializer::d, PacketDataSerializer::m);
   public static final DataWatcherSerializer<Long> c = DataWatcherSerializer.a(PacketDataSerializer::b, PacketDataSerializer::n);
   public static final DataWatcherSerializer<Float> d = DataWatcherSerializer.a(PacketDataSerializer::writeFloat, PacketDataSerializer::readFloat);
   public static final DataWatcherSerializer<String> e = DataWatcherSerializer.a(PacketDataSerializer::a, PacketDataSerializer::s);
   public static final DataWatcherSerializer<IChatBaseComponent> f = DataWatcherSerializer.a(PacketDataSerializer::a, PacketDataSerializer::l);
   public static final DataWatcherSerializer<Optional<IChatBaseComponent>> g = DataWatcherSerializer.b(PacketDataSerializer::a, PacketDataSerializer::l);
   public static final DataWatcherSerializer<ItemStack> h = new DataWatcherSerializer<ItemStack>() {
      public void a(PacketDataSerializer var0, ItemStack var1) {
         var0.a(var1);
      }

      public ItemStack b(PacketDataSerializer var0) {
         return var0.r();
      }

      public ItemStack a(ItemStack var0) {
         return var0.o();
      }
   };
   public static final DataWatcherSerializer<IBlockData> i = DataWatcherSerializer.a(Block.o);
   public static final DataWatcherSerializer<Optional<IBlockData>> j = new DataWatcherSerializer.a<Optional<IBlockData>>() {
      public void a(PacketDataSerializer var0, Optional<IBlockData> var1) {
         if (var1.isPresent()) {
            var0.d(Block.i(var1.get()));
         } else {
            var0.d(0);
         }
      }

      public Optional<IBlockData> b(PacketDataSerializer var0) {
         int var1 = var0.m();
         return var1 == 0 ? Optional.empty() : Optional.of(Block.a(var1));
      }
   };
   public static final DataWatcherSerializer<Boolean> k = DataWatcherSerializer.a(PacketDataSerializer::writeBoolean, PacketDataSerializer::readBoolean);
   public static final DataWatcherSerializer<ParticleParam> l = new DataWatcherSerializer.a<ParticleParam>() {
      public void a(PacketDataSerializer var0, ParticleParam var1) {
         var0.a(BuiltInRegistries.k, var1.b());
         var1.a(var0);
      }

      public ParticleParam b(PacketDataSerializer var0) {
         return this.a(var0, var0.a(BuiltInRegistries.k));
      }

      private <T extends ParticleParam> T a(PacketDataSerializer var0, Particle<T> var1) {
         return var1.d().b(var1, var0);
      }
   };
   public static final DataWatcherSerializer<Vector3f> m = new DataWatcherSerializer.a<Vector3f>() {
      public void a(PacketDataSerializer var0, Vector3f var1) {
         var0.writeFloat(var1.b());
         var0.writeFloat(var1.c());
         var0.writeFloat(var1.d());
      }

      public Vector3f b(PacketDataSerializer var0) {
         return new Vector3f(var0.readFloat(), var0.readFloat(), var0.readFloat());
      }
   };
   public static final DataWatcherSerializer<BlockPosition> n = DataWatcherSerializer.a(PacketDataSerializer::a, PacketDataSerializer::f);
   public static final DataWatcherSerializer<Optional<BlockPosition>> o = DataWatcherSerializer.b(PacketDataSerializer::a, PacketDataSerializer::f);
   public static final DataWatcherSerializer<EnumDirection> p = DataWatcherSerializer.a(EnumDirection.class);
   public static final DataWatcherSerializer<Optional<UUID>> q = DataWatcherSerializer.b(PacketDataSerializer::a, PacketDataSerializer::o);
   public static final DataWatcherSerializer<Optional<GlobalPos>> r = DataWatcherSerializer.b(PacketDataSerializer::a, PacketDataSerializer::i);
   public static final DataWatcherSerializer<NBTTagCompound> s = new DataWatcherSerializer<NBTTagCompound>() {
      public void a(PacketDataSerializer var0, NBTTagCompound var1) {
         var0.a(var1);
      }

      public NBTTagCompound b(PacketDataSerializer var0) {
         return var0.p();
      }

      public NBTTagCompound a(NBTTagCompound var0) {
         return var0.h();
      }
   };
   public static final DataWatcherSerializer<VillagerData> t = new DataWatcherSerializer.a<VillagerData>() {
      public void a(PacketDataSerializer var0, VillagerData var1) {
         var0.a(BuiltInRegistries.y, var1.a());
         var0.a(BuiltInRegistries.z, var1.b());
         var0.d(var1.c());
      }

      public VillagerData b(PacketDataSerializer var0) {
         return new VillagerData(var0.a(BuiltInRegistries.y), var0.a(BuiltInRegistries.z), var0.m());
      }
   };
   public static final DataWatcherSerializer<OptionalInt> u = new DataWatcherSerializer.a<OptionalInt>() {
      public void a(PacketDataSerializer var0, OptionalInt var1) {
         var0.d(var1.orElse(-1) + 1);
      }

      public OptionalInt b(PacketDataSerializer var0) {
         int var1 = var0.m();
         return var1 == 0 ? OptionalInt.empty() : OptionalInt.of(var1 - 1);
      }
   };
   public static final DataWatcherSerializer<EntityPose> v = DataWatcherSerializer.a(EntityPose.class);
   public static final DataWatcherSerializer<CatVariant> w = DataWatcherSerializer.a(BuiltInRegistries.ai);
   public static final DataWatcherSerializer<FrogVariant> x = DataWatcherSerializer.a(BuiltInRegistries.aj);
   public static final DataWatcherSerializer<Holder<PaintingVariant>> y = DataWatcherSerializer.a(BuiltInRegistries.m.t());
   public static final DataWatcherSerializer<Sniffer.a> z = DataWatcherSerializer.a(Sniffer.a.class);
   public static final DataWatcherSerializer<org.joml.Vector3f> A = DataWatcherSerializer.a(PacketDataSerializer::a, PacketDataSerializer::j);
   public static final DataWatcherSerializer<Quaternionf> B = DataWatcherSerializer.a(PacketDataSerializer::a, PacketDataSerializer::k);

   public static void a(DataWatcherSerializer<?> var0) {
      C.c(var0);
   }

   @Nullable
   public static DataWatcherSerializer<?> a(int var0) {
      return C.a(var0);
   }

   public static int b(DataWatcherSerializer<?> var0) {
      return C.a(var0);
   }

   private DataWatcherRegistry() {
   }

   static {
      a(a);
      a(b);
      a(c);
      a(d);
      a(e);
      a(f);
      a(g);
      a(h);
      a(k);
      a(m);
      a(n);
      a(o);
      a(p);
      a(q);
      a(i);
      a(j);
      a(s);
      a(l);
      a(t);
      a(u);
      a(v);
      a(w);
      a(x);
      a(r);
      a(y);
      a(z);
      a(A);
      a(B);
   }
}
