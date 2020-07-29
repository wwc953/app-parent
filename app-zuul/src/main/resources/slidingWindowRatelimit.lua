--
-- Created by IntelliJ IDEA.
-- User: Administrator
-- Date: 2020/7/29
-- Time: 13:46
-- To change this template use File | Settings | File Templates.

local listKey = KEYS[1];
local cycleTime = tonumber(ARGV[1]);
local limitCount = tonumber(ARGV[2]);
local currentTime = ARGV[3];

-- 当前时间 integer
-- 1495789058464908
-- 1596001963902
-- 开启单命令复制模式 , 解决Java报错问题
-- Write commands not allowed after non deterministic commands.
-- Call redis.replicate_commands() at the start of your script in order to switch to single commands replication mode.
--redis.replicate_commands();
--local ostime = redis.call('TIME');
--local currentTime = string.format("%d", ostime[1] * 1000000 + ostime[2]);

-- 当前队列长度
local listSize = redis.call("llen", listKey);

if (listSize < limitCount) then
    -- 当前时间放入队头
    redis.call("lpush", listKey, currentTime);
    return true;
else
    -- 获取最后一个元素
    local lastIndexValue = redis.call("lindex", listKey, -1);

    if (tonumber(currentTime) - tonumber(lastIndexValue) >= cycleTime) then
        redis.call("lpush", listKey, currentTime);
        redis.call("rpop", listKey);
        return true;
    else
        return false;
    end
end


