package com.lse.spring.example.ws;

import javax.jws.*;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.*;

@WebService
@SOAPBinding(style= Style.DOCUMENT, use= Use.LITERAL)
public interface AuditWS  {
    @WebMethod
    String audit(@WebParam(name = "message") String message);
}
