package com.icore.demo.netty.httpClient.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NettyHttpResponse {

    private Throwable error;

    /**
     * 消息体,utf8解码
     */
    private String result;

}
