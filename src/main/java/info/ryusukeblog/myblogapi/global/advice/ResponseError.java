package info.ryusukeblog.myblogapi.global.advice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseError {
    private int status;
    private String message;

    ResponseError(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
