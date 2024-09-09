package com.example.springessentialguide.batch;

import com.example.springessentialguide.entity.AfterEntity;
import com.example.springessentialguide.repository.AfterRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class FourthBatch {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final AfterRepository afterRepository;

    @Bean
    public Job fourthJob() {

        System.out.println("fourth job");

        return new JobBuilder("fourthJob", jobRepository)
                .start(fourthStep())
                .build();
    }

    @Bean
    public Step fourthStep() {

        return new StepBuilder("fourthStep", jobRepository)
                .<Row, AfterEntity> chunk(10, platformTransactionManager)
                .reader(excelReader())
                .processor(fourthProcessor())
                .writer(fourthAfterWriter())
                .build();
    }

    @Bean
    public ItemStreamReader<Row> excelReader() {
        try {
            return new ExcelRowReader("/Users/bagsang-u/Desktop/무제.xlsx");
            //리눅스나 맥은 /User/형태로
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Row 형태로 불러들인 데이터를 AfterEntity로 변환한 과정입니다.
     * @return
     */
    @Bean
    public ItemProcessor<Row, AfterEntity> fourthProcessor() {

        return new ItemProcessor<Row, AfterEntity>() {

            @Override
            public AfterEntity process(Row item) {

                AfterEntity afterEntity = new AfterEntity();
                afterEntity.setUsername(item.getCell(0).getStringCellValue());

                return afterEntity;
            }
        };
    }

    @Bean
    public RepositoryItemWriter<AfterEntity> fourthAfterWriter() {

        return new RepositoryItemWriterBuilder<AfterEntity>()
                .repository(afterRepository)
                .methodName("save")
                .build();
    }
}
