package com.waracle.cakemgr.service;

import com.waracle.cakemgr.model.Cake;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bianca.findlay, created on 23/08/2021.
 */
@Service
public interface CakeService {

  List<Cake> viewAllCakes();
  List<Cake> retrieveListOfCakes();
  boolean processAddNewCake(Cake cake);
}