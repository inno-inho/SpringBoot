package com.example.demo.Domain.Common.Service;

import com.example.demo.Domain.Common.Dto.MemoDto;
import com.example.demo.Domain.Common.Entity.Memo;
import com.example.demo.Domain.Common.Mapper.MemoMapper;
import com.example.demo.Domain.Common.Repository.MemoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;

@Service
@Slf4j
public class TxTestService {
    @Autowired
    private MemoRepository memoRepository;


    public void addMemo() throws Exception
    {
        log.info("TxTestService's addMemo...");
        Memo memo = Memo.builder()
                    .id(null)
                    .text("tx")
                    .writer("aa")
                    .createAt(LocalDateTime.now())
                    .build();
        memoRepository.save(memo);
        memo.setId(null);   // jpa특성상 한 번 넣은 아이디값을 넣어버리기 때문에 비워주어야 한다
        memoRepository.save(memo);
        memo.setId(null);
        memoRepository.save(memo);
        memo.setId(null);
        memoRepository.save(memo);
        throw new SQLException();
//        memoRepository.save(memo);
    }

    // Tx처리 할 거
    @Transactional(rollbackFor = SQLException.class, transactionManager = "jpaTransactionManager")        // SQLException이 발생하면 작업을 롤백하겠다
    public void addMemoTx() throws Exception
    {
        log.info("TxTestService's addMemoTx...");

        Memo memo = Memo.builder()
                .id(null)
                .text("tx")
                .writer("aa")
                .createAt(LocalDateTime.now())
                .build();
        memoRepository.save(memo);
        memo.setId(null);   // jpa특성상 한 번 넣은 아이디값을 넣어버리기 때문에 비워주어야 한다
        memoRepository.save(memo);
        memo.setId(null);
        memoRepository.save(memo);
        memo.setId(null);
        memoRepository.save(memo);
//        throw new SQLException();         // 일부러 예외발생시킴
//        memoRepository.save(memo);

    }



    @Autowired
    MemoMapper memoMapper;


    // 예외가 발생해도 데이터가 저장되어버림
    public void addMemoWithMybatis(MemoDto dto) throws SQLException {
        dto.setId(9994L);
        memoMapper.insert(dto);

        dto.setId(9995L);
        memoMapper.insert(dto);

        dto.setId(9996L);
        memoMapper.insert(dto);

        throw new SQLException("예외발생");
    }




    // 예외발생하면 롤백해줌
    @Transactional(rollbackFor =SQLException.class , transactionManager = "dataSourceTransactionManager")
    public void  addMemoWithMybatisTx(MemoDto dto) throws Exception{

        dto.setId(8994L);
        memoMapper.insert(dto);

        dto.setId(8995L);
        memoMapper.insert(dto);

        dto.setId(8996L);
        memoMapper.insert(dto);

        throw new SQLException("예외발생");

    }


}
