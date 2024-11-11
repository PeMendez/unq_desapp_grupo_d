package com.desapp.crypto_exchange.service

import com.desapp.crypto_exchange.repository.TransactionIntentRepository
import com.desapp.crypto_exchange.repository.UserRepository
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension


@SpringBootTest
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionIntentServiceServiceTest {
    @Autowired
    private lateinit var service: TransactionIntentService

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var transactionIntentRepository: TransactionIntentRepository
}
