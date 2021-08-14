package com.lovemesomecoding.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(value = Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueueMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String            id;
    private String            title;
    private String            content;

}
