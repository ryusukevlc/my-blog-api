package info.ryusukeblog.myblogapi.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    private final static String INTERNAL_SERVER_ERROR_MESSAGE = "サーバー内部でエラーが発生しました。";

    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<Object> handleResponseStatus(ResponseStatusException ex, WebRequest request) {
        return super.handleExceptionInternal(ex, this.createResponse(ex.getStatusCode().value(), ex.getReason()), new HttpHeaders(), ex.getStatusCode(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerAll(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(this.createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR_MESSAGE), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String createResponse(int httpStatusCode, String message) {
        ResponseError responseError = new ResponseError(httpStatusCode, message);
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(responseError);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return json;
    }
}