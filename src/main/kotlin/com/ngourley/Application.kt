package com.ngourley

import spark.Spark.*
import spark.Spark.initExceptionHandler

fun main(args: Array<String>) {

    val port: Int = if (System.getenv("PORT").isNullOrEmpty()) 9001 else System.getenv("PORT").toInt()
    port(port)

    initExceptionHandler { e -> println(e) }

    staticFiles.location("/public")

    get("*") { req, res ->
        "Hello World"
    }

}