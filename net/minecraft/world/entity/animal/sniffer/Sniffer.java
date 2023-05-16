package net.minecraft.world.entity.animal.sniffer;

import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.ParticleParamBlock;
import net.minecraft.core.particles.Particles;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.EnumRenderType;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.EntityDropItemEvent;

public class Sniffer extends EntityAnimal {
   private static final int cb = 1700;
   private static final int cc = 6000;
   private static final int cd = 30;
   private static final int ce = 120;
   private static final int cf = 10;
   private static final int cg = 48000;
   private static final DataWatcherObject<Sniffer.a> ch = DataWatcher.a(Sniffer.class, DataWatcherRegistry.z);
   private static final DataWatcherObject<Integer> ci = DataWatcher.a(Sniffer.class, DataWatcherRegistry.b);
   public final AnimationState bS = new AnimationState();
   public final AnimationState bT = new AnimationState();
   public final AnimationState bV = new AnimationState();
   public final AnimationState bW = new AnimationState();
   public final AnimationState bX = new AnimationState();
   public final AnimationState bY = new AnimationState();
   public final AnimationState bZ = new AnimationState();
   public final AnimationState ca = new AnimationState();

   public static AttributeProvider.Builder q() {
      return EntityInsentient.y().a(GenericAttributes.d, 0.1F).a(GenericAttributes.a, 14.0);
   }

   public Sniffer(EntityTypes<? extends EntityAnimal> entitytypes, World world) {
      super(entitytypes, world);
      this.G().a(true);
      this.a(PathType.j, -2.0F);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(ch, Sniffer.a.a);
      this.am.a(ci, 0);
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return this.a(entitypose).b * 0.6F;
   }

   private boolean fZ() {
      boolean flag = this.N || this.aW();
      return flag && this.dj().i() > 1.0E-6;
   }

   private boolean ga() {
      return this.fZ() && this.aT() && !this.aX() && this.dj().i() > 9.999999999999999E-6;
   }

   private boolean gb() {
      return this.fZ() && !this.aX() && !this.aT();
   }

   public boolean r() {
      return this.by.c(MemoryModuleType.Y).isPresent();
   }

   public boolean w() {
      return this.gd() == Sniffer.a.f || this.gd() == Sniffer.a.e;
   }

   private BlockPosition gc() {
      Vec3D vec3d = this.de().e(this.bE().a(2.25));
      return BlockPosition.a(vec3d.a(), this.dn(), vec3d.c());
   }

   public Sniffer.a gd() {
      return this.am.a(ch);
   }

   private Sniffer b(Sniffer.a sniffer_a) {
      this.am.b(ch, sniffer_a);
      return this;
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (ch.equals(datawatcherobject)) {
         Sniffer.a sniffer_a = this.gd();
         this.ge();
         switch(sniffer_a) {
            case b:
               this.bV.b(this.ag);
               break;
            case c:
               this.bW.b(this.ag);
               break;
            case d:
               this.bX.b(this.ag);
               break;
            case e:
               this.bY.b(this.ag);
               break;
            case f:
               this.bZ.b(this.ag);
               break;
            case g:
               this.ca.b(this.ag);
         }
      }

      super.a(datawatcherobject);
   }

   private void ge() {
      this.bY.a();
      this.bZ.a();
      this.bX.a();
      this.ca.a();
      this.bV.a();
      this.bW.a();
   }

   public Sniffer a(Sniffer.a sniffer_a) {
      switch(sniffer_a) {
         case a:
            this.b(Sniffer.a.a);
            break;
         case b:
            this.a(SoundEffects.wt, 1.0F, 1.0F);
            this.b(Sniffer.a.b);
            break;
         case c:
            this.a(SoundEffects.wo, 1.0F, 1.0F);
            this.b(Sniffer.a.c);
            break;
         case d:
            this.a(SoundEffects.wp, 1.0F, 1.0F);
            this.b(Sniffer.a.d);
            break;
         case e:
            this.b(Sniffer.a.e);
            break;
         case f:
            this.b(Sniffer.a.f).gf();
            break;
         case g:
            this.a(SoundEffects.ws, 1.0F, 1.0F);
            this.b(Sniffer.a.g);
      }

      return this;
   }

   private Sniffer gf() {
      this.am.b(ci, this.ag + 120);
      this.H.a(this, (byte)63);
      return this;
   }

   public Sniffer w(boolean flag) {
      if (flag) {
         this.h(this.aD());
      }

      return this;
   }

   public Optional<BlockPosition> fS() {
      return IntStream.range(0, 5)
         .mapToObj(i -> LandRandomPos.a(this, 10 + 2 * i, 3))
         .filter(Objects::nonNull)
         .map(BlockPosition::a)
         .map(BlockPosition::d)
         .filter(this::g)
         .findFirst();
   }

   @Override
   protected boolean l(Entity entity) {
      return false;
   }

   public boolean fY() {
      return !this.r() && !this.y_() && !this.aT() && this.g(this.gc().d());
   }

   private boolean g(BlockPosition blockposition) {
      if (this.H.a_(blockposition).a(TagsBlock.cd) && this.H.a_(blockposition.c()).h()) {
         Stream stream = this.gh();
         if (stream.noneMatch(blockposition::equals)) {
            boolean flag = true;
            return flag;
         }
      }

      return false;
   }

   private void gg() {
      if (!this.H.k_() && this.am.a(ci) == this.ag) {
         ItemStack itemstack = new ItemStack(Items.uk);
         BlockPosition blockposition = this.gc();
         EntityItem entityitem = new EntityItem(this.H, (double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w(), itemstack);
         EntityDropItemEvent event = new EntityDropItemEvent(this.getBukkitEntity(), (Item)entityitem.getBukkitEntity());
         Bukkit.getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return;
         }

         entityitem.k();
         this.H.b(entityitem);
         this.a(SoundEffects.wn, 1.0F, 1.0F);
      }
   }

   private Sniffer a(AnimationState animationstate) {
      boolean flag = animationstate.b() > 1700L && animationstate.b() < 6000L;
      if (flag) {
         IBlockData iblockdata = this.bd();
         BlockPosition blockposition = this.gc();
         if (iblockdata.i() != EnumRenderType.a) {
            for(int i = 0; i < 30; ++i) {
               Vec3D vec3d = Vec3D.b(blockposition);
               this.H.a(new ParticleParamBlock(Particles.c, iblockdata), vec3d.c, vec3d.d, vec3d.e, 0.0, 0.0, 0.0);
            }

            if (this.ag % 10 == 0) {
               this.H.a(this.dl(), this.dn(), this.dr(), iblockdata.t().f(), this.cX(), 0.5F, 0.5F, false);
            }
         }
      }

      return this;
   }

   public Sniffer h(BlockPosition blockposition) {
      List<BlockPosition> list = this.gh().limit(20L).collect(Collectors.toList());
      list.add(0, blockposition);
      this.dH().a(MemoryModuleType.aP, list);
      return this;
   }

   public Stream<BlockPosition> gh() {
      return this.dH().c(MemoryModuleType.aP).stream().flatMap(Collection::stream);
   }

   @Override
   protected void eS() {
      super.eS();
      double d0 = this.bK.c();
      if (d0 > 0.0) {
         double d1 = this.dj().i();
         if (d1 < 0.01) {
            this.a(0.1F, new Vec3D(0.0, 0.0, 1.0));
         }
      }
   }

   @Override
   public void l() {
      boolean flag = this.aT() && !this.aX();
      this.a(GenericAttributes.d).a(flag ? 0.2F : 0.1F);
      if (!this.gb() && !this.ga()) {
         this.bT.a();
         this.bS.a();
      } else if (this.r()) {
         this.bS.a();
         this.bT.b(this.ag);
      } else {
         this.bT.a();
         this.bS.b(this.ag);
      }

      switch(this.gd()) {
         case e:
            this.gi();
            break;
         case f:
            this.a(this.bZ).gg();
      }

      super.l();
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      EnumInteractionResult enuminteractionresult = super.b(entityhuman, enumhand);
      if (enuminteractionresult.a() && this.m(itemstack)) {
         this.H.a(null, this, this.d(itemstack), SoundCategory.g, 1.0F, MathHelper.b(this.H.z, 0.8F, 1.2F));
      }

      return enuminteractionresult;
   }

   private void gi() {
      if (this.H.k_() && this.ag % 20 == 0) {
         this.H.a(this.dl(), this.dn(), this.dr(), SoundEffects.wq, this.cX(), 1.0F, 1.0F, false);
      }
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.wi, 0.15F, 1.0F);
   }

   @Override
   public SoundEffect d(ItemStack itemstack) {
      return SoundEffects.wj;
   }

   @Override
   protected SoundEffect s() {
      return Set.of(Sniffer.a.f, Sniffer.a.e).contains(this.gd()) ? null : SoundEffects.wk;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.wl;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.wm;
   }

   @Override
   public int W() {
      return 50;
   }

   @Override
   public void a(boolean flag) {
      this.c_(flag ? -48000 : 0);
   }

   @Override
   public EntityAgeable a(WorldServer worldserver, EntityAgeable entityageable) {
      return EntityTypes.aN.a((World)worldserver);
   }

   @Override
   public boolean a(EntityAnimal entityanimal) {
      if (!(entityanimal instanceof Sniffer)) {
         return false;
      } else {
         Sniffer sniffer = (Sniffer)entityanimal;
         Set<Sniffer.a> set = Set.of(Sniffer.a.a, Sniffer.a.c, Sniffer.a.b);
         return set.contains(this.gd()) && set.contains(sniffer.gd()) && super.a(entityanimal);
      }
   }

   @Override
   public AxisAlignedBB A_() {
      return super.A_().g(0.6F);
   }

   @Override
   public boolean m(ItemStack itemstack) {
      return itemstack.a(TagsItem.aJ);
   }

   @Override
   protected BehaviorController<?> a(Dynamic<?> dynamic) {
      return SnifferAi.a(this.dI().a(dynamic));
   }

   @Override
   public BehaviorController<Sniffer> dH() {
      return super.dH();
   }

   @Override
   protected BehaviorController.b<Sniffer> dI() {
      return BehaviorController.a(SnifferAi.b, SnifferAi.a);
   }

   @Override
   protected void U() {
      this.H.ac().a("snifferBrain");
      this.dH().a((WorldServer)this.H, this);
      this.H.ac().b("snifferActivityUpdate");
      SnifferAi.a(this);
      this.H.ac().c();
      super.U();
   }

   @Override
   protected void T() {
      super.T();
      PacketDebug.a(this);
   }

   public static enum a {
      a,
      b,
      c,
      d,
      e,
      f,
      g;
   }
}
