package com.fatec.tcc.tccaudit.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fatec.tcc.tccaudit.security.exceptions.InvalidAuthAndSignUpException;
import com.fatec.tcc.tccaudit.security.exceptions.InvalidPasswordException;
import com.fatec.tcc.tccaudit.services.exceptions.AdminAccountBlockException;
import com.fatec.tcc.tccaudit.services.exceptions.CNPJAlreadyRegisteredException;
import com.fatec.tcc.tccaudit.services.exceptions.DatabaseException;
import com.fatec.tcc.tccaudit.services.exceptions.DepartmentNotAllowedException;
import com.fatec.tcc.tccaudit.services.exceptions.EmailAlreadyRegisteredException;
import com.fatec.tcc.tccaudit.services.exceptions.EmployeeAccountBlockedException;
import com.fatec.tcc.tccaudit.services.exceptions.EvidenceUploadException;
import com.fatec.tcc.tccaudit.services.exceptions.InvalidEmployeeNameException;
import com.fatec.tcc.tccaudit.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
                String error = "Resource not found error";
                HttpStatus status = HttpStatus.NOT_FOUND;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(DatabaseException.class)
        public ResponseEntity<StandardError> databaseException(DatabaseException e, HttpServletRequest request) {
                String error = "Database error";
                HttpStatus status = HttpStatus.BAD_REQUEST;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(InvalidAuthAndSignUpException.class)
        public ResponseEntity<StandardError> invalidAuthAndSignUpException(InvalidAuthAndSignUpException e,
                        HttpServletRequest request) {
                String error = "Authenticator error";
                HttpStatus status = HttpStatus.UNAUTHORIZED;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(InvalidPasswordException.class)
        public ResponseEntity<StandardError> invalidPasswordException(InvalidPasswordException e,
                        HttpServletRequest request) {
                String error = "`Password error";
                HttpStatus status = HttpStatus.BAD_REQUEST;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(EmailAlreadyRegisteredException.class)
        public ResponseEntity<StandardError> emailAlreadyRegisteredException(EmailAlreadyRegisteredException e,
                        HttpServletRequest request) {
                String error = "Email error";
                HttpStatus status = HttpStatus.CONFLICT;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(CNPJAlreadyRegisteredException.class)
        public ResponseEntity<StandardError> cnpjAlreadyRegisteredException(CNPJAlreadyRegisteredException e,
                        HttpServletRequest request) {
                String error = "CNPJ error";
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
                String error = "Name error";
                HttpStatus status = HttpStatus.BAD_REQUEST;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(EvidenceUploadException.class)
        public ResponseEntity<StandardError> evidenceUploadException(EvidenceUploadException e,
                        HttpServletRequest request) {
                String error = "Evidence error";
                HttpStatus status = HttpStatus.BAD_REQUEST;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(AdminAccountBlockException.class)
        public ResponseEntity<StandardError> AdminAccountBlockException(AdminAccountBlockException e,
                        HttpServletRequest request) {
                String error = "Admin account block error";
                HttpStatus status = HttpStatus.FORBIDDEN;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(EmployeeAccountBlockedException.class)
        public ResponseEntity<StandardError> EmployeeAccountBlockedException(EmployeeAccountBlockedException e,
                        HttpServletRequest request) {
                String error = "Employee account blocked error";
                HttpStatus status = HttpStatus.FORBIDDEN;
                StandardError err = new StandardError(Instant.now(), status.value(), error,
                                e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }
}