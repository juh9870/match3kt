package com.juh9870.match3kt.core.actions.gravity

import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.actions.BoardAction
import com.juh9870.match3kt.core.board.Board

abstract class GravityAction<B : Board<C, T>, C : Cell<T>, T> : BoardAction<B, C, T>