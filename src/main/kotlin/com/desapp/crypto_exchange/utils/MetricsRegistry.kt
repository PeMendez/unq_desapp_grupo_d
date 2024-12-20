package com.desapp.crypto_exchange.utils

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component

@Component
class MetricsRegistry(meterRegistry: MeterRegistry) {

    val loginAttemptsCounter: Counter = meterRegistry.counter("login_attempts_total")
    val transactionCompletedCounter: Counter = meterRegistry.counter("transactions_completed_total")

}
