/*     */ package com.worldprotect.region;
/*     */ import com.worldprotect.flag.Flag;
/*     */ import com.worldprotect.flag.RegionGroup;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ 
/*     */ public abstract class ProtectedRegion implements Comparable<ProtectedRegion> {
/*     */   protected final String id;
/*     */   
/*     */   public enum RegionType {
/*  14 */     CUBOID,
/*  15 */     POLYGON,
/*  16 */     GLOBAL;
/*     */   }
/*     */ 
/*     */   
/*  20 */   protected int priority = 0;
/*     */   protected ProtectedRegion parent;
/*  22 */   protected final Set<UUID> owners = new HashSet<>();
/*  23 */   protected final Set<UUID> members = new HashSet<>();
/*  24 */   protected final Set<String> ownerGroups = new HashSet<>();
/*  25 */   protected final Set<String> memberGroups = new HashSet<>();
/*  26 */   protected final Map<Flag<?>, Object> flags = new HashMap<>();
/*  27 */   protected final Map<Flag<?>, RegionGroup> flagGroups = new HashMap<>();
/*     */   
/*     */   protected ProtectedRegion(String id) {
/*  30 */     this.id = id.toLowerCase();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  34 */     return this.id;
/*     */   }
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
/*     */   public int getPriority() {
/*  50 */     return this.priority;
/*     */   }
/*     */   
/*     */   public void setPriority(int priority) {
/*  54 */     this.priority = priority;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ProtectedRegion getParent() {
/*  59 */     return this.parent;
/*     */   }
/*     */   
/*     */   public void setParent(@Nullable ProtectedRegion parent) {
/*  63 */     if (parent == this) {
/*  64 */       throw new IllegalArgumentException("Cannot set parent to self");
/*     */     }
/*  66 */     ProtectedRegion check = parent;
/*  67 */     while (check != null) {
/*  68 */       if (check == this) {
/*  69 */         throw new IllegalArgumentException("Circular inheritance detected");
/*     */       }
/*  71 */       check = check.getParent();
/*     */     } 
/*  73 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public Set<UUID> getOwners() {
/*  77 */     return Collections.unmodifiableSet(this.owners);
/*     */   }
/*     */   
/*     */   public void addOwner(UUID uuid) {
/*  81 */     this.owners.add(uuid);
/*     */   }
/*     */   
/*     */   public void removeOwner(UUID uuid) {
/*  85 */     this.owners.remove(uuid);
/*     */   }
/*     */   
/*     */   public boolean isOwner(UUID uuid) {
/*  89 */     if (this.owners.contains(uuid)) {
/*  90 */       return true;
/*     */     }
/*  92 */     if (this.parent != null) {
/*  93 */       return this.parent.isOwner(uuid);
/*     */     }
/*  95 */     return false;
/*     */   }
/*     */   
/*     */   public Set<UUID> getMembers() {
/*  99 */     return Collections.unmodifiableSet(this.members);
/*     */   }
/*     */   
/*     */   public void addMember(UUID uuid) {
/* 103 */     this.members.add(uuid);
/*     */   }
/*     */   
/*     */   public void removeMember(UUID uuid) {
/* 107 */     this.members.remove(uuid);
/*     */   }
/*     */   
/*     */   public boolean isMember(UUID uuid) {
/* 111 */     if (this.members.contains(uuid) || this.owners.contains(uuid)) {
/* 112 */       return true;
/*     */     }
/* 114 */     if (this.parent != null) {
/* 115 */       return this.parent.isMember(uuid);
/*     */     }
/* 117 */     return false;
/*     */   }
/*     */   
/*     */   public Set<String> getOwnerGroups() {
/* 121 */     return Collections.unmodifiableSet(this.ownerGroups);
/*     */   }
/*     */   
/*     */   public void addOwnerGroup(String group) {
/* 125 */     this.ownerGroups.add(group.toLowerCase());
/*     */   }
/*     */   
/*     */   public void removeOwnerGroup(String group) {
/* 129 */     this.ownerGroups.remove(group.toLowerCase());
/*     */   }
/*     */   
/*     */   public Set<String> getMemberGroups() {
/* 133 */     return Collections.unmodifiableSet(this.memberGroups);
/*     */   }
/*     */   
/*     */   public void addMemberGroup(String group) {
/* 137 */     this.memberGroups.add(group.toLowerCase());
/*     */   }
/*     */   
/*     */   public void removeMemberGroup(String group) {
/* 141 */     this.memberGroups.remove(group.toLowerCase());
/*     */   }
/*     */   
/*     */   public boolean hasMembersOrOwners() {
/* 145 */     return (!this.owners.isEmpty() || !this.members.isEmpty() || !this.ownerGroups.isEmpty() || !this.memberGroups.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void setFlag(Flag<T> flag, @Nullable T value) {
/* 150 */     if (value == null) {
/* 151 */       this.flags.remove(flag);
/*     */     } else {
/* 153 */       this.flags.put(flag, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> T getFlag(Flag<T> flag) {
/* 160 */     Object value = this.flags.get(flag);
/* 161 */     if (value != null) {
/* 162 */       return (T)value;
/*     */     }
/* 164 */     if (this.parent != null) {
/* 165 */       return this.parent.getFlag(flag);
/*     */     }
/* 167 */     return (T)flag.getDefault();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> T getOwnFlag(Flag<T> flag) {
/* 173 */     return (T)this.flags.get(flag);
/*     */   }
/*     */   
/*     */   public Map<Flag<?>, Object> getFlags() {
/* 177 */     return new HashMap<>(this.flags);
/*     */   }
/*     */   
/*     */   public void setFlags(Map<Flag<?>, Object> flags) {
/* 181 */     this.flags.clear();
/* 182 */     this.flags.putAll(flags);
/*     */   }
/*     */   
/*     */   public void setFlagGroup(Flag<?> flag, RegionGroup group) {
/* 186 */     if (group == flag.getDefaultGroup()) {
/* 187 */       this.flagGroups.remove(flag);
/*     */     } else {
/* 189 */       this.flagGroups.put(flag, group);
/*     */     } 
/*     */   }
/*     */   
/*     */   public RegionGroup getFlagGroup(Flag<?> flag) {
/* 194 */     RegionGroup group = this.flagGroups.get(flag);
/* 195 */     if (group != null) {
/* 196 */       return group;
/*     */     }
/* 198 */     if (this.parent != null) {
/* 199 */       return this.parent.getFlagGroup(flag);
/*     */     }
/* 201 */     return flag.getDefaultGroup();
/*     */   }
/*     */   
/*     */   public Map<Flag<?>, RegionGroup> getFlagGroups() {
/* 205 */     return new HashMap<>(this.flagGroups);
/*     */   }
/*     */   
/*     */   public boolean canBuild(Player player) {
/* 209 */     if (isOwner(player.m_20148_()) || isMember(player.m_20148_())) {
/* 210 */       return true;
/*     */     }
/* 212 */     StateFlag.State buildState = getFlag((Flag<StateFlag.State>)Flags.BUILD);
/* 213 */     return (buildState == StateFlag.State.ALLOW);
/*     */   }
/*     */   
/*     */   public boolean testPermission(Player player, Flag<?> flag) {
/* 217 */     if (isOwner(player.m_20148_())) {
/* 218 */       return true;
/*     */     }
/*     */     
/* 221 */     RegionGroup group = getFlagGroup(flag);
/* 222 */     UUID uuid = player.m_20148_();
/*     */     
/* 224 */     switch (group) {
/*     */       case ALL:
/* 226 */         return true;
/*     */       case OWNERS:
/* 228 */         return isOwner(uuid);
/*     */       case MEMBERS:
/* 230 */         return isMember(uuid);
/*     */       case NON_OWNERS:
/* 232 */         return !isOwner(uuid);
/*     */       case NON_MEMBERS:
/* 234 */         return !isMember(uuid);
/*     */       case NONE:
/* 236 */         return false;
/*     */     } 
/* 238 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(ProtectedRegion other) {
/* 244 */     int priorityCompare = Integer.compare(other.priority, this.priority);
/* 245 */     if (priorityCompare != 0) {
/* 246 */       return priorityCompare;
/*     */     }
/* 248 */     return this.id.compareTo(other.id);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 253 */     if (this == obj) return true; 
/* 254 */     if (obj == null || getClass() != obj.getClass()) return false; 
/* 255 */     ProtectedRegion that = (ProtectedRegion)obj;
/* 256 */     return this.id.equals(that.id);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 261 */     return this.id.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 266 */     return "ProtectedRegion{id='" + this.id + "', type=" + 
/*     */       
/* 268 */       String.valueOf(getType()) + ", priority=" + this.priority + "}";
/*     */   }
/*     */   
/*     */   public abstract RegionType getType();
/*     */   
/*     */   public abstract boolean contains(BlockPos paramBlockPos);
/*     */   
/*     */   public abstract boolean contains(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   public abstract BlockPos getMinimumPoint();
/*     */   
/*     */   public abstract BlockPos getMaximumPoint();
/*     */   
/*     */   public abstract int getVolume();
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\region\ProtectedRegion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */