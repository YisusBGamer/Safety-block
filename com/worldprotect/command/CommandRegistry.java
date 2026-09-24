/*     */ package com.worldprotect.command;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.StringArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import com.worldprotect.WorldProtect;
/*     */ import com.worldprotect.flag.Flag;
/*     */ import com.worldprotect.flag.Flags;
/*     */ import com.worldprotect.integration.WorldEditIntegration;
/*     */ import com.worldprotect.region.ProtectedPolygonalRegion;
/*     */ import com.worldprotect.region.ProtectedRegion;
/*     */ import com.worldprotect.region.RegionManager;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class CommandRegistry {
/*     */   private static final SuggestionProvider<CommandSourceStack> REGION_SUGGESTIONS;
/*     */   
/*     */   static {
/*  34 */     REGION_SUGGESTIONS = ((context, builder) -> {
/*     */         RegionManager manager = getRegionManager(context);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         return (manager != null) ? SharedSuggestionProvider.m_82981_(manager.getRegions().stream().map(ProtectedRegion::getId), builder) : builder.buildFuture();
/*     */       });
/*     */ 
/*     */ 
/*     */     
/*  45 */     FLAG_SUGGESTIONS = ((context, builder) -> SharedSuggestionProvider.m_82970_(Flags.getAll().keySet(), builder));
/*     */ 
/*     */ 
/*     */     
/*  49 */     FLAG_VALUE_SUGGESTIONS = ((context, builder) -> {
/*     */         try {
/*     */           String flagName = StringArgumentType.getString(context, "flag");
/*     */           Flag<?> flag = Flags.get(flagName);
/*     */           if (flag instanceof com.worldprotect.flag.StateFlag) {
/*     */             return SharedSuggestionProvider.m_82970_(Arrays.asList(new String[] { "allow", "deny" }, ), builder);
/*     */           }
/*  56 */         } catch (Exception exception) {}
/*     */         return builder.buildFuture();
/*     */       });
/*     */   } private static final SuggestionProvider<CommandSourceStack> FLAG_SUGGESTIONS; private static final SuggestionProvider<CommandSourceStack> FLAG_VALUE_SUGGESTIONS;
/*     */   public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
/*  61 */     dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("rg")
/*  62 */         .requires(source -> source.m_6761_(0)))
/*  63 */         .then(Commands.m_82127_("define")
/*  64 */           .then(((RequiredArgumentBuilder)Commands.m_82129_("id", (ArgumentType)StringArgumentType.word())
/*  65 */             .executes(CommandRegistry::defineRegion))
/*  66 */             .then(Commands.m_82129_("owner", (ArgumentType)EntityArgument.m_91466_())
/*  67 */               .executes(CommandRegistry::defineRegionWithOwner)))))
/*  68 */         .then(Commands.m_82127_("claim")
/*  69 */           .then(Commands.m_82129_("id", (ArgumentType)StringArgumentType.word())
/*  70 */             .executes(CommandRegistry::claimRegion))))
/*  71 */         .then(Commands.m_82127_("remove")
/*  72 */           .then(Commands.m_82129_("id", (ArgumentType)StringArgumentType.word())
/*  73 */             .suggests(REGION_SUGGESTIONS)
/*  74 */             .executes(CommandRegistry::removeRegion))))
/*  75 */         .then(Commands.m_82127_("redefine")
/*  76 */           .then(Commands.m_82129_("id", (ArgumentType)StringArgumentType.word())
/*  77 */             .suggests(REGION_SUGGESTIONS)
/*  78 */             .executes(CommandRegistry::redefineRegion))))
/*  79 */         .then(((LiteralArgumentBuilder)Commands.m_82127_("info")
/*  80 */           .executes(CommandRegistry::infoHere))
/*  81 */           .then(Commands.m_82129_("id", (ArgumentType)StringArgumentType.word())
/*  82 */             .suggests(REGION_SUGGESTIONS)
/*  83 */             .executes(CommandRegistry::infoRegion))))
/*  84 */         .then(((LiteralArgumentBuilder)Commands.m_82127_("list")
/*  85 */           .executes(CommandRegistry::listRegions))
/*  86 */           .then(Commands.m_82129_("page", (ArgumentType)IntegerArgumentType.integer(1))
/*  87 */             .executes(CommandRegistry::listRegionsPage))))
/*  88 */         .then(Commands.m_82127_("flag")
/*  89 */           .then(Commands.m_82129_("id", (ArgumentType)StringArgumentType.word())
/*  90 */             .suggests(REGION_SUGGESTIONS)
/*  91 */             .then(((RequiredArgumentBuilder)Commands.m_82129_("flag", (ArgumentType)StringArgumentType.word())
/*  92 */               .suggests(FLAG_SUGGESTIONS)
/*  93 */               .executes(CommandRegistry::clearFlag))
/*  94 */               .then(Commands.m_82129_("value", (ArgumentType)StringArgumentType.greedyString())
/*  95 */                 .suggests(FLAG_VALUE_SUGGESTIONS)
/*  96 */                 .executes(CommandRegistry::setFlag))))))
/*  97 */         .then(Commands.m_82127_("addmember")
/*  98 */           .then(Commands.m_82129_("id", (ArgumentType)StringArgumentType.word())
/*  99 */             .suggests(REGION_SUGGESTIONS)
/* 100 */             .then(Commands.m_82129_("player", (ArgumentType)EntityArgument.m_91466_())
/* 101 */               .executes(CommandRegistry::addMember)))))
/* 102 */         .then(Commands.m_82127_("removemember")
/* 103 */           .then(Commands.m_82129_("id", (ArgumentType)StringArgumentType.word())
/* 104 */             .suggests(REGION_SUGGESTIONS)
/* 105 */             .then(Commands.m_82129_("player", (ArgumentType)EntityArgument.m_91466_())
/* 106 */               .executes(CommandRegistry::removeMember)))))
/* 107 */         .then(Commands.m_82127_("addowner")
/* 108 */           .then(Commands.m_82129_("id", (ArgumentType)StringArgumentType.word())
/* 109 */             .suggests(REGION_SUGGESTIONS)
/* 110 */             .then(Commands.m_82129_("player", (ArgumentType)EntityArgument.m_91466_())
/* 111 */               .executes(CommandRegistry::addOwner)))))
/* 112 */         .then(Commands.m_82127_("removeowner")
/* 113 */           .then(Commands.m_82129_("id", (ArgumentType)StringArgumentType.word())
/* 114 */             .suggests(REGION_SUGGESTIONS)
/* 115 */             .then(Commands.m_82129_("player", (ArgumentType)EntityArgument.m_91466_())
/* 116 */               .executes(CommandRegistry::removeOwner)))))
/* 117 */         .then(Commands.m_82127_("setpriority")
/* 118 */           .then(Commands.m_82129_("id", (ArgumentType)StringArgumentType.word())
/* 119 */             .suggests(REGION_SUGGESTIONS)
/* 120 */             .then(Commands.m_82129_("priority", (ArgumentType)IntegerArgumentType.integer())
/* 121 */               .executes(CommandRegistry::setPriority)))))
/* 122 */         .then(Commands.m_82127_("setparent")
/* 123 */           .then(Commands.m_82129_("id", (ArgumentType)StringArgumentType.word())
/* 124 */             .suggests(REGION_SUGGESTIONS)
/* 125 */             .then(Commands.m_82129_("parent", (ArgumentType)StringArgumentType.word())
/* 126 */               .suggests(REGION_SUGGESTIONS)
/* 127 */               .executes(CommandRegistry::setParent)))))
/* 128 */         .then(((LiteralArgumentBuilder)Commands.m_82127_("save")
/* 129 */           .requires(source -> source.m_6761_(2)))
/* 130 */           .executes(CommandRegistry::saveRegions)))
/* 131 */         .then(((LiteralArgumentBuilder)Commands.m_82127_("load")
/* 132 */           .requires(source -> source.m_6761_(2)))
/* 133 */           .executes(CommandRegistry::loadRegions)))
/* 134 */         .then(Commands.m_82127_("wand")
/* 135 */           .executes(CommandRegistry::giveWand)))
/* 136 */         .then(Commands.m_82127_("select")
/* 137 */           .then(Commands.m_82129_("id", (ArgumentType)StringArgumentType.word())
/* 138 */             .suggests(REGION_SUGGESTIONS)
/* 139 */             .executes(CommandRegistry::selectRegion))))
/* 140 */         .then(Commands.m_82127_("flags")
/* 141 */           .executes(CommandRegistry::listFlags)));
/*     */ 
/*     */     
/* 144 */     dispatcher.register((LiteralArgumentBuilder)Commands.m_82127_("region")
/* 145 */         .redirect(dispatcher.getRoot().getChild("rg")));
/*     */     
/* 147 */     dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("worldprotect")
/* 148 */         .requires(source -> source.m_6761_(0)))
/* 149 */         .then(Commands.m_82127_("version")
/* 150 */           .executes(CommandRegistry::showVersion)))
/* 151 */         .then(((LiteralArgumentBuilder)Commands.m_82127_("reload")
/* 152 */           .requires(source -> source.m_6761_(2)))
/* 153 */           .executes(CommandRegistry::reloadConfig)));
/*     */   }
/*     */   
/*     */   private static int defineRegion(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
/* 157 */     ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
/* 158 */     String id = StringArgumentType.getString(context, "id");
/*     */     
/* 160 */     return createRegion((CommandSourceStack)context.getSource(), player, id, player);
/*     */   }
/*     */   
/*     */   private static int defineRegionWithOwner(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
/* 164 */     ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
/* 165 */     String id = StringArgumentType.getString(context, "id");
/* 166 */     ServerPlayer owner = EntityArgument.m_91474_(context, "owner");
/*     */     
/* 168 */     return createRegion((CommandSourceStack)context.getSource(), player, id, owner);
/*     */   }
/*     */   
/*     */   private static int claimRegion(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
/* 172 */     ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
/* 173 */     String id = StringArgumentType.getString(context, "id");
/*     */     
/* 175 */     return createRegion((CommandSourceStack)context.getSource(), player, id, player);
/*     */   }
/*     */   private static int createRegion(CommandSourceStack source, ServerPlayer player, String id, ServerPlayer owner) {
/*     */     ProtectedPolygonalRegion protectedPolygonalRegion;
/* 179 */     RegionManager manager = WorldProtect.getInstance().getRegionManager((Level)player.m_284548_());
/* 180 */     if (manager == null) {
/* 181 */       source.m_81352_((Component)Component.m_237113_("Region manager not available"));
/* 182 */       return 0;
/*     */     } 
/*     */     
/* 185 */     if (manager.hasRegion(id)) {
/* 186 */       source.m_81352_((Component)Component.m_237113_("A region with ID '" + id + "' already exists"));
/* 187 */       return 0;
/*     */     } 
/*     */     
/* 190 */     WorldEditIntegration integration = WorldProtect.getInstance().getWorldEditIntegration();
/* 191 */     WorldEditIntegration.Selection selection = null;
/*     */     
/* 193 */     if (integration != null) {
/* 194 */       selection = integration.getSelection((Player)player);
/*     */     }
/*     */     
/* 197 */     if (selection == null || !selection.isComplete()) {
/* 198 */       source.m_81352_((Component)Component.m_237113_("Please make a selection first using WorldEdit or the region wand"));
/* 199 */       return 0;
/*     */     } 
/*     */ 
/*     */     
/* 203 */     if (selection.getType() == WorldEditIntegration.Selection.Type.CUBOID) {
/* 204 */       ProtectedCuboidRegion protectedCuboidRegion = new ProtectedCuboidRegion(id, selection.getMinimumPoint(), selection.getMaximumPoint());
/*     */     } else {
/*     */       
/* 207 */       protectedPolygonalRegion = new ProtectedPolygonalRegion(id, selection.getPolygonPoints(), selection.getMinY(), selection.getMaxY());
/*     */     } 
/*     */     
/* 210 */     protectedPolygonalRegion.addOwner(owner.m_20148_());
/* 211 */     manager.addRegion((ProtectedRegion)protectedPolygonalRegion);
/*     */     
/* 213 */     WorldProtect.getInstance().saveAllRegions();
/*     */     
/* 215 */     source.m_288197_(() -> Component.m_237113_("Region '" + id + "' created successfully").m_130940_(ChatFormatting.GREEN), true);
/*     */     
/* 217 */     source.m_288197_(() -> Component.m_237113_("Volume: " + region.getVolume() + " blocks").m_130940_(ChatFormatting.GRAY), false);
/*     */ 
/*     */     
/* 220 */     return 1;
/*     */   }
/*     */   
/*     */   private static int removeRegion(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
/* 224 */     CommandSourceStack source = (CommandSourceStack)context.getSource();
/* 225 */     String id = StringArgumentType.getString(context, "id");
/*     */     
/* 227 */     RegionManager manager = getRegionManager(context);
/* 228 */     if (manager == null) {
/* 229 */       source.m_81352_((Component)Component.m_237113_("Region manager not available"));
/* 230 */       return 0;
/*     */     } 
/*     */     
/* 233 */     ProtectedRegion region = manager.getRegion(id);
/* 234 */     if (region == null) {
/* 235 */       source.m_81352_((Component)Component.m_237113_("Region '" + id + "' not found"));
/* 236 */       return 0;
/*     */     } 
/*     */     
/* 239 */     ServerPlayer player = source.m_230896_();
/* 240 */     if (player != null && !source.m_6761_(2) && 
/* 241 */       !region.isOwner(player.m_20148_())) {
/* 242 */       source.m_81352_((Component)Component.m_237113_("You don't own this region"));
/* 243 */       return 0;
/*     */     } 
/*     */ 
/*     */     
/* 247 */     manager.removeRegion(id);
/* 248 */     WorldProtect.getInstance().saveAllRegions();
/*     */     
/* 250 */     source.m_288197_(() -> Component.m_237113_("Region '" + id + "' removed").m_130940_(ChatFormatting.YELLOW), true);
/*     */ 
/*     */     
/* 253 */     return 1;
/*     */   }
/*     */   private static int redefineRegion(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
/*     */     ProtectedPolygonalRegion protectedPolygonalRegion;
/* 257 */     ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
/* 258 */     String id = StringArgumentType.getString(context, "id");
/*     */     
/* 260 */     RegionManager manager = WorldProtect.getInstance().getRegionManager((Level)player.m_284548_());
/* 261 */     if (manager == null) {
/* 262 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region manager not available"));
/* 263 */       return 0;
/*     */     } 
/*     */     
/* 266 */     ProtectedRegion oldRegion = manager.getRegion(id);
/* 267 */     if (oldRegion == null) {
/* 268 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region '" + id + "' not found"));
/* 269 */       return 0;
/*     */     } 
/*     */     
/* 272 */     if (!((CommandSourceStack)context.getSource()).m_6761_(2) && !oldRegion.isOwner(player.m_20148_())) {
/* 273 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("You don't own this region"));
/* 274 */       return 0;
/*     */     } 
/*     */     
/* 277 */     WorldEditIntegration integration = WorldProtect.getInstance().getWorldEditIntegration();
/* 278 */     WorldEditIntegration.Selection selection = null;
/*     */     
/* 280 */     if (integration != null) {
/* 281 */       selection = integration.getSelection((Player)player);
/*     */     }
/*     */     
/* 284 */     if (selection == null || !selection.isComplete()) {
/* 285 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Please make a selection first"));
/* 286 */       return 0;
/*     */     } 
/*     */ 
/*     */     
/* 290 */     if (selection.getType() == WorldEditIntegration.Selection.Type.CUBOID) {
/* 291 */       ProtectedCuboidRegion protectedCuboidRegion = new ProtectedCuboidRegion(id, selection.getMinimumPoint(), selection.getMaximumPoint());
/*     */     } else {
/*     */       
/* 294 */       protectedPolygonalRegion = new ProtectedPolygonalRegion(id, selection.getPolygonPoints(), selection.getMinY(), selection.getMaxY());
/*     */     } 
/*     */     
/* 297 */     protectedPolygonalRegion.setPriority(oldRegion.getPriority());
/* 298 */     protectedPolygonalRegion.setParent(oldRegion.getParent());
/* 299 */     protectedPolygonalRegion.setFlags(oldRegion.getFlags());
/* 300 */     for (UUID uuid : oldRegion.getOwners()) {
/* 301 */       protectedPolygonalRegion.addOwner(uuid);
/*     */     }
/* 303 */     for (UUID uuid : oldRegion.getMembers()) {
/* 304 */       protectedPolygonalRegion.addMember(uuid);
/*     */     }
/*     */     
/* 307 */     manager.removeRegion(id);
/* 308 */     manager.addRegion((ProtectedRegion)protectedPolygonalRegion);
/* 309 */     WorldProtect.getInstance().saveAllRegions();
/*     */     
/* 311 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Region '" + id + "' redefined").m_130940_(ChatFormatting.GREEN), true);
/*     */ 
/*     */     
/* 314 */     return 1;
/*     */   }
/*     */   
/*     */   private static int infoHere(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
/* 318 */     ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
/*     */     
/* 320 */     RegionManager manager = WorldProtect.getInstance().getRegionManager((Level)player.m_284548_());
/* 321 */     if (manager == null) {
/* 322 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region manager not available"));
/* 323 */       return 0;
/*     */     } 
/*     */     
/* 326 */     Set<ProtectedRegion> regions = manager.getApplicableRegions(player.m_20183_());
/* 327 */     if (regions.isEmpty()) {
/* 328 */       ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("No regions at your location").m_130940_(ChatFormatting.YELLOW), false);
/*     */       
/* 330 */       return 1;
/*     */     } 
/*     */     
/* 333 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Regions at your location:").m_130940_(ChatFormatting.GOLD), false);
/*     */ 
/*     */     
/* 336 */     for (ProtectedRegion region : regions) {
/* 337 */       showRegionInfo((CommandSourceStack)context.getSource(), region);
/*     */     }
/*     */     
/* 340 */     return 1;
/*     */   }
/*     */   
/*     */   private static int infoRegion(CommandContext<CommandSourceStack> context) {
/* 344 */     String id = StringArgumentType.getString(context, "id");
/*     */     
/* 346 */     RegionManager manager = getRegionManager(context);
/* 347 */     if (manager == null) {
/* 348 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region manager not available"));
/* 349 */       return 0;
/*     */     } 
/*     */     
/* 352 */     ProtectedRegion region = manager.getRegion(id);
/* 353 */     if (region == null) {
/* 354 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region '" + id + "' not found"));
/* 355 */       return 0;
/*     */     } 
/*     */     
/* 358 */     showRegionInfo((CommandSourceStack)context.getSource(), region);
/* 359 */     return 1;
/*     */   }
/*     */   
/*     */   private static void showRegionInfo(CommandSourceStack source, ProtectedRegion region) {
/* 363 */     source.m_288197_(() -> Component.m_237113_("=== Region: " + region.getId() + " ===").m_130940_(ChatFormatting.GOLD), false);
/*     */ 
/*     */     
/* 366 */     source.m_288197_(() -> Component.m_237113_("Type: " + region.getType().name()).m_130940_(ChatFormatting.GRAY), false);
/*     */ 
/*     */     
/* 369 */     source.m_288197_(() -> Component.m_237113_("Priority: " + region.getPriority()).m_130940_(ChatFormatting.GRAY), false);
/*     */ 
/*     */     
/* 372 */     if (region.getParent() != null) {
/* 373 */       source.m_288197_(() -> Component.m_237113_("Parent: " + region.getParent().getId()).m_130940_(ChatFormatting.GRAY), false);
/*     */     }
/*     */ 
/*     */     
/* 377 */     BlockPos min = region.getMinimumPoint();
/* 378 */     BlockPos max = region.getMaximumPoint();
/* 379 */     source.m_288197_(() -> Component.m_237113_(String.format("Bounds: (%d, %d, %d) to (%d, %d, %d)", new Object[] { Integer.valueOf(min.m_123341_()), Integer.valueOf(min.m_123342_()), Integer.valueOf(min.m_123343_()), Integer.valueOf(max.m_123341_()), Integer.valueOf(max.m_123342_()), Integer.valueOf(max.m_123343_()) })).m_130940_(ChatFormatting.GRAY), false);
/*     */ 
/*     */ 
/*     */     
/* 383 */     source.m_288197_(() -> Component.m_237113_("Volume: " + region.getVolume() + " blocks").m_130940_(ChatFormatting.GRAY), false);
/*     */ 
/*     */     
/* 386 */     if (!region.getOwners().isEmpty() || !region.getOwnerGroups().isEmpty()) {
/* 387 */       StringBuilder owners = new StringBuilder("Owners: ");
/* 388 */       for (UUID uuid : region.getOwners()) {
/* 389 */         owners.append(uuid.toString().substring(0, 8)).append("... ");
/*     */       }
/* 391 */       for (String group : region.getOwnerGroups()) {
/* 392 */         owners.append("g:").append(group).append(" ");
/*     */       }
/* 394 */       String ownersStr = owners.toString();
/* 395 */       source.m_288197_(() -> Component.m_237113_(ownersStr).m_130940_(ChatFormatting.AQUA), false);
/*     */     } 
/*     */ 
/*     */     
/* 399 */     if (!region.getMembers().isEmpty() || !region.getMemberGroups().isEmpty()) {
/* 400 */       StringBuilder members = new StringBuilder("Members: ");
/* 401 */       for (UUID uuid : region.getMembers()) {
/* 402 */         members.append(uuid.toString().substring(0, 8)).append("... ");
/*     */       }
/* 404 */       for (String group : region.getMemberGroups()) {
/* 405 */         members.append("g:").append(group).append(" ");
/*     */       }
/* 407 */       String membersStr = members.toString();
/* 408 */       source.m_288197_(() -> Component.m_237113_(membersStr).m_130940_(ChatFormatting.GREEN), false);
/*     */     } 
/*     */ 
/*     */     
/* 412 */     Map<Flag<?>, Object> flags = region.getFlags();
/* 413 */     if (!flags.isEmpty()) {
/* 414 */       source.m_288197_(() -> Component.m_237113_("Flags:").m_130940_(ChatFormatting.YELLOW), false);
/*     */       
/* 416 */       for (Map.Entry<Flag<?>, Object> entry : flags.entrySet()) {
/* 417 */         String flagName = ((Flag)entry.getKey()).getName();
/* 418 */         String flagValue = entry.getValue().toString();
/* 419 */         source.m_288197_(() -> Component.m_237113_("  " + flagName + ": " + flagValue).m_130940_(ChatFormatting.WHITE), false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int listRegions(CommandContext<CommandSourceStack> context) {
/* 426 */     return listRegionsWithPage(context, 1);
/*     */   }
/*     */   
/*     */   private static int listRegionsPage(CommandContext<CommandSourceStack> context) {
/* 430 */     int page = IntegerArgumentType.getInteger(context, "page");
/* 431 */     return listRegionsWithPage(context, page);
/*     */   }
/*     */   
/*     */   private static int listRegionsWithPage(CommandContext<CommandSourceStack> context, int page) {
/* 435 */     RegionManager manager = getRegionManager(context);
/* 436 */     if (manager == null) {
/* 437 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region manager not available"));
/* 438 */       return 0;
/*     */     } 
/*     */     
/* 441 */     List<ProtectedRegion> regions = new ArrayList<>(manager.getRegions());
/* 442 */     regions.sort(Comparator.comparing(ProtectedRegion::getId));
/*     */     
/* 444 */     int pageSize = 10;
/* 445 */     int totalPages = (int)Math.ceil(regions.size() / pageSize);
/*     */     
/* 447 */     if (regions.isEmpty()) {
/* 448 */       ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("No regions defined").m_130940_(ChatFormatting.YELLOW), false);
/*     */       
/* 450 */       return 1;
/*     */     } 
/*     */     
/* 453 */     page = Math.max(1, Math.min(page, totalPages));
/* 454 */     int finalPage = page;
/*     */     
/* 456 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("=== Regions (Page " + finalPage + "/" + totalPages + ") ===").m_130940_(ChatFormatting.GOLD), false);
/*     */ 
/*     */     
/* 459 */     int start = (page - 1) * pageSize;
/* 460 */     int end = Math.min(start + pageSize, regions.size());
/*     */     
/* 462 */     for (int i = start; i < end; i++) {
/* 463 */       ProtectedRegion region = regions.get(i);
/*     */ 
/*     */       
/* 466 */       MutableComponent component = Component.m_237113_("- " + region.getId()).m_130940_(ChatFormatting.AQUA).m_130938_(style -> style.m_131142_(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rg info " + region.getId())).m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, Component.m_237113_("Click for info"))));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 471 */       component.m_7220_((Component)Component.m_237113_(" (priority: " + region.getPriority() + ")")
/* 472 */           .m_130940_(ChatFormatting.GRAY));
/*     */       
/* 474 */       ((CommandSourceStack)context.getSource()).m_288197_(() -> component, false);
/*     */     } 
/*     */     
/* 477 */     return 1;
/*     */   }
/*     */   
/*     */   private static int setFlag(CommandContext<CommandSourceStack> context) {
/* 481 */     String id = StringArgumentType.getString(context, "id");
/* 482 */     String flagName = StringArgumentType.getString(context, "flag");
/* 483 */     String value = StringArgumentType.getString(context, "value");
/*     */     
/* 485 */     RegionManager manager = getRegionManager(context);
/* 486 */     if (manager == null) {
/* 487 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region manager not available"));
/* 488 */       return 0;
/*     */     } 
/*     */     
/* 491 */     ProtectedRegion region = manager.getRegion(id);
/* 492 */     if (region == null) {
/* 493 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region '" + id + "' not found"));
/* 494 */       return 0;
/*     */     } 
/*     */     
/* 497 */     Flag<?> flag = Flags.get(flagName);
/* 498 */     if (flag == null) {
/* 499 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Unknown flag: " + flagName));
/* 500 */       return 0;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 505 */       Flag<Object> typedFlag = (Flag)flag;
/* 506 */       Object parsedValue = typedFlag.parseInput(value);
/* 507 */       region.setFlag(typedFlag, parsedValue);
/* 508 */       WorldProtect.getInstance().saveAllRegions();
/*     */       
/* 510 */       ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Flag '" + flagName + "' set to '" + value + "' on region '" + id + "'").m_130940_(ChatFormatting.GREEN), true);
/*     */     }
/* 512 */     catch (IllegalArgumentException e) {
/* 513 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Invalid value: " + e.getMessage()));
/* 514 */       return 0;
/*     */     } 
/*     */     
/* 517 */     return 1;
/*     */   }
/*     */   
/*     */   private static int clearFlag(CommandContext<CommandSourceStack> context) {
/* 521 */     String id = StringArgumentType.getString(context, "id");
/* 522 */     String flagName = StringArgumentType.getString(context, "flag");
/*     */     
/* 524 */     RegionManager manager = getRegionManager(context);
/* 525 */     if (manager == null) {
/* 526 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region manager not available"));
/* 527 */       return 0;
/*     */     } 
/*     */     
/* 530 */     ProtectedRegion region = manager.getRegion(id);
/* 531 */     if (region == null) {
/* 532 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region '" + id + "' not found"));
/* 533 */       return 0;
/*     */     } 
/*     */     
/* 536 */     Flag<?> flag = Flags.get(flagName);
/* 537 */     if (flag == null) {
/* 538 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Unknown flag: " + flagName));
/* 539 */       return 0;
/*     */     } 
/*     */ 
/*     */     
/* 543 */     Flag<Object> typedFlag = (Flag)flag;
/* 544 */     region.setFlag(typedFlag, null);
/* 545 */     WorldProtect.getInstance().saveAllRegions();
/*     */     
/* 547 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Flag '" + flagName + "' cleared on region '" + id + "'").m_130940_(ChatFormatting.YELLOW), true);
/*     */ 
/*     */     
/* 550 */     return 1;
/*     */   }
/*     */   
/*     */   private static int addMember(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
/* 554 */     String id = StringArgumentType.getString(context, "id");
/* 555 */     ServerPlayer target = EntityArgument.m_91474_(context, "player");
/*     */     
/* 557 */     RegionManager manager = getRegionManager(context);
/* 558 */     if (manager == null) {
/* 559 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region manager not available"));
/* 560 */       return 0;
/*     */     } 
/*     */     
/* 563 */     ProtectedRegion region = manager.getRegion(id);
/* 564 */     if (region == null) {
/* 565 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region '" + id + "' not found"));
/* 566 */       return 0;
/*     */     } 
/*     */     
/* 569 */     region.addMember(target.m_20148_());
/* 570 */     WorldProtect.getInstance().saveAllRegions();
/*     */     
/* 572 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Added " + target.m_7755_().getString() + " as member to region '" + id + "'").m_130940_(ChatFormatting.GREEN), true);
/*     */ 
/*     */     
/* 575 */     return 1;
/*     */   }
/*     */   
/*     */   private static int removeMember(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
/* 579 */     String id = StringArgumentType.getString(context, "id");
/* 580 */     ServerPlayer target = EntityArgument.m_91474_(context, "player");
/*     */     
/* 582 */     RegionManager manager = getRegionManager(context);
/* 583 */     if (manager == null) {
/* 584 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region manager not available"));
/* 585 */       return 0;
/*     */     } 
/*     */     
/* 588 */     ProtectedRegion region = manager.getRegion(id);
/* 589 */     if (region == null) {
/* 590 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region '" + id + "' not found"));
/* 591 */       return 0;
/*     */     } 
/*     */     
/* 594 */     region.removeMember(target.m_20148_());
/* 595 */     WorldProtect.getInstance().saveAllRegions();
/*     */     
/* 597 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Removed " + target.m_7755_().getString() + " from members of region '" + id + "'").m_130940_(ChatFormatting.YELLOW), true);
/*     */ 
/*     */     
/* 600 */     return 1;
/*     */   }
/*     */   
/*     */   private static int addOwner(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
/* 604 */     String id = StringArgumentType.getString(context, "id");
/* 605 */     ServerPlayer target = EntityArgument.m_91474_(context, "player");
/*     */     
/* 607 */     RegionManager manager = getRegionManager(context);
/* 608 */     if (manager == null) {
/* 609 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region manager not available"));
/* 610 */       return 0;
/*     */     } 
/*     */     
/* 613 */     ProtectedRegion region = manager.getRegion(id);
/* 614 */     if (region == null) {
/* 615 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region '" + id + "' not found"));
/* 616 */       return 0;
/*     */     } 
/*     */     
/* 619 */     region.addOwner(target.m_20148_());
/* 620 */     WorldProtect.getInstance().saveAllRegions();
/*     */     
/* 622 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Added " + target.m_7755_().getString() + " as owner to region '" + id + "'").m_130940_(ChatFormatting.GREEN), true);
/*     */ 
/*     */     
/* 625 */     return 1;
/*     */   }
/*     */   
/*     */   private static int removeOwner(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
/* 629 */     String id = StringArgumentType.getString(context, "id");
/* 630 */     ServerPlayer target = EntityArgument.m_91474_(context, "player");
/*     */     
/* 632 */     RegionManager manager = getRegionManager(context);
/* 633 */     if (manager == null) {
/* 634 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region manager not available"));
/* 635 */       return 0;
/*     */     } 
/*     */     
/* 638 */     ProtectedRegion region = manager.getRegion(id);
/* 639 */     if (region == null) {
/* 640 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region '" + id + "' not found"));
/* 641 */       return 0;
/*     */     } 
/*     */     
/* 644 */     region.removeOwner(target.m_20148_());
/* 645 */     WorldProtect.getInstance().saveAllRegions();
/*     */     
/* 647 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Removed " + target.m_7755_().getString() + " from owners of region '" + id + "'").m_130940_(ChatFormatting.YELLOW), true);
/*     */ 
/*     */     
/* 650 */     return 1;
/*     */   }
/*     */   
/*     */   private static int setPriority(CommandContext<CommandSourceStack> context) {
/* 654 */     String id = StringArgumentType.getString(context, "id");
/* 655 */     int priority = IntegerArgumentType.getInteger(context, "priority");
/*     */     
/* 657 */     RegionManager manager = getRegionManager(context);
/* 658 */     if (manager == null) {
/* 659 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region manager not available"));
/* 660 */       return 0;
/*     */     } 
/*     */     
/* 663 */     ProtectedRegion region = manager.getRegion(id);
/* 664 */     if (region == null) {
/* 665 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region '" + id + "' not found"));
/* 666 */       return 0;
/*     */     } 
/*     */     
/* 669 */     region.setPriority(priority);
/* 670 */     WorldProtect.getInstance().saveAllRegions();
/*     */     
/* 672 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Set priority of region '" + id + "' to " + priority).m_130940_(ChatFormatting.GREEN), true);
/*     */ 
/*     */     
/* 675 */     return 1;
/*     */   }
/*     */   
/*     */   private static int setParent(CommandContext<CommandSourceStack> context) {
/* 679 */     String id = StringArgumentType.getString(context, "id");
/* 680 */     String parentId = StringArgumentType.getString(context, "parent");
/*     */     
/* 682 */     RegionManager manager = getRegionManager(context);
/* 683 */     if (manager == null) {
/* 684 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region manager not available"));
/* 685 */       return 0;
/*     */     } 
/*     */     
/* 688 */     ProtectedRegion region = manager.getRegion(id);
/* 689 */     if (region == null) {
/* 690 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region '" + id + "' not found"));
/* 691 */       return 0;
/*     */     } 
/*     */     
/* 694 */     ProtectedRegion parent = manager.getRegion(parentId);
/* 695 */     if (parent == null) {
/* 696 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Parent region '" + parentId + "' not found"));
/* 697 */       return 0;
/*     */     } 
/*     */     
/*     */     try {
/* 701 */       region.setParent(parent);
/* 702 */       WorldProtect.getInstance().saveAllRegions();
/*     */       
/* 704 */       ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Set parent of region '" + id + "' to '" + parentId + "'").m_130940_(ChatFormatting.GREEN), true);
/*     */     }
/* 706 */     catch (IllegalArgumentException e) {
/* 707 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Cannot set parent: " + e.getMessage()));
/* 708 */       return 0;
/*     */     } 
/*     */     
/* 711 */     return 1;
/*     */   }
/*     */   
/*     */   private static int saveRegions(CommandContext<CommandSourceStack> context) {
/* 715 */     WorldProtect.getInstance().saveAllRegions();
/*     */     
/* 717 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("All regions saved!").m_130940_(ChatFormatting.GREEN), true);
/*     */ 
/*     */     
/* 720 */     return 1;
/*     */   }
/*     */   
/*     */   private static int loadRegions(CommandContext<CommandSourceStack> context) {
/* 724 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Regions will be reloaded on next server restart").m_130940_(ChatFormatting.YELLOW), true);
/*     */ 
/*     */     
/* 727 */     return 1;
/*     */   }
/*     */   
/*     */   private static int giveWand(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
/* 731 */     ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
/*     */     
/* 733 */     ItemStack wand = WandHandler.createWand();
/* 734 */     if (!player.m_150109_().m_36054_(wand)) {
/* 735 */       player.m_36176_(wand, false);
/*     */     }
/*     */     
/* 738 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("You received a region wand!").m_130940_(ChatFormatting.GREEN), false);
/*     */     
/* 740 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Left-click to set position 1, right-click for position 2").m_130940_(ChatFormatting.GRAY), false);
/*     */ 
/*     */     
/* 743 */     return 1;
/*     */   }
/*     */   
/*     */   private static int selectRegion(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
/* 747 */     ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
/* 748 */     String id = StringArgumentType.getString(context, "id");
/*     */     
/* 750 */     RegionManager manager = WorldProtect.getInstance().getRegionManager((Level)player.m_284548_());
/* 751 */     if (manager == null) {
/* 752 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region manager not available"));
/* 753 */       return 0;
/*     */     } 
/*     */     
/* 756 */     ProtectedRegion region = manager.getRegion(id);
/* 757 */     if (region == null) {
/* 758 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Region '" + id + "' not found"));
/* 759 */       return 0;
/*     */     } 
/*     */     
/* 762 */     if (region.getType() == ProtectedRegion.RegionType.GLOBAL) {
/* 763 */       ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_("Cannot select a global region"));
/* 764 */       return 0;
/*     */     } 
/*     */     
/* 767 */     WorldEditIntegration integration = WorldProtect.getInstance().getWorldEditIntegration();
/* 768 */     if (integration != null) {
/* 769 */       BlockPos min = region.getMinimumPoint();
/* 770 */       BlockPos max = region.getMaximumPoint();
/* 771 */       integration.setFallbackPos1((Player)player, min);
/* 772 */       integration.setFallbackPos2((Player)player, max);
/*     */     } 
/*     */     
/* 775 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Region '" + id + "' selected").m_130940_(ChatFormatting.GREEN), false);
/*     */ 
/*     */     
/* 778 */     return 1;
/*     */   }
/*     */   
/*     */   private static int listFlags(CommandContext<CommandSourceStack> context) {
/* 782 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("=== Available Flags ===").m_130940_(ChatFormatting.GOLD), false);
/*     */ 
/*     */     
/* 785 */     Map<String, Flag<?>> flags = Flags.getAll();
/* 786 */     List<String> sortedFlags = new ArrayList<>(flags.keySet());
/* 787 */     Collections.sort(sortedFlags);
/*     */     
/* 789 */     StringBuilder builder = new StringBuilder();
/* 790 */     for (int i = 0; i < sortedFlags.size(); i++) {
/* 791 */       if (i > 0) builder.append(", "); 
/* 792 */       builder.append(sortedFlags.get(i));
/*     */     } 
/*     */     
/* 795 */     String flagList = builder.toString();
/* 796 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_(flagList).m_130940_(ChatFormatting.WHITE), false);
/*     */ 
/*     */     
/* 799 */     return 1;
/*     */   }
/*     */   
/*     */   private static int showVersion(CommandContext<CommandSourceStack> context) {
/* 803 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("WorldProtect v1.0.0").m_130940_(ChatFormatting.GOLD), false);
/*     */     
/* 805 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Region protection mod for Minecraft 1.20.1").m_130940_(ChatFormatting.GRAY), false);
/*     */ 
/*     */     
/* 808 */     return 1;
/*     */   }
/*     */   
/*     */   private static int reloadConfig(CommandContext<CommandSourceStack> context) {
/* 812 */     ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Configuration reloaded").m_130940_(ChatFormatting.GREEN), true);
/*     */ 
/*     */     
/* 815 */     return 1;
/*     */   }
/*     */   
/*     */   private static RegionManager getRegionManager(CommandContext<CommandSourceStack> context) {
/*     */     try {
/* 820 */       ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
/* 821 */       return WorldProtect.getInstance().getRegionManager((Level)player.m_284548_());
/* 822 */     } catch (CommandSyntaxException e) {
/* 823 */       MinecraftServer server = ((CommandSourceStack)context.getSource()).m_81377_();
/* 824 */       ServerLevel level = server.m_129783_();
/* 825 */       return WorldProtect.getInstance().getRegionManager((Level)level);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\command\CommandRegistry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */