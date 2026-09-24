/*    */ package com.worldprotect.util;
/*    */ 
/*    */ import com.worldprotect.WorldProtect;
/*    */ import com.worldprotect.config.WorldProtectConfig;
/*    */ import com.worldprotect.region.ProtectedRegion;
/*    */ import com.worldprotect.region.RegionManager;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ 
/*    */ public class PermissionHelper {
/*    */   public static boolean canBypass(Player player) {
/*    */     ServerPlayer serverPlayer;
/* 13 */     if (player instanceof ServerPlayer) { serverPlayer = (ServerPlayer)player; }
/* 14 */     else { return false; }
/*    */ 
/*    */     
/* 17 */     int requiredLevel = ((Integer)WorldProtectConfig.bypassOpLevel.get()).intValue();
/* 18 */     if (requiredLevel <= 0) {
/* 19 */       return false;
/*    */     }
/*    */     
/* 22 */     return serverPlayer.m_20310_(requiredLevel);
/*    */   }
/*    */   
/*    */   public static boolean canModifyRegion(Player player, ProtectedRegion region) {
/* 26 */     if (canBypass(player)) {
/* 27 */       return true;
/*    */     }
/*    */     
/* 30 */     return region.isOwner(player.m_20148_());
/*    */   }
/*    */   
/*    */   public static boolean canBuildInRegion(Player player, ProtectedRegion region) {
/* 34 */     if (canBypass(player)) {
/* 35 */       return true;
/*    */     }
/*    */     
/* 38 */     return region.canBuild(player);
/*    */   }
/*    */   
/*    */   public static int getOwnedRegionCount(Player player) {
/* 42 */     int count = 0;
/* 43 */     for (RegionManager manager : WorldProtect.getInstance().getAllRegionManagers().values()) {
/* 44 */       count += manager.getRegionsOwnedBy(player.m_20148_()).size();
/*    */     }
/* 46 */     return count;
/*    */   }
/*    */   
/*    */   public static boolean canClaimMoreRegions(Player player) {
/* 50 */     if (canBypass(player)) {
/* 51 */       return true;
/*    */     }
/*    */     
/* 54 */     int maxRegions = ((Integer)WorldProtectConfig.maxRegionCountPerPlayer.get()).intValue();
/* 55 */     if (maxRegions < 0) {
/* 56 */       return true;
/*    */     }
/*    */     
/* 59 */     return (getOwnedRegionCount(player) < maxRegions);
/*    */   }
/*    */   
/*    */   public static boolean isRegionVolumeAllowed(int volume) {
/* 63 */     int maxVolume = ((Integer)WorldProtectConfig.maxRegionVolume.get()).intValue();
/* 64 */     if (maxVolume < 0) {
/* 65 */       return true;
/*    */     }
/* 67 */     return (volume <= maxVolume);
/*    */   }
/*    */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotec\\util\PermissionHelper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */