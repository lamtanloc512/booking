package org.hrsgroup.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private int code;
    private String message;
    private Object data;
}
