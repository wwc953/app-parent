--调用系统时间作为锁标识：相当于线程获取了锁，并给锁加一个标志，这个标志是独有的，
--不被其他线程所知或者所得；其中KEYS[1] 是传入redis的key参数，不同的key值对应不同的锁
local ostime=redis.call('TIME') ;
local time=string.format("%d",ostime[1]*1000000+ostime[2]) ;
--CAS模式设值，保证锁只能被一个线程设置成功
local flag = redis.call("setnx",KEYS[1],time);
if(flag > 0) then --锁标识设置成功
    --对获取的锁设置过期时间（根据业务场景增加过期设置）
	redis.call("expire",KEYS[1],ARGV[1]);
	return redis.call("get",KEYS[1]);
else
	return "0";  --锁标识设置失败
end