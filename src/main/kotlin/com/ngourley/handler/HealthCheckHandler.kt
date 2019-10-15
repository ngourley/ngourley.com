package com.ngourley.handler

import spark.Request
import spark.Response
import spark.Route

/**
 * Handler used when requested resource does not exist.
 */
class HealthCheckHandler: Route {
    override fun handle(request: Request, response: Response): Any {
        response.status(200)
        return "Health Check"
    }
}
