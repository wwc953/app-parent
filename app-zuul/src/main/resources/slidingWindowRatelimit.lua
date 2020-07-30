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
    return "true";
else
    -- 获取最后一个元素
    local lastIndexValue = redis.call("lindex", listKey, -1);

    if (tonumber(currentTime) - tonumber(lastIndexValue) >= cycleTime) then
        redis.call("lpush", listKey, currentTime);
        -- 当limitCount由100变成10的时候，无法改变list的总长度
        -- redis.call("rpop", listKey);
        redis.call("ltrim", listKey, 0, limitCount - 1);
        return "true";
    else
        return "false";
    end
end


--？？？？？？
--Lua脚本中的变量(动态数据)请使用KEYS和ARGV获取，如果把变量放在脚本中，
-- 必然会导致每次的脚本内容都不同(SHA1)，Redis缓存大量无用或者一次性的脚本内容

--redis.call()和redis.pcall()区别：
--redis.call() 在执行命令的过程中发生错误时，脚本会停止执行，并返回一个脚本错误，错误的输出信息会说明错误造成的原因：
--redis.pcall() 出错时并不引发(raise)错误，而是返回一个带 err 域的 Lua 表(table)，用于表示错误：