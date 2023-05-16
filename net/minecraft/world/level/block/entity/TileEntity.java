package net.minecraft.world.level.block.entity;

import com.mojang.logging.LogUtils;
import javax.annotation.Nullable;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_19_R3.SpigotTimings;
import org.bukkit.craftbukkit.v1_19_R3.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_19_R3.persistence.CraftPersistentDataTypeRegistry;
import org.bukkit.inventory.InventoryHolder;
import org.slf4j.Logger;
import org.spigotmc.CustomTimingsHandler;

public abstract class TileEntity {
   public CustomTimingsHandler tickTimer = SpigotTimings.getTileEntityTimings(this);
   private static final CraftPersistentDataTypeRegistry DATA_TYPE_REGISTRY = new CraftPersistentDataTypeRegistry();
   public CraftPersistentDataContainer persistentDataContainer;
   private static final Logger c = LogUtils.getLogger();
   private final TileEntityTypes<?> d;
   @Nullable
   protected World o;
   protected final BlockPosition p;
   protected boolean q;
   private IBlockData e;

   public TileEntity(TileEntityTypes<?> tileentitytypes, BlockPosition blockposition, IBlockData iblockdata) {
      this.d = tileentitytypes;
      this.p = blockposition.i();
      this.e = iblockdata;
   }

   public static BlockPosition c(NBTTagCompound nbttagcompound) {
      return new BlockPosition(nbttagcompound.h("x"), nbttagcompound.h("y"), nbttagcompound.h("z"));
   }

   @Nullable
   public World k() {
      return this.o;
   }

   public void a(World world) {
      this.o = world;
   }

   public boolean l() {
      return this.o != null;
   }

   public void a(NBTTagCompound nbttagcompound) {
      this.persistentDataContainer = new CraftPersistentDataContainer(DATA_TYPE_REGISTRY);
      NBTBase persistentDataTag = nbttagcompound.c("PublicBukkitValues");
      if (persistentDataTag instanceof NBTTagCompound) {
         this.persistentDataContainer.putAll((NBTTagCompound)persistentDataTag);
      }
   }

   protected void b(NBTTagCompound nbttagcompound) {
   }

   public final NBTTagCompound m() {
      NBTTagCompound nbttagcompound = this.o();
      this.e(nbttagcompound);
      return nbttagcompound;
   }

   public final NBTTagCompound n() {
      NBTTagCompound nbttagcompound = this.o();
      this.d(nbttagcompound);
      return nbttagcompound;
   }

   public final NBTTagCompound o() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      this.b(nbttagcompound);
      if (this.persistentDataContainer != null && !this.persistentDataContainer.isEmpty()) {
         nbttagcompound.a("PublicBukkitValues", this.persistentDataContainer.toTagCompound());
      }

      return nbttagcompound;
   }

   private void d(NBTTagCompound nbttagcompound) {
      MinecraftKey minecraftkey = TileEntityTypes.a(this.u());
      if (minecraftkey == null) {
         throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
      } else {
         nbttagcompound.a("id", minecraftkey.toString());
      }
   }

   public static void a(NBTTagCompound nbttagcompound, TileEntityTypes<?> tileentitytypes) {
      nbttagcompound.a("id", TileEntityTypes.a(tileentitytypes).toString());
   }

   public void e(ItemStack itemstack) {
      ItemBlock.a(itemstack, this.u(), this.o());
   }

   private void e(NBTTagCompound nbttagcompound) {
      this.d(nbttagcompound);
      nbttagcompound.a("x", this.p.u());
      nbttagcompound.a("y", this.p.v());
      nbttagcompound.a("z", this.p.w());
   }

   @Nullable
   public static TileEntity a(BlockPosition blockposition, IBlockData iblockdata, NBTTagCompound nbttagcompound) {
      String s = nbttagcompound.l("id");
      MinecraftKey minecraftkey = MinecraftKey.a(s);
      if (minecraftkey == null) {
         c.error("Block entity has invalid type: {}", s);
         return null;
      } else {
         return BuiltInRegistries.l.b(minecraftkey).map(tileentitytypes -> {
            try {
               return tileentitytypes.a(blockposition, iblockdata);
            } catch (Throwable var5) {
               c.error("Failed to create block entity {}", s, var5);
               return null;
            }
         }).map(tileentity -> {
            try {
               tileentity.a(nbttagcompound);
               return tileentity;
            } catch (Throwable var4x) {
               c.error("Failed to load data for block entity {}", s, var4x);
               return null;
            }
         }).orElseGet(() -> {
            c.warn("Skipping BlockEntity with id {}", s);
            return null;
         });
      }
   }

   public void e() {
      if (this.o != null) {
         a(this.o, this.p, this.e);
      }
   }

   protected static void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      world.p(blockposition);
      if (!iblockdata.h()) {
         world.c(blockposition, iblockdata.b());
      }
   }

   public BlockPosition p() {
      return this.p;
   }

   public IBlockData q() {
      return this.e;
   }

   @Nullable
   public Packet<PacketListenerPlayOut> h() {
      return null;
   }

   public NBTTagCompound aq_() {
      return new NBTTagCompound();
   }

   public boolean r() {
      return this.q;
   }

   public void ar_() {
      this.q = true;
   }

   public void s() {
      this.q = false;
   }

   public boolean a_(int i, int j) {
      return false;
   }

   public void a(CrashReportSystemDetails crashreportsystemdetails) {
      crashreportsystemdetails.a("Name", () -> {
         MinecraftKey minecraftkey = BuiltInRegistries.l.b(this.u());
         return minecraftkey + " // " + this.getClass().getCanonicalName();
      });
      if (this.o != null) {
         CrashReportSystemDetails.a(crashreportsystemdetails, this.o, this.p, this.q());
         CrashReportSystemDetails.a(crashreportsystemdetails, this.o, this.p, this.o.a_(this.p));
      }
   }

   public boolean t() {
      return false;
   }

   public TileEntityTypes<?> u() {
      return this.d;
   }

   @Deprecated
   public void b(IBlockData iblockdata) {
      this.e = iblockdata;
   }

   public InventoryHolder getOwner() {
      if (this.o == null) {
         return null;
      } else {
         BlockState state = this.o.getWorld().getBlockAt(this.p.u(), this.p.v(), this.p.w()).getState();
         return state instanceof InventoryHolder ? (InventoryHolder)state : null;
      }
   }
}
