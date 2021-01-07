local status = 1
local reserved = {}
local idx = 'RESERVED:' .. KEYS[#KEYS]
local key, balanceQty, reservedQty, applyQty
if redis.call('exists', idx) == 1 then
    status = 0
else
    for index = 1, #KEYS - 1 do
        key = KEYS[index]
        applyQty = tonumber(ARGV[index] or 0)
        balanceQty = tonumber(redis.call('hget', key, 'balanceQty') or 0)
        reservedQty = (balanceQty > applyQty) and applyQty or balanceQty
        if (reservedQty > 0) then
            redis.call('hincrby', key, 'balanceQty', -reservedQty)
            redis.call('hincrby', key, 'reservedQty', reservedQty)
        end
        redis.call('hset', idx, key, reservedQty)
        reserved[key] = reservedQty
    end
end
reserved['status'] = status

return cjson.encode(reserved)