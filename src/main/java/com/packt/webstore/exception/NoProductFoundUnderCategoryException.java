package com.packt.webstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Brak produktów we wskazanej kategorii")
public class NoProductFoundUnderCategoryException extends RuntimeException {
    public static final long serialVersionUID = -6541372631015165309L;
}
