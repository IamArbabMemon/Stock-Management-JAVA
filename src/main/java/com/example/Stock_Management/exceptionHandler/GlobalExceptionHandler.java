package com.example.Stock_Management.exceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final JdbcTemplate jdbcTemplate;

    public GlobalExceptionHandler(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> EntityNotFoundExceptionHandler(EntityNotFoundException entityNotFoundException){
        return  new ResponseEntity<>(entityNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentExceptionhandler(IllegalArgumentException illegalArgumentException){
        return  new ResponseEntity<>(illegalArgumentException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerExceptionHandler(Exception exception){
        return  new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMessage();
        String constraintName = extractConstraintName(message);

        if (constraintName != null) {
            String columnName = getColumnNameFromConstraint("users", constraintName);  // Table name
            if (columnName != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Duplicate entry for field '" + columnName + "'. Please use a different value.");
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Duplicate entry detected. Please use a different value.");
    }

    private String extractConstraintName(String message) {
        Pattern pattern = Pattern.compile("key '(.*?)'");  // Extracts the constraint name
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            return matcher.group(1);
        }
        System.out.println(matcher.group(1));
        return null;
    }

    private String getColumnNameFromConstraint(String tableName, String constraintName) {
        try {
            String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                    "WHERE TABLE_NAME = ? AND CONSTRAINT_NAME = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{tableName, constraintName}, String.class);
        } catch (Exception e) {
            return null;  // If not found, return null
        }
    }

}
