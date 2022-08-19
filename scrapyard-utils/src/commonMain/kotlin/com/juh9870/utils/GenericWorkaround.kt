package com.juh9870.utils

/**
 * Functions marked with this annotation are usually not fully type-safe due
 * to java generics system limitations, and there should be an alternative
 * fully type-safe alternative nearby function declaration
 */
@RequiresOptIn
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION)
annotation class HardGenericWorkaround

/**
 * Same as [HardGenericWorkaround] but warning instead of errors
 *
 * Mainly used for cases when using the method won't necessarily lead to typing
 * issues, but there are better options
 */
@RequiresOptIn(level = RequiresOptIn.Level.WARNING)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION)
annotation class SoftGenericWorkaround
