/*     */ package com.worldprotect.region;
/*     */ import com.worldprotect.WorldProtect;
/*     */ import com.worldprotect.flag.Flag;
/*     */ import com.worldprotect.flag.StateFlag;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ 
/*     */ public class RegionManager {
/*  16 */   private final Map<String, ProtectedRegion> regions = new ConcurrentHashMap<>(); private final String worldName;
/*     */   private boolean dirty = false;
/*     */   
/*     */   public RegionManager(String worldName) {
/*  20 */     this.worldName = worldName;
/*     */   }
/*     */   
/*     */   public String getWorldName() {
/*  24 */     return this.worldName;
/*     */   }
/*     */   
/*     */   public void addRegion(ProtectedRegion region) {
/*  28 */     this.regions.put(region.getId().toLowerCase(), region);
/*  29 */     this.dirty = true;
/*  30 */     WorldProtect.LOGGER.debug("Added region: {} in {}", region.getId(), this.worldName);
/*     */   }
/*     */   
/*     */   public void removeRegion(String id) {
/*  34 */     ProtectedRegion removed = this.regions.remove(id.toLowerCase());
/*  35 */     if (removed != null) {
/*  36 */       for (ProtectedRegion region : this.regions.values()) {
/*  37 */         if (region.getParent() == removed) {
/*  38 */           region.setParent(null);
/*     */         }
/*     */       } 
/*  41 */       this.dirty = true;
/*  42 */       WorldProtect.LOGGER.debug("Removed region: {} from {}", id, this.worldName);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ProtectedRegion getRegion(String id) {
/*  48 */     return this.regions.get(id.toLowerCase());
/*     */   }
/*     */   
/*     */   public boolean hasRegion(String id) {
/*  52 */     return this.regions.containsKey(id.toLowerCase());
/*     */   }
/*     */   
/*     */   public Collection<ProtectedRegion> getRegions() {
/*  56 */     return Collections.unmodifiableCollection(this.regions.values());
/*     */   }
/*     */   
/*     */   public Map<String, ProtectedRegion> getRegionsMap() {
/*  60 */     return new HashMap<>(this.regions);
/*     */   }
/*     */   
/*     */   public int size() {
/*  64 */     return this.regions.size();
/*     */   }
/*     */   
/*     */   public void clear() {
/*  68 */     this.regions.clear();
/*  69 */     this.dirty = true;
/*     */   }
/*     */   
/*     */   public Set<ProtectedRegion> getApplicableRegions(BlockPos pos) {
/*  73 */     return getApplicableRegions(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
/*     */   }
/*     */   
/*     */   public Set<ProtectedRegion> getApplicableRegions(int x, int y, int z) {
/*  77 */     Set<ProtectedRegion> applicable = new TreeSet<>();
/*  78 */     for (ProtectedRegion region : this.regions.values()) {
/*  79 */       if (region.contains(x, y, z)) {
/*  80 */         applicable.add(region);
/*     */       }
/*     */     } 
/*  83 */     return applicable;
/*     */   }
/*     */   
/*     */   public ApplicableRegionSet getApplicableRegionSet(BlockPos pos) {
/*  87 */     return new ApplicableRegionSet(getApplicableRegions(pos));
/*     */   }
/*     */   
/*     */   public List<ProtectedRegion> getRegionsOwnedBy(UUID player) {
/*  91 */     return (List<ProtectedRegion>)this.regions.values().stream()
/*  92 */       .filter(r -> r.isOwner(player))
/*  93 */       .collect(Collectors.toList());
/*     */   }
/*     */   
/*     */   public List<ProtectedRegion> getRegionsMemberOf(UUID player) {
/*  97 */     return (List<ProtectedRegion>)this.regions.values().stream()
/*  98 */       .filter(r -> r.isMember(player))
/*  99 */       .collect(Collectors.toList());
/*     */   }
/*     */   
/*     */   public List<ProtectedRegion> getOverlappingRegions(ProtectedRegion region) {
/* 103 */     if (region.getType() == ProtectedRegion.RegionType.GLOBAL) {
/* 104 */       return new ArrayList<>(this.regions.values());
/*     */     }
/*     */     
/* 107 */     List<ProtectedRegion> overlapping = new ArrayList<>();
/* 108 */     BlockPos min = region.getMinimumPoint();
/* 109 */     BlockPos max = region.getMaximumPoint();
/*     */     
/* 111 */     for (ProtectedRegion other : this.regions.values()) {
/* 112 */       if (other == region || other.getType() == ProtectedRegion.RegionType.GLOBAL) {
/*     */         continue;
/*     */       }
/*     */       
/* 116 */       BlockPos otherMin = other.getMinimumPoint();
/* 117 */       BlockPos otherMax = other.getMaximumPoint();
/*     */       
/* 119 */       if (min.m_123341_() <= otherMax.m_123341_() && max.m_123341_() >= otherMin.m_123341_() && min
/* 120 */         .m_123342_() <= otherMax.m_123342_() && max.m_123342_() >= otherMin.m_123342_() && min
/* 121 */         .m_123343_() <= otherMax.m_123343_() && max.m_123343_() >= otherMin.m_123343_()) {
/* 122 */         overlapping.add(other);
/*     */       }
/*     */     } 
/*     */     
/* 126 */     return overlapping;
/*     */   }
/*     */   
/*     */   public boolean isDirty() {
/* 130 */     return this.dirty;
/*     */   }
/*     */   
/*     */   public void setDirty(boolean dirty) {
/* 134 */     this.dirty = dirty;
/*     */   }
/*     */   
/*     */   public static class ApplicableRegionSet {
/*     */     private final Set<ProtectedRegion> regions;
/*     */     
/*     */     public ApplicableRegionSet(Set<ProtectedRegion> regions) {
/* 141 */       this.regions = regions;
/*     */     }
/*     */     
/*     */     public Set<ProtectedRegion> getRegions() {
/* 145 */       return this.regions;
/*     */     }
/*     */     
/*     */     public int size() {
/* 149 */       return this.regions.size();
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 153 */       return this.regions.isEmpty();
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public <T> T queryValue(@Nullable Player player, Flag<T> flag) {
/* 158 */       T t = null;
/*     */       
/* 160 */       for (ProtectedRegion region : this.regions) {
/* 161 */         if (player != null && !region.testPermission(player, flag)) {
/*     */           continue;
/*     */         }
/*     */         
/* 165 */         T regionValue = region.getFlag(flag);
/* 166 */         if (regionValue != null) {
/* 167 */           StateFlag.State state; if (flag instanceof StateFlag) {
/*     */             
/* 169 */             StateFlag.State state1 = StateFlag.combine((StateFlag.State)t, (StateFlag.State)regionValue);
/* 170 */             state = state1; continue;
/* 171 */           }  if (state == null) {
/* 172 */             t = regionValue;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 177 */       return (t != null) ? t : (T)flag.getDefault();
/*     */     }
/*     */     
/*     */     public StateFlag.State queryState(@Nullable Player player, StateFlag... flags) {
/* 181 */       StateFlag.State result = null;
/*     */       
/* 183 */       for (StateFlag flag : flags) {
/* 184 */         StateFlag.State value = queryValue(player, (Flag<StateFlag.State>)flag);
/* 185 */         result = StateFlag.combine(result, value);
/*     */       } 
/*     */       
/* 188 */       return result;
/*     */     }
/*     */     
/*     */     public boolean testState(@Nullable Player player, StateFlag... flags) {
/* 192 */       StateFlag.State state = queryState(player, flags);
/* 193 */       return (state != StateFlag.State.DENY);
/*     */     }
/*     */     
/*     */     public boolean canBuild(Player player) {
/* 197 */       if (this.regions.isEmpty()) {
/* 198 */         return true;
/*     */       }
/*     */       
/* 201 */       for (ProtectedRegion region : this.regions) {
/* 202 */         if (region.isMember(player.m_20148_())) {
/* 203 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 207 */       return testState(player, new StateFlag[] { Flags.BUILD });
/*     */     }
/*     */     
/*     */     public boolean isOwnerOfAll(Player player) {
/* 211 */       for (ProtectedRegion region : this.regions) {
/* 212 */         if (!region.isOwner(player.m_20148_())) {
/* 213 */           return false;
/*     */         }
/*     */       } 
/* 216 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isMemberOfAll(Player player) {
/* 220 */       for (ProtectedRegion region : this.regions) {
/* 221 */         if (!region.isMember(player.m_20148_())) {
/* 222 */           return false;
/*     */         }
/*     */       } 
/* 225 */       return true;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public ProtectedRegion getHighestPriorityRegion() {
/* 230 */       return this.regions.stream().findFirst().orElse(null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\region\RegionManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */