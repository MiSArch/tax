package org.misarch.tax

import com.infobip.spring.data.r2dbc.EnableQuerydslR2dbcRepositories
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableQuerydslR2dbcRepositories
class TaxApplication

fun main(args: Array<String>) {
    runApplication<TaxApplication>(*args)
}