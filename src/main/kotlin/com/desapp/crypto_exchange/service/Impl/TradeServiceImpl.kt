package com.desapp.crypto_exchange.service.Impl

import com.desapp.crypto_exchange.model.OperationType
import com.desapp.crypto_exchange.model.Trade
import com.desapp.crypto_exchange.model.TradeStatus
import com.desapp.crypto_exchange.repository.PriceRepository
import com.desapp.crypto_exchange.repository.TradeRepository
import com.desapp.crypto_exchange.repository.TransactionIntentRepository
import com.desapp.crypto_exchange.repository.UserRepository
import com.desapp.crypto_exchange.service.TradeService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class TradeServiceImpl: TradeService {

    @Autowired
    lateinit var tradeRepository: TradeRepository

    @Autowired
    lateinit var transactionIntentRepository: TransactionIntentRepository

    @Autowired
    lateinit var priceRepository: PriceRepository

    @Autowired
    lateinit var userRepository: UserRepository

    override fun createTrade(buyerId: Long, transactionIntentId: Long): Trade {
        val buyer = userRepository.findById(buyerId).orElseThrow {
            UsernameNotFoundException("Buyer with id $buyerId was not found")
        }

        val transactionIntent = transactionIntentRepository.findById(transactionIntentId).orElseThrow {
            IllegalArgumentException("TransactionIntent with id $transactionIntentId does not exist.")
        }

        val seller = transactionIntent.owner
            ?: throw IllegalArgumentException("TransactionIntent does not have a valid seller.")

        val cryptoActive = transactionIntent.cryptoActive
            ?: throw IllegalArgumentException("CryptoActive must not be null.")

        val price = priceRepository.findByCryptoActive(cryptoActive)
            ?: throw IllegalArgumentException("Price for cryptoActive $cryptoActive not found.")

        if ((transactionIntent.operationType == OperationType.PURCHASE && price.price!! > transactionIntent.price!!)
            || (transactionIntent.operationType == OperationType.SALE && price.price!! < transactionIntent.price!!)
        ) {
            throw IllegalStateException("Transaction cannot be processed as the current price violates the +/-5% rule.")
        }

        val trade = Trade(buyer, seller,transactionIntent,cryptoActive,transactionIntent.amount!!, price.price!!, transactionIntent.operationType!!, tradeStatus = TradeStatus.PENDING)

        tradeRepository.save(trade)
        transactionIntent.active = false
        transactionIntentRepository.save(transactionIntent)
        return trade
    }

    override fun completeTrade(tradeId: Long) {
        val trade = tradeRepository.findById(tradeId).orElseThrow {
            IllegalArgumentException("Trade with id $tradeId does not exist.")
        }

        if (trade.tradeStatus == TradeStatus.COMPLETED || trade.tradeStatus == TradeStatus.CANCELLED) {
            throw IllegalStateException("Trade with id $tradeId is already completed or cancelled and cannot be modified.")
        }

        trade.tradeStatus = TradeStatus.COMPLETED
        tradeRepository.save(trade)
    }

    override fun cancelTrade(tradeId: Long) {
        val trade = tradeRepository.findById(tradeId).orElseThrow {
            IllegalArgumentException("Trade with id $tradeId does not exist.")
        }

        if (trade.tradeStatus == TradeStatus.COMPLETED || trade.tradeStatus == TradeStatus.CANCELLED) {
            throw IllegalStateException("Trade with id $tradeId is already completed or cancelled and cannot be modified.")
        }

        trade.tradeStatus = TradeStatus.CANCELLED
        tradeRepository.save(trade)
    }
    override fun getAllTrades(): List<Trade> {
        return tradeRepository.findAll()
    }


}
