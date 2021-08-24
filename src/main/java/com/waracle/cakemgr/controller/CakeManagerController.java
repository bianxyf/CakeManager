/**********************************************************************.
 *                                                                     *
 *         Copyright (c) ADB Safegate Airport Systems 2021          *
 *                         All rights reserved                         *
 *                                                                     *
 ***********************************************************************/
package com.waracle.cakemgr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.waracle.cakemgr.model.Cake;
import com.waracle.cakemgr.service.CakeService;
import com.waracle.cakemgr.service.CakeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * @author bianca.findlay, created on 23/08/2021.
 */
@Controller
public class CakeManagerController {

  @Autowired
  CakeService cakeService;

  @RequestMapping(value = "/")
  public String index(Model model) throws JsonProcessingException {
    return retrieveCakes(model);
  }

  @GetMapping("/landingPage")
  @RequestMapping(value = "retrieveCakes", method = RequestMethod.GET)
  public String retrieveCakes(Model model) throws JsonProcessingException {

    List<Cake> listOfCakes = cakeService.viewAllCakes();

    model.addAttribute("listOfCakes", listOfCakes);
    return "landingPage";
  }

  @GetMapping("/downloadPage")
  @RequestMapping(value = "cakes", method = RequestMethod.GET)
  public ResponseEntity<InputStreamResource> downloadPage(Model model) throws JsonProcessingException {

    RestTemplate rest = new RestTemplate();
    String url = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";
    ResponseEntity<String> response = rest.getForEntity(url, String.class);

    ObjectMapper mapper = new ObjectMapper();
    byte[] buf = mapper.writeValueAsBytes(response.getBody());

    return ResponseEntity
        .ok()
        .contentLength(buf.length)
        .contentType(
            MediaType.parseMediaType("application/octet-stream"))
        .header("Content-Disposition", "attachment; filename=\"Cakes.json\"")
        .body(new InputStreamResource(new ByteArrayInputStream(buf)));

    //model.addAttribute("listOfCakes", listOfCakes);
    //return "landingPage";
  }


  @RequestMapping(value = "api/addCake", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> addCake(@RequestBody Cake inputRequest, Model model){

    boolean isSuccess = cakeService.processAddNewCake(inputRequest);
    if(!isSuccess){
      return new ResponseEntity<>("Adding New Cake Failed.", HttpStatus.BAD_REQUEST);
    }
    List<Cake> listOfCakes = cakeService.retrieveListOfCakes();

    model.addAttribute("listOfCakes", listOfCakes);
    return new ResponseEntity<>("New Cake Added.", HttpStatus.OK);
  }

  @RequestMapping(value = "addCake", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String addCakeFromUI(@RequestParam(name="title", required = true) String cakeTitle, @RequestParam(name="desc", required = true)  String cakeDesc, Model model){
    Cake inputRequest = new Cake();
    inputRequest.setTitle(cakeTitle);
    inputRequest.setDesc(cakeDesc);
    boolean isSuccess = cakeService.processAddNewCake(inputRequest);
    if(!isSuccess){
      //TODO return entity
    }
    List<Cake> listOfCakes = cakeService.retrieveListOfCakes();

    model.addAttribute("listOfCakes", listOfCakes);
    return "landingPage";
  }

}