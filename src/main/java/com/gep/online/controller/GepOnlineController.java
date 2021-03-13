package com.gep.online.controller;

import com.gep.online.base.GepException;
import com.gep.online.base.GepStarter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(value = "Gep接口")
@RestController
@RequestMapping(value = "/gep")
@Controller
public class GepOnlineController {
    @ApiOperation(value = "开启分段式预测")
    @RequestMapping(value = "/startCycle", method = RequestMethod.POST)
    public Map CycleController(@RequestParam(value = "maxFitness") int maxFitness, @RequestParam(value = "populationsSize") int populationsSize,
                               @RequestParam(value = "headLength") int headLength,
                               @RequestParam(value = "numOfGenes") int numOfGenes,
                               @RequestParam(value = "selectRang") double selectRang,
                               @RequestParam(value = "errorLimit") double errorLimit,
                               @RequestParam(value = "cycle") int cycle, @RequestParam(value = "DcMutationRate") double DcMutationRate,
                               @RequestParam(value = "onePointRecombinationRate") double onePointRecombinationRate,
                               @RequestParam(value = "twoPointRecombinationRate") double twoPointRecombinationRate,
                               @RequestParam(value = "recombinationRate") double recombinationRate,
                               @RequestParam(value = "ISTranspositionRate") double ISTranspositionRate,
                               @RequestParam(value = "RISTranspositionRate") double RISTranspositionRate,
                               @RequestParam(value = "GeneTranspositionRate") double GeneTranspositionRate,
                               @RequestParam(value = "Data") Double[] datain) throws Exception {
        GepStarter gs = new GepStarter();
        try {
            gs.setHeadLength(headLength);
            gs.setDataIn(cycle, datain);
            gs.setBaseParams(populationsSize, maxFitness, numOfGenes, selectRang, errorLimit);
            gs.setRate(DcMutationRate, onePointRecombinationRate, twoPointRecombinationRate, recombinationRate,
                    GeneTranspositionRate, ISTranspositionRate, RISTranspositionRate);
        } catch (GepException e) {
            Map map = new HashMap();
            map.put("error", e.getMessage());
            return map;
        }
        return gs.initAndStartCycle();
    }

    @ApiOperation(value = "开启非分段式预测")
    @RequestMapping(value = "/startNonCycle", method = RequestMethod.POST)
    public Map NonCycleController(@RequestParam(value = "maxFitness") int maxFitness,
                                  @RequestParam(value = "populationsSize") int populationsSize,
                                  @RequestParam(value = "headLength") int headLength,
                                  @RequestParam(value = "numOfGenes") int numOfGenes,
                                  @RequestParam(value = "selectRang") double selectRang,
                                  @RequestParam(value = "errorLimit") double errorLimit,
                                  @RequestParam(value = "cycle") int cycle,
                                  @RequestParam(value = "DcMutationRate") double DcMutationRate,
                                  @RequestParam(value = "onePointRecombinationRate") double onePointRecombinationRate,
                                  @RequestParam(value = "twoPointRecombinationRate") double twoPointRecombinationRate,
                                  @RequestParam(value = "recombinationRate") double recombinationRate,
                                  @RequestParam(value = "ISTranspositionRate") double ISTranspositionRate,
                                  @RequestParam(value = "RISTranspositionRate") double RISTranspositionRate,
                                  @RequestParam(value = "GeneTranspositionRate") double GeneTranspositionRate,
                                  @RequestParam(value = "Data") Double[] dataIn) throws Exception {
        GepStarter gs = new GepStarter();
        try {
            gs.setHeadLength(headLength);
            gs.setDataIn(cycle, dataIn);
            gs.setBaseParams(populationsSize, maxFitness, numOfGenes, selectRang, errorLimit);
            gs.setRate(DcMutationRate, onePointRecombinationRate, twoPointRecombinationRate, recombinationRate,
                    GeneTranspositionRate, ISTranspositionRate, RISTranspositionRate);
        } catch (GepException e) {
            Map map = new HashMap();
            map.put("error", e.getMessage());
            return map;
        }
        return gs.initAndStartNonCycle();
    }
}
