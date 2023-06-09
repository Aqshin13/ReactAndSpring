package com.hoaxify.ws.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)//Yeni field null deyilse JSON'a elave ele
public class ApiError {
//    @JsonView(Views.Base.class)
    private int status;
//    @JsonView(Views.Base.class)
    private String message;
//    @JsonView(Views.Base.class)
    private String path;
//    @JsonView(Views.Base.class)
    private long timestamp = new Date().getTime();
    private Map<String, String> validationErrors;


    public ApiError(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
