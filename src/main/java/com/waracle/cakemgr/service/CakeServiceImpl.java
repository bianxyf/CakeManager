package com.waracle.cakemgr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.waracle.cakemgr.model.Cake;
import com.waracle.cakemgr.repository.CakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author bianca.findlay, created on 23/08/2021.
 */
@Service
public class CakeServiceImpl  implements CakeService {

  @Autowired
  CakeRepository cakeRepository;

  @Override
  public List<Cake> viewAllCakes() {

    //Retrieve data from DB to begin with, if no cakes are found retrieve from the server
    //TODO for future improvement this can also be stored in a cache
    List<Cake> listOfCakes = cakeRepository.findAll();

    if(listOfCakes.isEmpty() || listOfCakes.size()==1) {
      listOfCakes = retrieveListOfCakesFromServer(listOfCakes);
    }

    return listOfCakes;

  }

  private List<Cake> retrieveListOfCakesFromServer(List<Cake> listOfCakes) {
    String url = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";

    //TODO make restTemplate reusable (inject)
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    try {
      ObjectMapper mapper = new ObjectMapper();
      listOfCakes = mapper.readValue(response.getBody(),
          TypeFactory.defaultInstance().constructCollectionType(List.class, Cake.class));

      cakeRepository.saveAll(listOfCakes);
      cakeRepository.flush();

    } catch (JsonProcessingException e) {
      System.out.println("Exception encountered"+ e.getMessage());
    }
    return listOfCakes;
  }

  @Override
  public List<Cake> retrieveListOfCakes() {
    return cakeRepository.findAll();
  }

  @Override
  public boolean processAddNewCake(Cake cake) {
    cakeRepository.save(cake);
    cakeRepository.flush();
    return true;
  }

}