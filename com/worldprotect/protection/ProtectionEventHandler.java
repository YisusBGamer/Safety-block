/*     */ package com.worldprotect.protection;
/*     */ 
/*     */ import com.worldprotect.WorldProtect;
/*     */ import com.worldprotect.config.WorldProtectConfig;
/*     */ import com.worldprotect.flag.Flag;
/*     */ import com.worldprotect.flag.Flags;
/*     */ import com.worldprotect.flag.StateFlag;
/*     */ import com.worldprotect.region.ProtectedRegion;
/*     */ import com.worldprotect.region.RegionManager;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.damagesource.DamageTypes;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraftforge.event.entity.EntityTeleportEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingDamageEvent;
/*     */ import net.minecraftforge.event.entity.living.MobSpawnEvent;
/*     */ import net.minecraftforge.event.entity.player.AttackEntityEvent;
/*     */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
/*     */ import net.minecraftforge.event.level.BlockEvent;
/*     */ import net.minecraftforge.event.level.ExplosionEvent;
/*     */ import net.minecraftforge.eventbus.api.EventPriority;
/*     */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProtectionEventHandler
/*     */ {
/*  47 */   private final Map<UUID, String> lastRegion = new HashMap<>();
/*  48 */   private final Map<UUID, Long> lastDenyMessage = new HashMap<>();
/*     */   private static final long DENY_MESSAGE_COOLDOWN = 1000L;
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void onBlockBreak(BlockEvent.BreakEvent event) {
/*  53 */     if (event.getLevel().m_5776_())
/*     */       return; 
/*  55 */     Player player = event.getPlayer();
/*  56 */     BlockPos pos = event.getPos();
/*     */     
/*  58 */     if (player.m_20310_(2) && ((Integer)WorldProtectConfig.bypassOpLevel.get()).intValue() <= 2) {
/*     */       return;
/*     */     }
/*     */     
/*  62 */     RegionManager manager = getRegionManager(event.getLevel());
/*  63 */     if (manager == null)
/*     */       return; 
/*  65 */     RegionManager.ApplicableRegionSet regions = manager.getApplicableRegionSet(pos);
/*     */     
/*  67 */     if (!regions.canBuild(player)) {
/*  68 */       event.setCanceled(true);
/*  69 */       sendDenyMessage(player, regions, "break blocks");
/*     */     } 
/*     */   }
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
/*     */     Player player;
/*  75 */     if (event.getLevel().m_5776_())
/*     */       return; 
/*  77 */     Entity entity = event.getEntity();
/*  78 */     if (entity instanceof Player) { player = (Player)entity; }
/*     */     else { return; }
/*  80 */      BlockPos pos = event.getPos();
/*     */     
/*  82 */     if (player.m_20310_(2) && ((Integer)WorldProtectConfig.bypassOpLevel.get()).intValue() <= 2) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     RegionManager manager = getRegionManager(event.getLevel());
/*  87 */     if (manager == null)
/*     */       return; 
/*  89 */     RegionManager.ApplicableRegionSet regions = manager.getApplicableRegionSet(pos);
/*     */     
/*  91 */     if (!regions.canBuild(player)) {
/*  92 */       event.setCanceled(true);
/*  93 */       sendDenyMessage(player, regions, "place blocks");
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
/*  99 */     if (event.getLevel().m_5776_())
/*     */       return; 
/* 101 */     Player player = event.getEntity();
/* 102 */     BlockPos pos = event.getPos();
/*     */     
/* 104 */     if (player.m_20310_(2) && ((Integer)WorldProtectConfig.bypassOpLevel.get()).intValue() <= 2) {
/*     */       return;
/*     */     }
/*     */     
/* 108 */     RegionManager manager = getRegionManager((LevelAccessor)event.getLevel());
/* 109 */     if (manager == null)
/*     */       return; 
/* 111 */     RegionManager.ApplicableRegionSet regions = manager.getApplicableRegionSet(pos);
/*     */     
/* 113 */     BlockState state = event.getLevel().m_8055_(pos);
/* 114 */     Block block = state.m_60734_();
/*     */     
/* 116 */     if (block instanceof net.minecraft.world.level.block.ChestBlock || block instanceof net.minecraft.world.level.block.BarrelBlock || block instanceof net.minecraft.world.level.block.ShulkerBoxBlock || block instanceof net.minecraft.world.level.block.HopperBlock || block instanceof net.minecraft.world.level.block.DispenserBlock || block instanceof net.minecraft.world.level.block.DropperBlock) {
/*     */ 
/*     */ 
/*     */       
/* 120 */       StateFlag.State chestAccess = (StateFlag.State)regions.queryValue(player, (Flag)Flags.CHEST_ACCESS);
/* 121 */       if (chestAccess == StateFlag.State.DENY && !regions.isMemberOfAll(player)) {
/* 122 */         event.setCanceled(true);
/* 123 */         sendDenyMessage(player, regions, "access containers");
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 128 */     StateFlag.State useState = (StateFlag.State)regions.queryValue(player, (Flag)Flags.USE);
/* 129 */     if (useState == StateFlag.State.DENY && !regions.isMemberOfAll(player) && (
/* 130 */       block instanceof net.minecraft.world.level.block.DoorBlock || block instanceof net.minecraft.world.level.block.TrapDoorBlock || block instanceof net.minecraft.world.level.block.FenceGateBlock || block instanceof net.minecraft.world.level.block.LeverBlock || block instanceof net.minecraft.world.level.block.ButtonBlock)) {
/*     */ 
/*     */       
/* 133 */       event.setCanceled(true);
/* 134 */       sendDenyMessage(player, regions, "use this");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void onPlayerAttackEntity(AttackEntityEvent event) {
/* 141 */     if (event.getEntity().m_9236_().m_5776_())
/*     */       return; 
/* 143 */     Player attacker = event.getEntity();
/* 144 */     Entity target = event.getTarget();
/*     */     
/* 146 */     if (attacker.m_20310_(2) && ((Integer)WorldProtectConfig.bypassOpLevel.get()).intValue() <= 2) {
/*     */       return;
/*     */     }
/*     */     
/* 150 */     RegionManager manager = getRegionManager((LevelAccessor)attacker.m_9236_());
/* 151 */     if (manager == null)
/*     */       return; 
/* 153 */     RegionManager.ApplicableRegionSet regions = manager.getApplicableRegionSet(target.m_20183_());
/*     */     
/* 155 */     if (target instanceof Player) {
/* 156 */       StateFlag.State pvpState = (StateFlag.State)regions.queryValue(attacker, (Flag)Flags.PVP);
/* 157 */       if (pvpState == StateFlag.State.DENY) {
/* 158 */         event.setCanceled(true);
/* 159 */         sendDenyMessage(attacker, regions, "PvP");
/*     */       } 
/* 161 */     } else if (target instanceof net.minecraft.world.entity.animal.Animal) {
/* 162 */       StateFlag.State damageAnimals = (StateFlag.State)regions.queryValue(attacker, (Flag)Flags.DAMAGE_ANIMALS);
/* 163 */       if (damageAnimals == StateFlag.State.DENY && !regions.isMemberOfAll(attacker)) {
/* 164 */         event.setCanceled(true);
/* 165 */         sendDenyMessage(attacker, regions, "harm animals");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void onLivingDamage(LivingDamageEvent event) {
/* 172 */     LivingEntity entity = event.getEntity();
/* 173 */     if (entity.m_9236_().m_5776_())
/*     */       return; 
/* 175 */     RegionManager manager = getRegionManager((LevelAccessor)entity.m_9236_());
/* 176 */     if (manager == null)
/*     */       return; 
/* 178 */     RegionManager.ApplicableRegionSet regions = manager.getApplicableRegionSet(entity.m_20183_());
/* 179 */     DamageSource source = event.getSource();
/* 180 */     Player player = (entity instanceof Player) ? (Player)entity : null;
/*     */     
/* 182 */     StateFlag.State invincibility = (StateFlag.State)regions.queryValue(player, (Flag)Flags.INVINCIBILITY);
/* 183 */     if (invincibility == StateFlag.State.ALLOW) {
/* 184 */       event.setCanceled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 188 */     if (source.m_276093_(DamageTypes.f_268671_)) {
/* 189 */       StateFlag.State fallDamage = (StateFlag.State)regions.queryValue(player, (Flag)Flags.FALL_DAMAGE);
/* 190 */       if (fallDamage == StateFlag.State.DENY) {
/* 191 */         event.setCanceled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 196 */     if (source.m_276093_(DamageTypes.f_268631_) || source.m_276093_(DamageTypes.f_268468_)) {
/* 197 */       StateFlag.State fireDamage = (StateFlag.State)regions.queryValue(player, (Flag)Flags.FIRE_DAMAGE);
/* 198 */       if (fireDamage == StateFlag.State.DENY) {
/* 199 */         event.setCanceled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 204 */     if (source.m_276093_(DamageTypes.f_268546_)) {
/* 205 */       StateFlag.State lavaDamage = (StateFlag.State)regions.queryValue(player, (Flag)Flags.LAVA_DAMAGE);
/* 206 */       if (lavaDamage == StateFlag.State.DENY) {
/* 207 */         event.setCanceled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 212 */     if (source.m_276093_(DamageTypes.f_268722_)) {
/* 213 */       StateFlag.State drowningDamage = (StateFlag.State)regions.queryValue(player, (Flag)Flags.DROWNING_DAMAGE);
/* 214 */       if (drowningDamage == StateFlag.State.DENY) {
/* 215 */         event.setCanceled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 220 */     if (source.m_276093_(DamageTypes.f_268612_)) {
/* 221 */       StateFlag.State suffocationDamage = (StateFlag.State)regions.queryValue(player, (Flag)Flags.SUFFOCATION_DAMAGE);
/* 222 */       if (suffocationDamage == StateFlag.State.DENY) {
/* 223 */         event.setCanceled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 228 */     if (source.m_276093_(DamageTypes.f_268724_)) {
/* 229 */       StateFlag.State voidDamage = (StateFlag.State)regions.queryValue(player, (Flag)Flags.VOID_DAMAGE);
/* 230 */       if (voidDamage == StateFlag.State.DENY) {
/* 231 */         event.setCanceled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 236 */     if (source.m_276093_(DamageTypes.f_268450_)) {
/* 237 */       StateFlag.State lightningDamage = (StateFlag.State)regions.queryValue(player, (Flag)Flags.LIGHTNING_DAMAGE);
/* 238 */       if (lightningDamage == StateFlag.State.DENY) {
/* 239 */         event.setCanceled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 244 */     if (source.m_7639_() instanceof Mob) {
/* 245 */       StateFlag.State mobDamage = (StateFlag.State)regions.queryValue(player, (Flag)Flags.MOB_DAMAGE);
/* 246 */       if (mobDamage == StateFlag.State.DENY) {
/* 247 */         event.setCanceled(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void onMobSpawn(MobSpawnEvent.FinalizeSpawn event) {
/* 254 */     if (event.getLevel().m_5776_())
/*     */       return; 
/* 256 */     Mob mob = event.getEntity();
/* 257 */     RegionManager manager = getRegionManager((LevelAccessor)event.getLevel().m_6018_());
/* 258 */     if (manager == null)
/*     */       return; 
/* 260 */     RegionManager.ApplicableRegionSet regions = manager.getApplicableRegionSet(mob.m_20183_());
/* 261 */     StateFlag.State mobSpawning = (StateFlag.State)regions.queryValue(null, (Flag)Flags.MOB_SPAWNING);
/*     */     
/* 263 */     if (mobSpawning == StateFlag.State.DENY) {
/* 264 */       event.setSpawnCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void onExplosion(ExplosionEvent.Start event) {
/* 270 */     Explosion explosion = event.getExplosion();
/* 271 */     Level level = event.getLevel();
/*     */     
/* 273 */     if (level.m_5776_())
/*     */       return; 
/* 275 */     RegionManager manager = getRegionManager((LevelAccessor)level);
/* 276 */     if (manager == null)
/*     */       return; 
/* 278 */     BlockPos pos = BlockPos.m_274446_((Position)explosion.getPosition());
/* 279 */     RegionManager.ApplicableRegionSet regions = manager.getApplicableRegionSet(pos);
/*     */     
/* 281 */     Entity source = explosion.m_253049_();
/*     */     
/* 283 */     if (source instanceof net.minecraft.world.entity.monster.Creeper) {
/* 284 */       StateFlag.State creeperExplosion = (StateFlag.State)regions.queryValue(null, (Flag)Flags.CREEPER_EXPLOSION);
/* 285 */       if (creeperExplosion == StateFlag.State.DENY) {
/* 286 */         event.setCanceled(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 291 */     StateFlag.State tntState = (StateFlag.State)regions.queryValue(null, (Flag)Flags.TNT);
/* 292 */     if (tntState == StateFlag.State.DENY) {
/* 293 */       event.setCanceled(true);
/*     */       
/*     */       return;
/*     */     } 
/* 297 */     StateFlag.State otherExplosion = (StateFlag.State)regions.queryValue(null, (Flag)Flags.OTHER_EXPLOSION);
/* 298 */     if (otherExplosion == StateFlag.State.DENY) {
/* 299 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void onEnderpearlTeleport(EntityTeleportEvent.EnderPearl event) {
/* 305 */     if (event.getEntity().m_9236_().m_5776_())
/*     */       return; 
/* 307 */     ServerPlayer serverPlayer = event.getPlayer();
/* 308 */     BlockPos targetPos = BlockPos.m_274561_(event.getTargetX(), event.getTargetY(), event.getTargetZ());
/*     */     
/* 310 */     RegionManager manager = getRegionManager((LevelAccessor)serverPlayer.m_9236_());
/* 311 */     if (manager == null)
/*     */       return; 
/* 313 */     RegionManager.ApplicableRegionSet regions = manager.getApplicableRegionSet(targetPos);
/* 314 */     StateFlag.State enderpearl = (StateFlag.State)regions.queryValue((Player)serverPlayer, (Flag)Flags.ENDERPEARL);
/*     */     
/* 316 */     if (enderpearl == StateFlag.State.DENY && !regions.isMemberOfAll((Player)serverPlayer)) {
/* 317 */       event.setCanceled(true);
/* 318 */       sendDenyMessage((Player)serverPlayer, regions, "use enderpearls");
/*     */     } 
/*     */   }
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void onChorusFruitTeleport(EntityTeleportEvent.ChorusFruit event) {
/*     */     Player player;
/* 324 */     if (event.getEntity().m_9236_().m_5776_())
/*     */       return; 
/* 326 */     Entity entity = event.getEntity(); if (entity instanceof Player) { player = (Player)entity; }
/*     */     else { return; }
/* 328 */      BlockPos targetPos = BlockPos.m_274561_(event.getTargetX(), event.getTargetY(), event.getTargetZ());
/*     */     
/* 330 */     RegionManager manager = getRegionManager((LevelAccessor)player.m_9236_());
/* 331 */     if (manager == null)
/*     */       return; 
/* 333 */     RegionManager.ApplicableRegionSet regions = manager.getApplicableRegionSet(targetPos);
/* 334 */     StateFlag.State chorusTeleport = (StateFlag.State)regions.queryValue(player, (Flag)Flags.CHORUS_TELEPORT);
/*     */     
/* 336 */     if (chorusTeleport == StateFlag.State.DENY && !regions.isMemberOfAll(player)) {
/* 337 */       event.setCanceled(true);
/* 338 */       sendDenyMessage(player, regions, "teleport here");
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEndermanGrief(BlockEvent.EntityPlaceEvent event) {
/* 344 */     if (event.getLevel().m_5776_())
/*     */       return; 
/* 346 */     Entity entity = event.getEntity();
/* 347 */     if (!(entity instanceof net.minecraft.world.entity.monster.EnderMan))
/*     */       return; 
/* 349 */     RegionManager manager = getRegionManager(event.getLevel());
/* 350 */     if (manager == null)
/*     */       return; 
/* 352 */     RegionManager.ApplicableRegionSet regions = manager.getApplicableRegionSet(event.getPos());
/* 353 */     StateFlag.State endermanGrief = (StateFlag.State)regions.queryValue(null, (Flag)Flags.ENDERMAN_GRIEF);
/*     */     
/* 355 */     if (endermanGrief == StateFlag.State.DENY) {
/* 356 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onFluidFlow(BlockEvent.FluidPlaceBlockEvent event) {
/* 362 */     if (event.getLevel().m_5776_())
/*     */       return; 
/* 364 */     RegionManager manager = getRegionManager(event.getLevel());
/* 365 */     if (manager == null)
/*     */       return; 
/* 367 */     RegionManager.ApplicableRegionSet regions = manager.getApplicableRegionSet(event.getPos());
/*     */     
/* 369 */     BlockState state = event.getNewState();
/* 370 */     if (state.m_60734_() instanceof net.minecraft.world.level.block.LiquidBlock) {
/* 371 */       String fluidName = state.m_60819_().m_76152_().toString();
/* 372 */       if (fluidName.contains("water")) {
/* 373 */         StateFlag.State waterFlow = (StateFlag.State)regions.queryValue(null, (Flag)Flags.WATER_FLOW);
/* 374 */         if (waterFlow == StateFlag.State.DENY) {
/* 375 */           event.setCanceled(true);
/*     */           return;
/*     */         } 
/* 378 */       } else if (fluidName.contains("lava")) {
/* 379 */         StateFlag.State lavaFlow = (StateFlag.State)regions.queryValue(null, (Flag)Flags.LAVA_FLOW);
/* 380 */         if (lavaFlow == StateFlag.State.DENY) {
/* 381 */           event.setCanceled(true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private RegionManager getRegionManager(LevelAccessor level) {
/* 388 */     if (level instanceof ServerLevel) { ServerLevel serverLevel = (ServerLevel)level;
/* 389 */       return WorldProtect.getInstance().getRegionManager((Level)serverLevel); }
/*     */     
/* 391 */     return null;
/*     */   }
/*     */   
/*     */   private void sendDenyMessage(Player player, RegionManager.ApplicableRegionSet regions, String action) {
/* 395 */     UUID uuid = player.m_20148_();
/* 396 */     long now = System.currentTimeMillis();
/*     */     
/* 398 */     if (this.lastDenyMessage.containsKey(uuid) && now - ((Long)this.lastDenyMessage.get(uuid)).longValue() < 1000L) {
/*     */       return;
/*     */     }
/* 401 */     this.lastDenyMessage.put(uuid, Long.valueOf(now));
/*     */     
/* 403 */     ProtectedRegion highestPriority = regions.getHighestPriorityRegion();
/* 404 */     String customMessage = null;
/*     */     
/* 406 */     if (highestPriority != null) {
/* 407 */       customMessage = (String)highestPriority.getFlag((Flag)Flags.DENY_MESSAGE);
/*     */     }
/*     */     
/* 410 */     if (customMessage != null && !customMessage.isEmpty()) {
/* 411 */       player.m_213846_((Component)Component.m_237113_(customMessage).m_130940_(ChatFormatting.RED));
/*     */     } else {
/* 413 */       player.m_213846_((Component)Component.m_237113_("You cannot " + action + " here!")
/* 414 */           .m_130940_(ChatFormatting.RED));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void checkRegionEntry(ServerPlayer player) {
/* 419 */     RegionManager manager = WorldProtect.getInstance().getRegionManager((Level)player.m_284548_());
/* 420 */     if (manager == null)
/*     */       return; 
/* 422 */     Set<ProtectedRegion> regions = manager.getApplicableRegions(player.m_20183_());
/*     */     
/* 424 */     String currentRegionId = regions.isEmpty() ? null : ((ProtectedRegion)regions.iterator().next()).getId();
/*     */     
/* 426 */     String lastRegionId = this.lastRegion.get(player.m_20148_());
/*     */     
/* 428 */     if (currentRegionId != null && !currentRegionId.equals(lastRegionId)) {
/* 429 */       ProtectedRegion region = manager.getRegion(currentRegionId);
/* 430 */       if (region != null) {
/* 431 */         String greeting = (String)region.getFlag((Flag)Flags.GREETING);
/* 432 */         if (greeting != null && !greeting.isEmpty()) {
/* 433 */           player.m_213846_((Component)Component.m_237113_(greeting)
/* 434 */               .m_130940_(ChatFormatting.YELLOW));
/*     */         }
/*     */         
/* 437 */         String greetingTitle = (String)region.getFlag((Flag)Flags.GREETING_TITLE);
/* 438 */         if (greetingTitle != null && !greetingTitle.isEmpty()) {
/* 439 */           player.m_213846_((Component)Component.m_237113_(greetingTitle)
/* 440 */               .m_130944_(new ChatFormatting[] { ChatFormatting.GOLD, ChatFormatting.BOLD }));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 445 */     if (lastRegionId != null && !lastRegionId.equals(currentRegionId)) {
/* 446 */       ProtectedRegion region = manager.getRegion(lastRegionId);
/* 447 */       if (region != null) {
/* 448 */         String farewell = (String)region.getFlag((Flag)Flags.FAREWELL);
/* 449 */         if (farewell != null && !farewell.isEmpty()) {
/* 450 */           player.m_213846_((Component)Component.m_237113_(farewell)
/* 451 */               .m_130940_(ChatFormatting.YELLOW));
/*     */         }
/*     */         
/* 454 */         String farewellTitle = (String)region.getFlag((Flag)Flags.FAREWELL_TITLE);
/* 455 */         if (farewellTitle != null && !farewellTitle.isEmpty()) {
/* 456 */           player.m_213846_((Component)Component.m_237113_(farewellTitle)
/* 457 */               .m_130944_(new ChatFormatting[] { ChatFormatting.GOLD, ChatFormatting.BOLD }));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 462 */     this.lastRegion.put(player.m_20148_(), currentRegionId);
/*     */   }
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\protection\ProtectionEventHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */