package com.tao.commons.redisson;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisUnlockTransactionalEventReq {

    @ApiModelProperty(value = "是否已获取锁")
    private boolean lock;

    @ApiModelProperty(value = "获取锁开始时间")
    private long startTime;

    @ApiModelProperty(value = "锁关键字", required = true)
    @NotBlank(message = "锁关键字不能为空!")
    private String lockKey;

    public RedisUnlockTransactionalEventReq(boolean lock, @NotBlank(message = "锁关键字不能为空!") String lockKey) {
        this.lock = lock;
        this.lockKey = lockKey;
    }
}