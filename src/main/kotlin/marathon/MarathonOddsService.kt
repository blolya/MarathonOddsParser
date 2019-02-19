package marathon

import com.beust.klaxon.Klaxon
import main.OddsServiceInterface
import marathon.events.Event
import marathon.objects.categories.Children
import java.lang.Thread.sleep

class MarathonOddsService(private val channels: List<Int>): OddsServiceInterface {

    private val categoriesApiUrl = "https://mobile.marathonbet.ru/mobile-gate/api/v1/events/sport-categories?tree-id="
    private val liveEvents = mutableListOf<Event>()

    init {
        val categoriesRequester = Requester(this.categoriesApiUrl)
        val categoriesResponseFlow = categoriesRequester.subscribe(this.channels)
        categoriesResponseFlow.subscribe { categoryResponse -> run {
            val categoryInfo = Klaxon().parse<Children>(categoryResponse)
            if (categoryInfo != null) {
                println(categoryInfo.catInfo.children[0].name)
            }
        } }
        sleep(10000)
        categoriesRequester.unsubscribe()
    }

    override fun getOddsFlow() {}

}