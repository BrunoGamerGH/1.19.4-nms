package net.minecraft.commands.arguments.selector;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.CriterionConditionValue;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;

public class EntitySelector {
   public static final int a = Integer.MAX_VALUE;
   public static final BiConsumer<Vec3D, List<? extends Entity>> b = (vec3d, list) -> {
   };
   private static final EntityTypeTest<Entity, ?> c = new EntityTypeTest<Entity, Entity>() {
      public Entity a(Entity entity) {
         return entity;
      }

      @Override
      public Class<? extends Entity> a() {
         return Entity.class;
      }
   };
   private final int d;
   private final boolean e;
   private final boolean f;
   private final Predicate<Entity> g;
   private final CriterionConditionValue.DoubleRange h;
   private final Function<Vec3D, Vec3D> i;
   @Nullable
   private final AxisAlignedBB j;
   private final BiConsumer<Vec3D, List<? extends Entity>> k;
   private final boolean l;
   @Nullable
   private final String m;
   @Nullable
   private final UUID n;
   private final EntityTypeTest<Entity, ?> o;
   private final boolean p;

   public EntitySelector(
      int i,
      boolean flag,
      boolean flag1,
      Predicate<Entity> predicate,
      CriterionConditionValue.DoubleRange criterionconditionvalue_doublerange,
      Function<Vec3D, Vec3D> function,
      @Nullable AxisAlignedBB axisalignedbb,
      BiConsumer<Vec3D, List<? extends Entity>> biconsumer,
      boolean flag2,
      @Nullable String s,
      @Nullable UUID uuid,
      @Nullable EntityTypes<?> entitytypes,
      boolean flag3
   ) {
      this.d = i;
      this.e = flag;
      this.f = flag1;
      this.g = predicate;
      this.h = criterionconditionvalue_doublerange;
      this.i = function;
      this.j = axisalignedbb;
      this.k = biconsumer;
      this.l = flag2;
      this.m = s;
      this.n = uuid;
      this.o = (EntityTypeTest<Entity, ?>)(entitytypes == null ? c : entitytypes);
      this.p = flag3;
   }

   public int a() {
      return this.d;
   }

   public boolean b() {
      return this.e;
   }

   public boolean c() {
      return this.l;
   }

   public boolean d() {
      return this.f;
   }

   public boolean e() {
      return this.p;
   }

   private void e(CommandListenerWrapper commandlistenerwrapper) throws CommandSyntaxException {
      if (this.p && !commandlistenerwrapper.hasPermission(2, "minecraft.command.selector")) {
         throw ArgumentEntity.f.create();
      }
   }

   public Entity a(CommandListenerWrapper commandlistenerwrapper) throws CommandSyntaxException {
      this.e(commandlistenerwrapper);
      List<? extends Entity> list = this.b(commandlistenerwrapper);
      if (list.isEmpty()) {
         throw ArgumentEntity.d.create();
      } else if (list.size() > 1) {
         throw ArgumentEntity.a.create();
      } else {
         return list.get(0);
      }
   }

   public List<? extends Entity> b(CommandListenerWrapper commandlistenerwrapper) throws CommandSyntaxException {
      return this.f(commandlistenerwrapper).stream().filter(entity -> entity.ae().a(commandlistenerwrapper.v())).toList();
   }

   private List<? extends Entity> f(CommandListenerWrapper commandlistenerwrapper) throws CommandSyntaxException {
      this.e(commandlistenerwrapper);
      if (!this.e) {
         return this.d(commandlistenerwrapper);
      } else if (this.m != null) {
         EntityPlayer entityplayer = commandlistenerwrapper.l().ac().a(this.m);
         return (List<? extends Entity>)(entityplayer == null ? Collections.emptyList() : Lists.newArrayList(new EntityPlayer[]{entityplayer}));
      } else if (this.n != null) {
         for(WorldServer worldserver : commandlistenerwrapper.l().F()) {
            Entity entity = worldserver.a(this.n);
            if (entity != null) {
               return Lists.newArrayList(new Entity[]{entity});
            }
         }

         return Collections.emptyList();
      } else {
         Vec3D vec3d = this.i.apply(commandlistenerwrapper.d());
         Predicate<Entity> predicate = this.a(vec3d);
         if (this.l) {
            return (List<? extends Entity>)(commandlistenerwrapper.f() != null && predicate.test(commandlistenerwrapper.f())
               ? Lists.newArrayList(new Entity[]{commandlistenerwrapper.f()})
               : Collections.emptyList());
         } else {
            List<Entity> list = Lists.newArrayList();
            if (this.d()) {
               this.a(list, commandlistenerwrapper.e(), vec3d, predicate);
            } else {
               for(WorldServer worldserver1 : commandlistenerwrapper.l().F()) {
                  this.a(list, worldserver1, vec3d, predicate);
               }
            }

            return this.a(vec3d, list);
         }
      }
   }

   private void a(List<Entity> list, WorldServer worldserver, Vec3D vec3d, Predicate<Entity> predicate) {
      int i = this.f();
      if (list.size() < i) {
         if (this.j != null) {
            worldserver.a(this.o, this.j.c(vec3d), predicate, list, i);
         } else {
            worldserver.a(this.o, predicate, list, i);
         }
      }
   }

   private int f() {
      return this.k == b ? this.d : Integer.MAX_VALUE;
   }

   public EntityPlayer c(CommandListenerWrapper commandlistenerwrapper) throws CommandSyntaxException {
      this.e(commandlistenerwrapper);
      List<EntityPlayer> list = this.d(commandlistenerwrapper);
      if (list.size() != 1) {
         throw ArgumentEntity.e.create();
      } else {
         return list.get(0);
      }
   }

   public List<EntityPlayer> d(CommandListenerWrapper commandlistenerwrapper) throws CommandSyntaxException {
      this.e(commandlistenerwrapper);
      if (this.m != null) {
         EntityPlayer entityplayer = commandlistenerwrapper.l().ac().a(this.m);
         return (List<EntityPlayer>)(entityplayer == null ? Collections.emptyList() : Lists.newArrayList(new EntityPlayer[]{entityplayer}));
      } else if (this.n != null) {
         EntityPlayer entityplayer = commandlistenerwrapper.l().ac().a(this.n);
         return (List<EntityPlayer>)(entityplayer == null ? Collections.emptyList() : Lists.newArrayList(new EntityPlayer[]{entityplayer}));
      } else {
         Vec3D vec3d = this.i.apply(commandlistenerwrapper.d());
         Predicate<Entity> predicate = this.a(vec3d);
         if (this.l) {
            if (commandlistenerwrapper.f() instanceof EntityPlayer entityplayer1 && predicate.test(entityplayer1)) {
               return Lists.newArrayList(new EntityPlayer[]{entityplayer1});
            }

            return Collections.emptyList();
         } else {
            int i = this.f();
            Object object;
            if (this.d()) {
               object = commandlistenerwrapper.e().a(predicate, i);
            } else {
               object = Lists.newArrayList();

               for(EntityPlayer entityplayer2 : commandlistenerwrapper.l().ac().t()) {
                  if (predicate.test(entityplayer2)) {
                     ((List)object).add(entityplayer2);
                     if (((List)object).size() >= i) {
                        return (List<EntityPlayer>)object;
                     }
                  }
               }
            }

            return this.a(vec3d, (List<EntityPlayer>)object);
         }
      }
   }

   private Predicate<Entity> a(Vec3D vec3d) {
      Predicate<Entity> predicate = this.g;
      if (this.j != null) {
         AxisAlignedBB axisalignedbb = this.j.c(vec3d);
         predicate = predicate.and(entity -> axisalignedbb.c(entity.cD()));
      }

      if (!this.h.c()) {
         predicate = predicate.and(entity -> this.h.e(entity.e(vec3d)));
      }

      return predicate;
   }

   private <T extends Entity> List<T> a(Vec3D vec3d, List<T> list) {
      if (list.size() > 1) {
         this.k.accept(vec3d, list);
      }

      return list.subList(0, Math.min(this.d, list.size()));
   }

   public static IChatBaseComponent a(List<? extends Entity> list) {
      return ChatComponentUtils.b(list, Entity::G_);
   }
}
