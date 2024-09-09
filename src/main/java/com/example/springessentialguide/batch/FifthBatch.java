package com.example.springessentialguide.batch;

import com.example.springessentialguide.entity.BeforeEntity;
import com.example.springessentialguide.repository.BeforeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class FifthBatch {

    private final JobRepository jobRepository; // Job이나 Step이 Repository를 통해 관리될 수 있도록
    private final PlatformTransactionManager platformTransactionManager; // chunk 단위로 실행하다가 실행 실패 시 롤백을 위함
    private final BeforeRepository beforeRepository;

    @Bean
    public Job fifthJob() {

        System.out.println("fifth job");

        return new JobBuilder("fifthJob", jobRepository)
                .start(fifthStep())
                .build();
    }

    @Bean
    public Step fifthStep() {

        System.out.println("fifth step");

        return new StepBuilder("fifthStep", jobRepository)
                .<BeforeEntity, BeforeEntity> chunk(10, platformTransactionManager)
                .reader(fifthBeforeReader())
                .processor(fifthProcessor())
                .writer(excelWriter())
                .build();
    }

    /**
     * 기존과는 다르게 reader라는 변수를 만들어서 리턴해줬습니다.
     * 데이터를 읽어들여서 엑셀에 저장을 해야 하는데 배치가 실패할 경우 다시 첫번째부터 시작을 해야 하므로 어디까지 수행을 했는지에 대한 기록을 하지 못하도록 해야 합니다.
     * 그래서 reader.setSaveState() 를 false로 합니다.
     * @return
     */
    @Bean
    public RepositoryItemReader<BeforeEntity> fifthBeforeReader() {

        RepositoryItemReader<BeforeEntity> reader = new RepositoryItemReaderBuilder<BeforeEntity>()
                .name("beforeReader")
                .pageSize(10)
                .methodName("findAll")
                .repository(beforeRepository)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();

        // 전체 데이터 셋에서 어디까지 수행 했는지의 값을 저장하지 않음
        reader.setSaveState(false);

        return reader;
    }

    // BeforeEntity 타입으로 받아온 데이터를 BeforeEntity로 형태로 리턴해주면 됩니다.
    @Bean
    public ItemProcessor<BeforeEntity, BeforeEntity> fifthProcessor() {

        return item -> item;
    }

    @Bean
    public ItemStreamWriter<BeforeEntity> excelWriter() {

        try {
            return new ExcelRowWriter("/Users/bagsang-u/Desktop/무제.xlsx");
            //리눅스나 맥은 /User/형태로
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
