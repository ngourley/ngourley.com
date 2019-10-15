package com.ngourley.handler

import spark.Request
import spark.Response
import spark.Route

/**
 * Handler used when requested resource does not exist.
 */
class NotFoundHandler: Route {
    override fun handle(request: Request, response: Response): Any {
        response.type("text/plain")
        response.body("NOT FOUND")
        response.status(404)
        return "NOT FOUND"
    }
}
