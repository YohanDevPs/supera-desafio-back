package br.com.banco.exceptions;

import br.com.banco.exeptions.handler.CustomizedResponseEntityExceptionHandler;
import br.com.banco.exeptions.response.ExceptionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomizedResponseEntityExceptionHandlerTest {

    @Test
    void testHandlerAllExceptions() {
        WebRequest webRequest = mock(WebRequest.class);
        when(webRequest.getDescription(anyBoolean())).thenReturn("Request Description");

        Exception exception = new Exception("Test Exception");

        CustomizedResponseEntityExceptionHandler handler = new CustomizedResponseEntityExceptionHandler();

        ResponseEntity<ExceptionResponse> responseEntity = handler.handlerAllExceptions(exception, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        ExceptionResponse response = responseEntity.getBody();
        assertEquals(new Date().getClass(), response.getTimestamp().getClass());
        assertEquals("Test Exception", response.getMessage());
        assertEquals("Request Description", response.getDetails());
    }
}
