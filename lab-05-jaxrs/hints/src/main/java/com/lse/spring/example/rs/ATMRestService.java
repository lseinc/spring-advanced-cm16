package com.lse.spring.example.rs;

import com.lse.spring.example.atm.*;
import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.util.*;


@Component
@Path("/atm")
@Transactional
public class ATMRestService {
    private static final Logger log = LoggerFactory.getLogger(ATMRestService.class);

    @Inject
    ATM atm;

    @Inject
    AccountDao dao;

    @GET
    @Path("/health")
    @Produces(MediaType.APPLICATION_JSON)
    public Response health() {
        Response response = null;
        try {
            Map<String,String> info = new HashMap<>();
            info.put("account_count",String.valueOf(dao.count())  );
            response = Response.ok(info).build();
        }
        catch(Throwable t) {
            log.error("balance error: {}",t.getMessage(),t);
            response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

    @GET
    @Path("{account}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("account") String account) {
        Response response = null;
        try {
            Account theAccount = dao.findOne(account);
            response = Response.ok(theAccount).build();
        }
        catch(Throwable t) {
            log.error("balance error: {}",t.getMessage(),t);
            response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

    @GET
    @Path("{account}/balance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response balance(@PathParam("account") String account) {
        Response response = null;
        try {
            double balance = atm.getAccountBalance(account);
            response = Response.ok(balance).build();
        }
        catch(Throwable t) {
            log.error("balance error: {}",t.getMessage(),t);
            response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

}
