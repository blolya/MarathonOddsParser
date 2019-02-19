package main

import marathon.MarathonOddsService

fun main() {
    val url1 = "https://mobile.marathonbet.ru/mobile-gate/api/v1/events/sport-categories?tree-id=139722"
    val url2 = "https://mobile.marathonbet.ru/mobile-gate/api/v1/events/tree-item-with-children?tree-id=472763"
    val msi: OddsServiceInterface = MarathonOddsService( listOf(45356) )
}