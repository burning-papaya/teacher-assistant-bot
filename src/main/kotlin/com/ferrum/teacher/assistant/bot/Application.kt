/**
Author: BurningPapaya
Date: 08.04.2020
Time: 21:57
 */
package com.ferrum.teacher.assistant.bot

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.telegram.telegrambots.ApiContextInitializer

@SpringBootApplication
@EnableEncryptableProperties
class Application
fun main(args: Array<String>) {
    ApiContextInitializer.init()
    runApplication<Application>(*args)
}