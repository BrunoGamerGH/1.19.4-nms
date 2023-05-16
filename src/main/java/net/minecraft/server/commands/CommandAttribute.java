package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.UUID;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ArgumentUUID;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class CommandAttribute {
   private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.attribute.failed.entity", var0));
   private static final Dynamic2CommandExceptionType b = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("commands.attribute.failed.no_attribute", var0, var1)
   );
   private static final Dynamic3CommandExceptionType c = new Dynamic3CommandExceptionType(
      (var0, var1, var2) -> IChatBaseComponent.a("commands.attribute.failed.no_modifier", var1, var0, var2)
   );
   private static final Dynamic3CommandExceptionType d = new Dynamic3CommandExceptionType(
      (var0, var1, var2) -> IChatBaseComponent.a("commands.attribute.failed.modifier_already_present", var2, var1, var0)
   );

   public static void a(CommandDispatcher<CommandListenerWrapper> var0, CommandBuildContext var1) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("attribute")
               .requires(var0x -> var0x.c(2)))
            .then(
               net.minecraft.commands.CommandDispatcher.a("target", ArgumentEntity.a())
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                 "attribute", ResourceArgument.a(var1, Registries.b)
                              )
                              .then(
                                 ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("get")
                                       .executes(
                                          var0x -> a(
                                                (CommandListenerWrapper)var0x.getSource(),
                                                ArgumentEntity.a(var0x, "target"),
                                                ResourceArgument.a(var0x, "attribute"),
                                                1.0
                                             )
                                       ))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("scale", DoubleArgumentType.doubleArg())
                                          .executes(
                                             var0x -> a(
                                                   (CommandListenerWrapper)var0x.getSource(),
                                                   ArgumentEntity.a(var0x, "target"),
                                                   ResourceArgument.a(var0x, "attribute"),
                                                   DoubleArgumentType.getDouble(var0x, "scale")
                                                )
                                          )
                                    )
                              ))
                           .then(
                              ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("base")
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("set")
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("value", DoubleArgumentType.doubleArg())
                                                .executes(
                                                   var0x -> c(
                                                         (CommandListenerWrapper)var0x.getSource(),
                                                         ArgumentEntity.a(var0x, "target"),
                                                         ResourceArgument.a(var0x, "attribute"),
                                                         DoubleArgumentType.getDouble(var0x, "value")
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("get")
                                          .executes(
                                             var0x -> b(
                                                   (CommandListenerWrapper)var0x.getSource(),
                                                   ArgumentEntity.a(var0x, "target"),
                                                   ResourceArgument.a(var0x, "attribute"),
                                                   1.0
                                                )
                                          ))
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("scale", DoubleArgumentType.doubleArg())
                                             .executes(
                                                var0x -> b(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      ArgumentEntity.a(var0x, "target"),
                                                      ResourceArgument.a(var0x, "attribute"),
                                                      DoubleArgumentType.getDouble(var0x, "scale")
                                                   )
                                             )
                                       )
                                 )
                           ))
                        .then(
                           ((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("modifier")
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("add")
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("uuid", ArgumentUUID.a())
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("name", StringArgumentType.string())
                                                      .then(
                                                         ((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                                     "value", DoubleArgumentType.doubleArg()
                                                                  )
                                                                  .then(
                                                                     net.minecraft.commands.CommandDispatcher.a("add")
                                                                        .executes(
                                                                           var0x -> a(
                                                                                 (CommandListenerWrapper)var0x.getSource(),
                                                                                 ArgumentEntity.a(var0x, "target"),
                                                                                 ResourceArgument.a(var0x, "attribute"),
                                                                                 ArgumentUUID.a(var0x, "uuid"),
                                                                                 StringArgumentType.getString(var0x, "name"),
                                                                                 DoubleArgumentType.getDouble(var0x, "value"),
                                                                                 AttributeModifier.Operation.a
                                                                              )
                                                                        )
                                                                  ))
                                                               .then(
                                                                  net.minecraft.commands.CommandDispatcher.a("multiply")
                                                                     .executes(
                                                                        var0x -> a(
                                                                              (CommandListenerWrapper)var0x.getSource(),
                                                                              ArgumentEntity.a(var0x, "target"),
                                                                              ResourceArgument.a(var0x, "attribute"),
                                                                              ArgumentUUID.a(var0x, "uuid"),
                                                                              StringArgumentType.getString(var0x, "name"),
                                                                              DoubleArgumentType.getDouble(var0x, "value"),
                                                                              AttributeModifier.Operation.c
                                                                           )
                                                                     )
                                                               ))
                                                            .then(
                                                               net.minecraft.commands.CommandDispatcher.a("multiply_base")
                                                                  .executes(
                                                                     var0x -> a(
                                                                           (CommandListenerWrapper)var0x.getSource(),
                                                                           ArgumentEntity.a(var0x, "target"),
                                                                           ResourceArgument.a(var0x, "attribute"),
                                                                           ArgumentUUID.a(var0x, "uuid"),
                                                                           StringArgumentType.getString(var0x, "name"),
                                                                           DoubleArgumentType.getDouble(var0x, "value"),
                                                                           AttributeModifier.Operation.b
                                                                        )
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("remove")
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("uuid", ArgumentUUID.a())
                                             .executes(
                                                var0x -> a(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      ArgumentEntity.a(var0x, "target"),
                                                      ResourceArgument.a(var0x, "attribute"),
                                                      ArgumentUUID.a(var0x, "uuid")
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("value")
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("get")
                                          .then(
                                             ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("uuid", ArgumentUUID.a())
                                                   .executes(
                                                      var0x -> a(
                                                            (CommandListenerWrapper)var0x.getSource(),
                                                            ArgumentEntity.a(var0x, "target"),
                                                            ResourceArgument.a(var0x, "attribute"),
                                                            ArgumentUUID.a(var0x, "uuid"),
                                                            1.0
                                                         )
                                                   ))
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("scale", DoubleArgumentType.doubleArg())
                                                      .executes(
                                                         var0x -> a(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               ArgumentEntity.a(var0x, "target"),
                                                               ResourceArgument.a(var0x, "attribute"),
                                                               ArgumentUUID.a(var0x, "uuid"),
                                                               DoubleArgumentType.getDouble(var0x, "scale")
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static AttributeModifiable a(Entity var0, Holder<AttributeBase> var1) throws CommandSyntaxException {
      AttributeModifiable var2 = a(var0).eI().a(var1);
      if (var2 == null) {
         throw b.create(var0.Z(), a(var1));
      } else {
         return var2;
      }
   }

   private static EntityLiving a(Entity var0) throws CommandSyntaxException {
      if (!(var0 instanceof EntityLiving)) {
         throw a.create(var0.Z());
      } else {
         return (EntityLiving)var0;
      }
   }

   private static EntityLiving b(Entity var0, Holder<AttributeBase> var1) throws CommandSyntaxException {
      EntityLiving var2 = a(var0);
      if (!var2.eI().b(var1)) {
         throw b.create(var0.Z(), a(var1));
      } else {
         return var2;
      }
   }

   private static int a(CommandListenerWrapper var0, Entity var1, Holder<AttributeBase> var2, double var3) throws CommandSyntaxException {
      EntityLiving var5 = b(var1, var2);
      double var6 = var5.a(var2);
      var0.a(IChatBaseComponent.a("commands.attribute.value.get.success", a(var2), var1.Z(), var6), false);
      return (int)(var6 * var3);
   }

   private static int b(CommandListenerWrapper var0, Entity var1, Holder<AttributeBase> var2, double var3) throws CommandSyntaxException {
      EntityLiving var5 = b(var1, var2);
      double var6 = var5.b(var2);
      var0.a(IChatBaseComponent.a("commands.attribute.base_value.get.success", a(var2), var1.Z(), var6), false);
      return (int)(var6 * var3);
   }

   private static int a(CommandListenerWrapper var0, Entity var1, Holder<AttributeBase> var2, UUID var3, double var4) throws CommandSyntaxException {
      EntityLiving var6 = b(var1, var2);
      AttributeMapBase var7 = var6.eI();
      if (!var7.a(var2, var3)) {
         throw c.create(var1.Z(), a(var2), var3);
      } else {
         double var8 = var7.b(var2, var3);
         var0.a(IChatBaseComponent.a("commands.attribute.modifier.value.get.success", var3, a(var2), var1.Z(), var8), false);
         return (int)(var8 * var4);
      }
   }

   private static int c(CommandListenerWrapper var0, Entity var1, Holder<AttributeBase> var2, double var3) throws CommandSyntaxException {
      a(var1, var2).a(var3);
      var0.a(IChatBaseComponent.a("commands.attribute.base_value.set.success", a(var2), var1.Z(), var3), false);
      return 1;
   }

   private static int a(
      CommandListenerWrapper var0, Entity var1, Holder<AttributeBase> var2, UUID var3, String var4, double var5, AttributeModifier.Operation var7
   ) throws CommandSyntaxException {
      AttributeModifiable var8 = a(var1, var2);
      AttributeModifier var9 = new AttributeModifier(var3, var4, var5, var7);
      if (var8.a(var9)) {
         throw d.create(var1.Z(), a(var2), var3);
      } else {
         var8.c(var9);
         var0.a(IChatBaseComponent.a("commands.attribute.modifier.add.success", var3, a(var2), var1.Z()), false);
         return 1;
      }
   }

   private static int a(CommandListenerWrapper var0, Entity var1, Holder<AttributeBase> var2, UUID var3) throws CommandSyntaxException {
      AttributeModifiable var4 = a(var1, var2);
      if (var4.c(var3)) {
         var0.a(IChatBaseComponent.a("commands.attribute.modifier.remove.success", var3, a(var2), var1.Z()), false);
         return 1;
      } else {
         throw c.create(var1.Z(), a(var2), var3);
      }
   }

   private static IChatBaseComponent a(Holder<AttributeBase> var0) {
      return IChatBaseComponent.c(var0.a().c());
   }
}
