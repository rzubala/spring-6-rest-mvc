package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    @DeleteMapping("{beerId}")
    public ResponseEntity<HttpStatus> deleteBeerById(@PathVariable UUID beerId) {
        beerService.deleteBeerById(beerId);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{beerId}")
    public ResponseEntity<HttpStatus> updateBeer(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
        Beer updatedBeer = beerService.updateBeerById(beerId, beer);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }

    @PostMapping
    public ResponseEntity<HttpStatus> saveNewBeer(@RequestBody Beer beer) {
        Beer savedBeer = beerService.createBeer(beer);

        HttpHeaders header = new HttpHeaders();
        header.add("Location", "/api/v1/beer/" + savedBeer.getId());

        return new ResponseEntity<HttpStatus>(header, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{beerId}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Get Beer by Id - in controller");

        return beerService.getBeerById(beerId);
    }

}
