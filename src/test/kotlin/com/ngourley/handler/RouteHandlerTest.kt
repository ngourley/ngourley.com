package com.ngourley.handler

import com.ngourley.Config
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import spark.QueryParamsMap
import spark.Request
import spark.Response
import java.util.HashMap
import java.util.stream.Collectors.toMap
import kotlin.test.assertEquals

@RunWith(JUnitPlatform::class)
class AbstractRequestHandlerTest: Spek({

    describe("request handler") {
        it("sdfasdf") {


            val handler = object: RouteHandler<Any>(Any::class.java) {
                override fun process(body: Any?, urlParams: Map<String, String>, queryParams: Map<String, String>, headers: Map<String, String>): Answer {
                    return Answer(StatusCode.OK, "Khskjdl")
                }
            }

            val badRequestHandler = object: RouteHandler<Any>(Any::class.java) {
                override fun process(body: Any?, urlParams: Map<String, String>, queryParams: Map<String, String>, headers: Map<String, String>): Answer {
                    return Answer(500, "Grrrr")
                }
            }

            val queryParams = HashMap<String, Array<String>>()
            queryParams.put("test", arrayOf("test"))
            val queryParamsMap: QueryParamsMap = mock {
                on { toMap() } doReturn queryParams
            }

            val req: Request = mock {
                on { headers("Accept") } doReturn listOf("application/json")
                on { queryMap() } doReturn queryParamsMap
                on { headers() } doReturn setOf("test")
                on { headers("test") } doReturn "test"
            }

            val res: Response = mock {}

            val r = handler.handle(req, res)
//            assertEquals("\"\"", r.javaClass.toGenericString())

            val response = badRequestHandler.handle(req, res)
            println(res.status())
            println(res.body())

//            ()


//            val requestHandler = object : RouteHandler<Any>(Any::class.java) {
//                override fun process(
//                        body: Any?,
//                        urlParams: Map<String, String>,
//                        queryParams: Map<String, String>,
//                        headers: Map<String, String>): Answer {
//                    return Answer(200, "")
//                }
//            }

//            req

        }


//        val log = LoggerFactory.getLogger(javaClass)

//        val queryParams = HashMap<String, Array<String>>()
//        queryParams.put("test", arrayOf("test"))
//        val queryParamsMap: QueryParamsMap = mock {
//            on { toMap() } doReturn queryParams
//        }
//
//        val req: Request = mock {
//            on { headers("Accept") } doReturn listOf("application/json")
//            on { queryMap() } doReturn queryParamsMap
//            on { headers() } doReturn setOf("test")
//            on { headers("test") } doReturn "test"
//        }
    }
})
