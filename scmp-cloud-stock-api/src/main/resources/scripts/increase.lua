local status = 1
local increased = {}
local idx = 'INCREASED:' .. KEYS[#KEYS]
local key, balanceQty, increasedQty, applyQty
if redis.call('exists', idx) == 1 then
    status = 0
else
    for index = 1, #KEYS - 1 do
        key = KEYS[index]
        applyQty = tonumber(ARGV[index] or 0)
        increasedQty = applyQty
        if (increasedQty > 0) then
            redis.call('hincrby', key, 'increasedQty', increasedQty)
            redis.call('hincrby', key, 'balanceQty', increasedQty)
        end
        redis.call('hset', idx, key, increasedQty)
        increased[key] = increasedQty
    end
end
increased['status'] = status

return cjson.encode(increased)