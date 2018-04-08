package com.chalat.architecturedemo.utils

import org.mockito.Mockito

/**
 *
 * Created by Chalat Chansima on 4/8/18.
 *
 */
fun <T> eq(obj: T): T = Mockito.eq<T>(obj)

fun <T> any(): T = Mockito.any<T>()