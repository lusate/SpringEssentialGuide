package com.example.springessentialguide.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    /**
     * 파라미터를 받는 이유는 배칠르 실행시킬 때 특정 일자에만 실행시키고 겹치는 일자에 배치가 들어오면 실행되지 않도록 하기 위함
     */
    @GetMapping("/first")
    public String firstApi(@RequestParam("value") String value) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", value)
                .toJobParameters();

        // callable 과 같은 도구로 비동기 처리가 가능하다고 합니다.
        jobLauncher.run(jobRegistry.getJob("firstJob"), jobParameters);


        return "ok";
    }

    // http://localhost:8080/second?value=zzz
    @GetMapping("/second")
    public String secondApi(@RequestParam("value") String value) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", value)
                .toJobParameters();

        // callable 과 같은 도구로 비동기 처리가 가능하다고 합니다.
        jobLauncher.run(jobRegistry.getJob("secondJob"), jobParameters);


        return "ok";
    }
}
