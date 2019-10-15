package com.ngourley.handler


import com.sun.corba.se.spi.ior.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spark.Request
import spark.Response
import spark.Route
import java.util.*
import com.google.gson.*

class ContentType {
    companion object {
        val TEXT: String = "text/plain"
        val JSON: String = "application/json"
    }
}

class StatusCode {
    companion object {

        // Success Statuses
        val OK: Int = 200
        val CREATED: Int = 201

        // Client Errors
        val BAD_REQUEST: Int = 400
        val UNAUTHORIZED: Int = 401

        fun isSuccess(statusCode: Int): Boolean {
            return statusCode.toString().startsWith("2")
        }

        fun ifRedirect(statusCode: Int): Boolean {
            return statusCode.toString().startsWith("3")
        }

        fun isClientError(statusCode: Int): Boolean {
            return statusCode.toString().startsWith("4")
        }

        fun isServerError(statusCode: Int): Boolean {
            return statusCode.toString().startsWith("5")
        }
    }
}

data class Answer(var statusCode: Int, var response: Any) {
    companion object {
        fun badRequest(): Answer {return Answer(StatusCode.BAD_REQUEST, "skl")}
    }
}

abstract class RouteHandler<T>(val type: Class<T>): Route {
    val log: Logger = LoggerFactory.getLogger(javaClass)

    companion object {
        val gson: Gson = GsonBuilder()
                .registerTypeAdapter(ObjectId::class.java, JsonSerializer<ObjectId> { src, _, _ -> JsonPrimitive(src.toString()) })
                //.registerTypeAdapter(ObjectId::class.java, JsonDeserializer<ObjectId> { json, _, _ -> ObjectId(json.asString) })
                //.registerTypeAdapter(Date::class.java, UTCDateAdapter())
                .create()
    }

    abstract fun process(
            body: T?,
            urlParams: Map<String, String>,
            queryParams: Map<String, String>,
            headers: Map<String, String>): Answer

    override fun handle(req: Request, res: Response): Any {
        if (log.isInfoEnabled) {
            log.info("REQ - uri: ${req.uri()} ip: ${req.ip()}")
        }
        val resStr = try {
            val acceptHeader = req.headers("Accept")
            if (acceptHeader != null && !(acceptHeader.contains("application/json") ||
                            (acceptHeader == "*/*"))) {
                Answer.badRequest()
                res.type("text/plain")
                res.status(StatusCode.BAD_REQUEST)
                return "Accept content type must support JSON"
            }

            val urlParams = req.params()
            val queryParams = req.queryMap().toMap()
            val body = gson.fromJson<T>(req.body(), this.type)

            val headers = HashMap<String, String>()
            for (header in req.headers()) {
                headers[header] = req.headers(header)
            }

            val answer = process(body, urlParams, processQueryParams(queryParams), headers)

            res.status(answer.statusCode)
            res.type(ContentType.TEXT)

            if (answer.statusCode == 200) {
                gson.toJson(answer.response)
            } else {
                gson.toJson(answer)
            }
        } catch (e: JsonSyntaxException) {
            res.type(ContentType.TEXT)
            res.status(StatusCode.BAD_REQUEST)
            return "Missing, incomplete, or incorrect data"
        }
        if (log.isInfoEnabled) {
            log.info("RES - uri: ${req.uri()} ip: ${req.ip()} res: ${res.status()}")
        }
        return resStr
    }

    private fun processQueryParams(queryParams: Map<String, Array<String>>): Map<String, String> {
        val params = HashMap<String, String>()
        for (param in queryParams.keys) {
            val paramVal = queryParams[param]
            if (paramVal != null && paramVal.isNotEmpty()) {
                params[param] = paramVal[0]
            }
        }
        return params
    }

}
