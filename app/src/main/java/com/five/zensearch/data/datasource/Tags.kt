package com.five.zensearch.com.five.zensearch.data.datasource

sealed class Tags {

    val id: Int
    get() = when(this) {
        is Film -> 1
        is Culture -> 2
        is Sport -> 3
        is Games -> 4
        is Science -> 5
        is Books -> 6
        is Nature -> 7
    }

    val similar: List<Tags>
    get() = when(this) {
        is Film -> listOf(Culture, Books)
        is Culture -> listOf(Film, Books, Science)
        is Sport -> listOf(Games)
        is Games -> listOf(Sport)
        is Science -> listOf(Culture, Nature, Books)
        is Books -> listOf(Film, Culture, Science, Nature)
        is Nature -> listOf(Science, Books)
    }

    object Film : Tags()
    object Culture : Tags()
    object Sport : Tags()
    object Games : Tags()
    object Science : Tags()
    object Books : Tags()
    object Nature : Tags()

}
