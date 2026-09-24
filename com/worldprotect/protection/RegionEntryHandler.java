/*     */ package com.worldprotect.protection;
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
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraftforge.event.TickEvent;
/*     */ import net.minecraftforge.event.entity.player.PlayerEvent;
/*     */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ 
/*     */ @EventBusSubscriber(modid = "worldprotect")
/*     */ public class RegionEntryHandler {
/*  25 */   private static final Map<UUID, String> lastRegion = new HashMap<>();
/*  26 */   private static final Map<UUID, Long> lastCheck = new HashMap<>(); private static final long CHECK_INTERVAL = 500L;
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
/*     */     ServerPlayer player;
/*  31 */     if (event.phase != TickEvent.Phase.END)
/*  32 */       return;  Player player1 = event.player; if (player1 instanceof ServerPlayer) { player = (ServerPlayer)player1; }
/*     */     else { return; }
/*  34 */      UUID uuid = player.m_20148_();
/*  35 */     long now = System.currentTimeMillis();
/*     */     
/*  37 */     if (lastCheck.containsKey(uuid) && now - ((Long)lastCheck.get(uuid)).longValue() < 500L) {
/*     */       return;
/*     */     }
/*  40 */     lastCheck.put(uuid, Long.valueOf(now));
/*     */     
/*  42 */     RegionManager manager = WorldProtect.getInstance().getRegionManager((Level)player.m_284548_());
/*  43 */     if (manager == null)
/*     */       return; 
/*  45 */     Set<ProtectedRegion> regions = manager.getApplicableRegions(player.m_20183_());
/*     */     
/*  47 */     ProtectedRegion currentRegion = null;
/*  48 */     for (ProtectedRegion region : regions) {
/*  49 */       if (region.getType() != ProtectedRegion.RegionType.GLOBAL) {
/*  50 */         currentRegion = region;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  55 */     String currentRegionId = (currentRegion != null) ? currentRegion.getId() : null;
/*  56 */     String lastRegionId = lastRegion.get(uuid);
/*     */     
/*  58 */     if (currentRegionId != null && !currentRegionId.equals(lastRegionId)) {
/*  59 */       if (!player.m_20310_(((Integer)WorldProtectConfig.bypassOpLevel.get()).intValue())) {
/*  60 */         RegionManager.ApplicableRegionSet regionSet = manager.getApplicableRegionSet(player.m_20183_());
/*  61 */         StateFlag.State entryState = (StateFlag.State)regionSet.queryValue((Player)player, (Flag)Flags.ENTRY);
/*     */         
/*  63 */         if (entryState == StateFlag.State.DENY && !regionSet.isMemberOfAll((Player)player)) {
/*  64 */           player.m_213846_((Component)Component.m_237113_("You cannot enter this region!")
/*  65 */               .m_130940_(ChatFormatting.RED));
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*  70 */       String greeting = (String)currentRegion.getFlag((Flag)Flags.GREETING);
/*  71 */       if (greeting != null && !greeting.isEmpty()) {
/*  72 */         player.m_213846_((Component)Component.m_237113_(greeting)
/*  73 */             .m_130940_(ChatFormatting.YELLOW));
/*     */       }
/*     */       
/*  76 */       String greetingTitle = (String)currentRegion.getFlag((Flag)Flags.GREETING_TITLE);
/*  77 */       if (greetingTitle != null && !greetingTitle.isEmpty()) {
/*  78 */         player.m_213846_((Component)Component.m_237113_(greetingTitle)
/*  79 */             .m_130944_(new ChatFormatting[] { ChatFormatting.GOLD, ChatFormatting.BOLD }));
/*     */       }
/*     */     } 
/*     */     
/*  83 */     if (lastRegionId != null && !lastRegionId.equals(currentRegionId)) {
/*  84 */       ProtectedRegion oldRegion = manager.getRegion(lastRegionId);
/*  85 */       if (oldRegion != null) {
/*  86 */         if (!player.m_20310_(((Integer)WorldProtectConfig.bypassOpLevel.get()).intValue())) {
/*  87 */           StateFlag.State exitState = (StateFlag.State)oldRegion.getFlag((Flag)Flags.EXIT);
/*     */           
/*  89 */           if (exitState == StateFlag.State.DENY && !oldRegion.isMember(player.m_20148_())) {
/*  90 */             player.m_213846_((Component)Component.m_237113_("You cannot leave this region!")
/*  91 */                 .m_130940_(ChatFormatting.RED));
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*  96 */         String farewell = (String)oldRegion.getFlag((Flag)Flags.FAREWELL);
/*  97 */         if (farewell != null && !farewell.isEmpty()) {
/*  98 */           player.m_213846_((Component)Component.m_237113_(farewell)
/*  99 */               .m_130940_(ChatFormatting.YELLOW));
/*     */         }
/*     */         
/* 102 */         String farewellTitle = (String)oldRegion.getFlag((Flag)Flags.FAREWELL_TITLE);
/* 103 */         if (farewellTitle != null && !farewellTitle.isEmpty()) {
/* 104 */           player.m_213846_((Component)Component.m_237113_(farewellTitle)
/* 105 */               .m_130944_(new ChatFormatting[] { ChatFormatting.GOLD, ChatFormatting.BOLD }));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     lastRegion.put(uuid, currentRegionId);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
/* 115 */     UUID uuid = event.getEntity().m_20148_();
/* 116 */     lastRegion.remove(uuid);
/* 117 */     lastCheck.remove(uuid);
/*     */   }
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\protection\RegionEntryHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */