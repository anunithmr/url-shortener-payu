package com.payu.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.payu.dto.ShortUrlResponse;
import com.payu.dto.UrlRequest;
import lombok.extern.slf4j.Slf4j;
import com.payu.service.UrlShortnerService;

@RestController
@Slf4j
@RequestMapping(path = "/")
public class UrlShortenerResource {

  private UrlShortnerService urlShortnerService;

  @Autowired
  public UrlShortenerResource(UrlShortnerService urlShortnerService){
    this.urlShortnerService = urlShortnerService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<ShortUrlResponse> shortenUrl(@RequestBody @Valid UrlRequest urlRequest){
      return ResponseEntity.ok().body(urlShortnerService.createShortUrl(urlRequest));
  }


  @RequestMapping(path = "{shortUrl}", method = RequestMethod.DELETE)
  public ResponseEntity<ShortUrlResponse> removeUrl(@PathVariable String shortUrl){
    urlShortnerService.removeShortUrl(shortUrl);
    return ResponseEntity.ok().build();
  }

  @RequestMapping(path = "{shortUrl}", method = RequestMethod.GET)
  public ResponseEntity redirect(@PathVariable String shortUrl) {
    String destination = urlShortnerService.getLongUrl(shortUrl);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", destination);
    return new ResponseEntity(headers, HttpStatus.FOUND); // 302 REDIRECT
  }


}
