package com.waracle.cakemgr.repository;

import com.waracle.cakemgr.model.Cake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bianca.findlay, created on 24/08/2021.
 */
@Repository
public interface CakeRepository extends JpaRepository<Cake, Long> {

}