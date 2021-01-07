local status = 1
local reduced = {}
local idx = 'REDUCED:' .. KEYS[#KEYS]
local key, balanceQty, reducedQty, applyQty
if redis.call('exists', idx) == 1 then
    status = 0
else
    for index = 1, #KEYS - 1 do
        key = KEYS[index]
        applyQty = tonumber(ARGV[index] or 0)
        balanceQty = tonumber(redis.call('hget', key, 'balanceQty') or 0)
        reducedQty = (balanceQty > applyQty) and applyQty or balanceQty
        if (applyQty > balanceQty) then status = -2 end
        reduced[key] = reducedQty
    end
    if status > 0 then
        for key, reducedQty in pairs(reduced) do
            redis.call('hincrby', key, 'balanceQty', -reducedQty)
            redis.call('hincrby', key, 'reducedQty', reducedQty)
            redis.call('hset', idx, key, reducedQty)
        end
    end
end
reduced['status'] = status

return cjson.encode(reduced)