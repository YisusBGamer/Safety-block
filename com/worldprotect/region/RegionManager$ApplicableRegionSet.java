/*     */ package com.worldprotect.region;
/*     */ 
/*     */ import com.worldprotect.flag.Flag;
/*     */ import com.worldprotect.flag.Flags;
/*     */ import com.worldprotect.flag.StateFlag;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ApplicableRegionSet
/*     */ {
/*     */   private final Set<ProtectedRegion> regions;
/*     */   
/*     */   public ApplicableRegionSet(Set<ProtectedRegion> regions) {
/* 141 */     this.regions = regions;
/*     */   }
/*     */   
/*     */   public Set<ProtectedRegion> getRegions() {
/* 145 */     return this.regions;
/*     */   }
/*     */   
/*     */   public int size() {
/* 149 */     return this.regions.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 153 */     return this.regions.isEmpty();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public <T> T queryValue(@Nullable Player player, Flag<T> flag) {
/* 158 */     T t = null;
/*     */     
/* 160 */     for (ProtectedRegion region : this.regions) {
/* 161 */       if (player != null && !region.testPermission(player, flag)) {
/*     */         continue;
/*     */       }
/*     */       
/* 165 */       T regionValue = region.getFlag(flag);
/* 166 */       if (regionValue != null) {
/* 167 */         StateFlag.State state; if (flag instanceof StateFlag) {
/*     */           
/* 169 */           StateFlag.State state1 = StateFlag.combine((StateFlag.State)t, (StateFlag.State)regionValue);
/* 170 */           state = state1; continue;
/* 171 */         }  if (state == null) {
/* 172 */           t = regionValue;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     return (t != null) ? t : (T)flag.getDefault();
/*     */   }
/*     */   
/*     */   public StateFlag.State queryState(@Nullable Player player, StateFlag... flags) {
/* 181 */     StateFlag.State result = null;
/*     */     
/* 183 */     for (StateFlag flag : flags) {
/* 184 */       StateFlag.State value = queryValue(player, (Flag<StateFlag.State>)flag);
/* 185 */       result = StateFlag.combine(result, value);
/*     */     } 
/*     */     
/* 188 */     return result;
/*     */   }
/*     */   
/*     */   public boolean testState(@Nullable Player player, StateFlag... flags) {
/* 192 */     StateFlag.State state = queryState(player, flags);
/* 193 */     return (state != StateFlag.State.DENY);
/*     */   }
/*     */   
/*     */   public boolean canBuild(Player player) {
/* 197 */     if (this.regions.isEmpty()) {
/* 198 */       return true;
/*     */     }
/*     */     
/* 201 */     for (ProtectedRegion region : this.regions) {
/* 202 */       if (region.isMember(player.m_20148_())) {
/* 203 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 207 */     return testState(player, new StateFlag[] { Flags.BUILD });
/*     */   }
/*     */   
/*     */   public boolean isOwnerOfAll(Player player) {
/* 211 */     for (ProtectedRegion region : this.regions) {
/* 212 */       if (!region.isOwner(player.m_20148_())) {
/* 213 */         return false;
/*     */       }
/*     */     } 
/* 216 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isMemberOfAll(Player player) {
/* 220 */     for (ProtectedRegion region : this.regions) {
/* 221 */       if (!region.isMember(player.m_20148_())) {
/* 222 */         return false;
/*     */       }
/*     */     } 
/* 225 */     return true;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ProtectedRegion getHighestPriorityRegion() {
/* 230 */     return this.regions.stream().findFirst().orElse(null);
/*     */   }
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\region\RegionManager$ApplicableRegionSet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */