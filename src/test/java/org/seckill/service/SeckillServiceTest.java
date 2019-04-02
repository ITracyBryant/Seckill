package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}", list);
    }

    @Test
    public void testGetById() {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);
    }

    /*@Test
    public void testExportSeckillUrl() {
        long id = 1005;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);
    }

    @Test
    public void testExecuteSeckill() {
        long id = 1005;
        long phone = 53787384434L;
        String md5 = "357786556ea7181fcbecfe1997e2aa04";
        try{
            SeckillExecution seckillExecution = seckillService.executeSeckill(id,phone,md5);
            logger.info("result={}",seckillExecution);
        }catch (RepeatKillException e){
            logger.error(e.getMessage());
        }catch (SeckillCloseException e){
            logger.error(e.getMessage());
        }
    }*/

    /**
     * 集成测试代码完整逻辑，注意可重复执行
     */
    @Test
    public void testSeckillLogic() {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            logger.info("exposer={}",exposer);
            long phone = 53787384434L;
            String md5 = exposer.getMd5();
            try{
                SeckillExecution seckillExecution = seckillService.executeSeckill(id,phone,md5);
                logger.info("result={}",seckillExecution);
            }catch (RepeatKillException e){
                logger.error(e.getMessage());
            }catch (SeckillCloseException e){
                logger.error(e.getMessage());
            }
        }else{
            // 秒杀未开启
            logger.warn("exposer={}",exposer);
        }
    }

    @Test
    public void testExecuteSeckillProcedure(){
        long seckillId = 1005;
        long phone = 12498595790L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
            logger.info(execution.getStateInfo());
        }

    }
}