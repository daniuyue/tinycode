package org.tinycode.data;

import lombok.Data;

@Data
public class DClientResponseData {

    private String msg;

    public DClientResponseData() {
    }

    public DClientResponseData(String msg) {
        this.msg = msg;
    }
}
