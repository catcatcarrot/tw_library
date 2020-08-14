package com.zy.library.service.impl;

import com.zy.library.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DealExpiredBooksService {

    @Autowired
    private RedisUtil redisUtil;

    @Value("${book.remove.count}")
    private Integer bookRemoveCount;

    @Scheduled(fixedRate = 5 * 1000 * 60)
    public void removeExpiredBooks() {
        new Thread(() -> {

            Long hotBooksCount = redisUtil.zCard("hotBooks");

            if (hotBooksCount > bookRemoveCount) {
                redisUtil.removeRange("hotBooks", 0, -(bookRemoveCount + 1));
            }
        }).start();
    }


}
