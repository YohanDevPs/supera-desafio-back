package br.com.banco.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PageLimitExceededException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public PageLimitExceededException(String ex) {
        super(ex);
    }
}
