package com.iluwatar.tablemodule;

import lombok.*;


/**
 * A user POJO that represents the data that will be read from the data source.
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;

}
