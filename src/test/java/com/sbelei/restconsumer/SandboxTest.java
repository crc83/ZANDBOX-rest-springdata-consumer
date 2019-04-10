package com.sbelei.restconsumer;

import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class SandboxTest {

    @Test
    public void testSimpleRestCall() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:9000/sample/public";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl + "/hello", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo("Lorem ipsum"));
    }

    @Test
    public void testSimpleSecuredRestCall() {

        TestRestTemplate restTemplate = new TestRestTemplate ();
        String fooResourceUrl
                = "http://localhost:9000/sample/private";
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("spring", "secret")
                .getForEntity(fooResourceUrl + "/hello", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        //assertThat(response.getBody(), equalTo("Hello world"));
    }

    @Test
    public void testSimpleSecuredWithRestTemplate() {

        RestTemplate restTemplate = new RestTemplate ();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("spring", "secret"));
        String fooResourceUrl
                = "http://localhost:9000/sample/private";
        ResponseEntity<String> response = restTemplate
        //        .withBasicAuth("spring", "secret")
                .getForEntity(fooResourceUrl + "/hello", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        //assertThat(response.getBody(), equalTo("Hello world"));
    }
}