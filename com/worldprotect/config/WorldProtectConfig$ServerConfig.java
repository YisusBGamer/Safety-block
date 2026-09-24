/*    */ package com.worldprotect.config;
/*    */ 
/*    */ import net.minecraftforge.common.ForgeConfigSpec;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerConfig
/*    */ {
/*    */   public ServerConfig(ForgeConfigSpec.Builder builder) {
/* 40 */     builder.push("permissions");
/*    */ 
/*    */ 
/*    */     
/* 44 */     WorldProtectConfig.bypassOpLevel = builder.comment("Minimum operator level required to bypass protection (0-4, 0 = disabled)").defineInRange("bypassOpLevel", 2, 0, 4);
/*    */     
/* 46 */     builder.pop();
/*    */     
/* 48 */     builder.push("regions");
/*    */ 
/*    */ 
/*    */     
/* 52 */     WorldProtectConfig.maxRegionCountPerPlayer = builder.comment("Maximum number of regions a non-operator player can own (-1 for unlimited)").defineInRange("maxRegionCountPerPlayer", -1, -1, 2147483647);
/*    */ 
/*    */ 
/*    */     
/* 56 */     WorldProtectConfig.maxRegionVolume = builder.comment("Maximum volume (blocks) for a region (-1 for unlimited)").defineInRange("maxRegionVolume", -1, -1, 2147483647);
/*    */ 
/*    */ 
/*    */     
/* 60 */     WorldProtectConfig.claimPadding = builder.comment("Minimum distance between regions owned by different players").defineInRange("claimPadding", 0, 0, 100);
/*    */     
/* 62 */     builder.pop();
/*    */     
/* 64 */     builder.push("saving");
/*    */ 
/*    */ 
/*    */     
/* 68 */     WorldProtectConfig.autoSaveInterval = builder.comment("Auto-save interval in minutes (0 to disable)").defineInRange("autoSaveInterval", 5, 0, 60);
/*    */     
/* 70 */     builder.pop();
/*    */   }
/*    */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\config\WorldProtectConfig$ServerConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */