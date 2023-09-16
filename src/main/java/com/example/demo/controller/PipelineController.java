package com.example.demo.controller;

import com.example.demo.model.ADFResponse;
import com.example.demo.service.PipelineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/pipeline")
public class PipelineController {

    private final PipelineServiceImpl pipelineService;

    @Autowired
    public PipelineController(PipelineServiceImpl pipelineService) {
        this.pipelineService = pipelineService;
    }

    @PostMapping("/createRun")
    public ResponseEntity<ADFResponse> testCreateRunPipeline() throws IOException {
        return ResponseEntity.ok(pipelineService.pipelinesCreateRun());
    }

    @GetMapping("/pipelineruns/{runId}")
    public ResponseEntity<ADFResponse> testGetPipelineRun(@PathVariable String runId) {
        return ResponseEntity.ok(pipelineService.pipelinesRunGet(runId));
    }

}
