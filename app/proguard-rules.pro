# Winlator CMOD ProGuard Rules
# Optimized for Realme GT Master Edition (SD778G / Adreno 642L)

# ============================================================
# JNI / Native Methods - CRITICAL: Keep all native method names
# ============================================================
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep all classes with native methods
-keepclassmembers class * {
    native <methods>;
}

# ============================================================
# Android Components
# ============================================================
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.app.Application
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.Fragment
-keep public class * extends androidx.fragment.app.Fragment

# ============================================================
# Winlator-specific - Keep all classes in the cmod package
# These classes use reflection, JNI, and JSON serialization
# ============================================================
-keep class com.winlator.cmod.** { *; }
-keepclassmembers class com.winlator.cmod.** { *; }

# Keep enum classes for container/config serialization
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ============================================================
# Retrofit / OkHttp / Gson
# ============================================================
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# Retrofit
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.Unit
-keep class retrofit2.** { *; }

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Gson
-keepattributes Signature
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# ============================================================
# Glide (Image Loading)
# ============================================================
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule { <init>(...); }
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder { *** rewind(); }

# ============================================================
# JSON serialization - Container uses JSONObject extensively
# ============================================================
-keepclassmembers class * {
    *** toJson();
    *** fromJson(java.lang.String);
}

# ============================================================
# UnixSocket / JNI Socket
# ============================================================
-keep class org.newsclub.** { *; }
-dontwarn org.newsclub.**

# ============================================================
# BouncyCastle / Conscrypt / OpenJSSE (Crypto)
# ============================================================
-dontwarn org.bouncycastle.**
-dontwarn org.conscrypt.**
-dontwarn org.openjsse.**
-keep class org.bouncycastle.** { *; }
-keep class org.conscrypt.** { *; }
-keep class org.openjsse.** { *; }

# ============================================================
# Apache Commons Compress
# ============================================================
-dontwarn org.apache.commons.compress.**

# ============================================================
# ZSTD JNI
# ============================================================
-keep class com.github.luben.zstd.** { *; }

# ============================================================
# General Android / Java
# ============================================================
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Remove all logging in release
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
    public static int i(...);
}
