package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 配置Spring和JUnit整合,为了JUnit启动时加载SpringIoC容器(自动验证spring-dao.xml的配置)
 * spring-test, junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit, Spring配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    // 注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testQueryById() {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    //List<Seckill> queryAll(int offset, int limit)
    // java没有保存形参的记录,queryAll(int offset, int limit)->queryAll(arg0,arg1)
    // 使用@Param
    @Test
    public void testQueryAll() {
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }
    }

    @Test
    public void testReduceNumber() {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("updateCount=" + updateCount);

    }
}