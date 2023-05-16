package net.minecraft.world.entity.ai.behavior.declarative;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.IdF;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.OptionalBox;
import com.mojang.datafixers.kinds.OptionalBox.Mu;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Unit;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class BehaviorBuilder<E extends EntityLiving, M> implements App<BehaviorBuilder.c<E>, M> {
   private final BehaviorBuilder.e<E, M> a;

   public static <E extends EntityLiving, M> BehaviorBuilder<E, M> a(App<BehaviorBuilder.c<E>, M> var0) {
      return (BehaviorBuilder<E, M>)var0;
   }

   public static <E extends EntityLiving> BehaviorBuilder.b<E> a() {
      return new BehaviorBuilder.b<>();
   }

   public static <E extends EntityLiving> OneShot<E> a(Function<BehaviorBuilder.b<E>, ? extends App<BehaviorBuilder.c<E>, Trigger<E>>> var0) {
      final BehaviorBuilder.e<E, Trigger<E>> var1 = b((App<BehaviorBuilder.c<E>, Trigger<E>>)var0.apply(a()));
      return new OneShot<E>() {
         @Override
         public boolean trigger(WorldServer var0, E var1x, long var2) {
            Trigger<E> var4 = var1.a(var0, var1, var2);
            return var4 == null ? false : var4.trigger(var0, var1, var2);
         }

         @Override
         public String b() {
            return "OneShot[" + var1.a() + "]";
         }

         @Override
         public String toString() {
            return this.b();
         }
      };
   }

   public static <E extends EntityLiving> OneShot<E> a(Trigger<? super E> var0, Trigger<? super E> var1) {
      return a(
         (Function<BehaviorBuilder.b<E>, ? extends App<BehaviorBuilder.c<E>, Trigger<E>>>)(var2 -> var2.group(var2.a(var0))
               .apply(var2, var1xx -> var1::trigger))
      );
   }

   public static <E extends EntityLiving> OneShot<E> a(Predicate<E> var0, OneShot<? super E> var1) {
      return a(a(var0), var1);
   }

   public static <E extends EntityLiving> OneShot<E> a(Predicate<E> var0) {
      return a(
         (Function<BehaviorBuilder.b<E>, ? extends App<BehaviorBuilder.c<E>, Trigger<E>>>)(var1 -> var1.a((Trigger<E>)((var1x, var2, var3) -> var0.test(var2))))
      );
   }

   public static <E extends EntityLiving> OneShot<E> a(BiPredicate<WorldServer, E> var0) {
      return a(
         (Function<BehaviorBuilder.b<E>, ? extends App<BehaviorBuilder.c<E>, Trigger<E>>>)(var1 -> var1.a(
               (Trigger<E>)((var1x, var2, var3) -> var0.test(var1x, var2))
            ))
      );
   }

   static <E extends EntityLiving, M> BehaviorBuilder.e<E, M> b(App<BehaviorBuilder.c<E>, M> var0) {
      return a(var0).a;
   }

   BehaviorBuilder(BehaviorBuilder.e<E, M> var0) {
      this.a = var0;
   }

   static <E extends EntityLiving, M> BehaviorBuilder<E, M> a(BehaviorBuilder.e<E, M> var0) {
      return new BehaviorBuilder<>(var0);
   }

   static final class a<E extends EntityLiving, A> extends BehaviorBuilder<E, A> {
      a(A var0) {
         this(var0, () -> "C[" + var0 + "]");
      }

      a(final A var0, final Supplier<String> var1) {
         super(new BehaviorBuilder.e<E, A>() {
            @Override
            public A a(WorldServer var0x, E var1x, long var2) {
               return var0;
            }

            @Override
            public String a() {
               return var1.get();
            }

            @Override
            public String toString() {
               return this.a();
            }
         });
      }
   }

   public static final class b<E extends EntityLiving> implements Applicative<BehaviorBuilder.c<E>, BehaviorBuilder.b.a<E>> {
      public <Value> Optional<Value> a(MemoryAccessor<Mu, Value> var0) {
         return OptionalBox.unbox(var0.a());
      }

      public <Value> Value b(MemoryAccessor<com.mojang.datafixers.kinds.IdF.Mu, Value> var0) {
         return (Value)IdF.get(var0.a());
      }

      public <Value> BehaviorBuilder<E, MemoryAccessor<Mu, Value>> a(MemoryModuleType<Value> var0) {
         return new BehaviorBuilder.d<>(new MemoryCondition.c<>(var0));
      }

      public <Value> BehaviorBuilder<E, MemoryAccessor<com.mojang.datafixers.kinds.IdF.Mu, Value>> b(MemoryModuleType<Value> var0) {
         return new BehaviorBuilder.d<>(new MemoryCondition.b<>(var0));
      }

      public <Value> BehaviorBuilder<E, MemoryAccessor<com.mojang.datafixers.kinds.Const.Mu<Unit>, Value>> c(MemoryModuleType<Value> var0) {
         return new BehaviorBuilder.d<>(new MemoryCondition.a<>(var0));
      }

      public BehaviorBuilder<E, Unit> a(Trigger<? super E> var0) {
         return new BehaviorBuilder.f<>(var0);
      }

      public <A> BehaviorBuilder<E, A> a(A var0) {
         return new BehaviorBuilder.a<>(var0);
      }

      public <A> BehaviorBuilder<E, A> a(Supplier<String> var0, A var1) {
         return new BehaviorBuilder.a<>(var1, var0);
      }

      public <A, R> Function<App<BehaviorBuilder.c<E>, A>, App<BehaviorBuilder.c<E>, R>> lift1(App<BehaviorBuilder.c<E>, Function<A, R>> var0) {
         return var1x -> {
            final BehaviorBuilder.e<E, A> var2 = BehaviorBuilder.b(var1x);
            final BehaviorBuilder.e<E, Function<A, R>> var3 = BehaviorBuilder.b(var0);
            return BehaviorBuilder.a(new BehaviorBuilder.e<E, R>() {
               @Override
               public R a(WorldServer var0, E var1, long var2x) {
                  A var4 = (A)var2.a(var0, var1, var2);
                  if (var4 == null) {
                     return null;
                  } else {
                     Function<A, R> var5 = (Function)var3.a(var0, var1, var2);
                     return (R)(var5 == null ? null : var5.apply(var4));
                  }
               }

               @Override
               public String a() {
                  return var3.a() + " * " + var2.a();
               }

               @Override
               public String toString() {
                  return this.a();
               }
            });
         };
      }

      public <T, R> BehaviorBuilder<E, R> a(final Function<? super T, ? extends R> var0, App<BehaviorBuilder.c<E>, T> var1) {
         final BehaviorBuilder.e<E, T> var2 = BehaviorBuilder.b(var1);
         return BehaviorBuilder.a(new BehaviorBuilder.e<E, R>() {
            @Override
            public R a(WorldServer var0x, E var1, long var2x) {
               T var4 = var2.a(var0, var1, var2);
               return var4 == null ? null : var0.apply(var4);
            }

            @Override
            public String a() {
               return var2.a() + ".map[" + var0 + "]";
            }

            @Override
            public String toString() {
               return this.a();
            }
         });
      }

      public <A, B, R> BehaviorBuilder<E, R> a(
         App<BehaviorBuilder.c<E>, BiFunction<A, B, R>> var0, App<BehaviorBuilder.c<E>, A> var1, App<BehaviorBuilder.c<E>, B> var2
      ) {
         final BehaviorBuilder.e<E, A> var3 = BehaviorBuilder.b(var1);
         final BehaviorBuilder.e<E, B> var4 = BehaviorBuilder.b(var2);
         final BehaviorBuilder.e<E, BiFunction<A, B, R>> var5 = BehaviorBuilder.b(var0);
         return BehaviorBuilder.a(new BehaviorBuilder.e<E, R>() {
            @Override
            public R a(WorldServer var0, E var1, long var2) {
               A var4 = var3.a(var0, var1, var2);
               if (var4 == null) {
                  return null;
               } else {
                  B var5 = var4.a(var0, var1, var2);
                  if (var5 == null) {
                     return null;
                  } else {
                     BiFunction<A, B, R> var6 = var5.a(var0, var1, var2);
                     return var6 == null ? null : var6.apply(var4, var5);
                  }
               }
            }

            @Override
            public String a() {
               return var5.a() + " * " + var3.a() + " * " + var4.a();
            }

            @Override
            public String toString() {
               return this.a();
            }
         });
      }

      public <T1, T2, T3, R> BehaviorBuilder<E, R> a(
         App<BehaviorBuilder.c<E>, Function3<T1, T2, T3, R>> var0,
         App<BehaviorBuilder.c<E>, T1> var1,
         App<BehaviorBuilder.c<E>, T2> var2,
         App<BehaviorBuilder.c<E>, T3> var3
      ) {
         final BehaviorBuilder.e<E, T1> var4 = BehaviorBuilder.b(var1);
         final BehaviorBuilder.e<E, T2> var5 = BehaviorBuilder.b(var2);
         final BehaviorBuilder.e<E, T3> var6 = BehaviorBuilder.b(var3);
         final BehaviorBuilder.e<E, Function3<T1, T2, T3, R>> var7 = BehaviorBuilder.b(var0);
         return BehaviorBuilder.a(new BehaviorBuilder.e<E, R>() {
            @Override
            public R a(WorldServer var0, E var1, long var2) {
               T1 var4 = var4.a(var0, var1, var2);
               if (var4 == null) {
                  return null;
               } else {
                  T2 var5 = var5.a(var0, var1, var2);
                  if (var5 == null) {
                     return null;
                  } else {
                     T3 var6 = var6.a(var0, var1, var2);
                     if (var6 == null) {
                        return null;
                     } else {
                        Function3<T1, T2, T3, R> var7 = (Function3)var7.a(var0, var1, var2);
                        return (R)(var7 == null ? null : var7.apply(var4, var5, var6));
                     }
                  }
               }
            }

            @Override
            public String a() {
               return var7.a() + " * " + var4.a() + " * " + var5.a() + " * " + var6.a();
            }

            @Override
            public String toString() {
               return this.a();
            }
         });
      }

      public <T1, T2, T3, T4, R> BehaviorBuilder<E, R> a(
         App<BehaviorBuilder.c<E>, Function4<T1, T2, T3, T4, R>> var0,
         App<BehaviorBuilder.c<E>, T1> var1,
         App<BehaviorBuilder.c<E>, T2> var2,
         App<BehaviorBuilder.c<E>, T3> var3,
         App<BehaviorBuilder.c<E>, T4> var4
      ) {
         final BehaviorBuilder.e<E, T1> var5 = BehaviorBuilder.b(var1);
         final BehaviorBuilder.e<E, T2> var6 = BehaviorBuilder.b(var2);
         final BehaviorBuilder.e<E, T3> var7 = BehaviorBuilder.b(var3);
         final BehaviorBuilder.e<E, T4> var8 = BehaviorBuilder.b(var4);
         final BehaviorBuilder.e<E, Function4<T1, T2, T3, T4, R>> var9 = BehaviorBuilder.b(var0);
         return BehaviorBuilder.a(new BehaviorBuilder.e<E, R>() {
            @Override
            public R a(WorldServer var0, E var1, long var2) {
               T1 var4 = var5.a(var0, var1, var2);
               if (var4 == null) {
                  return null;
               } else {
                  T2 var5 = var6.a(var0, var1, var2);
                  if (var5 == null) {
                     return null;
                  } else {
                     T3 var6 = var7.a(var0, var1, var2);
                     if (var6 == null) {
                        return null;
                     } else {
                        T4 var7 = var8.a(var0, var1, var2);
                        if (var7 == null) {
                           return null;
                        } else {
                           Function4<T1, T2, T3, T4, R> var8 = (Function4)var9.a(var0, var1, var2);
                           return (R)(var8 == null ? null : var8.apply(var4, var5, var6, var7));
                        }
                     }
                  }
               }
            }

            @Override
            public String a() {
               return var9.a() + " * " + var5.a() + " * " + var6.a() + " * " + var7.a() + " * " + var8.a();
            }

            @Override
            public String toString() {
               return this.a();
            }
         });
      }

      static final class a<E extends EntityLiving> implements com.mojang.datafixers.kinds.Applicative.Mu {
         private a() {
         }
      }
   }

   public static final class c<E extends EntityLiving> implements K1 {
   }

   static final class d<E extends EntityLiving, F extends K1, Value> extends BehaviorBuilder<E, MemoryAccessor<F, Value>> {
      d(final MemoryCondition<F, Value> var0) {
         super(new BehaviorBuilder.e<E, MemoryAccessor<F, Value>>() {
            public MemoryAccessor<F, Value> b(WorldServer var0x, E var1, long var2) {
               BehaviorController<?> var4 = var1.dH();
               Optional<Value> var5 = var4.d(var0.a());
               return var5 == null ? null : var0.a(var4, var5);
            }

            @Override
            public String a() {
               return "M[" + var0 + "]";
            }

            @Override
            public String toString() {
               return this.a();
            }
         });
      }
   }

   interface e<E extends EntityLiving, R> {
      @Nullable
      R a(WorldServer var1, E var2, long var3);

      String a();
   }

   static final class f<E extends EntityLiving> extends BehaviorBuilder<E, Unit> {
      f(final Trigger<? super E> var0) {
         super(new BehaviorBuilder.e<E, Unit>() {
            @Nullable
            public Unit b(WorldServer var0x, E var1, long var2) {
               return var0.trigger(var0, var1, var2) ? Unit.INSTANCE : null;
            }

            @Override
            public String a() {
               return "T[" + var0 + "]";
            }
         });
      }
   }
}
