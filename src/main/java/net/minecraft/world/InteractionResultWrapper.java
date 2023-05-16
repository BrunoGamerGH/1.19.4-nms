package net.minecraft.world;

public class InteractionResultWrapper<T> {
   private final EnumInteractionResult a;
   private final T b;

   public InteractionResultWrapper(EnumInteractionResult var0, T var1) {
      this.a = var0;
      this.b = var1;
   }

   public EnumInteractionResult a() {
      return this.a;
   }

   public T b() {
      return this.b;
   }

   public static <T> InteractionResultWrapper<T> a(T var0) {
      return new InteractionResultWrapper<>(EnumInteractionResult.a, var0);
   }

   public static <T> InteractionResultWrapper<T> b(T var0) {
      return new InteractionResultWrapper<>(EnumInteractionResult.b, var0);
   }

   public static <T> InteractionResultWrapper<T> c(T var0) {
      return new InteractionResultWrapper<>(EnumInteractionResult.d, var0);
   }

   public static <T> InteractionResultWrapper<T> d(T var0) {
      return new InteractionResultWrapper<>(EnumInteractionResult.e, var0);
   }

   public static <T> InteractionResultWrapper<T> a(T var0, boolean var1) {
      return var1 ? a(var0) : b(var0);
   }
}
