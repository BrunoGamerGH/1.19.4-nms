package net.minecraft.world.phys.shapes;

public interface OperatorBoolean {
   OperatorBoolean a = (var0, var1) -> false;
   OperatorBoolean b = (var0, var1) -> !var0 && !var1;
   OperatorBoolean c = (var0, var1) -> var1 && !var0;
   OperatorBoolean d = (var0, var1) -> !var0;
   OperatorBoolean e = (var0, var1) -> var0 && !var1;
   OperatorBoolean f = (var0, var1) -> !var1;
   OperatorBoolean g = (var0, var1) -> var0 != var1;
   OperatorBoolean h = (var0, var1) -> !var0 || !var1;
   OperatorBoolean i = (var0, var1) -> var0 && var1;
   OperatorBoolean j = (var0, var1) -> var0 == var1;
   OperatorBoolean k = (var0, var1) -> var1;
   OperatorBoolean l = (var0, var1) -> !var0 || var1;
   OperatorBoolean m = (var0, var1) -> var0;
   OperatorBoolean n = (var0, var1) -> var0 || !var1;
   OperatorBoolean o = (var0, var1) -> var0 || var1;
   OperatorBoolean p = (var0, var1) -> true;

   boolean apply(boolean var1, boolean var2);
}
