package net.minecraft.world.entity;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.math.Transformation;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.util.Brightness;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ColorUtil;
import net.minecraft.util.FormattedString;
import net.minecraft.util.INamable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.phys.AxisAlignedBB;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.slf4j.Logger;

public abstract class Display extends Entity {
   static final Logger o = LogUtils.getLogger();
   private static final float p = Float.POSITIVE_INFINITY;
   public static final int b = -1;
   private static final DataWatcherObject<Integer> q = DataWatcher.a(Display.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Integer> r = DataWatcher.a(Display.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Vector3f> s = DataWatcher.a(Display.class, DataWatcherRegistry.A);
   private static final DataWatcherObject<Vector3f> t = DataWatcher.a(Display.class, DataWatcherRegistry.A);
   private static final DataWatcherObject<Quaternionf> u = DataWatcher.a(Display.class, DataWatcherRegistry.B);
   private static final DataWatcherObject<Quaternionf> aC = DataWatcher.a(Display.class, DataWatcherRegistry.B);
   private static final DataWatcherObject<Byte> aD = DataWatcher.a(Display.class, DataWatcherRegistry.a);
   private static final DataWatcherObject<Integer> aE = DataWatcher.a(Display.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Float> aF = DataWatcher.a(Display.class, DataWatcherRegistry.d);
   private static final DataWatcherObject<Float> aG = DataWatcher.a(Display.class, DataWatcherRegistry.d);
   private static final DataWatcherObject<Float> aH = DataWatcher.a(Display.class, DataWatcherRegistry.d);
   private static final DataWatcherObject<Float> aI = DataWatcher.a(Display.class, DataWatcherRegistry.d);
   private static final DataWatcherObject<Float> aJ = DataWatcher.a(Display.class, DataWatcherRegistry.d);
   private static final DataWatcherObject<Integer> aK = DataWatcher.a(Display.class, DataWatcherRegistry.b);
   private static final float aL = 0.0F;
   private static final float aM = 1.0F;
   private static final int aN = -1;
   public static final String c = "interpolation_duration";
   public static final String d = "start_interpolation";
   public static final String e = "transformation";
   public static final String f = "billboard";
   public static final String g = "brightness";
   public static final String h = "view_range";
   public static final String i = "shadow_radius";
   public static final String j = "shadow_strength";
   public static final String k = "width";
   public static final String l = "height";
   public static final String m = "glow_color_override";
   private final Display.GenericInterpolator<Transformation> aO = new Display.GenericInterpolator<Transformation>(Transformation.a()) {
      protected Transformation a(float var0, Transformation var1, Transformation var2) {
         return var1.a(var2, var0);
      }
   };
   private final Display.FloatInterpolator aP = new Display.FloatInterpolator(0.0F);
   private final Display.FloatInterpolator aQ = new Display.FloatInterpolator(1.0F);
   private final Quaternionf aR = new Quaternionf();
   protected final Display.InterpolatorSet n = new Display.InterpolatorSet();
   private long aS;
   private float aT;
   private AxisAlignedBB aU;
   private boolean aV;
   private boolean aW;

   public Display(EntityTypes<?> var0, World var1) {
      super(var0, var1);
      this.ae = true;
      this.as = true;
      this.aU = this.cD();
      this.n.a(Set.of(s, u, t, aC), (var0x, var1x) -> this.aO.a(var0x, a(var1x)));
      this.n.a(aH, this.aQ);
      this.n.a(aG, this.aP);
   }

   @Override
   public void a(List<DataWatcher.b<?>> var0) {
      super.a(var0);
      boolean var1 = false;

      for(DataWatcher.b<?> var3 : var0) {
         var1 |= this.n.a(var3.a());
      }

      if (var1) {
         boolean var2 = this.ag <= 0;
         if (var2) {
            this.n.a(Float.POSITIVE_INFINITY, this.am);
         } else {
            this.aV = true;
         }
      }
   }

   @Override
   public void a(DataWatcherObject<?> var0) {
      super.a(var0);
      if (aJ.equals(var0) || aI.equals(var0)) {
         this.y();
      }

      if (q.equals(var0)) {
         this.aW = true;
      }
   }

   public static Transformation a(DataWatcher var0) {
      Vector3f var1 = var0.a(s);
      Quaternionf var2 = var0.a(u);
      Vector3f var3 = var0.a(t);
      Quaternionf var4 = var0.a(aC);
      return new Transformation(var1, var2, var3, var4);
   }

   @Override
   public void l() {
      Entity var0 = this.cV();
      if (var0 != null && var0.dB()) {
         this.bz();
      }

      if (this.H.B) {
         if (this.aW) {
            this.aW = false;
            int var1 = this.p();
            this.aS = (long)(this.ag + var1);
         }

         if (this.aV) {
            this.aV = false;
            this.n.a(this.aT, this.am);
         }
      }
   }

   @Override
   protected void a_() {
      this.am.a(q, 0);
      this.am.a(r, 0);
      this.am.a(s, new Vector3f());
      this.am.a(t, new Vector3f(1.0F, 1.0F, 1.0F));
      this.am.a(aC, new Quaternionf());
      this.am.a(u, new Quaternionf());
      this.am.a(aD, Display.BillboardConstraints.a.a());
      this.am.a(aE, -1);
      this.am.a(aF, 1.0F);
      this.am.a(aG, 0.0F);
      this.am.a(aH, 1.0F);
      this.am.a(aI, 0.0F);
      this.am.a(aJ, 0.0F);
      this.am.a(aK, -1);
   }

   @Override
   protected void a(NBTTagCompound var0) {
      if (var0.e("transformation")) {
         Transformation.b
            .decode(DynamicOpsNBT.a, var0.c("transformation"))
            .resultOrPartial(SystemUtils.a("Display entity", o::error))
            .ifPresent(var0x -> this.a((Transformation)var0x.getFirst()));
      }

      if (var0.b("interpolation_duration", 99)) {
         int var1 = var0.h("interpolation_duration");
         this.b(var1);
      }

      if (var0.b("start_interpolation", 99)) {
         int var1 = var0.h("start_interpolation");
         this.c(var1);
      }

      if (var0.b("billboard", 8)) {
         Display.BillboardConstraints.e
            .decode(DynamicOpsNBT.a, var0.c("billboard"))
            .resultOrPartial(SystemUtils.a("Display entity", o::error))
            .ifPresent(var0x -> this.a((Display.BillboardConstraints)var0x.getFirst()));
      }

      if (var0.b("view_range", 99)) {
         this.g(var0.j("view_range"));
      }

      if (var0.b("shadow_radius", 99)) {
         this.h(var0.j("shadow_radius"));
      }

      if (var0.b("shadow_strength", 99)) {
         this.w(var0.j("shadow_strength"));
      }

      if (var0.b("width", 99)) {
         this.x(var0.j("width"));
      }

      if (var0.b("height", 99)) {
         this.y(var0.j("height"));
      }

      if (var0.b("glow_color_override", 99)) {
         this.d(var0.h("glow_color_override"));
      }

      if (var0.b("brightness", 10)) {
         Brightness.b
            .decode(DynamicOpsNBT.a, var0.c("brightness"))
            .resultOrPartial(SystemUtils.a("Display entity", o::error))
            .ifPresent(var0x -> this.a((Brightness)var0x.getFirst()));
      } else {
         this.a(null);
      }
   }

   public void a(Transformation var0) {
      this.am.b(s, var0.d());
      this.am.b(u, var0.e());
      this.am.b(t, var0.f());
      this.am.b(aC, var0.g());
   }

   @Override
   protected void b(NBTTagCompound var0) {
      Transformation.b.encodeStart(DynamicOpsNBT.a, a(this.am)).result().ifPresent(var1x -> var0.a("transformation", var1x));
      Display.BillboardConstraints.e.encodeStart(DynamicOpsNBT.a, this.j()).result().ifPresent(var1x -> var0.a("billboard", var1x));
      var0.a("interpolation_duration", this.o());
      var0.a("view_range", this.r());
      var0.a("shadow_radius", this.s());
      var0.a("shadow_strength", this.t());
      var0.a("width", this.v());
      var0.a("height", this.x());
      var0.a("glow_color_override", this.w());
      Brightness var1 = this.q();
      if (var1 != null) {
         Brightness.b.encodeStart(DynamicOpsNBT.a, var1).result().ifPresent(var1x -> var0.a("brightness", var1x));
      }
   }

   @Override
   public Packet<PacketListenerPlayOut> S() {
      return new PacketPlayOutSpawnEntity(this);
   }

   @Override
   public AxisAlignedBB A_() {
      return this.aU;
   }

   @Override
   public EnumPistonReaction C_() {
      return EnumPistonReaction.d;
   }

   public Quaternionf i() {
      return this.aR;
   }

   public Transformation a(float var0) {
      return this.aO.a(var0);
   }

   public void b(int var0) {
      this.am.b(r, var0);
   }

   public int o() {
      return this.am.a(r);
   }

   public void c(int var0) {
      this.am.a(q, var0, true);
   }

   public int p() {
      return this.am.a(q);
   }

   public void a(Display.BillboardConstraints var0) {
      this.am.b(aD, var0.a());
   }

   public Display.BillboardConstraints j() {
      return Display.BillboardConstraints.f.apply(this.am.a(aD));
   }

   public void a(@Nullable Brightness var0) {
      this.am.b(aE, var0 != null ? var0.a() : -1);
   }

   @Nullable
   public Brightness q() {
      int var0 = this.am.a(aE);
      return var0 != -1 ? Brightness.a(var0) : null;
   }

   public int k() {
      return this.am.a(aE);
   }

   public void g(float var0) {
      this.am.b(aF, var0);
   }

   public float r() {
      return this.am.a(aF);
   }

   public void h(float var0) {
      this.am.b(aG, var0);
   }

   public float s() {
      return this.am.a(aG);
   }

   public float b(float var0) {
      return this.aP.a(var0);
   }

   public void w(float var0) {
      this.am.b(aH, var0);
   }

   public float t() {
      return this.am.a(aH);
   }

   public float c(float var0) {
      return this.aQ.a(var0);
   }

   public void x(float var0) {
      this.am.b(aI, var0);
   }

   public float v() {
      return this.am.a(aI);
   }

   public void y(float var0) {
      this.am.b(aJ, var0);
   }

   public int w() {
      return this.am.a(aK);
   }

   public void d(int var0) {
      this.am.b(aK, var0);
   }

   public float d(float var0) {
      int var1 = this.o();
      if (var1 <= 0) {
         return 1.0F;
      } else {
         float var2 = (float)((long)this.ag - this.aS);
         float var3 = var2 + var0;
         float var4 = MathHelper.a(MathHelper.g(var3, 0.0F, (float)var1), 0.0F, 1.0F);
         this.aT = var4;
         return var4;
      }
   }

   public float x() {
      return this.am.a(aJ);
   }

   @Override
   public void e(double var0, double var2, double var4) {
      super.e(var0, var2, var4);
      this.y();
   }

   private void y() {
      float var0 = this.v();
      float var1 = this.x();
      if (var0 != 0.0F && var1 != 0.0F) {
         this.as = false;
         float var2 = var0 / 2.0F;
         double var3 = this.dl();
         double var5 = this.dn();
         double var7 = this.dr();
         this.aU = new AxisAlignedBB(var3 - (double)var2, var5, var7 - (double)var2, var3 + (double)var2, var5 + (double)var1, var7 + (double)var2);
      } else {
         this.as = true;
      }
   }

   @Override
   public void e(float var0) {
      super.e(var0);
      this.z();
   }

   @Override
   public void f(float var0) {
      super.f(var0);
      this.z();
   }

   private void z() {
      this.aR.rotationYXZ(((float) (-Math.PI / 180.0)) * this.dw(), (float) (Math.PI / 180.0) * this.dy(), 0.0F);
   }

   @Override
   public boolean a(double var0) {
      return var0 < MathHelper.k((double)this.r() * 64.0 * cw());
   }

   @Override
   public int B_() {
      int var0 = this.w();
      return var0 != -1 ? var0 : super.B_();
   }

   public static enum BillboardConstraints implements INamable {
      a((byte)0, "fixed"),
      b((byte)1, "vertical"),
      c((byte)2, "horizontal"),
      d((byte)3, "center");

      public static final Codec<Display.BillboardConstraints> e = INamable.a(Display.BillboardConstraints::values);
      public static final IntFunction<Display.BillboardConstraints> f = ByIdMap.a(Display.BillboardConstraints::a, values(), ByIdMap.a.a);
      private final byte g;
      private final String h;

      private BillboardConstraints(byte var2, String var3) {
         this.h = var3;
         this.g = var2;
      }

      @Override
      public String c() {
         return this.h;
      }

      byte a() {
         return this.g;
      }
   }

   public static class BlockDisplay extends Display {
      public static final String o = "block_state";
      private static final DataWatcherObject<IBlockData> p = DataWatcher.a(Display.BlockDisplay.class, DataWatcherRegistry.i);

      public BlockDisplay(EntityTypes<?> var0, World var1) {
         super(var0, var1);
      }

      @Override
      protected void a_() {
         super.a_();
         this.am.a(p, Blocks.a.o());
      }

      public IBlockData o() {
         return this.am.a(p);
      }

      @Override
      public void b(IBlockData var0) {
         this.am.b(p, var0);
      }

      @Override
      protected void a(NBTTagCompound var0) {
         super.a(var0);
         this.b(GameProfileSerializer.a(this.H.a(Registries.e), var0.p("block_state")));
      }

      @Override
      protected void b(NBTTagCompound var0) {
         super.b(var0);
         var0.a("block_state", GameProfileSerializer.a(this.o()));
      }
   }

   static class ColorInterpolator extends Display.IntInterpolator {
      protected ColorInterpolator(int var0) {
         super(var0);
      }

      @Override
      protected int a(float var0, int var1, int var2) {
         return ColorUtil.b.a(var0, var1, var2);
      }
   }

   static class FloatInterpolator extends Display.Interpolator<Float> {
      protected FloatInterpolator(float var0) {
         super(var0);
      }

      protected float a(float var0, float var1, float var2) {
         return MathHelper.i(var0, var1, var2);
      }

      public float a(float var0) {
         return !((double)var0 >= 1.0) && this.b != null ? this.a(var0, this.b, this.c) : this.c;
      }

      protected Float b(float var0) {
         return this.a(var0);
      }
   }

   abstract static class GenericInterpolator<T> extends Display.Interpolator<T> {
      protected GenericInterpolator(T var0) {
         super(var0);
      }

      protected abstract T a(float var1, T var2, T var3);

      public T a(float var0) {
         return (T)(!((double)var0 >= 1.0) && this.b != null ? this.a(var0, this.b, this.c) : this.c);
      }

      @Override
      protected T c(float var0) {
         return this.a(var0);
      }
   }

   static class IntInterpolator extends Display.Interpolator<Integer> {
      protected IntInterpolator(int var0) {
         super(var0);
      }

      protected int a(float var0, int var1, int var2) {
         return MathHelper.a(var0, var1, var2);
      }

      public int a(float var0) {
         return !((double)var0 >= 1.0) && this.b != null ? this.a(var0, this.b, this.c) : this.c;
      }

      protected Integer b(float var0) {
         return this.a(var0);
      }
   }

   @FunctionalInterface
   interface IntepolatorUpdater {
      void update(float var1, DataWatcher var2);
   }

   abstract static class Interpolator<T> {
      @Nullable
      protected T b;
      protected T c;

      protected Interpolator(T var0) {
         this.c = var0;
      }

      protected abstract T c(float var1);

      public void a(float var0, T var1) {
         if (var0 != Float.POSITIVE_INFINITY) {
            this.b = this.c(var0);
         }

         this.c = var1;
      }
   }

   static class InterpolatorSet {
      private final IntSet a = new IntOpenHashSet();
      private final List<Display.IntepolatorUpdater> b = new ArrayList<>();

      protected <T> void a(DataWatcherObject<T> var0, Display.Interpolator<T> var1) {
         this.a.add(var0.a());
         this.b.add((var2x, var3) -> var1.a(var2x, var3.a(var0)));
      }

      protected void a(Set<DataWatcherObject<?>> var0, Display.IntepolatorUpdater var1) {
         for(DataWatcherObject<?> var3 : var0) {
            this.a.add(var3.a());
         }

         this.b.add(var1);
      }

      public boolean a(int var0) {
         return this.a.contains(var0);
      }

      public void a(float var0, DataWatcher var1) {
         for(Display.IntepolatorUpdater var3 : this.b) {
            var3.update(var0, var1);
         }
      }
   }

   public static class ItemDisplay extends Display {
      private static final String o = "item";
      private static final String p = "item_display";
      private static final DataWatcherObject<ItemStack> q = DataWatcher.a(Display.ItemDisplay.class, DataWatcherRegistry.h);
      private static final DataWatcherObject<Byte> r = DataWatcher.a(Display.ItemDisplay.class, DataWatcherRegistry.a);
      private final SlotAccess s = new SlotAccess() {
         @Override
         public ItemStack a() {
            return ItemDisplay.this.o();
         }

         @Override
         public boolean a(ItemStack var0) {
            ItemDisplay.this.a(var0);
            return true;
         }
      };

      public ItemDisplay(EntityTypes<?> var0, World var1) {
         super(var0, var1);
      }

      @Override
      protected void a_() {
         super.a_();
         this.am.a(q, ItemStack.b);
         this.am.a(r, ItemDisplayContext.a.a());
      }

      public ItemStack o() {
         return this.am.a(q);
      }

      public void a(ItemStack var0) {
         this.am.b(q, var0);
      }

      public void a(ItemDisplayContext var0) {
         this.am.b(r, var0.a());
      }

      public ItemDisplayContext p() {
         return ItemDisplayContext.k.apply(this.am.a(r));
      }

      @Override
      protected void a(NBTTagCompound var0) {
         super.a(var0);
         this.a(ItemStack.a(var0.p("item")));
         if (var0.b("item_display", 8)) {
            ItemDisplayContext.j
               .decode(DynamicOpsNBT.a, var0.c("item_display"))
               .resultOrPartial(SystemUtils.a("Display entity", Display.o::error))
               .ifPresent(var0x -> this.a((ItemDisplayContext)var0x.getFirst()));
         }
      }

      @Override
      protected void b(NBTTagCompound var0) {
         super.b(var0);
         var0.a("item", this.o().b(new NBTTagCompound()));
         ItemDisplayContext.j.encodeStart(DynamicOpsNBT.a, this.p()).result().ifPresent(var1x -> var0.a("item_display", var1x));
      }

      @Override
      public SlotAccess a_(int var0) {
         return var0 == 0 ? this.s : SlotAccess.b;
      }
   }

   public static class TextDisplay extends Display {
      public static final String o = "text";
      private static final String aC = "line_width";
      private static final String aD = "text_opacity";
      private static final String aE = "background";
      private static final String aF = "shadow";
      private static final String aG = "see_through";
      private static final String aH = "default_background";
      private static final String aI = "alignment";
      public static final byte p = 1;
      public static final byte q = 2;
      public static final byte r = 4;
      public static final byte s = 8;
      public static final byte t = 16;
      private static final byte aJ = -1;
      public static final int u = 1073741824;
      private static final DataWatcherObject<IChatBaseComponent> aK = DataWatcher.a(Display.TextDisplay.class, DataWatcherRegistry.f);
      private static final DataWatcherObject<Integer> aL = DataWatcher.a(Display.TextDisplay.class, DataWatcherRegistry.b);
      private static final DataWatcherObject<Integer> aM = DataWatcher.a(Display.TextDisplay.class, DataWatcherRegistry.b);
      private static final DataWatcherObject<Byte> aN = DataWatcher.a(Display.TextDisplay.class, DataWatcherRegistry.a);
      private static final DataWatcherObject<Byte> aO = DataWatcher.a(Display.TextDisplay.class, DataWatcherRegistry.a);
      private final Display.IntInterpolator aP = new Display.IntInterpolator(-1);
      private final Display.IntInterpolator aQ = new Display.ColorInterpolator(1073741824);
      @Nullable
      private Display.TextDisplay.CachedInfo aR;

      public TextDisplay(EntityTypes<?> var0, World var1) {
         super(var0, var1);
         this.n.a(aM, this.aQ);
         this.n.a(Set.of(aN), (var0x, var1x) -> this.aP.a(var0x, Integer.valueOf(var1x.a(aN) & 255)));
      }

      @Override
      protected void a_() {
         super.a_();
         this.am.a(aK, IChatBaseComponent.h());
         this.am.a(aL, 200);
         this.am.a(aM, 1073741824);
         this.am.a(aN, (byte)-1);
         this.am.a(aO, (byte)0);
      }

      @Override
      public void a(DataWatcherObject<?> var0) {
         super.a(var0);
         this.aR = null;
      }

      public IChatBaseComponent o() {
         return this.am.a(aK);
      }

      public void c(IChatBaseComponent var0) {
         this.am.b(aK, var0);
      }

      @Override
      public int p() {
         return this.am.a(aL);
      }

      @Override
      public void b(int var0) {
         this.am.b(aL, var0);
      }

      public byte g(float var0) {
         return (byte)this.aP.a(var0);
      }

      public byte r() {
         return this.am.a(aN);
      }

      public void c(byte var0) {
         this.am.b(aN, var0);
      }

      public int h(float var0) {
         return this.aQ.a(var0);
      }

      public int s() {
         return this.am.a(aM);
      }

      @Override
      public void c(int var0) {
         this.am.b(aM, var0);
      }

      public byte q() {
         return this.am.a(aO);
      }

      public void d(byte var0) {
         this.am.b(aO, var0);
      }

      private static byte a(byte var0, NBTTagCompound var1, String var2, byte var3) {
         return var1.q(var2) ? (byte)(var0 | var3) : var0;
      }

      @Override
      protected void a(NBTTagCompound var0) {
         super.a(var0);
         if (var0.b("line_width", 99)) {
            this.b(var0.h("line_width"));
         }

         if (var0.b("text_opacity", 99)) {
            this.c(var0.f("text_opacity"));
         }

         if (var0.b("background", 99)) {
            this.c(var0.h("background"));
         }

         byte var1 = a((byte)0, var0, "shadow", (byte)1);
         var1 = a(var1, var0, "see_through", (byte)2);
         var1 = a(var1, var0, "default_background", (byte)4);
         Optional<Display.TextDisplay.Align> var2 = Display.TextDisplay.Align.d
            .decode(DynamicOpsNBT.a, var0.c("alignment"))
            .resultOrPartial(SystemUtils.a("Display entity", Display.o::error))
            .map(Pair::getFirst);
         if (var2.isPresent()) {
            var1 = switch((Display.TextDisplay.Align)var2.get()) {
               case a -> var1;
               case b -> (byte)(var1 | 8);
               case c -> (byte)(var1 | 16);
            };
         }

         this.d(var1);
         if (var0.b("text", 8)) {
            String var3 = var0.l("text");

            try {
               IChatBaseComponent var4 = IChatBaseComponent.ChatSerializer.a(var3);
               if (var4 != null) {
                  CommandListenerWrapper var5 = this.cZ().a(2);
                  IChatBaseComponent var6 = ChatComponentUtils.a(var5, var4, this, 0);
                  this.c(var6);
               } else {
                  this.c(IChatBaseComponent.h());
               }
            } catch (Exception var8) {
               Display.o.warn("Failed to parse display entity text {}", var3, var8);
            }
         }
      }

      private static void b(byte var0, NBTTagCompound var1, String var2, byte var3) {
         var1.a(var2, (var0 & var3) != 0);
      }

      @Override
      protected void b(NBTTagCompound var0) {
         super.b(var0);
         var0.a("text", IChatBaseComponent.ChatSerializer.a(this.o()));
         var0.a("line_width", this.p());
         var0.a("background", this.s());
         var0.a("text_opacity", this.r());
         byte var1 = this.q();
         b(var1, var0, "shadow", (byte)1);
         b(var1, var0, "see_through", (byte)2);
         b(var1, var0, "default_background", (byte)4);
         Display.TextDisplay.Align.d.encodeStart(DynamicOpsNBT.a, a(var1)).result().ifPresent(var1x -> var0.a("alignment", var1x));
      }

      public Display.TextDisplay.CachedInfo a(Display.TextDisplay.LineSplitter var0) {
         if (this.aR == null) {
            int var1 = this.p();
            this.aR = var0.split(this.o(), var1);
         }

         return this.aR;
      }

      public static Display.TextDisplay.Align a(byte var0) {
         if ((var0 & 8) != 0) {
            return Display.TextDisplay.Align.b;
         } else {
            return (var0 & 16) != 0 ? Display.TextDisplay.Align.c : Display.TextDisplay.Align.a;
         }
      }

      public static enum Align implements INamable {
         a("center"),
         b("left"),
         c("right");

         public static final Codec<Display.TextDisplay.Align> d = INamable.a(Display.TextDisplay.Align::values);
         private final String e;

         private Align(String var2) {
            this.e = var2;
         }

         @Override
         public String c() {
            return this.e;
         }
      }

      public static record CachedInfo(List<Display.TextDisplay.CachedLine> lines, int width) {
         private final List<Display.TextDisplay.CachedLine> a;
         private final int b;

         public CachedInfo(List<Display.TextDisplay.CachedLine> var0, int var1) {
            this.a = var0;
            this.b = var1;
         }
      }

      public static record CachedLine(FormattedString contents, int width) {
         private final FormattedString a;
         private final int b;

         public CachedLine(FormattedString var0, int var1) {
            this.a = var0;
            this.b = var1;
         }
      }

      @FunctionalInterface
      public interface LineSplitter {
         Display.TextDisplay.CachedInfo split(IChatBaseComponent var1, int var2);
      }
   }
}
