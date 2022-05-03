package rsp.rest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;

@Data
public class CreateStudent {


    private String username;

    /*
    private String email;

    private String password;

    private String city;
     */

    private String nameSchool;


}
