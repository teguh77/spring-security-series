package io.tam.ssc1.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@Async
public class HelloController {

    @GetMapping("/hello")
    public String hello() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal().toString());

        /*
        ###################### ALTERNATIVE WAY TO CREATE NEW THREAD WITHOUT ASYNC (SecurityContextHolder.MODE_INHERITABLETHREADLOCAL) #########################
        Runnable r = () -> {
            Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication1.getPrincipal().toString());
        };

        DelegatingSecurityContextRunnable dr = new DelegatingSecurityContextRunnable(r);

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(dr);
        service.shutdown();
        */

        /*
        ###################### MORE ALTERNATIVE #################################
        Runnable r = () -> {
            Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication1.getPrincipal().toString());
        };

//        DelegatingSecurityContextRunnable dr = new DelegatingSecurityContextRunnable(r);

        ExecutorService service = Executors.newSingleThreadExecutor();
        DelegatingSecurityContextExecutorService dService = new DelegatingSecurityContextExecutorService(service);

        dService.submit(r);
        dService.shutdown();
        */

        return "Hello!";
    }

}
