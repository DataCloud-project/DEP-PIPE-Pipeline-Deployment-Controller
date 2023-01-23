package eu.ubitech.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DefPipeRestResponseDto {
    private Object data;
    private boolean success;
    private String errorMessage;
}
