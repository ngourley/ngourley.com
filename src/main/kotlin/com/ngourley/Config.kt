package com.ngourley

import java.io.File

enum class Environment {
    DEVELOPMENT, PRODUCTION
}

object Config {
    val PORT: Int = if (System.getenv("PORT").isNullOrEmpty()) 9001 else System.getenv("PORT").toInt()
    val LOG4J_CONFIG: File = File(if (System.getenv("LOG4J_CONFIG").isNullOrEmpty()) "src/main/resources/log4j.properties" else System.getenv("LOG4J_CONFIG"))
    val ENVIRONMENT: Environment? = try { Environment.valueOf(System.getenv("ENVIRONMENT")) } catch (e: Exception) { Environment.DEVELOPMENT }
}
