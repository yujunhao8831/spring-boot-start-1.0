package com.aidijing.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author : 余峻豪
 * @date : 16/6/16
 */
public class ResponseEntity < T > {

    /**
     * 状态
     */
    private volatile String statusCode = StatusCode.FAIL.getStatusCode();

    /**
     * 失败或成功的提示信息
     */
    private volatile String statusMessage = StatusCode.FAIL.getStatusMessage();

    /**
     * 返回的内容
     */
    private volatile T responseContent;

    public ResponseEntity () {
    }

    public ResponseEntity ( String statusCode ) {
        this.statusCode = statusCode;
    }

    public ResponseEntity ( String statusCode, String statusMessage ) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public ResponseEntity ( String statusCode, String statusMessage, T responseContent ) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.responseContent = responseContent;
    }

    public static ResponseEntity ok ( String message ) {
        return new ResponseEntity( StatusCode.SUCCESS.getStatusCode(), message );
    }

    public static ResponseEntity ok ( StatusCode ok, String message ) {
        return new ResponseEntity( ok.getStatusCode(), message );
    }

    public static ResponseEntity ok () {
        return new ResponseEntity( StatusCode.SUCCESS.getStatusCode(), StatusCode.SUCCESS.getStatusMessage() );
    }

    public static ResponseEntity error ( String message ) {
        return new ResponseEntity( StatusCode.ERROR.getStatusCode(), message );
    }

    public static ResponseEntity fail ( String message ) {
        return new ResponseEntity( StatusCode.FAIL.getStatusCode(), message );
    }

    public static ResponseEntity fail ( StatusCode fail, String message ) {
        return new ResponseEntity( fail.getStatusCode(), message );
    }

    public static ResponseEntity error ( StatusCode error, String message ) {
        return new ResponseEntity( error.getStatusCode(), message );
    }


    @JsonIgnore
    public boolean isOk () {
        return StatusCode.SUCCESS.getStatusCode().equals( this.getStatusCode() );
    }

    @JsonIgnore
    public boolean isNotOk () {
        return ! isOk();
    }

    @Override
    public String toString () {
        return "ResponseEntity{" +
                "statusCode='" + statusCode + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", responseContent=" + responseContent +
                '}';
    }


    public String getStatusCode () {
        return statusCode;
    }

    public ResponseEntity setStatusCode ( String statusCode ) {
        this.statusCode = statusCode;
        return this;
    }

    public String getStatusMessage () {
        return statusMessage;
    }

    public ResponseEntity setStatusMessage ( String statusMessage ) {
        this.statusMessage = statusMessage;
        return this;
    }

    public T getResponseContent () {
        return responseContent;
    }

    public ResponseEntity setResponseContent ( T responseContent ) {
        this.responseContent = responseContent;
        return this;
    }

    public String toJson () {
        if ( this != null ) {
            synchronized ( this ) {
                if ( this != null ) {
                    return JsonUtils.toJson( this );
                }
            }
        }
        return null;
    }


    public enum StatusCode {
        SUCCESS( "200", "操作成功." ),
        OK_NOT_HANDLER( "202", "收到请求,但是不会做任何处理" ),
        NO_CONTENT( "204", "收到请求,但是没有内容" ),
        FAIL( "400", "操作失败,请求参数有误" ),
        UNAUTHORIZED( "401", "身份验证失败." ),
        REQUEST_TIME_OUT( "408", "服务器等待客户端发送的请求时间过长,超时" ),
        TOO_MANY_REQUESTS( "429", "太多的请求" ),
        TRADE_REPETITION( "460", "重复交易" ),
        ERROR( "500", "操作错误." );

        private String statusCode;
        private String statusMessage;

        StatusCode ( String statusCode, String statusMessage ) {
            this.statusCode = statusCode;
            this.statusMessage = statusMessage;
        }

        public String getStatusMessage () {
            return statusMessage;
        }

        public String getStatusCode () {
            return statusCode;
        }
    }

}
