package com.sevabank.SevaBank.dto.generic;

import com.sevabank.SevaBank.dto.response.MessageResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class GenericDto<T> {
    private HttpStatus status;
    private String message;
    private MessageResponseDto msg;
    private T data;

    public GenericDto(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public GenericDto(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public GenericDto(HttpStatus status, MessageResponseDto msg){
        this.status = status;
        this.msg = msg;
    }
}
