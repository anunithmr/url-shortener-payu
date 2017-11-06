package com.payu.service;
import com.payu.dto.ShortUrlResponse;
import com.payu.dto.UrlRequest;
import com.payu.models.Urls;
import com.payu.repositories.UrlRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TestUrlShortnerService {

  @Mock
  UrlRepository urlsRepository;

  UrlShortnerService urlShortnerService;

  @Before
  public void setup(){
    MockitoAnnotations.initMocks(this);
    this.urlShortnerService = new UrlShortnerService(urlsRepository);
  }

  @Test
  public void testUrlShorteningFirstEntry(){
    Mockito.when(urlsRepository.findOne(Mockito.anyString())).thenReturn(null);
    ShortUrlResponse response = urlShortnerService.createShortUrl(new UrlRequest("http://www.google.com"));

    Mockito.verify(urlsRepository, Mockito.times(1)).findOne(Mockito.anyString());
    Mockito.verify(urlsRepository, Mockito.times(1)).save(Mockito.any(Urls.class));
    Mockito.verifyNoMoreInteractions(urlsRepository);
  }

}
