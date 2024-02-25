package com.gajyoung.riot

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "riot")
data class RiotProperties(val apiKey: String)
