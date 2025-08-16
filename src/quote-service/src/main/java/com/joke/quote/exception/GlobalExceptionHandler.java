package com.joke.quote.exception;

import com.joke.common.dto.ApiResponse;
import com.joke.common.exception.ResourceNotFoundException;
import com.joke.common.exception.ValidationException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {ResourceNotFoundException.class, ExceptionHandler.class})
public class GlobalExceptionHandler implements ExceptionHandler<Exception, HttpResponse<ApiResponse<Object>>> {

    @Override
    public HttpResponse<ApiResponse<Object>> handle(HttpRequest request, Exception exception) {
        if (exception instanceof ResourceNotFoundException) {
            return handleResourceNotFoundException((ResourceNotFoundException) exception);
        } else if (exception instanceof ValidationException) {
            return handleValidationException((ValidationException) exception);
        } else if (exception instanceof jakarta.validation.ConstraintViolationException) {
            return handleConstraintViolationException((jakarta.validation.ConstraintViolationException) exception);
        } else {
            return handleGenericException(exception);
        }
    }

    private HttpResponse<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse<Object> response = ApiResponse.error(ex.getMessage());
        return HttpResponse.status(HttpStatus.NOT_FOUND).body(response);
    }

    private HttpResponse<ApiResponse<Object>> handleValidationException(ValidationException ex) {
        ApiResponse<Object> response = ApiResponse.error(ex.getMessage());
        return HttpResponse.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private HttpResponse<ApiResponse<Object>> handleConstraintViolationException(
            jakarta.validation.ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .reduce((s1, s2) -> s1 + "; " + s2)
                .orElse("Validation failed");

        ApiResponse<Object> response = ApiResponse.error(message);
        return HttpResponse.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private HttpResponse<ApiResponse<Object>> handleGenericException(Exception ex) {
        ApiResponse<Object> response = ApiResponse.error("An unexpected error occurred: " + ex.getMessage());
        return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
