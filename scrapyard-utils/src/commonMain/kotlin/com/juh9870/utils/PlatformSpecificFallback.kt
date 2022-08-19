package com.juh9870.utils

@RequiresOptIn("Marked class/method is only intended for internal use as a fallback for different compile targets, consider using common class/method instead")
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class PlatformSpecificFallback