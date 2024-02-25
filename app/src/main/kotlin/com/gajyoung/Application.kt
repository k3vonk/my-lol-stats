/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.gajyoung

import com.gajyoung.riot.RiotProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RiotProperties::class)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
