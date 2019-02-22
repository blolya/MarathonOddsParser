package marathon

import com.beust.klaxon.Klaxon
import main.OddsServiceInterface
import marathon.events.Event
import marathon.objects.categories.Category
import marathon.objects.categories.Children
import java.lang.Thread.sleep

class MarathonOddsService(private val channels: List<Int>): OddsServiceInterface {

    private val liveEvents = mutableListOf<Event>()

    init {
        val categoriesRequester = Requester("https://mobile.marathonbet.ru/mobile-gate/api/v1/events/sport-categories?tree-id=")
        val categoriesResponseFlow = categoriesRequester.subscribe( this.channels )

        categoriesResponseFlow.subscribe { categoryResponse -> run {
            val category = Category(categoryResponse)
            val categoryTrees = category.getTrees()

            val eventsRequester = Requester("https://mobile.marathonbet.ru/mobile-gate/api/v1/events/tree-item-with-children?tree-id=")
            val eventsResponseFlow = eventsRequester.subscribe(categoryTrees)

            eventsResponseFlow.subscribe { eventResponse -> run {
                println(eventResponse)
            }}
        } }

        sleep(10000)
        categoriesRequester.unsubscribe()
    }

    override fun getOddsFlow() {}

}