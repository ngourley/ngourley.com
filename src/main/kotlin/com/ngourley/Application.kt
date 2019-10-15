package com.ngourley


import com.ngourley.handler.Answer
import org.apache.log4j.PropertyConfigurator
import org.slf4j.LoggerFactory
import spark.Spark.*

import com.ngourley.handler.NotFoundHandler
import com.ngourley.handler.HealthCheckHandler
import io.javalin.Context
import io.javalin.Handler
import io.javalin.Javalin
import io.javalin.NotFoundResponse


class DerpHandler: Handler {
    override fun handle(ctx: Context) {
        ctx.status(500).result("{}").contentType("application/json")
    }

}

object Application {

    @JvmStatic
    fun main(args: Array<String>) {

        val app = Javalin.create().port(8888).start()

        app.get("*", DerpHandler())





//        val file = Config.LOG4J_CONFIG
//        if (file.exists()) PropertyConfigurator.configure(file.absolutePath)
//        val log = LoggerFactory.getLogger(this.javaClass)
//
//        port(Config.PORT)
//        log.debug("Started application on ${Config.PORT}")
//
//        initExceptionHandler { e -> println(e) }
//
//        if (Config.ENVIRONMENT == Environment.DEVELOPMENT) {
//            val projectDir = System.getProperty("user.dir")
//            val staticDir = "/src/main/resources/public"
//            staticFiles.externalLocation(projectDir + staticDir)
//            log.info("Running in development w/ static file refreshing")
//        } else {
//            staticFiles.location("/public")
//            log.info("Running in production mode, static files will not refresh")
//        }
//
//        get("/health", HealthCheckHandler())
//
//        notFound(NotFoundHandler())
    }

}
