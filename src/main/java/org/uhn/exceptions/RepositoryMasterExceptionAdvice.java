package org.uhn.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class RepositoryMasterExceptionAdvice {

  @ResponseBody
  @ExceptionHandler(RepositoryMasterExceptions.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String ErrorsType400(RepositoryMasterExceptions ex) {
	  return ex.getMessage();
  }
}