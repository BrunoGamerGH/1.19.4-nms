package net.minecraft.world.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.SystemUtils;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.commands.arguments.blocks.ArgumentBlock;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.datafix.fixes.DataConverterTypes;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.decoration.EntityItemFrame;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentDurability;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockSapling;
import net.minecraft.world.level.block.BlockTileEntity;
import net.minecraft.world.level.block.BlockWitherSkull;
import net.minecraft.world.level.block.SoundEffectType;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityJukeBox;
import net.minecraft.world.level.block.entity.TileEntitySign;
import net.minecraft.world.level.block.entity.TileEntitySkull;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.slf4j.Logger;

public final class ItemStack {
   public static final Codec<ItemStack> a = RecordCodecBuilder.create(
      instance -> instance.group(
               BuiltInRegistries.i.q().fieldOf("id").forGetter(itemstack -> itemstack.u),
               Codec.INT.fieldOf("Count").forGetter(itemstack -> itemstack.s),
               NBTTagCompound.a.optionalFieldOf("tag").forGetter(itemstack -> Optional.ofNullable(itemstack.v))
            )
            .apply(instance, ItemStack::new)
   );
   private static final Logger j = LogUtils.getLogger();
   public static final ItemStack b = new ItemStack(null);
   public static final DecimalFormat c = SystemUtils.a(
      new DecimalFormat("#.##"), decimalformat -> decimalformat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT))
   );
   public static final String d = "Enchantments";
   public static final String e = "display";
   public static final String f = "Name";
   public static final String g = "Lore";
   public static final String h = "Damage";
   public static final String i = "color";
   private static final String k = "Unbreakable";
   private static final String l = "RepairCost";
   private static final String m = "CanDestroy";
   private static final String n = "CanPlaceOn";
   private static final String o = "HideFlags";
   private static final IChatBaseComponent p = IChatBaseComponent.c("item.disabled").a(EnumChatFormat.m);
   private static final int q = 0;
   private static final ChatModifier r = ChatModifier.a.a(EnumChatFormat.f).b(true);
   private int s;
   private int t;
   @Deprecated
   private Item u;
   @Nullable
   private NBTTagCompound v;
   private boolean w;
   @Nullable
   private Entity x;
   @Nullable
   private AdventureModeCheck y;
   @Nullable
   private AdventureModeCheck z;

   public Optional<TooltipComponent> a() {
      return this.c().h(this);
   }

   public ItemStack(IMaterial imaterial) {
      this(imaterial, 1);
   }

   public ItemStack(Holder<Item> holder) {
      this(holder.a(), 1);
   }

   private ItemStack(IMaterial imaterial, int i, Optional<NBTTagCompound> optional) {
      this(imaterial, i);
      optional.ifPresent(this::c);
   }

   public ItemStack(Holder<Item> holder, int i) {
      this(holder.a(), i);
   }

   public ItemStack(IMaterial imaterial, int i) {
      this.u = imaterial == null ? null : imaterial.k();
      this.s = i;
      if (this.u != null && this.u.o()) {
         this.b(this.j());
      }

      this.O();
   }

   public void convertStack(int version) {
      if (version > 0 && version < CraftMagicNumbers.INSTANCE.getDataVersion()) {
         NBTTagCompound savedStack = new NBTTagCompound();
         this.b(savedStack);
         savedStack = (NBTTagCompound)MinecraftServer.getServer()
            .L
            .update(DataConverterTypes.m, new Dynamic(DynamicOpsNBT.a, savedStack), version, CraftMagicNumbers.INSTANCE.getDataVersion())
            .getValue();
         this.load(savedStack);
      }
   }

   private void O() {
      if (this.w && this == b) {
         throw new AssertionError("TRAP");
      } else {
         this.w = false;
         this.w = this.b();
      }
   }

   private void load(NBTTagCompound nbttagcompound) {
      this.u = BuiltInRegistries.i.a(new MinecraftKey(nbttagcompound.l("id")));
      this.s = nbttagcompound.f("Count");
      if (nbttagcompound.b("tag", 10)) {
         this.v = nbttagcompound.p("tag").h();
         this.c().b(this.v);
      }

      if (this.c().o()) {
         this.b(this.j());
      }
   }

   private ItemStack(NBTTagCompound nbttagcompound) {
      this.load(nbttagcompound);
      this.O();
   }

   public static ItemStack a(NBTTagCompound nbttagcompound) {
      try {
         return new ItemStack(nbttagcompound);
      } catch (RuntimeException var2) {
         j.debug("Tried to load invalid item: {}", nbttagcompound, var2);
         return b;
      }
   }

   public boolean b() {
      return this == b ? true : (this.c() != null && !this.a(Items.a) ? this.s <= 0 : true);
   }

   public boolean a(FeatureFlagSet featureflagset) {
      return this.b() || this.c().a(featureflagset);
   }

   public ItemStack a(int i) {
      int j = Math.min(i, this.s);
      ItemStack itemstack = this.o();
      itemstack.f(j);
      this.h(j);
      return itemstack;
   }

   public Item c() {
      return this.w ? Items.a : this.u;
   }

   public Holder<Item> d() {
      return this.c().j();
   }

   public boolean a(TagKey<Item> tagkey) {
      return this.c().j().a(tagkey);
   }

   public boolean a(Item item) {
      return this.c() == item;
   }

   public boolean a(Predicate<Holder<Item>> predicate) {
      return predicate.test(this.c().j());
   }

   public boolean a(Holder<Item> holder) {
      return this.c().j() == holder;
   }

   public Stream<TagKey<Item>> e() {
      return this.c().j().c();
   }

   public EnumInteractionResult useOn(ItemActionContext itemactioncontext, EnumHand enumhand) {
      EntityHuman entityhuman = itemactioncontext.o();
      BlockPosition blockposition = itemactioncontext.a();
      ShapeDetectorBlock shapedetectorblock = new ShapeDetectorBlock(itemactioncontext.q(), blockposition, false);
      if (entityhuman != null && !entityhuman.fK().e && !this.a(itemactioncontext.q().u_().d(Registries.e), shapedetectorblock)) {
         return EnumInteractionResult.d;
      } else {
         NBTTagCompound oldData = this.getTagClone();
         int oldCount = this.K();
         WorldServer world = (WorldServer)itemactioncontext.q();
         if (!(this.c() instanceof ItemBucket) && !(this.c() instanceof SolidBucketItem)) {
            world.captureBlockStates = true;
            if (this.c() == Items.qG) {
               world.captureTreeGeneration = true;
            }
         }

         Item item = this.c();
         EnumInteractionResult enuminteractionresult = item.a(itemactioncontext);
         NBTTagCompound newData = this.getTagClone();
         int newCount = this.K();
         this.f(oldCount);
         this.setTagClone(oldData);
         world.captureBlockStates = false;
         if (enuminteractionresult.a() && world.captureTreeGeneration && world.capturedBlockStates.size() > 0) {
            world.captureTreeGeneration = false;
            Location location = new Location(world.getWorld(), (double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w());
            TreeType treeType = BlockSapling.treeType;
            BlockSapling.treeType = null;
            List<CraftBlockState> blocks = new ArrayList<>(world.capturedBlockStates.values());
            world.capturedBlockStates.clear();
            StructureGrowEvent structureEvent = null;
            if (treeType != null) {
               boolean isBonemeal = this.c() == Items.qG;
               structureEvent = new StructureGrowEvent(location, treeType, isBonemeal, (Player)entityhuman.getBukkitEntity(), blocks);
               Bukkit.getPluginManager().callEvent(structureEvent);
            }

            BlockFertilizeEvent fertilizeEvent = new BlockFertilizeEvent(CraftBlock.at(world, blockposition), (Player)entityhuman.getBukkitEntity(), blocks);
            fertilizeEvent.setCancelled(structureEvent != null && structureEvent.isCancelled());
            Bukkit.getPluginManager().callEvent(fertilizeEvent);
            if (!fertilizeEvent.isCancelled()) {
               if (this.K() == oldCount && Objects.equals(this.v, oldData)) {
                  this.c(newData);
                  this.f(newCount);
               }

               for(CraftBlockState blockstate : blocks) {
                  world.a(blockstate.getPosition(), blockstate.getHandle(), blockstate.getFlag());
               }

               entityhuman.b(StatisticList.c.b(item));
            }

            ItemSign.openSign = null;
            return enuminteractionresult;
         } else {
            world.captureTreeGeneration = false;
            if (entityhuman != null && enuminteractionresult.c()) {
               BlockPlaceEvent placeEvent = null;
               List<BlockState> blocks = new ArrayList<>(world.capturedBlockStates.values());
               world.capturedBlockStates.clear();
               if (blocks.size() > 1) {
                  placeEvent = CraftEventFactory.callBlockMultiPlaceEvent(
                     world, entityhuman, enumhand, blocks, blockposition.u(), blockposition.v(), blockposition.w()
                  );
               } else if (blocks.size() == 1) {
                  placeEvent = CraftEventFactory.callBlockPlaceEvent(
                     world, entityhuman, enumhand, (BlockState)blocks.get(0), blockposition.u(), blockposition.v(), blockposition.w()
                  );
               }

               if (placeEvent != null && (placeEvent.isCancelled() || !placeEvent.canBuild())) {
                  enuminteractionresult = EnumInteractionResult.e;
                  placeEvent.getPlayer().updateInventory();
                  world.preventPoiUpdated = true;

                  for(BlockState blockstate : blocks) {
                     blockstate.update(true, false);
                  }

                  world.preventPoiUpdated = false;
                  BlockPosition placedPos = ((CraftBlock)placeEvent.getBlock()).getPosition();

                  EnumDirection[] var44;
                  for(EnumDirection dir : var44 = EnumDirection.values()) {
                     ((EntityPlayer)entityhuman).b.a(new PacketPlayOutBlockChange(world, placedPos.a(dir)));
                  }

                  ItemSign.openSign = null;
               } else {
                  if (this.K() == oldCount && Objects.equals(this.v, oldData)) {
                     this.c(newData);
                     this.f(newCount);
                  }

                  for(Entry<BlockPosition, TileEntity> e : world.capturedTileEntities.entrySet()) {
                     world.a(e.getValue());
                  }

                  for(BlockState blockstate : blocks) {
                     int updateFlag = ((CraftBlockState)blockstate).getFlag();
                     IBlockData oldBlock = ((CraftBlockState)blockstate).getHandle();
                     BlockPosition newblockposition = ((CraftBlockState)blockstate).getPosition();
                     IBlockData block = world.a_(newblockposition);
                     if (!(block.b() instanceof BlockTileEntity)) {
                        block.b().b(block, world, newblockposition, oldBlock, true);
                     }

                     world.notifyAndUpdatePhysics(newblockposition, null, oldBlock, block, world.a_(newblockposition), updateFlag, 512);
                  }

                  if (this.u instanceof ItemRecord) {
                     TileEntity tileentity = world.c_(blockposition);
                     if (tileentity instanceof TileEntityJukeBox tileentityjukebox) {
                        ItemStack record = this.o();
                        if (!record.b()) {
                           record.f(1);
                        }

                        tileentityjukebox.b(record);
                        world.a(GameEvent.c, blockposition, GameEvent.a.a(entityhuman, world.a_(blockposition)));
                     }

                     this.h(1);
                     entityhuman.a(StatisticList.al);
                  }

                  if (this.u == Items.to) {
                     BlockPosition bp = blockposition;
                     if (!world.a_(blockposition).d().e()) {
                        if (!world.a_(blockposition).d().b()) {
                           bp = null;
                        } else {
                           bp = blockposition.a(itemactioncontext.k());
                        }
                     }

                     if (bp != null) {
                        TileEntity te = world.c_(bp);
                        if (te instanceof TileEntitySkull) {
                           BlockWitherSkull.a(world, bp, (TileEntitySkull)te);
                        }
                     }
                  }

                  if (this.u instanceof ItemSign && ItemSign.openSign != null) {
                     try {
                        entityhuman.a((TileEntitySign)world.c_(ItemSign.openSign));
                     } finally {
                        ItemSign.openSign = null;
                     }
                  }

                  if (this.u instanceof ItemBlock) {
                     SoundEffectType soundeffecttype = ((ItemBlock)this.u).e().m(null);
                     world.a(entityhuman, blockposition, soundeffecttype.e(), SoundCategory.e, (soundeffecttype.a() + 1.0F) / 2.0F, soundeffecttype.b() * 0.8F);
                  }

                  entityhuman.b(StatisticList.c.b(item));
               }
            }

            world.capturedTileEntities.clear();
            world.capturedBlockStates.clear();
            return enuminteractionresult;
         }
      }
   }

   public float a(IBlockData iblockdata) {
      return this.c().a(this, iblockdata);
   }

   public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
      return this.c().a(world, entityhuman, enumhand);
   }

   public ItemStack a(World world, EntityLiving entityliving) {
      return this.c().a(this, world, entityliving);
   }

   public NBTTagCompound b(NBTTagCompound nbttagcompound) {
      MinecraftKey minecraftkey = BuiltInRegistries.i.b(this.c());
      nbttagcompound.a("id", minecraftkey == null ? "minecraft:air" : minecraftkey.toString());
      nbttagcompound.a("Count", (byte)this.s);
      if (this.v != null) {
         nbttagcompound.a("tag", this.v.h());
      }

      return nbttagcompound;
   }

   public int f() {
      return this.c().l();
   }

   public boolean g() {
      return this.f() > 1 && (!this.h() || !this.i());
   }

   public boolean h() {
      if (!this.w && this.c().n() > 0) {
         NBTTagCompound nbttagcompound = this.u();
         return nbttagcompound == null || !nbttagcompound.q("Unbreakable");
      } else {
         return false;
      }
   }

   public boolean i() {
      return this.h() && this.j() > 0;
   }

   public int j() {
      return this.v == null ? 0 : this.v.h("Damage");
   }

   public void b(int i) {
      this.v().a("Damage", Math.max(0, i));
   }

   public int k() {
      return this.c().n();
   }

   public boolean a(int i, RandomSource randomsource, @Nullable EntityPlayer entityplayer) {
      if (!this.h()) {
         return false;
      } else {
         if (i > 0) {
            int j = EnchantmentManager.a(Enchantments.w, this);
            int k = 0;

            for(int l = 0; j > 0 && l < i; ++l) {
               if (EnchantmentDurability.a(this, j, randomsource)) {
                  ++k;
               }
            }

            i -= k;
            if (entityplayer != null) {
               PlayerItemDamageEvent event = new PlayerItemDamageEvent(entityplayer.getBukkitEntity(), CraftItemStack.asCraftMirror(this), i);
               event.getPlayer().getServer().getPluginManager().callEvent(event);
               if (i != event.getDamage() || event.isCancelled()) {
                  event.getPlayer().updateInventory();
               }

               if (event.isCancelled()) {
                  return false;
               }

               i = event.getDamage();
            }

            if (i <= 0) {
               return false;
            }
         }

         if (entityplayer != null && i != 0) {
            CriterionTriggers.t.a(entityplayer, this, this.j() + i);
         }

         int j = this.j() + i;
         this.b(j);
         return j >= this.k();
      }
   }

   public <T extends EntityLiving> void a(int i, T t0, Consumer<T> consumer) {
      if (!t0.H.B
         && (!(t0 instanceof EntityHuman) || !((EntityHuman)t0).fK().d)
         && this.h()
         && this.a(i, t0.dZ(), t0 instanceof EntityPlayer ? (EntityPlayer)t0 : null)) {
         consumer.accept(t0);
         Item item = this.c();
         if (this.s == 1 && t0 instanceof EntityHuman) {
            CraftEventFactory.callPlayerItemBreakEvent((EntityHuman)t0, this);
         }

         this.h(1);
         if (t0 instanceof EntityHuman) {
            ((EntityHuman)t0).b(StatisticList.d.b(item));
         }

         this.b(0);
      }
   }

   public boolean l() {
      return this.u.e(this);
   }

   public int m() {
      return this.u.f(this);
   }

   public int n() {
      return this.u.g(this);
   }

   public boolean a(Slot slot, ClickAction clickaction, EntityHuman entityhuman) {
      return this.c().a(this, slot, clickaction, entityhuman);
   }

   public boolean a(ItemStack itemstack, Slot slot, ClickAction clickaction, EntityHuman entityhuman, SlotAccess slotaccess) {
      return this.c().a(this, itemstack, slot, clickaction, entityhuman, slotaccess);
   }

   public void a(EntityLiving entityliving, EntityHuman entityhuman) {
      Item item = this.c();
      if (item.a(this, entityliving, entityhuman)) {
         entityhuman.b(StatisticList.c.b(item));
      }
   }

   public void a(World world, IBlockData iblockdata, BlockPosition blockposition, EntityHuman entityhuman) {
      Item item = this.c();
      if (item.a(this, world, iblockdata, blockposition, entityhuman)) {
         entityhuman.b(StatisticList.c.b(item));
      }
   }

   public boolean b(IBlockData iblockdata) {
      return this.c().a_(iblockdata);
   }

   public EnumInteractionResult a(EntityHuman entityhuman, EntityLiving entityliving, EnumHand enumhand) {
      return this.c().a(this, entityhuman, entityliving, enumhand);
   }

   public ItemStack o() {
      if (this.b()) {
         return b;
      } else {
         ItemStack itemstack = new ItemStack(this.c(), this.s);
         itemstack.e(this.J());
         if (this.v != null) {
            itemstack.v = this.v.h();
         }

         return itemstack;
      }
   }

   public ItemStack c(int i) {
      ItemStack itemstack = this.o();
      itemstack.f(i);
      return itemstack;
   }

   public static boolean a(ItemStack itemstack, ItemStack itemstack1) {
      return itemstack.b() && itemstack1.b()
         ? true
         : (
            itemstack.b() || itemstack1.b()
               ? false
               : (itemstack.v == null && itemstack1.v != null ? false : itemstack.v == null || itemstack.v.equals(itemstack1.v))
         );
   }

   public static boolean b(ItemStack itemstack, ItemStack itemstack1) {
      return itemstack.b() && itemstack1.b() ? true : (!itemstack.b() && !itemstack1.b() ? itemstack.b(itemstack1) : false);
   }

   private boolean b(ItemStack itemstack) {
      return this.s != itemstack.s
         ? false
         : (!this.a(itemstack.c()) ? false : (this.v == null && itemstack.v != null ? false : this.v == null || this.v.equals(itemstack.v)));
   }

   public static boolean c(ItemStack itemstack, ItemStack itemstack1) {
      return itemstack == itemstack1 ? true : (!itemstack.b() && !itemstack1.b() ? itemstack.a(itemstack1) : false);
   }

   public boolean a(ItemStack itemstack) {
      return !itemstack.b() && this.a(itemstack.c());
   }

   public static boolean d(ItemStack itemstack, ItemStack itemstack1) {
      return itemstack.a(itemstack1.c()) && a(itemstack, itemstack1);
   }

   public String p() {
      return this.c().j(this);
   }

   @Override
   public String toString() {
      return this.s + " " + this.c();
   }

   public void a(World world, Entity entity, int i, boolean flag) {
      if (this.t > 0) {
         --this.t;
      }

      if (this.c() != null) {
         this.c().a(this, world, entity, i, flag);
      }
   }

   public void a(World world, EntityHuman entityhuman, int i) {
      entityhuman.a(StatisticList.b.b(this.c()), i);
      this.c().b(this, world, entityhuman);
   }

   public int q() {
      return this.c().b(this);
   }

   public EnumAnimation r() {
      return this.c().c(this);
   }

   public void a(World world, EntityLiving entityliving, int i) {
      this.c().a(this, world, entityliving, i);
   }

   public boolean s() {
      return this.c().l(this);
   }

   public boolean t() {
      return !this.w && this.v != null && !this.v.g();
   }

   @Nullable
   public NBTTagCompound u() {
      return this.v;
   }

   @Nullable
   private NBTTagCompound getTagClone() {
      return this.v == null ? null : this.v.h();
   }

   private void setTagClone(@Nullable NBTTagCompound nbtttagcompound) {
      this.c(nbtttagcompound == null ? null : nbtttagcompound.h());
   }

   public NBTTagCompound v() {
      if (this.v == null) {
         this.c(new NBTTagCompound());
      }

      return this.v;
   }

   public NBTTagCompound a(String s) {
      if (this.v != null && this.v.b(s, 10)) {
         return this.v.p(s);
      } else {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         this.a(s, nbttagcompound);
         return nbttagcompound;
      }
   }

   @Nullable
   public NBTTagCompound b(String s) {
      return this.v != null && this.v.b(s, 10) ? this.v.p(s) : null;
   }

   public void c(String s) {
      if (this.v != null && this.v.e(s)) {
         this.v.r(s);
         if (this.v.g()) {
            this.v = null;
         }
      }
   }

   public NBTTagList w() {
      return this.v != null ? this.v.c("Enchantments", 10) : new NBTTagList();
   }

   public void c(@Nullable NBTTagCompound nbttagcompound) {
      this.v = nbttagcompound;
      if (this.c().o()) {
         this.b(this.j());
      }

      if (nbttagcompound != null) {
         this.c().b(nbttagcompound);
      }
   }

   public IChatBaseComponent x() {
      NBTTagCompound nbttagcompound = this.b("display");
      if (nbttagcompound != null && nbttagcompound.b("Name", 8)) {
         try {
            IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.ChatSerializer.a(nbttagcompound.l("Name"));
            if (ichatmutablecomponent != null) {
               return ichatmutablecomponent;
            }

            nbttagcompound.r("Name");
         } catch (Exception var3) {
            nbttagcompound.r("Name");
         }
      }

      return this.c().m(this);
   }

   public ItemStack a(@Nullable IChatBaseComponent ichatbasecomponent) {
      NBTTagCompound nbttagcompound = this.a("display");
      if (ichatbasecomponent != null) {
         nbttagcompound.a("Name", IChatBaseComponent.ChatSerializer.a(ichatbasecomponent));
      } else {
         nbttagcompound.r("Name");
      }

      return this;
   }

   public void y() {
      NBTTagCompound nbttagcompound = this.b("display");
      if (nbttagcompound != null) {
         nbttagcompound.r("Name");
         if (nbttagcompound.g()) {
            this.c("display");
         }
      }

      if (this.v != null && this.v.g()) {
         this.v = null;
      }
   }

   public boolean z() {
      NBTTagCompound nbttagcompound = this.b("display");
      return nbttagcompound != null && nbttagcompound.b("Name", 8);
   }

   public List<IChatBaseComponent> a(@Nullable EntityHuman entityhuman, TooltipFlag tooltipflag) {
      List<IChatBaseComponent> list = Lists.newArrayList();
      IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.h().b(this.x()).a(this.B().e);
      if (this.z()) {
         ichatmutablecomponent.a(EnumChatFormat.u);
      }

      list.add(ichatmutablecomponent);
      if (!tooltipflag.a() && !this.z() && this.a(Items.rb)) {
         Integer integer = ItemWorldMap.d(this);
         if (integer != null) {
            list.add(IChatBaseComponent.b("#" + integer).a(EnumChatFormat.h));
         }
      }

      int i = this.P();
      if (a(i, ItemStack.HideFlags.f)) {
         this.c().a(this, entityhuman == null ? null : entityhuman.H, list, tooltipflag);
      }

      if (this.t()) {
         if (a(i, ItemStack.HideFlags.h) && entityhuman != null) {
            ArmorTrim.a(this, entityhuman.H.u_(), list);
         }

         if (a(i, ItemStack.HideFlags.a)) {
            a(list, this.w());
         }

         if (this.v.b("display", 10)) {
            NBTTagCompound nbttagcompound = this.v.p("display");
            if (a(i, ItemStack.HideFlags.g) && nbttagcompound.b("color", 99)) {
               if (tooltipflag.a()) {
                  list.add(IChatBaseComponent.a("item.color", String.format(Locale.ROOT, "#%06X", nbttagcompound.h("color"))).a(EnumChatFormat.h));
               } else {
                  list.add(IChatBaseComponent.c("item.dyed").a(EnumChatFormat.h, EnumChatFormat.u));
               }
            }

            if (nbttagcompound.d("Lore") == 9) {
               NBTTagList nbttaglist = nbttagcompound.c("Lore", 8);

               for(int j = 0; j < nbttaglist.size(); ++j) {
                  String s = nbttaglist.j(j);

                  try {
                     IChatMutableComponent ichatmutablecomponent1 = IChatBaseComponent.ChatSerializer.a(s);
                     if (ichatmutablecomponent1 != null) {
                        list.add(ChatComponentUtils.a(ichatmutablecomponent1, r));
                     }
                  } catch (Exception var19) {
                     nbttagcompound.r("Lore");
                  }
               }
            }
         }
      }

      if (a(i, ItemStack.HideFlags.b)) {
         for(EnumItemSlot enumitemslot : EnumItemSlot.values()) {
            Multimap<AttributeBase, AttributeModifier> multimap = this.a(enumitemslot);
            if (!multimap.isEmpty()) {
               list.add(CommonComponents.a);
               list.add(IChatBaseComponent.c("item.modifiers." + enumitemslot.d()).a(EnumChatFormat.h));

               for(Entry<AttributeBase, AttributeModifier> entry : multimap.entries()) {
                  AttributeModifier attributemodifier = entry.getValue();
                  double d0 = attributemodifier.d();
                  boolean flag = false;
                  if (entityhuman != null) {
                     if (attributemodifier.a() == Item.m) {
                        d0 += entityhuman.c(GenericAttributes.f);
                        d0 += (double)EnchantmentManager.a(this, EnumMonsterType.a);
                        flag = true;
                     } else if (attributemodifier.a() == Item.n) {
                        d0 += entityhuman.c(GenericAttributes.h);
                        flag = true;
                     }
                  }

                  double d1;
                  if (attributemodifier.c() == AttributeModifier.Operation.b || attributemodifier.c() == AttributeModifier.Operation.c) {
                     d1 = d0 * 100.0;
                  } else if (entry.getKey().equals(GenericAttributes.c)) {
                     d1 = d0 * 10.0;
                  } else {
                     d1 = d0;
                  }

                  if (flag) {
                     list.add(
                        CommonComponents.a()
                           .b(
                              IChatBaseComponent.a(
                                 "attribute.modifier.equals." + attributemodifier.c().a(), c.format(d1), IChatBaseComponent.c(entry.getKey().c())
                              )
                           )
                           .a(EnumChatFormat.c)
                     );
                  } else if (d0 > 0.0) {
                     list.add(
                        IChatBaseComponent.a("attribute.modifier.plus." + attributemodifier.c().a(), c.format(d1), IChatBaseComponent.c(entry.getKey().c()))
                           .a(EnumChatFormat.j)
                     );
                  } else if (d0 < 0.0) {
                     d1 *= -1.0;
                     list.add(
                        IChatBaseComponent.a("attribute.modifier.take." + attributemodifier.c().a(), c.format(d1), IChatBaseComponent.c(entry.getKey().c()))
                           .a(EnumChatFormat.m)
                     );
                  }
               }
            }
         }
      }

      if (this.t()) {
         if (a(i, ItemStack.HideFlags.c) && this.v.q("Unbreakable")) {
            list.add(IChatBaseComponent.c("item.unbreakable").a(EnumChatFormat.j));
         }

         if (a(i, ItemStack.HideFlags.d) && this.v.b("CanDestroy", 9)) {
            NBTTagList nbttaglist1 = this.v.c("CanDestroy", 8);
            if (!nbttaglist1.isEmpty()) {
               list.add(CommonComponents.a);
               list.add(IChatBaseComponent.c("item.canBreak").a(EnumChatFormat.h));

               for(int k = 0; k < nbttaglist1.size(); ++k) {
                  list.addAll(d(nbttaglist1.j(k)));
               }
            }
         }

         if (a(i, ItemStack.HideFlags.e) && this.v.b("CanPlaceOn", 9)) {
            NBTTagList nbttaglist1 = this.v.c("CanPlaceOn", 8);
            if (!nbttaglist1.isEmpty()) {
               list.add(CommonComponents.a);
               list.add(IChatBaseComponent.c("item.canPlace").a(EnumChatFormat.h));

               for(int k = 0; k < nbttaglist1.size(); ++k) {
                  list.addAll(d(nbttaglist1.j(k)));
               }
            }
         }
      }

      if (tooltipflag.a()) {
         if (this.i()) {
            list.add(IChatBaseComponent.a("item.durability", this.k() - this.j(), this.k()));
         }

         list.add(IChatBaseComponent.b(BuiltInRegistries.i.b(this.c()).toString()).a(EnumChatFormat.i));
         if (this.t()) {
            list.add(IChatBaseComponent.a("item.nbt_tags", this.v.e().size()).a(EnumChatFormat.i));
         }
      }

      if (entityhuman != null && !this.c().a(entityhuman.Y().G())) {
         list.add(p);
      }

      return list;
   }

   private static boolean a(int i, ItemStack.HideFlags itemstack_hideflags) {
      return (i & itemstack_hideflags.a()) == 0;
   }

   private int P() {
      return this.t() && this.v.b("HideFlags", 99) ? this.v.h("HideFlags") : 0;
   }

   public void a(ItemStack.HideFlags itemstack_hideflags) {
      NBTTagCompound nbttagcompound = this.v();
      nbttagcompound.a("HideFlags", nbttagcompound.h("HideFlags") | itemstack_hideflags.a());
   }

   public static void a(List<IChatBaseComponent> list, NBTTagList nbttaglist) {
      for(int i = 0; i < nbttaglist.size(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.a(i);
         BuiltInRegistries.g.b(EnchantmentManager.b(nbttagcompound)).ifPresent(enchantment -> list.add(enchantment.d(EnchantmentManager.a(nbttagcompound))));
      }
   }

   private static Collection<IChatBaseComponent> d(String s) {
      try {
         return (Collection<IChatBaseComponent>)ArgumentBlock.b(BuiltInRegistries.f.p(), s, true)
            .map(
               argumentblock_a -> Lists.newArrayList(new IChatBaseComponent[]{argumentblock_a.a().b().f().a(EnumChatFormat.i)}),
               argumentblock_b -> argumentblock_b.a().a().map(holder -> holder.a().f().a(EnumChatFormat.i)).collect(Collectors.toList())
            );
      } catch (CommandSyntaxException var2) {
         return Lists.newArrayList(new IChatBaseComponent[]{IChatBaseComponent.b("missingno").a(EnumChatFormat.i)});
      }
   }

   public boolean A() {
      return this.c().i(this);
   }

   public EnumItemRarity B() {
      return this.c().n(this);
   }

   public boolean C() {
      return !this.c().d_(this) ? false : !this.D();
   }

   public void a(Enchantment enchantment, int i) {
      this.v();
      if (!this.v.b("Enchantments", 9)) {
         this.v.a("Enchantments", new NBTTagList());
      }

      NBTTagList nbttaglist = this.v.c("Enchantments", 10);
      nbttaglist.add(EnchantmentManager.a(EnchantmentManager.a(enchantment), (byte)i));
   }

   public boolean D() {
      return this.v != null && this.v.b("Enchantments", 9) ? !this.v.c("Enchantments", 10).isEmpty() : false;
   }

   public void a(String s, NBTBase nbtbase) {
      this.v().a(s, nbtbase);
   }

   public boolean E() {
      return this.x instanceof EntityItemFrame;
   }

   public void a(@Nullable Entity entity) {
      this.x = entity;
   }

   @Nullable
   public EntityItemFrame F() {
      return this.x instanceof EntityItemFrame ? (EntityItemFrame)this.G() : null;
   }

   @Nullable
   public Entity G() {
      return !this.w ? this.x : null;
   }

   public int H() {
      return this.t() && this.v.b("RepairCost", 3) ? this.v.h("RepairCost") : 0;
   }

   public void d(int i) {
      if (i == 0) {
         this.c("RepairCost");
      } else {
         this.v().a("RepairCost", i);
      }
   }

   public Multimap<AttributeBase, AttributeModifier> a(EnumItemSlot enumitemslot) {
      Object object;
      if (this.t() && this.v.b("AttributeModifiers", 9)) {
         object = HashMultimap.create();
         NBTTagList nbttaglist = this.v.c("AttributeModifiers", 10);

         for(int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.a(i);
            if (!nbttagcompound.b("Slot", 8) || nbttagcompound.l("Slot").equals(enumitemslot.d())) {
               Optional<AttributeBase> optional = BuiltInRegistries.u.b(MinecraftKey.a(nbttagcompound.l("AttributeName")));
               if (optional.isPresent()) {
                  AttributeModifier attributemodifier = AttributeModifier.a(nbttagcompound);
                  if (attributemodifier != null
                     && attributemodifier.a().getLeastSignificantBits() != 0L
                     && attributemodifier.a().getMostSignificantBits() != 0L) {
                     ((Multimap)object).put(optional.get(), attributemodifier);
                  }
               }
            }
         }
      } else {
         object = this.c().a(enumitemslot);
      }

      return (Multimap<AttributeBase, AttributeModifier>)object;
   }

   public void a(AttributeBase attributebase, AttributeModifier attributemodifier, @Nullable EnumItemSlot enumitemslot) {
      this.v();
      if (!this.v.b("AttributeModifiers", 9)) {
         this.v.a("AttributeModifiers", new NBTTagList());
      }

      NBTTagList nbttaglist = this.v.c("AttributeModifiers", 10);
      NBTTagCompound nbttagcompound = attributemodifier.e();
      nbttagcompound.a("AttributeName", BuiltInRegistries.u.b(attributebase).toString());
      if (enumitemslot != null) {
         nbttagcompound.a("Slot", enumitemslot.d());
      }

      nbttaglist.add(nbttagcompound);
   }

   @Deprecated
   public void setItem(Item item) {
      this.u = item;
   }

   public IChatBaseComponent I() {
      IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.h().b(this.x());
      if (this.z()) {
         ichatmutablecomponent.a(EnumChatFormat.u);
      }

      IChatMutableComponent ichatmutablecomponent1 = ChatComponentUtils.a((IChatBaseComponent)ichatmutablecomponent);
      if (!this.w) {
         ichatmutablecomponent1.a(this.B().e).a(chatmodifier -> chatmodifier.a(new ChatHoverable(ChatHoverable.EnumHoverAction.b, new ChatHoverable.c(this))));
      }

      return ichatmutablecomponent1;
   }

   public boolean a(IRegistry<Block> iregistry, ShapeDetectorBlock shapedetectorblock) {
      if (this.z == null) {
         this.z = new AdventureModeCheck("CanPlaceOn");
      }

      return this.z.a(this, iregistry, shapedetectorblock);
   }

   public boolean b(IRegistry<Block> iregistry, ShapeDetectorBlock shapedetectorblock) {
      if (this.y == null) {
         this.y = new AdventureModeCheck("CanDestroy");
      }

      return this.y.a(this, iregistry, shapedetectorblock);
   }

   public int J() {
      return this.t;
   }

   public void e(int i) {
      this.t = i;
   }

   public int K() {
      return this.w ? 0 : this.s;
   }

   public void f(int i) {
      this.s = i;
      this.O();
   }

   public void g(int i) {
      this.f(this.s + i);
   }

   public void h(int i) {
      this.g(-i);
   }

   public void b(World world, EntityLiving entityliving, int i) {
      this.c().a(world, entityliving, this, i);
   }

   public void a(EntityItem entityitem) {
      this.c().a(entityitem);
   }

   public boolean L() {
      return this.c().u();
   }

   public SoundEffect M() {
      return this.c().ae_();
   }

   public SoundEffect N() {
      return this.c().af_();
   }

   public static enum HideFlags {
      a,
      b,
      c,
      d,
      e,
      f,
      g,
      h;

      private final int i = 1 << this.ordinal();

      public int a() {
         return this.i;
      }
   }
}
