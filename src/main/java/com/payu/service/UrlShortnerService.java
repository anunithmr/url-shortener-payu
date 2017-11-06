package com.payu.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payu.dto.ShortUrlResponse;
import com.payu.dto.UrlRequest;

import com.payu.models.Urls;
import com.payu.repositories.UrlRepository;

@Service
public class UrlShortnerService {
  private UrlRepository urlRepository;
  private static final String PREFIX = "http://payu.de/";
  @Autowired
  public UrlShortnerService(UrlRepository urlRepository){
    this.urlRepository = urlRepository;
  }

  public ShortUrlResponse createShortUrl(UrlRequest urlRequest){
      String hash = RandomStringUtils.randomAlphanumeric(6);
      while (urlRepository.findOne(hash) != null){
        hash = RandomStringUtils.randomAlphanumeric(6);
      }
      Urls urls = new Urls(hash,urlRequest.getLongUrl());
      urlRepository.save(urls);

      ShortUrlResponse shortUrlResponse = new ShortUrlResponse();
      return new ShortUrlResponse(PREFIX + hash, urlRequest.getLongUrl());

  }

  public String getLongUrl(String hash){
    Urls urls = urlRepository.findOne(hash);
    if(urls == null){
      throw new IllegalArgumentException("Resource not found!");
    }
    return urls.getActualLink();
  }

  public void removeShortUrl(String hash){
    urlRepository.delete(hash);
  }
}
