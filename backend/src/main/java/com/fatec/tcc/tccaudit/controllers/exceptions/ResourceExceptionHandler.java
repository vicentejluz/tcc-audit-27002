package com.fatec.tcc.tccaudit.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fatec.tcc.tccaudit.security.exceptions.InvalidAuthAndSignUpException;
import com.fatec.tcc.tccaudit.security.exceptions.InvalidPasswordException;
import com.fatec.tcc.tccaudit.services.exceptions.CnpjAlreadyRegisteredException;
import com.fatec.tcc.tccaudit.services.exceptions.DatabaseException;
import com.fatec.tcc.tccaudit.services.exceptions.DepartmentNotAllowedException;
import com.fatec.tcc.tccaudit.services.exceptions.EmailAlreadyRegisteredException;
import com.fatec.tcc.tccaudit.services.exceptions.EvidenceUploadException;
import com.fatec.tcc.tccaudit.services.exceptions.InvalidEmployeeNameException;
import com.fatec.tcc.tccaudit.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
                String error = "Resource not found";
                HttpStatus status = HttpStatus.NOT_FOUND;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(DatabaseException.class)
        public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
                String error = "Database error";
                HttpStatus status = HttpStatus.BAD_REQUEST;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(InvalidAuthAndSignUpException.class)
        public ResponseEntity<StandardError> invalidAuthAndSignUpException(InvalidAuthAndSignUpException e,
                        HttpServletRequest request) {
                String error = "authenticator error";
                HttpStatus status = HttpStatus.UNAUTHORIZED;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(InvalidPasswordException.class)
        public ResponseEntity<StandardError> invalidPasswordException(InvalidPasswordException e,
                        HttpServletRequest request) {
                String error = "password error";
                HttpStatus status = HttpStatus.BAD_REQUEST;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(EmailAlreadyRegisteredException.class)
        public ResponseEntity<StandardError> emailAlreadyRegisteredException(EmailAlreadyRegisteredException e,
                        HttpServletRequest request) {
                String error = "email error";
                HttpStatus status = HttpStatus.CONFLICT;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(CnpjAlreadyRegisteredException.class)
        public ResponseEntity<StandardError> cnpjAlreadyRegisteredException(CnpjAlreadyRegisteredException e,
                        HttpServletRequest request) {
                String error = "cnpj error";
                HttpStatus status = HttpStatus.CONFLICT;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(DepartmentNotAllowedException.class)
        public ResponseEntity<StandardError> departmentNotAllowedException(DepartmentNotAllowedException e,
                        HttpServletRequest request) {
                String error = "Department error";
                HttpStatus status = HttpStatus.FORBIDDEN;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(InvalidEmployeeNameException.class)
        public ResponseEntity<StandardError> invalidEmployeeNameException(InvalidEmployeeNameException e,
                        HttpServletRequest request) {
                String error = "name error";
                HttpStatus status = HttpStatus.BAD_REQUEST;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(EvidenceUploadException.class)
        public ResponseEntity<StandardError> evidenceSaveException(EvidenceUploadException e,
                        HttpServletRequest request) {
                String error = "evidence error";
                HttpStatus status = HttpStatus.BAD_REQUEST;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }
}