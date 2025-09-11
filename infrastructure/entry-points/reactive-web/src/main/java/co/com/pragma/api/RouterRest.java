package co.com.pragma.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/reportes",
                    produces = { "text/plain"},
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "listenGetTotalApprovedLoans",
                            summary = "Show total number of loans",
                            tags = { "Reports" },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "OK",
                                            content = @Content(
                                                    mediaType = "text/plain",
                                                    examples = {
                                                            @ExampleObject(
                                                                    value = "5"
                                                            )
                                                    }
                                            )),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden",
                                            content = @Content(
                                                    mediaType = "text/plain",
                                                    examples = {
                                                            @ExampleObject(
                                                                    value = "Access Denied"
                                                            )
                                                    }
                                            )
                                    )
                            }
                    )

            )
    })
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/v1/reportes"), handler::listenGetTotalApprovedLoans);
    }
}
