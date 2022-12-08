package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Delegate {
    private Integer id;
    private String delegatorName;
    private String delegateName;
    private Integer gmin;
    private String businessUnit;

    public Delegate(){
        super();
    }


}
