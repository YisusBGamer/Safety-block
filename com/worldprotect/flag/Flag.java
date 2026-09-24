/*    */ package com.worldprotect.flag;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public abstract class Flag<T>
/*    */ {
/*    */   private final String name;
/*    */   private final RegionGroup defaultGroup;
/*    */   
/*    */   public Flag(String name, RegionGroup defaultGroup) {
/* 12 */     this.name = name;
/* 13 */     this.defaultGroup = defaultGroup;
/*    */   }
/*    */   
/*    */   public Flag(String name) {
/* 17 */     this(name, RegionGroup.ALL);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 21 */     return this.name;
/*    */   }
/*    */   
/*    */   public RegionGroup getDefaultGroup() {
/* 25 */     return this.defaultGroup;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public abstract T getDefault();
/*    */   
/*    */   public abstract T parseInput(String paramString) throws IllegalArgumentException;
/*    */   
/*    */   public abstract JsonElement serialize(T paramT);
/*    */   
/*    */   public abstract T deserialize(JsonElement paramJsonElement);
/*    */   
/*    */   public String getValueString(T value) {
/* 38 */     return (value != null) ? value.toString() : "not set";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 43 */     if (this == obj) return true; 
/* 44 */     if (obj == null || getClass() != obj.getClass()) return false; 
/* 45 */     Flag<?> flag = (Flag)obj;
/* 46 */     return this.name.equals(flag.name);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 51 */     return this.name.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 56 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\flag\Flag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */