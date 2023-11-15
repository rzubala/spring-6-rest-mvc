package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
//@RequestMapping("/api/v1/beer")
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";

    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<HttpStatus> patchBeer(@PathVariable("beerId") UUID id, @RequestBody BeerDTO beer) {
        Optional<BeerDTO> patchedBeer = beerService.patchBeerById(id, beer);
        if (patchedBeer.isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<HttpStatus> deleteBeerById(@PathVariable UUID beerId) {
        if (!beerService.deleteById(beerId)) {
            throw new NotFoundException();
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<HttpStatus> updateBeer(@PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDTO beer) {
        if (beerService.updateBeerById(beerId, beer).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = BEER_PATH, method = RequestMethod.GET)
    public List<BeerDTO> listBeers(@RequestParam(required = false) String beerName,
                                   @RequestParam(required = false) BeerStyle beerStyle,
                                   @RequestParam(required = false) Boolean showInventory){
        return beerService.listBeers(beerName, beerStyle, showInventory);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity<HttpStatus> saveNewBeer(@Validated @RequestBody BeerDTO beer) {
        BeerDTO savedBeer = beerService.createBeer(beer);

        HttpHeaders header = new HttpHeaders();
        header.add("Location", BEER_PATH + "/" + savedBeer.getId());

        return new ResponseEntity<HttpStatus>(header, HttpStatus.CREATED);
    }

    @RequestMapping(value = BEER_PATH_ID, method = RequestMethod.GET)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Get Beer by Id - in controller");

        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

}
