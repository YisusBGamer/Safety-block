/*    */ package com.worldprotect.flag;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SetFlag<T>
/*    */   extends Flag<Set<T>> {
/*    */   private final Flag<T> subFlag;
/*    */   
/*    */   public SetFlag(String name, Flag<T> subFlag) {
/* 14 */     super(name);
/* 15 */     this.subFlag = subFlag;
/*    */   }
/*    */   
/*    */   public Flag<T> getSubFlag() {
/* 19 */     return this.subFlag;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Set<T> getDefault() {
/* 25 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<T> parseInput(String input) throws IllegalArgumentException {
/* 30 */     Set<T> result = new HashSet<>();
/* 31 */     String[] parts = input.split(",");
/* 32 */     for (String part : parts) {
/* 33 */       result.add(this.subFlag.parseInput(part.trim()));
/*    */     }
/* 35 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement serialize(Set<T> value) {
/* 40 */     JsonArray array = new JsonArray();
/* 41 */     for (T item : value) {
/* 42 */       array.add(this.subFlag.serialize(item));
/*    */     }
/* 44 */     return (JsonElement)array;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<T> deserialize(JsonElement element) {
/* 49 */     Set<T> result = new HashSet<>();
/* 50 */     JsonArray array = element.getAsJsonArray();
/* 51 */     for (JsonElement item : array) {
/* 52 */       result.add(this.subFlag.deserialize(item));
/*    */     }
/* 54 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getValueString(Set<T> value) {
/* 59 */     if (value == null || value.isEmpty()) {
/* 60 */       return "not set";
/*    */     }
/* 62 */     StringBuilder sb = new StringBuilder();
/* 63 */     boolean first = true;
/* 64 */     for (T item : value) {
/* 65 */       if (!first) sb.append(", "); 
/* 66 */       sb.append(this.subFlag.getValueString(item));
/* 67 */       first = false;
/*    */     } 
/* 69 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\flag\SetFlag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */