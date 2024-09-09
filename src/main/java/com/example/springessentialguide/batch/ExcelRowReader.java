package com.example.springessentialguide.batch;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelRowReader implements ItemStreamReader<Row> {

    private final String filePath; // 엑셀 파일의 경로를 받을 변수
    private FileInputStream fileInputStream; // 해당 경로를 기반으로 파일을 열 객체
    private Workbook workbook; // 엑셀 파일을 받을 워크북
    private Iterator<Row> rowCursor; // 엑셀 파일 내부 시트에 각각의 행들을 반복할 커서
    private int currentRowNumber; // 어디 행까지 수행을 했는지 행번호로 기록할 객체
    private final String CURRENT_ROW_KEY = "current.row.number"; // 메타 데이터 테이블에 어떤 행까지 수행했는지 기록할 객체

    public ExcelRowReader(String filePath) throws IOException {
        this.filePath = filePath;
        this.currentRowNumber = 0;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        try {
            fileInputStream = new FileInputStream(filePath); // 엑셀 파일 열기
            workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0); // 첫번째 엑셀 시트를 가져옴
            this.rowCursor = sheet.iterator(); // 각각의 행을 반복하도록 세팅

            // 이미 동일한 배치 처리를 했다거나 일부분만 처리를 했을 경우 메타 데이터 테이블에 동일한 배치와 파라미터를 가지고
            // 특정 키인 CURRENT_ROW_KEY라는 어떤 행까지 진행했는지에 대한 변수가 있으면 가져옵니다. 중복 처리를 하지 않기 위함입니다.
            // 동일 배치 파라미터에 대해 특정 키 값 "current.row.number"의 값이 존재한다면 초기화
            if (executionContext.containsKey(CURRENT_ROW_KEY)) {
                currentRowNumber = executionContext.getInt(CURRENT_ROW_KEY);
            }

            // 위의 값을 가져와 이미 실행한 부분은 건너 뜀
            for (int i = 0; i < currentRowNumber && rowCursor.hasNext(); i++) {
                rowCursor.next();
            }

        } catch (IOException e) {
            throw new ItemStreamException(e);
        }
    }

    @Override
    public Row read() {
        if (rowCursor != null && rowCursor.hasNext()) {
            currentRowNumber++; // 어디 행까지 수행을 했는지 행번호로 기록할 객체
            return rowCursor.next();
        } else {
            return null;
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.putInt(CURRENT_ROW_KEY, currentRowNumber);
    }

    @Override
    public void close() throws ItemStreamException {
        try {
            if (workbook != null) {
                workbook.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        } catch (IOException e) {
            throw new ItemStreamException(e);
        }
    }
}
