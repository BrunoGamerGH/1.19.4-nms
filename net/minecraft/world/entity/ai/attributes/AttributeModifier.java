package net.minecraft.world.entity.ai.attributes;

import com.mojang.logging.LogUtils;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import org.slf4j.Logger;

public class AttributeModifier {
   private static final Logger a = LogUtils.getLogger();
   private final double b;
   private final AttributeModifier.Operation c;
   private final Supplier<String> d;
   private final UUID e;

   public AttributeModifier(String var0, double var1, AttributeModifier.Operation var3) {
      this(MathHelper.a(RandomSource.c()), () -> var0, var1, var3);
   }

   public AttributeModifier(UUID var0, String var1, double var2, AttributeModifier.Operation var4) {
      this(var0, () -> var1, var2, var4);
   }

   public AttributeModifier(UUID var0, Supplier<String> var1, double var2, AttributeModifier.Operation var4) {
      this.e = var0;
      this.d = var1;
      this.b = var2;
      this.c = var4;
   }

   public UUID a() {
      return this.e;
   }

   public String b() {
      return this.d.get();
   }

   public AttributeModifier.Operation c() {
      return this.c;
   }

   public double d() {
      return this.b;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (var0 != null && this.getClass() == var0.getClass()) {
         AttributeModifier var1 = (AttributeModifier)var0;
         return Objects.equals(this.e, var1.e);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.e.hashCode();
   }

   @Override
   public String toString() {
      return "AttributeModifier{amount=" + this.b + ", operation=" + this.c + ", name='" + (String)this.d.get() + "', id=" + this.e + "}";
   }

   public NBTTagCompound e() {
      NBTTagCompound var0 = new NBTTagCompound();
      var0.a("Name", this.b());
      var0.a("Amount", this.b);
      var0.a("Operation", this.c.a());
      var0.a("UUID", this.e);
      return var0;
   }

   @Nullable
   public static AttributeModifier a(NBTTagCompound var0) {
      try {
         UUID var1 = var0.a("UUID");
         AttributeModifier.Operation var2 = AttributeModifier.Operation.a(var0.h("Operation"));
         return new AttributeModifier(var1, var0.l("Name"), var0.k("Amount"), var2);
      } catch (Exception var3) {
         a.warn("Unable to create attribute: {}", var3.getMessage());
         return null;
      }
   }

   public static enum Operation {
      a(0),
      b(1),
      c(2);

      private static final AttributeModifier.Operation[] d = new AttributeModifier.Operation[]{a, b, c};
      private final int e;

      private Operation(int var2) {
         this.e = var2;
      }

      public int a() {
         return this.e;
      }

      public static AttributeModifier.Operation a(int var0) {
         if (var0 >= 0 && var0 < d.length) {
            return d[var0];
         } else {
            throw new IllegalArgumentException("No operation with value " + var0);
         }
      }
   }
}
