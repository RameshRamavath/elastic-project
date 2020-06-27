package com.nosql.elastic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
//@Slf4j
public class GlobalAdvice {

    private static Logger logger = LogManager.getLogger(GlobalAdvice.class);

    @ExceptionHandler(RecordNotFound.class)
    @ResponseBody
    protected ResponseEntity<AppError> handleRecordNotFound(RecordNotFound ex) {
        System.out.println("error while processing request : {}" + ex.getMessage());

        AppError AppError = new AppError();
        AppError.setStatus(ex.getStatus());
        AppError.setMessage(ex.getMessage());
        return new ResponseEntity<>(AppError, BAD_REQUEST);
    }

    @ExceptionHandler({ResponseException.class})
    @ResponseBody
    public ResponseEntity<AppError> handleElasticsearchStatusException(final ResponseException ex) {
        System.out.println("Error while processing request : {}" + ex.getCause().getMessage());

        final AppError AppError = new AppError(HttpStatus.BAD_REQUEST, "INDEX_NOT_FOUND");
        return new ResponseEntity<>(AppError, BAD_REQUEST);

    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<AppError> handleArgumentNotValidExceptionException(final MethodArgumentNotValidException ex) {
        System.out.println("Error while processing request : {}" + ex);

        final List<ErrorDetails> AppErrors = new ArrayList<>();
        for (final FieldError fieldAppError : ex.getBindingResult().getFieldErrors()) {
            ErrorDetails AppErrorDetails = new ErrorDetails(fieldAppError.getField(), fieldAppError.getDefaultMessage());
            AppErrors.add(AppErrorDetails);
        }
        final AppError AppError = new AppError(HttpStatus.BAD_REQUEST, "FIELDS_VALIDATION_AppError", AppErrors);
        return new ResponseEntity<>(AppError, BAD_REQUEST);

    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    protected ResponseEntity<AppError> handleUnhandledAppErrors(Exception ex) {

        System.out.println("AppError while processing request : {}" + ex);
        AppError AppError = new AppError(INTERNAL_SERVER_ERROR, "Facing some server side issue...!!");
        return new ResponseEntity<>(AppError, INTERNAL_SERVER_ERROR);
    }
}

