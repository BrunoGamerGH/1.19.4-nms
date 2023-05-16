package net.minecraft.network;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTReadLimiter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.CryptographyException;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MinecraftEncryption;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class PacketDataSerializer extends ByteBuf {
   private static final int d = 5;
   private static final int e = 10;
   public static final int a = 2097152;
   private final ByteBuf f;
   public static final short b = 32767;
   public static final int c = 262144;
   private static final int g = 256;
   private static final int h = 256;
   private static final int i = 512;
   private static final Gson j = new Gson();

   public PacketDataSerializer(ByteBuf bytebuf) {
      this.f = bytebuf;
   }

   public static int a(int i) {
      for(int j = 1; j < 5; ++j) {
         if ((i & -1 << j * 7) == 0) {
            return j;
         }
      }

      return 5;
   }

   public static int a(long i) {
      for(int j = 1; j < 10; ++j) {
         if ((i & -1L << j * 7) == 0L) {
            return j;
         }
      }

      return 10;
   }

   @Deprecated
   public <T> T a(DynamicOps<NBTBase> dynamicops, Codec<T> codec) {
      NBTTagCompound nbttagcompound = this.q();
      return SystemUtils.a(codec.parse(dynamicops, nbttagcompound), s -> new DecoderException("Failed to decode: " + s + " " + nbttagcompound));
   }

   @Deprecated
   public <T> void a(DynamicOps<NBTBase> dynamicops, Codec<T> codec, T t0) {
      NBTBase nbtbase = SystemUtils.a(codec.encodeStart(dynamicops, t0), s -> new EncoderException("Failed to encode: " + s + " " + t0));
      this.a((NBTTagCompound)nbtbase);
   }

   public <T> T a(Codec<T> codec) {
      JsonElement jsonelement = ChatDeserializer.a(j, this.s(), JsonElement.class);
      DataResult<T> dataresult = codec.parse(JsonOps.INSTANCE, jsonelement);
      return SystemUtils.a(dataresult, s -> new DecoderException("Failed to decode json: " + s));
   }

   public <T> void a(Codec<T> codec, T t0) {
      DataResult<JsonElement> dataresult = codec.encodeStart(JsonOps.INSTANCE, t0);
      this.a(j.toJson(SystemUtils.a(dataresult, s -> new EncoderException("Failed to encode: " + s + " " + t0))));
   }

   public <T> void a(Registry<T> registry, T t0) {
      int i = registry.a(t0);
      if (i == -1) {
         throw new IllegalArgumentException("Can't find id for '" + t0 + "' in map " + registry);
      } else {
         this.d(i);
      }
   }

   public <T> void a(Registry<Holder<T>> registry, Holder<T> holder, PacketDataSerializer.b<T> packetdataserializer_b) {
      switch(holder.f()) {
         case a:
            int i = registry.a(holder);
            if (i == -1) {
               Object object = holder.a();
               throw new IllegalArgumentException("Can't find id for '" + object + "' in map " + registry);
            }

            this.d(i + 1);
            break;
         case b:
            this.d(0);
            packetdataserializer_b.accept((T)this, holder.a());
      }
   }

   @Nullable
   public <T> T a(Registry<T> registry) {
      int i = this.m();
      return registry.a(i);
   }

   public <T> Holder<T> a(Registry<Holder<T>> registry, PacketDataSerializer.a<T> packetdataserializer_a) {
      int i = this.m();
      if (i == 0) {
         return Holder.a(packetdataserializer_a.apply((T)this));
      } else {
         Holder<T> holder = registry.a(i - 1);
         if (holder == null) {
            throw new IllegalArgumentException("Can't find element with id " + i);
         } else {
            return holder;
         }
      }
   }

   public static <T> IntFunction<T> a(IntFunction<T> intfunction, int i) {
      return j -> {
         if (j > i) {
            throw new DecoderException("Value " + j + " is larger than limit " + i);
         } else {
            return intfunction.apply(j);
         }
      };
   }

   public <T, C extends Collection<T>> C a(IntFunction<C> intfunction, PacketDataSerializer.a<T> packetdataserializer_a) {
      int i = this.m();
      C c0 = intfunction.apply(i);

      for(int j = 0; j < i; ++j) {
         c0.add(packetdataserializer_a.apply((T)this));
      }

      return c0;
   }

   public <T> void a(Collection<T> collection, PacketDataSerializer.b<T> packetdataserializer_b) {
      this.d(collection.size());

      for(T t0 : collection) {
         packetdataserializer_b.accept((T)this, t0);
      }
   }

   public <T> List<T> a(PacketDataSerializer.a<T> packetdataserializer_a) {
      return this.a(Lists::newArrayListWithCapacity, packetdataserializer_a);
   }

   public IntList a() {
      int i = this.m();
      IntArrayList intarraylist = new IntArrayList();

      for(int j = 0; j < i; ++j) {
         intarraylist.add(this.m());
      }

      return intarraylist;
   }

   public void a(IntList intlist) {
      this.d(intlist.size());
      intlist.forEach(this::d);
   }

   public <K, V, M extends Map<K, V>> M a(
      IntFunction<M> intfunction, PacketDataSerializer.a<K> packetdataserializer_a, PacketDataSerializer.a<V> packetdataserializer_a1
   ) {
      int i = this.m();
      M m0 = intfunction.apply(i);

      for(int j = 0; j < i; ++j) {
         K k0 = packetdataserializer_a.apply((K)this);
         V v0 = packetdataserializer_a1.apply((V)this);
         m0.put(k0, v0);
      }

      return m0;
   }

   public <K, V> Map<K, V> a(PacketDataSerializer.a<K> packetdataserializer_a, PacketDataSerializer.a<V> packetdataserializer_a1) {
      return this.a(Maps::newHashMapWithExpectedSize, packetdataserializer_a, packetdataserializer_a1);
   }

   public <K, V> void a(Map<K, V> map, PacketDataSerializer.b<K> packetdataserializer_b, PacketDataSerializer.b<V> packetdataserializer_b1) {
      this.d(map.size());
      map.forEach((object, object1) -> {
         packetdataserializer_b.accept((K)this, object);
         packetdataserializer_b1.accept((V)this, object1);
      });
   }

   public void a(Consumer<PacketDataSerializer> consumer) {
      int i = this.m();

      for(int j = 0; j < i; ++j) {
         consumer.accept(this);
      }
   }

   public <E extends Enum<E>> void a(EnumSet<E> enumset, Class<E> oclass) {
      Enum[] ae = oclass.getEnumConstants();
      BitSet bitset = new BitSet(ae.length);

      for(int i = 0; i < ae.length; ++i) {
         bitset.set(i, enumset.contains(ae[i]));
      }

      this.a(bitset, ae.length);
   }

   public <E extends Enum<E>> EnumSet<E> a(Class<E> oclass) {
      Enum[] ae = oclass.getEnumConstants();
      BitSet bitset = this.f(ae.length);
      EnumSet<E> enumset = EnumSet.noneOf(oclass);

      for(int i = 0; i < ae.length; ++i) {
         if (bitset.get(i)) {
            enumset.add((E)ae[i]);
         }
      }

      return enumset;
   }

   public <T> void a(Optional<T> optional, PacketDataSerializer.b<T> packetdataserializer_b) {
      if (optional.isPresent()) {
         this.writeBoolean(true);
         packetdataserializer_b.accept((T)this, optional.get());
      } else {
         this.writeBoolean(false);
      }
   }

   public <T> Optional<T> b(PacketDataSerializer.a<T> packetdataserializer_a) {
      return this.readBoolean() ? Optional.of(packetdataserializer_a.apply((T)this)) : Optional.empty();
   }

   @Nullable
   public <T> T c(PacketDataSerializer.a<T> packetdataserializer_a) {
      return this.readBoolean() ? packetdataserializer_a.apply((T)this) : null;
   }

   public <T> void a(@Nullable T t0, PacketDataSerializer.b<T> packetdataserializer_b) {
      if (t0 != null) {
         this.writeBoolean(true);
         packetdataserializer_b.accept((T)this, t0);
      } else {
         this.writeBoolean(false);
      }
   }

   public <L, R> void a(Either<L, R> either, PacketDataSerializer.b<L> packetdataserializer_b, PacketDataSerializer.b<R> packetdataserializer_b1) {
      either.ifLeft(object -> {
         this.writeBoolean(true);
         packetdataserializer_b.accept((L)this, object);
      }).ifRight(object -> {
         this.writeBoolean(false);
         packetdataserializer_b1.accept((R)this, object);
      });
   }

   public <L, R> Either<L, R> b(PacketDataSerializer.a<L> packetdataserializer_a, PacketDataSerializer.a<R> packetdataserializer_a1) {
      return this.readBoolean() ? Either.left(packetdataserializer_a.apply((L)this)) : Either.right(packetdataserializer_a1.apply((R)this));
   }

   public byte[] b() {
      return this.b(this.readableBytes());
   }

   public PacketDataSerializer a(byte[] abyte) {
      this.d(abyte.length);
      this.writeBytes(abyte);
      return this;
   }

   public byte[] b(int i) {
      int j = this.m();
      if (j > i) {
         throw new DecoderException("ByteArray with size " + j + " is bigger than allowed " + i);
      } else {
         byte[] abyte = new byte[j];
         this.readBytes(abyte);
         return abyte;
      }
   }

   public PacketDataSerializer a(int[] aint) {
      this.d(aint.length);

      for(int k : aint) {
         this.d(k);
      }

      return this;
   }

   public int[] c() {
      return this.c(this.readableBytes());
   }

   public int[] c(int i) {
      int j = this.m();
      if (j > i) {
         throw new DecoderException("VarIntArray with size " + j + " is bigger than allowed " + i);
      } else {
         int[] aint = new int[j];

         for(int k = 0; k < aint.length; ++k) {
            aint[k] = this.m();
         }

         return aint;
      }
   }

   public PacketDataSerializer a(long[] along) {
      this.d(along.length);

      for(long k : along) {
         this.writeLong(k);
      }

      return this;
   }

   public long[] d() {
      return this.b(null);
   }

   public long[] b(@Nullable long[] along) {
      return this.a(along, this.readableBytes() / 8);
   }

   public long[] a(@Nullable long[] along, int i) {
      int j = this.m();
      if (along == null || along.length != j) {
         if (j > i) {
            throw new DecoderException("LongArray with size " + j + " is bigger than allowed " + i);
         }

         along = new long[j];
      }

      for(int k = 0; k < along.length; ++k) {
         along[k] = this.readLong();
      }

      return along;
   }

   @VisibleForTesting
   public byte[] e() {
      int i = this.writerIndex();
      byte[] abyte = new byte[i];
      this.getBytes(0, abyte);
      return abyte;
   }

   public BlockPosition f() {
      return BlockPosition.d(this.readLong());
   }

   public PacketDataSerializer a(BlockPosition blockposition) {
      this.writeLong(blockposition.a());
      return this;
   }

   public ChunkCoordIntPair g() {
      return new ChunkCoordIntPair(this.readLong());
   }

   public PacketDataSerializer a(ChunkCoordIntPair chunkcoordintpair) {
      this.writeLong(chunkcoordintpair.a());
      return this;
   }

   public SectionPosition h() {
      return SectionPosition.a(this.readLong());
   }

   public PacketDataSerializer a(SectionPosition sectionposition) {
      this.writeLong(sectionposition.s());
      return this;
   }

   public GlobalPos i() {
      ResourceKey<World> resourcekey = this.a(Registries.aF);
      BlockPosition blockposition = this.f();
      return GlobalPos.a(resourcekey, blockposition);
   }

   public void a(GlobalPos globalpos) {
      this.b(globalpos.a());
      this.a(globalpos.b());
   }

   public Vector3f j() {
      return new Vector3f(this.readFloat(), this.readFloat(), this.readFloat());
   }

   public void a(Vector3f vector3f) {
      this.writeFloat(vector3f.x());
      this.writeFloat(vector3f.y());
      this.writeFloat(vector3f.z());
   }

   public Quaternionf k() {
      return new Quaternionf(this.readFloat(), this.readFloat(), this.readFloat(), this.readFloat());
   }

   public void a(Quaternionf quaternionf) {
      this.writeFloat(quaternionf.x);
      this.writeFloat(quaternionf.y);
      this.writeFloat(quaternionf.z);
      this.writeFloat(quaternionf.w);
   }

   public IChatBaseComponent l() {
      IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.ChatSerializer.a(this.e(262144));
      if (ichatmutablecomponent == null) {
         throw new DecoderException("Received unexpected null component");
      } else {
         return ichatmutablecomponent;
      }
   }

   public PacketDataSerializer a(IChatBaseComponent ichatbasecomponent) {
      return this.a(IChatBaseComponent.ChatSerializer.a(ichatbasecomponent), 262144);
   }

   public <T extends Enum<T>> T b(Class<T> oclass) {
      return oclass.getEnumConstants()[this.m()];
   }

   public PacketDataSerializer a(Enum<?> oenum) {
      return this.d(oenum.ordinal());
   }

   public int m() {
      int i = 0;
      int j = 0;

      byte b0;
      do {
         b0 = this.readByte();
         i |= (b0 & 127) << j++ * 7;
         if (j > 5) {
            throw new RuntimeException("VarInt too big");
         }
      } while((b0 & 128) == 128);

      return i;
   }

   public long n() {
      long i = 0L;
      int j = 0;

      byte b0;
      do {
         b0 = this.readByte();
         i |= (long)(b0 & 127) << j++ * 7;
         if (j > 10) {
            throw new RuntimeException("VarLong too big");
         }
      } while((b0 & 128) == 128);

      return i;
   }

   public PacketDataSerializer a(UUID uuid) {
      this.writeLong(uuid.getMostSignificantBits());
      this.writeLong(uuid.getLeastSignificantBits());
      return this;
   }

   public UUID o() {
      return new UUID(this.readLong(), this.readLong());
   }

   public PacketDataSerializer d(int i) {
      while((i & -128) != 0) {
         this.writeByte(i & 127 | 128);
         i >>>= 7;
      }

      this.writeByte(i);
      return this;
   }

   public PacketDataSerializer b(long i) {
      while((i & -128L) != 0L) {
         this.writeByte((int)(i & 127L) | 128);
         i >>>= 7;
      }

      this.writeByte((int)i);
      return this;
   }

   public PacketDataSerializer a(@Nullable NBTTagCompound nbttagcompound) {
      if (nbttagcompound == null) {
         this.writeByte(0);
      } else {
         try {
            NBTCompressedStreamTools.a(nbttagcompound, new ByteBufOutputStream(this));
         } catch (Exception var3) {
            throw new EncoderException(var3);
         }
      }

      return this;
   }

   @Nullable
   public NBTTagCompound p() {
      return this.a(new NBTReadLimiter(2097152L));
   }

   @Nullable
   public NBTTagCompound q() {
      return this.a(NBTReadLimiter.a);
   }

   @Nullable
   public NBTTagCompound a(NBTReadLimiter nbtreadlimiter) {
      int i = this.readerIndex();
      byte b0 = this.readByte();
      if (b0 == 0) {
         return null;
      } else {
         this.readerIndex(i);

         try {
            return NBTCompressedStreamTools.a(new ByteBufInputStream(this), nbtreadlimiter);
         } catch (IOException var5) {
            throw new EncoderException(var5);
         }
      }
   }

   public PacketDataSerializer a(ItemStack itemstack) {
      if (!itemstack.b() && itemstack.c() != null) {
         this.writeBoolean(true);
         Item item = itemstack.c();
         this.a(BuiltInRegistries.i, item);
         this.writeByte(itemstack.K());
         NBTTagCompound nbttagcompound = null;
         if (item.o() || item.r()) {
            itemstack = itemstack.o();
            CraftItemStack.setItemMeta(itemstack, CraftItemStack.getItemMeta(itemstack));
            nbttagcompound = itemstack.u();
         }

         this.a(nbttagcompound);
      } else {
         this.writeBoolean(false);
      }

      return this;
   }

   public ItemStack r() {
      if (!this.readBoolean()) {
         return ItemStack.b;
      } else {
         Item item = this.a(BuiltInRegistries.i);
         byte b0 = this.readByte();
         ItemStack itemstack = new ItemStack(item, b0);
         itemstack.c(this.p());
         if (itemstack.u() != null) {
            CraftItemStack.setItemMeta(itemstack, CraftItemStack.getItemMeta(itemstack));
         }

         return itemstack;
      }
   }

   public String s() {
      return this.e(32767);
   }

   public String e(int i) {
      int j = g(i);
      int k = this.m();
      if (k > j) {
         throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + k + " > " + j + ")");
      } else if (k < 0) {
         throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
      } else {
         String s = this.toString(this.readerIndex(), k, StandardCharsets.UTF_8);
         this.readerIndex(this.readerIndex() + k);
         if (s.length() > i) {
            int l = s.length();
            throw new DecoderException("The received string length is longer than maximum allowed (" + l + " > " + i + ")");
         } else {
            return s;
         }
      }
   }

   public PacketDataSerializer a(String s) {
      return this.a(s, 32767);
   }

   public PacketDataSerializer a(String s, int i) {
      if (s.length() > i) {
         int j = s.length();
         throw new EncoderException("String too big (was " + j + " characters, max " + i + ")");
      } else {
         byte[] abyte = s.getBytes(StandardCharsets.UTF_8);
         int k = g(i);
         if (abyte.length > k) {
            throw new EncoderException("String too big (was " + abyte.length + " bytes encoded, max " + k + ")");
         } else {
            this.d(abyte.length);
            this.writeBytes(abyte);
            return this;
         }
      }
   }

   private static int g(int i) {
      return i * 3;
   }

   public MinecraftKey t() {
      return new MinecraftKey(this.e(32767));
   }

   public PacketDataSerializer a(MinecraftKey minecraftkey) {
      this.a(minecraftkey.toString());
      return this;
   }

   public <T> ResourceKey<T> a(ResourceKey<? extends IRegistry<T>> resourcekey) {
      MinecraftKey minecraftkey = this.t();
      return ResourceKey.a(resourcekey, minecraftkey);
   }

   public void b(ResourceKey<?> resourcekey) {
      this.a(resourcekey.a());
   }

   public Date u() {
      return new Date(this.readLong());
   }

   public PacketDataSerializer a(Date date) {
      this.writeLong(date.getTime());
      return this;
   }

   public Instant v() {
      return Instant.ofEpochMilli(this.readLong());
   }

   public void a(Instant instant) {
      this.writeLong(instant.toEpochMilli());
   }

   public PublicKey w() {
      try {
         return MinecraftEncryption.a(this.b(512));
      } catch (CryptographyException var2) {
         throw new DecoderException("Malformed public key bytes", var2);
      }
   }

   public PacketDataSerializer a(PublicKey publickey) {
      this.a(publickey.getEncoded());
      return this;
   }

   public MovingObjectPositionBlock x() {
      BlockPosition blockposition = this.f();
      EnumDirection enumdirection = this.b(EnumDirection.class);
      float f = this.readFloat();
      float f1 = this.readFloat();
      float f2 = this.readFloat();
      boolean flag = this.readBoolean();
      return new MovingObjectPositionBlock(
         new Vec3D((double)blockposition.u() + (double)f, (double)blockposition.v() + (double)f1, (double)blockposition.w() + (double)f2),
         enumdirection,
         blockposition,
         flag
      );
   }

   public void a(MovingObjectPositionBlock movingobjectpositionblock) {
      BlockPosition blockposition = movingobjectpositionblock.a();
      this.a(blockposition);
      this.a(movingobjectpositionblock.b());
      Vec3D vec3d = movingobjectpositionblock.e();
      this.writeFloat((float)(vec3d.c - (double)blockposition.u()));
      this.writeFloat((float)(vec3d.d - (double)blockposition.v()));
      this.writeFloat((float)(vec3d.e - (double)blockposition.w()));
      this.writeBoolean(movingobjectpositionblock.d());
   }

   public BitSet y() {
      return BitSet.valueOf(this.d());
   }

   public void a(BitSet bitset) {
      this.a(bitset.toLongArray());
   }

   public BitSet f(int i) {
      byte[] abyte = new byte[MathHelper.e(i, 8)];
      this.readBytes(abyte);
      return BitSet.valueOf(abyte);
   }

   public void a(BitSet bitset, int i) {
      if (bitset.length() > i) {
         int j = bitset.length();
         throw new EncoderException("BitSet is larger than expected size (" + j + ">" + i + ")");
      } else {
         byte[] abyte = bitset.toByteArray();
         this.writeBytes(Arrays.copyOf(abyte, MathHelper.e(i, 8)));
      }
   }

   public GameProfile z() {
      UUID uuid = this.o();
      String s = this.e(16);
      GameProfile gameprofile = new GameProfile(uuid, s);
      gameprofile.getProperties().putAll(this.A());
      return gameprofile;
   }

   public void a(GameProfile gameprofile) {
      this.a(gameprofile.getId());
      this.a(gameprofile.getName());
      this.a(gameprofile.getProperties());
   }

   public PropertyMap A() {
      PropertyMap propertymap = new PropertyMap();
      this.a((Consumer<PacketDataSerializer>)(packetdataserializer -> {
         Property property = this.B();
         propertymap.put(property.getName(), property);
      }));
      return propertymap;
   }

   public void a(PropertyMap propertymap) {
      this.a(propertymap.values(), PacketDataSerializer::a);
   }

   public Property B() {
      String s = this.s();
      String s1 = this.s();
      if (this.readBoolean()) {
         String s2 = this.s();
         return new Property(s, s1, s2);
      } else {
         return new Property(s, s1);
      }
   }

   public void a(Property property) {
      this.a(property.getName());
      this.a(property.getValue());
      if (property.hasSignature()) {
         this.writeBoolean(true);
         this.a(property.getSignature());
      } else {
         this.writeBoolean(false);
      }
   }

   public int capacity() {
      return this.f.capacity();
   }

   public ByteBuf capacity(int i) {
      return this.f.capacity(i);
   }

   public int maxCapacity() {
      return this.f.maxCapacity();
   }

   public ByteBufAllocator alloc() {
      return this.f.alloc();
   }

   public ByteOrder order() {
      return this.f.order();
   }

   public ByteBuf order(ByteOrder byteorder) {
      return this.f.order(byteorder);
   }

   public ByteBuf unwrap() {
      return this.f.unwrap();
   }

   public boolean isDirect() {
      return this.f.isDirect();
   }

   public boolean isReadOnly() {
      return this.f.isReadOnly();
   }

   public ByteBuf asReadOnly() {
      return this.f.asReadOnly();
   }

   public int readerIndex() {
      return this.f.readerIndex();
   }

   public ByteBuf readerIndex(int i) {
      return this.f.readerIndex(i);
   }

   public int writerIndex() {
      return this.f.writerIndex();
   }

   public ByteBuf writerIndex(int i) {
      return this.f.writerIndex(i);
   }

   public ByteBuf setIndex(int i, int j) {
      return this.f.setIndex(i, j);
   }

   public int readableBytes() {
      return this.f.readableBytes();
   }

   public int writableBytes() {
      return this.f.writableBytes();
   }

   public int maxWritableBytes() {
      return this.f.maxWritableBytes();
   }

   public boolean isReadable() {
      return this.f.isReadable();
   }

   public boolean isReadable(int i) {
      return this.f.isReadable(i);
   }

   public boolean isWritable() {
      return this.f.isWritable();
   }

   public boolean isWritable(int i) {
      return this.f.isWritable(i);
   }

   public ByteBuf clear() {
      return this.f.clear();
   }

   public ByteBuf markReaderIndex() {
      return this.f.markReaderIndex();
   }

   public ByteBuf resetReaderIndex() {
      return this.f.resetReaderIndex();
   }

   public ByteBuf markWriterIndex() {
      return this.f.markWriterIndex();
   }

   public ByteBuf resetWriterIndex() {
      return this.f.resetWriterIndex();
   }

   public ByteBuf discardReadBytes() {
      return this.f.discardReadBytes();
   }

   public ByteBuf discardSomeReadBytes() {
      return this.f.discardSomeReadBytes();
   }

   public ByteBuf ensureWritable(int i) {
      return this.f.ensureWritable(i);
   }

   public int ensureWritable(int i, boolean flag) {
      return this.f.ensureWritable(i, flag);
   }

   public boolean getBoolean(int i) {
      return this.f.getBoolean(i);
   }

   public byte getByte(int i) {
      return this.f.getByte(i);
   }

   public short getUnsignedByte(int i) {
      return this.f.getUnsignedByte(i);
   }

   public short getShort(int i) {
      return this.f.getShort(i);
   }

   public short getShortLE(int i) {
      return this.f.getShortLE(i);
   }

   public int getUnsignedShort(int i) {
      return this.f.getUnsignedShort(i);
   }

   public int getUnsignedShortLE(int i) {
      return this.f.getUnsignedShortLE(i);
   }

   public int getMedium(int i) {
      return this.f.getMedium(i);
   }

   public int getMediumLE(int i) {
      return this.f.getMediumLE(i);
   }

   public int getUnsignedMedium(int i) {
      return this.f.getUnsignedMedium(i);
   }

   public int getUnsignedMediumLE(int i) {
      return this.f.getUnsignedMediumLE(i);
   }

   public int getInt(int i) {
      return this.f.getInt(i);
   }

   public int getIntLE(int i) {
      return this.f.getIntLE(i);
   }

   public long getUnsignedInt(int i) {
      return this.f.getUnsignedInt(i);
   }

   public long getUnsignedIntLE(int i) {
      return this.f.getUnsignedIntLE(i);
   }

   public long getLong(int i) {
      return this.f.getLong(i);
   }

   public long getLongLE(int i) {
      return this.f.getLongLE(i);
   }

   public char getChar(int i) {
      return this.f.getChar(i);
   }

   public float getFloat(int i) {
      return this.f.getFloat(i);
   }

   public double getDouble(int i) {
      return this.f.getDouble(i);
   }

   public ByteBuf getBytes(int i, ByteBuf bytebuf) {
      return this.f.getBytes(i, bytebuf);
   }

   public ByteBuf getBytes(int i, ByteBuf bytebuf, int j) {
      return this.f.getBytes(i, bytebuf, j);
   }

   public ByteBuf getBytes(int i, ByteBuf bytebuf, int j, int k) {
      return this.f.getBytes(i, bytebuf, j, k);
   }

   public ByteBuf getBytes(int i, byte[] abyte) {
      return this.f.getBytes(i, abyte);
   }

   public ByteBuf getBytes(int i, byte[] abyte, int j, int k) {
      return this.f.getBytes(i, abyte, j, k);
   }

   public ByteBuf getBytes(int i, ByteBuffer bytebuffer) {
      return this.f.getBytes(i, bytebuffer);
   }

   public ByteBuf getBytes(int i, OutputStream outputstream, int j) throws IOException {
      return this.f.getBytes(i, outputstream, j);
   }

   public int getBytes(int i, GatheringByteChannel gatheringbytechannel, int j) throws IOException {
      return this.f.getBytes(i, gatheringbytechannel, j);
   }

   public int getBytes(int i, FileChannel filechannel, long j, int k) throws IOException {
      return this.f.getBytes(i, filechannel, j, k);
   }

   public CharSequence getCharSequence(int i, int j, Charset charset) {
      return this.f.getCharSequence(i, j, charset);
   }

   public ByteBuf setBoolean(int i, boolean flag) {
      return this.f.setBoolean(i, flag);
   }

   public ByteBuf setByte(int i, int j) {
      return this.f.setByte(i, j);
   }

   public ByteBuf setShort(int i, int j) {
      return this.f.setShort(i, j);
   }

   public ByteBuf setShortLE(int i, int j) {
      return this.f.setShortLE(i, j);
   }

   public ByteBuf setMedium(int i, int j) {
      return this.f.setMedium(i, j);
   }

   public ByteBuf setMediumLE(int i, int j) {
      return this.f.setMediumLE(i, j);
   }

   public ByteBuf setInt(int i, int j) {
      return this.f.setInt(i, j);
   }

   public ByteBuf setIntLE(int i, int j) {
      return this.f.setIntLE(i, j);
   }

   public ByteBuf setLong(int i, long j) {
      return this.f.setLong(i, j);
   }

   public ByteBuf setLongLE(int i, long j) {
      return this.f.setLongLE(i, j);
   }

   public ByteBuf setChar(int i, int j) {
      return this.f.setChar(i, j);
   }

   public ByteBuf setFloat(int i, float f) {
      return this.f.setFloat(i, f);
   }

   public ByteBuf setDouble(int i, double d0) {
      return this.f.setDouble(i, d0);
   }

   public ByteBuf setBytes(int i, ByteBuf bytebuf) {
      return this.f.setBytes(i, bytebuf);
   }

   public ByteBuf setBytes(int i, ByteBuf bytebuf, int j) {
      return this.f.setBytes(i, bytebuf, j);
   }

   public ByteBuf setBytes(int i, ByteBuf bytebuf, int j, int k) {
      return this.f.setBytes(i, bytebuf, j, k);
   }

   public ByteBuf setBytes(int i, byte[] abyte) {
      return this.f.setBytes(i, abyte);
   }

   public ByteBuf setBytes(int i, byte[] abyte, int j, int k) {
      return this.f.setBytes(i, abyte, j, k);
   }

   public ByteBuf setBytes(int i, ByteBuffer bytebuffer) {
      return this.f.setBytes(i, bytebuffer);
   }

   public int setBytes(int i, InputStream inputstream, int j) throws IOException {
      return this.f.setBytes(i, inputstream, j);
   }

   public int setBytes(int i, ScatteringByteChannel scatteringbytechannel, int j) throws IOException {
      return this.f.setBytes(i, scatteringbytechannel, j);
   }

   public int setBytes(int i, FileChannel filechannel, long j, int k) throws IOException {
      return this.f.setBytes(i, filechannel, j, k);
   }

   public ByteBuf setZero(int i, int j) {
      return this.f.setZero(i, j);
   }

   public int setCharSequence(int i, CharSequence charsequence, Charset charset) {
      return this.f.setCharSequence(i, charsequence, charset);
   }

   public boolean readBoolean() {
      return this.f.readBoolean();
   }

   public byte readByte() {
      return this.f.readByte();
   }

   public short readUnsignedByte() {
      return this.f.readUnsignedByte();
   }

   public short readShort() {
      return this.f.readShort();
   }

   public short readShortLE() {
      return this.f.readShortLE();
   }

   public int readUnsignedShort() {
      return this.f.readUnsignedShort();
   }

   public int readUnsignedShortLE() {
      return this.f.readUnsignedShortLE();
   }

   public int readMedium() {
      return this.f.readMedium();
   }

   public int readMediumLE() {
      return this.f.readMediumLE();
   }

   public int readUnsignedMedium() {
      return this.f.readUnsignedMedium();
   }

   public int readUnsignedMediumLE() {
      return this.f.readUnsignedMediumLE();
   }

   public int readInt() {
      return this.f.readInt();
   }

   public int readIntLE() {
      return this.f.readIntLE();
   }

   public long readUnsignedInt() {
      return this.f.readUnsignedInt();
   }

   public long readUnsignedIntLE() {
      return this.f.readUnsignedIntLE();
   }

   public long readLong() {
      return this.f.readLong();
   }

   public long readLongLE() {
      return this.f.readLongLE();
   }

   public char readChar() {
      return this.f.readChar();
   }

   public float readFloat() {
      return this.f.readFloat();
   }

   public double readDouble() {
      return this.f.readDouble();
   }

   public ByteBuf readBytes(int i) {
      return this.f.readBytes(i);
   }

   public ByteBuf readSlice(int i) {
      return this.f.readSlice(i);
   }

   public ByteBuf readRetainedSlice(int i) {
      return this.f.readRetainedSlice(i);
   }

   public ByteBuf readBytes(ByteBuf bytebuf) {
      return this.f.readBytes(bytebuf);
   }

   public ByteBuf readBytes(ByteBuf bytebuf, int i) {
      return this.f.readBytes(bytebuf, i);
   }

   public ByteBuf readBytes(ByteBuf bytebuf, int i, int j) {
      return this.f.readBytes(bytebuf, i, j);
   }

   public ByteBuf readBytes(byte[] abyte) {
      return this.f.readBytes(abyte);
   }

   public ByteBuf readBytes(byte[] abyte, int i, int j) {
      return this.f.readBytes(abyte, i, j);
   }

   public ByteBuf readBytes(ByteBuffer bytebuffer) {
      return this.f.readBytes(bytebuffer);
   }

   public ByteBuf readBytes(OutputStream outputstream, int i) throws IOException {
      return this.f.readBytes(outputstream, i);
   }

   public int readBytes(GatheringByteChannel gatheringbytechannel, int i) throws IOException {
      return this.f.readBytes(gatheringbytechannel, i);
   }

   public CharSequence readCharSequence(int i, Charset charset) {
      return this.f.readCharSequence(i, charset);
   }

   public int readBytes(FileChannel filechannel, long i, int j) throws IOException {
      return this.f.readBytes(filechannel, i, j);
   }

   public ByteBuf skipBytes(int i) {
      return this.f.skipBytes(i);
   }

   public ByteBuf writeBoolean(boolean flag) {
      return this.f.writeBoolean(flag);
   }

   public ByteBuf writeByte(int i) {
      return this.f.writeByte(i);
   }

   public ByteBuf writeShort(int i) {
      return this.f.writeShort(i);
   }

   public ByteBuf writeShortLE(int i) {
      return this.f.writeShortLE(i);
   }

   public ByteBuf writeMedium(int i) {
      return this.f.writeMedium(i);
   }

   public ByteBuf writeMediumLE(int i) {
      return this.f.writeMediumLE(i);
   }

   public ByteBuf writeInt(int i) {
      return this.f.writeInt(i);
   }

   public ByteBuf writeIntLE(int i) {
      return this.f.writeIntLE(i);
   }

   public ByteBuf writeLong(long i) {
      return this.f.writeLong(i);
   }

   public ByteBuf writeLongLE(long i) {
      return this.f.writeLongLE(i);
   }

   public ByteBuf writeChar(int i) {
      return this.f.writeChar(i);
   }

   public ByteBuf writeFloat(float f) {
      return this.f.writeFloat(f);
   }

   public ByteBuf writeDouble(double d0) {
      return this.f.writeDouble(d0);
   }

   public ByteBuf writeBytes(ByteBuf bytebuf) {
      return this.f.writeBytes(bytebuf);
   }

   public ByteBuf writeBytes(ByteBuf bytebuf, int i) {
      return this.f.writeBytes(bytebuf, i);
   }

   public ByteBuf writeBytes(ByteBuf bytebuf, int i, int j) {
      return this.f.writeBytes(bytebuf, i, j);
   }

   public ByteBuf writeBytes(byte[] abyte) {
      return this.f.writeBytes(abyte);
   }

   public ByteBuf writeBytes(byte[] abyte, int i, int j) {
      return this.f.writeBytes(abyte, i, j);
   }

   public ByteBuf writeBytes(ByteBuffer bytebuffer) {
      return this.f.writeBytes(bytebuffer);
   }

   public int writeBytes(InputStream inputstream, int i) throws IOException {
      return this.f.writeBytes(inputstream, i);
   }

   public int writeBytes(ScatteringByteChannel scatteringbytechannel, int i) throws IOException {
      return this.f.writeBytes(scatteringbytechannel, i);
   }

   public int writeBytes(FileChannel filechannel, long i, int j) throws IOException {
      return this.f.writeBytes(filechannel, i, j);
   }

   public ByteBuf writeZero(int i) {
      return this.f.writeZero(i);
   }

   public int writeCharSequence(CharSequence charsequence, Charset charset) {
      return this.f.writeCharSequence(charsequence, charset);
   }

   public int indexOf(int i, int j, byte b0) {
      return this.f.indexOf(i, j, b0);
   }

   public int bytesBefore(byte b0) {
      return this.f.bytesBefore(b0);
   }

   public int bytesBefore(int i, byte b0) {
      return this.f.bytesBefore(i, b0);
   }

   public int bytesBefore(int i, int j, byte b0) {
      return this.f.bytesBefore(i, j, b0);
   }

   public int forEachByte(ByteProcessor byteprocessor) {
      return this.f.forEachByte(byteprocessor);
   }

   public int forEachByte(int i, int j, ByteProcessor byteprocessor) {
      return this.f.forEachByte(i, j, byteprocessor);
   }

   public int forEachByteDesc(ByteProcessor byteprocessor) {
      return this.f.forEachByteDesc(byteprocessor);
   }

   public int forEachByteDesc(int i, int j, ByteProcessor byteprocessor) {
      return this.f.forEachByteDesc(i, j, byteprocessor);
   }

   public ByteBuf copy() {
      return this.f.copy();
   }

   public ByteBuf copy(int i, int j) {
      return this.f.copy(i, j);
   }

   public ByteBuf slice() {
      return this.f.slice();
   }

   public ByteBuf retainedSlice() {
      return this.f.retainedSlice();
   }

   public ByteBuf slice(int i, int j) {
      return this.f.slice(i, j);
   }

   public ByteBuf retainedSlice(int i, int j) {
      return this.f.retainedSlice(i, j);
   }

   public ByteBuf duplicate() {
      return this.f.duplicate();
   }

   public ByteBuf retainedDuplicate() {
      return this.f.retainedDuplicate();
   }

   public int nioBufferCount() {
      return this.f.nioBufferCount();
   }

   public ByteBuffer nioBuffer() {
      return this.f.nioBuffer();
   }

   public ByteBuffer nioBuffer(int i, int j) {
      return this.f.nioBuffer(i, j);
   }

   public ByteBuffer internalNioBuffer(int i, int j) {
      return this.f.internalNioBuffer(i, j);
   }

   public ByteBuffer[] nioBuffers() {
      return this.f.nioBuffers();
   }

   public ByteBuffer[] nioBuffers(int i, int j) {
      return this.f.nioBuffers(i, j);
   }

   public boolean hasArray() {
      return this.f.hasArray();
   }

   public byte[] array() {
      return this.f.array();
   }

   public int arrayOffset() {
      return this.f.arrayOffset();
   }

   public boolean hasMemoryAddress() {
      return this.f.hasMemoryAddress();
   }

   public long memoryAddress() {
      return this.f.memoryAddress();
   }

   public String toString(Charset charset) {
      return this.f.toString(charset);
   }

   public String toString(int i, int j, Charset charset) {
      return this.f.toString(i, j, charset);
   }

   public int hashCode() {
      return this.f.hashCode();
   }

   public boolean equals(Object object) {
      return this.f.equals(object);
   }

   public int compareTo(ByteBuf bytebuf) {
      return this.f.compareTo(bytebuf);
   }

   public String toString() {
      return this.f.toString();
   }

   public ByteBuf retain(int i) {
      return this.f.retain(i);
   }

   public ByteBuf retain() {
      return this.f.retain();
   }

   public ByteBuf touch() {
      return this.f.touch();
   }

   public ByteBuf touch(Object object) {
      return this.f.touch(object);
   }

   public int refCnt() {
      return this.f.refCnt();
   }

   public boolean release() {
      return this.f.release();
   }

   public boolean release(int i) {
      return this.f.release(i);
   }

   @FunctionalInterface
   public interface a<T> extends Function<PacketDataSerializer, T> {
      default PacketDataSerializer.a<Optional<T>> asOptional() {
         return packetdataserializer -> packetdataserializer.b(this);
      }
   }

   @FunctionalInterface
   public interface b<T> extends BiConsumer<PacketDataSerializer, T> {
      default PacketDataSerializer.b<Optional<T>> asOptional() {
         return (packetdataserializer, optional) -> packetdataserializer.a(optional, this);
      }
   }
}
