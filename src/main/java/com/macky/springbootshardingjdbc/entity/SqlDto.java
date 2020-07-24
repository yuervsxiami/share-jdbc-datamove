package com.macky.springbootshardingjdbc.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SqlDto {
    private String event;
    private Object value;
}
