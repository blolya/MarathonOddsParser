package marathon

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Requester(private val url: String) {
    private var subscribed = false
    private val responseFlow: PublishSubject<String> = PublishSubject.create()

    fun subscribe(channels: List<Int>): Observable<String> {
        this.subscribed = true
        val self = this

        GlobalScope.launch {
            while(self.subscribed) {
                channels.forEach { channelId ->
                    run {
                        val fullUrl = "${self.url}$channelId"
                        responseFlow.onNext(sendGet(fullUrl))
                    }
                }
            }
        }
        return this.responseFlow
    }
    fun unsubscribe() {
        println("Unsubscribed")
        this.subscribed = false
    }
    fun askOnce() {

    }

    private fun sendGet(url: String): String {
        val response = StringBuffer()
        val obj = URL(url)

        with(obj.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "GET"

            BufferedReader(InputStreamReader(inputStream)).use {

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
            }
        }
        return response.toString()
    }
}