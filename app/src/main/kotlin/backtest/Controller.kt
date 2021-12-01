package backtest

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.ThreadLocalRandom
import reactor.core.publisher.Flux

@RestController
class Controller {

    data class StockPrice(
        val symbol: String,
        val price: Double,
        val time: LocalDateTime
    )

    @GetMapping("/")
    fun hello(): String {
        return "Hello world! Maybe return html and JS here..."
    }

    @GetMapping(path = ["/stock/{symbol}"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun prices(@PathVariable symbol: String): Flux<StockPrice> {
        return Flux.interval(Duration.ofSeconds(1)).take(5)
            .map { StockPrice(symbol, randomStockPrice(), LocalDateTime.now()) }
    }

    private fun randomStockPrice(): Double {
        return ThreadLocalRandom.current().nextDouble(100.0)
    }

}
